package com.kharin.danii.badcats.di


import com.kharin.danii.badcats.viewmodel.ImagesViewModel
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [
    MainModule::class,
    NetworkModule::class,
    RepositoryModule::class
])
interface MainActivityComponent {
    fun inject(viewModel: ImagesViewModel)

}