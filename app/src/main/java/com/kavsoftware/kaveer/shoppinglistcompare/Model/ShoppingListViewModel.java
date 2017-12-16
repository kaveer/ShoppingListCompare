package com.kavsoftware.kaveer.shoppinglistcompare.Model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by kaveer on 12/16/2017.
 */

@SuppressWarnings("serial")
public class ShoppingListViewModel implements Serializable {
        int ListId;
        String ListName;
        String Date;
        ArrayList<MasterSupermarketViewModel> stores;

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

