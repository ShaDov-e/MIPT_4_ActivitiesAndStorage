package com.example.mipt_4_activitiesandstorage
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import com.example.mipt_4_activitiesandstorage.R
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class MainActivity : AppCompatActivity() {

    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var notesList: ArrayList<Note>
    private lateinit var adapter: ArrayAdapter<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        sharedPreferences = getSharedPreferences("NotesPrefs", MODE_PRIVATE)

        // Load notes from SharedPreferences
        loadNotes()

        // Initialize ListView and ArrayAdapter
        val listView: ListView = findViewById(R.id.listView)
        adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, getNoteNames())
        listView.adapter = adapter

        // Handle item click in the ListView
        listView.setOnItemClickListener { _, _, position, _ ->
            val selectedNote = notesList[position]
            val intent = Intent(this, DeleteNoteActivity::class.java)
            intent.putExtra("note", selectedNote)
            startActivity(intent)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_add_note -> {
                startActivity(Intent(this, AddNoteActivity::class.java))
                return true
            }
            R.id.menu_delete_note -> {
                startActivity(Intent(this, DeleteNoteActivity::class.java))
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

    private fun loadNotes() {
        val notesJson = sharedPreferences.getString("notes", null)
        val gson = Gson()
        val type = object : TypeToken<ArrayList<Note>>() {}.type
        notesList = gson.fromJson(notesJson, type) ?: ArrayList()
    }

    private fun getNoteNames(): ArrayList<String> {
        val noteNames = ArrayList<String>()
        for (note in notesList) {
            noteNames.add(note.name)
        }
        return noteNames
    }
}
