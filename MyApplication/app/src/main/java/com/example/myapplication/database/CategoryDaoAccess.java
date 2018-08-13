package com.example.myapplication.database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

@Dao
public interface CategoryDaoAccess {
    @Insert
    void insertOnlySingleCategory(Category category);

    @Query("DELETE from Category")
    void deleteAll();

    @Query("SELECT categoryName FROM Category WHERE childCategoryId IS NOT NULL")
    String[] queryMainCategoryList();

    @Query("SELECT childCategoryId FROM Category WHERE categoryName=:categoryName")
    String queryChildCategoryId(String categoryName);

    @Query("SELECT categoryName FROM Category WHERE categoryId=:categoryId GROUP BY 1")
    String queryCategoryForId(String categoryId);

    @Query("SELECT productName FROM Category WHERE categoryName=:categoryName")
    String[] queryProductsForCategoryName(String categoryName);

    @Query("SELECT productId FROM Category WHERE productName=:productName")
    int queryProductIdForProductName(String productName);

    @Query("SELECT productName FROM Category WHERE productId=:productId")
    String queryProductNameForProductId(int productId);

    @Query("SELECT taxName FROM Category WHERE productId=:productId")
    String queryTaxNameForProductId(int productId);

    @Query("SELECT taxValue FROM Category WHERE productId=:productId")
    double queryTaxValueForProductId(int productId);
}

// show variants
// Get VAT
// * select taxName, taxValue from Category where productId=<>;
// show variants
// select color, price from Variants where productId=<>;