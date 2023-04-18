package com.ibaevzz.rwp.auth.models

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.ibaevzz.rwp.data.Profile

class PhoneRegViewModel: ViewModel() {

    private val complete = MutableLiveData<Boolean>()

    fun signInWithPhone(credential: PhoneAuthCredential): LiveData<Boolean>{
        Firebase.auth.signInWithCredential(credential)
            .addOnCompleteListener{
                if(it.isSuccessful) {
                    val user = Firebase.auth.currentUser
                    val profile = Profile(
                        uid = user?.uid ?: "",
                        name = "Безымяный",
                        phone = user?.phoneNumber ?: ""
                    )
                    Firebase.database.reference.child("profiles").child(profile.uid).get().addOnCompleteListener {task->
                        if (task.isSuccessful) {
                            if(task.result.getValue(Profile::class.java)==null){
                                Firebase.database.reference.child("profiles").child(profile.uid).setValue(profile)
                            }
                        }
                    }
                    complete.value = true
                }else{
                    complete.value = false
                }
            }
        return complete
    }

}