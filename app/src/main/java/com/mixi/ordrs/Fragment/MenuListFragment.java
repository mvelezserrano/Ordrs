package com.mixi.ordrs.Fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mixi.ordrs.Activity.MenuDishPagerActivity;
import com.mixi.ordrs.Model.Allergen;
import com.mixi.ordrs.Model.Dish;
import com.mixi.ordrs.Model.MenuList;
import com.mixi.ordrs.R;

import java.util.List;

public class MenuListFragment extends Fragment {

    private static final int REQUEST_DISH = 1;

    private RecyclerView mMenuListRecyclerView;
    private MenuListAdapter mAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recyclerview_list, container, false);
        mMenuListRecyclerView = (RecyclerView) view.findViewById(R.id.list_recycler_view);
        mMenuListRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        FloatingActionButton addButton = (FloatingActionButton) getActivity().findViewById(R.id.add_button);
        addButton.setVisibility(View.INVISIBLE);

        updateUI();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        updateUI();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //outState.putBoolean(SAVED_SUBTITLE_VISIBLE, mSubtitleVisible);
    }

    private void updateUI() {
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.getSupportActionBar().setTitle(R.string.menu_list_action_bar_title);

        MenuList menuList = MenuList.get(getActivity());
        List<Dish> menuDishes = menuList.getDishes();

        if (mAdapter == null) {
            mAdapter = new MenuListAdapter(menuDishes);
            mMenuListRecyclerView.setAdapter(mAdapter);
        } else {
            mAdapter.notifyDataSetChanged();
        }
    }

    private class MenuListHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private Dish mDish;

        private TextView mDishNameTextView;
        private TextView mDishAllergensTextView;
        private TextView mDishPriceTextView;

        public MenuListHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            mDishNameTextView = (TextView) itemView.findViewById(R.id.list_item_dish_name);
            mDishAllergensTextView = (TextView) itemView.findViewById(R.id.list_item_dish_allergens);
            mDishPriceTextView = (TextView) itemView.findViewById(R.id.list_item_dish_price);
        }

        public void bindDish(Dish dish) {
            mDish = dish;
            mDishNameTextView.setText(mDish.getName());
            StringBuilder allergenListBuilder = new StringBuilder();
            List<Allergen> allergenList = mDish.getAllergens();
            int i=1;
            for (Allergen allergen: allergenList) {
                allergenListBuilder.append(allergen.getName());
                if (i++ == allergenList.size()) {
                    allergenListBuilder.append(".");
                } else {
                    allergenListBuilder.append(", ");
                }
            }
            mDishAllergensTextView.setText(allergenListBuilder.toString());
            mDishPriceTextView.setText(Double.toString(mDish.getPrice()) + " €");
        }

        @Override
        public void onClick(View v) {
            Intent intent = MenuDishPagerActivity.newIntent(getActivity(), mDish.getId());
            startActivityForResult(intent, REQUEST_DISH);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            System.out.println("Añadimos en MenuListFragment");
            getActivity().setResult(Activity.RESULT_OK, data);
            getActivity().finish();
        }
    }

    private class MenuListAdapter extends RecyclerView.Adapter<MenuListHolder> {

        private List<Dish> mMenuDishes;

        public MenuListAdapter(List<Dish> menuDishes) {
            mMenuDishes = menuDishes;
        }

        @Override
        public MenuListHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            View view = layoutInflater.inflate(R.layout.list_item_dish, parent, false);
            return new MenuListHolder(view);
        }

        @Override
        public void onBindViewHolder(MenuListHolder holder, int position) {
            Dish dish = mMenuDishes.get(position);
            holder.bindDish(dish);
        }

        @Override
        public int getItemCount() {
            return mMenuDishes.size();
        }
    }
}
