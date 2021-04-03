package com.iprokopyuk.worldnews.di.modules

import com.iprokopyuk.worldnews.di.scopes.ActivityScoped
import com.iprokopyuk.worldnews.views.NewsActivity
import com.iprokopyuk.worldnews.views.WebActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBindingModule {

    @ContributesAndroidInjector
    abstract fun newsActivity(): NewsActivity

    @ContributesAndroidInjector
    abstract fun webActivity(): WebActivity
}