package com.example.hsexercise.view

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.item_photo.view.*
import com.example.hsexercise.R
import com.example.hsexercise.businesslogic.PhotoModel
import com.example.hsexercise.util.getProgressDrawable
import com.example.hsexercise.util.loadImage

class PhotoListAdapter(var photoModels:ArrayList<PhotoModel>):RecyclerView.Adapter<PhotoListAdapter.PhotoViewHolder>() {

    fun updateCountries(newPhotoModels:List<PhotoModel>){
        photoModels.clear()
        photoModels.addAll(newPhotoModels)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int) = PhotoViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_photo,parent,false))

    override fun getItemCount() = photoModels.size

    override fun onBindViewHolder(holder: PhotoViewHolder, position: Int) {
        holder.bind(photoModels[position])
    }


    class PhotoViewHolder(view: View):RecyclerView.ViewHolder(view){

        private val photoAuthor = view.title
        private val imageView = view.imageView
        private val photoDimension = view.dimension
        private val progressDrawable  = getProgressDrawable(view.context)

        fun bind(photoModel:PhotoModel){
            photoAuthor.text = photoModel.author
            photoDimension.text = "${photoModel.height} X ${photoModel.width}"
            imageView.loadImage(photoModel.download_url,progressDrawable)

        }
    }

}