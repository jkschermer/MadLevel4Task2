package com.hva.madlevel4task2.model

import androidx.room.*
import java.util.*


@Entity(tableName= "gameTable")
data class Game(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name= "id")
    val id: Long? = null,

    @TypeConverters
    @ColumnInfo
    var date: Date? = null,

    @ColumnInfo
    var result: String? = null,

    @ColumnInfo
    var move_computer: String? = null,

    @ColumnInfo
    var move_user: String? = null,

    @ColumnInfo
    var amountOfGamesWon: Int?,

    @ColumnInfo
    var amountOfGamesDrawn: Int?,

    @ColumnInfo
    var amountOfGamesLost: Int?
)

{  constructor() : this(null, null, null, null, null, 0, 0, 0)


}



