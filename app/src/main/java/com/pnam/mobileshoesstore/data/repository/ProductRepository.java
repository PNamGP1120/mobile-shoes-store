package com.pnam.mobileshoesstore.data.repository;

import com.pnam.mobileshoesstore.data.model.Product;
import com.pnam.mobileshoesstore.data.model.ProductSize;

public class ProductRepository {

    public ProductRepository() {
    }

    public void getAllProducts() {
        // TODO: Fetch all products
    }

    public void getActiveProducts() {
        // TODO: Fetch active products
    }

    public void getProductsByCategory(String categoryId) {
        // TODO: Fetch by category
    }

    public void getProductById(String productId) {
        // TODO: Fetch product detail
    }

    public void searchProductsByName(String keyword) {
        // TODO: Search products
    }

    public void createProduct(Product product) {
        // TODO: Add product
    }

    public void updateProduct(Product product) {
        // TODO: Update product
    }

    public void deleteProduct(String productId) {
        // TODO: Delete product
    }

    public void addOrUpdateProductSize(String productId, ProductSize productSize) {
        // TODO: Add/update size subcollection
    }

    public void getProductSizes(String productId) {
        // TODO: Load sizes
    }
}