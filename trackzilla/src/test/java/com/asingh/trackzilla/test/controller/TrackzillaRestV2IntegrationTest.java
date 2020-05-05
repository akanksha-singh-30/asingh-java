package com.asingh.trackzilla.test.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.util.UriComponentsBuilder;

import com.asingh.trackzilla.enums.TicketStatus;
import com.asingh.trackzilla.model.Application;
import com.asingh.trackzilla.model.Ticket;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@AutoConfigureMockMvc
@SpringBootTest
public class TrackzillaRestV2IntegrationTest {

	@Autowired
	private MockMvc mock;

//	@Autowired
//	private UriComponentsBuilder builder;

	private final String baseUrl = "/tza/v2/";

	private static String appJson = "";

	private static String ticketWithoutAppJSON = "";

	private static final String releaseWithoutAppJSON = "{\"description\":\"RELEASE_1.0\",\"releaseDate\":\"2020-05-30\"}";

	private static final String releaseWithApps = "{\n\t\"releaseDate\": \"2020-09-30\",\n\t\"description\": \"The description\",\n\t\"applications\": [{\"name\": \"New App1\", \"description\": \"App added with release\", \"owner\": \"Jane Doe\"},{\"name\": \"New App2\", \"description\": \"Another app added with release\", \"owner\": \"Jane Doe\"}]\n}";

	private static Application app;

	private static Ticket ticket;

	@BeforeAll
	public static void init() {
		app = new Application("Test Application 5", "Unknown owner 5");
		app.setDescription("Description of Test Application 5");
		ObjectMapper mapper = new ObjectMapper();
		try {
			appJson = mapper.writeValueAsString(app);

		} catch (JsonProcessingException e) {
			e.printStackTrace();
			appJson = "{\"name\":\"Test Application\",\"description\":\"A test application.\",\"owner\":\"Kesha Williams\"}";
		}

		ticket = new Ticket("Implement Entity for Application",
				"Create an Entity class for Ticket having id, title, description, application, status as fields");
		try {
			ticketWithoutAppJSON = mapper.writeValueAsString(ticket);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void createNewApplication() throws Exception {

		System.out.println("Creating new application:" + appJson);
		RequestBuilder requestBuilder = MockMvcRequestBuilders.post(baseUrl + "application")
				.contentType(MediaType.APPLICATION_JSON).content(appJson).accept(MediaType.APPLICATION_JSON);

		MockHttpServletResponse response = mock.perform(requestBuilder).andReturn().getResponse();

		assertEquals(HttpStatus.CREATED.value(), response.getStatus());
	}

	@Test
	public void getApplicationById() throws Exception {

		RequestBuilder requestBuilder = MockMvcRequestBuilders.get(baseUrl + "application/3")
				.accept(MediaType.APPLICATION_JSON);
		MockHttpServletResponse response = mock.perform(requestBuilder).andReturn().getResponse();

		assertEquals(HttpStatus.OK.value(), response.getStatus());
		assertEquals(MediaType.APPLICATION_JSON.toString(), response.getContentType());
	}

	@Test
	public void getApplicationByIdNegTest() throws Exception {

		RequestBuilder requestBuilder = MockMvcRequestBuilders.get(baseUrl + "application/1000")
				.accept(MediaType.APPLICATION_JSON);
		MockHttpServletResponse response = mock.perform(requestBuilder).andReturn().getResponse();

		assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatus());
	}

	@Test
	public void getApplications() throws Exception {

		RequestBuilder requestBuilder = MockMvcRequestBuilders.get(baseUrl + "applications")
				.accept(MediaType.APPLICATION_JSON);
		MockHttpServletResponse response = mock.perform(requestBuilder).andReturn().getResponse();

		assertEquals(response.getStatus(), HttpStatus.OK.value());
		assertEquals(response.getContentType(), MediaType.APPLICATION_JSON.toString());
		assertNotNull(response.getContentAsString());
	}

	@Test
	public void putRequestUpdateExistingApplication() throws Exception {

		Application updateApp = new Application();
		updateApp.setName("Test Application 4");
		updateApp.setOwner("Unknown Owner 4");
		updateApp.setDescription("Updated Description of Test Application 4");

		ObjectMapper mapper = new ObjectMapper();
		String updateJsonString = mapper.writeValueAsString(updateApp);

		RequestBuilder requestBuilder = MockMvcRequestBuilders.put(baseUrl + "application")
				.contentType(MediaType.APPLICATION_JSON).content(updateJsonString);
		MockHttpServletResponse response = mock.perform(requestBuilder).andReturn().getResponse();

		assertEquals(response.getStatus(), HttpStatus.ACCEPTED.value());
	}

	@Test
	public void putRequestUpdateExistingApplicationNeg() throws Exception {

		Application newApp = new Application("Test Application 6000", "Unknown owner 6000");
		newApp.setDescription("Description of Test Application 6000");
		ObjectMapper mapper = new ObjectMapper();
		String jsonString = mapper.writeValueAsString(newApp);

		RequestBuilder requestBuilder = MockMvcRequestBuilders.put(baseUrl + "application")
				.contentType(MediaType.APPLICATION_JSON).content(jsonString);
		MockHttpServletResponse response = mock.perform(requestBuilder).andReturn().getResponse();

		assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
	}

	@Test
	public void deleteApplicationById() throws Exception {

		RequestBuilder requestBuilder = MockMvcRequestBuilders.delete(baseUrl + "application/2")
				.accept(MediaType.APPLICATION_JSON);
		MockHttpServletResponse response = mock.perform(requestBuilder).andReturn().getResponse();

		assertEquals(HttpStatus.NO_CONTENT.value(), response.getStatus());
	}

	@Test
	public void deleteApplicationByIdNegTest() throws Exception {

		RequestBuilder requestBuilder = MockMvcRequestBuilders.delete(baseUrl + "application/20000")
				.accept(MediaType.APPLICATION_JSON);
		MockHttpServletResponse response = mock.perform(requestBuilder).andReturn().getResponse();

		assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatus());
	}

	@Test
	public void addTicketWithApplication() throws Exception {

		Application application = new Application("Test Application 3", "Unknown Owner 3");
		application.setId(3);
		application.setDescription("Description of Test Application 3");
		ticket.setApplication(application);
		ObjectMapper mapper = new ObjectMapper();
		String ticketJson = mapper.writeValueAsString(ticket);

		RequestBuilder requestBuilder = MockMvcRequestBuilders.post(baseUrl + "ticket")
				.contentType(MediaType.APPLICATION_JSON).content(ticketJson);
		MockHttpServletResponse response = mock.perform(requestBuilder).andReturn().getResponse();

		assertEquals(HttpStatus.CREATED.value(), response.getStatus());
	}

	@Test
	public void addTicketWithoutApplication() throws Exception {

		RequestBuilder requestBuilder = MockMvcRequestBuilders.post(baseUrl + "ticket")
				.contentType(MediaType.APPLICATION_JSON).content(ticketWithoutAppJSON)
				.accept(MediaType.APPLICATION_JSON);
		MockHttpServletResponse response = mock.perform(requestBuilder).andReturn().getResponse();

		assertEquals(HttpStatus.CREATED.value(), response.getStatus());
	}

	@Test
	public void getTicketById() throws Exception {

		RequestBuilder requestBuilder = MockMvcRequestBuilders.get(baseUrl + "ticket/3")
				.accept(MediaType.APPLICATION_JSON);
		MockHttpServletResponse response = mock.perform(requestBuilder).andReturn().getResponse();

		assertEquals(HttpStatus.OK.value(), response.getStatus());
		assertEquals(MediaType.APPLICATION_JSON.toString(), response.getContentType());
		String responseJson = response.getContentAsString();
		Ticket responseTicket = new ObjectMapper().readValue(responseJson, Ticket.class);
		assertNotNull(responseTicket);
		assertEquals("Ticket 3", responseTicket.getTitle());
	}

	@Test
	public void getAllTickets() throws Exception {

		RequestBuilder requestBuilder = MockMvcRequestBuilders.get(baseUrl + "tickets")
				.accept(MediaType.APPLICATION_JSON);
		MockHttpServletResponse response = mock.perform(requestBuilder).andReturn().getResponse();

		assertEquals(HttpStatus.OK.value(), response.getStatus());
		assertEquals(MediaType.APPLICATION_JSON.toString(), response.getContentType());
		String responseJson = response.getContentAsString();
		List<Ticket> tickets = new ObjectMapper().readValue(responseJson, new TypeReference<List<Ticket>>() {
		});
		assertThat(tickets).hasSizeGreaterThanOrEqualTo(3);
	}

	@Test
	public void updateTicket() throws Exception {

		Application application = new Application("Test Application 3", "Unknown Owner 3");
		application.setId(3);
		application.setDescription("Description of Test Application 3");
		ticket.setApplication(application);

		Ticket updateTicket = new Ticket("Ticket 4", "Description 4");
		updateTicket.setId(4);
		updateTicket.setStatus(TicketStatus.IN_PROGRESS);
		updateTicket.setApplication(application);

		String ticketJson = new ObjectMapper().writeValueAsString(updateTicket);

		MockHttpServletResponse response = mock.perform(MockMvcRequestBuilders.put(baseUrl + "ticket")
				.contentType(MediaType.APPLICATION_JSON).content(ticketJson)).andReturn().getResponse();

		assertEquals(HttpStatus.OK.value(), response.getStatus());
	}

	@Test
	public void updateTicketById() throws Exception {

		Application application = new Application("Test Application 3", "Unknown Owner 3");
		application.setId(3);
		application.setDescription("Description of Test Application 3");
		ticket.setApplication(application);

		Ticket updateTicket = new Ticket("Ticket 2", "Description 2");
		updateTicket.setId(4);
		updateTicket.setStatus(TicketStatus.IN_PROGRESS);
		updateTicket.setApplication(application);

		String ticketJson = new ObjectMapper().writeValueAsString(updateTicket);

		MockHttpServletResponse response = mock.perform(MockMvcRequestBuilders.put(baseUrl + "ticket/2")
				.contentType(MediaType.APPLICATION_JSON).content(ticketJson)).andReturn().getResponse();

		assertEquals(HttpStatus.OK.value(), response.getStatus());
	}

	@Test
	public void closeTicket() throws Exception {

		MockHttpServletResponse response = mock.perform(MockMvcRequestBuilders.put(baseUrl + "ticket/2")).andReturn()
				.getResponse();

		assertEquals(HttpStatus.OK.value(), response.getStatus());
	}

	@Test
	public void deleteTicket() throws Exception {
		MockHttpServletResponse response = mock.perform(MockMvcRequestBuilders.put(baseUrl + "ticket/5")).andReturn()
				.getResponse();

		assertEquals(HttpStatus.OK.value(), response.getStatus());
	}

	@Test
	public void addNewReleaseWithoutApp() throws Exception {
		MockHttpServletResponse response = mock.perform(MockMvcRequestBuilders.post(baseUrl + "/release")
				.content(releaseWithoutAppJSON).contentType(MediaType.APPLICATION_JSON)).andReturn().getResponse();

		assertEquals(HttpStatus.CREATED.value(), response.getStatus());
	}

	@Test
	public void addNewReleaseWithNewApps() throws Exception {
		MockHttpServletResponse response = mock.perform(MockMvcRequestBuilders.post(baseUrl + "/release")
				.content(releaseWithApps).contentType(MediaType.APPLICATION_JSON)).andReturn().getResponse();

		assertEquals(HttpStatus.CREATED.value(), response.getStatus());
	}

	@Test
	public void addApplicationToRelease() throws Exception {
		MockHttpServletResponse response = mock.perform(MockMvcRequestBuilders
				.put(UriComponentsBuilder.newInstance().path(baseUrl + "/release/{appId}/{releaseId}")
						.buildAndExpand(1, 1).toUri())).andReturn().getResponse();

		assertEquals(HttpStatus.OK.value(), response.getStatus());
	}

	@Test
	public void getAllReleases() throws Exception {
		MockHttpServletResponse response = mock
				.perform(MockMvcRequestBuilders.get(baseUrl + "/releases").accept(MediaType.APPLICATION_JSON))
				.andReturn().getResponse();

		assertEquals(HttpStatus.OK.value(), response.getStatus());
		assertEquals(MediaType.APPLICATION_JSON.toString(), response.getContentType());		
	}

}