package com.example.android.popularmovies;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailsActivity extends AppCompatActivity{

    @BindView(R.id.title_text_view) TextView mTitle;
    @BindView(R.id.release_date_text_view) TextView mReleaseDate;
    @BindView(R.id.synopsis_text_view) TextView mSynopsis;
    @BindView(R.id.vote_average_text_view) TextView mVoteAverage;
    @BindView(R.id.poster_image_view) ImageView mPoster;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        ButterKnife.bind(this);
        Movie movie = (Movie) getIntent().getParcelableExtra("parcel_data");

        mTitle.setText(movie.getTitle());
        mReleaseDate.setText(movie.getReleaseDate());
        mSynopsis.setText(movie.getSynopsis());
        mVoteAverage.setText(String.valueOf(movie.getVoteAverage()));
        Picasso.with(this).load(movie.getBackdropUrl()).into(mPoster);
    }
}
