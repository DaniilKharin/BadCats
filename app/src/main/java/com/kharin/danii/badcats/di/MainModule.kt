package com.kharin.danii.badcats.di

import android.app.DownloadManager
import android.content.Context.DOWNLOAD_SERVICE
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.kharin.danii.badcats.BuildConfig
import com.kharin.danii.badcats.CatsApi
import com.kharin.danii.badcats.model.DaoMaster
import com.kharin.danii.badcats.model.DaoSession
import com.kharin.danii.badcats.view.MainActivity
import com.tbruyelle.rxpermissions2.RxPermissions
import dagger.Module
import dagger.Provides
import kotlinx.android.synthetic.main.app_bar_main.*
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


@Module
class MainModule(private val mainActivity: MainActivity) {


    @Provides
    fun provideNavigation():NavController = mainActivity.nav_host_fragment.findNavController()



    @Provides
    fun provideDAOSession(): DaoSession {
        val helper = DaoMaster.DevOpenHelper(mainActivity.applicationContext, "images-db")
        val db = helper.writableDb
        DaoMaster.createAllTables(db,true)
        return DaoMaster(db).newSession()
    }

    @Provides
    fun provideDownloadManager(): DownloadManager = mainActivity.getSystemService(DOWNLOAD_SERVICE) as DownloadManager

    @Provides
    fun proviedRxPermissions():RxPermissions = RxPermissions(mainActivity)

}