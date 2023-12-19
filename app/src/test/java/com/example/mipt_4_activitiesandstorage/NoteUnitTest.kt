package com.example.mipt_4_activitiesandstorage

import android.os.Parcel
import org.junit.Assert.assertEquals
import org.junit.Test

class NoteUnitTest {

    @Test
    fun testNoteCreation() {
        val note = Note("Test Note", "This is a test note content")
        assertEquals("Test Note", note.name)
        assertEquals("This is a test note content", note.content)
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
