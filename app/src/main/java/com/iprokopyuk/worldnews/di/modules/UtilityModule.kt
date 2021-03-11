package com.iprokopyuk.worldnews.di.modules

import com.iprokopyuk.worldnews.app.Application
import com.iprokopyuk.worldnews.di.scopes.AppScoped
import com.squareup.picasso.Picasso
import dagger.Module
import dagger.Provides

@Module
class UtilityModule {

    @AppScoped
    @Provides
    fun providePicasso(application: Application) = Picasso.get()
}