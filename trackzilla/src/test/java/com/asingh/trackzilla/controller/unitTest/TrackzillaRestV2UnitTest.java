package com.asingh.trackzilla.controller.unitTest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;

import com.asingh.trackzilla.controllers.TrackzillaRestController_V2;
import com.asingh.trackzilla.model.Application;
import com.asingh.trackzilla.service.ApplicationService;

@WebMvcTest(TrackzillaRestController_V2.class)
public class TrackzillaRestV2UnitTest {

	@Autowired
	TestRestTemplate restTemplate;

	@MockBean
	private ApplicationService appService;

	private final String baseUrl = "/tza/v2/";

	@Test
	public void getAllApplications() {
		Application app = restTemplate.getForObject((baseUrl + "application/1"), Application.class);
		assertThat(app).isNotNull();
		assertEquals(1L, app.getId());
		verify(appService, times(1)).findApplicationById(1);
	}
}
