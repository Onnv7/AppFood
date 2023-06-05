package com.hcmute.foodapphomework.Entity;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "Food")
public class Food implements Serializable {
    @NonNull
    @PrimaryKey
    private String id;

    private String name;
    private String price;
    private String images;
    private int quantity;


//    public Food(@NonNull String id, String name, float price, String images, int quantity) {
//        this.id = id;
//        this.name = name;
//        this.price = price;
//        this.images = images;
//        this.quantity = quantity;
//    }

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

    public String getImages() {
        return images;
    }

    public void setImages(String images) {
        this.images = images;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "Food{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", images='" + images + '\'' +
                ", quantity=" + quantity +
                '}';
    }
}
