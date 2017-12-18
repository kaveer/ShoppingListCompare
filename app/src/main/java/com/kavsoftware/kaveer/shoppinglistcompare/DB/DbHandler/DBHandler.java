package com.kavsoftware.kaveer.shoppinglistcompare.DB.DbHandler;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.kavsoftware.kaveer.shoppinglistcompare.DB.Table.Table;
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

        sqLiteDatabase.execSQL(CreateMasterSuperMarket);
        sqLiteDatabase.execSQL(CreateMasterGrocery);

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


}
