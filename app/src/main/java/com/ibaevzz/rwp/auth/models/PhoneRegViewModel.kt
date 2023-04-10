package com.ibaevzz.rwp.auth.models

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class PhoneRegViewModel: ViewModel() {

    private val complete = MutableLiveData<Boolean>()

    fun signInWithPhone(credential: PhoneAuthCredential): LiveData<Boolean>{
        Firebase.auth.signInWithCredential(credential)
            .addOnCompleteListener{
                complete.value = it.isSuccessful
            }
        return complete
    }

}