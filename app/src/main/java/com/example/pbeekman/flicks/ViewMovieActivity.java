package com.example.pbeekman.flicks;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import jp.wasabeef.picasso.transformations.RoundedCornersTransformation;

/**
 * Created by pbeekman on 6/16/16.
 */
public class ViewMovieActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movie_info);

        Bundle extras = getIntent().getExtras();
        String backDropPath = extras.getString("backdrop_path");
        String posterPath = extras.getString("poster_path");
        String title = extras.getString("title");
        String overview = extras.getString("overview");
        Float rating = extras.getFloat("rating");

        RatingBar ratingBar = (RatingBar) findViewById(R.id.ratingBar);
        ratingBar.setRating(rating);


        TextView tvTitle = (TextView) findViewById(R.id.ivTitle);
        tvTitle.setText(title);

        TextView tvOverview = (TextView) findViewById(R.id.ivOverview);
        tvOverview.setText(overview);

        ImageView ivBack = (ImageView) findViewById(R.id.ivBack);

        // Give each image a loading bar
        final ProgressBar loading = (ProgressBar) findViewById(R.id.loading);
        com.squareup.picasso.Callback cb = new com.squareup.picasso.Callback() {
            @Override
            public void onSuccess() {
                loading.setVisibility(View.GONE);
            }

            @Override
            public void onError() {
            }
        };

        // Load the backdrop only if the movie has a backdrop; otherwise, load the poster.
        if (!backDropPath.equals("https://image.tmdb.org/t/p/w342/null")) {
            ((Picasso.with(this).load(backDropPath)
            ).transform(new RoundedCornersTransformation(10, 10))
            ).resize(800, 450).into(ivBack, cb);
        } else {
            ((Picasso.with(this).load(posterPath)
            ).transform(new RoundedCornersTransformation(10, 10))
            ).into(ivBack, cb);
        }
    }

    public void onBack(View v) {
        Intent i = new Intent(ViewMovieActivity.this, MainActivity.class);
        startActivity(i);
        this.finish();
    }
}
