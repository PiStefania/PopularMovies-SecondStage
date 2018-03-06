package com.example.android.popularmovies;

/**
 * Created by stefa on 18/7/2017.
 */

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;


public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder> {
    private Context mContext;
    private ArrayList<Movie> mMovies;
    private MovieAdapterOnClickHandler mClickHandler;

    private static final String LOG_TAG = MovieAdapter.class.getSimpleName();

    public MovieAdapter(Context context, ArrayList<Movie> movies,MovieAdapterOnClickHandler clickHandler){
        mContext = context;
        mMovies = movies;
        mClickHandler = clickHandler;
    }

    public interface MovieAdapterOnClickHandler {
        void onClick(String movieTitle);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView mTitle;
        public TextView mVoteAverage;
        public ImageView mThumbnail;
        public ViewHolder(View v){
            super(v);
            mTitle = (TextView) v.findViewById(R.id.title);
            mVoteAverage = (TextView) v.findViewById(R.id.vote_average);
            mThumbnail = (ImageView) v.findViewById(R.id.thumbnail);
            v.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            String movieTitle = mMovies.get(position).getTitle();
            Log.e(LOG_TAG,"MOVIETITLE: " + movieTitle + " pos: " + position);
            mClickHandler.onClick(movieTitle);
        }
    }


    @Override
    public MovieAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        // Create a new View
        View v = LayoutInflater.from(mContext).inflate(R.layout.list_item,parent,false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position){
        holder.mTitle.setText(mMovies.get(position).getTitle());
        holder.mVoteAverage.setText(String.valueOf(mMovies.get(position).getVoteAverage()));
       // holder.mThumbnail.setImageDrawable(mMovies.get(position).getTitle());

    }

    @Override
    public int getItemCount() {
        return mMovies.size();
    }

}