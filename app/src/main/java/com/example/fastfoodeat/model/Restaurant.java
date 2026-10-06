package com.example.fastfoodeat.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;
import java.util.Map;

public class Restaurant implements Parcelable {

    public String name;
    public String address;
    public int delivery_charge;
    public String image;
    public Map<String, String> hours;
    public List<MenuItem> menus;

    public Restaurant() {
    }

    protected Restaurant(Parcel in) {
        name = in.readString();
        address = in.readString();
        delivery_charge = in.readInt();
        image = in.readString();
        menus = in.createTypedArrayList(MenuItem.CREATOR);
    }

    public static final Creator<Restaurant> CREATOR = new Creator<Restaurant>() {
        @Override
        public Restaurant createFromParcel(Parcel in) {
            return new Restaurant(in);
        }

        @Override
        public Restaurant[] newArray(int size) {
            return new Restaurant[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(address);
        dest.writeInt(delivery_charge);
        dest.writeString(image);
        dest.writeTypedList(menus);
    }
}