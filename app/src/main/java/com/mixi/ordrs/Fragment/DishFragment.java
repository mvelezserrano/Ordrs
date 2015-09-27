package com.mixi.ordrs.Fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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

    private static final String ARG_DISH_ID = "dish_id";

    private Dish mDish;

    private TextView mNameTextView;
    private TextView mPriceTextView;
    private ImageView mDishImageView;
    private TextView mIngredientsTextView;
    private TextView mAllergensTextView;
    private EditText mRequestField;
    private Button mAddToOrderButton;

    public static DishFragment newInstance(int dishId) {
        Bundle args = new Bundle();
        args.putInt(ARG_DISH_ID, dishId);

        DishFragment fragment = new DishFragment();
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        int dishId = getArguments().getInt(ARG_DISH_ID, 0);
        mDish = MenuList.get(getActivity()).getDish(dishId);
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
        /*mTitleField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // This space intentionally left blank
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mCrime.setTitle(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
                // This one too
            }
        });*/

        mAddToOrderButton = (Button) v.findViewById(R.id.add_dish_to_order);
        mAddToOrderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*FragmentManager manager = getFragmentManager();
                DatePickerFragment dialog = DatePickerFragment.newInstance(mCrime.getDate());
                dialog.setTargetFragment(CrimeFragment.this, REQUEST_DATE);
                dialog.show(manager, DIALOG_DATE);*/
            }
        });

        return v;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK) {
            return;
        }

        /*if (requestCode == REQUEST_DATE) {
            Date date = (Date) data.getSerializableExtra(DatePickerFragment.EXTRA_DATE);
            mCrime.setDate(date);
            updateDate();
        }*/
    }
}
