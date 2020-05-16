package com.asingh.trackzilla.actuator.endpoint;

import java.util.HashMap;
import java.util.Map;

import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.stereotype.Component;

@Component
@Endpoint(id = "tza-api-endpoints", enableByDefault = false)
public class TrackzillaAPIEndpoints {

	@ReadOperation
	public Map<String, String> getAPIEndpoints() {

		Map<String, String> tzaAppEndpoints = new HashMap<String, String>();

		tzaAppEndpoints.put("All Releases", "http://localhost:9090/tza/v2/releases");
		tzaAppEndpoints.put("All Applications", "http://localhost:9090/tza/v2/applications");
		tzaAppEndpoints.put("All Tickets", "http://localhost:9090/tza/v2/tickets");
		tzaAppEndpoints.put("All Enhancements", "http://localhost:9090/tza/v2/enhancements");
		tzaAppEndpoints.put("All Bugs", "http://localhost:9090/tza/v2/bugs");

		return tzaAppEndpoints;
	}

}
