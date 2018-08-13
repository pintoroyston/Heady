package com.example.myapplication.provider;

import android.arch.persistence.room.Room;
import android.content.Context;

import com.example.myapplication.database.MasterDatabase;
import com.example.myapplication.database.Rankings;
import com.example.myapplication.database.Variants;

public class QueryProvider {

    private MasterDatabase masterDatabase;
    private final String DB = "store.db";

    public QueryProvider(Context context) {
        masterDatabase = Room.databaseBuilder(context, MasterDatabase.class, DB).build();
    }

    public String[] mainCategoryList() {
        return masterDatabase.categoryDaoAccess().queryMainCategoryList();
    }

    public String secondLevelCategoryListIds(String name) {
        return masterDatabase.categoryDaoAccess().queryChildCategoryId(name);
    }

    public String getCategoryForId(String id) {
        return masterDatabase.categoryDaoAccess().queryCategoryForId(id);
    }

    public String[] getProductsForCategoryName(String name) {
        return masterDatabase.categoryDaoAccess().queryProductsForCategoryName(name);
    }

    public int getProductIdForProductName(String name) {
        return masterDatabase.categoryDaoAccess().queryProductIdForProductName(name);
    }

    public String getProductNameForProductId(int id) {
        return masterDatabase.categoryDaoAccess().queryProductNameForProductId(id);
    }

    public String getTaxNameForProductId(int id) {
        return masterDatabase.categoryDaoAccess().queryTaxNameForProductId(id);
    }

    public double getTaxValueForProductId(int id) {
        return masterDatabase.categoryDaoAccess().queryTaxValueForProductId(id);
    }

    public Variants[] getVariantsForProductId(int id) {
        return masterDatabase.variantsDaoAccess().queryVariantsForProductId(id);
    }

    public String[] getRankingNames() {
        return masterDatabase.rankingsDaoAccess().queryRankingNames();
    }

    public Rankings[] getAllRankings() {
        return masterDatabase.rankingsDaoAccess().queryAllRankings();
    }
}
