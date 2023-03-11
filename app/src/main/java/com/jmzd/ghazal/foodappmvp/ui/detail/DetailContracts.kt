package com.jmzd.ghazal.foodappmvp.ui.detail

import com.jmzd.ghazal.foodappmvp.data.database.FoodEntity
import com.jmzd.ghazal.foodappmvp.data.model.home.ResponseFoodsList
import com.jmzd.ghazal.foodappmvp.utils.base.BasePresenter
import com.jmzd.ghazal.foodappmvp.utils.base.BaseView
import org.w3c.dom.Entity

interface DetailContracts {
    interface View : BaseView {
        fun loadDetail(data: ResponseFoodsList)
        fun updateFavorite(isAdded : Boolean)
    }

    interface Presenter : BasePresenter {
        fun callDetailApi(id: Int)
        fun saveFood(entity : FoodEntity)
        fun deleteFood(entity : FoodEntity)
        fun checkFavorite (id : Int )
    }
}