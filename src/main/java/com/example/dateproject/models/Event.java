package com.example.dateproject.models;


import org.apache.tomcat.jni.Local;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

@Entity
public class Event {

    @Id
    @GeneratedValue
    private int id;

    private String title;

    private String start;

    public Event(String title) {
        this.title = title;
    }

    public Event() {}

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getStart() {
        return start;
    }

    public void setStart(LocalDate start) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = Date.from(start.atStartOfDay(ZoneId.systemDefault()).toInstant());
        this.start = sdf.format(date);
    }

}
