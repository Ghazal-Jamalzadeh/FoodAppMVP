package com.jmzd.ghazal.foodappmvp.data.repository

import com.jmzd.ghazal.foodappmvp.data.server.ApiServices
import javax.inject.Inject

class DetailRepository @Inject constructor(private val api: ApiServices) {

    fun foodDetail(foodId : Int ) = api.foodDetail(foodId = foodId)
}