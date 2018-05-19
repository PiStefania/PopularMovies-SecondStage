package com.example.android.popularmoviesstage2;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.preference.PreferenceManager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.ScrollView;

import com.example.android.popularmoviesstage2.Utils.ItemDecoration;
import com.example.android.popularmoviesstage2.Utils.MoviesJsonUtils;
import com.example.android.popularmoviesstage2.Utils.NetworkUtils;

import java.net.URL;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivityFragment extends Fragment implements MovieAdapter.MovieAdapterOnClickHandler,LoaderManager.LoaderCallbacks<ArrayList<Movie>>,SharedPreferences.OnSharedPreferenceChangeListener{

    private static final String LOG_TAG = MainActivity.class.getSimpleName();

    private MovieAdapter moviesAdapter;
    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;

    @BindView(R.id.movies_not_found)
    ScrollView moviesNotFound;
    @BindView(R.id.loading_indicator)
    ProgressBar loadingIndicator;
    private ArrayList<Movie> mMovies;

    private static final int MOVIES_LOADER_ID = 1;
    private static boolean PREFERENCES_UPDATED = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mMovies = new ArrayList<>();
        moviesAdapter = new MovieAdapter(getContext(),mMovies,this);

        View v = inflater.inflate(R.layout.activity_main, container, false);
        ButterKnife.bind(this,v);

        int spacingInPixels = getResources().getDimensionPixelSize(R.dimen.spacing_col);
        mRecyclerView.addItemDecoration(new ItemDecoration(spacingInPixels));
        mRecyclerView.setHasFixedSize(true);

        mLayoutManager = new GridLayoutManager(getContext(),2,GridLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(moviesAdapter);

        LoaderManager.LoaderCallbacks<ArrayList<Movie>> callback = this;

        PreferenceManager.getDefaultSharedPreferences(getContext())
                .registerOnSharedPreferenceChangeListener(this);

        moviesNotFound.setVisibility(View.INVISIBLE);
        mRecyclerView.setVisibility(View.VISIBLE);
        getLoaderManager().initLoader(MOVIES_LOADER_ID, null, callback);

        return v;
    }

    @Override
    public void onClick(Movie movie) {
        Intent intentDetails = new Intent(getContext(), com.example.android.popularmoviesstage2.DetailsActivity.class);
        intentDetails.putExtra("parcel_data", movie);
        startActivity(intentDetails);
    }

    @Override
    public Loader<ArrayList<Movie>> onCreateLoader(int i, Bundle bundle) {
        return new AsyncTaskLoader<ArrayList<Movie>>(getContext()) {

            ArrayList<Movie> movies = null;

            @Override
            protected void onStartLoading() {
                if (movies != null) {
                    movies = null;
                } else {
                    loadingIndicator.setVisibility(View.VISIBLE);
                    forceLoad();
                }
            }

            @Override
            public ArrayList<Movie> loadInBackground() {
                URL movieUrl = NetworkUtils.buildUrl(getPreferenceOption(getContext()));

                try {
                    String jsonResponse = NetworkUtils.getResponseFromHttpUrl(movieUrl);

                    ArrayList<Movie> jsonResults = MoviesJsonUtils.getResultsFromJson(jsonResponse);

                    return jsonResults;
                } catch (Exception e) {
                    e.printStackTrace();
                    return null;
                }
            }
        };
    }

    @Override
    public void onLoadFinished(Loader<ArrayList<Movie>> loader, ArrayList<Movie> movies) {
        loadingIndicator.setVisibility(View.INVISIBLE);
        moviesAdapter.setMovieData(movies);
        if(movies == null || movies.size()==0){
            mRecyclerView.setVisibility(View.INVISIBLE);
            moviesNotFound.setVisibility(View.VISIBLE);
        }else{
            moviesNotFound.setVisibility(View.INVISIBLE);
            mRecyclerView.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onLoaderReset(Loader<ArrayList<Movie>> loader) {
        moviesAdapter.setMovieData(null);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String s) {
        PREFERENCES_UPDATED = true;
    }

    private static int getPreferenceOption(Context context){
        SharedPreferences prefs = PreferenceManager
                .getDefaultSharedPreferences(context);
        String keyForLocation = context.getString(R.string.pref_sort_movie_key);
        String defaultLocation = context.getString(R.string.pref_sort_option_popular);
        String preference = prefs.getString(keyForLocation, defaultLocation);
        if(preference.equals(context.getString(R.string.pref_sort_movies_popular))){
            return 1;
        }else{
            return 0;
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        if (PREFERENCES_UPDATED) {
            getLoaderManager().restartLoader(MOVIES_LOADER_ID, null, this);
            PREFERENCES_UPDATED = false;
        }
    }

    @Override
    public void onPause(){
        super.onPause();
    }
    @Override
    public void onResume(){
        super.onResume();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        PreferenceManager.getDefaultSharedPreferences(getContext())
                .unregisterOnSharedPreferenceChangeListener(this);
    }

}