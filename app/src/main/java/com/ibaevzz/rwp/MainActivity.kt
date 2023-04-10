package com.ibaevzz.rwp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.ibaevzz.rwp.auth.ui.AuthActivity
import com.ibaevzz.rwp.databinding.ActivityMainBinding

class MainActivity: AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        if (Firebase.auth.currentUser==null){
            startActivity(Intent(this, AuthActivity::class.java))
            finish()
        }
        binding = ActivityMainBinding.inflate(layoutInflater)
        binding.button.setOnClickListener{
            Firebase.auth.signOut()
            startActivity(Intent(this, AuthActivity::class.java))
            finish()
        }
        setContentView(binding.root)
    }

}