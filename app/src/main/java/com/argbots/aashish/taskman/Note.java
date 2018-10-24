package com.argbots.aashish.taskman;

public class Note {
    private String title, date , tid;
    
    
    public Note() {
    }

    public Note(String title, String date ,String tid) {
        this.title = title;

        this.date = date;

        this.tid = tid;
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

    public String getTid() {
        return tid;
    }

    public void setTid(String tid) {
        this.tid= tid;
    }


}
