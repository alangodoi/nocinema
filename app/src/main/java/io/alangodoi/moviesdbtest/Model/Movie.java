package io.alangodoi.moviesdbtest.Model;

import java.util.Date;

public class Movie {

    private int id, genre;
    private String title, overview, poster;
    private Date releaseDate;
    private boolean adult;

    public Movie() {}

    public Movie(int id, String title, String overview, int genre, Date releaseDate, boolean adult, String poster) {

        this.id = id;
        this.title = title;
        this.overview = overview;
        this.genre = genre;
        this.releaseDate = releaseDate;
        this.adult = adult;
        this.poster = poster;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public int getGenre() {
        return genre;
    }

    public void setGenre(int genre) {
        this.genre = genre;
    }

    public Date getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }

    public boolean isAdult() {
        return adult;
    }

    public void setAdult(boolean adult) {
        this.adult = adult;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

}
