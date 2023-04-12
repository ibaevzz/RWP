package com.ibaevzz.rwp.auth.ui

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.ibaevzz.rwp.ui.MainActivity
import com.ibaevzz.rwp.auth.models.EmailSignInViewModel
import com.ibaevzz.rwp.databinding.FragmentEmailSignInBinding

class EmailSignInFragment: Fragment() {

    private lateinit var binding: FragmentEmailSignInBinding
    private val viewModel: EmailSignInViewModel by lazy{
        ViewModelProvider(this)[EmailSignInViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentEmailSignInBinding.inflate(inflater)

        binding.back.setOnClickListener {
            findNavController().navigateUp()
        }
        binding.signIn.setOnClickListener {
            signInEmail()
        }
        binding.email.doOnTextChanged { _, _, _, _ ->
            binding.badReg.visibility = View.INVISIBLE
            binding.badReg.text = ""
        }
        binding.password.doOnTextChanged { _, _, _, _ ->
            binding.badReg.visibility = View.INVISIBLE
            binding.badReg.text = ""
        }

        return binding.root
    }

    private fun signInEmail(){
        val email = binding.email.text.toString().replace(" ", "")
        val password = binding.password.text.toString()
        binding.progress.visibility = View.VISIBLE
        binding.parent.visibility = View.INVISIBLE
        viewModel.signInEmail(email, password).observe(viewLifecycleOwner){
            if(it==null){
                val intent = Intent(activity, MainActivity::class.java)
                startActivity(intent)
                activity?.finish()
            }else if(it.message!=""){
                binding.badReg.visibility = View.VISIBLE
                binding.badReg.text = it.message
                binding.progress.visibility = View.INVISIBLE
                binding.parent.visibility = View.VISIBLE
            }
        }
    }

}