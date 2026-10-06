package com.example.fastfoodeat.ui.order;

import android.app.Dialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.Window;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fastfoodeat.R;
import com.example.fastfoodeat.adapter.OrderMenuAdapter;
import com.example.fastfoodeat.model.MenuItem;
import com.example.fastfoodeat.model.Restaurant;

public class PlaceYourOrderActivity extends AppCompatActivity {

    private double subTotal = 0.0;
    private double deliveryChargeValue = 0.0;
    private TextView textDeliveryCharge;
    private TextView textTotal;
    private Restaurant restaurant;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_place_your_order);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        restaurant = getIntent().getParcelableExtra("restaurant");

        TextView textOrderRestaurantName = findViewById(R.id.textOrderRestaurantName);
        RecyclerView recyclerOrderMenus = findViewById(R.id.recyclerOrderMenus);
        TextView textSubTotal = findViewById(R.id.textSubTotal);
        textDeliveryCharge = findViewById(R.id.textDeliveryCharge);
        textTotal = findViewById(R.id.textTotal);
        TextView buttonPlaceOrder = findViewById(R.id.buttonPlaceOrder);

        EditText editCustomerName = findViewById(R.id.editCustomerName);
        EditText editCardNumber = findViewById(R.id.editCardNumber);
        EditText editExpirationDate = findViewById(R.id.editExpirationDate);
        EditText editCVV = findViewById(R.id.editCVV);

        Switch switchDelivery = findViewById(R.id.switchDelivery);
        TextView textDeliveryMode = findViewById(R.id.textDeliveryMode);

        if (restaurant != null) {
            textOrderRestaurantName.setText(restaurant.name);

            recyclerOrderMenus.setLayoutManager(new LinearLayoutManager(this));
            OrderMenuAdapter orderMenuAdapter = new OrderMenuAdapter(restaurant.menus);
            recyclerOrderMenus.setAdapter(orderMenuAdapter);

            subTotal = 0.0;

            for (MenuItem item : restaurant.menus) {
                subTotal = subTotal + (item.price * item.totalInCart);
            }

            deliveryChargeValue = restaurant.delivery_charge;

            textSubTotal.setText("$" + String.format("%.2f", subTotal));
            textDeliveryCharge.setText("$0.00");
            textTotal.setText("$" + String.format("%.2f", subTotal));
        }

        switchDelivery.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                textDeliveryMode.setText("Mode: Delivery");
                textDeliveryCharge.setText("$" + String.format("%.2f", deliveryChargeValue));
                textTotal.setText("$" + String.format("%.2f", subTotal + deliveryChargeValue));
            } else {
                textDeliveryMode.setText("Mode: Pickup");
                textDeliveryCharge.setText("$0.00");
                textTotal.setText("$" + String.format("%.2f", subTotal));
            }
        });

        buttonPlaceOrder.setOnClickListener(v -> {
            String customerName = editCustomerName.getText().toString().trim();
            String cardNumber = editCardNumber.getText().toString().trim();
            String expirationDate = editExpirationDate.getText().toString().trim();
            String cvv = editCVV.getText().toString().trim();

            if (customerName.isEmpty()) {
                Toast.makeText(this, "Please enter your name", Toast.LENGTH_SHORT).show();
                return;
            }

            if (cardNumber.isEmpty() || expirationDate.isEmpty() || cvv.isEmpty()) {
                Toast.makeText(this, "Please fill payment details", Toast.LENGTH_SHORT).show();
                return;
            }

            showOrderSuccessDialog();
        });
    }

    private void showOrderSuccessDialog() {
        Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_order_success);
        dialog.setCancelable(false);

        TextView textSuccessTitle = dialog.findViewById(R.id.textSuccessTitle);
        TextView textSuccessSubtitle = dialog.findViewById(R.id.textSuccessSubtitle);

        if (restaurant != null) {
            textSuccessTitle.setText("Merci pour votre commande chez " + restaurant.name);
        }

        textSuccessSubtitle.setText("Votre commande est en préparation");

        if (dialog.getWindow() != null) {
            dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        }

        dialog.show();

        ScaleAnimation scaleAnimation = new ScaleAnimation(
                0.8f, 1f,
                0.8f, 1f,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f
        );
        scaleAnimation.setDuration(300);

        AlphaAnimation alphaAnimation = new AlphaAnimation(0f, 1f);
        alphaAnimation.setDuration(300);

        dialog.findViewById(R.id.imageSuccess).startAnimation(scaleAnimation);
        dialog.findViewById(R.id.textSuccessTitle).startAnimation(alphaAnimation);
        dialog.findViewById(R.id.textSuccessSubtitle).startAnimation(alphaAnimation);

        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            dialog.dismiss();
            setResult(RESULT_OK);
            finish();
        }, 2200);
    }
}