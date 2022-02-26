package com.teddyDev.myweather.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import org.jetbrains.annotations.NotNull

@Entity(tableName = "t_location")
data class LocationEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,

    @NotNull @ColumnInfo(name = "location")
    val location: String
)

