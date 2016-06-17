package com.example.pbeekman.flicks.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by pbeekman on 6/15/16.
 */
public class Movie {

    String title;
    String overview;
    String posterPath;
    String backdropPath;
    float voteAverage;
    Double popularity;

    public String getTitle() {
        return title;
    }

    public String getOverview() {
        return overview;
    }

    public String getPosterPath() {
        return String.format("https://image.tmdb.org/t/p/w342/%s", posterPath);
    }

    public String getBackdropPath() {
        return String.format("https://image.tmdb.org/t/p/w342/%s", backdropPath);
    }

    public float getVoteAverage() {
        return voteAverage;
    }

    public boolean isPopular() {
        return popularity > 18;
    }

    public boolean hasBackdrop() {
        return !backdropPath.equals("null");
    }

    public Movie (JSONObject j) throws JSONException {
        posterPath = j.getString("poster_path");
        backdropPath = j.getString("backdrop_path");
        title = j.getString("original_title");
        overview = j.getString("overview");
        voteAverage = (float) j.getDouble("vote_average");
        popularity = j.getDouble("popularity");
    }

    public static ArrayList<Movie> fromJSONArray(JSONArray array) {
        ArrayList<Movie> movies = new ArrayList<>();
        for (int i = 0; i < array.length(); i++) {
            try {
                movies.add(new Movie(array.getJSONObject(i)));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return movies;
    }
}
