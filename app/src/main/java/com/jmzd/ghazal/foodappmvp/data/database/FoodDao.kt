package com.jmzd.ghazal.foodappmvp.data.database

import androidx.room.*
import com.jmzd.ghazal.foodappmvp.utils.FOOD_DB_TABLE
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Observable

@Dao
interface FoodDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveFood(entity: FoodEntity): Completable

    @Delete
    fun deleteFood(entity: FoodEntity): Completable

    @Query("SELECT * FROM $FOOD_DB_TABLE")
    fun getAllFoods(): Observable<MutableList<FoodEntity>>

    @Query("SELECT EXISTS (SELECT 1 FROM $FOOD_DB_TABLE WHERE id = :id)")
    fun existsFood(id: Int): Observable<Boolean>
}
