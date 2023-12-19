package com.example.mipt_4_activitiesandstorage;

import androidx.test.espresso.Espresso;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.assertion.ViewAssertions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.allOf;

@RunWith(AndroidJUnit4ClassRunner.class)
public class NoteApplicationUITest {

    @Rule
    public ActivityScenarioRule<MainActivity> activityRule = new ActivityScenarioRule<>(MainActivity.class);

    @Test
    public void testAddNoteButton() {
        Espresso.onView(ViewMatchers.withId(R.id.btnCreateNote))
                .perform(ViewActions.click());

        Espresso.onView(ViewMatchers.withId(R.id.etNoteName))
                .perform(ViewActions.typeText("Test Note"), ViewActions.closeSoftKeyboard());
        Espresso.onView(ViewMatchers.withId(R.id.etNoteContent))
                .perform(ViewActions.typeText("This is a test note content"), ViewActions.closeSoftKeyboard());
        Espresso.onView(ViewMatchers.withId(R.id.btnSave))
                .perform(ViewActions.click());
        Espresso.onView(ViewMatchers.withId(R.id.listView))
                .check(ViewAssertions.matches(ViewMatchers.withChild(ViewMatchers.withText("Test Note"))));
    }

    @Test
    public void testDeleteNoteButton() {
        Espresso.onView(ViewMatchers.withId(R.id.btnDeleteNote))
                .perform(ViewActions.click());

        Espresso.onView(ViewMatchers.withId(R.id.spinnerNotes))
                .perform(ViewActions.click());
        Espresso.onData(allOf(is(instanceOf(String.class)), containsString("Note")))
                .perform(ViewActions.click());
        Espresso.onView(ViewMatchers.withId(R.id.btnDelete))
                .perform(ViewActions.click());

        Espresso.onView(ViewMatchers.withId(R.id.listView))
                .check(ViewAssertions.doesNotExist());
    }
}
