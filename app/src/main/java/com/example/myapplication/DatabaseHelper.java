package com.example.myapplication;


import android.content.ContentValues;
        import android.content.Context;
        import android.database.Cursor;
        import android.database.sqlite.SQLiteDatabase;
        import android.database.sqlite.SQLiteOpenHelper;
        import android.util.Log;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final int DATABASEVERSION = 1;

    public static final String DATABASENAME = "UserManager.db";

    public static final String TABLE_USER = "table_user";
    public static final String COLUMN_USER_EMAIL = "user_email";
    public static final String COLUMN_USER_PASSWORD = "user_password";
    public static final String COLUMN_USER_FIRST_NAME = "user_first_name";
    public static final String COLUMN_USER_LAST_NAME = "user_last_name";
    public static final String COLUMN_USER_GENDER = "user_gender";


    private String CREATE_USER_TABLE =
            "CREATE TABLE " + TABLE_USER
                    + "("
                    + COLUMN_USER_EMAIL + " TEXT PRIMARY KEY,"
                    + COLUMN_USER_PASSWORD + " TEXT,"
                    + COLUMN_USER_FIRST_NAME + " TEXT,"
                    + COLUMN_USER_LAST_NAME + " TEXT,"
                    + COLUMN_USER_GENDER + " TEXT"
                    + ")";

    private String DROP_USER_TABLE = "DROP TABLE IF EXISTS " + TABLE_USER;

    public DatabaseHelper(Context context) {
        super(context, DATABASENAME, null, DATABASEVERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_USER_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DROP_USER_TABLE);
        onCreate(db);
    }


    public boolean checkUser(String email, String password) {
        String[] columns = {
                COLUMN_USER_FIRST_NAME
        };
        SQLiteDatabase db = this.getReadableDatabase();
        String selection = COLUMN_USER_EMAIL + " = ? " + " AND " + COLUMN_USER_PASSWORD + " = ? ";
        String[] selectionArgs = {email,password};

        Cursor cursor = db.query(TABLE_USER,
                columns,
                selection,
                selectionArgs,
                null,
                null,
                null);
        int cursorCount = cursor.getCount();
        cursor.close();
        db.close();

        if (cursorCount > 0) {
            return true;
        }

        return false;
    }

    public boolean checkUser(String email) {
        String[] columns = {
                COLUMN_USER_FIRST_NAME
        };
        SQLiteDatabase db = this.getReadableDatabase();
        String selection = COLUMN_USER_EMAIL + " = ?";
        String[] selectionArgs = {String.valueOf(email)};

        Cursor cursor = db.query(TABLE_USER,
                columns,
                selection,
                selectionArgs,
                null,
                null,
                null);
        int cursorCount = cursor.getCount();
        cursor.close();
        db.close();

        if (cursorCount > 0) {
            return true;
        }

        return false;
    }

    public boolean updateuser (User user){
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(COLUMN_USER_FIRST_NAME, user.getFirstname());
            values.put(COLUMN_USER_LAST_NAME, user.getLastname());
            values.put(COLUMN_USER_GENDER, user.getGender());

            db.update(TABLE_USER, values, COLUMN_USER_EMAIL + " = ?",
                    new String[]{String.valueOf(user.getEmail())});
            db.close();
            return true;

        } catch (Exception e) {
            Log.e("updateuser",e.toString());
        }
        return false;

    }
    public boolean addUser(User user) {
        try {
            SQLiteDatabase db = this.getWritableDatabase();

            ContentValues values = new ContentValues();
            values.put(COLUMN_USER_EMAIL, user.getEmail());
            values.put(COLUMN_USER_PASSWORD, user.getPassword());
            values.put(COLUMN_USER_FIRST_NAME, user.getFirstname());
            values.put(COLUMN_USER_LAST_NAME, user.getLastname());
            values.put(COLUMN_USER_GENDER, user.getGender());

            db.insert(TABLE_USER, null, values);
            db.close();
            return true;
        } catch (Exception e) {
            Log.e("addUser", e.toString());
        }
        return false;
    }
}