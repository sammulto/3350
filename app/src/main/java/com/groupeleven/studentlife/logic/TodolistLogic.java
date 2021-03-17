package com.groupeleven.studentlife.logic;

import com.groupeleven.studentlife.data.DB;
import com.groupeleven.studentlife.data.FakeDB;
import com.groupeleven.studentlife.data.IDatabase;
import com.groupeleven.studentlife.domainSpecificObjects.ITaskObject;
import com.groupeleven.studentlife.domainSpecificObjects.Task;
public class TodolistLogic implements ITodolistLogic {

    private IDatabase database;
    private ITimeEstimator timeEstimator;

    public TodolistLogic(){
        this.database = new FakeDB();
    }

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
    public boolean addTask(String name, String priorityText, String startTime, String endTime, String type, int quantity, String unit){

        boolean result = false;

        // check the input
        if(validTaskInput(name, priorityText, startTime, endTime, type, quantity, unit)) {

            ITaskObject.Priority priority = ITaskObject.Priority.valueOf(priorityText.toUpperCase());

            Task newTask = new Task(name, priority, startTime, endTime, 0, type,quantity,unit);

            result = database.insertTask(newTask);
        }
        return result;
    }

//--------------------------------------------------------------------------------------------------
// edit a task
    @Override
    public boolean editTask(int id, String name, String priorityText, String startTime, String endTime,String type, int quantity, String unit){

        boolean result = false;

        // check the input
        if(validTaskInput(name, priorityText, startTime, endTime, type, quantity, unit)) {

            ITaskObject.Priority priority = ITaskObject.Priority.valueOf(priorityText.toUpperCase());

            Task newTask = new Task(name, priority, startTime, endTime, 0, type,quantity,unit);

            result = database.updateTask(newTask,id);
        }
        return result;
    }

//--------------------------------------------------------------------------------------------------
// validate the user input

    private boolean validTaskInput(String name, String priorityText, String startTime, String endTime,String type, int quantity, String unit){

        boolean notEmptyName = !name.equals("");
        boolean validPriority = !priorityText.equals("Choose priority");
        boolean notEmptyStart = !startTime.equals(":00");
        boolean notEmptyEnd = !endTime.equals(":00");
        boolean validType = !type.equals("Choose task type");
        boolean notEmptyQ = quantity >0;
        boolean notEmptyUnit = !unit.equals("");

        boolean result = false;

        if(notEmptyName && validPriority && notEmptyStart && notEmptyEnd && validType && notEmptyQ && notEmptyUnit) {
            result = true;
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
// set the task completed or uncompleted
    public boolean setCompleted(int id, boolean status){
        Task whichTask = database.getTasks()[id];
        whichTask.setCompleted(status);
        return database.updateTask(whichTask,id);
    }

//--------------------------------------------------------------------------------------------------
// get time estimate result
    public int getTimeEstimate(int id){
        Task whichTask = database.getTasks()[id];
        timeEstimator = new TimeEstimator(4,40);
        return timeEstimator.getTimeEstimate(whichTask);
    }

//--------------------------------------------------------------------------------------------------
// find which data user no input in adding

    @Override
    public void checkUserInput(int taskNameLength, String taskPriority, int startLength, int endLength, String type, int workNum, String workUnit) throws Exception {

        Boolean validPriority = !taskPriority.equals("Choose priority");
        boolean validType = !type.equals("Choose task type");
        boolean notEmptyUnit = !workUnit.equals("");

        // check error one by one text box
        if ( taskNameLength != 0 && endLength != 0 && startLength != 0 && !validPriority && validType && workNum != 0 && notEmptyUnit){
            throw new Exception("Please choose a priority");
        }
        else if (taskNameLength == 0 && endLength != 0 && startLength != 0 && validPriority && validType && workNum != 0 && notEmptyUnit){
            throw new Exception("Please input a task name");
        }
        else if(taskNameLength != 0 && endLength != 0 && startLength == 0 && validPriority && validType && workNum != 0 && notEmptyUnit){
            throw new Exception("Please choose a start time");
        }
        else if(taskNameLength != 0 && endLength == 0 && startLength != 0 && validPriority && validType && workNum != 0 && notEmptyUnit){
            throw new Exception("Please choose a end time");
        }
        else if(taskNameLength != 0 && endLength != 0 && startLength != 0 && validPriority && !validType && workNum != 0 && notEmptyUnit){
            throw new Exception("Please choose a task type");
        }
        else if(taskNameLength != 0 && endLength != 0 && startLength != 0 && validPriority && validType && workNum == 0 && notEmptyUnit){
            throw new Exception("Please choose a quantity");
        }
        else if(taskNameLength != 0 && endLength != 0 && startLength != 0 && validPriority && validType && workNum != 0 && !notEmptyUnit){
            throw new Exception("Please choose a unit");
        }
        else {
            throw new Exception("Please fill all information");
        }
    }


//--------------------------------------------------------------------------------------------------
// find which data user no input in adding
    @Override
    public String getTaskPriorityText (Task task){

        String rawText = task.getPriority().name();
        String priorityText = rawText.substring(0,1) + rawText.substring(1).toLowerCase();
        return priorityText;
    }


//--------------------------------------------------------------------------------------------------
// Cover int date to String in the a DB accepted format
    @Override
    public String covertDateToString(int year, int month, int day){

        String tempMon=""+month;
        String tempDay=""+day;
        month = month + 1;
        if(day<10){
            tempDay = "0"+day;
        }
        if(month<10){
            tempMon = "0"+month;
        }
        String date = year+"-"+tempMon+"-"+tempDay;
        return date;
    }
}