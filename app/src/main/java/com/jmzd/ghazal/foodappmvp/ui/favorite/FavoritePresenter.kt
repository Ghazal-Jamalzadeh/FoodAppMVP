package com.jmzd.ghazal.foodappmvp.ui.favorite

import android.util.Log
import com.jmzd.ghazal.foodappmvp.data.repository.FavoriteRepository
import com.jmzd.ghazal.foodappmvp.utils.TAG
import com.jmzd.ghazal.foodappmvp.utils.base.BasePresenter
import com.jmzd.ghazal.foodappmvp.utils.base.BasePresenterImpl
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

class FavoritePresenter @Inject constructor(
    private val repository: FavoriteRepository ,
    private val view : FavoriteContrracts.View
) : BasePresenterImpl() , FavoriteContrracts.Presenter{
    override fun loadAllFoods() {
        disposable = repository.loadAllFoods()
            .observeOn(Schedulers.io())
            .subscribeOn(AndroidSchedulers.mainThread())
            .subscribe({
                if(it.isEmpty()){
                    Log.d(TAG, "loadAllFoods: empty")
                    view.foodsEmpty()
                }else{
                    Log.d(TAG, "loadAllFoods: ${it.size}")
                   view.showAllFoods(it)
                }
            }, {
                it.printStackTrace()
            } )
    }
}