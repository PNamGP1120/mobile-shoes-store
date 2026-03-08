package com.pnam.mobileshoesstore.data.local;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.pnam.mobileshoesstore.data.model.CartItem;
import java.util.ArrayList;
import java.util.List;

public class CartDbHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "mobile_shoes_store.db";
    public static final int DATABASE_VERSION = 1;

    public static final String TABLE_CART_ITEMS = "cart_items";

    public static final String COLUMN_ID = "id";
    public static final String COLUMN_PRODUCT_ID = "productId";
    public static final String COLUMN_PRODUCT_NAME = "productName";
    public static final String COLUMN_IMAGE_URL = "imageUrl";
    public static final String COLUMN_SIZE = "size";
    public static final String COLUMN_QUANTITY = "quantity";
    public static final String COLUMN_PRICE = "price";
    public static final String COLUMN_STOCK = "stock";
    public static final String COLUMN_SELECTED = "selected";

    public CartDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createCartTable = "CREATE TABLE " + TABLE_CART_ITEMS + " ("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_PRODUCT_ID + " TEXT NOT NULL, "
                + COLUMN_PRODUCT_NAME + " TEXT NOT NULL, "
                + COLUMN_IMAGE_URL + " TEXT, "
                + COLUMN_SIZE + " INTEGER NOT NULL, "
                + COLUMN_QUANTITY + " INTEGER NOT NULL, "
                + COLUMN_PRICE + " REAL NOT NULL, "
                + COLUMN_STOCK + " INTEGER DEFAULT 0, "
                + COLUMN_SELECTED + " INTEGER DEFAULT 1"
                + ")";
        db.execSQL(createCartTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CART_ITEMS);
        onCreate(db);
    }

    public long insertCartItem(CartItem item) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_PRODUCT_ID, item.getProductId());
        values.put(COLUMN_PRODUCT_NAME, item.getProductName());
        values.put(COLUMN_IMAGE_URL, item.getImageUrl());
        values.put(COLUMN_SIZE, item.getSize());
        values.put(COLUMN_QUANTITY, item.getQuantity());
        values.put(COLUMN_PRICE, item.getPrice());
        values.put(COLUMN_STOCK, item.getStock());
        values.put(COLUMN_SELECTED, item.isSelected() ? 1 : 0);

        long result = db.insert(TABLE_CART_ITEMS, null, values);
        db.close();
        return result;
    }

    public long getExistingCartItemId(String productId, int size) {
        SQLiteDatabase db = this.getReadableDatabase();
        long cartItemId = -1;

        Cursor cursor = db.query(
                TABLE_CART_ITEMS,
                new String[]{COLUMN_ID},
                COLUMN_PRODUCT_ID + " = ? AND " + COLUMN_SIZE + " = ?",
                new String[]{productId, String.valueOf(size)},
                null, null, null
        );

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                cartItemId = cursor.getLong(cursor.getColumnIndexOrThrow(COLUMN_ID));
            }
            cursor.close();
        }

        db.close();
        return cartItemId;
    }

    public boolean updateQuantity(long id, int quantity) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_QUANTITY, quantity);

        int rows = db.update(
                TABLE_CART_ITEMS,
                values,
                COLUMN_ID + " = ?",
                new String[]{String.valueOf(id)}
        );

        db.close();
        return rows > 0;
    }

    public boolean updateSelected(long id, boolean selected) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_SELECTED, selected ? 1 : 0);

        int rows = db.update(
                TABLE_CART_ITEMS,
                values,
                COLUMN_ID + " = ?",
                new String[]{String.valueOf(id)}
        );

        db.close();
        return rows > 0;
    }

    public boolean deleteCartItem(long id) {
        SQLiteDatabase db = this.getWritableDatabase();
        int rows = db.delete(TABLE_CART_ITEMS, COLUMN_ID + " = ?", new String[]{String.valueOf(id)});
        db.close();
        return rows > 0;
    }

    public boolean clearCart() {
        SQLiteDatabase db = this.getWritableDatabase();
        int rows = db.delete(TABLE_CART_ITEMS, null, null);
        db.close();
        return rows >= 0;
    }

    public List<CartItem> getAllCartItems() {
        List<CartItem> cartItems = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_CART_ITEMS, null, null, null, null, null, COLUMN_ID + " DESC");

        if (cursor != null) {
            while (cursor.moveToNext()) {
                CartItem item = new CartItem();
                item.setId(cursor.getLong(cursor.getColumnIndexOrThrow(COLUMN_ID)));
                item.setProductId(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PRODUCT_ID)));
                item.setProductName(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PRODUCT_NAME)));
                item.setImageUrl(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_IMAGE_URL)));
                item.setSize(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_SIZE)));
                item.setQuantity(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_QUANTITY)));
                item.setPrice(cursor.getDouble(cursor.getColumnIndexOrThrow(COLUMN_PRICE)));
                item.setStock(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_STOCK)));
                item.setSelected(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_SELECTED)) == 1);
                cartItems.add(item);
            }
            cursor.close();
        }

        db.close();
        return cartItems;
    }

    public CartItem getCartItemById(long id) {
        SQLiteDatabase db = this.getReadableDatabase();
        CartItem item = null;

        Cursor cursor = db.query(
                TABLE_CART_ITEMS,
                null,
                COLUMN_ID + " = ?",
                new String[]{String.valueOf(id)},
                null, null, null
        );

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                item = new CartItem();
                item.setId(cursor.getLong(cursor.getColumnIndexOrThrow(COLUMN_ID)));
                item.setProductId(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PRODUCT_ID)));
                item.setProductName(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PRODUCT_NAME)));
                item.setImageUrl(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_IMAGE_URL)));
                item.setSize(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_SIZE)));
                item.setQuantity(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_QUANTITY)));
                item.setPrice(cursor.getDouble(cursor.getColumnIndexOrThrow(COLUMN_PRICE)));
                item.setStock(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_STOCK)));
                item.setSelected(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_SELECTED)) == 1);
            }
            cursor.close();
        }

        db.close();
        return item;
    }

    public int getTotalQuantity() {
        SQLiteDatabase db = this.getReadableDatabase();
        int total = 0;

        Cursor cursor = db.rawQuery(
                "SELECT SUM(" + COLUMN_QUANTITY + ") FROM " + TABLE_CART_ITEMS,
                null
        );

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                total = cursor.isNull(0) ? 0 : cursor.getInt(0);
            }
            cursor.close();
        }

        db.close();
        return total;
    }

    public double getSelectedTotalAmount() {
        SQLiteDatabase db = this.getReadableDatabase();
        double total = 0;

        String query = "SELECT SUM(" + COLUMN_PRICE + " * " + COLUMN_QUANTITY + ") FROM "
                + TABLE_CART_ITEMS + " WHERE " + COLUMN_SELECTED + " = 1";

        Cursor cursor = db.rawQuery(query, null);

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                total = cursor.isNull(0) ? 0 : cursor.getDouble(0);
            }
            cursor.close();
        }

        db.close();
        return total;
    }
}