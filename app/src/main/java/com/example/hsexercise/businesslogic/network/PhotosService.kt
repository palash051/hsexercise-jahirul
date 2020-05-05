package com.example.hsexercise.businesslogic.network

import com.example.hsexercise.businesslogic.PhotoModel
import io.reactivex.Single
import com.example.hsexercise.di.component.DaggerApiComponent
import javax.inject.Inject

class PhotosService {

    @Inject
    lateinit var api: PhotosApi

    init {
        DaggerApiComponent.create().inject(this)
    }

    fun getPhotos():Single<List<PhotoModel>>{
        return api.getPhotos()
    }
}