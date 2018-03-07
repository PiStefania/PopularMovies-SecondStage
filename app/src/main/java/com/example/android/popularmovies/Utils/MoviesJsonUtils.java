package com.example.android.popularmovies.Utils;

import com.example.android.popularmovies.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public final class MoviesJsonUtils {

    public static int STATUS_CODE;

    private static final String LOG_TAG = MoviesJsonUtils.class.getSimpleName();
    public static ArrayList<Movie> getResultsFromJson(String moviesJsonStr) throws JSONException {

        final int HTTP_NOT_FOUND = 7;
        final int HTTP_UNAUTHORIZED = 34;
        final String STATUS_CODE_TAG = "status_code";
        final String RESULTS_TAG = "results";
        final String TITLE_TAG = "title";
        final String VOTE_AVERAGE_TAG = "vote_average";
        final String IMAGE_TAG = "poster_path";
        final String RELEASE_DATE_TAG = "release_date";
        final String SYNOPSIS_TAG = "overview";
        final String BACKDROP_URL_TAG = "backdrop_path";

        ArrayList<Movie> movies = new ArrayList<>();
        JSONObject movieJson = new JSONObject(moviesJsonStr);

        //error
        if (movieJson.has(STATUS_CODE_TAG)) {
            STATUS_CODE = movieJson.getInt(STATUS_CODE_TAG);
            return null;
        }

        JSONArray movieArrayResults = movieJson.getJSONArray(RESULTS_TAG);

        for (int i = 0; i < movieArrayResults.length(); i++) {
            JSONObject movieDetails = movieArrayResults.getJSONObject(i);

            String title = movieDetails.getString(TITLE_TAG);
            double voteAverage = movieDetails.getDouble(VOTE_AVERAGE_TAG);
            String imageUrl = returnFormattedImageUrlThumbnail(movieDetails.getString(IMAGE_TAG));
            String backdropUrl = returnFormattedImageUrlBackdrop(movieDetails.getString(BACKDROP_URL_TAG));
            String releaseDate = movieDetails.getString(RELEASE_DATE_TAG);
            String synopsis = movieDetails.getString(SYNOPSIS_TAG);

            Movie movie = new Movie(title,imageUrl,voteAverage,backdropUrl,releaseDate,synopsis);
            movies.add(movie);
        }

        return movies;
    }

    private static String returnFormattedImageUrlThumbnail(String posterPath){
        String url =  "http://image.tmdb.org/t/p/";
        url += "w342";
        url += posterPath;
        return url;
    }

    private static String returnFormattedImageUrlBackdrop(String posterPath){
        String url =  "http://image.tmdb.org/t/p/";
        url += "w780";
        url += posterPath;
        return url;
    }
}
