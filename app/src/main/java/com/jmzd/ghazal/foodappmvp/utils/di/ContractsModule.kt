package com.jmzd.ghazal.foodappmvp.utils.di

import androidx.fragment.app.Fragment
import com.jmzd.ghazal.foodappmvp.ui.detail.DetailContracts
import com.jmzd.ghazal.foodappmvp.ui.favorite.FavoriteContrracts
import com.jmzd.ghazal.foodappmvp.ui.home.HomeContracts
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent

@Module
@InstallIn(FragmentComponent::class) // چون روی فرگمنت داریم کار میکنیم
object ContractsModule {

    /* این وابستگی های ویو را باید تامین کنیم چون پرزنتر از اینا استفاده میکنه و نیاز به تامین شدن دارن */
    @Provides
    fun homeView(fragment: Fragment): HomeContracts.View {
        return fragment as HomeContracts.View
    }

    @Provides
    fun detailView(fragment: Fragment): DetailContracts.View {
        return fragment as DetailContracts.View
    }

    @Provides
    fun favoriteView (fragment: Fragment): FavoriteContrracts.View {
        return fragment as FavoriteContrracts.View
    }

}