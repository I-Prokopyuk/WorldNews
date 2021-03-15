package com.iprokopyuk.worldnews.di.components

import com.iprokopyuk.worldnews.app.Application
import com.iprokopyuk.worldnews.di.modules.AppModule
import com.iprokopyuk.worldnews.di.modules.ViewModelModule
import com.iprokopyuk.worldnews.di.scopes.AppScoped
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector


@AppScoped
@Component(modules = [AndroidInjectionModule::class, ViewModelModule::class, AppModule::class])
public interface AppComponent : AndroidInjector<Application> {


    @Component.Builder
    interface Builder {

        fun build(): AppComponent

        @BindsInstance
        fun application(app: Application): Builder
    }

    //override fun inject(app: Application)
}