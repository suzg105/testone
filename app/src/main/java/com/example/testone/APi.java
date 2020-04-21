package com.example.testone;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface APi {
    @GET("ajax.php")
    Call<testBean> getExchange(@Query("a") String a, @Query("f") String f, @Query("t") String t, @Query("w") String w );
}
