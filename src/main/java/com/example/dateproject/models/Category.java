package com.example.dateproject.models;



import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;


@Entity
public class Category {

    @Id
    @GeneratedValue
    private int id;

    @NotNull
    private String name;

    @OneToMany
    @JoinColumn(name = "category_id")
    private List<Product> products = new ArrayList<>();

    public Category(String name) {
        this.name = name;
    }

    public Category() {}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
