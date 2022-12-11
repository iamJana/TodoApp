package com.jana.todo.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.jana.todo.Models.Notes
import com.jana.todo.R
import org.w3c.dom.Text
import kotlin.random.Random


class NotesAdapter(private val context : Context) : RecyclerView.Adapter<NotesAdapter.NotesViewHolder> (){

    private val NotesList : ArrayList<Notes>()
    private val fullList : ArrayList<Notes>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotesViewHolder {
        return NotesViewHolder(
            LayoutInflater.from(context).inflate(R.layout.list_items, parent, false)
        )
    }

    override fun onBindViewHolder(holder: NotesViewHolder, position: Int) {
     var currentNote = NotesList[position]
        holder.title.text = currentNote.Title
        holder.title.isSelected = true

        holder.note.text = currentNote.note
        holder.date.text = currentNote.date
        holder.date.isSelected = true
    }

    override fun getItemCount(): Int {
        return NotesList.size
    }

    fun getRandomColor(): Int{
        var colorList = ArrayList<Int>()
        colorList.add(R.color.NotesColor1)
        colorList.add(R.color.NotesColor2)
        colorList.add(R.color.NotesColor3)
        colorList.add(R.color.NotesColor4)
        colorList.add(R.color.NotesColor5)
        colorList.add(R.color.NotesColor6)

        var seed = System.currentTimeMillis().toInt()
        var randomIndex = Random(seed).nextInt(colorList.size)
        return colorList[randomIndex]
    }

    inner class NotesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val notesLayout = itemView.findViewById<CardView>(R.id.cardLayout)
        val title = itemView.findViewById<TextView>(R.id.tvTitle)
        val note = itemView.findViewById<TextView>(R.id.tvNote)
        val date = itemView.findViewById<TextView>(R.id.tvDate)
    }
}