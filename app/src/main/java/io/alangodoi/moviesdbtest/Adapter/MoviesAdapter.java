package io.alangodoi.moviesdbtest.Adapter;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.List;

import io.alangodoi.moviesdbtest.Adapter.Viewholder.MoviesHolder;
import io.alangodoi.moviesdbtest.Helper.Helper;
import io.alangodoi.moviesdbtest.Model.Movie;
import io.alangodoi.moviesdbtest.R;

public class MoviesAdapter extends RecyclerView.Adapter<MoviesHolder>{

    private Context mContext;
    private List<Movie> movieList;
    private MoviesHolder.OnMovieListener mOnMovieListener;
    private Helper helper = new Helper();

    public MoviesAdapter(Context mContext, List<Movie> movieList, MoviesHolder.OnMovieListener onMovieListener) {
        this.mContext = mContext;
        this.movieList = movieList;
        this.mOnMovieListener = onMovieListener;
    }


    @NonNull
    @Override
    public MoviesHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.movie_card, viewGroup, false);
        return new MoviesHolder(view, mOnMovieListener);
    }

    @Override
    public void onBindViewHolder(final MoviesHolder holder, int position) {
        Movie movie = movieList.get(position);

        holder.title.setText(movie.getTitle());

        Glide.with(mContext).load(helper.imagesURL + movie.getPoster())
                .placeholder(R.drawable.loading_animation)
                .error(R.drawable.popcorn)
                .skipMemoryCache(false)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .into(holder.poster);
    }

    @Override
    public int getItemCount() {
        return movieList != null ? movieList.size() : 0;
    }
}
