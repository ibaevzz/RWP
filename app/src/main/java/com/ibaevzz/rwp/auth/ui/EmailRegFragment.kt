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
import com.ibaevzz.rwp.MainActivity
import com.ibaevzz.rwp.auth.models.EmailRegViewModel
import com.ibaevzz.rwp.databinding.FragmentEmailRegBinding

class EmailRegFragment: Fragment(){

    private lateinit var binding: FragmentEmailRegBinding
    private val viewModel: EmailRegViewModel by lazy {
        ViewModelProvider(this)[EmailRegViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentEmailRegBinding.inflate(inflater)
        binding.back.setOnClickListener {
            findNavController().navigateUp()
        }
        binding.reg.setOnClickListener {
            binding.progress.visibility = View.VISIBLE
            binding.parent.visibility = View.INVISIBLE
            regWithEmail()
        }
        binding.email.doOnTextChanged { _, _, _, _ ->
            binding.badReg.visibility = View.INVISIBLE
            binding.badReg.text = ""
        }
        binding.password.doOnTextChanged { _, _, _, _ ->
            binding.badReg.visibility = View.INVISIBLE
            binding.badReg.text = ""
        }
        binding.password2.doOnTextChanged { _, _, _, _ ->
            binding.badReg.visibility = View.INVISIBLE
            binding.badReg.text = ""
        }
        return binding.root
    }

    private fun regWithEmail(){
        val email = binding.email.text.toString().replace(" ", "")
        val password = binding.password.text.toString()
        val password2 = binding.password2.text.toString()
        if(password==password2){
            viewModel.regWithEmail(email, password).observe(viewLifecycleOwner){
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
        }else{
            binding.badReg.visibility = View.VISIBLE
            binding.badReg.text = "Пароли не совпадают"
        }
    }
}