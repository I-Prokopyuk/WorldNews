package com.iprokopyuk.worldnews.di.modules

import com.iprokopyuk.worldnews.di.scopes.ActivityScoped
import com.iprokopyuk.worldnews.views.NewsActivity
import com.iprokopyuk.worldnews.views.WebActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBindingModule {

    @ActivityScoped
    @ContributesAndroidInjector(modules = [ActivityModule::class])
    abstract fun newsActivity(): NewsActivity

    @ActivityScoped
    @ContributesAndroidInjector(modules = [ActivityModule::class])
    abstract fun webActivity(): WebActivity
}