package com.example.dyplom.presentation.ui.auth.registration

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.dyplom.R
import com.example.dyplom.data.UIState
import com.example.dyplom.databinding.FragmentSignUpBinding
import com.example.dyplom.presentation.ui.auth.UserViewModel
import com.example.dyplom.presentation.ui.util.ext.hide
import com.example.dyplom.presentation.ui.util.ext.show
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


const val PASSWORD_MIN_LENGTH = 8

@AndroidEntryPoint
class SignUpFragment : Fragment() {

    private val binding: FragmentSignUpBinding by lazy {
        FragmentSignUpBinding.inflate(
            layoutInflater
        )
    }
    private val viewModel: UserViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        setListeners()
        setObservers()
        return binding.root
    }

    private fun setObservers() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.isRegistered.collect {
                    if (it is UIState.Success) {
                        disableLoading()
                        findNavController().popBackStack(R.id.viewPagerFragment, false)
                    } else if (it is UIState.Error) {
                        disableLoading()
                        Toast.makeText(
                            requireContext(),
                            getString(R.string.user_with_this_email_already_exist),
                            Toast.LENGTH_SHORT
                        ).show()
                    } else if (it is UIState.Loading) {
                        showLoading()
                    }
                }
            }
        }
    }

    private fun showLoading() {
        with(binding) {
            buttonSignUp.isEnabled = false
            progressBar.show()
            buttonSignUp.setBackgroundDrawable(resources.getDrawable(R.drawable.black_button_pressed_background))
            buttonSignUp.setTextColor(Color.TRANSPARENT)
        }
    }

    private fun disableLoading() {
        with(binding) {
            progressBar.hide()
            buttonSignUp.isEnabled = true
            buttonSignUp.setBackgroundDrawable(resources.getDrawable(R.drawable.black_button_background))
            buttonSignUp.setTextColor(Color.WHITE)
        }
    }

    private fun setListeners() {
        with(binding) {
            buttonSignUp.setOnClickListener {
                signUp()
            }
            buttonToSignIn.setOnClickListener {
                findNavController().popBackStack()
            }
        }
    }

    private fun isValidRegisterData(): Boolean {
        var isValidRegisterData = true
        with(binding) {
            if (edittextEmail.text.toString().isBlank()) {
                textInputLayoutEmail.error = getString(R.string.cannot_be_blank)
                isValidRegisterData = false
            }
            if (edittextPassword.text.toString().isBlank()) {
                textInputLayoutPassword.error = getString(R.string.cannot_be_blank)
                isValidRegisterData = false
            }
            if (edittextPassword.text.toString().length < PASSWORD_MIN_LENGTH) {
                textInputLayoutPassword.error =
                    getString(R.string.password_must_contain_at_least_8_symbols)
                isValidRegisterData = false
            }
            if (!edittextEmail.text.toString().matches(Patterns.EMAIL_ADDRESS.toRegex())) {
                textInputLayoutEmail.error = getString(R.string.incorrect_email)
                isValidRegisterData = false
            }
        }
        return isValidRegisterData
    }

    private fun signUp() {
        if (isValidRegisterData()) {
            viewModel.createUser(
                binding.edittextUsername.text.toString(),
                binding.edittextEmail.text.toString(),
                binding.edittextPassword.text.toString()
            )
        }
    }

}