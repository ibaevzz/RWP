package com.ibaevzz.rwp.auth.models

import android.app.Activity
import androidx.activity.result.ActivityResult
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class RegistrationViewModel: ViewModel() {

    private val resultReg = MutableLiveData<Boolean>()

    fun activityResult(data: ActivityResult): LiveData<Boolean>{
        if (data.resultCode== Activity.RESULT_OK) {
            val signInAccountTask: Task<GoogleSignInAccount> =
                GoogleSignIn.getSignedInAccountFromIntent(data.data)
            val googleSignInAccount = signInAccountTask.getResult(ApiException::class.java)
            val authCredential: AuthCredential = GoogleAuthProvider.getCredential(
                googleSignInAccount.idToken, null
            )
            val credential = Firebase.auth.signInWithCredential(authCredential)
            credential.addOnCompleteListener {
                resultReg.value = it.isSuccessful
            }
        }else{
            resultReg.value = false
        }
        return resultReg
    }
}