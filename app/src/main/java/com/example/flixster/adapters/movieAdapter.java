package com.example.flixster.adapters;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.example.flixster.MovieDetailsActivity;
import com.example.flixster.R;
import com.example.flixster.models.Movie;
import java.util.List;

import org.parceler.Parcels;

// new since Glide v4

public class movieAdapter extends RecyclerView.Adapter<movieAdapter.ViewHolder> {

    Context context;
    List<Movie> movies;

    public movieAdapter(Context context, List<Movie> movies) {
        this.context = context;
        this.movies = movies;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View movieView = LayoutInflater.from(context).inflate(R.layout.item_movie, parent, false);
        return new ViewHolder(movieView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Movie movie = movies.get(position);
        holder.bind(movie);
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            // gets item position
            int position = getBindingAdapterPosition();
            // make sure the position is valid, i.e. actually exists in the view
            if (position != RecyclerView.NO_POSITION) {
                // get the movie at the position, this won't work if the class is static
                Movie movie = movies.get(position);
                // create intent for the new activity
                Intent intent = new Intent(context, MovieDetailsActivity.class);
                // serialize the movie using parceler, use its short name as a key
                intent.putExtra(Movie.class.getSimpleName(), Parcels.wrap(movie));
                // show the activity
                context.startActivity(intent);
            }
        }

        TextView tvTitle;
        TextView tvSummary;
        ImageView ivPoster;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.titleItem);
            tvSummary = itemView.findViewById(R.id.summaryItem);
            ivPoster = itemView.findViewById(R.id.myImg);
            itemView.setOnClickListener(this);
        }

        public void bind(Movie movie) {
            tvTitle.setText(movie.getTitle());
            tvSummary.setText(movie.getOverview());
            String imageURl;
            if(context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT){
                imageURl = movie.getPosterPath();
            }
            else{
                imageURl = movie.getBackdropPath();
            }
            //Glide.with(context).load(imageURl).placeholder(R.drawable.flicks_movie_placeholder).into(ivPoster);
            Glide.with(context).load(imageURl).placeholder(R.drawable.flicks_movie_placeholder).transform(new CenterCrop(), new RoundedCorners(100)).into(ivPoster);
        }
    }
}
