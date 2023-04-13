package com.ibaevzz.rwp.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ibaevzz.rwp.data.Profile
import javax.inject.Inject

class ProfileViewModel(private val mutableLiveDataProfile: MutableLiveData<Profile>): ViewModel() {

    val liveDataProfile: LiveData<Profile> = mutableLiveDataProfile
    var profile: Profile = Profile()

    class Factory @Inject constructor(private val profile: MutableLiveData<Profile>)
        :ViewModelProvider.Factory{

        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if(modelClass.isAssignableFrom(ProfileViewModel::class.java)){
                @Suppress("UNCHECKED_CAST")
                return ProfileViewModel(profile) as T
            }
            throw IllegalArgumentException("UNKNOWN VIEW MODEL CLASS")
        }

    }

}