package com.example.hsexercise.businesslogic.network

import com.example.hsexercise.businesslogic.PhotoModel
import io.reactivex.Single
import retrofit2.http.GET

interface PhotosApi {

    @GET("v2/list")
    fun getPhotos():Single<List<PhotoModel>>
}