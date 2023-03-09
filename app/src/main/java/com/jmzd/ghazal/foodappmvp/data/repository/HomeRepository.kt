package com.jmzd.ghazal.foodappmvp.data.repository

import com.jmzd.ghazal.foodappmvp.data.server.ApiServices
import javax.inject.Inject

class HomeRepository @Inject constructor(private val api: ApiServices) {
    fun loadFoodRandom() = api.foodRandom()
    fun loadCategoriesList() = api.categoriesList()
    fun loadFoodsList(letter : String) = api.foodsList(letter = letter)
    fun searchFood(search : String) = api.searchFood(search = search )
    fun foodsByCategory(category : String) = api.foodsByCategory(category = category )
}