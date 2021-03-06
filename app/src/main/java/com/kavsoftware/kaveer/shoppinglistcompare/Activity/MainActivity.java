package com.kavsoftware.kaveer.shoppinglistcompare.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.kavsoftware.kaveer.shoppinglistcompare.Fragment.ListNameAddFragment;
import com.kavsoftware.kaveer.shoppinglistcompare.Fragment.ListNameFragment;
import com.kavsoftware.kaveer.shoppinglistcompare.R;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setting from database
        SetFullScreen();
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        LoadDefaultFragment();
        LoadNavigationDrawer(toolbar);
    }

    private void SetFullScreen() {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
    }

    private void LoadNavigationDrawer(Toolbar toolbar) {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

    }

    private void LoadDefaultFragment() {
        ListNameFragment fragment = new ListNameFragment();
        android.support.v4.app.FragmentTransaction fmTransaction = getSupportFragmentManager().beginTransaction();
        fmTransaction.replace(R.id.MainFrameLayout, fragment);
        fmTransaction.commit();
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {

            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);

            Toast.makeText(this, "Press the back button once again to close the application.", Toast.LENGTH_SHORT).show();

          //  super.onBackPressed();

        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
       // getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }



    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_add_list) {
            ListNameAddFragment fragment = new ListNameAddFragment();
            android.support.v4.app.FragmentTransaction fmTransaction = getSupportFragmentManager().beginTransaction();
            fmTransaction.replace(R.id.MainFrameLayout, fragment);
            fmTransaction.commit();
        } else if (id == R.id.nav_view_history) {
            ListNameFragment fragment = new ListNameFragment();
            android.support.v4.app.FragmentTransaction fmTransaction = getSupportFragmentManager().beginTransaction();
            fmTransaction.replace(R.id.MainFrameLayout, fragment);
            fmTransaction.commit();
        }



        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
