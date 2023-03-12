package com.jmzd.ghazal.foodappmvp.ui.favorite

import com.jmzd.ghazal.foodappmvp.data.database.FoodEntity
import com.jmzd.ghazal.foodappmvp.utils.base.BasePresenter
import com.jmzd.ghazal.foodappmvp.utils.base.BaseView

interface FavoriteContrracts {

    /* بر خلاف بقیه کانترکت ها اینجا برای ویو از بیس ویو ارث بری نکردیم چون بیس ویو بیشتر مواردش عملیات سرور را هندل میکرد که اینجا با دیتایس کار میکنیم نیازی بهشون نداشتیم */
    interface View {
        fun showAllFoods (list : MutableList<FoodEntity>)
        fun foodsEmpty ()

    }

    interface Presenter : BasePresenter {
        fun loadAllFoods()
    }
}