package com.kharin.danii.badcats.viewmodel

import android.Manifest
import android.app.DownloadManager
import android.app.DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED
import android.net.Uri
import android.os.Environment
import androidx.databinding.ObservableArrayList
import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableList
import androidx.lifecycle.ViewModel
import com.kharin.danii.badcats.BR
import com.kharin.danii.badcats.R
import com.kharin.danii.badcats.model.Favorite
import com.kharin.danii.badcats.repository.ImagesRepository
import com.tbruyelle.rxpermissions2.RxPermissions
import io.reactivex.disposables.Disposable
import me.tatarka.bindingcollectionadapter2.ItemBinding
import javax.inject.Inject


class ImagesViewModel() : ViewModel() {

    @Inject
    lateinit var downloadManager: DownloadManager

    @Inject
    lateinit var imagesRepository: ImagesRepository

    @Inject
    lateinit var rxPermissions: RxPermissions

    private var disposable: Disposable? = null

    var loading: ObservableBoolean = ObservableBoolean(false)
    var loadingErr: ObservableBoolean = ObservableBoolean(false)

    private val downloadClickListener = object : DownloadClickListenerInterface {
        override fun download(url: String) {
            val uri = Uri.parse(url)
            if (rxPermissions.isGranted(Manifest.permission.WRITE_EXTERNAL_STORAGE))
                download(uri)
            else
                rxPermissions
                    .request(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    .subscribe({ download(uri) }, {}, {})
        }
    }

    private val toFavoriteListener = object : ToFavoriteListener {
        override fun toFavorite(item: ImageItemViewModel) {
            if (item.isFavorite.get())
                favorites.remove(imagesRepository.removeFromFavorite(item.image.id))
            else
                favorites.add(imagesRepository.addToFavorite(item.image))
            item.isFavorite.set(!item.isFavorite.get())
        }

    }

    private val removeFavoriteListener = object : RemoveFavoriteListener {
        override fun removeFavorite(favorite: Favorite) {
            favorites.remove(imagesRepository.removeFromFavorite(favorite.id))
        }

    }

    val loadNextPageListener = object : LoadNextPageListener {
        override fun loadNextPage() {
            if (!loading.get() && (disposable == null || disposable?.isDisposed!!))
                disposable = loadNext()
        }
    }


    fun init() {
        initFavorites()
        if (items.size == 0) {
            disposable?.dispose()
            disposable = loadFirst()
        }
    }

    fun initFavorites() {
        favorites.clear()
        favorites.addAll(imagesRepository.getFavorites())
    }


    var items: ObservableList<ImageItemViewModel> = ObservableArrayList()
    var favorites: ObservableList<Favorite> = ObservableArrayList()
    val itemBinding = ItemBinding.of<ImageItemViewModel>(BR.imageViewModel, R.layout.image_item)
        .bindExtra(BR.downloadListener, downloadClickListener).bindExtra(BR.toFavoriteListener, toFavoriteListener)!!
    val favoritesBinding = ItemBinding.of<Favorite>(BR.favorite, R.layout.favorite_item)
        .bindExtra(BR.downloadListener, downloadClickListener).bindExtra(
            BR.removeFavoriteListener,
            removeFavoriteListener
        )!!

    fun onRefresh() {
        disposable?.dispose()
        disposable = loadFirst()
    }

    private fun loadFirst(): Disposable? = imagesRepository.loadFirstImagesFlowable()
        ?.doOnSubscribe { loading.set(true) }
        ?.doOnTerminate { loading.set(false) }
        ?.subscribe(
            { images ->
                items.clear()
                items.addAll(images)
            },
            {
                loading.set(false)
                loadingErr.set(true)
            },
            { loading.set(false) })

    fun loadNext(): Disposable? = imagesRepository.loadNextPageImagesFlowable()
        ?.doOnSubscribe { loading.set(true) }
        ?.doOnTerminate { loading.set(false) }
        ?.subscribe(
            { images -> items.addAll(images) },
            {
                loading.set(false)
                loadingErr.set(true)
            },
            { loading.set(false) })

    fun download(uri: Uri) {
        downloadManager.enqueue(
            DownloadManager.Request(uri)
                .setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI or DownloadManager.Request.NETWORK_MOBILE)
                .setAllowedOverRoaming(false)
                .setTitle("Image download")
                .setNotificationVisibility(VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
                .setDestinationInExternalPublicDir(
                    Environment.DIRECTORY_DOWNLOADS,
                    uri.pathSegments.last()
                )
        )
    }

    override fun onCleared() {
        super.onCleared()
        disposable?.dispose()
    }


    interface DownloadClickListenerInterface {
        fun download(url: String)
    }

    interface LoadNextPageListener {
        fun loadNextPage()
    }

    interface ToFavoriteListener {
        fun toFavorite(item: ImageItemViewModel)
    }

    interface RemoveFavoriteListener {
        fun removeFavorite(favorite: Favorite)
    }

}
