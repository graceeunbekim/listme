package com.example.listme.listme;

/**
 * Task class creates a Task object with task name in string format, status and id in integer.
 * taskName is a name of task.
 * id is a task id for a unique identifier for a task.
 * status is a status of a task. If 0, it's it is not done yet. else if 1, it is done.
 */
public class Task {
    private String taskName;
    private int status;
    private int id;

    /*
        default constructor
     */
    public Task() {
        this.taskName = null;
        this.status = 0;
    }

    /*
       initiate Task object with taskName and status
     */
    public Task(String taskName, int status) {
        super();
        this.taskName = taskName;
        this.status = status;
    }

    /*
        getId() returns an id of a task.

        @return: an integer of a task id.
     */
    public int getId() {
        return id;
    }

    /*
        setId() takes an integer id and sets it to the object's id.

        @ param: id is a unique integer value to identify a task.
     */
    public void setId(int id) {
        this.id = id;
    }

    /*
        getTaskName() returns a name of task.

        @return: a string of task name.
     */
    public String getTaskName() {
        return taskName;
    }

    /*
        setTaskName() takes a string taskname and sets it to the object's task name.

        @param: a string of task name.
     */
    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    /*
        getStatus() returns a status of a task.

        @return: an integer value status of ask. 0 means in process, 1 means done.
     */
    public int getStatus() {
        return status;
    }

    /*
        setStatus() takes an integer status and sets it to the object's task status.

        @param: an integer of status.
     */
    public void setStatus(int status) {
        this.status = status;
    }
}
