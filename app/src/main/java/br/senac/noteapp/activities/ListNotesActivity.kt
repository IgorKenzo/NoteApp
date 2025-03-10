package br.senac.noteapp.activities

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import br.senac.noteapp.R
import br.senac.noteapp.adapter.NoteRecyclerViewAdapter
import br.senac.noteapp.databinding.ActivityListNotesBinding
import br.senac.noteapp.databinding.NoteCardBinding
import br.senac.noteapp.db.AppDatabase
import br.senac.noteapp.model.Note
import br.senac.noteapp.model.Notes
import java.math.RoundingMode

class ListNotesActivity : AppCompatActivity() {
    lateinit var binding: ActivityListNotesBinding
    lateinit var adapter: NoteRecyclerViewAdapter
    val noteList = arrayListOf<Note>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListNotesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.fab.setOnClickListener {
            val i = Intent(this, NewNoteActivity::class.java)
            startActivity(i)
        }

        adapter = NoteRecyclerViewAdapter(noteList)

        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
    }

    fun updateUI(notes : List<Note>) {
        //binding.noteContainer.removeAllViews()

        val prefManager = PreferenceManager.getDefaultSharedPreferences(this)
        val color = prefManager.getInt("noteColor", R.color.noteDefaultColor)

        //Notes.noteList.forEach {
        notes.forEach {
            val cardBinding = NoteCardBinding.inflate(layoutInflater)

            cardBinding.txtTitle.text = it.title
            cardBinding.txtDesc.text = it.desc
            cardBinding.txtUser.text = it.user

            cardBinding.root.setCardBackgroundColor(color)

            //binding.noteContainer.addView(cardBinding.root)
        }
    }

    fun refreshNotes() {
        val db = Room.databaseBuilder(this, AppDatabase::class.java, "db").build()
        Thread {
            val notes = db.noteDao().getAll()

            runOnUiThread {
                //updateUI(notes)
                noteList.clear()
                noteList.addAll(notes)

                adapter.notifyDataSetChanged()
            }
            
        }.start()
    }

    override fun onResume() {
        super.onResume()
        refreshNotes()
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.user -> {
                val i = Intent(this, UserActivity::class.java)
                startActivity(i)
            }
            R.id.config -> {
                val i = Intent(this, SettingsActivity::class.java)
                startActivity(i)
            }
        }
        return super.onOptionsItemSelected(item)
    }
}