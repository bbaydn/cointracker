package com.ba.bitcointicker.ui.profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.ba.bitcointicker.R
import com.ba.bitcointicker.databinding.FragmentProfileBinding
import com.ba.bitcointicker.viewmodel.AuthViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment : Fragment() {

  private lateinit var binding: FragmentProfileBinding
  private val authViewModel: AuthViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProfileBinding.inflate(layoutInflater, container, false)
        binding.buttonFav.setOnClickListener {
            findNavController().navigate(R.id.action_profileFragment_to_favoritesFragment)
        }
        binding.logOut.setOnClickListener {
            authViewModel.logOut {
                findNavController().navigate(R.id.action_profileFragment_to_loginFragment)
            }
        }

        return binding.root
    }

}