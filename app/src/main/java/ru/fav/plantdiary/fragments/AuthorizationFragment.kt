package ru.fav.plantdiary.fragments

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import ru.fav.plantdiary.MainActivity
import ru.fav.plantdiary.R
import ru.fav.plantdiary.databinding.FragmentAuthorizationBinding
import ru.fav.plantdiary.di.ServiceLocator
import ru.fav.plantdiary.utils.NavigationAction

class AuthorizationFragment: Fragment(R.layout.fragment_authorization) {

    private var viewBinding: FragmentAuthorizationBinding? = null
    private val userRepository = ServiceLocator.getUserRepository()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewBinding = FragmentAuthorizationBinding.bind(view)

        viewBinding?.buttonSignIn?.setOnClickListener {
            login()
        }

        viewBinding?.buttonSignUp?.setOnClickListener {
            (requireActivity() as? MainActivity)?.navigate(
                destination = RegistrationFragment(),
                destinationTag = RegistrationFragment.REGISTRATION_TAG,
                action = NavigationAction.REPLACE,
                isAddToBackStack = true)
        }
    }

    private fun login() {
        val email = viewBinding?.editTextEmail?.text.toString().trim()
        val password = viewBinding?.editTextPassword?.text.toString().trim()

        if (email.isEmpty() || password.isEmpty()) {
            showToast(getString(R.string.fill_all_fields))
            return
        }

        lifecycleScope.launch {
            val user = userRepository.authenticateUser(email, password)
            if (user != null) {
                (requireActivity() as? MainActivity)?.saveLoginState(
                    id = user.id
                )
                (requireActivity() as? MainActivity)?.navigate(
                    destination = PlantsFragment.getInstance(param = user.id),
                    destinationTag = PlantsFragment.PLANTS_TAG,
                    action = NavigationAction.REPLACE,
                    isAddToBackStack = true
                )
            } else {
                showToast(getString(R.string.incorrect_email_or_password))
            }
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
        const val AUTHORIZATION_TAG = "AUTHORIZATION_TAG"
    }
}