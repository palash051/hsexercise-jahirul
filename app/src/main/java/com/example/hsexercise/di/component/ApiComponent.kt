package com.example.hsexercise.di.component

import dagger.Component
import com.example.hsexercise.di.modules.ApiModule
import com.example.hsexercise.businesslogic.network.PhotosService
import com.example.hsexercise.viewmodel.FeatureViewModel

@Component(modules = [ApiModule::class])
interface ApiComponent {

    fun inject(service: PhotosService)

    fun inject(viewModel:FeatureViewModel)

}