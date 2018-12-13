package com.example.markito.appproyecto.items;

public class ItemMenu {
    private String id;
    private String idRestaurant;
    private String name;
    private String price;
    private String picture;

    public ItemMenu(String id, String idRestaurant, String name, String price, String picture) {
        this.id = id;
        this.idRestaurant = idRestaurant;
        this.name = name;
        this.price = price;
        this.picture = picture;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIdRestaurant() {
        return idRestaurant;
    }

    public void setIdRestaurant(String idRestaurant) {
        this.idRestaurant = idRestaurant;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }
}
