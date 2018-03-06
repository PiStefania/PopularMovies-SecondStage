package com.example.android.popularmovies;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

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
        void onClick(Movie movie);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public ImageView mThumbnail;
        public ViewHolder(View v){
            super(v);
            mThumbnail = (ImageView) v.findViewById(R.id.thumbnail);
            v.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            mClickHandler.onClick(mMovies.get(position));
        }
    }


    @Override
    public MovieAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        // Create a new View
        View v = LayoutInflater.from(mContext).inflate(R.layout.list_item,parent,false);
        int height = parent.getMeasuredHeight() / 2;
        v.setMinimumHeight(height);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position){
        Picasso.with(mContext).load(mMovies.get(position).getPosterUrl()).into(holder.mThumbnail);
    }

    @Override
    public int getItemCount() {
        if(mMovies == null)
            return 0;
        return mMovies.size();
    }

    public void setMovieData(ArrayList<Movie> movies) {
        mMovies = movies;
        notifyDataSetChanged();
    }
}