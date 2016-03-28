package com.kuo.mychart;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();

        ChartListFragment chartListFragment = new ChartListFragment();
        fragmentTransaction.replace(R.id.frame_layout, chartListFragment, "chartListFragment");
        fragmentTransaction.commit();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        int stackCount = getSupportFragmentManager().getBackStackEntryCount();

        if(id == android.R.id.home && stackCount > 0) {
            getSupportFragmentManager().popBackStack();
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
            getSupportActionBar().setSubtitle("");
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setSubtitle("");
    }
}
