package com.example.Giggles

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class Product(
    @field:PrimaryKey(autoGenerate = true) var pid: Long, @field:ColumnInfo(
        name = "pname"
    ) var pname: String, @field:ColumnInfo(name = "price") var price: Int, @field:ColumnInfo(
        name = "qnt"
    ) var qnt: Int
)