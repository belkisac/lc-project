package com.example.dateproject.models.forms;

import javax.validation.constraints.NotNull;
import java.time.Month;

public class AddProductForm {

    @NotNull
    private String name;

    @NotNull
    private int year;

    @NotNull
    private String month;

    @NotNull
    private int day;

    @NotNull
    private long expirationTime;

    public AddProductForm() {}

    public AddProductForm(String name, String month, int day, int year, long expirationTime) {
        this.name = name;
        this.month = month;
        this.day = day;
        this.year = year;
        this.expirationTime = expirationTime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public long getExpirationTime() {
        return expirationTime;
    }

    public void setExpirationTime(long expirationTime) {
        this.expirationTime = expirationTime;
    }
}
