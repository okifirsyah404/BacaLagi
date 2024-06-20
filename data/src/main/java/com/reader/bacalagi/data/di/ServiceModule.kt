package com.reader.bacalagi.data.di

import com.reader.bacalagi.data.network.service.AreaService
import com.reader.bacalagi.data.network.service.AuthService
import com.reader.bacalagi.data.network.service.BookService
import com.reader.bacalagi.data.network.service.FaqService
import com.reader.bacalagi.data.network.service.PolicyService
import com.reader.bacalagi.data.network.service.ProductService
import com.reader.bacalagi.data.network.service.ProfileService
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit

val serviceModule = module {
    single {
        provideAuthService(get(named(DataDiKey.DEFAULT_RETROFIT)))
    }

    single {
        provideProfileService(get(named(DataDiKey.DEFAULT_RETROFIT)))
    }

    single {
        provideAreaService(get(named(DataDiKey.AREA_RETROFIT)))
    }
    single {
        provideBookService(get(named(DataDiKey.DEFAULT_RETROFIT)))
    }
    single {
        provideFaqService(get(named(DataDiKey.DEFAULT_RETROFIT)))
    }
    single {
        providePrivacyPolicyService(get(named(DataDiKey.DEFAULT_RETROFIT)))
    }
    single {
        provideProductService(get(named(DataDiKey.DEFAULT_RETROFIT)))
    }
}

private fun provideAuthService(retrofit: Retrofit): AuthService {
    return retrofit.create(AuthService::class.java)
}

private fun provideProfileService(retrofit: Retrofit): ProfileService {
    return retrofit.create(ProfileService::class.java)
}

private fun provideAreaService(retrofit: Retrofit): AreaService {
    return retrofit.create(AreaService::class.java)
}
private fun provideBookService(retrofit: Retrofit): BookService {
    return retrofit.create(BookService::class.java)
}
private fun provideFaqService(retrofit: Retrofit): FaqService {
    return retrofit.create(FaqService::class.java)
}
private fun providePrivacyPolicyService(retrofit: Retrofit): PolicyService {
    return retrofit.create(PolicyService::class.java)
}
private fun provideProductService(retrofit: Retrofit): ProductService {
    return retrofit.create(ProductService::class.java)
}

