package com.ibaevzz.rwp.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.ibaevzz.rwp.databinding.FragmentSignInBinding

class SignInFragment: Fragment() {

    private lateinit var binding: FragmentSignInBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSignInBinding.inflate(inflater)
        binding.back.setOnClickListener{
            findNavController().navigateUp()
        }
        binding.reg.setOnClickListener{
            findNavController().navigate(SignInFragmentDirections.actionSignInFragmentToRegistrationFragment())
        }
        return binding.root
    }

}