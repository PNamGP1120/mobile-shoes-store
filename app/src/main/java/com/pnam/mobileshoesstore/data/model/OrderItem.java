package com.pnam.mobileshoesstore.data.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class OrderItem implements Serializable {

    private String id;
    private String productId;
    private String productName;
    private String productImage;
    private int size;
    private int quantity;
    private double unitPrice;

    public OrderItem() {
    }

    public OrderItem(String id, String productId, String productName, String productImage,
                     int size, int quantity, double unitPrice) {
        this.id = id;
        this.productId = productId;
        this.productName = productName;
        this.productImage = productImage;
        this.size = size;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
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

    public String getProductImage() {
        return productImage;
    }

    public void setProductImage(String productImage) {
        this.productImage = productImage;
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

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public Map<String, Object> toMap() {
        Map<String, Object> map = new HashMap<>();
        map.put("id", id);
        map.put("productId", productId);
        map.put("productName", productName);
        map.put("productImage", productImage);
        map.put("size", size);
        map.put("quantity", quantity);
        map.put("unitPrice", unitPrice);
        return map;
    }
}