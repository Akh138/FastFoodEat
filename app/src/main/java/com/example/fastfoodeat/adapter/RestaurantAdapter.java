package com.example.fastfoodeat.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.fastfoodeat.R;
import com.example.fastfoodeat.model.Restaurant;

import java.util.List;

public class RestaurantAdapter extends RecyclerView.Adapter<RestaurantAdapter.RestaurantViewHolder> {

    private final List<Restaurant> restaurants;
    private final OnRestaurantClickListener listener;

    // Interface pour gérer le clic
    public interface OnRestaurantClickListener {
        void onRestaurantClick(Restaurant restaurant);
    }

    public RestaurantAdapter(List<Restaurant> restaurants, OnRestaurantClickListener listener) {
        this.restaurants = restaurants;
        this.listener = listener;
    }

    @NonNull
    @Override
    public RestaurantViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_restaurant, parent, false);
        return new RestaurantViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RestaurantViewHolder holder, int position) {
        Restaurant restaurant = restaurants.get(position);

        holder.textName.setText(restaurant.name);
        holder.textAddress.setText(restaurant.address);
        holder.textDelivery.setText("Delivery $" + restaurant.delivery_charge);

        // Charger l'image depuis l'URL
        Glide.with(holder.itemView.getContext())
                .load(restaurant.image)
                .into(holder.imageRestaurant);

        // Gérer le clic sur le restaurant
        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onRestaurantClick(restaurant);
            }
        });
    }

    @Override
    public int getItemCount() {
        return restaurants != null ? restaurants.size() : 0;
    }

    static class RestaurantViewHolder extends RecyclerView.ViewHolder {

        ImageView imageRestaurant;
        TextView textName, textAddress, textDelivery;

        public RestaurantViewHolder(@NonNull View itemView) {
            super(itemView);

            imageRestaurant = itemView.findViewById(R.id.imageRestaurant);
            textName = itemView.findViewById(R.id.textName);
            textAddress = itemView.findViewById(R.id.textAddress);
            textDelivery = itemView.findViewById(R.id.textDelivery);
        }
    }
}