package io.alangodoi.moviesdbtest.Adapter.Viewholder;

import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.alangodoi.moviesdbtest.R;

public class MoviesHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    @BindView(R.id.title) public TextView title;
    @BindView(R.id.poster) public ImageView poster;
//    public TextView title;
//    public ImageView thumbnail;

    MoviesHolder.OnMovieListener onMovieListener;

    public MoviesHolder(final View itemView, final MoviesHolder.OnMovieListener onMovieListener) {
        super(itemView);
        ButterKnife.bind(this, itemView);
//        title = itemView.findViewById(R.id.title);
//        thumbnail = itemView.findViewById(R.id.thumbnail);
        this.onMovieListener = onMovieListener;

        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        onMovieListener.onMovieListener(getAdapterPosition());
    }

    public interface OnMovieListener {
        void onMovieListener(int position);
    }
}
