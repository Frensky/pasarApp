package com.adut.pasar.app.di

import com.adut.pasar.app.MyApplication
import com.adut.pasar.app.base.BaseActivity
import com.adut.pasar.app.base.BaseFragment
import com.adut.pasar.app.di.viewModel.ViewModelModule
import com.adut.pasar.data.di.DatabaseModule
import com.adut.pasar.data.di.RepoModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AppModule::class,
        ViewModelModule::class,
        DatabaseModule::class,
        RepoModule::class
        ]
)

interface AppComponent {
    fun inject(application: MyApplication)
    fun inject(activity: BaseActivity)
    fun inject(fragment: BaseFragment)
}