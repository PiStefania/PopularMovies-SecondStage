package com.example.android.popularmovies;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.preference.PreferenceManager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.example.android.popularmovies.Utils.ItemDecoration;
import com.example.android.popularmovies.Utils.MoviesJsonUtils;
import com.example.android.popularmovies.Utils.NetworkUtils;

import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements MovieAdapter.MovieAdapterOnClickHandler,LoaderManager.LoaderCallbacks<ArrayList<Movie>>,SharedPreferences.OnSharedPreferenceChangeListener{

    private static final String LOG_TAG = MainActivity.class.getSimpleName();

    private MovieAdapter moviesAdapter;
    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;

    private LinearLayout errorConnection;
    private LinearLayout moviesNotFound;
    private ProgressBar loadingIndicator;
    private ArrayList<Movie> mMovies;

    private static final int MOVIES_LOADER_ID = 1;
    private static boolean PREFERENCES_UPDATED = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        errorConnection = (LinearLayout)findViewById(R.id.connection_error);
        moviesNotFound = (LinearLayout)findViewById(R.id.movies_not_found);
        loadingIndicator = (ProgressBar)findViewById(R.id.loading_indicator);

        mMovies = new ArrayList<>();

        moviesAdapter = new MovieAdapter(this,mMovies,this);

        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        int spacingInPixels = getResources().getDimensionPixelSize(R.dimen.spacing_col);
        mRecyclerView.addItemDecoration(new ItemDecoration(spacingInPixels));
        mRecyclerView.setHasFixedSize(true);

        mLayoutManager = new GridLayoutManager(this,2,GridLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(moviesAdapter);

        LoaderManager.LoaderCallbacks<ArrayList<Movie>> callback = MainActivity.this;

        PreferenceManager.getDefaultSharedPreferences(this)
                .registerOnSharedPreferenceChangeListener(this);

        errorConnection.setVisibility(View.INVISIBLE);
        mRecyclerView.setVisibility(View.VISIBLE);
        getSupportLoaderManager().initLoader(MOVIES_LOADER_ID, null, callback);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_settings, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            Intent startSettingsActivity = new Intent(this, SettingsActivity.class);
            startActivity(startSettingsActivity);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(String movieTitle) {
        Intent intentDetails = new Intent(this, DetailsActivity.class);
        intentDetails.putExtra(Intent.EXTRA_TEXT, movieTitle);
        startActivity(intentDetails);
    }

    @Override
    public Loader<ArrayList<Movie>> onCreateLoader(int i, Bundle bundle) {
         return new AsyncTaskLoader<ArrayList<Movie>>(this) {

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
                URL movieUrl = NetworkUtils.buildUrl(getPreferenceOption(MainActivity.this));

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
            errorConnection.setVisibility(View.VISIBLE);
        }else{
            errorConnection.setVisibility(View.INVISIBLE);
            mRecyclerView.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onLoaderReset(Loader<ArrayList<Movie>> loader) {
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
    protected void onStart() {
        super.onStart();
        if (PREFERENCES_UPDATED) {
            getSupportLoaderManager().restartLoader(MOVIES_LOADER_ID, null, this);
            PREFERENCES_UPDATED = false;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        PreferenceManager.getDefaultSharedPreferences(this)
                .unregisterOnSharedPreferenceChangeListener(this);
    }
}
