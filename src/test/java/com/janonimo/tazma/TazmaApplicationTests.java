package com.janonimo.tazma;

import com.nimbusds.jose.shaded.gson.Gson;
import com.nimbusds.jose.shaded.gson.JsonArray;
import com.nimbusds.jose.shaded.gson.JsonObject;
import com.nimbusds.jose.shaded.gson.JsonParser;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@SpringBootTest
class TazmaApplicationTests {

	@Test
	void contextLoads() {
		RestTemplate restTemplate = new RestTemplate();

		String baseURL = "api.tomtom.com";
		String versionNumber = "2";
		String query = "Lawley fire station gauteng";
		String ext = "json";
		String apiKey = "GaOeN69sWgGEIKbea3uUftnOFXd2ONMA";

		String url = UriComponentsBuilder.newInstance()
				.scheme("https")
				.host(baseURL)
				.pathSegment("search", versionNumber, "geocode", query + "." + ext)
				.queryParam("key", apiKey)
				// Add additional query parameters as needed
				.build()
				.toString();
		ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, null, String.class);
		//System.out.println(response.getBody());
		double longitude = 0.0;

		// Parse the response string and extract the longitude
		Gson gson = new Gson();
		JsonObject responseJson = gson.fromJson(response.getBody(), JsonObject.class);

		JsonArray resultsArray = responseJson.getAsJsonArray("results");
		if (resultsArray.size() > 0) {
			JsonObject firstResult = resultsArray.get(0).getAsJsonObject();
			JsonObject position = firstResult.getAsJsonObject("position");
			longitude = position.get("lon").getAsDouble();
			System.out.println("Longitude" + longitude);
		}
	}

}
