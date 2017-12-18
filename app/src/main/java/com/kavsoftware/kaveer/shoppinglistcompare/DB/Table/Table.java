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

}
