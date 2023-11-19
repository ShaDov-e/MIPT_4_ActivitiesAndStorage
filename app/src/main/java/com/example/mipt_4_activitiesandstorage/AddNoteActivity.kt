import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.example.mipt_4_activitiesandstorage.R
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class AddNoteActivity : AppCompatActivity() {

    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_note)

        sharedPreferences = getSharedPreferences("NotesPrefs", MODE_PRIVATE)

        val btnSave: Button = findViewById(R.id.btnSave)
        btnSave.setOnClickListener {
            saveNote()
        }
    }

    private fun saveNote() {
        val etNoteName: EditText = findViewById(R.id.etNoteName)
        val etNoteContent: EditText = findViewById(R.id.etNoteContent)

        val name = etNoteName.text.toString().trim()
        val content = etNoteContent.text.toString().trim()

        // Validate input
        if (name.isNotEmpty() && content.isNotEmpty()) {
            val gson = Gson()
            val note = Note(name, content)

            // Load existing notes
            val notesJson = sharedPreferences.getString("notes", null)
            val type = object : TypeToken<ArrayList<Note>>() {}.type
            val notesList = gson.fromJson<ArrayList<Note>>(notesJson, type) ?: ArrayList()

            // Add the new note
            notesList.add(note)

            // Save updated notes to SharedPreferences
            val editor = sharedPreferences.edit()
            editor.putString("notes", gson.toJson(notesList))
            editor.apply()

            // Finish the activity
            finish()
        }
    }
}
