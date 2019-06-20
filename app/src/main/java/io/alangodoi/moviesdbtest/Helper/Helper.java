package io.alangodoi.moviesdbtest.Helper;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Helper {

    // https://www.themoviedb.org/
    public final String apiKey = "GET YOUR API_KEY HERE: https://www.themoviedb.org/";
    public final String imagesURL = "https://image.tmdb.org/t/p/original/";
    public final String youtubeURL = "https://www.youtube.com/watch?v=";

    public Date stringToDate (String date) {

        String[] dateParts = date.split("-");
        int year = Integer.parseInt(dateParts[0]);
        int month = Integer.parseInt(dateParts[1]);
        int day = Integer.parseInt(dateParts[2]);

        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, month - 1);
        cal.set(Calendar.DAY_OF_MONTH, day);

        return cal.getTime();
    }

    public String dateToString(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

        return sdf.format(date);
    }

    public boolean isConnected(Context context) {
        ConnectivityManager cm = (ConnectivityManager)
                context.getSystemService(Context.CONNECTIVITY_SERVICE);
        
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

}
