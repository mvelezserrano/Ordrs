package com.mixi.ordrs.Model;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class DishFetchr {

    private static final String DISH_URL = "https://dl.dropboxusercontent.com/u/4539692/dishes2.json";
    private static final String TAG = "DishFetchr";

    public byte[] getUrlBytes(String urlSpec) throws IOException {
        URL url = new URL(urlSpec);
        HttpURLConnection connection = (HttpURLConnection)url.openConnection();
        try {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            InputStream in = connection.getInputStream();
            if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                throw new IOException(connection.getResponseMessage() +
                        ": with " +
                        urlSpec);
            }
            int bytesRead = 0;
            byte[] buffer = new byte[1024];
            while ((bytesRead = in.read(buffer)) > 0) {
                out.write(buffer, 0, bytesRead);
            }
            out.close();
            return out.toByteArray();
        } finally {
            connection.disconnect();
        }
    }
    public String getUrlString(String urlSpec) throws IOException {
        return new String(getUrlBytes(urlSpec));
    }

    public List<Dish> fetchDishes() {

        List<Dish> dishes = new ArrayList<>();

        try {
            String jsonString = getUrlString(DISH_URL);
            //Log.i(TAG, "Received JSON: " + jsonString);
            JSONObject jsonBody = new JSONObject(jsonString);
            parseDishes(dishes, jsonBody);
        } catch (JSONException je) {
            Log.e(TAG, "Failed to parse JSON", je);
        } catch (IOException ioe) {
            Log.e(TAG, "Failed to fetch dishes", ioe);
        }

        return dishes;
    }

    private void parseDishes(List<Dish> dishes, JSONObject jsonBody) throws IOException, JSONException {

        JSONArray dishJsonArray = jsonBody.getJSONArray("dishes");
        for (int i = 0; i < dishJsonArray.length(); i++) {
            JSONObject dishJsonObject = dishJsonArray.getJSONObject(i);

            Dish dish = new Dish();
            dish.setId(Integer.parseInt(dishJsonObject.getString("_id")));
            dish.setName(dishJsonObject.getString("name"));
            dish.setPrice(Double.parseDouble(dishJsonObject.getString("price")));
            dish.setImageUrl(dishJsonObject.getString("picture"));

            List<String> ingredientsList = new ArrayList<>();
            JSONArray ingredientsJsonArray = dishJsonObject.getJSONArray("ingredients");
            for (int j = 0; j < ingredientsJsonArray.length(); j++) {
                ingredientsList.add(ingredientsJsonArray.getString(j));
            }
            dish.setIngredients(ingredientsList);

            List<Allergen> allergensList = new ArrayList<>();
            JSONArray allergensJsonArray = dishJsonObject.getJSONArray("allergens");
            for (int k = 0; k < allergensJsonArray.length(); k++) {
                JSONObject allergenJsonObject = allergensJsonArray.getJSONObject(k);

                Allergen allergen = new Allergen();
                allergen.setId(Integer.parseInt(allergenJsonObject.getString("id")));
                allergen.setName(allergenJsonObject.getString("name"));
                allergensList.add(allergen);
            }
            dish.setAllergens(allergensList);

            dish.setRequest("");

            dishes.add(dish);
        }
    }
}






















