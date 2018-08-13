package com.example.myapplication.database;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class Variants {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private int categoryId;
    private int productId;
    private int variantId;
    private String color;
    private int size;
    private int price;

    // Getters

    public int getId() {
        return id;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public int getProductId() {
        return productId;
    }

    public int getVariantId() {
        return variantId;
    }

    public String getColor() {
        return color;
    }

    public int getPrice() {
        return price;
    }

    public int getSize() {
        return size;
    }

    // Setters

    public void setId(int id) {
        this.id = id;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public void setVariantId(int variantId) {
        this.variantId = variantId;
    }
}
