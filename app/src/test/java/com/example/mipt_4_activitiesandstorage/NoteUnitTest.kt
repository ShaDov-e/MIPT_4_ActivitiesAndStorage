package com.example.mipt_4_activitiesandstorage

import android.os.Parcel
import org.junit.Assert.assertEquals
import org.junit.Test

class NoteUnitTest {

    @Test
    fun testNoteCreation() {
        val expectedName = "Test Note"
        val expectedContent = "This is a test note content"

        val note = Note(expectedName, expectedContent)

        assertEquals(expectedName, note.name)
        assertEquals(expectedContent, note.content)
    }

    @Test
    fun testParcelable() {

        val originalNote = Note("Parcelable Note", "Testing Parcelable implementation")
        val parcel = Parcel.obtain()
        originalNote.writeToParcel(parcel, originalNote.describeContents())
        parcel.setDataPosition(0)
        val loadedNote = Note.CREATOR.createFromParcel(parcel)

        assertEquals(originalNote, loadedNote)
    }
}
