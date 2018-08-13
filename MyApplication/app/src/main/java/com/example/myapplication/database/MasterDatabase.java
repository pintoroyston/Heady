package com.example.myapplication.database;

import android.arch.persistence.room.RoomDatabase;

@android.arch.persistence.room.Database(entities = {Category.class, Variants.class, Rankings.class}, version = 1)
public abstract class MasterDatabase extends RoomDatabase {
    public abstract CategoryDaoAccess categoryDaoAccess();
    public abstract VariantsDaoAccess variantsDaoAccess();
    public abstract RankingsDaoAccess rankingsDaoAccess();
}
