package com.iprokopyuk.worldnews.di.modules

import android.content.Context
import androidx.room.Room
import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.iprokopyuk.worldnews.app.Application
import com.iprokopyuk.worldnews.data.local.NewsDatabase
import com.iprokopyuk.worldnews.data.remote.api.ApiServices
import com.iprokopyuk.worldnews.di.scopes.AppScoped
import com.iprokopyuk.worldnews.utils.API_BASE_URL
import com.iprokopyuk.worldnews.utils.DB_NAME
import com.squareup.picasso.Picasso
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

@Module(includes = [ActivityBindingModule::class, ViewModelModule::class])
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
    fun provideGson(): Gson {
        val gsonBuilder = GsonBuilder()
        gsonBuilder.setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
        return gsonBuilder.create()
    }

    @AppScoped
    @Provides
    fun provideRetrofit(gson: Gson): Retrofit {
        return Retrofit.Builder()
            .baseUrl(API_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
    }

    @AppScoped
    @Provides
    fun provideApiServices(retrofit: Retrofit): ApiServices =
        retrofit.create(ApiServices::class.java)

    @AppScoped
    @Provides
    fun providePicasso(app: Application) = Picasso.get()

//    @AppScoped
//    @Provides
//    fun providePagedListConfig(): PagedList.Config {
//        return PagedList.Config.Builder()
//            .setEnablePlaceholders(false)
//            .setPageSize(15)
//            .build();
//    }

}