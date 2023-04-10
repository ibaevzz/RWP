package com.ibaevzz.rwp.auth.ui

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.ibaevzz.rwp.MainActivity
import com.ibaevzz.rwp.R
import com.ibaevzz.rwp.auth.models.MainRegViewModel
import com.ibaevzz.rwp.databinding.FragmentMainRegistrationBinding

class MainRegistrationFragment: Fragment() {

    private lateinit var binding: FragmentMainRegistrationBinding
    private val viewModel: MainRegViewModel by lazy {
        ViewModelProvider(this)[MainRegViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainRegistrationBinding.inflate(inflater)
        binding.registration.setOnClickListener{
            findNavController().navigate(R.id.registrationFragment)
        }
        binding.signIn.setOnClickListener{
            findNavController().navigate(R.id.signInFragment)
        }
        binding.guest.setOnClickListener{
            binding.parent.visibility = View.INVISIBLE
            binding.progress.visibility = View.VISIBLE
            viewModel.signInGuest().observe(viewLifecycleOwner){result->
                if(result){
                    val intent = Intent(activity, MainActivity::class.java)
                    startActivity(intent)
                    activity?.finish()
                }else{
                    binding.parent.visibility = View.VISIBLE
                    binding.progress.visibility = View.INVISIBLE
                }
            }
        }
        return binding.root
    }
}