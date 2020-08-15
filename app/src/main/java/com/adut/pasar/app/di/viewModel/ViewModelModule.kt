package com.adut.pasar.app.di.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.adut.pasar.app.feature.favorite.FavoriteViewModel
import com.adut.pasar.app.feature.product.ProductViewModel
import com.adut.pasar.app.feature.syncron.SyncronViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {

    @Binds
    internal abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(ProductViewModel::class)
    abstract fun bindDashboardViewModel(dashboardViewModel: ProductViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(FavoriteViewModel::class)
    abstract fun bindFavoriteViewModel(favoriteViewModel: FavoriteViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(SyncronViewModel::class)
    abstract fun bindSyncronViewModel(syncronViewModel: SyncronViewModel): ViewModel

}