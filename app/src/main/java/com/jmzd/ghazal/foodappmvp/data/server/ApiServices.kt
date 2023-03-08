package com.jmzd.ghazal.foodappmvp.data.server

import com.jmzd.ghazal.foodappmvp.data.model.home.ResponseCategoriesList
import com.jmzd.ghazal.foodappmvp.data.model.home.ResponseFoodsList
import io.reactivex.rxjava3.core.Single
import retrofit2.Response
import retrofit2.http.GET


interface ApiServices {

    @GET("random.php")
    fun foodRandom(): Single<Response<ResponseFoodsList>>

    @GET("categories.php")
    fun categoriesList() : Single<Response<ResponseCategoriesList>>
}