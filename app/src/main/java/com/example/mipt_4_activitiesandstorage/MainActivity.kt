package com.example.mipt_4_activitiesandstorage

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
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

        loadNotes()

        val listView: ListView = findViewById(R.id.listView)
        adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, getNoteNames())
        listView.adapter = adapter

        // Set up button click listeners
        findViewById<Button>(R.id.btnCreateNote).setOnClickListener {
            startActivity(Intent(this, AddNoteActivity::class.java))
        }

        findViewById<Button>(R.id.btnDeleteNote).setOnClickListener {
            startActivity(Intent(this, DeleteNoteActivity::class.java))
        }

        listView.setOnItemClickListener { _, _, position, _ ->
            val selectedNote = notesList[position]

            val selectedNoteNameTextView: TextView = findViewById(R.id.textViewSelectedNoteName)
            val selectedNoteContentTextView: TextView = findViewById(R.id.textViewSelectedNoteContent)

            selectedNoteNameTextView.text = "Note Name: ${selectedNote.name}"
            selectedNoteContentTextView.text = "Note Content: ${selectedNote.content}"

            selectedNoteNameTextView.visibility = View.VISIBLE
            selectedNoteContentTextView.visibility = View.VISIBLE
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
