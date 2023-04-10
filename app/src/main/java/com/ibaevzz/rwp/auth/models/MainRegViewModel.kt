package com.ibaevzz.rwp.auth.models

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class MainRegViewModel: ViewModel() {

    private val result = MutableLiveData<Boolean>()

    fun signInGuest(): LiveData<Boolean>{
        Firebase.auth.signInAnonymously()
            .addOnCompleteListener{
                result.value = it.isSuccessful
            }
        return result
    }

}