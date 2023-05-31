package com.example.Giggles

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface ProductDao {
    @Insert
    fun insertrecord(product: Product?)

    @Query("SELECT EXISTS(SELECT * FROM Product WHERE pid = :productid)")
    fun is_exist(productid: Long): Boolean?

    @Query("SELECT * FROM Product")
    fun getallproduct(): MutableList<Product?>?

    @Query("DELETE FROM Product WHERE pid = :id")
    fun deleteById(id: Long)

    @Query("SELECT qnt FROM Product WHERE pid = :id")
    fun getQuantity(id:Long):Int

    @Update
    fun updateRecord(product: Product?)
}