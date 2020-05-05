package com.example.hsexercise.viewmodel

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.content.Context
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import com.example.hsexercise.di.component.DaggerApiComponent
import com.example.hsexercise.businesslogic.network.PhotosService
import com.example.hsexercise.businesslogic.PhotoModel
import com.example.hsexercise.businesslogic.database.AppDatabase
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import javax.inject.Inject

class FeatureViewModel : ViewModel() {

    @Inject
    lateinit var photosService: PhotosService
    private val disposable = CompositeDisposable()
    val photos = MutableLiveData<List<PhotoModel>>()
    val photosLoadError = MutableLiveData<Boolean>()
    val loading = MutableLiveData<Boolean>()

    init {
        DaggerApiComponent.create().inject(this)
    }

    fun refresh(context: Context) {
        fetchPhotos(context)
    }

    private fun fetchPhotos(context: Context) {
        loading.value = true
        doAsync {
            val database = AppDatabase.getInstance(context)
            val dbPhotos = database.photosDao().getAll()
            uiThread {
                if(dbPhotos.count()>0){
                    setDataOnSuccess(dbPhotos)
                }else {
                    disposable.add(
                        photosService.getPhotos().subscribeOn(Schedulers.newThread())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribeWith(object : DisposableSingleObserver<List<PhotoModel>>() {
                                override fun onSuccess(value: List<PhotoModel>?) {
                                    setDataOnSuccess(value)
                                    doAsync {
                                        database.photosDao().insertAll(value)
                                    }
                                }

                                override fun onError(e: Throwable?) {
                                    photosLoadError.value = true
                                    loading.value = false
                                }

                            })
                    )
                }
            }
        }
    }

    private fun setDataOnSuccess(value: List<PhotoModel>?) {
        photos.value = value
        photosLoadError.value = false
        loading.value = false
    }

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }

}
