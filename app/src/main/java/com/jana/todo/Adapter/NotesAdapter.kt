package com.jana.todo.Adapter

import android.content.Context
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.jana.todo.Models.Notes
import com.jana.todo.R
import kotlin.random.Random


class NotesAdapter(private val context : Context, val listener : NotesClickListener) : RecyclerView.Adapter<NotesAdapter.NotesViewHolder> (){

    private val notesList = ArrayList<Notes>()
    private val fullList = ArrayList<Notes>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotesViewHolder {
        return NotesViewHolder(
            LayoutInflater.from(context).inflate(R.layout.list_items, parent, false)
        )
    }

    override fun onBindViewHolder(holder: NotesViewHolder, position: Int) {
     var currentNote = notesList[position]
        holder.title.text = currentNote.Title
        holder.title.isSelected = true

        holder.note.text = currentNote.note
        holder.date.text = currentNote.date
        holder.date.isSelected = true

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            holder.notesLayout.setCardBackgroundColor(holder.itemView.resources.getColor(getRandomColor(),null))
        }

        holder.notesLayout.setOnClickListener {
            listener.onItemClicked(notesList[holder.adapterPosition])
        }
        holder.notesLayout.setOnLongClickListener {
            listener.onLongItemClicked(notesList[holder.adapterPosition], holder.notesLayout)
            true
        }
    }

    override fun getItemCount(): Int {
        return notesList.size
    }

    fun updateList(newList: List<Notes>){
        fullList.clear()
        fullList.addAll(newList)

        notesList.clear()
        notesList.addAll(fullList)
        notifyDataSetChanged()
    }

    fun filterNotes(search : String){
        notesList.clear()
        for (item in fullList){
            if(item.Title?.lowercase()?.contains(search.lowercase())==true ||
                item.note?.lowercase()?.contains(search.lowercase())==true){
                notesList.add(item)
            }
        }
        notifyDataSetChanged()
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
    interface NotesClickListener{
        fun onItemClicked(note:Notes)
        fun onLongItemClicked(note:Notes, cardView: CardView)
    }
}