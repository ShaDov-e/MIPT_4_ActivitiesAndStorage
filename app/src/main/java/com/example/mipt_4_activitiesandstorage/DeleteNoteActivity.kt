package com.example.mipt_4_activitiesandstorage

import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class DeleteNoteActivity : AppCompatActivity() {

    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var notesList: ArrayList<Note>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_delete_note)

        sharedPreferences = getSharedPreferences("NotesPrefs", MODE_PRIVATE)

        loadNotes()

        val spinner: Spinner = findViewById(R.id.spinnerNotes)
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, getNoteNames())
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
            }
        }

        val btnDelete: Button = findViewById(R.id.btnDelete)
        btnDelete.setOnClickListener {
            val selectedNotePosition = spinner.selectedItemPosition
            deleteNote(selectedNotePosition)
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

    private fun deleteNote(position: Int) {
        if (position >= 0 && position < notesList.size) {
            notesList.removeAt(position)

            val editor = sharedPreferences.edit()
            val gson = Gson()
            editor.putString("notes", gson.toJson(notesList))
            editor.apply()
            finish()
        }
    }
}
