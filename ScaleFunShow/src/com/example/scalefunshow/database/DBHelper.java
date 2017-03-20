package com.example.scalefunshow.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.scalefunshow.bean.PersonBean;

import java.util.ArrayList;
import java.util.List;

public class DBHelper extends SQLiteOpenHelper {
    String dbPath=android.os.Environment.getExternalStorageDirectory()
        .getAbsolutePath()+"/database";

    private static final String name = "person"; // 数据库名称

    private static final int version = 1; // 数据库版本

    private static final String TABLE_PERSON = "person";

    // Contacts Table Columns names
    private static final String KEY_ID = "id";

    private static final String KEY_FIRSTNAME = "name";

    private static final String KEY_ID_NUMBER = "id_number";

    private static final String KEY_GENDER = "gender";

    private static final String KEY_COUNTRY = "country";

    private static final String KEY_ADDRESS = "address";

    private static final String KEY_IMAGE_PATH = "image_path";

    private static final String KEY_FINGERPRINT_ID = "fingerprint_id";

    public DBHelper(Context context) {

        super(context, name, null, version);
    }

    String createSql = "CREATE TABLE IF NOT EXISTS " + TABLE_PERSON + " ("
        + KEY_ID + " integer primary key autoincrement,"
        + KEY_FIRSTNAME + " TEXT,"
        + KEY_ID_NUMBER + " TEXT,"
        + KEY_GENDER + " TEXT,"
        + KEY_COUNTRY + " TEXT,"
        + KEY_ADDRESS + " TEXT,"
        + KEY_IMAGE_PATH + " TEXT,"
        + KEY_FINGERPRINT_ID + " TEXT"
        + ")";

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(createSql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // db.execSQL("ALTER TABLE person ADD phone VARCHAR(12)"); // 往表中增加一列
    }

    public int addPerson(PersonBean person) {
        ContentValues values = new ContentValues();
        values.put(KEY_FIRSTNAME, person.getC_ryxm()); //
        values.put(KEY_ID_NUMBER, person.getC_rybh()); //
        values.put(KEY_GENDER, person.getC_xb()); //

        // Inserting Row
        SQLiteDatabase db = this.getWritableDatabase();
        long id = db.insert(TABLE_PERSON, null, values);
        db.close();
        // Closing database connection
        return (int) id;
    }

    public PersonBean getPersonByFingerprintId(String id) {
        PersonBean person = new PersonBean();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_PERSON,
            null,
            KEY_FINGERPRINT_ID + "=?",
            new String[]{id}, null, null, null, null);

        if (cursor != null) {
            cursor.moveToFirst();
            person = getPerson(cursor);
        }
        cursor.close();
        db.close();
        return person;
    }

    public PersonBean getPerson(String idNo) {
        PersonBean person = new PersonBean();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_PERSON, null, KEY_ID_NUMBER + "=?", new String[]{idNo}, null, null, null,
            null);
        if (cursor != null) {
            cursor.moveToFirst();
            person = getPerson(cursor);
        }
        cursor.close();
        db.close();
        return person;
    }

    public List<PersonBean> getAllPersons() {
        try {
            List<PersonBean> list = new ArrayList<PersonBean>();
            // Select All Query
            String selectQuery = "SELECT  * FROM " + TABLE_PERSON;
            SQLiteDatabase db = this.getWritableDatabase();
            Cursor cursor = db.rawQuery(selectQuery, null);
            if (cursor.moveToFirst()) {
                do {
                    PersonBean person = getPerson(cursor);
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

    private PersonBean getPerson(Cursor cursor) {
        if (cursor == null) {
            return null;
        }
        String[] columnNames = cursor.getColumnNames();

        PersonBean person = new PersonBean();
        for (String column : columnNames) {

            int index = cursor.getColumnIndex(column);
            String value = null;
            try {
                value = cursor.getString(index);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return person;
    }

    public int deletePerson(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        int del_count = db.delete(TABLE_PERSON, KEY_ID + " = ?", new String[]{id});
        db.close();
        return del_count;
    }
}
