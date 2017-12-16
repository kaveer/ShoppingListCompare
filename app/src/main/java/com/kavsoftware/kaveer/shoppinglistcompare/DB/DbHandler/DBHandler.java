package com.kavsoftware.kaveer.shoppinglistcompare.DB.DbHandler;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.kavsoftware.kaveer.shoppinglistcompare.DB.Table.Table;
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

    public DBHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public DBHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version, DatabaseErrorHandler errorHandler) {
        super(context, name, factory, version, errorHandler);
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String CreateMasterSuperMarket;

        CreateMasterSuperMarket =
                "CREATE TABLE "+ Table.MasterSupermarket.tableName +
                        " ("
                        + Table.MasterSupermarket.StoreId + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                        + Table.MasterSupermarket.StoreName + "  TEXT"
                        + " )";

        sqLiteDatabase.execSQL(CreateMasterSuperMarket);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + Table.MasterSupermarket.tableName);
    }

    public void PostMasterSupermarket(){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Table.MasterSupermarket.StoreName, "bes");

        db.insert(Table.MasterSupermarket.tableName, null , values);
        db.close();
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
}
