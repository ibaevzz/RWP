package com.ibaevzz.rwp.auth.models

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import java.lang.Exception

class EmailSignInViewModel: ViewModel() {

    private val result = MutableLiveData<Exception?>()

    fun signInEmail(email: String, password: String): LiveData<Exception?> {
        result.value = Exception("")
        if(email==""||password==""){
            result.value = Exception("Введите почту и пароль")
            return result
        }
        Firebase.auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener{
                if(it.isSuccessful){
                    result.value = null
                }else{
                    result.value = it.exception
                }
            }
        return result
    }

}