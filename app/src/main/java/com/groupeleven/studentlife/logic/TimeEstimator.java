package com.groupeleven.studentlife.logic;

import com.groupeleven.studentlife.domainSpecificObjects.Task;

public class TimeEstimator {
    private final int TIME_PER_PAGE = 5;
    private final int WORDS_PER_MINUTE = 75;
    private final int LECTURES_PER_WEEK = 150;
    private int numCourses;
    private int minutesPerWeek;

    //Creates a TimeEstimator tailored to a specific study schedule
    public TimeEstimator(int numCourses, int hoursPerWeek){
        this.numCourses=numCourses;
        this.minutesPerWeek=60*hoursPerWeek;
    }

    //Use this function to get the time in minutes estimated to complete a specific task
    public int getTimeEstimate(Task t){
        int result = -1;
        switch(t.getType().toLowerCase()){
            case "reading":
                result = readingEstimate(t);
                break;
            case "homework":
                result = homeworkEstimate(t);
                break;
            case "lecture":
                result = lectureEstimate(t);
                break;
        }
        return result;
    }

    public int readingEstimate(Task t){
        int result = -1;
        switch(t.getQuantityUnit().toLowerCase()){
            case "pages":
                result = TIME_PER_PAGE*t.getQuantity();
                break;
            case "words":
                result = t.getQuantity()/WORDS_PER_MINUTE;
                break;
        }
        return result;
    }

    public int homeworkEstimate(Task t){
        int result = -1;
        switch(t.getQuantityUnit().toLowerCase()){
            case "days":
                result = t.getQuantity()*(4*((minutesPerWeek/numCourses)-LECTURES_PER_WEEK)/5)/7;
                break;
            case "weeks":
                result = t.getQuantity()*(4*((minutesPerWeek/numCourses)-LECTURES_PER_WEEK)/5);
                break;
        }
        return result;
    }

    public int lectureEstimate(Task t){
        int result = -1;
        switch(t.getQuantityUnit().toLowerCase()){
            case "minutes":
                result = t.getQuantity();
                break;
            case "hours":
                result = t.getQuantity()*60;
        }
        return result;
    }

    public int getNumCourses() {
        return numCourses;
    }

    public void setNumCourses(int numCourses) {
        this.numCourses = numCourses;
    }

    public int getMinutesPerWeek() {
        return minutesPerWeek;
    }

    public void setMinutesPerWeek(int minutesPerWeek) {
        this.minutesPerWeek = minutesPerWeek;
    }

}
