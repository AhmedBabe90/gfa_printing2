package com.example.gfa_printing;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.Callback;

public interface ServiceArrayAPI {
    /* @GET url is after Base_URL.
       We are going to get List of country as response.
    */
    @GET("api/type-counters")
    public Call<List<ServiceModel>> getCountries();
}
