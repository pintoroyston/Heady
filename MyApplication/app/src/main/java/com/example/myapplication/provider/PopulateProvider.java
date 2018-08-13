package com.example.myapplication.provider;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.util.Log;

import com.example.myapplication.database.Category;
import com.example.myapplication.database.MasterDatabase;
import com.example.myapplication.database.Rankings;
import com.example.myapplication.database.Variants;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class PopulateProvider {

    private MasterDatabase masterDatabase;
    private final String DB = "store.db";

    public PopulateProvider(Context context) {
        masterDatabase = Room.databaseBuilder(context, MasterDatabase.class, DB).build();
    }

    public void insertContentIntoProvider(String json) {
        int categoryId = 0;
        String categoryName = null;
        int productId = 0;
        String productName = null;
        String productDateAdded = null;
        String productTaxName = null;
        double productTaxValue = 0;
        int variantId = 0;
        String variantColor = null;
        int variantSize = 0;
        int variantPrice = 0;
        String rankingName = "";
        int rankingCount = 0;

        // clear database from old content
        masterDatabase.categoryDaoAccess().deleteAll();
        masterDatabase.variantsDaoAccess().deleteAll();
        masterDatabase.rankingsDaoAccess().deleteAll();

        try {
            JSONObject masterObject = new JSONObject(json);
            JSONArray categoriesArray = masterObject.getJSONArray("categories");
            for (int i = 0; i < categoriesArray.length(); i++) {
                JSONObject categoryObject = categoriesArray.getJSONObject(i);
                categoryId = categoryObject.getInt("id");
                categoryName = categoryObject.getString("name");
                JSONArray productArray = categoryObject.getJSONArray("products");
                for (int j = 0; j < productArray.length(); j++) {
                    JSONObject productObject = productArray.getJSONObject(j);
                    productId = productObject.getInt("id");
                    productName = productObject.getString("name");
                    productDateAdded = productObject.getString("date_added");

                    JSONObject productTaxObject = productObject.getJSONObject("tax");
                    productTaxName = productTaxObject.getString("name");
                    productTaxValue = productTaxObject.getDouble("value");

                    // Insert categories into db
                    Category category = new Category();
                    category.setCategoryId(categoryId);
                    category.setCategoryName(categoryName);
                    category.setDate(productDateAdded);
                    category.setProductId(productId);
                    category.setProductName(productName);
                    category.setTaxName(productTaxName);
                    category.setTaxValue(productTaxValue);
                    masterDatabase.categoryDaoAccess().insertOnlySingleCategory(category);

                    JSONArray variantsArray = productObject.getJSONArray("variants");
                    for (int k = 0; k < variantsArray.length(); k++) {
                        JSONObject variantsObject = variantsArray.getJSONObject(k);
                        variantId = variantsObject.getInt("id");
                        variantColor = variantsObject.getString("color");
                        variantPrice = variantsObject.getInt("price");
                        String size = variantsObject.getString("size");
                        if (size.equals("null"))
                            variantSize = 0;
                        else
                            variantSize = Integer.parseInt(size);

                        // Insert variants into db
                        Variants variants = new Variants();
                        variants.setCategoryId(categoryId);
                        variants.setProductId(productId);
                        variants.setVariantId(variantId);
                        variants.setColor(variantColor);
                        variants.setPrice(variantPrice);
                        variants.setSize(variantSize);
                        masterDatabase.variantsDaoAccess().insertOnlySingleVariant(variants);
                    }
                }

                JSONArray childCategoriesArray = categoryObject.getJSONArray("child_categories");
                String appendedCategories = "";
                for (int j = 0; j < childCategoriesArray.length(); j++) {
                    appendedCategories += childCategoriesArray.getInt(j) + ",";
                }

                // Insert categories into db
                if (!appendedCategories.equals("")) {
                    Category category = new Category();
                    category.setCategoryId(categoryId);
                    category.setCategoryName(categoryName);
                    category.setChildCategoryId(appendedCategories.substring(0, appendedCategories.length() - 1));
                    masterDatabase.categoryDaoAccess().insertOnlySingleCategory(category);
                }
            }

            JSONArray rankingsArray = masterObject.getJSONArray("rankings");
            for (int i = 0; i < rankingsArray.length(); i++) {
                JSONObject rankingsObject = rankingsArray.getJSONObject(i);
                rankingName = rankingsObject.getString("ranking");
                JSONArray rankingsProductsArray = rankingsObject.getJSONArray("products");
                for (int j = 0; j < rankingsProductsArray.length(); j++) {
                    JSONObject rankingsProductObject = rankingsProductsArray.getJSONObject(j);
                    productId = rankingsProductObject.getInt("id");
                    switch (i) {
                        case 0:
                            rankingCount = rankingsProductObject.getInt("view_count");
                            break;
                        case 1:
                            rankingCount = rankingsProductObject.getInt("order_count");
                            break;
                        case 2:
                            rankingCount = rankingsProductObject.getInt("shares");
                            break;
                        default:
                            rankingCount = 0;
                    }

                    Rankings rankings = new Rankings();
                    rankings.setRankingName(rankingName);
                    rankings.setProductId(productId);
                    rankings.setRankingCount(rankingCount);
                    masterDatabase.rankingsDaoAccess().insertOnlySingleRanking(rankings);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        masterDatabase.close();
    }
}
