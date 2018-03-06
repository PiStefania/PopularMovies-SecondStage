package com.example.android.popularmovies.Utils;

import com.example.android.popularmovies.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.HttpURLConnection;
import java.util.ArrayList;

public final class MoviesJsonUtils {

    private static final String LOG_TAG = MoviesJsonUtils.class.getSimpleName();
    public static ArrayList<Movie> getResultsFromJson(String moviesJsonStr) throws JSONException {

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
            int statusCode = movieJson.getInt(STATUS_CODE_TAG);

            switch (statusCode) {
                case HttpURLConnection.HTTP_OK:
                    break;
                case HttpURLConnection.HTTP_NOT_FOUND:
                    return null;
                case HttpURLConnection.HTTP_UNAUTHORIZED:
                    return null;
                default:
                    return null;
            }
        }

        JSONArray movieArrayResults = movieJson.getJSONArray(RESULTS_TAG);

        for (int i = 0; i < movieArrayResults.length(); i++) {
            JSONObject movieDetails = movieArrayResults.getJSONObject(i);

            String title = movieDetails.getString(TITLE_TAG);
            int voteAverage = movieDetails.getInt(VOTE_AVERAGE_TAG);
            String imageUrl = movieDetails.getString(IMAGE_TAG);
            imageUrl = returnFormattedImageUrl(imageUrl);

            Movie movie = new Movie(title,imageUrl,voteAverage);
            movies.add(movie);
        }

        return movies;
    }

    private static String returnFormattedImageUrl(String posterPath){
        String url =  "http://image.tmdb.org/t/p/";
        url += "w342";
        url += posterPath;
        return url;
    }
}
