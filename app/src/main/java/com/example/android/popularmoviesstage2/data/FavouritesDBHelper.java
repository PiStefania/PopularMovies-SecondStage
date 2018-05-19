package com.example.android.popularmoviesstage2.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class FavouritesDBHelper extends SQLiteOpenHelper {

    public static final String LOG_TAG = FavouritesDBHelper.class.getSimpleName();

    //name & version
    private static final String DATABASE_NAME = "favourites.db";
    private static final int DATABASE_VERSION = 1;

    public FavouritesDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Create the database
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        final String SQL_CREATE_MOVIE_TABLE = "CREATE TABLE " +
                FavouritesContract.FavouriteEntry.TABLE_FAVOURITES + "(" + FavouritesContract.FavouriteEntry._ID +
                " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                FavouritesContract.FavouriteEntry.COLUMN_ID_MOVIE + " TEXT NOT NULL UNIQUE ON CONFLICT REPLACE, " +
                FavouritesContract.FavouriteEntry.COLUMN_TITLE_MOVIE + " TEXT NOT NULL, " +
                FavouritesContract.FavouriteEntry.COLUMN_POSTER_MOVIE + " TEXT NOT NULL, " +
                FavouritesContract.FavouriteEntry.COLUMN_BACKDROP_MOVIE + " TEXT NOT NULL, " +
                FavouritesContract.FavouriteEntry.COLUMN_SYNOPSIS_MOVIE + " TEXT NOT NULL, " +
                FavouritesContract.FavouriteEntry.COLUMN_RATING_MOVIE + " REAL NOT NULL, " +
                FavouritesContract.FavouriteEntry.COLUMN_RELEASE_MOVIE + " TEXT NOT NULL )";

        sqLiteDatabase.execSQL(SQL_CREATE_MOVIE_TABLE);
    }

    // Upgrade database when version is changed.
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        Log.w(LOG_TAG, "Upgrading database from version " + oldVersion + " to " +
                newVersion + ". OLD DATA WILL BE DESTROYED");
        // Drop the table
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + FavouritesContract.FavouriteEntry.TABLE_FAVOURITES);
        sqLiteDatabase.execSQL("DELETE FROM SQLITE_SEQUENCE WHERE NAME = '" +
                FavouritesContract.FavouriteEntry.TABLE_FAVOURITES + "'");

        // re-create database
        onCreate(sqLiteDatabase);
    }
}
