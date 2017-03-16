package com.example.rent.weatherapplication;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by RENT on 2017-03-15.
 */

public interface WeatherService {           //dla Retrofita

    @GET("api.php")
    Observable<Weather> getActualWeather (@Query("city") String city);

}
