package com.example.dateproject.models.forms;


import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class AddProductForm {

    @NotNull
    @Min(value = 1)
    @Max(value = 31)
    private int day;

    @NotNull
    @Min(value = 1)
    @Max(value = 12)
    private int month;

    @NotNull
    @Min(value = 1)
    private int year;

    public AddProductForm() {}

    public AddProductForm(int month, int day, int year) {
        this.month = month;
        this.day = day;
        this.year = year;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }
}