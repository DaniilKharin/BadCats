package com.kharin.danii.badcats.repository

import com.kharin.danii.badcats.model.Favorite
import com.kharin.danii.badcats.model.Image

fun Image.toFavorite():Favorite{
    return Favorite(this.id,this.url)
}