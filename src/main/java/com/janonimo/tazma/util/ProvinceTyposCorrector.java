package com.janonimo.tazma.util;
import java.util.Arrays;
import java.util.List;

public class ProvinceTyposCorrector {

    private static final List<String> PROVINCES = Arrays.asList(
            "Gauteng", "Mpumalanga", "Eastern Cape", "Western Cape",
            "Northern Cape", "Limpopo", "Kwazulu Natal", "North West", "Free State"
    );

    public static String correctProvinceTypo(String userInput) {
        String correctedProvince = null;
        int minimumDistance = Integer.MAX_VALUE;

        for (String province : PROVINCES) {
            int distance = calculateLevenshteinDistance(userInput, province);
            if (distance < minimumDistance) {
                minimumDistance = distance;
                correctedProvince = province;
            }
        }

        return correctedProvince;
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
