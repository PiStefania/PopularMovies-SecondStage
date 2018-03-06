package com.example.android.popularmovies;

public class Movie {
    private String title;
    private String posterUrl;
    private String backdropUrl;
    private int voteAverage;
    private String releaseDate;
    private String synopsis;

    public Movie(String title,String posterUrl,int voteAverage){
        this.title = title;
        this.posterUrl = posterUrl;
        this.voteAverage = voteAverage;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setPosterUrl(String image) {
        this.posterUrl = image;
    }

    public void setVoteAverage(int voteAverage) {
        this.voteAverage = voteAverage;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public void setSynopsis(String synopsis) {
        this.synopsis = synopsis;
    }

    public void setBackdropUrl(String backdropUrl) {
        this.backdropUrl = backdropUrl;
    }

    public String getTitle() {
        return title;
    }

    public String getPosterUrl() {
        return posterUrl;
    }

    public int getVoteAverage() {
        return voteAverage;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public String getSynopsis() {
        return synopsis;
    }

    public String getBackdropUrl() {
        return backdropUrl;
    }

    @Override
    public String toString() {
        return "Title: " + title + " Vote Average: " + voteAverage + " image url: " + posterUrl;
    }
}
