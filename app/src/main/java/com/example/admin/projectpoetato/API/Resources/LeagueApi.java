package com.example.admin.projectpoetato.API.Resources;

import com.example.admin.projectpoetato.Models.League;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface LeagueApi {
    /**
     * https://www.pathofexile.com/developer/docs/api-resource-leagues
     * @param type (Optional) : Possible values:
     *              -"main": retrieves permanant and challenge leagues (default)
     *              -"event": retrieves event leagues
     *              -"season": retrieves leagues in a particular season
     * @param season (Optional) : A particular season id. Required when type=season.
     * @param compact (Optional) : Possible values:
     *                  -"0": Displays the full info for leagues retrieved,
     *                  (will only retrieve a maximum of 50 leagues) (the default)
     *                  -"1": Display compact info for leagues retrieved (will retrieve up to 230 leagues)
     * @param limit (Optional) : This specifies the number of league entries to include.
     *              By default this is the maximum, which depends on the setting above.
     * @param offset (Optional) : This specifies the offset to the first league entry to include. Default: 0.
     * @return : Get a list of current and past leagues.
     */
    @GET("leagues")
    Call<List<League>> getLeagues(@Query("type") String type,
                                  @Query("season") String season,
                                  @Query("compact") int compact,
                                  @Query("limit") int limit,
                                  @Query("offset") int offset);


}
