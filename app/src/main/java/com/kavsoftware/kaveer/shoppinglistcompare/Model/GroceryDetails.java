package com.kavsoftware.kaveer.shoppinglistcompare.Model;

/**
 * Created by kaveer on 12/17/2017.
 */

public class GroceryDetails {
    private int GroceryDetailsId;
    private int GroceryId;
    private int StoreId;

    public int getGroceryDetailsId() {
        return GroceryDetailsId;
    }

    public void setGroceryDetailsId(int groceryDetailsId) {
        GroceryDetailsId = groceryDetailsId;
    }

    public int getGroceryId() {
        return GroceryId;
    }

    public void setGroceryId(int groceryId) {
        GroceryId = groceryId;
    }

    public int getStoreId() {
        return StoreId;
    }

    public void setStoreId(int storeId) {
        StoreId = storeId;
    }

    public float getPrice() {
        return Price;
    }

    public void setPrice(float price) {
        Price = price;
    }

    private float Price;
}
