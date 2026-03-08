package com.pnam.mobileshoesstore.data.model;

import java.io.Serializable;

public class CartItem implements Serializable {

    private long id;
    private String productId;
    private String productName;
    private String imageUrl;
    private int size;
    private int quantity;
    private double price;
    private int stock;
    private boolean selected;

    public CartItem() {
    }

    public CartItem(long id, String productId, String productName, String imageUrl,
                    int size, int quantity, double price, int stock, boolean selected) {
        this.id = id;
        this.productId = productId;
        this.productName = productName;
        this.imageUrl = imageUrl;
        this.size = size;
        this.quantity = quantity;
        this.price = price;
        this.stock = stock;
        this.selected = selected;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public double getSubTotal() {
        return price * quantity;
    }
}