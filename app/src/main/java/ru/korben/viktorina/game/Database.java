package ru.korben.viktorina.game;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;


public class Database {
    private static final String DB_NAME = "resultDatabase";
    private static final int DB_VERSION = 1;
    private static final String DB_TABLE = "resultTable";

    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_TEXT = "text";
    public static final String COLUMN_DATE = "date";
    public static final String COLUMN_RESULT_VALUE = "value";

    private static final String DB_CREATE = "create table " + DB_TABLE + "(" +
            COLUMN_ID + " integer primary key autoincrement, " +
            COLUMN_TEXT + " text," + COLUMN_DATE + " text," + COLUMN_RESULT_VALUE + " text" + ");";

    private final Context mCtx;

    private DBHelper myDBHelper;
    private SQLiteDatabase myDB;

    public Database(Context mCtx) {
        this.mCtx = mCtx;
    }

    public void open() {
        myDBHelper = new DBHelper(mCtx, DB_NAME, null, DB_VERSION);
        myDB = myDBHelper.getWritableDatabase();
    }

    public void close() {
        if (myDBHelper != null) myDBHelper.close();
    }
    public Cursor getAllData() {
        return myDB.query(DB_TABLE, null, null, null, null, null, "_id DESC");
    }
    public void addRec(String text, String date, String result) {
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_TEXT, "Result is: " + text + "%" + " correct answers!");
        cv.put(COLUMN_DATE, "Test completed: " + date);
        cv.put(COLUMN_RESULT_VALUE, result);
        myDB.insert(DB_TABLE, null, cv);
    }
    public void delRec(long id) {
        myDB.delete(DB_TABLE, COLUMN_ID + " = " + id, null);
    }

    private static class DBHelper extends SQLiteOpenHelper {

        public DBHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
            super(context, name, factory, version);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(DB_CREATE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        }
    }
}
