package com.example.markito.appproyecto.items;

public class ItemListRest {
    private String picture;
    private String name;

    public ItemListRest(String picture, String name) {
        this.picture = picture;
        this.name = name;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
