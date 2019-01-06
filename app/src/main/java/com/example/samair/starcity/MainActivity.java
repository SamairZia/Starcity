package com.example.samair.starcity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.CardView;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.example.samair.starcity.Adapter.ViewPagerAdapter;
import com.example.samair.starcity.Dashboard.Events;
import com.example.samair.starcity.Dashboard.MallInfo;
import com.example.samair.starcity.Dashboard.MallMap;
import com.example.samair.starcity.Dashboard.Promotions;
import com.example.samair.starcity.Dashboard.Search;
import com.example.samair.starcity.Dashboard.StoreFinder;
import com.example.samair.starcity.User.Login;
import com.example.samair.starcity.User.Signup;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener , View.OnClickListener{

    ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        CardView search_card = findViewById(R.id.search_card);
        CardView storefinder_card = findViewById(R.id.storefinder_card);
        CardView mallmaps_card = findViewById(R.id.mallmaps_card);
        CardView mallinfo_cards = findViewById(R.id.mallinfo_cards);
        CardView events_card = findViewById(R.id.events_card);
        CardView promotions_card = findViewById(R.id.promotions_card);

        //add click listeners to cards
//        statsCard.setOnClickListener(this);
        search_card.setOnClickListener(this);
        storefinder_card.setOnClickListener(this);
        mallmaps_card.setOnClickListener(this);
        mallinfo_cards.setOnClickListener(this);
        events_card.setOnClickListener(this);
        promotions_card.setOnClickListener(this);


        viewPager = findViewById(R.id.viewPager);

        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(this);

        viewPager.setAdapter(viewPagerAdapter);

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
//         as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_login) {
            Intent intentLogin = new Intent(this, Login.class);
            startActivity(intentLogin);

        } else if (id == R.id.nav_signup) {
            Intent intentSignUp = new Intent(this, Signup.class);
            startActivity(intentSignUp);

        } else if (id == R.id.nav_about) {
            Intent intentAbout = new Intent(this, About.class);
            startActivity(intentAbout);

        } else if (id == R.id.nav_contact) {
            Intent intentContact = new Intent(this, Contact.class);
            startActivity(intentContact);

       }
// else if (id == R.id.nav_share) {
//
//        } else if (id == R.id.nav_send) {
//
//        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onClick(View view) {
        Intent i;

        switch (view.getId()){
            case R.id.search_card:
                i = new Intent(this, Search.class);
                startActivity(i);
                break;
            case R.id.storefinder_card:
                i = new Intent(this, StoreFinder.class);
                startActivity(i);
                break;
            case R.id.mallmaps_card:
                i = new Intent(this, MallMap.class);
                startActivity(i);
                break;
            case R.id.mallinfo_cards:
                i = new Intent(this, MallInfo.class);
                startActivity(i);
                break;
            case R.id.events_card:
                i = new Intent(this, Promotions.class);
                startActivity(i);
                break;
            case R.id.promotions_card:
                i = new Intent(this, Events.class);
                startActivity(i);
                break;
            default:
                break;
        }
    }
}
