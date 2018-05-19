package com.example.android.popularmoviesstage2.data;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

public class FavouritesContract{

    public static final String CONTENT_AUTHORITY = "com.example.android.popularmoviesstage2";

    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);


    public static final class FavouriteEntry implements BaseColumns{
        // table name
        public static final String TABLE_FAVOURITES = "favourites";
        // columns
        public static final String _ID = "_id";
        public static final String COLUMN_ID_MOVIE = "id_movie";
        public static final String COLUMN_TITLE_MOVIE = "title_movie";
        public static final String COLUMN_POSTER_MOVIE = "poster_movie";
        public static final String COLUMN_BACKDROP_MOVIE = "backdrop_movie";
        public static final String COLUMN_SYNOPSIS_MOVIE = "synopsis_movie";
        public static final String COLUMN_RATING_MOVIE = "rating_movie";
        public static final String COLUMN_RELEASE_MOVIE = "release_movie";

        // create content uri
        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon()
                .appendPath(TABLE_FAVOURITES).build();
        // create cursor of base type directory for multiple entries
        public static final String CONTENT_DIR_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + TABLE_FAVOURITES;
        // create cursor of base type item for single entry
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE +"/" + CONTENT_AUTHORITY + "/" + TABLE_FAVOURITES;

        // for building URIs on insertion
        public static Uri buildFavouritesUri(long id){
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }
    }
}