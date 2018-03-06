package com.example.android.popularmovies;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.example.android.popularmovies.Utils.ItemDecoration;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements MovieAdapter.MovieAdapterOnClickHandler{

    private static final String LOG_TAG = MainActivity.class.getSimpleName();

    private RecyclerView.Adapter moviesAdapter;
    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;

    ArrayList<Movie> movies;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        movies = new ArrayList<Movie>();

        movies.add(new Movie("Cupcake", null, 5));
        movies.add(new Movie("Donut",  null ,5));
        movies.add(new Movie("Eclair",  null,4));
        movies.add(new Movie("Froyo",  null, 5));
        movies.add(new Movie("GingerBread",null, 5));
        movies.add(new Movie("Honeycomb", null, 5));
        movies.add(new Movie("Honeycomb", null, 5));
        movies.add(new Movie("Honeycomb", null, 5));
        movies.add(new Movie("Honeycomb", null, 5));
        movies.add(new Movie("Honeycomb", null, 5));

        moviesAdapter = new MovieAdapter(this,movies,this);

        // Get a reference to the ListView, and attach this adapter to it.
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        int spacingInPixels = getResources().getDimensionPixelSize(R.dimen.spacing_col);
        mRecyclerView.addItemDecoration(new ItemDecoration(spacingInPixels));

        mLayoutManager = new GridLayoutManager(getApplicationContext(),2);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(moviesAdapter);
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

        Log.e(LOG_TAG,"MOVIETITLE: ");
        Intent intentDetails = new Intent(this, DetailsActivity.class);
        intentDetails.putExtra(Intent.EXTRA_TEXT, movieTitle);

        startActivity(intentDetails);
    }
}
