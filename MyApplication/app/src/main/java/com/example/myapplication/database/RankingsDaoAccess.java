package com.example.myapplication.database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

@Dao
public interface RankingsDaoAccess {

    @Insert
    void insertOnlySingleRanking(Rankings rankings);

    @Query("DELETE from Rankings")
    void deleteAll();

    @Query("SELECT DISTINCT rankingName from Rankings")
    String[] queryRankingNames();

    @Query("SELECT * FROM Rankings")
    Rankings[] queryAllRankings();
}
