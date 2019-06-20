package io.alangodoi.moviesdbtest.Activity;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.alangodoi.moviesdbtest.Adapter.MoviesAdapter;
import io.alangodoi.moviesdbtest.Adapter.Viewholder.MoviesHolder;
import io.alangodoi.moviesdbtest.Helper.Helper;
import io.alangodoi.moviesdbtest.Model.Movie;
import io.alangodoi.moviesdbtest.Model.ResponseModel.NowPlaying.NowPlaying;
import io.alangodoi.moviesdbtest.Network.ApiClient;
import io.alangodoi.moviesdbtest.Network.ApiInterface;
import io.alangodoi.moviesdbtest.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements MoviesHolder.OnMovieListener {

    @BindView(R.id.recyclerView) RecyclerView recyclerView;
    @BindView(R.id.progressBar) ProgressBar progressBar;
    @BindView(R.id.tvNowPlaying) TextView tvNowPlaying;
    @BindView(R.id.ivConnection) ImageView ivConnection;
    @BindView(R.id.tvConnection) TextView tvConnection;
    @BindView(R.id.clConnection) ConstraintLayout clConnection;

    private MoviesAdapter moviesAdapter;
    private List<Movie> movieList;

    ApiInterface apiInterface;
    Helper helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        // ProgressBar Color
        progressBar.getIndeterminateDrawable().setColorFilter(
                getResources().getColor(R.color.colorAccent),
                android.graphics.PorterDuff.Mode.SRC_IN);

//        movieList = new ArrayList<>();
//        moviesAdapter = new MoviesAdapter(this, movieList, this);
//
//        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 2);
//        recyclerView.setLayoutManager(mLayoutManager);
//        recyclerView.setItemAnimator(new DefaultItemAnimator());
//        recyclerView.setAdapter(moviesAdapter);

        apiInterface =
                ApiClient.getClient().create(ApiInterface.class);
        helper = new Helper();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (helper.isConnected(MainActivity.this)) {
            tvNowPlaying.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.VISIBLE);
            clConnection.setVisibility(View.GONE);

            movieList = new ArrayList<>();
            moviesAdapter = new MoviesAdapter(this, movieList, this);

            RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 2);
            recyclerView.setLayoutManager(mLayoutManager);
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            recyclerView.setAdapter(moviesAdapter);

            getMovies();
        } else {
            tvNowPlaying.setVisibility(View.GONE);
            recyclerView.setVisibility(View.GONE);
            clConnection.setVisibility(View.VISIBLE);
        }
    }

    private void getMovies() {
        progressBar.setVisibility(View.VISIBLE);
        Call<NowPlaying> call = apiInterface.getNowPlaying(
                helper.apiKey,
                "pt-BR");

        call.enqueue(new Callback<NowPlaying>() {
            @Override
            public void onResponse(Call<NowPlaying> call, Response<NowPlaying> response) {
                if (response.code() == 200) {
                    for (int i=0; i<response.body().getResults().size(); i++) {

                        movieList.add(new Movie(
                                response.body().getResults().get(i).getId(),
                                response.body().getResults().get(i).getTitle(),
                                response.body().getResults().get(i).getOverview(),
                                response.body().getResults().get(i).getGenreIds().get(0),
                                helper.stringToDate(
                                        response.body().getResults().get(i).getReleaseDate()),
                                response.body().getResults().get(i).getAdult(),
                                response.body().getResults().get(i).getPosterPath()
                        ));

                    }
                } else {
                    Toast.makeText(
                            MainActivity.this,
                            R.string.msg_err,
                            Toast.LENGTH_SHORT).show();
                }

                moviesAdapter.notifyDataSetChanged();
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<NowPlaying> call, Throwable t) {
                Toast.makeText(MainActivity.this, R.string.msg_err, Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.GONE);
            }
        });
    }

    @Override
    public void onMovieListener(int position) {
        Intent i = new Intent(MainActivity.this, MovieDetailsActivity.class);
        i.putExtra("id", movieList.get(position).getId());
        i.putExtra("title", movieList.get(position).getTitle());
        i.putExtra("overview", movieList.get(position).getOverview());
        i.putExtra("genre", movieList.get(position).getGenre());
        i.putExtra("releaseDate", helper.dateToString(movieList.get(position).getReleaseDate()));
        i.putExtra("poster", movieList.get(position).getPoster());
        startActivity(i);
    }

}
