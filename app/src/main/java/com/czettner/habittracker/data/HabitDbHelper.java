package com.czettner.habittracker.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.czettner.habittracker.data.HabitContract.HabitEntry;

/**
 * Created by sandor on 08/09/16.
 */
public class HabitDbHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "habit.db";
    private static final int DATABASE_VERSION = 1;

    public HabitDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String SQL_CREATE_HABITS_TABLE = "CREATE TABLE " + HabitEntry.TABLE_NAME + " ("
                + HabitEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + HabitEntry.COLUMN_HABIT_TYPE + " INTEGER NOT NULL, "
                + HabitEntry.COLUMN_HABIT_TIMESTAMP + " INTEGER NOT NULL, "
                + HabitEntry.COLUMN_HABIT_LENGTH + " INTEGER NOT NULL DEFAULT 0, "
                + HabitEntry.COLUMN_HABIT_HOWIFELT + " TEXT);";

        db.execSQL(SQL_CREATE_HABITS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        // The database is still at version 1, so there's nothing to do be done here.
    }
}
