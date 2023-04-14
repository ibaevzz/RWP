package com.ibaevzz.rwp.di

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.ibaevzz.rwp.WEB_CLIENT
import com.ibaevzz.rwp.auth.ui.RegistrationFragment
import com.ibaevzz.rwp.auth.ui.SignInFragment
import com.ibaevzz.rwp.data.Profile
import com.ibaevzz.rwp.ui.ProfileFragment
import dagger.BindsInstance
import dagger.Component
import dagger.Module
import dagger.Provides

@Component(modules = [AppModule::class])
interface AppComponent{

    fun inject(fragment: RegistrationFragment)
    fun inject(fragment: SignInFragment)
    fun inject(profileFragment: ProfileFragment)

    @Component.Builder
    interface Builder{
        fun build(): AppComponent

        @BindsInstance
        fun context(context: Context): Builder
    }
}

@Module(includes = [AuthModule::class, MainModule::class])
interface AppModule

@Module
object AuthModule{

    @Provides
    fun getGoogleSignInOptions(): GoogleSignInOptions {
        return GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(WEB_CLIENT)
            .requestEmail()
            .build()
    }

    @Provides
    fun getGoogleSignInClient(context: Context, googleSignInOptions: GoogleSignInOptions): GoogleSignInClient{
        return GoogleSignIn.getClient(context, googleSignInOptions)
    }

}

@Module
object MainModule{

    @Provides
    fun getProfile(): LiveData<Profile> {
        val reference = Firebase.database.reference
        val user = Firebase.auth.currentUser
        val liveData = MutableLiveData<Profile>()
        reference.child("profiles").child(user?.uid?:"").get().addOnCompleteListener{
            if(it.isSuccessful) {
                liveData.value = it.result.getValue(Profile::class.java)
            }else{
                liveData.value = Profile()
            }
        }
        return liveData
    }
}