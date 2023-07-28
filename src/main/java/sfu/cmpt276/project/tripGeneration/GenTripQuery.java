package sfu.cmpt276.project.tripGeneration;

public class GenTripQuery {
    private static final String TRAVEL_ADVISOR_QUERY = "Could you suggest a day by day itinerary for a trip to ";
    private static final String DATE_QUERY = "I will be visiting from ";
    private static final String DATE_FORMAT = "The dates given are in the following format Day/month/year";
    private static final String CHAT_INSTRUCTIONS = " include places to visit, organized in a " +
            "logical sequence based on the types of activities, and their general geographic proximity. For each item, " +
            "provide the name of the place, a brief description of why we should visit or what we could do there, please " +
            "return each day back in the following format" +
            ". An example is provided below for a day itinerary, return each day in the following format and KEEP THE CURLY BRACES: ";

    private static final String FORMAT =
            "\n" +
            "{ ^Day 1^\n" +
            "(~name of place enclosed in tildes~: @why you should come here or what you should do enclosed in at symbols@)\n" +
            "(~name of place enclosed in tildes~: @why you should come here or what you should do enclosed in at symbols@)\n" +
            "(~name of place enclosed in tildes~: @why you should come here or what you should do enclosed in at symbols@)\n" +
            "}";

    private static final String LIMITING_STATEMENT = "Do NOT return any other information.";

    public static String genTripQuery(String city, String startDate, String endDate) {
        String query;
        query = TRAVEL_ADVISOR_QUERY + city + ", ";
        query += DATE_QUERY + startDate + " to " + endDate + ", ";
        query += DATE_FORMAT;
        query += CHAT_INSTRUCTIONS;
        query += FORMAT;
        query += LIMITING_STATEMENT;
        return query;
    }
}