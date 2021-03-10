package com.groupeleven.studentlife.logic;

import com.groupeleven.studentlife.data.FakeDB;
import com.groupeleven.studentlife.data.IDatabase;
import com.groupeleven.studentlife.domainSpecificObjects.Task;
public class TodolistLogic implements ITodolistLogic {

    private IDatabase database;

    public TodolistLogic(){this.database = new FakeDB(); }

//--------------------------------------------------------------------------------------------------
// get task list form database

    @Override
    public Task[] getData() throws RuntimeException{
        //fetch data from the database
        Task[] list = null;

        try {
            list = database.getTasks();
        }catch(Exception exception){
            list = new Task[0];
        }

        return list;
    }

//--------------------------------------------------------------------------------------------------
// add a task
    @Override
    public boolean addTask(String name, int priority, String endTime){
        Task newTask = new Task(name, priority, "2020-01-01 12:12:12",endTime, 0, "test Type");
        boolean notEmptyName = !name.equals("");
        boolean notEmptyPriority = (priority!=0);
        boolean not0priority = priority!=0;
        boolean result = false;

        if(notEmptyName&&notEmptyPriority&&not0priority) {
            result = database.insertTask(newTask);
        }
        return result;
    }

//--------------------------------------------------------------------------------------------------
// edit a task
    @Override
    public boolean editTask(int id, String name, int priority, String endTime){
        Task newTask = new Task(name, priority, "2020-01-01 12:12:12",endTime, 0, "test Type");
        boolean notEmptyName = !name.equals("");
        boolean notEmptyPriority = (priority!=0);
        boolean not0priority = priority!=0;
        boolean result = false;

        if(notEmptyName&&notEmptyPriority&&not0priority) {
            result = database.updateTask(newTask,id);
        }
        return result;
    }

//--------------------------------------------------------------------------------------------------
// delete a task
    @Override
    public boolean deleteTask(int id){
        Task whichTask = database.getTasks()[id];

        return database.deleteTask(whichTask);
    }

//--------------------------------------------------------------------------------------------------
// find which data user no input in adding

    @Override
    public String whichDataNotfill(String taskName, String taskPriority, String taskDate, String taskTime) {
        String result = "";
        int intPriority = ITodolistLogic.toInt(taskPriority);

        if(intPriority==0&&!taskName.equals("")&&!taskDate.equals("")&&!taskTime.equals("")){
            result = "Please choose a priority";
        }
        else if (intPriority!=0&&taskName.equals("")&&!taskDate.equals("")&&!taskTime.equals("")){
            result = "Please input a task name";
        }
        else if(intPriority!=0&&!taskName.equals("")&&taskDate.equals("")&&!taskTime.equals("")){
            result = "Please choose a date";
        }
        else if(intPriority!=0&&!taskName.equals("")&&!taskDate.equals("")&&taskTime.equals("")){
            result = "Please choose a time";
        }
        else{
           result = "Please fill all information";
        }
        return result;
    }

}
