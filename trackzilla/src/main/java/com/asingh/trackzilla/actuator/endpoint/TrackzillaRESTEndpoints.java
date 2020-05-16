package com.asingh.trackzilla.actuator.endpoint;

import org.springframework.boot.actuate.info.Info.Builder;
import org.springframework.boot.actuate.info.InfoContributor;
import org.springframework.stereotype.Component;

@Component
public class TrackzillaRESTEndpoints implements InfoContributor {

	@Override
	public void contribute(Builder builder) {
		
		builder.withDetail("All Tickets", "http://localhost:9090/tza/v2/tickets")
			   .withDetail("All Applications", "http://localhost:9090/tza/v2/applications")
			   .withDetail("All Releases", "http://localhost:9090/tza/v2/releases")
			   .build();
	}
}
