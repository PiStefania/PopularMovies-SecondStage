package com.example.android.popularmoviesstage2;

import android.os.Parcel;
import android.os.Parcelable;

public class Movie implements Parcelable {
    private int id;
    private String title;
    private String posterUrl;
    private String backdropUrl;
    private double voteAverage;
    private String releaseDate;
    private String synopsis;

    public Movie(int id,String title,String posterUrl,double voteAverage,String backdropUrl,String releaseDate,String synopsis){
        this.id=id;
        this.title = title;
        this.posterUrl = posterUrl;
        this.voteAverage = voteAverage;
        this.backdropUrl = backdropUrl;
        this.releaseDate = releaseDate;
        this.synopsis = synopsis;
    }

    public Movie(Parcel in) {
        this.id=in.readInt();
        this.title = in.readString();
        this.posterUrl = in.readString();
        this.voteAverage = in.readDouble();
        this.backdropUrl = in.readString();
        this.releaseDate = in.readString();
        this.synopsis = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.title);
        dest.writeString(this.posterUrl);
        dest.writeDouble(this.voteAverage);
        dest.writeString(this.backdropUrl);
        dest.writeString(this.releaseDate);
        dest.writeString(this.synopsis);
    }

    public static final Parcelable.Creator<Movie> CREATOR = new Parcelable.Creator<Movie>(){
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };


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

    public double getVoteAverage() {
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setVoteAverage(double voteAverage) {
        this.voteAverage = voteAverage;
    }

    @Override
    public String toString() {
        return "Title: " + title + " Vote Average: " + voteAverage + " image url: " + posterUrl + " backdrop_url:" + backdropUrl + " release date: " + releaseDate + " synopsis: " + synopsis;
    }
}
