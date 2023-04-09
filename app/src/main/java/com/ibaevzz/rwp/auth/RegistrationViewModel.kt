package com.ibaevzz.rwp.auth

import androidx.activity.result.ActivityResult
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
    fun activityResult(data: ActivityResult): Boolean {
        val signInAccountTask: Task<GoogleSignInAccount> =
            GoogleSignIn.getSignedInAccountFromIntent(data.data)

        if (signInAccountTask.isSuccessful) {
            val googleSignInAccount = signInAccountTask.getResult(ApiException::class.java)
            if (googleSignInAccount != null) {
                val authCredential: AuthCredential = GoogleAuthProvider.getCredential(
                    googleSignInAccount.idToken, null
                )
                return Firebase.auth.signInWithCredential(authCredential).isSuccessful
            }
        }
        return false
    }
}