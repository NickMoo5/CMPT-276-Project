package sfu.cmpt276.project.tripGeneration;

public class GenTripQuery {
    private static final String TRAVEL_ADVISOR_QUERY = "You are a travel advisor. Give me a list of places to see in ";
    private static final String DATE_QUERY = "I will be visiting from ";
    private static final String FILTERSYMBOL_QUERY = "Enclose all locations to visit in at symbols.";

    public static String genTripQuery(String city, String startDate, String endDate, String budget) {
        String query;
        query = TRAVEL_ADVISOR_QUERY + city + ". ";
        query += DATE_QUERY + startDate + " to " + endDate + ". ";
        query += FILTERSYMBOL_QUERY;
        return query;
    }
}