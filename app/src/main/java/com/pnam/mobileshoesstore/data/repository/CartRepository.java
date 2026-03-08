package com.pnam.mobileshoesstore.data.repository;

import android.content.Context;
import com.pnam.mobileshoesstore.data.local.CartDbHelper;
import com.pnam.mobileshoesstore.data.model.CartItem;
import java.util.List;

public class CartRepository {

    private final CartDbHelper cartDbHelper;

    public CartRepository(Context context) {
        this.cartDbHelper = new CartDbHelper(context);
    }

    public long addToCart(CartItem item) {
        long existingId = cartDbHelper.getExistingCartItemId(item.getProductId(), item.getSize());

        if (existingId != -1) {
            CartItem existingItem = cartDbHelper.getCartItemById(existingId);
            if (existingItem != null) {
                int newQuantity = existingItem.getQuantity() + item.getQuantity();
                cartDbHelper.updateQuantity(existingId, newQuantity);
                return existingId;
            }
        }

        return cartDbHelper.insertCartItem(item);
    }

    public List<CartItem> getAllCartItems() {
        return cartDbHelper.getAllCartItems();
    }

    public boolean updateQuantity(long cartItemId, int quantity) {
        return cartDbHelper.updateQuantity(cartItemId, quantity);
    }

    public boolean updateSelected(long cartItemId, boolean selected) {
        return cartDbHelper.updateSelected(cartItemId, selected);
    }

    public boolean deleteCartItem(long cartItemId) {
        return cartDbHelper.deleteCartItem(cartItemId);
    }

    public boolean clearCart() {
        return cartDbHelper.clearCart();
    }

    public int getTotalQuantity() {
        return cartDbHelper.getTotalQuantity();
    }

    public double getSelectedTotalAmount() {
        return cartDbHelper.getSelectedTotalAmount();
    }
}