package com.example.fastfoodeat.ui.main;

import android.os.Bundle;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fastfoodeat.util.GridSpacingItemDecoration;
import com.example.fastfoodeat.R;
import com.example.fastfoodeat.ui.menu.RestaurantMenuActivity;
import com.example.fastfoodeat.adapter.RestaurantAdapter;
import com.example.fastfoodeat.model.Restaurant;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Arrays;
import java.util.List;
import androidx.appcompat.widget.Toolbar;
import android.content.Intent;
import androidx.recyclerview.widget.GridLayoutManager;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "JSON";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // ✅ Lecture + parsing
        List<Restaurant> restaurants = getRestaurantData();

        // ✅ Logs (tests)
        Log.d(TAG, "Restaurants: " + (restaurants != null ? restaurants.size() : 0));
        if (restaurants != null && !restaurants.isEmpty()) {
            Restaurant first = restaurants.get(0);
            Log.d(TAG, "Premier: " + first.name);
            Log.d(TAG, "Menus premier: " + (first.menus != null ? first.menus.size() : 0));
        }

        // ✅ Brancher le RecyclerView
        RecyclerView recyclerView = findViewById(R.id.recyclerRestaurants);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, 24, true));

        RestaurantAdapter adapter = new RestaurantAdapter(restaurants, restaurant -> {
            Log.d("CLICK", "Restaurant cliqué : " + restaurant.name);

            Intent intent = new Intent(MainActivity.this, RestaurantMenuActivity.class);
            intent.putExtra("restaurant", restaurant);
            startActivity(intent);
        });
        recyclerView.setAdapter(adapter);
    }

    // ✅ Méthode style prof : lit res/raw/restaurants.json puis Gson -> Restaurant[]
    private List<Restaurant> getRestaurantData() {

        InputStream is = getResources().openRawResource(R.raw.restaurants);
        Writer writer = new StringWriter();
        char[] buffer = new char[1024];

        try {
            Reader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            int n;
            while ((n = reader.read(buffer)) != -1) {
                writer.write(buffer, 0, n);
            }
            reader.close();
            is.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        String jsonStr = writer.toString();

        Gson gson = new Gson();
        Restaurant[] restaurantsArray = gson.fromJson(jsonStr, Restaurant[].class);
        return Arrays.asList(restaurantsArray);
    }
}