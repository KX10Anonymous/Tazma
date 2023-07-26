package com.janonimo.tazma.core.maps;

import com.janonimo.tazma.user.Address;
import com.nimbusds.jose.shaded.gson.Gson;
import com.nimbusds.jose.shaded.gson.JsonArray;
import com.nimbusds.jose.shaded.gson.JsonObject;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class GeocodingService {


    public String geocodeAddress(Address address) {
        RestTemplate restTemplate = new RestTemplate();

        String baseURL = "api.tomtom.com";
        String versionNumber = "2";
        String query = address.toString();
        String ext = "json";
        String apiKey = "GaOeN69sWgGEIKbea3uUftnOFXd2ONMA";

        String url = UriComponentsBuilder.newInstance()
                .scheme("https")
                .host(baseURL)
                .pathSegment("search", versionNumber, "geocode", query + "." + ext)
                .queryParam("key", apiKey)
                .build()
                .toString();

        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, null, String.class);
        return response.getBody();
    }
    public double calculateDistance(double lat1, double lon1, double lat2, double lon2) {
        double earthRadius = 6371; // Earth's radius in kilometers

        double latDistance = Math.toRadians(lat2 - lat1);
        double lonDistance = Math.toRadians(lon2 - lon1);

        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return earthRadius * c;


    }

    public double getLatitudeFromResponse(String response) {
        double latitude = 0.0;
        Gson gson = new Gson();
        JsonObject responseJson = gson.fromJson(response, JsonObject.class);
        JsonArray resultsArray = responseJson.getAsJsonArray("results");
        if (resultsArray.size() > 0) {
            JsonObject firstResult = resultsArray.get(0).getAsJsonObject();
            JsonObject position = firstResult.getAsJsonObject("position");
            latitude = position.get("lat").getAsDouble();
        }

        return latitude;
    }

    public double getLongitudeFromResponse(String response) {
        double longitude = 0.0;
        Gson gson = new Gson();
        JsonObject responseJson = gson.fromJson(response, JsonObject.class);

        JsonArray resultsArray = responseJson.getAsJsonArray("results");
        if (resultsArray.size() > 0) {
            JsonObject firstResult = resultsArray.get(0).getAsJsonObject();
            JsonObject position = firstResult.getAsJsonObject("position");
            longitude = position.get("lon").getAsDouble();
        }

        return longitude;
    }


}



