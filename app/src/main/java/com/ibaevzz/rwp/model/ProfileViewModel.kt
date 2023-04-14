package com.ibaevzz.rwp.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ibaevzz.rwp.data.Profile
import javax.inject.Inject
import javax.inject.Provider

class ProfileViewModel(val liveDataProfile: Provider<LiveData<Profile>>): ViewModel() {

    class Factory @Inject constructor(private val profile: Provider<LiveData<Profile>>)
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