package com.example.hapag;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "UserDB";
    private static final int DATABASE_VERSION = 3;

    // Table names
    public static final String TABLE_USER = "user";
    public static final String TABLE_POST = "post";

    // User Table Columns
    static final String COLUMN_USER_ID = "userid";
    static final String COLUMN_EMAIL = "email";
    static final String COLUMN_FNAME = "fname";
    static final String COLUMN_LNAME = "lname";
    static final String COLUMN_PASSWORD = "password";

    // Post Table Columns
    static final String COLUMN_POST_ID = "postid";
    static final String COLUMN_USER_ID_FK = "userid";
    static final String COLUMN_DATE = "date";
    static final String COLUMN_TIME = "time";
    static final String COLUMN_TITLE = "title";
    static final String COLUMN_INGREDIENTS = "ingredients";
    static final String COLUMN_STEPS = "steps";
    static final String COLUMN_UPVOTES = "upvotes";
    static final String COLUMN_DOWNVOTES = "downvotes";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Creating User table
        String CREATE_USER_TABLE = "CREATE TABLE " + TABLE_USER +
                "(" +
                COLUMN_USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                COLUMN_EMAIL + " TEXT NOT NULL," +
                COLUMN_FNAME + " TEXT NOT NULL," +
                COLUMN_LNAME + " TEXT NOT NULL," +
                COLUMN_PASSWORD + " TEXT NOT NULL" +
                ")";
        db.execSQL(CREATE_USER_TABLE);

        // Creating Post table
        String CREATE_POST_TABLE = "CREATE TABLE " + TABLE_POST +
                "(" +
                COLUMN_POST_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                COLUMN_USER_ID_FK + " INTEGER," +
                COLUMN_DATE + " TEXT," +
                COLUMN_TIME + " TEXT," +
                COLUMN_TITLE + " TEXT," +
                COLUMN_INGREDIENTS + " TEXT," +
                COLUMN_STEPS + " TEXT," +
                COLUMN_UPVOTES + " INTEGER," +
                COLUMN_DOWNVOTES + " INTEGER," +
                " FOREIGN KEY (" + COLUMN_USER_ID_FK + ") REFERENCES " + TABLE_USER + "(" + COLUMN_USER_ID + ")" +
                ")";
        db.execSQL(CREATE_POST_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion < 3) {
            String CREATE_POST_TABLE = "CREATE TABLE " + TABLE_POST +
                    "(" +
                    COLUMN_POST_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    COLUMN_USER_ID_FK + " INTEGER," +
                    COLUMN_DATE + " TEXT," +
                    COLUMN_TIME + " TEXT," +
                    COLUMN_TITLE + " TEXT," +
                    COLUMN_INGREDIENTS + " TEXT," +
                    COLUMN_STEPS + " TEXT," +
                    COLUMN_UPVOTES + " INTEGER," +
                    COLUMN_DOWNVOTES + " INTEGER," +
                    " FOREIGN KEY (" + COLUMN_USER_ID_FK + ") REFERENCES " + TABLE_USER + "(" + COLUMN_USER_ID + ")" +
                    ")";
            db.execSQL(CREATE_POST_TABLE);
        }
    }

    // Insert a new post
    public long insertPost(long userId, String date, String time, String title, String ingredients, String steps, int upvotes, int downvotes) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_USER_ID_FK, userId);
        values.put(COLUMN_DATE, date);
        values.put(COLUMN_TIME, time);
        values.put(COLUMN_TITLE, title);
        values.put(COLUMN_INGREDIENTS, ingredients);
        values.put(COLUMN_STEPS, steps);
        values.put(COLUMN_UPVOTES, upvotes);
        values.put(COLUMN_DOWNVOTES, downvotes);

        long postId = db.insert(TABLE_POST, null, values);

        db.close();

        return postId;
    }

    // Fetch posts
    public Cursor getAllPosts() {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT " +
                TABLE_POST + "." + COLUMN_POST_ID + ", " +
                TABLE_USER + "." + COLUMN_FNAME + " || ' ' || " + TABLE_USER + "." + COLUMN_LNAME + " AS username, " +
                TABLE_POST + "." + COLUMN_DATE + ", " +
                TABLE_POST + "." + COLUMN_TIME + ", " +
                TABLE_POST + "." + COLUMN_TITLE + ", " +
                TABLE_POST + "." + COLUMN_INGREDIENTS + ", " +
                TABLE_POST + "." + COLUMN_STEPS + ", " +
                TABLE_POST + "." + COLUMN_UPVOTES + ", " +
                TABLE_POST + "." + COLUMN_DOWNVOTES +
                " FROM " + TABLE_POST +
                " INNER JOIN " + TABLE_USER +
                " ON " + TABLE_POST + "." + COLUMN_USER_ID_FK + " = " + TABLE_USER + "." + COLUMN_USER_ID;

        return db.rawQuery(query, null);
    }

    public long insertUser(String email, String fname, String lname, String password) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_EMAIL, email);
        values.put(COLUMN_FNAME, fname);
        values.put(COLUMN_LNAME, lname);
        values.put(COLUMN_PASSWORD, password);

        long userId = db.insert(TABLE_USER, null, values);

        db.close();

        return userId;
    }

    public String getUserFirstName(long userId) {
        SQLiteDatabase db = this.getReadableDatabase();

        String fname = null;

        String query = "SELECT " + COLUMN_FNAME + " FROM " + TABLE_USER + " WHERE " + COLUMN_USER_ID + " = ?";

        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(userId)});

        if (cursor != null && cursor.moveToFirst()) {
            int columnIndex = cursor.getColumnIndex(COLUMN_FNAME);
            if (columnIndex != -1) {
                fname = cursor.getString(columnIndex);
            }
            cursor.close();
        }

        db.close();

        return fname;
    }

    public String getUserLastName(long userId) {
        SQLiteDatabase db = this.getReadableDatabase();

        String lname = null;

        String query = "SELECT " + COLUMN_LNAME + " FROM " + TABLE_USER + " WHERE " + COLUMN_USER_ID + " = ?";

        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(userId)});

        if (cursor != null && cursor.moveToFirst()) {
            int columnIndex = cursor.getColumnIndex(COLUMN_LNAME);
            if (columnIndex != -1) {
                lname = cursor.getString(columnIndex);
            }
            cursor.close();
        }

        db.close();

        return lname;
    }

    public String getUserPassword(long userId) {
        SQLiteDatabase db = this.getReadableDatabase();

        String password = null;

        String query = "SELECT " + COLUMN_PASSWORD + " FROM " + TABLE_USER + " WHERE " + COLUMN_USER_ID + " = ?";

        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(userId)});

        if (cursor != null && cursor.moveToFirst()) {
            int columnIndex = cursor.getColumnIndex(COLUMN_PASSWORD);
            if (columnIndex != -1) {
                password = cursor.getString(columnIndex);
            }
            cursor.close();
        }

        db.close();

        return password;
    }

    public String getUserEmail(long userId) {
        SQLiteDatabase db = this.getReadableDatabase();

        String email = null;

        String query = "SELECT " + COLUMN_EMAIL + " FROM " + TABLE_USER + " WHERE " + COLUMN_USER_ID + " = ?";

        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(userId)});

        if (cursor != null && cursor.moveToFirst()) {
            int columnIndex = cursor.getColumnIndex(COLUMN_EMAIL);
            if (columnIndex != -1) {
                email = cursor.getString(columnIndex);
            }
            cursor.close();
        }

        db.close();

        return email;
    }

    public String getUserPassword(String email) {
        SQLiteDatabase db = this.getReadableDatabase();
        String password = null;
        String query = "SELECT " + COLUMN_PASSWORD + " FROM " + TABLE_USER + " WHERE " + COLUMN_EMAIL + " = ?";

        Cursor cursor = db.rawQuery(query, new String[]{email});

        if (cursor != null && cursor.moveToFirst()) {
            int columnIndex = cursor.getColumnIndex(COLUMN_PASSWORD);
            if (columnIndex != -1) {
                password = cursor.getString(columnIndex);
            }
            cursor.close();
        }

        db.close();
        return password;
    }

    public long getUserId(String email) {
        SQLiteDatabase db = this.getReadableDatabase();
        long userId = -1;

        String query = "SELECT " + COLUMN_USER_ID + " FROM " + TABLE_USER + " WHERE " + COLUMN_EMAIL + " = ?";

        Cursor cursor = db.rawQuery(query, new String[]{email});

        if (cursor != null && cursor.moveToFirst()) {
            int columnIndex = cursor.getColumnIndex(COLUMN_USER_ID);
            if (columnIndex != -1) {
                userId = cursor.getLong(columnIndex);
            }
            cursor.close();
        }

        db.close();

        return userId;
    }
}