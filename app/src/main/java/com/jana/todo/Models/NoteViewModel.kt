package com.jana.todo.Models

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jana.todo.Database.NotesDatabase
import com.jana.todo.Database.NotesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NoteViewModel(application: Application): AndroidViewModel(application) {
    private lateinit var repository : NotesRepository
     lateinit var allNotes : LiveData<List<Notes>>

    init {
        var dao = NotesDatabase.getDatabaseInstance(application
        ).getNotesDao()
        repository = NotesRepository(dao)
        allNotes = repository.allNotes
    }

    fun deleteNote(notes: Notes)=viewModelScope.launch(Dispatchers.IO){
    repository.delete(notes)
    }

    fun insertNote(notes: Notes)=viewModelScope.launch(Dispatchers.IO){
        repository.insert(notes)
    }

    fun updateNote(notes: Notes)=viewModelScope.launch(Dispatchers.IO){
        repository.update(notes)
    }

}