/**
 * We need an activity so the code compiles and can run on the device
 */

package com.czettner.habittracker;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.czettner.habittracker.data.HabitContract.HabitEntry;
import com.czettner.habittracker.data.HabitDbHelper;

import java.util.Date;

public class MainActivity extends AppCompatActivity {

    private HabitDbHelper mDbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mDbHelper = new HabitDbHelper(this);
        displayDatabaseInfo();
    }

    @Override
    protected void onStart() {
        super.onStart();
        displayDatabaseInfo();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_delete_all:
                deleteAll();
                displayDatabaseInfo();
                return true;
            case R.id.action_insert_dummy_data:
                insertDummy();
                displayDatabaseInfo();
                return true;
            case R.id.action_set_lengths:
                setLengths();
                displayDatabaseInfo();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Insert dummy data
     */
    private void insertDummy() {
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        Date currentDate = new Date();
        ContentValues v = new ContentValues();
        v.put(HabitEntry.COLUMN_HABIT_TYPE, HabitEntry.TYPE_NAP);
        v.put(HabitEntry.COLUMN_HABIT_TIMESTAMP, currentDate.getTime() / 1000); // UNIX time
        v.put(HabitEntry.COLUMN_HABIT_LENGTH, 3600); // 3600 seconds = 1 hour
        v.put(HabitEntry.COLUMN_HABIT_HOWIFELT, "Relaxed");

        long newRowId = db.insert(HabitEntry.TABLE_NAME, null, v);
    }

    /**
     * Delete all records
     */
    private void deleteAll() {
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        // Delete all records
        db.delete(HabitEntry.TABLE_NAME, null, null);
    }

    /**
     * Set lengths to 5 minutes (300s)
     */
    private void setLengths() {
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        ContentValues v = new ContentValues();
        v.put(HabitEntry.COLUMN_HABIT_LENGTH, 300);
        db.update(HabitEntry.TABLE_NAME, v, null, null);
    }

    private Cursor read() {
        // Create and/or open a database to read from it
        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        Cursor cursor = db.query(
                HabitEntry.TABLE_NAME,
                new String[]{
                        HabitEntry._ID,
                        HabitEntry.COLUMN_HABIT_TYPE,
                        HabitEntry.COLUMN_HABIT_TIMESTAMP,
                        HabitEntry.COLUMN_HABIT_LENGTH,
                        HabitEntry.COLUMN_HABIT_HOWIFELT,
                },
                null,
                null,
                null,
                null,
                null
        );
        return cursor;
    }

    /**
     * Display database info
     */
    private void displayDatabaseInfo() {

        Cursor cursor = read();
        try {
            // Display the number of rows in the Cursor (which reflects the number of rows in the
            // pets table in the database).
            TextView displayView = (TextView) findViewById(R.id.text_view_habit);
            displayView.setText("Number of rows in pets database table: " + cursor.getCount() + "\n\n");
            displayView.append(HabitEntry._ID + " - " +
                    HabitEntry.COLUMN_HABIT_TYPE + " - " +
                    HabitEntry.COLUMN_HABIT_TIMESTAMP + " - " +
                    HabitEntry.COLUMN_HABIT_LENGTH + " - " +
                    HabitEntry.COLUMN_HABIT_HOWIFELT + " \n "

            );
            int idColumnIndex = cursor.getColumnIndex(HabitEntry._ID);
            int typeColumnIndex = cursor.getColumnIndex(HabitEntry.COLUMN_HABIT_TYPE);
            int timestampColumnIndex = cursor.getColumnIndex(HabitEntry.COLUMN_HABIT_TIMESTAMP);
            int lengthColumnIndex = cursor.getColumnIndex(HabitEntry.COLUMN_HABIT_LENGTH);
            int howifeltColumnIndex = cursor.getColumnIndex(HabitEntry.COLUMN_HABIT_HOWIFELT);

            while (cursor.moveToNext()) {
                int currentID = cursor.getInt(idColumnIndex);
                int currentType = cursor.getInt(typeColumnIndex);
                int currentTimestamp = cursor.getInt(timestampColumnIndex);
                int currentLength = cursor.getInt(lengthColumnIndex);
                String currentHowifelt = cursor.getString(howifeltColumnIndex);
                displayView.append(("\n" + currentID + " - " +
                        currentType + " - " +
                        currentTimestamp + " - " +
                        currentLength + " - " +
                        currentHowifelt));
            }
        } finally {
            // Always close the cursor when you're done reading from it. This releases all its
            // resources and makes it invalid.
            cursor.close();
        }
    }
}
