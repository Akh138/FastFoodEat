package com.example.fastfoodeat.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.fastfoodeat.model.MenuItem;
import com.example.fastfoodeat.R;

import java.util.List;

public class MenuAdapter extends RecyclerView.Adapter<MenuAdapter.MenuViewHolder> {

    private final List<MenuItem> menuItems;
    private final OnMenuClickListener listener;

    public interface OnMenuClickListener {
        void onAddToCartClick(MenuItem menuItem);
        void onUpdateCartClick(MenuItem menuItem);
        void onRemoveFromCartClick(MenuItem menuItem);
    }

    public MenuAdapter(List<MenuItem> menuItems, OnMenuClickListener listener) {
        this.menuItems = menuItems;
        this.listener = listener;
    }

    @NonNull
    @Override
    public MenuViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_menu, parent, false);
        return new MenuViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MenuViewHolder holder, int position) {
        MenuItem menuItem = menuItems.get(position);

        holder.textMenuName.setText(menuItem.name);
        holder.textMenuPrice.setText("$" + menuItem.price);

        Glide.with(holder.itemView.getContext())
                .load(menuItem.url)
                .placeholder(R.drawable.logo_fastfood)
                .error(R.drawable.logo_fastfood)
                .centerCrop()
                .into(holder.imageMenu);

        if (menuItem.totalInCart > 0) {
            holder.addToCartButton.setVisibility(View.GONE);
            holder.addMoreLayout.setVisibility(View.VISIBLE);
            holder.tvCount.setText(String.valueOf(menuItem.totalInCart));
        } else {
            holder.addToCartButton.setVisibility(View.VISIBLE);
            holder.addMoreLayout.setVisibility(View.GONE);
        }

        holder.addToCartButton.setOnClickListener(v -> {
            menuItem.totalInCart = 1;

            holder.addToCartButton.setVisibility(View.GONE);
            holder.addMoreLayout.setVisibility(View.VISIBLE);
            holder.tvCount.setText(String.valueOf(menuItem.totalInCart));

            if (listener != null) {
                listener.onAddToCartClick(menuItem);
            }
        });
        // le +
        holder.imageAddOne.setOnClickListener(v -> {
            menuItem.totalInCart = menuItem.totalInCart + 1;
            holder.tvCount.setText(String.valueOf(menuItem.totalInCart));

            if (listener != null) {
                listener.onUpdateCartClick(menuItem);
            }
        });
        holder.imageMinus.setOnClickListener(v -> {
            menuItem.totalInCart = menuItem.totalInCart - 1;

            if (menuItem.totalInCart > 0) {
                holder.tvCount.setText(String.valueOf(menuItem.totalInCart));

                if (listener != null) {
                    listener.onUpdateCartClick(menuItem);
                }

            } else {
                menuItem.totalInCart = 0;
                holder.addMoreLayout.setVisibility(View.GONE);
                holder.addToCartButton.setVisibility(View.VISIBLE);

                if (listener != null) {
                    listener.onRemoveFromCartClick(menuItem);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return menuItems != null ? menuItems.size() : 0;
    }

    static class MenuViewHolder extends RecyclerView.ViewHolder {

        ImageView imageMenu;
        TextView textMenuName;
        TextView textMenuPrice;
        TextView addToCartButton;
        ImageView imageMinus;
        ImageView imageAddOne;
        TextView tvCount;
        LinearLayout addMoreLayout;

        public MenuViewHolder(@NonNull View itemView) {
            super(itemView);

            imageMenu = itemView.findViewById(R.id.imageMenu);
            textMenuName = itemView.findViewById(R.id.textMenuName);
            textMenuPrice = itemView.findViewById(R.id.textMenuPrice);
            addToCartButton = itemView.findViewById(R.id.addToCartButton);
            imageMinus = itemView.findViewById(R.id.imageMinus);
            imageAddOne = itemView.findViewById(R.id.imageAddOne);
            tvCount = itemView.findViewById(R.id.tvCount);
            addMoreLayout = itemView.findViewById(R.id.addMoreLayout);
        }
    }
}