package com.example.fastfoodeat.ui.menu;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fastfoodeat.ui.order.PlaceYourOrderActivity;
import com.example.fastfoodeat.R;
import com.example.fastfoodeat.adapter.MenuAdapter;
import com.example.fastfoodeat.model.MenuItem;
import com.example.fastfoodeat.model.Restaurant;

import java.util.ArrayList;
import java.util.List;

public class RestaurantMenuActivity extends AppCompatActivity implements MenuAdapter.OnMenuClickListener {

    private TextView buttonCheckout;
    private List<MenuItem> itemsInCartList = new ArrayList<>();
    private int totalItemInCart = 0;
    private Restaurant restaurant;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_restaurant_menu);

        Toolbar toolbar = findViewById(R.id.toolbarMenu);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        restaurant = getIntent().getParcelableExtra("restaurant");

        TextView textRestaurantName = findViewById(R.id.textRestaurantName);
        RecyclerView recyclerMenus = findViewById(R.id.recyclerMenus);
        buttonCheckout = findViewById(R.id.buttonCheckout);

        if (restaurant != null) {
            textRestaurantName.setText(restaurant.name);

            recyclerMenus.setLayoutManager(new GridLayoutManager(this, 2));
            MenuAdapter menuAdapter = new MenuAdapter(restaurant.menus, this);
            recyclerMenus.setAdapter(menuAdapter);
        }

        buttonCheckout.setOnClickListener(v -> {
            if (restaurant != null && itemsInCartList.size() > 0) {

                restaurant.menus = itemsInCartList;

                Intent intent = new Intent(RestaurantMenuActivity.this, PlaceYourOrderActivity.class);
                intent.putExtra("restaurant", restaurant);
                startActivityForResult(intent, 1000);
            }
        });
    }

    @Override
    public void onAddToCartClick(MenuItem menuItem) {

        if (!itemsInCartList.contains(menuItem)) {
            itemsInCartList.add(menuItem);
        }

        updateCheckoutButton();
    }

    @Override
    public void onUpdateCartClick(MenuItem menuItem) {
        updateCheckoutButton();
    }

    @Override
    public void onRemoveFromCartClick(MenuItem menuItem) {
        itemsInCartList.remove(menuItem);
        updateCheckoutButton();
    }

    private void updateCheckoutButton() {

        totalItemInCart = 0;

        for (MenuItem item : itemsInCartList) {
            totalItemInCart = totalItemInCart + item.totalInCart;
        }

        if (totalItemInCart > 0) {

            if (totalItemInCart == 1) {
                buttonCheckout.setText("Checkout (1) item");
            } else {
                buttonCheckout.setText("Checkout (" + totalItemInCart + ") items");
            }

        } else {
            buttonCheckout.setText("Checkout");
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1000 && resultCode == RESULT_OK) {
            finish();
        }
    }
}