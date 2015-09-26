package com.mixi.ordrs.Model;

import java.util.List;

public class Dish {

    private int mId;
    private String mName;
    private double mPrice;
    private String mImageUrl;
    private List<String> mIngredients;
    private List<Allergen> mAllergens;
    private String mRequest;

    public Dish() {};

    public Dish(int id, String name, double price, String imageUrl, List<String> ingredients, List<Allergen> allergens) {
        mId = id;
        mName = name;
        mPrice = price;
        mImageUrl = imageUrl;
        mIngredients = ingredients;
        mAllergens = allergens;
    }

    public void setId(int id) {
        mId = id;
    }

    public void setName(String name) {
        mName = name;
    }

    public void setPrice(double price) {
        mPrice = price;
    }

    public void setImageUrl(String imageUrl) {
        mImageUrl = imageUrl;
    }

    public void setIngredients(List<String> ingredients) {
        mIngredients = ingredients;
    }

    public void setAllergens(List<Allergen> allergens) {
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

    public double getPrice() {
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
