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
    String Guid;
    ArrayList<MasterSupermarketViewModel> stores;
    ArrayList<MasterGroceryViewModel> groceryList;
    boolean IsViewOnly;

    public boolean isViewOnly() {
        return IsViewOnly;
    }

    public void setViewOnly(boolean viewOnly) {
        IsViewOnly = viewOnly;
    }

    public ArrayList<MasterGroceryViewModel> getGroceryList() {
        return groceryList;
    }

    public void setGroceryList(ArrayList<MasterGroceryViewModel> groceryList) {
        this.groceryList = groceryList;
    }

    public String getGuid() {
        return Guid;
    }

    public void setGuid(String guid) {
        Guid = guid;
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

