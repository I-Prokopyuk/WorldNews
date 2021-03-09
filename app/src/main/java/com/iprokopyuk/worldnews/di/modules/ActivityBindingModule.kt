package com.iprokopyuk.worldnews.di.modules

import com.iprokopyuk.worldnews.views.NewsActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBindingModule {

    @ContributesAndroidInjector
    abstract fun newsActivity(): NewsActivity
}