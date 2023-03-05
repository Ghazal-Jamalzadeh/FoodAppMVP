package com.jmzd.ghazal.foodappmvp.utils.di

import androidx.fragment.app.Fragment
import com.jmzd.ghazal.foodappmvp.ui.home.HomeContracts
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent

@Module
@InstallIn(FragmentComponent::class) // چون روی فرگمنت داریم کار میکنیم
object ContractsModule {

    @Provides
    fun homeView(fragment: Fragment): HomeContracts.View {
        return fragment as HomeContracts.View
    }

}