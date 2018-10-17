package com.kharin.danii.badcats.repository

import com.kharin.danii.badcats.model.Favorite
import com.kharin.danii.badcats.model.Image
import com.kharin.danii.badcats.viewmodel.ImageItemViewModel
import io.reactivex.Flowable

interface ImagesRepository {

    fun loadFirstImagesFlowable():Flowable<List<ImageItemViewModel>>?
    fun loadNextPageImagesFlowable(): Flowable<List<ImageItemViewModel>>?
    fun getFavorites():List<Favorite>
    fun addToFavorite(image: Image):Favorite
    fun isFavorite(imageId: String):Boolean
    fun removeFromFavorite(id: String?):Favorite
}