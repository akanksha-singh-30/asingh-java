package com.asingh.trackzilla.controller.unitTest;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.asingh.trackzilla.model.Application;
import com.asingh.trackzilla.repository.ApplicationJpaRepository;
import com.asingh.trackzilla.service.ApplicationService;

@SpringBootTest
//@MockBeans({ @MockBean(ApplicationService.class), @MockBean(TicketService.class), @MockBean(ReleaseService.class) })
public class ApplicationServiceUnitTest {

	@MockBean
	private ApplicationJpaRepository mockAppRepo;
	
	@Autowired
	private ApplicationService appService;

	@Test
	public void getAllApplications() {
		Application app = new Application("Test Application 5", "Unknown owner 5");
		app.setDescription("Description of Test Application 5");
		app.setId(5L);
		List<Application> appList = new ArrayList<>();
		appList.add(app);
		when(mockAppRepo.getApplications()).thenReturn(Optional.of(appList));
		
		Optional<List<Application>> opApp = appService.getAllApplications();		
		assertEquals(1, opApp.get().size());	
		assertEquals(5L, opApp.get().get(0).getId());
		verify(mockAppRepo, times(1)).getApplications();
	}
}
