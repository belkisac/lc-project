package com.example.dateproject.models;

import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Entity
public class Product {

    @Id
    @GeneratedValue
    @Column(name = "product_id")
    private int id;

    @NotNull
    @NotBlank(message = "Name cannot be empty")
    private String name;

    @NotNull(message = "Year cannot be empty")
    @Min(value = 1, message = "Please enter a valid year")
    private Integer year;

    @NotNull(message = "Month cannot be empty")
    @Min(value = 1, message = "Value must be between 1-12")
    @Max(value = 12, message = "Value must be between 1-12")
    private Integer month;

    @NotNull(message = "Day cannot be empty")
    @Min(value = 1, message = "Value cannot be less than 1")
    private Integer day;

    private LocalDate entryDate;

    @NotNull(message = "Field cannot be empty")
    @Min(value = 1, message = "Value must be at least 1")
    private Long expirationTime;

    private String expirationFrame;

    private LocalDate expirationDate;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

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

    public void setExpirationDate(LocalDate entryDate, String expirationFrame, Long expirationTime) {
        if(expirationFrame.equals("days")) {
            expirationDate = entryDate.plusDays(expirationTime);
        } else if(expirationFrame.equals("weeks")) {
            expirationDate = entryDate.plusWeeks(expirationTime);
        } else {
            expirationDate = entryDate.plusMonths(expirationTime);
        }
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public User getUser() { return user; }

    public void setUser(User user) { this.user = user; }
}