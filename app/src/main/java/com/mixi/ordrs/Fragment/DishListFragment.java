package com.mixi.ordrs.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mixi.ordrs.Model.Allergen;
import com.mixi.ordrs.Model.Dish;
import com.mixi.ordrs.Model.Table;
import com.mixi.ordrs.Model.TableSet;
import com.mixi.ordrs.R;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

public class DishListFragment extends Fragment {

    private static final String ARG_TABLE_ID = "table_id";

    //private MenuList mMenuList;
    private Table mTable;
    private RecyclerView mTableRecyclerView;
    private DishAdapter mAdapter;

    //private FloatingActionButton mAddCityButton;

    public static DishListFragment newInstance (UUID tableId) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_TABLE_ID, tableId);

        DishListFragment fragment = new DishListFragment();
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        UUID tableId = (UUID) getArguments().getSerializable(ARG_TABLE_ID);
        mTable = TableSet.get(getActivity()).getTable(tableId);


    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_table_list, container, false);
        mTableRecyclerView = (RecyclerView) view.findViewById(R.id.table_recycler_view);
        mTableRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        //mMenuList = MenuList.get(getActivity());

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

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        /*inflater.inflate(R.menu.fragment_crime_list, menu);

        MenuItem subtitleItem = menu.findItem(R.id.menu_item_show_subtitle);
        if (mSubtitleVisible) {
            subtitleItem.setTitle(R.string.hide_subtitle);
        } else {
            subtitleItem.setTitle(R.string.show_subtitle);
        }*/
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        /*switch (item.getItemId()) {
            case R.id.menu_item_new_crime:
                Crime crime = new Crime();
                CrimeLab.get(getActivity()).addCrime(crime);
                Intent intent = CrimePagerActivity.newIntent(getActivity(), crime.getId());
                startActivity(intent);
                return true;

            case R.id.menu_item_show_subtitle:
                mSubtitleVisible = !mSubtitleVisible;
                getActivity().invalidateOptionsMenu();
                updateSubtitle();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }*/
        return super.onOptionsItemSelected(item);
    }

    public void addDish() {
        mTable.addDish(createFakeDish());
        updateUI();
    }

    public void updateUI() {
        List<Dish> dishes = mTable.getDishes();

        if (mAdapter == null) {
            mAdapter = new DishAdapter(dishes);
            mTableRecyclerView.setAdapter(mAdapter);
        } else {
            mAdapter.notifyDataSetChanged();
        }

        /*
        mAddCityButton = (FloatingActionButton) getActivity().findViewById(R.id.add_button);
        if (!mMenuList.isMenuDownloaded()) {
            mAddCityButton.setVisibility(View.INVISIBLE);
        } else {
            mAddCityButton.setVisibility(View.VISIBLE);
        }*/

        //updateSubtitle();
    }
    
    private Dish createFakeDish() {

        int dishNumber = mTable.getDishes().size()+1;
        List<String> ingredients = new ArrayList<>();
        for (int i = 0; i < ThreadLocalRandom.current().nextInt(3, 7 + 1); i++) {
            ingredients.add("Ingrediente "+Integer.toString(i));
        }

        List<Allergen> allergens = new ArrayList<>();
        for (int i = 0; i < ThreadLocalRandom.current().nextInt(0, 3 + 1); i++) {
            Allergen allergen = new Allergen(i, "Allergen "+Integer.toString(i));
            allergens.add(allergen);
        }

        ingredients.add("Ingrediente 1");
        Dish dish = new Dish(dishNumber,
                "Plato "+Integer.toString(dishNumber),
                15.0, "url", ingredients, allergens);

        return dish;
    }

    private class DishHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private Dish mDish;

        private TextView mNameTextView;

        public DishHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            mNameTextView = (TextView) itemView;
        }

        public void bindDish(Dish dish) {
            mDish = dish;
            mNameTextView.setText(mDish.getName());
        }

        @Override
        public void onClick(View v) {
            //Intent intent = new Intent(getActivity(), MenuListActivity.class);
            //startActivity(intent);
        }
    }

    private class DishAdapter extends RecyclerView.Adapter<DishHolder> {

        private List<Dish> mDishes;

        public DishAdapter(List<Dish> dishes) {
            mDishes = dishes;
        }

        @Override
        public DishHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            View view = layoutInflater.inflate(android.R.layout.simple_list_item_1, parent, false);
            return new DishHolder(view);
        }

        @Override
        public void onBindViewHolder(DishHolder holder, int position) {
            Dish dish = mDishes.get(position);
            holder.bindDish(dish);
        }

        @Override
        public int getItemCount() {
            return mDishes.size();
        }
    }
}
