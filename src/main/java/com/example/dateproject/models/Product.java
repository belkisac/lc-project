package com.example.dateproject.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Entity
public class Product {

    @Id
    @GeneratedValue
    private int id;

    @NotNull
    private String name;

    @NotNull
    private LocalDate entryDate;

    @NotNull
    private long expiration;

    private LocalDate expirationDate;

    @ManyToOne
    private Category category;

    public Product(String name, LocalDate entryDate, long expiration) {
        this.name = name;
        this.entryDate = entryDate;
        this.expiration = expiration;
    }

    public Product() {}

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

    public long getExpiration() {
        return expiration;
    }

    public void setExpiration(long expiration) {
        this.expiration = expiration;
    }

    public LocalDate getExpirationDate() {
        return entryDate.plusMonths(expiration);
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
}