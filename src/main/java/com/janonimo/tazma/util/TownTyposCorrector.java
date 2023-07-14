package com.janonimo.tazma.util;
import java.util.*;

public class TownTyposCorrector {

    public static final List<String> GAUTENG_NEIGHBORHOODS = Arrays.asList(
            "Sandton", "Randburg", "Roodepoort", "Midrand", "Centurion",
            "Pretoria East", "Pretoria West", "Johannesburg CBD", "Bryanston",
            "Kempton Park", "Bedfordview", "Germiston", "Alberton","Vereenigin",
            "Edenvale", "Boksburg", "Vanderbijlpark", "Soweto","Lawley", "Lawley Station",
            "Lenasia", "Ennerdale", "Lehae", "Lenasia South", "Finetown", "Orange Farm",
            "Sebokeng",
            "Protea North"
    );
    private static final Set<String> NEIGHBORHOODS = new TreeSet<>(Arrays.asList(
            "Sandton", "Randburg", "Roodepoort", "Midrand", "Centurion",
            "Pretoria East", "Pretoria West", "Johannesburg CBD", "Bryanston",
            "Fourways", "Soweto", "Kempton Park", "Bedfordview", "Benoni",
            "Edenvale", "Germiston", "Alberton", "Boksburg", "Vanderbijlpark",
            "Vereeniging", "Welkom", "Bloemfontein", "Rustenburg", "Potchefstroom",
            "Polokwane", "Nelspruit", "Mbombela", "Secunda", "Parys", "Hermanus",
            "Stellenbosch", "Somerset West", "Paarl", "Franschhoek", "Grahamstown",
            "East London", "Port Elizabeth", "Jeffreys Bay", "Knysna", "George",
            "Mossel Bay", "Durbanville", "Strand", "Bloubergstrand", "Hout Bay",
            "Muizenberg", "Tokai", "Fish Hoek", "Simon's Town", "Noordhoek",
            "Milnerton", "Bellville", "Tygerberg", "Kuils River", "Goodwood",
            "Edgemead", "Parow", "Kraaifontein", "Brackenfell", "Panorama",
            "Glenlily", "Belhar", "Durbanville Hills", "Protea Valley", "Sonstraal",
            "Kenridge", "Rosenpark", "Vergesig", "Amandelsig", "Aurora",
            "Boston", "Oude Westhof", "Goedemoed", "Protea Heights", "Zevenwacht",
            "Eversdal", "Loevenstein", "Wynberg", "De Bron", "Morgenster",
            "Pinehurst", "Bosonia", "Loevenstein", "Kanoneiland", "Bo Oakdale",
            "Silver Oaks", "Sonkring", "Amanda Glen", "Ruwari", "Durmonte",
            "Kleinbron Park", "Bergsig", "Zonnendal", "Burgundy Estate", "Nzhelele","Giyani", "Ennerdale",
            "Hammanskraal", "Protea Glen", "Carltonville", "East Rand","Thembisa", "Alexander",
            "Lawley", "Finetown", "Grasmere", "Meriting", "Walkerville", "Snake Park", "Chiawelo",
            "Midway", "Protea South", "Meadowlands", "Diepkloof", "Lawley Station", "Lehae", "Noordgesig", "Hannover Park",
            "Dlamini", "Kliptown", "Eldorado Park", "Devland", "Mapetla", "Naledi", "Lenasia South", "Poorjtie",
            "Palm Springs","Sebokeng", "Evaton", "Orange Farm", "Tsakane", "Freedom Park",
            "Thokoza", "Molapo", "Jabavu", "White City", "Orlande West", "Orlando East",
            "Protea North", ""
    ));

    public static String correctNeighborhoodTypo(String userInput) {
        String correctedNeighborhood = null;
        int minimumDistance = Integer.MAX_VALUE;

        for (String neighborhood : NEIGHBORHOODS) {
            int distance = calculateLevenshteinDistance(userInput, neighborhood);
            if (distance < minimumDistance) {
                minimumDistance = distance;
                correctedNeighborhood = neighborhood;
            }
        }

        return correctedNeighborhood;
    }

    private static int calculateLevenshteinDistance(String s1, String s2) {
        int m = s1.length();
        int n = s2.length();
        int[][] dp = new int[m + 1][n + 1];

        for (int i = 0; i <= m; i++) {
            dp[i][0] = i;
        }

        for (int j = 0; j <= n; j++) {
            dp[0][j] = j;
        }

        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                int cost = s1.charAt(i - 1) == s2.charAt(j - 1) ? 0 : 1;
                dp[i][j] = Math.min(Math.min(dp[i - 1][j] + 1, dp[i][j - 1] + 1), dp[i - 1][j - 1] + cost);
            }
        }

        return dp[m][n];
    }


}
