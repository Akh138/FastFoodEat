package com.example.fastfoodeat.model;

import android.os.Parcel;
import android.os.Parcelable;

public class MenuItem implements Parcelable {

    public String name;
    public double price;
    public String url;
    public int totalInCart = 0;

    public MenuItem() {
    }

    protected MenuItem(Parcel in) {
        name = in.readString();
        price = in.readDouble();
        url = in.readString();
        totalInCart = in.readInt();
    }

    public static final Creator<MenuItem> CREATOR = new Creator<MenuItem>() {
        @Override
        public MenuItem createFromParcel(Parcel in) {
            return new MenuItem(in);
        }

        @Override
        public MenuItem[] newArray(int size) {
            return new MenuItem[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeDouble(price);
        dest.writeString(url);
        dest.writeInt(totalInCart);
    }
}