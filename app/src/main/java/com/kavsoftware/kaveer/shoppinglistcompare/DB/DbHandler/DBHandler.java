package com.kavsoftware.kaveer.shoppinglistcompare.DB.DbHandler;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.kavsoftware.kaveer.shoppinglistcompare.DB.Table.Table;
import com.kavsoftware.kaveer.shoppinglistcompare.Model.GroceryDetailsViewModel;
import com.kavsoftware.kaveer.shoppinglistcompare.Model.ListViewModel;
import com.kavsoftware.kaveer.shoppinglistcompare.Model.MasterGroceryViewModel;
import com.kavsoftware.kaveer.shoppinglistcompare.Model.MasterSupermarketViewModel;

import java.util.ArrayList;

/**
 * Created by kaveer on 12/15/2017.
 */

public class DBHandler extends SQLiteOpenHelper {

    public static final String databaseName = "SlcDb";
    public static final int databaseVersion = 1;

    public DBHandler(Context context){
        super(context, databaseName, null, databaseVersion);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String CreateMasterSuperMarket;
        String CreateMasterGrocery;
        String CreateList;
//        String CreateListDetails;
        String CreateGroceryDetails;


        CreateMasterSuperMarket =
                "CREATE TABLE " + Table.MasterSupermarket.tableName +
                        " ("
                        + Table.MasterSupermarket.StoreId + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                        + Table.MasterSupermarket.StoreName + "  TEXT"
                        + " )";

        CreateMasterGrocery =
                "CREATE TABLE " + Table.MasterGrocery.tableName +
                        " ("
                        + Table.MasterGrocery.GroceryId + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                        + Table.MasterGrocery.GroceryName + "  TEXT"
                        + " )";

        CreateList =
                "CREATE TABLE " + Table.List.tableName +
                        " ("
                        + Table.List.ListId + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                        + Table.List.ListName + "  TEXT,"
                        + Table.List.Date + "  DATE DEFAULT CURRENT_DATE,"
                        + Table.List.Uuid + "  TEXT"
                        + " )";
//
//        CreateListDetails =
//                "CREATE TABLE " + Table.ListDetails.tableName +
//                        " ("
//                        + Table.ListDetails.ListDetailsId + " INTEGER PRIMARY KEY AUTOINCREMENT,"
//                        + Table.ListDetails.ListId + "  INT,"
//                        + Table.ListDetails.StoreId + "  INT"
//                        + " )";

        CreateGroceryDetails =
                "CREATE TABLE " + Table.GroceryDetails.tableName +
                        " ("
                        + Table.GroceryDetails.GroceryDetailsId + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                        + Table.GroceryDetails.GroceryId + "  INT,"
                        + Table.GroceryDetails.StoreId + "  INT,"
                        + Table.GroceryDetails.ListId + "  INT,"
                        + Table.GroceryDetails.Price + "  REAL"
                        + " )";



        sqLiteDatabase.execSQL(CreateMasterSuperMarket);
        sqLiteDatabase.execSQL(CreateMasterGrocery);
        sqLiteDatabase.execSQL(CreateList);
//        sqLiteDatabase.execSQL(CreateListDetails);
        sqLiteDatabase.execSQL(CreateGroceryDetails);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + Table.MasterSupermarket.tableName);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + Table.MasterGrocery.tableName);
    }

    public ArrayList<MasterSupermarketViewModel> GetMasterSupermarket(){
        ArrayList<MasterSupermarketViewModel> result = new ArrayList<>();

        SQLiteDatabase db = this.getWritableDatabase();
        String query;
        query  = "SELECT * FROM "
                + Table.MasterSupermarket.tableName;


        Cursor cursor = db.rawQuery(query , null);
        if(cursor.getCount() > 0){
            for(cursor.moveToFirst(); !cursor.isAfterLast() ; cursor.moveToNext()){
                MasterSupermarketViewModel store = new MasterSupermarketViewModel();

                store.setStoreId(Integer.parseInt(cursor.getString(0)));
                store.setStoreName(cursor.getString(1));

                result.add(store);
            }
        }
        db.close();

        return result;
    }

    public ArrayList<MasterGroceryViewModel> GetMasterGrocery() {
        ArrayList<MasterGroceryViewModel> result = new ArrayList<>();

        SQLiteDatabase db = this.getWritableDatabase();
        String query;
        query  = "SELECT * FROM "
                + Table.MasterGrocery.tableName;

        Cursor cursor = db.rawQuery(query , null);
        if(cursor.getCount() > 0){
            for(cursor.moveToFirst(); !cursor.isAfterLast() ; cursor.moveToNext()){
                MasterGroceryViewModel grocery = new MasterGroceryViewModel();

                grocery.setGroceryId(Integer.parseInt(cursor.getString(0)));
                grocery.setGroceryName(cursor.getString(1));

                result.add(grocery);
            }
        }
        db.close();

        return result;
    }

    private int GetIdByGroceryName(String item) {
        int result = 0;

        SQLiteDatabase db = this.getWritableDatabase();
        String query;
        query  = "SELECT * FROM "
                + Table.MasterGrocery.tableName  +
                " WHERE " + Table.MasterGrocery.GroceryName + " = '" + item + "'";

        Cursor cursor = db.rawQuery(query , null);
        if(cursor.getCount() > 0){
            cursor.moveToFirst();

            result = Integer.parseInt(cursor.getString(0));

        }
        db.close();

        return result;
    }

    public int GetIdByStoreName(String item) {
        int result = 0;

        SQLiteDatabase db = this.getWritableDatabase();
        String query;
        query  = "SELECT * FROM "
                + Table.MasterSupermarket.tableName  +
                " WHERE " + Table.MasterSupermarket.StoreName + " = '" + item + "'";

        Cursor cursor = db.rawQuery(query , null);
        if(cursor.getCount() > 0){
            cursor.moveToFirst();

            result = Integer.parseInt(cursor.getString(0));

        }
        db.close();

        return result;
    }

    public int PostMasterSupermarket(String item){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Table.MasterSupermarket.StoreName, item);

        db.insert(Table.MasterSupermarket.tableName, null , values);
        db.close();

        int result = GetIdByStoreName(item);

        return result;
    }

    public int PostMasterGrocery(String item){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Table.MasterGrocery.GroceryName, item);

        db.insert(Table.MasterGrocery.tableName, null , values);
        db.close();

        int result = GetIdByGroceryName(item);

        return result;
    }

    public int PostList(ListViewModel listDetails){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Table.List.ListName, listDetails.getListName());
        values.put(Table.List.Date, listDetails.getDate());
        values.put(Table.List.Uuid, listDetails.getUuid());


        db.insert(Table.List.tableName, null , values);
        db.close();

        int result = GetIdByListDetails(listDetails);

        return result;
    }

    public int GetIdByListDetails(ListViewModel listDetails) {
        int result = 0;

        SQLiteDatabase db = this.getWritableDatabase();
        String query;
        if (listDetails.getUuid() != null){
            query  = "SELECT " + Table.List.ListId + " FROM "
                    + Table.List.tableName  +
                    " WHERE " + Table.List.ListName + " = '" + listDetails.getListName() + "' "
                    + " AND " + Table.List.Date + " = '" + listDetails.getDate() + "' "
                    + " AND " + Table.List.Uuid + " = '" + listDetails.getUuid() + "' ";

        }else {
            query  = "SELECT " + Table.List.ListId + " FROM "
                    + Table.List.tableName  +
                    " WHERE " + Table.List.ListName + " = '" + listDetails.getListName() + "' "
                    + " AND " + Table.List.Date + " = '" + listDetails.getDate() + "' ";
        }

        Cursor cursor = db.rawQuery(query , null);
        if(cursor.getCount() > 0){
            cursor.moveToFirst();

            result = Integer.parseInt(cursor.getString(0));

        }
        db.close();

        return result;
    }




//    public void PostListDetails(int listId, int storeId) {
//        SQLiteDatabase db = this.getWritableDatabase();
//        ContentValues values = new ContentValues();
//        values.put(Table.ListDetails.ListId, listId);
//        values.put(Table.ListDetails.StoreId, storeId);
//
//
//        db.insert(Table.ListDetails.tableName, null , values);
//        db.close();
//    }

    public void PostGroceryDetails(GroceryDetailsViewModel item) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Table.GroceryDetails.GroceryId, item.getGroceryId());
        values.put(Table.GroceryDetails.StoreId, item.getStoreId());
        values.put(Table.GroceryDetails.ListId, item.getListId());
        values.put(Table.GroceryDetails.Price, item.getPrice());

        db.insert(Table.GroceryDetails.tableName, null , values);
        db.close();
    }

    public ArrayList<ListViewModel> GetList() {
        ArrayList<ListViewModel> result = new ArrayList<>();

        SQLiteDatabase db = this.getWritableDatabase();
        String query;

        query  = "SELECT * FROM "
                + Table.List.tableName
                + " ORDER BY " + Table.List.Date + " DESC ";

        Cursor cursor = db.rawQuery(query , null);
        if(cursor.getCount() > 0){
            for(cursor.moveToFirst(); !cursor.isAfterLast() ; cursor.moveToNext()) {
                ListViewModel item = new ListViewModel();

                item.setListId(Integer.parseInt(cursor.getString(0)));
                item.setListName(cursor.getString(1));
                item.setDate(cursor.getString(2));
                item.setUuid(cursor.getString(3));

                result.add(item);
            }
        }
        db.close();

        return result;
    }

    public void DeleteByListId(int listId) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(Table.List.tableName, Table.List.ListId + " = ? ",
                new String[]{String.valueOf(listId)});
        db.close();

        DeleteListDetailsByListId(listId);

    }

    public void DeleteListDetailsByListId(int listId){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(Table.GroceryDetails.tableName, Table.GroceryDetails.ListId + " = ? ",
                new String[]{String.valueOf(listId)});
        db.close();

    }

    public ArrayList<MasterSupermarketViewModel> GetStoreDetailsByListId(ListViewModel listDetails) {
        ArrayList<MasterSupermarketViewModel> result = new ArrayList<>();

        SQLiteDatabase db = this.getWritableDatabase();
        String query;

        query  = "SELECT DISTINCT  "
                    + Table.MasterSupermarket.tableName + "." + Table.MasterSupermarket.StoreId
                    + ", "
                    + Table.MasterSupermarket.tableName + "." + Table.MasterSupermarket.StoreName
                + " FROM " + Table.GroceryDetails.tableName
                + " INNER JOIN " + Table.MasterSupermarket.tableName + " ON "
                    + Table.MasterSupermarket.tableName + "." + Table.MasterSupermarket.StoreId + " = " + Table.GroceryDetails.tableName + "." + Table.GroceryDetails.StoreId
                + " WHERE " + Table.GroceryDetails.tableName + "." + Table.GroceryDetails.ListId + " = " + listDetails.getListId();

        Cursor cursor = db.rawQuery(query , null);
        if(cursor.getCount() > 0){
            for(cursor.moveToFirst(); !cursor.isAfterLast() ; cursor.moveToNext()) {
                MasterSupermarketViewModel item = new MasterSupermarketViewModel();

                item.setStoreId(Integer.parseInt(cursor.getString(0)));
                item.setStoreName(cursor.getString(1));

                result.add(item);
            }
        }
        db.close();

        return result;
    }

    public ArrayList<GroceryDetailsViewModel> GetGroceryDetailsByListId(int listId) {
        ArrayList<GroceryDetailsViewModel> result = new ArrayList<>();

        SQLiteDatabase db = this.getWritableDatabase();
        String query;

        query  =
                "SELECT * "
                + "FROM ("
                    + " SELECT "
                        + Table.GroceryDetails.GroceryId + ", "
                        + Table.GroceryDetails.StoreId + ", "
                        + Table.GroceryDetails.Price
                    + " FROM " + Table.GroceryDetails.tableName
                    + " WHERE " + Table.GroceryDetails.ListId + " = " + listId +
                        " )" ;

        Cursor cursor = db.rawQuery(query , null);
        if(cursor.getCount() > 0){
            for(cursor.moveToFirst(); !cursor.isAfterLast() ; cursor.moveToNext()) {
                GroceryDetailsViewModel item = new GroceryDetailsViewModel();

                item.setGroceryId(Integer.parseInt(cursor.getString(0)));
                item.setStoreId(Integer.parseInt(cursor.getString(1)));
//                item.setStoreId(Integer.parseInt(cursor.getString(2)));
//                item.setListId(Integer.parseInt(cursor.getString(3)));
                item.setPrice(Float.parseFloat(cursor.getString(2)));

                result.add(item);
            }
        }
        db.close();

        return result;
    }

    public String GetGroceryNameByGroceryId(int groceryId) {
        String result = "";

        SQLiteDatabase db = this.getWritableDatabase();
        String query;
        query  = "SELECT " + Table.MasterGrocery.GroceryName
                + " FROM " + Table.MasterGrocery.tableName  +
                " WHERE " + Table.MasterGrocery.GroceryId + " = " + groceryId ;

        Cursor cursor = db.rawQuery(query , null);
        if(cursor.getCount() > 0){
            cursor.moveToFirst();

            result = cursor.getString(0);

        }
        db.close();

        return result;
    }
}
