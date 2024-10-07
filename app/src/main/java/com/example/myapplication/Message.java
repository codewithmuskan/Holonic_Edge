package com.example.myapplication;



public class Message {
    private String request;
    private String deadline;
    private String incentive;
    private String timeTaken;

    public Message(String message, String deadline, String incentive, String timeTaken) {
        this.request = message;
        this.deadline = deadline;
        this.incentive = incentive;
        this.timeTaken = timeTaken;
    }

    // Getters and setters
    public String getRequest() {
        return request;
    }

    public void setRequest(String request) {
        this.request = request;
    }

    public String getDeadline() {
        return deadline;
    }

    public void setDeadline(String deadline) {
        this.deadline = deadline;
    }



    public String getIncentive() {
        return incentive;
    }

    public void setIncentive(String incentive) {
        this.incentive = incentive;
    }

    public String getTimeTaken() {
        return timeTaken;
    }

    public void setTimeTaken(String timeTaken) {
        this.timeTaken = timeTaken;
    }
}


