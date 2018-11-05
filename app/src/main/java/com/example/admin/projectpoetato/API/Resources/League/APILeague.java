package com.example.admin.projectpoetato.API.Resources.League;

/**
 * https://www.pathofexile.com/developer/docs/api-resource-leagues
 */
public class APILeague {
    private static final String resourceUrl = "http://api.pathofexile.com/leagues?";

    /**
     * Possible values:
     * -"all": retrieves all leagues (the default)
     * -"main": retrieves main leagues (ones from the character screen)
     * -"event": retrieves event leagues
     * -"season": retrieves leagues in a particular season
     */
    private String type;

    /**
     * A particular season id. Required when type=season.
     */
    private String season;

    /**
     * Possible values:
     * -"0": Displays the full info for leagues retrieved (will only retrieve a maximum of 50 leagues) (the default)
     * -"1": Display compact info for leagues retrieved (will retrieve up to 230 leagues)
     */
    private boolean compact;

    /**
     * This specifies the number of league entries to include. By default this is the maximum, which depends on the setting above.
     */
    private int limit;

    /**
     * This specifies the offset to the first league entry to include. Default: 0.
     */
    private int offset;

    /**
     * Constructor
     * @param type
     * @param season
     * @param compact
     * @param limit
     * @param offset
     */
    public APILeague(String type, String season, boolean compact, int limit, int offset) {
        this.type = type;
        this.season = season;
        this.compact = compact;
        this.limit = limit;
        this.offset = offset;
    }

    // Getters & Setters
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSeason() {
        return season;
    }

    public void setSeason(String season) {
        this.season = season;
    }

    public int getCompact() {
        return (this.compact ? 1 : 0);
    }

    public void setCompact(boolean compact) {
        this.compact = compact;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public String getRequestUrl() {
        return resourceUrl +
                "type=" + getType() +
                "&season=" + getSeason() +
                "&compact=" + getCompact() +
                "&limit=" + getLimit() +
                "&offset=" + getOffset();
    }

}
