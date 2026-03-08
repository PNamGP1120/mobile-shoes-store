package com.pnam.mobileshoesstore.data.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class ProductSize implements Serializable {

    private String id;
    private String productId;
    private int size;
    private int stock;

    public ProductSize() {
    }

    public ProductSize(String id, String productId, int size, int stock) {
        this.id = id;
        this.productId = productId;
        this.size = size;
        this.stock = stock;
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

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public Map<String, Object> toMap() {
        Map<String, Object> map = new HashMap<>();
        map.put("id", id);
        map.put("productId", productId);
        map.put("size", size);
        map.put("stock", stock);
        return map;
    }
}