package com.compkerworld.autosuggestion;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 *
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "products.db";
    private static final int DB_VERSION_NUMBER = 1;
    private static final String DB_TABLE_NAME = "products";
    private static final String PRODUCT_NAME = "product_name";

    private static final String CREATE_TABLE = "CREATE TABLE "
            + DB_TABLE_NAME
            + " (_id integer primary key autoincrement, " + PRODUCT_NAME+ " text not null);)";

    private SQLiteDatabase sqliteDBInstance = null;

    public DatabaseHelper(Context context){
        super(context, DB_NAME, null, DB_VERSION_NUMBER);
        return;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        sqLiteDatabase.execSQL(CREATE_TABLE);

        this.sqliteDBInstance=sqLiteDatabase;
        insertProduct("Soap");
        insertProduct("Surf");
        insertProduct("Computer");
        insertProduct("Hard Disk");
        insertProduct("HP Printer");
        insertProduct("HP Laser Printer");
        insertProduct("HP Injet Printer");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public void openDB() throws SQLException {
        if (this.sqliteDBInstance == null) {
            this.sqliteDBInstance = this.getWritableDatabase();
        }
    }

    public void closeDB() {
        if (this.sqliteDBInstance != null) {
            if (this.sqliteDBInstance.isOpen())
                this.sqliteDBInstance.close();
        }
    }

    public long insertProduct(String item) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(PRODUCT_NAME, item);
        return this.sqliteDBInstance.insert(DB_TABLE_NAME, null, contentValues);
    }

    public String[] getAllItemFilter() {
        Cursor cursor = this.sqliteDBInstance
                .query(DB_TABLE_NAME, new String[] { PRODUCT_NAME }, null,
                        null, null, null, null);

        if (cursor.getCount() > 0) {
            String[] str = new String[cursor.getCount()];
            int i = 0;

            while (cursor.moveToNext()) {
                str[i] = cursor.getString(cursor
                        .getColumnIndex(PRODUCT_NAME));
                i++;
            }
            return str;
        } else {
            return new String[] {};
        }
    }
}
