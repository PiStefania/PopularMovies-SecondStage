package com.example.android.popularmoviesstage2.Utils;

import com.example.android.popularmoviesstage2.Movie;
import com.example.android.popularmoviesstage2.Review;
import com.example.android.popularmoviesstage2.Trailer;

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
        final String ID_TAG = "id";
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

            int id=movieDetails.getInt(ID_TAG);
            String title = movieDetails.getString(TITLE_TAG);
            double voteAverage = movieDetails.getDouble(VOTE_AVERAGE_TAG);
            String imageUrl = returnFormattedImageUrlThumbnail(movieDetails.getString(IMAGE_TAG));
            String backdropUrl = returnFormattedImageUrlBackdrop(movieDetails.getString(BACKDROP_URL_TAG));
            String releaseDate = movieDetails.getString(RELEASE_DATE_TAG);
            String synopsis = movieDetails.getString(SYNOPSIS_TAG);

            Movie movie = new Movie(id,title,imageUrl,voteAverage,backdropUrl,releaseDate,synopsis);
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

    public static ArrayList<Trailer> getResultsFromJsonTrailers(String movieVideosJsonStr) throws JSONException {
        final String STATUS_CODE_TAG = "status_code";
        final String RESULTS_TAG = "results";
        final String KEY_VIDEO = "key";

        ArrayList<Trailer> trailers = new ArrayList<>();
        JSONObject movieVideosJson = new JSONObject(movieVideosJsonStr);

        //error
        if (movieVideosJson.has(STATUS_CODE_TAG)) {
            STATUS_CODE = movieVideosJson.getInt(STATUS_CODE_TAG);
            return null;
        }

        JSONArray videosArrayResults = movieVideosJson.getJSONArray(RESULTS_TAG);

        for (int i = 0; i < videosArrayResults.length(); i++) {
            JSONObject videos = videosArrayResults.getJSONObject(i);
            String key = videos.getString(KEY_VIDEO);
            Trailer trailer = new Trailer(i+1,key);
            trailers.add(trailer);
        }

        return trailers;
    }

    public static ArrayList<Review> getResultsFromJsonReviews(String movieReviewsJsonStr) throws JSONException {
        final String STATUS_CODE_TAG = "status_code";
        final String RESULTS_TAG = "results";
        final String AUTHOR_REVIEW = "author";
        final String CONTENT_REVIEW = "content";

        ArrayList<Review> reviews = new ArrayList<>();
        JSONObject movieReviewsJson = new JSONObject(movieReviewsJsonStr);

        //error
        if (movieReviewsJson.has(STATUS_CODE_TAG)) {
            STATUS_CODE = movieReviewsJson.getInt(STATUS_CODE_TAG);
            return null;
        }

        JSONArray reviewsArrayResults = movieReviewsJson.getJSONArray(RESULTS_TAG);

        for (int i = 0; i < reviewsArrayResults.length(); i++) {
            JSONObject review = reviewsArrayResults.getJSONObject(i);
            String author = review.getString(AUTHOR_REVIEW).trim();
            String content = review.getString(CONTENT_REVIEW).trim();
            Review rev = new Review(i+1,content,author);
            reviews.add(rev);
        }

        return reviews;
    }
}
