package com.example.admin.projectpoetato.API.Resources;

import com.example.admin.projectpoetato.Models.Ladder;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;


public interface LadderApi {
    /**
     * https://www.pathofexile.com/developer/docs/api-resource-ladders
     * @param id (Required) : The id (name) of the league for the ladder you want to retrieve.
     * @param limit (Optional) : Specifies the number of ladder entries to include.
     *             Default: 20, Max: 200.
     * @param offset (Optional) : Specifies the offset to the first ladder entry to include.
     *              Default: 0.
     * @param type (Optional) : Specifies the type of ladder, options:
     *             [league (default), pvp, labyrinth]
     * @param track (Optional) : Adds unique IDs for each character returned.
     *              These can be used when name conflicts occur. Default: true
     * @param accountName (Optional) : League only:
     *                    Filters by account name within the first 15000 results.
     * @param difficulty (Optional) : Labyrinth only: Standard (1), Cruel (2) or Merciless (3)
     * @param start (Optional) : Labyrinth only: Timestamp of the ladder you want.
     * @return : Get a ladder by league id. There is a restriction in place on the last ladder,
     *              entry you are able to retrieve which is set to 15000.
     */
    @GET("ladders/{id}/?")
    Call<Ladder> getLadders(@Path("id") String id,
                                  @Query("limit") String limit,
                                  @Query("offset") String offset,
                                  @Query("type") String type,
                                  @Query("track") String track,
                                  @Query("accountName") String accountName,
                                  @Query("difficulty") String difficulty,
                                  @Query("start") String start);
}
