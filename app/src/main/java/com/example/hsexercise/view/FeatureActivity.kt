package com.example.hsexercise.view

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import kotlinx.android.synthetic.main.activity_feature.*
import com.example.hsexercise.R
import com.example.hsexercise.viewmodel.FeatureViewModel

class FeatureActivity : AppCompatActivity(),SwipeRefreshLayout.OnRefreshListener {


    lateinit var viewModel:FeatureViewModel
    private var countriesAdapter:PhotoListAdapter = PhotoListAdapter(arrayListOf())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_feature)
        instantiateTheViewModel()
    }


    private fun instantiateTheViewModel(){
        viewModel  = ViewModelProviders.of(this).get(FeatureViewModel::class.java)
        viewModel.refresh(this)

        countriesList.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = countriesAdapter
        }

        swipeRefreshLayout.setOnRefreshListener(this)

        observeViewModel()
    }

    fun observeViewModel(){
        viewModel.photos.observe(this, Observer { countries->
            countries?.let{list->
                countriesList.visibility = View.VISIBLE
                countriesAdapter.updateCountries(list)
            }
        })

        viewModel.photosLoadError.observe(this, Observer { isError->
            isError?.let { list_error.visibility = if(it) View.VISIBLE else View.GONE }
        })

        viewModel.loading.observe(this, Observer { isLoading->
            isLoading?.let {
                loading_view.visibility = if(it) View.VISIBLE else View.GONE
                if(it){
                    list_error.visibility = View.GONE
                    countriesList.visibility = View.GONE
                }
            }
        })
    }

    override fun onRefresh() {
        swipeRefreshLayout.isRefreshing = false
        viewModel.refresh(this)
    }
}
