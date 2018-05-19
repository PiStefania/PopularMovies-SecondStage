package com.example.android.popularmoviesstage2;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.android.popularmoviesstage2.data.FavouritesContract;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FavouritesAdapter extends CursorAdapter {

    private static final String LOG_TAG = FavouritesAdapter.class.getSimpleName();
    Context mContext;

    class ViewHolder {
        @BindView(R.id.thumbnail)
        ImageView mThumbnail;

        public ViewHolder(View v) {
            ButterKnife.bind(this, v);
        }
    }

    public FavouritesAdapter(Context context, Cursor c, int flags, int loaderID) {
        super(context, c, flags);
        mContext = context;
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.list_item, parent, false);
        int orientation = mContext.getResources().getConfiguration().orientation;
        int height;
        if (orientation == 1) {
            height = parent.getMeasuredHeight() / 2;
        } else {
            height = parent.getMeasuredHeight();
        }
        v.setMinimumHeight(height);
        ViewHolder viewHolder = new ViewHolder(v);
        v.setTag(viewHolder);
        return v;
    }

    @Override
    public void bindView(View view, Context context, final Cursor cursor) {
        ViewHolder viewHolder = (ViewHolder) view.getTag();
        int imageIndex = cursor.getColumnIndex(FavouritesContract.FavouriteEntry.COLUMN_POSTER_MOVIE);
        String image = cursor.getString(imageIndex);
        Picasso.with(mContext).load(image).into(viewHolder.mThumbnail);

        int id = cursor.getInt(cursor.getColumnIndex("_id"));
        final String title = cursor.getString(cursor.getColumnIndexOrThrow("title_movie"));
        final Uri uri = Uri.parse(FavouritesContract.FavouriteEntry.CONTENT_URI + "/" + id);

        viewHolder.mThumbnail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentDetails = new Intent(mContext, com.example.android.popularmoviesstage2.DetailsActivity.class);
                if(cursor != null) {
                    intentDetails.putExtra("parcel_data", returnSpecificMovie(cursor));
                    mContext.startActivity(intentDetails);
                }
            }
        });
    }

    private Movie returnSpecificMovie(Cursor returnCursor){
        returnCursor.moveToFirst();
        int idIndex = returnCursor.getColumnIndex(FavouritesContract.FavouriteEntry.COLUMN_ID_MOVIE);
        int titleIndex = returnCursor.getColumnIndex(FavouritesContract.FavouriteEntry.COLUMN_TITLE_MOVIE);
        int posterIndex = returnCursor.getColumnIndex(FavouritesContract.FavouriteEntry.COLUMN_POSTER_MOVIE);
        int backdropIndex = returnCursor.getColumnIndex(FavouritesContract.FavouriteEntry.COLUMN_BACKDROP_MOVIE);
        int synopsisIndex = returnCursor.getColumnIndex(FavouritesContract.FavouriteEntry.COLUMN_SYNOPSIS_MOVIE);
        int ratingIndex = returnCursor.getColumnIndex(FavouritesContract.FavouriteEntry.COLUMN_RATING_MOVIE);
        int releaseIndex = returnCursor.getColumnIndex(FavouritesContract.FavouriteEntry.COLUMN_RELEASE_MOVIE);

        int idMovie = returnCursor.getInt(idIndex);
        String title = returnCursor.getString(titleIndex);
        String poster = returnCursor.getString(posterIndex);
        String backdrop = returnCursor.getString(backdropIndex);
        String synopsis = returnCursor.getString(synopsisIndex);
        double rating = returnCursor.getDouble(ratingIndex);
        String release = returnCursor.getString(releaseIndex);

        return new Movie(idMovie, title, poster, rating, backdrop, release, synopsis);
    }

}
