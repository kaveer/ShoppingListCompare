package com.kavsoftware.kaveer.shoppinglistcompare.Model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by kaveer on 12/16/2017.
 */

@SuppressWarnings("serial")
public class ListViewModel implements Serializable {
    int ListId;
    String ListName;
    String Date;
    String Uuid;

    ArrayList<MasterSupermarketViewModel> stores;
    ArrayList<MasterGroceryViewModel> grocery;
    ArrayList<GroceryDetailsViewModel> groceryDetail;
    boolean IsViewOnly;


    public ArrayList<GroceryDetailsViewModel> getGroceryDetail() {
        return groceryDetail;
    }

    public void setGroceryDetail(ArrayList<GroceryDetailsViewModel> groceryDetail) {
        this.groceryDetail = groceryDetail;
    }

    public boolean isViewOnly() {
        return IsViewOnly;
    }

    public void setViewOnly(boolean viewOnly) {
        IsViewOnly = viewOnly;
    }

    public ArrayList<MasterGroceryViewModel> getGrocery() {
        return grocery;
    }

    public void setGrocery(ArrayList<MasterGroceryViewModel> grocery) {
        this.grocery = grocery;
    }

    public String getUuid() {
        return Uuid;
    }

    public void setUuid(String uuid) {
        Uuid = uuid;
    }

    public ArrayList<MasterSupermarketViewModel> getStores() {
        return stores;
    }

    public void setStores(ArrayList<MasterSupermarketViewModel> stores) {
        this.stores = stores;
    }

    public int getListId() {
        return ListId;
    }

    public void setListId(int listId) {
        this.ListId = listId;
    }

    public String getListName() {
        return ListName;
    }

    public void setListName(String listName) {
        this.ListName = listName;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

}

