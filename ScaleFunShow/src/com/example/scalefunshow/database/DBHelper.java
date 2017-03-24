package com.example.scalefunshow.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.scalefunshow.bean.AdjustBean;

import java.util.ArrayList;
import java.util.List;

public class DBHelper extends SQLiteOpenHelper {
    String dbPath = android.os.Environment.getExternalStorageDirectory()
        .getAbsolutePath() + "/database";

    private static final String name = "database"; // 数据库名称

    private static final int version = 1; // 数据库版本

    private static final String TABLE_ADJUST = "adjust";

    // Contacts Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_OPERATOR = "operator";
    private static final String KEY_TIME = "time";
    private static final String KEY_FAMA_WEIGHT = "fama_weight";
    private static final String KEY_WEIGHT = "weight";
    private static final String KEY_POINT = "point";
    private static final String KEY_IS_RIGHT = "isRight";

    public DBHelper(Context context) {
        super(context, name, null, version);
    }

    String createSql = "CREATE TABLE IF NOT EXISTS " + TABLE_ADJUST + " ("
        + KEY_ID + " integer primary key autoincrement,"
        + KEY_OPERATOR + " TEXT,"
        + KEY_TIME + " TEXT,"
        + KEY_FAMA_WEIGHT + " TEXT,"
        + KEY_WEIGHT + " TEXT,"
        + KEY_POINT + " TEXT,"
        + KEY_IS_RIGHT + " TEXT"
        + ")";

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(createSql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // db.execSQL("ALTER TABLE person ADD phone VARCHAR(12)"); // 往表中增加一列
    }

    public int addAdjust(AdjustBean bean) {
        ContentValues values = new ContentValues();
        values.put(KEY_OPERATOR, bean.getOperator()); //
        values.put(KEY_TIME, bean.getTime()); //
        values.put(KEY_FAMA_WEIGHT, bean.getFamaWeight()); //
        values.put(KEY_WEIGHT, bean.getWeight()); //
        values.put(KEY_POINT, bean.getPoint()); //
        values.put(KEY_IS_RIGHT, bean.getIsRight()); //
        // Inserting Row
        SQLiteDatabase db = this.getWritableDatabase();
        long id = db.insert(TABLE_ADJUST, null, values);
        db.close();
        // Closing database connection
        return (int) id;
    }

    public AdjustBean getAdjustById(String id) {
        AdjustBean bean = new AdjustBean();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_ADJUST,
            null,
            KEY_ID + "=?",
            new String[]{id}, null, null, null, null);

        if (cursor != null) {
            cursor.moveToFirst();
            bean = getAdjust(cursor);
        }
        cursor.close();
        db.close();
        return bean;
    }

    public List<AdjustBean> getAllAdjust() {
        try {
            List<AdjustBean> list = new ArrayList<AdjustBean>();
            // Select All Query
            String selectQuery = "SELECT  * FROM " + TABLE_ADJUST;
            SQLiteDatabase db = this.getWritableDatabase();
            Cursor cursor = db.rawQuery(selectQuery, null);
            if (cursor.moveToFirst()) {
                do {
                    AdjustBean person = getAdjust(cursor);
                    list.add(person);
                } while (cursor.moveToNext());
            }

            // return contact list
            cursor.close();
            db.close();
            return list;
        } catch (Exception e) {
            Log.e("all_contact", "" + e);
        }
        return null;
    }

    private AdjustBean getAdjust(Cursor cursor) {
        if (cursor == null) {
            return null;
        }
        AdjustBean bean = new AdjustBean();
        int index = cursor.getColumnIndex(KEY_ID);
        bean.setId(cursor.getInt(index));
        bean.setOperator(cursor.getString(cursor.getColumnIndex(KEY_OPERATOR)));
        bean.setFamaWeight(cursor.getString(cursor.getColumnIndex(KEY_FAMA_WEIGHT)));
        bean.setWeight(cursor.getString(cursor.getColumnIndex(KEY_WEIGHT)));
        bean.setTime(cursor.getString(cursor.getColumnIndex(KEY_TIME)));
        bean.setPoint(cursor.getString(cursor.getColumnIndex(KEY_POINT)));
        bean.setIsRight(cursor.getString(cursor.getColumnIndex(KEY_IS_RIGHT)));
        return bean;
    }

    public int delete(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        int del_count = db.delete(TABLE_ADJUST, KEY_ID + " = ?", new String[]{id});
        db.close();
        return del_count;
    }
}
