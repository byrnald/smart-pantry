package com.byrnald.smart_pantry.model;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity // tells springboot to create a table named pantry_item in the database
public class PantryItem {
    @Id // this is primary key (unique id)
    @GeneratedValue(strategy = GenerationType.IDENTITY) //this just auto incremements, so it goes by: 1,2,3,4,5... etc
    private Long id;

    private String name;
    private Integer quantity;
    private LocalDate expirationDate;

    ///Contructors:
    // we'll have overloaded contructors, one with no paramters, and one will all the parameters.

    public PantryItem() {  //default contructor

    }

    public PantryItem(String name, Integer quantity, LocalDate expirationDate) {
        this.name = name;
        this.quantity = quantity;
        this.expirationDate = expirationDate;
    }

    /// GETTER's and SETTER's
    public Long getId() { 
        return id;
    }
    public String getName() { 
        return name;
    }
    public Integer getQuantity() { 
        return quantity;
    }
    public LocalDate getExpirationDate() { 
        return expirationDate;
    }

    //SETTER's
    public void setId(Long id) { 
        this.id = id;
    }
    public void setName(String name) { 
        this.name = name;
    }
    public void setQuantity(Integer quantity) { 
        this.quantity = quantity;
    }
    public void setExpirationDate(LocalDate expirationDate) { 
        this.expirationDate = expirationDate;
    }

    private String category; // we can add a category field to categorize items, like "food", "electronics"

    //now we add the getter and settery for category
    public String getCategory() { 
        return category;
    }
    public void setCategory(String category) { 
        this.category = category;
    }

}
