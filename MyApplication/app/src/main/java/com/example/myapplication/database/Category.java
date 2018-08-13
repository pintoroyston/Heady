package com.example.myapplication.database;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class Category {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private int categoryId;
    private String categoryName;
    private String productName;
    private int productId;
    private String childCategoryId;
    private String date;
    private String taxName;
    private double taxValue;

    public Category() {
    }

    // Getters

    public int getId() {
        return id;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public String getProductName() {
        return productName;
    }

    public int getProductId() {
        return productId;
    }

    public String getChildCategoryId() {
        return childCategoryId;
    }

    public String getDate() {
        return date;
    }

    public String getTaxName() {
        return taxName;
    }

    public double getTaxValue() {
        return taxValue;
    }

    // Setters

    public void setId(int id) {
        this.id = id;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public void setChildCategoryId(String childCategoryId) {
        this.childCategoryId = childCategoryId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public void setTaxName(String taxName) {
        this.taxName = taxName;
    }

    public void setTaxValue(double taxValue) {
        this.taxValue = taxValue;
    }
}
