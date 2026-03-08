package com.pnam.mobileshoesstore.data.repository;

import com.pnam.mobileshoesstore.data.model.Review;

public class ReviewRepository {

    public ReviewRepository() {
    }

    public void addReview(String productId, Review review) {
        // TODO: Add review to products/{productId}/reviews
    }

    public void getReviewsByProduct(String productId) {
        // TODO: Load product reviews
    }

    public void updateAverageRating(String productId) {
        // TODO: Recalculate avg rating
    }
}