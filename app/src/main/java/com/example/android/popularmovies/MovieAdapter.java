package com.example.android.popularmovies;

/**
 * Created by stefa on 18/7/2017.
 */

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;


public class MovieAdapter extends ArrayAdapter<Movie> {


    private static final String LOG_TAG = MovieAdapter.class.getSimpleName();


    public MovieAdapter(Activity context, List<Movie> movies) {
        super(context, 0, movies);
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Movie movie = getItem(position);
        ViewHolder viewHolder;

        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.list_item, parent, false);
            viewHolder.title = (TextView) convertView.findViewById(R.id.title);
            viewHolder.voteAverage = (TextView) convertView.findViewById(R.id.vote_average);
            viewHolder.thumbnail = (ImageView) convertView.findViewById(R.id.thumbnail);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.title.setText(movie.getTitle());
        viewHolder.voteAverage.setText(String.valueOf(movie.getVoteAverage()));
        //viewHolder.thumbnail.setImageDrawable(movie.getImage());
        // Return the completed view to render on screen
        return convertView;
    }

    class ViewHolder {
        private TextView title;
        private TextView voteAverage;
        private ImageView thumbnail;
    }

}