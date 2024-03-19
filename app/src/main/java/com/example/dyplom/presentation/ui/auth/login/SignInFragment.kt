package com.example.dyplom.presentation.ui.auth.login

import android.graphics.Color
import android.os.Bundle
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
import com.example.dyplom.databinding.FragmentSignInBinding
import com.example.dyplom.presentation.ui.auth.UserViewModel
import com.example.dyplom.presentation.ui.util.ext.hide
import com.example.dyplom.presentation.ui.util.ext.show
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SignInFragment : Fragment() {

    private val binding: FragmentSignInBinding by lazy {
        FragmentSignInBinding.inflate(
            layoutInflater
        )
    }
    private val viewModel: UserViewModel by viewModels()
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
                    when (it) {
                        is UIState.Success -> {
                            disableLoading()
                            findNavController().popBackStack()
                        }
                        is UIState.Error -> {
                            disableLoading()
                            Toast.makeText(
                                requireContext(),
                               it.error,
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                        is UIState.Loading -> {
                            showLoading()
                        }
                        else -> {}
                    }
                }
            }
        }

    }

    private fun showLoading() {
        with(binding) {
            buttonSignIn.isEnabled = false
            progressBar.show()
            buttonSignIn.setBackgroundDrawable(resources.getDrawable(R.drawable.black_button_pressed_background))
            buttonSignIn.setTextColor(Color.TRANSPARENT)
        }
    }

    private fun disableLoading() {
        with(binding) {
            progressBar.hide()
            buttonSignIn.isEnabled = true
            buttonSignIn.setBackgroundDrawable(resources.getDrawable(R.drawable.black_button_background))
            buttonSignIn.setTextColor(Color.WHITE)
        }
    }

    private fun setListeners() {
        with(binding) {
            buttonToSignUp.setOnClickListener {
                findNavController().navigate(SignInFragmentDirections.actionSignInFragmentToRegistrationFragment())
            }
            buttonSignIn.setOnClickListener {
                viewModel.loginUser(
                    edittextEmail.text.toString(),
                    edittextPassword.text.toString()
                )
            }
        }
    }
}