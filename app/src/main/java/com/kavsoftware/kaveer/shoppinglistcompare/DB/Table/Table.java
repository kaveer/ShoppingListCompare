package com.kavsoftware.kaveer.shoppinglistcompare.DB.Table;

import android.provider.BaseColumns;

/**
 * Created by kaveer on 12/15/2017.
 */

public class Table {

    public  static abstract class MasterSupermarket implements BaseColumns {
        public static final String tableName = "MasterSupermarket";

        public static String StoreId = "StoreId";
        public static String StoreName = "StoreName";
    }

    public  static abstract class MasterGrocery implements BaseColumns {
        public static final String tableName = "MasterGrocery";

        public static String GroceryId = "GroceryId";
        public static String GroceryName = "GroceryName";
    }

    public  static abstract class List implements BaseColumns {
        public static final String tableName = "List";

        public static String ListId = "ListId";
        public static String ListName = "ListName";
        public static String Date = "ListDate";
        public static String Uuid = "ListUuid";

    }

//    public  static abstract class ListDetails implements BaseColumns {
//        public static final String tableName = "ListDetails";
//
//        public static String ListDetailsId = "ListDetailsId";
//        public static String ListId = "ListId";
//        public static String StoreId = "StoreId";
//
//    }

    public  static abstract class GroceryDetails implements BaseColumns {
        public static final String tableName = "GroceryDetails";

        public static String GroceryDetailsId = "GroceryDetailsId";
        public static String GroceryId = "GroceryId";
        public static String StoreId = "StoreId";
        public static String ListId = "ListId";
        public static String Price = "Price";

    }

}
