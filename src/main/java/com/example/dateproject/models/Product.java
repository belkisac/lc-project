package com.example.dateproject.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.time.Month;

@Entity
public class Product {

    @Id
    @GeneratedValue
    private int id;

    @NotNull
    @Size(min = 1, message = "Field cannot be empty")
    private String name;

    @NotNull
    private LocalDate entryDate;

    @NotNull
    private long expirationFrame;

    private LocalDate expirationDate;

    @ManyToOne
    private Category category;

    public Product() {}

    public Product(String name, LocalDate entryDate, long expirationFrame) {
        this.name = name;
        this.entryDate = entryDate;
        this.expirationFrame = expirationFrame;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getEntryDate() {
        return entryDate;
    }

    public void setEntryDate(LocalDate entryDate) {
        this.entryDate = entryDate;
    }

    public long getExpirationFrame() {
        return expirationFrame;
    }

    public void setExpirationFrame(long expirationFrame) {
        this.expirationFrame = expirationFrame;
    }

    public LocalDate getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(LocalDate expirationDate, long expirationFrame) {
        this.expirationDate = entryDate.plusMonths(expirationFrame);
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
}