package com.applover.di

import org.koin.core.module.Module

abstract class ModuleLoader {
    abstract val modules: List<Module>
}