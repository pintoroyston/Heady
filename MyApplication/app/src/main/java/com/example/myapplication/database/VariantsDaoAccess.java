package com.example.myapplication.database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

@Dao
public interface VariantsDaoAccess {
    @Insert
    void insertOnlySingleVariant(Variants variants);

    @Query("DELETE from Variants")
    void deleteAll();

    @Query("SELECT * from Variants WHERE productId=:productId")
    Variants[] queryVariantsForProductId(int productId);
}
