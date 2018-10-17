package com.kharin.danii.badcats

import com.kharin.danii.badcats.model.Image
import io.reactivex.Flowable
import retrofit2.http.GET
import retrofit2.http.Query


interface CatsApi {

    @GET("images/")
    fun images(
               @Query("limit") limit: Int,
               @Query("page") page: Int): Flowable<List<Image>>
}