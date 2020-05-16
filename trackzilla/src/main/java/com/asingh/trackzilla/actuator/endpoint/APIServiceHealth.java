package com.asingh.trackzilla.actuator.endpoint;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpClient.Redirect;
import java.net.http.HttpClient.Version;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.time.Duration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class APIServiceHealth implements HealthIndicator {

	final String baseURL = "http://localhost:9090/tza/v2/";

	final static Set<String> apiUrlCheck = new HashSet<String>();
	static {
		apiUrlCheck.add("applications");
		apiUrlCheck.add("tickets");
		apiUrlCheck.add("releases");
	}

	final HttpClient client = HttpClient
									.newBuilder()
									.followRedirects(Redirect.NORMAL)
									.version(Version.HTTP_1_1)
									.connectTimeout(Duration.ofSeconds(15))
									.build();

	@Override
	public Health health() {
		boolean down = basicRestAPICheck().values().contains(Boolean.FALSE);

		return down == false ? Health.up().withDetail("REST services", "Responding").build()
				: Health.up().withDetail("REST services", "Not-Responding").build();
	}

	private Map<String, Boolean> basicRestAPICheck() {

		Map<String, Boolean> apiStatus = new HashMap<>();
		for (String urlSuffix : apiUrlCheck) {
			HttpRequest request = HttpRequest
					.newBuilder()
					.uri(URI.create(baseURL + urlSuffix))
					.GET()
					.build();
			try {
				//TODO Send the request as Async and handle CompletableFuture results
				HttpResponse<Void> response = client.send(request, BodyHandlers.discarding());
				if (response.statusCode() == HttpStatus.OK.value()) {
					apiStatus.put(urlSuffix, true);
				} else {
					apiStatus.put(urlSuffix, false);
				}
			} catch (IOException | InterruptedException e) {
				e.printStackTrace();
				log.warn("Failed to perform basic REST API check for " + urlSuffix + " due to exception: "
						+ e.getMessage());
				apiStatus.put(urlSuffix, false);
			}
		}
		return apiStatus;
	}
}
