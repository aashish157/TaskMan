package com.argbots.aashish.taskman;

public class Note {
    private String title, date;
    
    
    public Note() {
    }

    public Note(String title, String date) {
        this.title = title;

        this.date = date;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String name) {
        this.title = name;
    }

    public String getdate() {
        return date;
    }

    public void setdate(String date) {
        this.date = date;
    }


}
