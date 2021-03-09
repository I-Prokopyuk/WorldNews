package com.iprokopyuk.worldnews.di.modules

import android.content.Context
import androidx.room.Room
import com.iprokopyuk.worldnews.app.Application
import com.iprokopyuk.worldnews.data.local.NewsDatabase
import com.iprokopyuk.worldnews.data.remote.api.ApiServices
import com.iprokopyuk.worldnews.di.scopes.AppScoped
import com.iprokopyuk.worldnews.utils.API_BASE_URL
import com.iprokopyuk.worldnews.utils.DB_NAME
import dagger.Module
import dagger.Provides
import io.reactivex.disposables.CompositeDisposable
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

@Module(includes = [ActivityBindingModule::class])
class AppModule {

    @AppScoped
    @Provides
    fun provideContext(app: Application): Context = app.applicationContext

    @AppScoped
    @Provides
    fun provideNewsDb(context: Context): NewsDatabase = Room.databaseBuilder(
        context, NewsDatabase::class.java,
        DB_NAME
    ).fallbackToDestructiveMigration().build()

    @AppScoped
    @Provides
    fun provideNewsDao(newsDatabase: NewsDatabase) = newsDatabase.newsDao()

    @AppScoped
    @Provides
    fun provideRetrofit(): ApiServices {
        return Retrofit.Builder()
            .baseUrl(API_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
            .create(ApiServices::class.java)
    }

    @AppScoped
    @Provides
    fun provideCompositeDisposable(): CompositeDisposable = CompositeDisposable()
}