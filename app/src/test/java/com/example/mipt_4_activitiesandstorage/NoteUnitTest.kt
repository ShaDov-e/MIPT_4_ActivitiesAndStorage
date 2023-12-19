package com.example.mipt_4_activitiesandstorage

import android.os.Parcel
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class NoteUnitTest {

    @Test
    fun testNoteCreation() {
        // Given
        val expectedName = "Test Note"
        val expectedContent = "This is a test note content"

        // When
        val note = Note(expectedName, expectedContent)

        // Then
        assertEquals(expectedName, note.name)
        assertEquals(expectedContent, note.content)
    }

    @Test
    fun testParcelable() {
        // Given
        val originalNote = Note("Parcelable Note", "Testing Parcelable implementation")

        // When
        val parcel = Parcel.obtain()
        originalNote.writeToParcel(parcel, originalNote.describeContents())
        parcel.setDataPosition(0)
        val loadedNote = Note.CREATOR.createFromParcel(parcel)

        // Then
        assertEquals(originalNote, loadedNote)
    }
}
