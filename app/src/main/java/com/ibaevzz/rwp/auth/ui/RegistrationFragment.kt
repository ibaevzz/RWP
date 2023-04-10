package com.ibaevzz.rwp.auth.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.ibaevzz.rwp.App
import com.ibaevzz.rwp.MainActivity
import com.ibaevzz.rwp.auth.models.RegistrationViewModel
import com.ibaevzz.rwp.databinding.FragmentRegistrationBinding
import javax.inject.Inject

class RegistrationFragment: Fragment() {

    @Inject
    lateinit var googleSignInClient: GoogleSignInClient

    private lateinit var binding: FragmentRegistrationBinding
    private val viewModel: RegistrationViewModel by lazy {
        ViewModelProvider(this)[RegistrationViewModel::class.java]
    }

    private val launcher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            firebaseAuthWithGoogle(result)
        }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (activity?.application as App).appComponent.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRegistrationBinding.inflate(inflater)
        binding.back.setOnClickListener {
            findNavController().navigateUp()
        }
        binding.signIn.setOnClickListener {
            findNavController().navigate(RegistrationFragmentDirections.actionRegistrationFragmentToSignInFragment())
        }
        binding.regWithGoogle.setOnClickListener {
            launcher.launch(googleSignInClient.signInIntent)
        }
        binding.regWithEmail.setOnClickListener{
            findNavController().navigate(RegistrationFragmentDirections.actionRegistrationFragmentToEmailRegFragment())
        }
        binding.regWithNumber.setOnClickListener {
            findNavController().navigate(RegistrationFragmentDirections.actionRegistrationFragmentToPhoneRegFragment())
        }
        return binding.root
    }

    private fun firebaseAuthWithGoogle(result: ActivityResult){
        binding.parent.visibility = View.INVISIBLE
        binding.progress.visibility = View.VISIBLE
        viewModel.activityResult(result).observe(viewLifecycleOwner){
            if(it){
                googleSignInClient.revokeAccess()
                val intent = Intent(activity, MainActivity::class.java)
                startActivity(intent)
                activity?.finish()
            }else{
                binding.parent.visibility = View.VISIBLE
                binding.progress.visibility = View.INVISIBLE
            }
        }
    }

}