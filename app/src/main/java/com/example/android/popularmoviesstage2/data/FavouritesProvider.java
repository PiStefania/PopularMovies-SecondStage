package com.example.android.popularmoviesstage2.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;


public class FavouritesProvider extends ContentProvider{
    private static final String LOG_TAG = FavouritesProvider.class.getSimpleName();
    private static final UriMatcher sUriMatcher = buildUriMatcher();
    private FavouritesDBHelper mDBHelper;

    //for uri matcher
    private static final int FAVOURITES = 100;
    private static final int FAVOURITES_ID = 200;

    private static UriMatcher buildUriMatcher(){
        final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        final String authority = FavouritesContract.CONTENT_AUTHORITY;

        // add uri for each type
        matcher.addURI(authority, FavouritesContract.FavouriteEntry.TABLE_FAVOURITES, FAVOURITES);
        matcher.addURI(authority, FavouritesContract.FavouriteEntry.TABLE_FAVOURITES + "/#", FAVOURITES_ID);

        return matcher;
    }

    @Override
    public boolean onCreate(){
        mDBHelper = new FavouritesDBHelper(getContext());
        return true;
    }

    @Override
    public String getType(Uri uri){
        final int match = sUriMatcher.match(uri);

        switch (match){
            case FAVOURITES:{
                return FavouritesContract.FavouriteEntry.CONTENT_DIR_TYPE;
            }
            case FAVOURITES_ID:{
                return FavouritesContract.FavouriteEntry.CONTENT_ITEM_TYPE;
            }
            default:{
                throw new UnsupportedOperationException("Unknown uri: " + uri);
            }
        }
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder){
        Cursor retCursor;
        switch(sUriMatcher.match(uri)){

            // All Favourites selected
            case FAVOURITES:{
                retCursor = mDBHelper.getReadableDatabase().query(
                        FavouritesContract.FavouriteEntry.TABLE_FAVOURITES,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder);
                retCursor.setNotificationUri(getContext().getContentResolver(), uri);
                return retCursor;
            }

            // a favourite
            case FAVOURITES_ID:{
                retCursor = mDBHelper.getReadableDatabase().query(
                        FavouritesContract.FavouriteEntry.TABLE_FAVOURITES,
                        projection,
                        FavouritesContract.FavouriteEntry._ID + " = ?",
                        new String[] {String.valueOf(ContentUris.parseId(uri))},
                        null,
                        null,
                        sortOrder);
                retCursor.setNotificationUri(getContext().getContentResolver(), uri);
                return retCursor;
            }
            default:{
                // By default, we assume a bad URI
                throw new UnsupportedOperationException("Unknown uri: " + uri);
            }
        }
    }

    @Override
    public Uri insert(Uri uri, ContentValues values){
        final SQLiteDatabase db = mDBHelper.getWritableDatabase();
        Uri returnUri;
        switch (sUriMatcher.match(uri)) {
            case FAVOURITES: {
                long _id = db.insert(FavouritesContract.FavouriteEntry.TABLE_FAVOURITES, null, values);

                // insert unless it is already contained in the database
                if (_id > 0) {
                    returnUri = FavouritesContract.FavouriteEntry.buildFavouritesUri(_id);
                } else {
                    throw new android.database.SQLException("Failed to insert row into: " + uri);
                }
                break;
            }

            default: {
                throw new UnsupportedOperationException("Unknown uri: " + uri);

            }
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return returnUri;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs){
        final SQLiteDatabase db = mDBHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        int numDeleted;
        switch(match){
            case FAVOURITES:
                numDeleted = db.delete(
                        FavouritesContract.FavouriteEntry.TABLE_FAVOURITES, selection, selectionArgs);

                //reset _ID
                db.execSQL("DELETE FROM SQLITE_SEQUENCE WHERE NAME = '" +
                        FavouritesContract.FavouriteEntry.TABLE_FAVOURITES + "'");
                break;
            case FAVOURITES_ID:
                numDeleted = db.delete(FavouritesContract.FavouriteEntry.TABLE_FAVOURITES,
                        FavouritesContract.FavouriteEntry._ID + " = ?",
                        new String[]{String.valueOf(ContentUris.parseId(uri))});
                //reset _ID
                db.execSQL("DELETE FROM SQLITE_SEQUENCE WHERE NAME = '" +
                        FavouritesContract.FavouriteEntry.TABLE_FAVOURITES + "'");

                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        return numDeleted;
    }

    @Override
    public int update(Uri uri, ContentValues contentValues, String selection, String[] selectionArgs){
        final SQLiteDatabase db = mDBHelper.getWritableDatabase();
        int numUpdated = 0;

        if (contentValues == null){
            throw new IllegalArgumentException("Cannot have null content values");
        }

        switch(sUriMatcher.match(uri)){
            case FAVOURITES:{
                numUpdated = db.update(FavouritesContract.FavouriteEntry.TABLE_FAVOURITES,
                        contentValues,
                        selection,
                        selectionArgs);
                break;
            }
            case FAVOURITES_ID: {
                numUpdated = db.update(FavouritesContract.FavouriteEntry.TABLE_FAVOURITES,
                        contentValues,
                        FavouritesContract.FavouriteEntry._ID + " = ?",
                        new String[] {String.valueOf(ContentUris.parseId(uri))});
                break;
            }
            default:{
                throw new UnsupportedOperationException("Unknown uri: " + uri);
            }
        }

        if (numUpdated > 0){
            getContext().getContentResolver().notifyChange(uri, null);
        }

        return numUpdated;
    }

}