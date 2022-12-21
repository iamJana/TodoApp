package com.jana.todo

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.jana.todo.Models.Notes
import com.jana.todo.databinding.ActivityMainBinding
import com.jana.todo.databinding.ActivityNotesAddBinding
import java.text.SimpleDateFormat
import java.util.*

class NotesAdd : AppCompatActivity() {
    private lateinit var binding: ActivityNotesAddBinding
    private lateinit var note: Notes
    private lateinit var oldNote: Notes
    var isUpdate = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNotesAddBinding.inflate(layoutInflater)
        setContentView(binding.root)

        try {
            oldNote = intent.getSerializableExtra("current_note") as Notes

            binding.etTitle.setText(oldNote.Title)
            binding.etNotes.setText(oldNote.note)
            isUpdate = true

        } catch (e : Exception){
            e.printStackTrace()
        }

        binding.imgCheck.setOnClickListener{
            val title = binding.etTitle.toString()
            val notes_desc = binding.etNotes.toString()
            if(title.isNotEmpty() && notes_desc.isNotEmpty()){
                val formatter =  SimpleDateFormat("EEE, d MMM yyyy HH:mm a")
                if(isUpdate){
                     note= Notes(
                         oldNote.id,
                         title, notes_desc, formatter.format(Date())
                     )
                }
                else{
                        note = Notes(
                            null, title, notes_desc, formatter.format(Date())
                        )
                }
                val intent = Intent()
                intent.putExtra("note" , note)
                setResult(Activity.RESULT_OK, intent)
                finish()
            }
            else{
                Toast.makeText(this@NotesAdd, "Please Add Notes", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
        }

        binding.imgBack.setOnClickListener {
            onBackPressed()
        }


    }
}