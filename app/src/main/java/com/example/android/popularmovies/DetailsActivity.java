package com.example.android.popularmovies;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class DetailsActivity extends AppCompatActivity{

    private TextView mTitle;
    private TextView mReleaseDate;
    private TextView mSynopsis;
    private TextView mVoteAverage;
    private ImageView mPoster;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        Movie movie = (Movie) getIntent().getParcelableExtra("parcel_data");

        mTitle = findViewById(R.id.title_text_view);
        mReleaseDate = findViewById(R.id.release_date_text_view);
        mSynopsis = findViewById(R.id.synopsis_text_view);
        mVoteAverage = findViewById(R.id.vote_average_text_view);
        mPoster = findViewById(R.id.poster_image_view);

        mTitle.setText(movie.getTitle());
        mReleaseDate.setText(movie.getReleaseDate());
        mSynopsis.setText(movie.getSynopsis());
        mVoteAverage.setText(String.valueOf(movie.getVoteAverage()));
        Picasso.with(this).load(movie.getBackdropUrl()).into(mPoster);
    }
}
