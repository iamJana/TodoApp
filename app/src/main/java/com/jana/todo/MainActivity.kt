package com.jana.todo

import android.app.Activity
import android.app.Instrumentation.ActivityResult
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.LinearLayout
import android.widget.PopupMenu
import android.widget.SearchView
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts
import androidx.cardview.widget.CardView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.jana.todo.Adapter.NotesAdapter
import com.jana.todo.Database.NotesDatabase
import com.jana.todo.Models.NoteViewModel
import com.jana.todo.Models.Notes
import com.jana.todo.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() , NotesAdapter.NotesClickListener , PopupMenu.OnMenuItemClickListener {
    private lateinit var binding : ActivityMainBinding
     lateinit var database: NotesDatabase
     lateinit var viewModel: NoteViewModel
     lateinit var adapter : NotesAdapter
     lateinit var selectedNote : Notes

    private val updateNote = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){result->
        if(result.resultCode==Activity.RESULT_OK){
            val note = result.data?.getSerializableExtra("note") as? Notes
            if(note!=null){
                viewModel.updateNote(note)
            }
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initUI()

        viewModel = ViewModelProvider(this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(application))
            .get(NoteViewModel::class.java)

        viewModel.allNotes.observe(this) { list ->
            list?.let {
                adapter.updateList(list)
            }
        }
        database = NotesDatabase.getDatabaseInstance(this)

    }

    private fun initUI() {
    binding.recyclerView.setHasFixedSize(true)
    binding.recyclerView.layoutManager = StaggeredGridLayoutManager(2, LinearLayout.VERTICAL )
    adapter = NotesAdapter(this,this)
    binding.recyclerView.adapter = adapter

    val getContent = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){result->
        if (result.resultCode == Activity.RESULT_OK){
            val note = result.data?.getSerializableExtra("note") as? Notes
            if(note!=null){
                viewModel.insertNote(note)
            }

        }
    }
        binding.addNewNote.setOnClickListener {
            val intent = Intent(this,NotesAdd::class.java )
            getContent.launch(intent)
        }

        binding.searchView.setOnQueryTextListener( object : SearchView.OnQueryTextListener{
            override fun onQueryTextChange(p0: String?): Boolean {
                return false
            }

            override fun onQueryTextSubmit(newText: String?): Boolean {
                if(newText!=null){
                    adapter.filterNotes(newText)
                }
                return true
            }
        })

    }

    override fun onItemClicked(note: Notes) {
       val intent = Intent(this@MainActivity, NotesAdd::class.java)
        intent.putExtra("current_note", note)
        updateNote.launch(intent)
    }

    override fun onLongItemClicked(note: Notes, cardView: CardView) {
        selectedNote = note
        popupDisplay(cardView)
    }

    private fun popupDisplay(cardView: CardView) {
    val popup = PopupMenu(this,cardView)
     popup.setOnMenuItemClickListener(this@MainActivity)
     popup.inflate(R.menu.pop_menu_delete)
        popup.show()
    }

    override fun onMenuItemClick(item: MenuItem?): Boolean {
        if(item?.itemId == R.id.deleteNote){
            viewModel.deleteNote(selectedNote)
            return true
        }
        return false
    }
}