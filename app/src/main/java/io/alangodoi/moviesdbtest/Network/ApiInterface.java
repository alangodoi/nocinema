package io.alangodoi.moviesdbtest.Network;

import io.alangodoi.moviesdbtest.Model.ResponseModel.Movie.MovieDetails;
import io.alangodoi.moviesdbtest.Model.ResponseModel.NowPlaying.NowPlaying;
import io.alangodoi.moviesdbtest.Model.ResponseModel.Videos.Trailers;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiInterface {

    @GET("now_playing")
    Call<NowPlaying> getNowPlaying (
            @Query("api_key") String apiKey,
            @Query("language") String language
    );

    @GET("{movieId}")
    Call<MovieDetails> getMovieDetails (
            @Path("movieId") int movieId,
            @Query("api_key") String apiKey,
            @Query("language") String language
    );

    @GET("{movie_id}/videos")
    Call<Trailers> getTrailers (
            @Path("movie_id") int movieId,
            @Query("api_key") String apiKey,
            @Query("language") String language
    );
}
