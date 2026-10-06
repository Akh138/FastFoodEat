package com.example.fastfoodeat.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.fastfoodeat.model.MenuItem;
import com.example.fastfoodeat.R;

import java.util.List;

public class OrderMenuAdapter extends RecyclerView.Adapter<OrderMenuAdapter.OrderMenuViewHolder> {

    private final List<MenuItem> menuItems;

    public OrderMenuAdapter(List<MenuItem> menuItems) {
        this.menuItems = menuItems;
    }

    @NonNull
    @Override
    public OrderMenuViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_order_menu, parent, false);
        return new OrderMenuViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderMenuViewHolder holder, int position) {
        MenuItem menuItem = menuItems.get(position);

        holder.textOrderMenuName.setText(menuItem.name);
        holder.textOrderMenuPrice.setText("Price: $" + menuItem.price);
        holder.textOrderMenuQty.setText("Qty: " + menuItem.totalInCart);

        Glide.with(holder.itemView.getContext())
                .load(menuItem.url)
                .placeholder(R.drawable.logo_fastfood)
                .error(R.drawable.logo_fastfood)
                .centerCrop()
                .into(holder.imageOrderMenu);
    }

    @Override
    public int getItemCount() {
        return menuItems != null ? menuItems.size() : 0;
    }

    static class OrderMenuViewHolder extends RecyclerView.ViewHolder {

        ImageView imageOrderMenu;
        TextView textOrderMenuName;
        TextView textOrderMenuPrice;
        TextView textOrderMenuQty;

        public OrderMenuViewHolder(@NonNull View itemView) {
            super(itemView);

            imageOrderMenu = itemView.findViewById(R.id.imageOrderMenu);
            textOrderMenuName = itemView.findViewById(R.id.textOrderMenuName);
            textOrderMenuPrice = itemView.findViewById(R.id.textOrderMenuPrice);
            textOrderMenuQty = itemView.findViewById(R.id.textOrderMenuQty);
        }
    }
}