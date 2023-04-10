package com.ibaevzz.rwp.auth.models

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import java.lang.Exception

class EmailRegViewModel: ViewModel() {

    private val resultReg = MutableLiveData<Exception?>()

    fun regWithEmail(email: String, password: String): LiveData<Exception?>{
        resultReg.value = Exception("")
        if(email==""||password==""){
            resultReg.value = Exception("Введите почту и пароль")
            return resultReg
        }
        Firebase.auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener{
                if(it.isSuccessful){
                    resultReg.value = null
                }else{
                    resultReg.value = it.exception
                }
            }
        return resultReg
    }
}