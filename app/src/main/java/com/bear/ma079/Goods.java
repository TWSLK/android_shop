package com.bear.ma079;

import java.io.Serializable;

public class Goods implements Serializable {
    private String id;
    private String name;
    private String price;
    private String description;
    private String address;
    private String image;
    private String state;
    private String type;

    public Goods( ) {
    }

    public Goods(String name, String price, String description, String address) {
        this.name = name;
        this.price = price;
        this.description = description;
        this.address = address;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return this.id+this.name+this.price+this.description+this.address;
    }
}