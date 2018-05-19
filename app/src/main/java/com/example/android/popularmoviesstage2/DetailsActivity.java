package com.example.android.popularmoviesstage2;

import android.content.ActivityNotFoundException;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.popularmoviesstage2.Utils.ItemDecoration;
import com.example.android.popularmoviesstage2.Utils.MoviesJsonUtils;
import com.example.android.popularmoviesstage2.Utils.NetworkUtils;
import com.example.android.popularmoviesstage2.data.FavouritesContract;
import com.squareup.picasso.Picasso;

import java.net.URL;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailsActivity extends AppCompatActivity implements TrailerAdapter.TrailerAdapterOnClickHandler,ReviewAdapter.ReviewAdapterOnClickHandler{

    private TrailerAdapter trailersAdapter;
    private ReviewAdapter reviewsAdapter;
    private ArrayList<Trailer> mTrailers;
    private ArrayList<Review> mReviews;
    private RecyclerView.LayoutManager mLayoutManagerTrailers;
    private RecyclerView.LayoutManager mLayoutManagerReviews;

    @BindView(R.id.title_text_view) TextView mTitle;
    @BindView(R.id.release_date_text_view) TextView mReleaseDate;
    @BindView(R.id.synopsis_text_view) TextView mSynopsis;
    @BindView(R.id.vote_average_text_view) TextView mVoteAverage;
    @BindView(R.id.poster_image_view) ImageView mPoster;
    @BindView(R.id.favourite) ImageView mFavourite;
    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.recycler_view_trailers) RecyclerView mRecyclerViewTrailers;
    @BindView(R.id.recycler_view_reviews) RecyclerView mRecyclerViewReviews;

    private static final int TRAILERS_LOADER_ID = 2;
    private static final int REVIEWS_LOADER_ID = 3;
    private Movie movie;
    private Context context = DetailsActivity.this;
    private boolean flagFavourite=true;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        ButterKnife.bind(this);
        movie = (Movie) getIntent().getParcelableExtra("parcel_data");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        LoadPreferences();
        mTitle.setText(movie.getTitle());
        mReleaseDate.setText(movie.getReleaseDate());
        mSynopsis.setText(movie.getSynopsis());
        mVoteAverage.setText(String.valueOf(movie.getVoteAverage()));
        Picasso.with(this).load(movie.getBackdropUrl()).into(mPoster);

        mFavourite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setFavourite();
            }
        });

        mTrailers = new ArrayList<>();
        mReviews = new ArrayList<>();

        trailersAdapter = new TrailerAdapter(this,mTrailers,this);
        reviewsAdapter = new ReviewAdapter(this,mReviews,this);

        int spacingInPixels = getResources().getDimensionPixelSize(R.dimen.margin_views);
        mRecyclerViewReviews.addItemDecoration(new ItemDecoration(spacingInPixels));
        mRecyclerViewReviews.setHasFixedSize(true);

        mLayoutManagerTrailers = new LinearLayoutManager(this);
        mLayoutManagerReviews = new LinearLayoutManager(this);
        mRecyclerViewTrailers.setLayoutManager(mLayoutManagerTrailers);
        mRecyclerViewReviews.setLayoutManager(mLayoutManagerReviews);
        mRecyclerViewTrailers.setAdapter(trailersAdapter);
        mRecyclerViewReviews.setAdapter(reviewsAdapter);
        getSupportLoaderManager().initLoader(TRAILERS_LOADER_ID, null, dataTrailers);
        getSupportLoaderManager().initLoader(REVIEWS_LOADER_ID, null, dataReviews);

        mFavourite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setFavourite();
            }
        });
    }

    @Override
    public void onClick(Trailer trailer) {
        Intent appIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:" + trailer.getKey()));
        Intent webIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.youtube.com/watch?v=" + trailer.getKey()));
        try {
            startActivity(appIntent);
        } catch (ActivityNotFoundException ex) {
            startActivity(webIntent);
        }
    }

    private LoaderManager.LoaderCallbacks<ArrayList<Trailer>> dataTrailers = new LoaderManager.LoaderCallbacks<ArrayList<Trailer>>() {

        @Override
        public Loader<ArrayList<Trailer>> onCreateLoader(int i, Bundle bundle) {
            return new AsyncTaskLoader<ArrayList<Trailer>>(context) {


                ArrayList<Movie> trailers = null;

                @Override
                protected void onStartLoading() {
                    if (trailers != null) {
                        trailers = null;
                    } else {
                        forceLoad();
                    }
                }

                @Override
                public ArrayList<Trailer> loadInBackground() {
                    URL trailerUrl = NetworkUtils.buildMovieVideosUrl(movie.getId());

                    try {
                        String jsonResponse = NetworkUtils.getResponseFromHttpUrl(trailerUrl);

                        ArrayList<Trailer> jsonResults = MoviesJsonUtils.getResultsFromJsonTrailers(jsonResponse);

                        return jsonResults;
                    } catch (Exception e) {
                        e.printStackTrace();
                        return null;
                    }
                }
            };
        }

        @Override
        public void onLoadFinished(Loader<ArrayList<Trailer>> loader, ArrayList<Trailer> trailers) {
            trailersAdapter.setTrailerData(trailers);
        }

        @Override
        public void onLoaderReset(Loader<ArrayList<Trailer>> loader) {
            trailersAdapter.setTrailerData(null);
        }

    };

    private LoaderManager.LoaderCallbacks<ArrayList<Review>> dataReviews = new LoaderManager.LoaderCallbacks<ArrayList<Review>>() {

        @Override
        public Loader<ArrayList<Review>> onCreateLoader(int i, Bundle bundle) {
            return new AsyncTaskLoader<ArrayList<Review>>(context) {


                ArrayList<Movie> reviews = null;

                @Override
                protected void onStartLoading() {
                    if (reviews != null) {
                        reviews = null;
                    } else {
                        forceLoad();
                    }
                }

                @Override
                public ArrayList<Review> loadInBackground() {
                    URL reviewUrl = NetworkUtils.buildMovieReviewsUrl(movie.getId());

                    try {
                        String jsonResponse = NetworkUtils.getResponseFromHttpUrl(reviewUrl);

                        ArrayList<Review> jsonResults = MoviesJsonUtils.getResultsFromJsonReviews(jsonResponse);

                        return jsonResults;
                    } catch (Exception e) {
                        e.printStackTrace();
                        return null;
                    }
                }
            };
        }

        @Override
        public void onLoadFinished(Loader<ArrayList<Review>> loader, ArrayList<Review> reviews) {
            reviewsAdapter.setTrailerData(reviews);
        }

        @Override
        public void onLoaderReset(Loader<ArrayList<Review>> loader) {
            reviewsAdapter.setTrailerData(null);
        }

    };



    @Override
    public void onClick(Review review) {

    }

    @Override
    protected void onPause(){
        super.onPause();
    }
    @Override
    protected void onResume(){
        super.onResume();
    }

    private void setFavourite(){
        if(!flagFavourite){
            mFavourite.setImageDrawable(this.getResources().getDrawable(R.drawable.round_star_border_white_48dp));
            //delete from database
            String[] args = {String.valueOf(movie.getId())};
            int deletion = getContentResolver().delete(FavouritesContract.FavouriteEntry.CONTENT_URI,"id_movie=?", args);
            if(deletion > 0){
                Toast.makeText(this,"Removed from favourites",Toast.LENGTH_SHORT).show();
            }
            DeletePreferences();
        }else{
            mFavourite.setImageDrawable(this.getResources().getDrawable(R.drawable.star_filled));
            //insert to database
            ContentValues values = new ContentValues();
            values.put(FavouritesContract.FavouriteEntry.COLUMN_ID_MOVIE,movie.getId());
            values.put(FavouritesContract.FavouriteEntry.COLUMN_TITLE_MOVIE,movie.getTitle());
            values.put(FavouritesContract.FavouriteEntry.COLUMN_POSTER_MOVIE,movie.getPosterUrl());
            values.put(FavouritesContract.FavouriteEntry.COLUMN_BACKDROP_MOVIE,movie.getBackdropUrl());
            values.put(FavouritesContract.FavouriteEntry.COLUMN_SYNOPSIS_MOVIE,movie.getSynopsis());
            values.put(FavouritesContract.FavouriteEntry.COLUMN_RATING_MOVIE,movie.getVoteAverage());
            values.put(FavouritesContract.FavouriteEntry.COLUMN_RELEASE_MOVIE,movie.getReleaseDate());
            getContentResolver().insert(FavouritesContract.FavouriteEntry.CONTENT_URI, values);
            Toast.makeText(this,"Added to favourites",Toast.LENGTH_SHORT).show();
            SavePreferences();
        }
    }

    void setDrawableFav(){
        if(flagFavourite){
            mFavourite.setImageDrawable(this.getResources().getDrawable(R.drawable.round_star_border_white_48dp));
            flagFavourite = true;
        }else{
            mFavourite.setImageDrawable(this.getResources().getDrawable(R.drawable.star_filled));
            flagFavourite = false;
        }
    }

    private void SavePreferences(){
        SharedPreferences sharedPreferences = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        if(!sharedPreferences.contains("id_"+movie.getId())){
            editor.putInt("id_" + movie.getId(), movie.getId());
        }
        editor.apply();
    }

    private void DeletePreferences(){
        SharedPreferences sharedPreferences = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        if(sharedPreferences.contains("id_"+movie.getId())) {
            editor.remove("id_" + movie.getId());
        }
        editor.apply();
    }

    private void LoadPreferences(){
        SharedPreferences sharedPreferences = getPreferences(MODE_PRIVATE);
        int id = 0;
        if(sharedPreferences.contains("id_"+movie.getId())){
            id = sharedPreferences.getInt("id_"+movie.getId(),0);
        }
        if(id == 0){
            flagFavourite = true;
        }else {
            flagFavourite = false;
        }
        setDrawableFav();
    }
}
