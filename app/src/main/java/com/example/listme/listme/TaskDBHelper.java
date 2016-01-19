package com.example.listme.listme;

import java.util.*;
import android.database.Cursor;
import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase.CursorFactory;

/**
 * TaskDBHelper is a helper class to write and read a task into a SQL data base.
 */
public class TaskDBHelper extends SQLiteOpenHelper {

    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "taskerManager";

    // Task Table Name
    public static final String TABLE_TASKS = "tasks";

    // Table Column Names
    public static final String KEY_ID = "id";
    public static final String KEY_STATUS = "status";
    public static final String KEY_TASKNAME = "taskName";

    public TaskDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE IF NOT EXISTS " + TABLE_TASKS + " ( "
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + KEY_TASKNAME+ " TEXT, "
                + KEY_STATUS + " INTEGER)";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldV, int newV) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TASKS);
        onCreate(db);
    }

    /*
        addTask() takes a task object and writes it into a SQL database.

        @param: task is a Task object.
     */
    public void addTask(Task task) {
        SQLiteDatabase db = this.getWritableDatabase();

        // getting values
        // status of task- can be 0 for not done and 1 for done
        ContentValues values = new ContentValues();
        values.put(KEY_TASKNAME, task.getTaskName());
        values.put(KEY_STATUS, task.getStatus());

        // Inserting Row
        db.insert(TABLE_TASKS, null, values);
        db.close();
    }

    /*
        getAllTasks() returns a list of Task objects that are written into a current SQL database.

        @return: a list of Task objects from database table.
     */
    public List<Task> getAllTasks() {
        List<Task> taskList = new ArrayList<Task>();

        String selectQuery = "SELECT  * FROM " + TABLE_TASKS;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Task task = new Task();
                task.setId(cursor.getInt(0));
                task.setTaskName(cursor.getString(1));
                task.setStatus(cursor.getInt(2));

                taskList.add(task);
            } while (cursor.moveToNext());
        }
        return taskList;
    }

    /*
       updateTask() takes a new task object ands update a task with new information. If task id
       that we're targeting to update is matched, then update the information correctly.
       Otherwise, do not proceed with overwriting.

       @param: task is a Task object with new information that a user wants to overwrite (update).
     */
    public void updateTask(Task task) {
        // updating row
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_TASKNAME, task.getTaskName());
        values.put(KEY_STATUS, task.getStatus());
        db.update(TABLE_TASKS, values, KEY_ID + " = ?",
                new String[]{String.valueOf(task.getId())});
    }

    public boolean deleteTask(int taskId) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_TASKS, KEY_ID + "=" + taskId, null) > 0;
    }
}
