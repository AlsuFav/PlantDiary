package ru.fav.plantdiary.fragments


import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import ru.fav.plantdiary.MainActivity
import ru.fav.plantdiary.R
import ru.fav.plantdiary.data.db.entities.UserEntity
import ru.fav.plantdiary.databinding.FragmentRegistrationBinding
import ru.fav.plantdiary.di.ServiceLocator
import ru.fav.plantdiary.utils.NavigationAction
import ru.fav.plantdiary.utils.validators.EmailValidator

class RegistrationFragment: Fragment(R.layout.fragment_registration) {

    private var viewBinding: FragmentRegistrationBinding? = null
    private val userRepository = ServiceLocator.getUserRepository()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewBinding = FragmentRegistrationBinding.bind(view)

        viewBinding?.buttonSignUp?.setOnClickListener {
            registerUser()
        }

        viewBinding?.buttonSignIn?.setOnClickListener {
            (requireActivity() as? MainActivity)?.navigate(
                destination = AuthorizationFragment(),
                destinationTag = AuthorizationFragment.AUTHORIZATION_TAG,
                action = NavigationAction.REPLACE,
                isAddToBackStack = true)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        viewBinding = null
    }

    private fun registerUser() {
        val surname = viewBinding?.editTextSurname?.text.toString().trim()
        val name = viewBinding?.editTextName?.text.toString().trim()
        val email = viewBinding?.editTextEmail?.text.toString().trim()
        val password = viewBinding?.editTextPassword?.text.toString().trim()
        val confirmPassword = viewBinding?.editTextConfirmPassword?.text.toString().trim()

        val toastText = when {
            surname.isEmpty() || name.isEmpty() || email.isEmpty() || password.isEmpty() -> R.string.fill_all_fields

            ! EmailValidator().isEmailValid(email) -> R.string.invalid_email

            password != confirmPassword -> R.string.passwords_are_not_the_same

            else -> null
        }
        toastText?.let { textRes ->
            showToast(getString(textRes))
            return
        }

        lifecycleScope.launch {
            val existingUser = userRepository.getUserByEmail(email)
            if (existingUser != null) {
                showToast(getString(R.string.user_already_exists))
                return@launch
            }

            val user = UserEntity(
                email = email,
                name = name,
                surname = surname,
                password = password
            )

            userRepository.saveUserData(user)


            (requireActivity() as? MainActivity)?.saveLoginState(
                id = user.id
            )

            (requireActivity() as? MainActivity)?.navigate(
                destination = PlantsFragment.getInstance(param = user.id),
                destinationTag = PlantsFragment.PLANTS_TAG,
                action = NavigationAction.REPLACE,
                isAddToBackStack = true
            )
        }

    }

    private fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    companion object {
        const val REGISTRATION_TAG = "REGISTRATION_TAG"
    }
}