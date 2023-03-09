package com.jmzd.ghazal.foodappmvp.ui.detail

import com.jmzd.ghazal.foodappmvp.data.model.home.ResponseFoodsList
import com.jmzd.ghazal.foodappmvp.utils.base.BasePresenter
import com.jmzd.ghazal.foodappmvp.utils.base.BaseView

interface DetailContracts {
    interface View : BaseView {
        fun loadDetail(data: ResponseFoodsList)
    }

    interface Presenter : BasePresenter {
        fun callDetailApi(id: Int)
    }
}