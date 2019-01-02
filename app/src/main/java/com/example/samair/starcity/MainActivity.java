package com.example.samair.starcity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
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

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener , View.OnClickListener{

    ViewPager viewPager;
    private CardView search_card, storefinder_card, mallmaps_card, mallinfo_cards, lookcontact_card, directory_card;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        search_card = findViewById(R.id.search_card);
        storefinder_card = findViewById(R.id.storefinder_card);
        mallmaps_card = findViewById(R.id.mallmaps_card);
        mallinfo_cards = findViewById(R.id.mallinfo_cards);
        lookcontact_card = findViewById(R.id.lookcontact_card);
        directory_card = findViewById(R.id.directory_card);

        //add click listeners to cards
//        statsCard.setOnClickListener(this);
        search_card.setOnClickListener(this);
        storefinder_card.setOnClickListener(this);
        mallmaps_card.setOnClickListener(this);
        mallinfo_cards.setOnClickListener(this);
        lookcontact_card.setOnClickListener(this);
        directory_card.setOnClickListener(this);


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

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
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
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_login) {
            Intent intentLogin = new Intent(this, login.class);
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

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
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
            case R.id.lookcontact_card:
                i = new Intent(this, LookContacts.class);
                startActivity(i);
                break;
            case R.id.directory_card:
                i = new Intent(this, Directory.class);
                startActivity(i);
                break;
            default:break;
        }
    }
}
