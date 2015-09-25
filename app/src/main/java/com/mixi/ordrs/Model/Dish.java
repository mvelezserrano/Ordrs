package com.mixi.ordrs.Model;

import java.util.List;

public class Dish {

    private int mId;
    private String mName;
    private float mPrice;
    private String mImageUrl;
    private List<String> mIngredients;
    private List<Allergen> mAllergens;
    private String mRequest;

    public Dish(int id, String name, float price, String imageUrl, List<String> ingredients, List<Allergen> allergens) {
        mId = id;
        mName = name;
        mPrice = price;
        mImageUrl = imageUrl;
        mIngredients = ingredients;
        mAllergens = allergens;
    }

    public void setRequest(String request) {
        mRequest = request;
    }

    public int getId() {
        return mId;
    }

    public String getName() {
        return mName;
    }

    public float getPrice() {
        return mPrice;
    }

    public String getImageUrl() {
        return mImageUrl;
    }

    public List<String> getIngredients() {
        return mIngredients;
    }

    public List<Allergen> getAllergens() {
        return mAllergens;
    }

    public String getRequest() {
        return mRequest;
    }
}
