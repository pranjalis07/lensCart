package com.example.eyewear;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.eyewear.adapters.MyAdapter;
import com.example.eyewear.model.ImageData;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import pl.droidsonroids.gif.GifImageView;

public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    DrawerLayout drawerLayout;
    NavigationView navigationView;

    RecyclerView offerRecycler;
    List<ImageData> list;
    MyAdapter adapter;
    FirebaseFirestore db;

    FirebaseAuth mAuth;
    FirebaseUser currentUser;
    Toolbar toolbar;

    GifImageView eyeglasses,sunglasses,contactlenses,accessories,cart,category,shops;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        drawerLayout = findViewById(R.id.drawerLayout);
        navigationView = findViewById(R.id.navigationView);

        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();

        //-------------------- TOOLBARR -------------------------------
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //-------------------- DRAWER/NAVIGATION VIEW -----------------
        showDrawer();
        //--------------------Internet Connection check ---------------
        internetConnectionCheck();
        //-------------------- Offers recycler view--------------------
        offerRecycler();
        //-------------------- Dashboard Buttons-----------------------
        dashboardButtons();
        //demoRecycler();


    }

    private void dashboardButtons() {

        eyeglasses=findViewById(R.id.eyeglasses);
        sunglasses=findViewById(R.id.sunglasses);
        contactlenses=findViewById(R.id.contactlenses);
        accessories=findViewById(R.id.accessories);
        category=findViewById(R.id.catagory);
        shops=findViewById(R.id.shops);
        cart= findViewById(R.id.cart);

        eyeglasses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(HomeActivity.this, "Eye Glasses", Toast.LENGTH_SHORT).show();
            }
        });
        sunglasses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(HomeActivity.this, "Sun Glasses", Toast.LENGTH_SHORT).show();
            }
        });
        contactlenses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(HomeActivity.this, "Contact Lenses", Toast.LENGTH_SHORT).show();
            }
        });
        accessories.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(HomeActivity.this, "Accessories", Toast.LENGTH_SHORT).show();
            }
        });
        cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(HomeActivity.this, "View Cart", Toast.LENGTH_SHORT).show();
            }
        });
        category.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(HomeActivity.this, "Category", Toast.LENGTH_SHORT).show();
            }
        });
        shops.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(HomeActivity.this, "Stores", Toast.LENGTH_SHORT).show();
            }
        });
    }


    @Override
    public void onBackPressed() {

        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            navigationView.setCheckedItem(R.id.home);
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    private boolean isConnected() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getApplicationContext().getSystemService(CONNECTIVITY_SERVICE);
        return connectivityManager.getActiveNetworkInfo() != null && connectivityManager.getActiveNetworkInfo().isConnectedOrConnecting();
    }

    private void showDrawer() {
        Menu menu = navigationView.getMenu();
        if (currentUser == null) {
            menu.findItem(R.id.profile).setVisible(false);
            menu.findItem(R.id.logOut).setVisible(false);
        } else {
            menu.findItem(R.id.logIn).setVisible(false);
        }

        navigationView.bringToFront();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open_navigation_drawer, R.string.close_navigation_drawer);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);

        navigationView.setCheckedItem(R.id.home);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_menu, menu);
        MenuItem menuItem = menu.findItem(R.id.search);
        SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setQueryHint("Type here to search");
        return true;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.logIn:
                Intent i = new Intent(getApplicationContext(), SignInActivity.class);
                startActivity(i);
                onBackPressed();
                break;

            case R.id.profile:
                Intent in = new Intent(getApplicationContext(), ProfileActivity.class);
                startActivity(in);
                Toast.makeText(this, "Profile", Toast.LENGTH_SHORT).show();
                break;

            case R.id.logOut:
                AlertDialog.Builder builder = new AlertDialog.Builder(HomeActivity.this);
                builder.setTitle("Logout");
                builder.setMessage("Are you sure you want to log out?");
                builder.setCancelable(false);

                builder.setPositiveButton("Logout", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        mAuth.signOut();
                        finish();
                        startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                    }
                });

                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
                builder.create().show();
                break;

            case R.id.home:
                break;

            case R.id.myOrder:
                break;

            case R.id.membership:
                break;

            case R.id.category:
                Toast.makeText(this, "Category", Toast.LENGTH_SHORT).show();
                break;

            case R.id.frameSize:
                Toast.makeText(this, "Frame size", Toast.LENGTH_SHORT).show();
                break;

            case R.id.eyeTest:
                Toast.makeText(this, "Eye test", Toast.LENGTH_SHORT).show();
                break;

            case R.id.aboutUs:
                Toast.makeText(this, "About us", Toast.LENGTH_SHORT).show();
                break;

            case R.id.faq:
                Toast.makeText(this, "FAQs", Toast.LENGTH_SHORT).show();
                break;

            case R.id.rateUS:
                Toast.makeText(this, "Rate us", Toast.LENGTH_SHORT).show();
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + item.getItemId());
        }
        onBackPressed();
        navigationView.setCheckedItem(R.id.home);
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    private void internetConnectionCheck() {
        if (!isConnected()) {
            AlertDialog.Builder builder = new AlertDialog.Builder(HomeActivity.this);
            builder.setCancelable(false);
            builder.setPositiveButton("Retry", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    finish();
                    startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                }
            });
            builder.setView(R.layout.dialog_background);
            builder.create().show();
        }
    }

    private void offerRecycler() {
        db = FirebaseFirestore.getInstance();
        offerRecycler = findViewById(R.id.mainRecycler);
        offerRecycler.setHasFixedSize(true);
        offerRecycler.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
        list = new ArrayList<>();
        adapter = new MyAdapter(this, list);
        offerRecycler.setAdapter(adapter);

        db.collection("Offers")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                ImageData imageData= document.toObject(ImageData.class);
                                list.add(imageData);
                                adapter.notifyDataSetChanged();
                            }
                        } else {
                            Toast.makeText(HomeActivity.this, "Error "+task.getException().toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }



}


