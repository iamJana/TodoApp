package com.jana.todo.Database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.jana.todo.Models.Notes
import com.jana.todo.Utils.DATABASE_NAME

@Database(entities = [Notes::class], version = 1, exportSchema = false)
abstract class NotesDatabase : RoomDatabase() {

    abstract fun getNotesDao() : NotesDao

    companion object{
        @Volatile
        private var INSTANCE : NotesDatabase ?= null

        fun getDatabaseInstance(context : Context) : NotesDatabase {

            return INSTANCE ?: synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    NotesDatabase::class.java,
                    DATABASE_NAME,

                ).build()
                INSTANCE = instance
                instance
            }
        }

    }

}