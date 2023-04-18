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
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.ibaevzz.rwp.data.Profile

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
                if(it.isSuccessful) {
                    val user = Firebase.auth.currentUser
                    val profile = Profile(
                        uid = user?.uid ?: "",
                        email = googleSignInAccount.email ?: "",
                        photo = googleSignInAccount.photoUrl.toString(),
                        name = googleSignInAccount.givenName ?: "Безымяный",
                        surname = googleSignInAccount.familyName ?: ""
                    )
                    Firebase.database.reference.child("profiles").child(profile.uid).get().addOnCompleteListener {task->
                        if (task.isSuccessful) {
                            if(task.result.getValue(Profile::class.java)==null){
                                Firebase.database.reference.child("profiles").child(profile.uid).setValue(profile)
                            }
                        }
                    }
                    resultReg.value = true
                }else{
                    resultReg.value = false
                }
            }
        }else{
            resultReg.value = false
        }
        return resultReg
    }
}