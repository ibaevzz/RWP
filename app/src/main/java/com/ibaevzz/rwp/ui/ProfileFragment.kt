package com.ibaevzz.rwp.ui

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
import com.ibaevzz.rwp.databinding.FragmentProfileBinding
import com.ibaevzz.rwp.model.ProfileViewModel
import javax.inject.Inject

class ProfileFragment: Fragment() {

    @Inject
    lateinit var factory: ProfileViewModel.Factory

    private val viewModel: ProfileViewModel by lazy{
        ViewModelProvider(this, factory)[ProfileViewModel::class.java]
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

        viewModel.liveDataProfile.observe(viewLifecycleOwner){
            viewModel.profile = it
            updateUI()
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

    private fun updateUI(){

    }

}