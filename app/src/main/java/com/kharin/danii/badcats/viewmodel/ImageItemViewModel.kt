package com.kharin.danii.badcats.viewmodel

import androidx.databinding.ObservableBoolean
import com.kharin.danii.badcats.model.Favorite
import com.kharin.danii.badcats.model.Image

data class ImageItemViewModel(val image: Image, var isFavorite: ObservableBoolean = ObservableBoolean(false))