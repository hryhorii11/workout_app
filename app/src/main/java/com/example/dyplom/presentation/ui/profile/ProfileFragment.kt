package com.example.dyplom.presentation.ui.profile

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.dyplom.databinding.FragmentProfileBinding
import com.example.dyplom.presentation.ui.auth.UserViewModel
import com.example.dyplom.presentation.ui.mainViewPager.MainViewPagerFragmentDirections
import com.example.dyplom.presentation.ui.util.ext.setCirclePhoto
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ProfileFragment : Fragment() {
    private val binding: FragmentProfileBinding by lazy {
        FragmentProfileBinding.inflate(layoutInflater)
    }
    private val viewModel: ProfileViewModel by viewModels()
    private var imageUri: Uri? = null
    private lateinit var photoActivityResult: ActivityResultLauncher<Intent>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        setListeners()
        setObservers()
        viewModel.setUser()
        Log.i("hui","f")
        setActivityResultLauncher()
        return binding.root
    }

    private fun setUserData() {
        with(binding) {
            textViewUsername.text = viewModel.user.value?.displayName
            imageViewUserPhoto.setCirclePhoto(viewModel.user.value?.photoUrl.toString())
        }
    }

    private fun setObservers() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.user.collect() {
                    if (it != null) {
                        binding.textViewUsername.text = it.displayName
                        binding.imageViewUserPhoto.setCirclePhoto(it.photoUrl.toString())
                    }
                }
            }
        }
    }

    private fun setListeners() {
        binding.btn.setOnClickListener {
            findNavController().navigate(
                MainViewPagerFragmentDirections.actionViewPagerFragmentToSignInFragment()
            )
        }
        binding.imageViewUserPhoto.setOnClickListener {
            addPhoto()
        }
    }

    @SuppressLint("Recycle")
    private fun setActivityResultLauncher() {
        photoActivityResult = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) {
            if (it.resultCode == Activity.RESULT_OK) {
                imageUri = it.data?.data
                imageUri?.let { it1 -> viewModel.setUserImage(it1) }
                binding.imageViewUserPhoto.setCirclePhoto(imageUri.toString())
            }
        }
    }

    private fun addPhoto() {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "image/*"
        photoActivityResult.launch(intent)
    }

}