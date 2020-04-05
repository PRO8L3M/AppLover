package com.applover.di

import android.content.Context
import android.net.ConnectivityManager
import com.applover.BuildConfig
import com.applover.common.APP_LOVER_OK_HTTP
import com.applover.common.APP_LOVER_RETROFIT
import com.applover.network.AppLoverApi
import com.applover.network.ConnectionManagerImpl
import com.applover.repository.LoginRepository
import com.applover.ui.LoginViewModel
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object AppModules : ModuleLoader() {
    override val modules: List<Module> =
        listOf(
            viewModelModule,
            repositoryModule,
            networkModule
        )
}

private val viewModelModule = module {
    viewModel { LoginViewModel(get()) }
}

private val repositoryModule = module {
    single { LoginRepository(get(named(APP_LOVER_RETROFIT))) }
}

private val networkModule = module {
    single(named(APP_LOVER_RETROFIT)) {
        Retrofit.Builder()
            .addConverterFactory(MoshiConverterFactory.create())
            .baseUrl(BuildConfig.BASE_URL)
            .client(get(named(APP_LOVER_OK_HTTP)))
            .build()
            .create(AppLoverApi::class.java)
    }

    single(named(APP_LOVER_OK_HTTP)) {
        OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor())
            .build()
    }

    single { androidApplication().getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager }

    single { ConnectionManagerImpl(get()) }
}