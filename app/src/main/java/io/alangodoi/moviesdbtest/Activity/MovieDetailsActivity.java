package io.alangodoi.moviesdbtest.Activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.alangodoi.moviesdbtest.Helper.Helper;
import io.alangodoi.moviesdbtest.Model.ResponseModel.Movie.MovieDetails;
import io.alangodoi.moviesdbtest.Model.ResponseModel.Videos.Trailers;
import io.alangodoi.moviesdbtest.Network.ApiClient;
import io.alangodoi.moviesdbtest.Network.ApiInterface;
import io.alangodoi.moviesdbtest.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieDetailsActivity extends AppCompatActivity {

    @BindView(R.id.svMovieDetails) ScrollView svMovieDetails;
    @BindView(R.id.clConnection) ConstraintLayout clConnection;
    @BindView(R.id.ivPoster) ImageView ivPoster;
    @BindView(R.id.ivYoutube) ImageView ivYoutube;
    @BindView(R.id.tvTitle) TextView tvTitle;
    @BindView(R.id.tvReleaseDate) TextView tvReleaseDate;
    @BindView(R.id.tvGenre) TextView tvGenre;
    @BindView(R.id.tvOverview) TextView tvOverview;
    @BindView(R.id.progressBar) ProgressBar progressBar;
    @BindView(R.id.ivConnection) ImageView ivConnection;
    @BindView(R.id.tvConnection) TextView tvConnection;

    ApiInterface apiInterface;
    Helper helper;

    private int movieId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_moviedetails);
        ButterKnife.bind(this);

        Intent i = getIntent();
        movieId = i.getIntExtra("id", 0);
        String title = i.getStringExtra("title");
        String overview = i.getStringExtra("overview");
        String releaseDate = i.getStringExtra("releaseDate");
        String poster = i.getStringExtra("poster");

        tvTitle.setText(title);
        tvReleaseDate.setText(releaseDate);
        tvOverview.setText(overview);

        apiInterface =
                ApiClient.getClient().create(ApiInterface.class);
        helper = new Helper();

        Glide.with(this).load(helper.imagesURL + poster)
                .placeholder(R.drawable.loading_animation)
                .error(R.drawable.popcorn)
                .skipMemoryCache(false)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(ivPoster);

        Glide.with(this).load(R.drawable.youtube)
                .placeholder(R.drawable.loading_animation)
                .skipMemoryCache(false)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .centerCrop()
                .into(ivYoutube);

    }

    @OnClick(R.id.ivYoutube) void youtube(View view) {
        progressBar.setVisibility(View.VISIBLE);

        Call<Trailers> call =
                apiInterface.getTrailers(movieId, helper.apiKey, "en-US");

        call.enqueue(new Callback<Trailers>() {
            @Override
            public void onResponse(Call<Trailers> call, Response<Trailers> response) {
                if (response.code() == 200) {
                    startActivity(new Intent(
                            Intent.ACTION_VIEW,
                            Uri.parse(helper.youtubeURL +
                                    response.body().getResults().get(0).getKey())));
                } else {
                    Toast.makeText(
                            MovieDetailsActivity.this,
                            R.string.msg_err,
                            Toast.LENGTH_SHORT).show();
                }

                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<Trailers> call, Throwable t) {
                Toast.makeText(
                        MovieDetailsActivity.this,
                        R.string.msg_err,
                        Toast.LENGTH_SHORT).show();

                progressBar.setVisibility(View.GONE);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (helper.isConnected(MovieDetailsActivity.this)) {
            svMovieDetails.setVisibility(View.VISIBLE);
            clConnection.setVisibility(View.GONE);
            getMovieDetails();
        } else {
            svMovieDetails.setVisibility(View.GONE);
            clConnection.setVisibility(View.VISIBLE);
        }
    }

    private void getMovieDetails() {
        progressBar.setVisibility(View.VISIBLE);

        Call<MovieDetails> call =
                apiInterface.getMovieDetails(movieId, helper.apiKey, "pt-BR");

        call.enqueue(new Callback<MovieDetails>() {
            @Override
            public void onResponse(Call<MovieDetails> call, Response<MovieDetails> response) {
                if (response.code() == 200) {
                    StringBuilder sb = new StringBuilder();
                    for (int i=0; i<response.body().getGenres().size(); i++) {
                        if (i>0)
                            sb.append(", ");

                        if ((i+1) % 3 == 0)
                            sb.append(System.getProperty("line.separator"));

                        sb.append(response.body().getGenres().get(i).getName());
                    }

                    tvGenre.setText(sb);
                } else {
                    Toast.makeText(
                            MovieDetailsActivity.this,
                            R.string.msg_err,
                            Toast.LENGTH_SHORT).show();
                }

                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<MovieDetails> call, Throwable t) {
                Toast.makeText(
                        MovieDetailsActivity.this,
                        R.string.msg_err,
                        Toast.LENGTH_SHORT).show();

                progressBar.setVisibility(View.GONE);
            }
        });
    }

}
