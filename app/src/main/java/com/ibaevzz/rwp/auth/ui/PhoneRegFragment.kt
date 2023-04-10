package com.ibaevzz.rwp.auth.ui

import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.firebase.FirebaseException
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.ibaevzz.rwp.MainActivity
import com.ibaevzz.rwp.R
import com.ibaevzz.rwp.auth.models.PhoneRegViewModel
import com.ibaevzz.rwp.databinding.FragmentPhoneRegBinding
import java.util.concurrent.TimeUnit

class PhoneRegFragment: Fragment() {

    private lateinit var binding: FragmentPhoneRegBinding
    private val viewModel: PhoneRegViewModel by lazy{
        ViewModelProvider(this)[PhoneRegViewModel::class.java]
    }
    private val timer = Timer()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPhoneRegBinding.inflate(inflater)
        binding.back.setOnClickListener {
            findNavController().navigateUp()
        }
        binding.getCode.setOnClickListener {
            getCode()
        }
        binding.phone.doOnTextChanged { _, _, _, _ ->
            binding.badReg.visibility = View.INVISIBLE
            binding.badReg.text
        }
        return binding.root
    }

    private fun getCode(){
        binding.progress.visibility = View.VISIBLE
        val number = binding.phone.text.toString().let {
            if(it!="") {
                it.replace(" ", "")
                if (it[0] == '8') {
                    "+7" + it.substring(1)
                } else {
                    it
                }
            }else {
                "0"
            }
        }

        val options = PhoneAuthOptions.newBuilder(Firebase.auth)
            .setPhoneNumber(number)
            .setTimeout(60L, TimeUnit.SECONDS)
            .setActivity(requireActivity())
            .setCallbacks(object: PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                override fun onVerificationCompleted(credential: PhoneAuthCredential) {
                    binding.parent.visibility = View.INVISIBLE
                    binding.progress.visibility = View.VISIBLE
                    viewModel.signInWithPhone(credential).observe(viewLifecycleOwner){
                        if(it){
                            startMainActivity()
                        }else{
                            binding.parent.visibility = View.VISIBLE
                            binding.progress.visibility = View.INVISIBLE
                        }
                    }
                }

                override fun onVerificationFailed(exc: FirebaseException) {
                    binding.badReg.text = exc.message
                    binding.badReg.visibility = View.VISIBLE
                    binding.progress.visibility = View.INVISIBLE
                }

                override fun onCodeSent(id: String, token: PhoneAuthProvider.ForceResendingToken) {
                    super.onCodeSent(id, token)
                    binding.progress.visibility = View.INVISIBLE
                    binding.reg.visibility = View.VISIBLE
                    binding.codeL.visibility = View.VISIBLE
                    binding.getCode.isEnabled = false
                    binding.getCode.setTextColor(resources.getColor(android.R.color.darker_gray))
                    binding.getCode.text = "Можно запросить заново через 60 секунд"
                    binding.reg.setOnClickListener{
                        binding.parent.visibility = View.INVISIBLE
                        binding.progress.visibility = View.VISIBLE
                        val code = binding.code.text.toString()
                        val credential = PhoneAuthProvider.getCredential(id, code)
                        viewModel.signInWithPhone(credential).observe(viewLifecycleOwner){result->
                            if(result){
                                startMainActivity()
                            }else{
                                binding.parent.visibility = View.VISIBLE
                                binding.progress.visibility = View.INVISIBLE
                            }
                        }
                    }
                    timer.start()
                }
            })
            .build()
        PhoneAuthProvider.verifyPhoneNumber(options)
    }

    private fun startMainActivity(){
        timer.cancel()
        val intent = Intent(activity, MainActivity::class.java)
        startActivity(intent)
        activity?.finish()
    }

    private inner class Timer(a: Long = 60000, b: Long = 1000) : CountDownTimer(a, b) {
        override fun onTick(millisUntilFinished: Long) {
            binding.getCode.text = "Можно запросить заново через ${millisUntilFinished/1000} секунд"
        }

        override fun onFinish() {
            binding.getCode.text = "Запросить заново"
            binding.getCode.setTextColor(resources.getColor(R.color.purple_700))
            binding.getCode.isEnabled = true
        }
    }
}