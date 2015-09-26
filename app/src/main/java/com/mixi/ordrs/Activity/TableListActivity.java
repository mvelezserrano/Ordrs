package com.mixi.ordrs.Activity;

import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.Toast;

import com.mixi.ordrs.Fragment.TableListFragment;
import com.mixi.ordrs.R;

public class TableListActivity extends SingleFragmentActivity implements TableListFragment.OnMenuListDownloaded {

    private FloatingActionButton mAddTableButton;

    @Override
    protected Fragment createFragment() {
        return new TableListFragment();
    }

    @Override
    protected void addNewElement() {
        Toast toast = Toast.makeText(this, "Añadir Mesa!!!", Toast.LENGTH_SHORT);
        toast.show();
        TableListFragment fragment = (TableListFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_container);
        fragment.addTable();
    }

    @Override
    public void menuListHasDownloaded() {
        Snackbar snackbar = Snackbar.make(
                findViewById(R.id.coordinator),
                R.string.menu_downloaded,
                Snackbar.LENGTH_SHORT);
        snackbar.show();
        mAddTableButton = (FloatingActionButton) findViewById(R.id.add_button);
        mAddTableButton.setVisibility(View.VISIBLE);
    }
}
