package com.jmzd.ghazal.foodappmvp.data.server

import com.jmzd.ghazal.foodappmvp.data.model.home.ResponseCategoriesList
import com.jmzd.ghazal.foodappmvp.data.model.home.ResponseFoodsList
import io.reactivex.rxjava3.core.Single
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query


interface ApiServices {

    @GET("random.php")
    fun foodRandom(): Single<Response<ResponseFoodsList>>

    @GET("categories.php")
    fun categoriesList() : Single<Response<ResponseCategoriesList>>

    //search.php?f=a
    @GET("search.php")
    fun foodsList(@Query("f") letter : String ) : Single<Response<ResponseFoodsList>>

    //search.php?s=Arrabiata
    @GET("search.php")
    fun searchFood(@Query("s") search : String ) : Single<Response<ResponseFoodsList>>

    //filter.php?c=Seafood
    @GET("filter.php")
    fun foodsByCategory(@Query("c") category : String ) : Single<Response<ResponseFoodsList>>

    //lookup.php?i=52772
    @GET("lookup.php")
    fun foodDetail(@Query("i") foodId : Int ) : Single<Response<ResponseFoodsList>>



}