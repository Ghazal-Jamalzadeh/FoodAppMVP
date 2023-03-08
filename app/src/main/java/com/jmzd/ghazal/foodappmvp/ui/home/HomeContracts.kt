package com.jmzd.ghazal.foodappmvp.ui.home

import com.jmzd.ghazal.foodappmvp.data.model.home.ResponseCategoriesList
import com.jmzd.ghazal.foodappmvp.data.model.home.ResponseFoodsList
import com.jmzd.ghazal.foodappmvp.utils.base.BasePresenter
import com.jmzd.ghazal.foodappmvp.utils.base.BaseView

interface HomeContracts {

    interface View : BaseView {
        fun loadFoodRandom(data: ResponseFoodsList)
        fun loadCategoriesList (data: ResponseCategoriesList)
    }

    interface Presenter : BasePresenter {
        fun callFoodRandom()
        fun callCategoriesList()
    }


}