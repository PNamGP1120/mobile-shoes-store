package com.pnam.mobileshoesstore.data.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Category implements Serializable {

    private String id;
    private String name;
    private String description;
    private String imageUrl;
    private boolean active;

    public Category() {
    }

    public Category(String id, String name, String description, String imageUrl, boolean active) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.imageUrl = imageUrl;
        this.active = active;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public Map<String, Object> toMap() {
        Map<String, Object> map = new HashMap<>();
        map.put("id", id);
        map.put("name", name);
        map.put("description", description);
        map.put("imageUrl", imageUrl);
        map.put("active", active);
        return map;
    }
}