package com.example.listme.listme;

import java.util.*;
import java.util.zip.CheckedInputStream;

import android.util.Log;
import android.view.View;
import android.content.Intent;
import android.content.Context;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.ArrayAdapter;
import android.view.LayoutInflater;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.AdapterView.OnItemClickListener;

public class MainActivity extends AppCompatActivity {

    protected TaskDBHelper db;
    List<Task> list;
    MyAdapter adapt;


    private class MyAdapter extends ArrayAdapter<Task> {
        Context context;
        List<Task> taskList = new ArrayList<Task>();
        int layoutResourceId;

        public MyAdapter(Context context, int layoutResourceId, List<Task> objects) {
            super(context, layoutResourceId, objects);
            this.layoutResourceId = layoutResourceId;
            this.taskList=objects;
            this.context=context;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View rowView = inflater.inflate(R.layout.list_inner_view, parent, false);
            CheckBox chk = (CheckBox) rowView.findViewById(R.id.checkBox1);
            Task current = taskList.get(position);
            chk.setText(current.getTaskName());
            chk.setChecked(current.getStatus() == 1 ? true : false);

            return rowView;
        }
    }

    /*
        onCreate() displays a main activity and get all tasks written into a database.
        the method also listens to a long click listner to each item. If a user
        long clicks an item, it deletes the item from database as well as adapter.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //db is a variable of type TaskerDbHelper
        db=new TaskDBHelper(this);
        list = db.getAllTasks();

        adapt = new MyAdapter(this, R.layout.list_inner_view, list);
        ListView listTask = (ListView) findViewById(R.id.listView1);
        listTask.setAdapter(adapt);

        // set a delete listener when a user does a long click.
        listTask.setOnItemLongClickListener(new OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView parent, View view, int position, long id) {

                Task task = list.get(position);
                int taskId = list.get(position).getId();
                db.deleteTask(taskId);
                Log.d("tasker", "" + taskId);

                adapt.remove(task);
                adapt.notifyDataSetChanged();
                return true;
            }
        });

        // onclick it edit a text
        listTask.setOnItemClickListener(new OnItemClickListener() {
                                            @Override
                                            public void onItemClick(AdapterView parent, View view, int position, long id) {
                                                EditText t = (EditText) findViewById(R.id.editText1);
                                                String s = t.getText().toString();

                                                if (s.equalsIgnoreCase("")) {
                                                    TextView error = (TextView) findViewById(R.id.errorMessage);
                                                    String message = "please edit the task name.";
                                                    error.setText(message);
                                                    adapt.notifyDataSetChanged();
                                                } else {
                                                    Task task = list.get(position);
                                                    task.setTaskName(s);
                                                    db.updateTask(task);
                                                    Log.d("tasker", "edit text");
                                                    adapt.notifyDataSetChanged();
                                                }
                                            }
                                        }

        );
    }

    /*
        addTaskNow() takes a view object and write a task description that is entered by a user
        to a database. If no description is entered, toast it with an error message.
        Otherwise, successfully write into a databse.

        @param: view object.
     */
    public void addTaskNow(View v) {
        EditText t = (EditText) findViewById(R.id.editText1);
        String s = t.getText().toString();

        if (s.equalsIgnoreCase("")) {
            TextView error = (TextView) findViewById(R.id.errorMessage);
            String message = "please enter a task name here.";
            error.setText(message);
        } else {
            Task task = new Task(s, 0);
            db.addTask(task);
            Log.d("tasker", "data added");
            t.setText("");
            adapt.add(task);
            adapt.notifyDataSetChanged();
        }
    }
}
