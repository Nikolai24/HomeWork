package com.example.fourthhomework;

public class Item {
    private String name;
    private String contact;
    private int image;

    public Item(String name, String contact, int image){
        this.name = name;
        this.contact = contact;
        this.image = image;
    }
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
    public int getImage(){
        return this.image;
    }
    public void setImage(int image){
        this.image = image;
    }
}
