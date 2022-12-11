package com.jana.todo.Database

import androidx.lifecycle.LiveData
import com.jana.todo.Models.Notes
class NotesRepository(private val notesDao : NotesDao) {

    val allNotes : LiveData<List<Notes>> = notesDao.getAllNotes()

    suspend fun delete(notes: Notes){
        notesDao.delete(notes)
    }

    suspend fun insert(notes: Notes){
        notesDao.insert(notes)
    }

    suspend fun update(notes: Notes){
        notesDao.update(notes.id, notes.Title, notes.note)
    }
}