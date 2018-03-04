package com.example.android.popularmovies;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.GridView;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    private MovieAdapter moviesAdapter;

    Movie[] movies = {
            new Movie("Cupcake", null, 5),
            new Movie("Donut",  null ,5),
            new Movie("Eclair",  null,4),
            new Movie("Froyo",  null, 5),
            new Movie("GingerBread",null, 5),
            new Movie("Honeycomb", null, 5),
            new Movie("Ice Cream Sandwich", null, 5),
            new Movie("Jelly Bean",  null, 5),
            new Movie("KitKat",  null, 5),
            new Movie("Lollipop",  null, 5)
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        moviesAdapter = new MovieAdapter(this, Arrays.asList(movies));

        // Get a reference to the ListView, and attach this adapter to it.
        GridView listView = (GridView) findViewById(R.id.grid_view);
        listView.setAdapter(moviesAdapter);
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
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
