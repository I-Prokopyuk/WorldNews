package com.iprokopyuk.worldnews.di

import com.iprokopyuk.worldnews.app.Application
import com.iprokopyuk.worldnews.di.scopes.AppScoped
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector


@AppScoped
@Component(modules = [AndroidInjectionModule::class])
public interface AppComponent : AndroidInjector<Application> {

    @Component.Builder
    interface Builder {

        fun build(): AppComponent

        @BindsInstance
        fun application(app: Application): Builder
    }

    override fun inject(app: Application)
}