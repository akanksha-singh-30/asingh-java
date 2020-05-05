package com.asingh.trackzilla.test.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import java.net.URI;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.asingh.trackzilla.controllers.TrackzillaRestController_V2;
import com.asingh.trackzilla.service.ApplicationService;

//@ExtendWith(SpringExtension.class)
//@WebMvcTest(controllers = TrackzillaRestController_V2.class)
@SpringBootTest
@AutoConfigureMockMvc
public class TrackzillaRestV2UnitTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@Mock
	private ApplicationService service;
	
	@InjectMocks
	private TrackzillaRestController_V2 controller;
	
	private final String baseUrl = "/tza/v2/";
	
	@BeforeAll 
	public static void init() {
		MockitoAnnotations.initMocks(TrackzillaRestV2UnitTest.class);
	}
	
	@Test
	public void getApplicationById() throws Exception {
		mockMvc.perform(get(URI.create(baseUrl + "application/3")).accept(MediaType.APPLICATION_JSON))
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))			
			.andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Test Application 3"));
	}
	
}
