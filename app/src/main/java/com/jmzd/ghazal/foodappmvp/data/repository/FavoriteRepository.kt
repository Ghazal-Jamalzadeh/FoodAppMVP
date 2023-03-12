package com.jmzd.ghazal.foodappmvp.data.repository

import com.jmzd.ghazal.foodappmvp.data.database.FoodDao
import javax.inject.Inject

class FavoriteRepository @Inject constructor( private val dao : FoodDao) {

    fun loadAllFoods() = dao.getAllFoods()

}