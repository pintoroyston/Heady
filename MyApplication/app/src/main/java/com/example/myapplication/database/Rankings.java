package com.example.myapplication.database;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class Rankings {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String rankingName;
    private int productId;
    private int rankingCount;

    // Getters

    public int getId() {
        return id;
    }

    public String getRankingName() {
        return rankingName;
    }

    public int getProductId() {
        return productId;
    }

    public int getRankingCount() {
        return rankingCount;
    }

    // Setters

    public void setId(int id) {
        this.id = id;
    }

    public void setRankingName(String rankingName) {
        this.rankingName = rankingName;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public void setRankingCount(int rankingCount) {
        this.rankingCount = rankingCount;
    }
}
