package com.jana.todo.Models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "notes_table")
data class Notes(
    @PrimaryKey(autoGenerate = true)
    val id : Int?,
    @ColumnInfo(name= "title")
    val Title : String?,
    @ColumnInfo(name= "note")
    val note : String?,
    @ColumnInfo(name = "date")
    val date : String?
)
