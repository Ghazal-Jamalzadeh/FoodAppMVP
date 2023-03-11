package com.jmzd.ghazal.foodappmvp.data.repository

import com.jmzd.ghazal.foodappmvp.data.database.FoodDao
import com.jmzd.ghazal.foodappmvp.data.database.FoodEntity
import com.jmzd.ghazal.foodappmvp.data.server.ApiServices
import javax.inject.Inject

class DetailRepository @Inject constructor(private val api: ApiServices , private val dao : FoodDao) {

    fun foodDetail(foodId : Int ) = api.foodDetail(foodId = foodId)
    fun saveFood(entity: FoodEntity) = dao.saveFood(entity)
    fun deleteFood(entity: FoodEntity) = dao.deleteFood(entity)
    fun existsFood(id: Int) = dao.existsFood(id)
}