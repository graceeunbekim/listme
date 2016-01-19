package com.example.listme.listme;

import java.util.*;
import java.util.zip.CheckedInputStream;

import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.content.Context;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.ArrayAdapter;
import android.view.LayoutInflater;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    protected TaskDBHelper db;
    List<Task> list;
    MyAdapter adapt;


    private class MyAdapter extends ArrayAdapter<Task> {
        Context context;
        List<Task> taskList=new ArrayList<Task>();
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
//            CheckBox chk = null;
//
//            if (convertView == null) {
//                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//                View rowView = inflater.inflate(R.layout.list_inner_view, parent, false);
//                chk=(CheckBox)rowView.findViewById(R.id.checkBox1);
//                convertView.setTag(chk);
//
//                chk.setOnClickListener(new View.OnClickListener() {
//
//                    @Override
//                    public void onClick(View v) {
//                        CheckBox cb = (CheckBox) v;
//                        Task changeTask = (Task) cb.getTag();
//                        changeTask.setStatus(cb.isChecked() == true ? 1 : 0);
//                        db.updateTask(changeTask);
//
//                        Toast.makeText(
//                                getApplicationContext(),
//                                "Clicked on Checkbox: " + cb.getText() + " is "
//                                        + cb.isChecked(), Toast.LENGTH_LONG)
//                                .show();
//                    }
//                });
//            } else {
//                chk = (CheckBox) convertView.getTag();
//            }
//            Task current = taskList.get(position);
//            chk.setText(current.getTaskName());
//            chk.setChecked(current.getStatus() == 1 ? true : false);
//            chk.setTag(current);
//            Log.d("listener", String.valueOf(current.getId()));
//            return convertView;
//        }
    }

    /*
        onCreate() displays a main activity and get all tasks written into a database.
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
            Toast.makeText(this, "enter the task description first!!", Toast.LENGTH_LONG);
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
