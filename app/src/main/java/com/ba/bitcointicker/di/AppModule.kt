package com.ba.bitcointicker.di

import com.ba.bitcointicker.data.remote.CoinApiService
import com.ba.bitcointicker.data.repository.CoinRepository
import com.ba.bitcointicker.domain.usecase.GetCoinsUseCase
import com.ba.bitcointicker.viewmodel.FavoritesViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideFirebaseFirestore(): FirebaseFirestore {
        return FirebaseFirestore.getInstance()
    }

    @Provides
    @Singleton
    fun provideFirebaseAuth(): FirebaseAuth {
        return FirebaseAuth.getInstance()
    }

    @Provides
    @Singleton
    fun provideCoinApiService(): CoinApiService {
        return Retrofit.Builder()
            .baseUrl("https://api.coingecko.com/api/v3/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(CoinApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideCoinRepository(api: CoinApiService): CoinRepository {
        return CoinRepository(api)
    }

    @Provides
    fun provideGetCoinsUseCase(repository: CoinRepository): GetCoinsUseCase {
        return GetCoinsUseCase(repository)
    }

    @Provides
    fun provideFavoritesViewModel(firebase: FirebaseFirestore): FavoritesViewModel {
        return FavoritesViewModel(firebase)
    }
}
