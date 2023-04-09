package com.ibaevzz.rwp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.ibaevzz.rwp.databinding.FragmentMainRegistrationBinding

class MainRegistrationFragment : Fragment() {

    private lateinit var binding: FragmentMainRegistrationBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainRegistrationBinding.inflate(inflater)
        binding.registration.setOnClickListener{
            findNavController().navigate(R.id.registrationFragment)
        }
        return binding.root
    }
}