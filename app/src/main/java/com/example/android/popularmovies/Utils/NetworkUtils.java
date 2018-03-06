package com.example.android.popularmovies.Utils;

import android.net.Uri;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;


public final class NetworkUtils {
    private static final String API_KEY = "your api key";
    private static final String LOG_TAG = NetworkUtils.class.getSimpleName();
    private static final String MOVIES_POPULAR_URL = "https://api.themoviedb.org/3/movie/popular";
    private static final String MOVIES_TOP_RATED_URL = "https://api.themoviedb.org/3/movie/top_rated";
    private final static String API_PARAM = "api_key";

    public static URL buildUrl(int preference) {
        String base_url;
        if(preference == 1) {
            base_url = MOVIES_POPULAR_URL;
        }
        else{
            base_url = MOVIES_TOP_RATED_URL;
        }
        Uri builtUri = Uri.parse(base_url).buildUpon()
                .appendQueryParameter(API_PARAM, API_KEY)
                .build();

        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return url;
    }

    public static String getResponseFromHttpUrl(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = urlConnection.getInputStream();
            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }
    }

}
