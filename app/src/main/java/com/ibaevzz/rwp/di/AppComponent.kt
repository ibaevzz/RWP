package com.ibaevzz.rwp.di

import android.content.Context
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.ibaevzz.rwp.WEB_CLIENT
import com.ibaevzz.rwp.auth.RegistrationFragment
import dagger.BindsInstance
import dagger.Component
import dagger.Module
import dagger.Provides

@Component(modules = [AppModule::class])
interface AppComponent{

    fun inject(fragment: RegistrationFragment)

    @Component.Builder
    interface Builder{
        fun build(): AppComponent

        @BindsInstance
        fun context(context: Context): Builder
    }
}

@Module(includes = [AuthModule::class])
interface AppModule{
}

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