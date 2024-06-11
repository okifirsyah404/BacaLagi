package com.reader.bacalagi.domain.di

import org.koin.dsl.module

val domainModule = module {
    includes(repositoryModule)
}