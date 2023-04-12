package com.ibaevzz.rwp.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.navigation.NavigationBarView
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.ibaevzz.rwp.R
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
        binding.navigation.setOnItemSelectedListener(NavigationBarView.OnItemSelectedListener {
            when(it.itemId){
                R.id.books->{
                    supportFragmentManager.beginTransaction().replace(R.id.container, BooksFragment()).commit()
                    return@OnItemSelectedListener true
                }
                R.id.films->{
                    supportFragmentManager.beginTransaction().replace(R.id.container, FilmsFragment()).commit()
                    return@OnItemSelectedListener true
                }
                R.id.games->{
                    supportFragmentManager.beginTransaction().replace(R.id.container, GamesFragment()).commit()
                    return@OnItemSelectedListener true
                }
                R.id.profile->{
                    supportFragmentManager.beginTransaction().replace(R.id.container, ProfileFragment()).commit()
                    return@OnItemSelectedListener true
                }
            }
            return@OnItemSelectedListener false
        })

        setContentView(binding.root)
    }

}