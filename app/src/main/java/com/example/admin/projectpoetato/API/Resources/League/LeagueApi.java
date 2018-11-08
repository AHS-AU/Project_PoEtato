package com.example.admin.projectpoetato.API.Resources.League;

import com.example.admin.projectpoetato.Models.League;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface LeagueApi {


    @GET("leagues")
    Call<List<League>> getLeagues(@Query("type") String type,
                                  @Query("season") String season,
                                  @Query("compact") int compact,
                                  @Query("limit") int limit,
                                  @Query("offset") int offset);


}
