package com.groupeleven.studentlife.logic;

import com.groupeleven.studentlife.domainSpecificObjects.Task;

public interface ITodolistLogic {

    public Task[] getData() throws RuntimeException;

    //--------------------------------------------------------------------------------------------------
    // add a task
    public boolean addTask(String name, String priorityText, String startTime, String endTime, String type, int quantity, String unit);


    //--------------------------------------------------------------------------------------------------
    // edit a task
    public boolean editTask(int id, String name, String priorityText, String startTime, String endTime, String type, int quantity, String unit);


    //--------------------------------------------------------------------------------------------------
    // delete a task
    public boolean deleteTask(int id);


    //--------------------------------------------------------------------------------------------------
    // get the priority of a task
    public String getTaskPriorityText(Task task);


    //--------------------------------------------------------------------------------------------------
    // check the user input to see is it validate
    public void checkUserInput(int taskNameLength, String taskPriority, int startLength, int endLength, String type, int workNum, String workUnit) throws Exception;


    //--------------------------------------------------------------------------------------------------
    // covert a date to a standard date format of string
    public String covertDateToString(int year, int month, int day);


    //--------------------------------------------------------------------------------------------------
    // set the task completed or uncompleted
    public boolean setCompleted(int id, boolean status);


    //--------------------------------------------------------------------------------------------------
    // get the time estimate result of a task
    public int getTimeEstimate(int id);
}
