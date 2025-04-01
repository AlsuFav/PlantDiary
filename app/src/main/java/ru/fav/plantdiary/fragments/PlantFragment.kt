package ru.fav.plantdiary.fragments

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import kotlinx.coroutines.launch
import ru.fav.plantdiary.MainActivity
import ru.fav.plantdiary.R
import ru.fav.plantdiary.databinding.FragmentPlantBinding
import ru.fav.plantdiary.di.ServiceLocator
import ru.fav.plantdiary.utils.NavigationAction


class PlantFragment : Fragment(R.layout.fragment_plant) {
    private var viewBinding: FragmentPlantBinding? = null
    private val plantRepository = ServiceLocator.getPlantRepository()
    private var userId: String? = null
    private var plantId: String? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewBinding = FragmentPlantBinding.bind(view)

        userId = arguments?.getString(USER_ID)
        plantId = arguments?.getString(PLANT_ID)

        if (userId.isNullOrEmpty()) {
            handleUnknownUser()
            return
        }

        if (plantId.isNullOrEmpty()) {
            handleUnknownPlant()
            return
        }

        loadPlant()
    }

    private fun loadPlant() {
        lifecycleScope.launch {
            val plant = plantId?.let { plantRepository.getPlantById(it) }
            plant?.let {
                viewBinding?.textViewPlantName?.text = it.name
                viewBinding?.textViewPlantingYear?.text = it.plantingYear.toString()
                viewBinding?.textViewCareInstructions?.text = it.careInstructions

                it.photo?.let { byteArray ->
                    Glide.with(this@PlantFragment)
                        .asBitmap()
                        .load(byteArray)
                        .into(viewBinding?.imageViewPlantPhoto!!)
                } ?: run {
                    Glide.with(this@PlantFragment)
                        .load(R.drawable.ic_plant)
                        .into(viewBinding?.imageViewPlantPhoto!!)
                }
            }
        }
    }

    private fun handleUnknownUser() {
        showToast(getString(R.string.error_unknown_user))

        requireActivity().supportFragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)

        (requireActivity() as? MainActivity)?.navigate(
            destination = AuthorizationFragment(),
            destinationTag = AuthorizationFragment.AUTHORIZATION_TAG,
            action = NavigationAction.REPLACE,
            isAddToBackStack = false
        )
    }

    private fun handleUnknownPlant() {
        showToast(getString(R.string.error_unknown_plant))

        userId?.let { safeUserId ->
            (requireActivity() as? MainActivity)?.navigate(
                destination = PlantsFragment.getInstance(safeUserId),
                destinationTag = PlantsFragment.PLANTS_TAG,
                action = NavigationAction.REPLACE,
                isAddToBackStack = false
            )
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    override fun onDestroy() {
        super.onDestroy()
        viewBinding = null
    }

    companion object {
        const val PLANT_TAG = "PLANT_TAG"

        private const val USER_ID = "ID"
        private const val PLANT_ID = "ID"

        fun getInstance(userId: String, plantId: String): PlantFragment {
            return PlantFragment().apply {
                arguments = bundleOf(USER_ID to userId, PLANT_ID to plantId)
            }
        }
    }
}