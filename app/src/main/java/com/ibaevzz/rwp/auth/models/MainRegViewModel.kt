package com.ibaevzz.rwp.auth.models

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.ibaevzz.rwp.data.Profile

class MainRegViewModel: ViewModel() {

    private val result = MutableLiveData<Boolean>()

    fun signInGuest(): LiveData<Boolean>{
        Firebase.auth.signInAnonymously()
            .addOnCompleteListener{
                if(it.isSuccessful){
                    val user = Firebase.auth.currentUser
                    val profile = Profile(uid = user?.uid ?:"", name = "Гость")
                    Firebase.database.reference.child("profiles").child(profile.uid).setValue(profile)
                    result.value = true
                }else{
                    result.value = false
                }
            }
        return result
    }

}