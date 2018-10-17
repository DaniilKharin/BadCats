package com.kharin.danii.badcats.repository

import androidx.databinding.ObservableBoolean
import com.kharin.danii.badcats.CatsApi
import com.kharin.danii.badcats.model.DaoSession
import com.kharin.danii.badcats.model.Favorite
import com.kharin.danii.badcats.model.Image
import com.kharin.danii.badcats.model.FavoriteDao
import com.kharin.danii.badcats.viewmodel.ImageItemViewModel
import io.reactivex.Flowable
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class ImagesRepositoryImpl(val daoSession: DaoSession, private val catsApi: CatsApi) : ImagesRepository {

    override fun getFavorites(): List<Favorite> = daoSession.favoriteDao.loadAll()


    override fun addToFavorite(image: Image): Favorite {
        val favorite = image.toFavorite()
        daoSession.favoriteDao.insertInTx(favorite)
        return favorite
    }

    override fun isFavorite(imageId: String): Boolean {
        return daoSession.favoriteDao.queryBuilder().where(FavoriteDao.Properties.Id.eq(imageId)).count() > 0
    }

    override fun removeFromFavorite(id: String?): Favorite {
        val res = daoSession.favoriteDao.queryBuilder().where(FavoriteDao.Properties.Id.eq(id)).unique()
        daoSession.favoriteDao.deleteInTx(res)
        return res
    }

    var currentPage = 0
    var allPagesLoaded = false

    override fun loadFirstImagesFlowable(): Flowable<List<ImageItemViewModel>>? {
        allPagesLoaded = false
        currentPage = 0
        return loadImages()
    }

    override fun loadNextPageImagesFlowable(): Flowable<List<ImageItemViewModel>>? {
        if (allPagesLoaded)
            return null
        currentPage++
        return loadImages()
    }

    private fun loadImages() = catsApi.images(limit, currentPage)
        .observeOn(AndroidSchedulers.mainThread())
        .subscribeOn(Schedulers.io())
        .flatMap { list -> Flowable.fromIterable(list) }
        .map { image -> ImageItemViewModel(image, ObservableBoolean(isFavorite(image.id))) }
        .toList()
        .toFlowable()
        .doOnNext { images -> if (images.size != limit) allPagesLoaded = true }

    companion object {
        const val limit = 10 //values 1-10
    }


}