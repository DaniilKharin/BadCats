package com.kharin.danii.badcats.di

import com.kharin.danii.badcats.CatsApi
import com.kharin.danii.badcats.model.DaoSession
import com.kharin.danii.badcats.repository.ImagesRepository
import com.kharin.danii.badcats.repository.ImagesRepositoryImpl
import dagger.Module
import dagger.Provides

@Module
class RepositoryModule {

    @Provides
    fun provideImagesRepository(daoSession: DaoSession, catsApi: CatsApi):ImagesRepository = ImagesRepositoryImpl(daoSession, catsApi)
}