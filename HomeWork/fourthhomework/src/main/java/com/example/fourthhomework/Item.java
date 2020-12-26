package com.example.fourthhomework;

import android.os.Parcel;
import android.os.Parcelable;

public class Item implements Parcelable {
    private String name;
    private String contact;
    private String image;

    public Item(String name, String contact, String image){
        this.name = name;
        this.contact = contact;
        this.image = image;
    }

    protected Item(Parcel in) {
        name = in.readString();
        contact = in.readString();
        image = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(contact);
        dest.writeString(image);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Item> CREATOR = new Creator<Item>() {
        @Override
        public Item createFromParcel(Parcel in) {
            return new Item(in);
        }

        @Override
        public Item[] newArray(int size) {
            return new Item[size];
        }
    };

    public String getName(){
        return this.name;
    }
    public void setName(String name){
        this.name = name;
    }
    public String getContact(){
        return this.contact;
    }
    public void setContact(String contact){
        this.contact = contact;
    }
    public String getImage(){
        return this.image;
    }
    public void setImage(String image){
        this.image = image;
    }
}
