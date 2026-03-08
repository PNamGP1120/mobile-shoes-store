package com.pnam.mobileshoesstore.data.repository;

import com.pnam.mobileshoesstore.data.model.Order;
import com.pnam.mobileshoesstore.data.model.OrderItem;
import java.util.List;

public class OrderRepository {

    public OrderRepository() {
    }

    public void createOrder(Order order, List<OrderItem> items) {
        // TODO: Create order and order items in Firestore
    }

    public void getOrdersByUser(String userId) {
        // TODO: Load user order history
    }

    public void getOrderById(String orderId) {
        // TODO: Load order detail
    }

    public void getAllOrders() {
        // TODO: Admin load all orders
    }

    public void updateOrderStatus(String orderId, String status) {
        // TODO: Update order status
    }

    public void updatePaymentStatus(String orderId, String status) {
        // TODO: Update payment status
    }
}