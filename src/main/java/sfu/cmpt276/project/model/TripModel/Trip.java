package sfu.cmpt276.project.model.TripModel;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


import jakarta.persistence.*;

@Entity
@Table(name="trips")
public class Trip {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int uid;
    private static final Pattern REG_EXP = Pattern.compile("~(.*?)~");
    private static String LOCATIONS_SEP = "~";
    private String[] locations;
    private String startDate;
    private String endDate;
    private String location;
    private String budget;
    private int userUid;            // The UID of the user account that this trip belongs to

    public Trip() {}

    public Trip(String chatResponse, String startDate, String endDate, String location, String budget, int userUid) {
        this.locations = parseLocations(chatResponse);
        this.location = location;
        this.startDate = startDate;
        this.endDate = endDate;
        this.budget = budget;
        this.userUid = userUid;
    }

    private String[] parseLocations(String chatResponse) {
        String extractedText;
        String[] locations = new String[25];
        Matcher matcher = REG_EXP.matcher(chatResponse);
        int i = 0;

        while (matcher.find()) {
            extractedText = matcher.group(1);
            System.out.printf(extractedText);
            locations[i] = extractedText;
            i++;
        }
        return locations;
    }

    public List<String> getLocationsList() {
        List<String> locationsList = new ArrayList<String>(Arrays.asList(locations));
        while (locationsList.remove(null)) {}

        return locationsList;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getBudget() {
        return budget;
    }

    public void setBudget(String budget) {
        this.budget = budget;
    }

    public int getUid() {
        return uid;
    }

    public int getUserUid() {
        return userUid;
    }

    /*
    public static void main(String[] args) {
        String query = "Sure! Here's a list of must-see places in Tokyo for your visit from 17/07/2023 to 24/07/2023:\n" +
                "\n" +
                "1. @Senso-ji Temple@: Tokyo's oldest and most famous temple located in Asakusa. Explore the vibrant Nakamise shopping street leading to the temple.\n" +
                "\n" +
                "2. @Tsukiji Outer Market@: Visit this bustling marketplace to experience the freshest seafood and delicious street food.\n" +
                "\n" +
                "3. @Shibuya Crossing@: Witness the iconic scramble crossing in Shibuya, one of the busiest intersections in the world.\n" +
                "\n" +
                "4. @Meiji Shrine@: Take a peaceful stroll through this Shinto shrine located in a lush forest in the heart of the city.\n" +
                "\n" +
                "5. @Harajuku@: Experience the unique and vibrant fashion culture in Harajuku, filled with trendy boutiques and stylish cafes.\n" +
                "\n" +
                "6. @Shinjuku Gyoen National Garden@: Relax in this beautiful garden, offering picturesque landscapes and a variety of cherry blossom trees.\n" +
                "\n" +
                "7. @Akihabara@: Explore the electronics and anime district, known for its gaming shops, manga cafes, and retro arcades.\n" +
                "\n" +
                "8. @Tokyo Skytree@: Enjoy breathtaking views of the city from one of the world's tallest towers.\n" +
                "\n" +
                "9. @Ginza@: Shop at Tokyo's high-end district with luxury boutiques, department stores, and exquisite dining options.\n" +
                "\n" +
                "10. @Odaiba@: Discover this futuristic entertainment island with attractions like TeamLab Borderless, Oedo Onsen Monogatari, and the Rainbow Bridge.\n" +
                "\n" +
                "11. @Ueno Park@: Wander through this vast park, home to various museums, temples, and a lovely pond.\n" +
                "\n" +
                "12. @Roppongi Hills@: Visit this modern complex for shopping, dining, art exhibitions, and panoramic city views from the observation deck.\n" +
                "\n" +
                "13. @Asakusa Culture and Tourist Information Center@: Head to the rooftop for an excellent view of the Senso-ji Temple and Tokyo Skytree.\n" +
                "\n" +
                "14. @Tokyo Disneyland@: Have a magical day at this iconic theme park, perfect for travelers of all ages.\n" +
                "\n" +
                "15. @Tokyo Tower@: Another great spot for city views, this iconic landmark offers a mix of retro and modern attractions.\n" +
                "\n" +
                "Enjoy your trip to Tokyo! \uD83D\uDDFC\uD83C\uDDEF\uD83C\uDDF5";

        Trip newTrip = new Trip(query, "n", "n", "m,", "n", 1);
        System.out.println(newTrip.getLocationsList());
    }
    */
}
