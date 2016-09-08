package com.czettner.habittracker.data;

import android.provider.BaseColumns;

public  final class HabitContract {

    // Private constructor, so the class can't be instantiated
    private HabitContract() {}

    // Habit table
    public static final class HabitEntry implements BaseColumns {
        public final static String TABLE_NAME = "habits";
        public final static String _ID = BaseColumns._ID;

        public final static String COLUMN_HABIT_TYPE = "type";
        public final static String COLUMN_HABIT_TIMESTAMP = "timestamp";
        public final static String COLUMN_HABIT_LENGTH = "length";
        public final static String COLUMN_HABIT_HOWIFELT = "howifelt";

        // type constants
        public static final int TYPE_NAP = 0;         // Taking a nap
        public static final int TYPE_PARAGLIDING = 1; // Paragliding
        public static final int TYPE_WINDSURFING = 2; // Windsurfing
        public static final int TYPE_KAYAKING = 3;    // Kayaking
        public static final int TYPE_HILLWALKING = 4; // Hillwalking
    }
}
