package com.teddyDev.myweather.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import org.jetbrains.annotations.NotNull

@Entity(tableName = "t_location", primaryKeys = ["name","country"])
data class LocationEntity(
    val name: String,
    val country: String,
    val lat: String,
    val lon: String,
    val state: String?
)

