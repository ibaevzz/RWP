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

private const val PROFILE_TAG = "PROFILE"
private const val BOOKS_TAG = "BOOKS"
private const val FILMS_TAG = "FILMS"
private const val GAMES_TAG = "GAMES"

class MainActivity: AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var currentTAG = ""

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        if (Firebase.auth.currentUser==null){
            startActivity(Intent(this, AuthActivity::class.java))
            finish()
        }

        binding = ActivityMainBinding.inflate(layoutInflater)
        binding.navigation.setOnItemSelectedListener(NavigationBarView.OnItemSelectedListener {
            return@OnItemSelectedListener onItemSelected(it.itemId)
        })

        if(savedInstanceState==null){
            supportFragmentManager.beginTransaction()
                .add(R.id.container, BooksFragment(), BOOKS_TAG).commit()
            currentTAG = BOOKS_TAG
        }

        setContentView(binding.root)
    }

    private fun onItemSelected(itemId: Int): Boolean{
        when(itemId){
            R.id.books->{
                if(currentTAG!=""){
                    supportFragmentManager.beginTransaction()
                        .hide(supportFragmentManager.findFragmentByTag(currentTAG)!!).commit()
                }
                if(supportFragmentManager.findFragmentByTag(BOOKS_TAG)==null) {
                    supportFragmentManager.beginTransaction()
                        .add(R.id.container, BooksFragment(), BOOKS_TAG).commit()
                }else{
                    supportFragmentManager.beginTransaction()
                        .show(supportFragmentManager.findFragmentByTag(BOOKS_TAG)!!).commit()
                }
                currentTAG = BOOKS_TAG
                return true
            }
            R.id.films->{
                if(currentTAG!=""){
                    supportFragmentManager.beginTransaction()
                        .hide(supportFragmentManager.findFragmentByTag(currentTAG)!!).commit()
                }
                if(supportFragmentManager.findFragmentByTag(FILMS_TAG)==null) {
                    supportFragmentManager.beginTransaction()
                        .add(R.id.container, FilmsFragment(), FILMS_TAG).commit()
                }else{
                    supportFragmentManager.beginTransaction()
                        .show(supportFragmentManager.findFragmentByTag(FILMS_TAG)!!).commit()
                }
                currentTAG = FILMS_TAG
                return true
            }
            R.id.games->{
                if(currentTAG!=""){
                    supportFragmentManager.beginTransaction()
                        .hide(supportFragmentManager.findFragmentByTag(currentTAG)!!).commit()
                }
                if(supportFragmentManager.findFragmentByTag(GAMES_TAG)==null) {
                    supportFragmentManager.beginTransaction()
                        .add(R.id.container, GamesFragment(), GAMES_TAG).commit()
                }else{
                    supportFragmentManager.beginTransaction()
                        .show(supportFragmentManager.findFragmentByTag(GAMES_TAG)!!).commit()
                }
                currentTAG = GAMES_TAG
                return true
            }
            R.id.profile->{
                if(currentTAG!=""){
                    supportFragmentManager.beginTransaction()
                        .hide(supportFragmentManager.findFragmentByTag(currentTAG)!!).commit()
                }
                if(supportFragmentManager.findFragmentByTag(PROFILE_TAG)==null) {
                    supportFragmentManager.beginTransaction()
                        .add(R.id.container, ProfileFragment(), PROFILE_TAG).commit()
                }else{
                    supportFragmentManager.beginTransaction()
                        .show(supportFragmentManager.findFragmentByTag(PROFILE_TAG)!!).commit()
                }
                currentTAG = PROFILE_TAG
                return true
            }
        }
        return false
    }

}