package com.example.android.popularmovies;

/**
 * Created by stefa on 4/3/2018.
 */

public class Movie {
    private String title;
    private String image;
    private int voteAverage;

    public Movie(String title,String image,int voteAverage){
        this.title = title;
        this.image = image;
        this.voteAverage = voteAverage;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setVoteAverage(int voteAverage) {
        this.voteAverage = voteAverage;
    }

    public String getTitle() {
        return title;
    }

    public String getImage() {
        return image;
    }

    public int getVoteAverage() {
        return voteAverage;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
