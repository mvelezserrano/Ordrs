package com.mixi.ordrs.Fragment;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.mixi.ordrs.Model.Allergen;
import com.mixi.ordrs.Model.Dish;
import com.mixi.ordrs.Model.MenuList;
import com.mixi.ordrs.R;

import java.util.List;

public class DishFragment extends Fragment {

    private static final String ARG_CUSTOMER_REQUEST = "customer_request";
    private static final String ARG_DISH_ID = "dish_id";

    public static final String EXTRA_SELECTED_DISH_ID = "selected_dish_id";
    public static final String EXTRA_REQUESTS = "request";

    private static boolean newDish=true;
    private List<Dish> mDishes;
    private Dish mDish;

    private TextView mNameTextView;
    private TextView mPriceTextView;
    private ImageView mDishImageView;
    private TextView mIngredientsTextView;
    private TextView mAllergensTextView;
    private EditText mRequestField;
    private Button mAddToOrderButton;

    private String mCustomerRequest="";

    public static DishFragment newInstance(int dishId) {
        Bundle args = new Bundle();
        args.putInt(ARG_DISH_ID, dishId);

        newDish=true;

        DishFragment fragment = new DishFragment();
        fragment.setArguments(args);

        return fragment;
    }

    public static DishFragment newInstance(String request, int dishId) {

        Bundle args = new Bundle();
        args.putInt(ARG_DISH_ID, dishId);
        args.putString(ARG_CUSTOMER_REQUEST, request);

        newDish=false;

        DishFragment fragment = new DishFragment();
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        int dishId = getArguments().getInt(ARG_DISH_ID, 0);
        mDish = MenuList.get(getActivity()).getDish(dishId);
        if (!newDish) {
            mCustomerRequest = getArguments().getString(ARG_CUSTOMER_REQUEST);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_dish, container, false);

        mNameTextView = (TextView) v.findViewById(R.id.dish_name);
        mNameTextView.setText(mDish.getName());

        mPriceTextView = (TextView) v.findViewById(R.id.dish_price);
        mPriceTextView.setText(Double.toString(mDish.getPrice()) + " €");

        mIngredientsTextView = (TextView) v.findViewById(R.id.dish_ingredients);
        StringBuilder ingredientsListBuilder = new StringBuilder();
        List<String> ingredientsList = mDish.getIngredients();
        int i=1;
        for (String ingredient: ingredientsList) {
            ingredientsListBuilder.append(ingredient);
            if (i++ == ingredientsList.size()) {
                ingredientsListBuilder.append(".");
            } else {
                ingredientsListBuilder.append(", ");
            }
        }
        mIngredientsTextView.setText(ingredientsListBuilder.toString());

        mAllergensTextView = (TextView) v.findViewById(R.id.dish_allergens);
        StringBuilder allergenListBuilder = new StringBuilder();
        List<Allergen> allergenList = mDish.getAllergens();
        i=1;
        for (Allergen allergen: allergenList) {
            allergenListBuilder.append(allergen.getName());
            if (i++ == allergenList.size()) {
                allergenListBuilder.append(".");
            } else {
                allergenListBuilder.append(", ");
            }
        }
        mAllergensTextView.setText(allergenListBuilder.toString());


        mRequestField = (EditText) v.findViewById(R.id.dish_customer_request);
        mRequestField.setText(mCustomerRequest);
        mRequestField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // This space intentionally left blank
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mCustomerRequest=s.toString();
            }

            @Override
            public void afterTextChanged(Editable s) {
                // This one too
            }
        });

        mAddToOrderButton = (Button) v.findViewById(R.id.add_dish_to_order);
        if (!newDish) {
            mAddToOrderButton.setBackgroundColor(Color.rgb(231, 76, 60));
            mAddToOrderButton.setText(R.string.remove_from_order_button);
        } else {
            mAddToOrderButton.setBackgroundColor(Color.rgb(39, 174, 96));
            mAddToOrderButton.setText(R.string.add_to_order_button);
        }
        mAddToOrderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                returnResult();
            }
        });

        return v;
    }

    public void returnResult() {
        Intent intent = new Intent();
        intent.putExtra(EXTRA_SELECTED_DISH_ID, mDish.getId());
        intent.putExtra(EXTRA_REQUESTS, mCustomerRequest);
        getActivity().setResult(Activity.RESULT_OK, intent);
        getActivity().finish();
    }
}
