package com.example.pbeekman.flicks.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.pbeekman.flicks.R;
import com.example.pbeekman.flicks.models.Movie;
import com.squareup.picasso.Picasso;

import java.util.List;

import jp.wasabeef.picasso.transformations.RoundedCornersTransformation;

/**
 * Created by pbeekman on 6/15/16.
 */
public class MovieArrayAdapter extends ArrayAdapter<Movie> {

    public MovieArrayAdapter(Context context, List<Movie> movies) {
        super(context, android.R.layout.simple_list_item_1, movies);
    }

    private View getInflatedLayoutForMovie(Movie m) {
        if (m.isPopular() && m.hasBackdrop())
                return LayoutInflater.from(getContext()).inflate(R.layout.item_movie2, null);
        return LayoutInflater.from(getContext()).inflate(R.layout.item_movie, null);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Movie movie = getItem(position);
        convertView = getInflatedLayoutForMovie(movie);

        // Give the layout a loading bar
        final ProgressBar loading = (ProgressBar) convertView.findViewById(R.id.loading);
        com.squareup.picasso.Callback cb = new com.squareup.picasso.Callback() {
            @Override
            public void onSuccess() {
                loading.setVisibility(View.GONE);
            }

            @Override
            public void onError() {
            }
        };

        // Try loading the poster image
        try {
            ImageView ivPoster = (ImageView) convertView.findViewById(R.id.ivPoster);
            ivPoster.setImageResource(0);
            (Picasso.with(getContext()).load(movie.getPosterPath())
            ).transform(new RoundedCornersTransformation(10, 10)).into(ivPoster, cb);
        }

        // If the poster image is not in the layout, then load the background image
        catch (NullPointerException e) {
            if (movie.hasBackdrop()) {
                ImageView ivBack = (ImageView) convertView.findViewById(R.id.ivBack);
                ivBack.setImageResource(0);
                ((Picasso.with(getContext()).load(movie.getBackdropPath())
                ).transform(new RoundedCornersTransformation(10, 10))
                ).resize(800, 450).into(ivBack, cb);
            }
            else {
                ImageView ivPoster = (ImageView) convertView.findViewById(R.id.ivBack);
                ivPoster.setImageResource(0);
                (Picasso.with(getContext()).load(movie.getPosterPath())
                ).transform(new RoundedCornersTransformation(10, 10)).into(ivPoster, cb);
            }
        }

        // If the title and overview remain in the layout, set them.
        try {
            TextView tvTitle = (TextView) convertView.findViewById(R.id.tvTitle);
            TextView tvOverview = (TextView) convertView.findViewById(R.id.tvOverview);
            tvTitle.setText(movie.getTitle());

            String text = movie.getOverview();
            // Ensure that the overview text is less than 80 characters
            text = text.substring(0, Math.min(text.length(), 80));
            // Cut off text at the last complete word
            text = text.substring(0, text.lastIndexOf(" ")) + "...";
            tvOverview.setText(text);
        } catch (NullPointerException e) {}
        return convertView;
    }

}
