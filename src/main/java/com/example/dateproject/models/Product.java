package com.example.dateproject.models;

import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Entity
public class Product {

    @Id
    @GeneratedValue
    private int id;

    @NotNull
    @NotBlank(message = "Field cannot be empty")
    private String name;

    @NotNull
    private Integer year;

    @NotNull(message = "field cannot be empty")
    @Min(value = 1, message = "Value must be between 1-12")
    @Max(value = 12, message = "Value must be between 1-12")
    private Integer month;

    @NotNull
    @Min(value = 1, message = "Value must be between 1-31")
    @Max(value = 31, message = "Value must be between 1-31")
    private Integer day;

    private LocalDate entryDate;

    @NotNull
    private Long expirationTime;

    private String expirationFrame;

    private LocalDate expirationDate;

    @ManyToOne
    private Category category;

    public Product() {}

    public Product(String name, LocalDate entryDate, long expirationTime, String expirationFrame) {
        this.name = name;
        this.entryDate = entryDate;
        this.expirationTime = expirationTime;
        this.expirationFrame = expirationFrame;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Integer getMonth() {
        return month;
    }

    public void setMonth(Integer month) {
        this.month = month;
    }

    public Integer getDay() {
        return day;
    }

    public void setDay(Integer day) {
        this.day = day;
    }

    public LocalDate getEntryDate() {
        return entryDate;
    }

    public void setEntryDate(int year, int month, int day) {
        this.entryDate = LocalDate.of(year, month, day);
    }

    public Long getExpirationTime() {
        return expirationTime;
    }

    public void setExpirationTime(Long expirationTime) {
        this.expirationTime = expirationTime;
    }

    public String getExpirationFrame() {
        return expirationFrame;
    }

    public void setExpirationFrame(String expirationFrame) {
        this.expirationFrame = expirationFrame;
    }

    public LocalDate getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(LocalDate expirationDate) {
        this.expirationDate = expirationDate;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
}