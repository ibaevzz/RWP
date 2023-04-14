package com.ibaevzz.rwp.ui

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.ibaevzz.rwp.App
import com.ibaevzz.rwp.R
import com.ibaevzz.rwp.auth.ui.AuthActivity
import com.ibaevzz.rwp.data.Profile
import com.ibaevzz.rwp.databinding.FragmentProfileBinding
import com.ibaevzz.rwp.model.ProfileViewModel
import kotlinx.coroutines.*
import javax.inject.Inject

class ProfileFragment: Fragment() {

    @Inject
    lateinit var factory: ProfileViewModel.Factory

    private val viewModel: ProfileViewModel by lazy{
        ViewModelProvider(this, factory)[ProfileViewModel::class.java]
    }

    @OptIn(DelicateCoroutinesApi::class)
    private val scope = GlobalScope.launch(Dispatchers.IO) {
        var bool = true
        var i = 0.0f
        while (true) {
            launch (Dispatchers.Main) {
                binding.bio.alpha = i%1
                binding.email.alpha = i%1
                binding.name.alpha = i%1
                binding.phone.alpha = i%1
            }
            if(i>=0.95f) {
                bool = false
            }else if(i<=0.05f){
                bool = true
            }
            if(bool){
                i+=0.05f
            }else{
                i-=0.05f
            }
            delay(50)
        }
    }

    private lateinit var binding: FragmentProfileBinding
    private lateinit var builder: AlertDialog.Builder

    override fun onAttach(context: Context) {
        (activity?.application as App).appComponent.inject(this)
        super.onAttach(context)

        builder = AlertDialog.Builder(context).setTitle("Вы точно хотите выйти?")
            .setPositiveButton("Да"){_, _ ->
                Firebase.auth.signOut()
                activity?.startActivity(Intent(activity, AuthActivity::class.java))
                activity?.finish()
            }
            .setNegativeButton("Нет"){dialog, _ ->
                dialog.cancel()
            }
            .setView(R.layout.sad_cat)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProfileBinding.inflate(inflater)
        binding.logout.setOnClickListener{
            signOut()
        }
        binding.update.setOnRefreshListener {
            viewModel.liveDataProfile.get().observe(viewLifecycleOwner){
                updateUI(it)
            }
            binding.update.isRefreshing = false
        }
        binding.bio.text = "Загрузка..."
        binding.email.text = "Загрузка..."
        binding.name.text = "Загрузка..."
        binding.phone.text = "Загрузка..."

        viewModel.liveDataProfile.get().observe(viewLifecycleOwner){
            updateUI(it)
            scope.cancel()
        }

        return binding.root
    }

    private fun signOut(){
        val dialog = builder.create()
        dialog.setOnShowListener{
            val positiveButton = dialog.getButton(DialogInterface.BUTTON_POSITIVE)
            positiveButton.setTextColor(resources.getColor(R.color.purple_700))
            val negativeButton = dialog.getButton(DialogInterface.BUTTON_NEGATIVE)
            negativeButton.setTextColor(resources.getColor(R.color.purple_700))
            val sadCat = dialog.findViewById<ImageView>(R.id.sad_cat)
            Glide.with(this).asGif().load(R.drawable.sad_cat).into(sadCat)
        }
        dialog.show()
    }

    @SuppressLint("SetTextI18n")
    private fun updateUI(profile: Profile){
        binding.bio.alpha = 1f
        binding.email.alpha = 1f
        binding.name.alpha = 1f
        binding.phone.alpha = 1f

        binding.name.text = profile.name+ " " + profile.surname
        if(profile.bio==""){
            binding.bio.text = "Описание отсутствует"
        }else{
            binding.bio.text = profile.bio
        }
        if(profile.email==""){
            binding.email.text = "Не привязан"
        }else{
            binding.email.text = profile.email
        }
        if(profile.phone==""){
            binding.phone.text = "Не привязан"
        }else{
            binding.phone.text = profile.phone
        }
        if(profile.photo!=""){
            Glide.with(this).load(profile.photo).into(binding.profilePhoto)
        }
    }

}