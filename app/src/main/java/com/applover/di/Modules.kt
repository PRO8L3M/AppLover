package com.applover.di

import com.applover.repository.LoginRepository
import com.applover.ui.login.LoginViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module

object AppModules : ModuleLoader() {
    override val modules: List<Module> =
        listOf(
            viewModelModule,
            repositoryModule
        )
}

private val viewModelModule = module {
    viewModel { LoginViewModel(get()) }
}

private val repositoryModule = module {
    single { LoginRepository() }
}