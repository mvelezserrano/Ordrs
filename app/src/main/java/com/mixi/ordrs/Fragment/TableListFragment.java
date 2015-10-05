package com.mixi.ordrs.Fragment;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
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

    OnMenuListDownloaded mCallback;
    private FloatingActionButton mAddTableButton;

    private RecyclerView mTableRecyclerView;
    private TableAdapter mAdapter;

    public interface OnMenuListDownloaded {
        public void menuListHasDownloaded();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception
        try {
            mCallback = (OnMenuListDownloaded) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement OnHeadlineSelectedListener");
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        new FetchDishesTask().execute();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recyclerview_list, container, false);
        mTableRecyclerView = (RecyclerView) view.findViewById(R.id.list_recycler_view);
        mTableRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        mAddTableButton = (FloatingActionButton) getActivity().findViewById(R.id.add_button);
        mAddTableButton.setVisibility(View.INVISIBLE);

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
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
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

        private ProgressDialog mProgressDialog;


        @Override
        protected void onPreExecute() {
            //mContainer.setAlpha(0.0f);

            mProgressDialog = new ProgressDialog(getActivity());
            mProgressDialog.setTitle(R.string.downloading_dishes);
            mProgressDialog.show();
        }

        @Override
        protected List<Dish> doInBackground(Void... params) {
            return new DishFetchr().fetchDishes();
        }

        @Override
        protected void onPostExecute(List<Dish> dishes) {
            try {
                mProgressDialog.dismiss();
            } catch (IllegalArgumentException ex) {
                // Android bug can sometimes cause this
            }
            
            if (dishes == null) {
                AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
                alert.setMessage(R.string.error_downloading_dishes);
                alert.setCancelable(false);
                alert.setNegativeButton(R.string.retry_download_button, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        new FetchDishesTask().execute();
                    }
                });
                alert.show();
            } else {
                MenuList menuList = MenuList.get(getActivity());
                menuList.setDishes(dishes);
                menuList.setMenuDownloaded(true);
                mCallback.menuListHasDownloaded();
            }
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
