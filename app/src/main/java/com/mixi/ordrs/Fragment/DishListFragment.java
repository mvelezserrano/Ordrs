package com.mixi.ordrs.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mixi.ordrs.Activity.DishListActivity;
import com.mixi.ordrs.Activity.TableDishPagerActivity;
import com.mixi.ordrs.Model.Allergen;
import com.mixi.ordrs.Model.Dish;
import com.mixi.ordrs.Model.Dishtable;
import com.mixi.ordrs.Model.MenuList;
import com.mixi.ordrs.Model.Table;
import com.mixi.ordrs.Model.TableSet;
import com.mixi.ordrs.R;

import java.util.List;
import java.util.UUID;

public class DishListFragment extends Fragment {

    private static final String ARG_TABLE_ID = "table_id";
    private static final String DIALOG_BILL = "DialogBill";

    private Table mTable;
    private RecyclerView mTableRecyclerView;
    private DishAdapter mAdapter;

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

        setHasOptionsMenu(true);

        UUID tableId = (UUID) getArguments().getSerializable(ARG_TABLE_ID);
        mTable = TableSet.get(getActivity()).getTable(tableId);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_table_list, container, false);
        mTableRecyclerView = (RecyclerView) view.findViewById(R.id.table_recycler_view);
        mTableRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

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
    public void onPrepareOptionsMenu(Menu menu) {
        List<Dishtable> dishes = mTable.getDishes();
        if (dishes.size() == 0) {
            menu.findItem(R.id.menu_item_get_bill).setEnabled(false);
        } else {
            menu.findItem(R.id.menu_item_get_bill).setEnabled(true);
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fragment_table_dish_list, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_item_get_bill:
                System.out.println("Mostramos la cuenta!!!");
                FragmentManager manager = getFragmentManager();

                List<Dishtable> dishes = mTable.getDishes();
                double billAmount=0;
                for (Dishtable dish: dishes) {
                    billAmount+=dish.getPrice();
                }

                ShowBillFragment dialog = ShowBillFragment.newInstance(billAmount);
                dialog.show(manager, DIALOG_BILL);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void addDish(int dishId, String request) {
        MenuList menuList = MenuList.get(getActivity());
        Dishtable dishtable = new Dishtable(menuList.getDish(dishId));
        dishtable.setRequest(request);
        mTable.addDish(dishtable);
        updateUI();
    }

    public void removeDish(int dishId) {
        mTable.removeDish(dishId);
        updateUI();
    }

    public void updateUI() {

        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.getSupportActionBar().setTitle(mTable.getName());

        List<Dishtable> dishes = mTable.getDishes();

        if (mAdapter == null) {
            mAdapter = new DishAdapter(dishes);
            mTableRecyclerView.setAdapter(mAdapter);
        } else {
            mAdapter.notifyDataSetChanged();
        }

        getActivity().invalidateOptionsMenu();
    }

    private class DishHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private Dish mDish;

        private TextView mDishNameTextView;
        private TextView mDishAllergensTextView;
        private TextView mDishPriceTextView;


        public DishHolder(View itemView) {
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
            Intent intent = TableDishPagerActivity.newIntent(getActivity(), mTable.getId(), mDish.getId());
            getActivity().startActivityForResult(intent, DishListActivity.REQUEST_TABLE_DISH);
        }
    }

    private class DishAdapter extends RecyclerView.Adapter<DishHolder> {

        private List<Dishtable> mDishes;

        public DishAdapter(List<Dishtable> dishes) {
            mDishes = dishes;
        }

        @Override
        public DishHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            View view = layoutInflater.inflate(R.layout.list_item_dish, parent, false);
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
