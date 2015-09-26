package com.mixi.ordrs.Fragment;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
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

import com.mixi.ordrs.Activity.DishListActivity;
import com.mixi.ordrs.Model.Dish;
import com.mixi.ordrs.Model.DishFetchr;
import com.mixi.ordrs.Model.MenuList;
import com.mixi.ordrs.Model.Table;
import com.mixi.ordrs.Model.TableSet;
import com.mixi.ordrs.R;

import java.util.List;

public class TableListFragment extends Fragment {



    private RecyclerView mTableRecyclerView;
    private TableAdapter mAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        new FetchDishesTask().execute();
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

    public void addTable() {
        TableSet tableSet = TableSet.get(getActivity());
        int tableNumber = tableSet.getTables().size()+1;

        Table table = new Table();
        table.setName("Table number " + Integer.toString(tableNumber));
        tableSet.addTable(table);
        updateUI();
    }

    private void updateUI() {
        TableSet tableSet = TableSet.get(getActivity());
        List<Table> tables = tableSet.getTables();

        if (mAdapter == null) {
            mAdapter = new TableAdapter(tables);
            mTableRecyclerView.setAdapter(mAdapter);
        } else {
            mAdapter.notifyDataSetChanged();
        }

        //updateSubtitle();
    }

    private class FetchDishesTask extends AsyncTask<Void, Void, List<Dish>> {

        @Override
        protected List<Dish> doInBackground(Void... params) {
            return new DishFetchr().fetchDishes();
        }

        @Override
        protected void onPostExecute(List<Dish> dishes) {

            MenuList menuList = MenuList.get(getActivity());
            menuList.setDishes(dishes);

            Handler showAddButton = new Handler();
            showAddButton.postDelayed(new Runnable() {
                @Override
                public void run() {
                    MenuList menuList = MenuList.get(getActivity());
                    menuList.setMenuDownloaded(true);
                }
            }, 5000);
        }
    }

    private class TableHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private Table mTable;

        private TextView mNameTextView;

        public TableHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            mNameTextView = (TextView) itemView;
        }

        public void bindTable(Table table) {
            mTable = table;
            mNameTextView.setText(mTable.getName());
        }

        @Override
        public void onClick(View v) {
            Intent intent = DishListActivity.newIntent(getActivity(), mTable.getId());
            startActivity(intent);
        }
    }

    private class TableAdapter extends RecyclerView.Adapter<TableHolder> {

        private List<Table> mTables;

        public TableAdapter(List<Table> tables) {
            mTables = tables;
        }

        @Override
        public TableHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            //View view = layoutInflater.inflate(R.layout.list_item_crime, parent, false);
            View view = layoutInflater.inflate(android.R.layout.simple_list_item_1, parent, false);
            return new TableHolder(view);
        }

        @Override
        public void onBindViewHolder(TableHolder holder, int position) {
            Table table = mTables.get(position);
            holder.bindTable(table);
        }

        @Override
        public int getItemCount() {
            return mTables.size();
        }
    }
}
