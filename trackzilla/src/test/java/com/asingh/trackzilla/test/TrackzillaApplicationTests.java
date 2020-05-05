package com.asingh.trackzilla.test;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import com.asingh.trackzilla.repository.IApplicationDAO;

@SpringBootTest
@AutoConfigureMockMvc
public class TrackzillaApplicationTests {

	@Autowired
	MockMvc mock;

	@Test
	void contextLoads() {
		assertThat(mock.getDispatcherServlet().getWebApplicationContext()).isNotNull();
	}

	@Test
	void repoLoads() {
		IApplicationDAO appDao = mock.getDispatcherServlet().getWebApplicationContext().getBean(IApplicationDAO.class);
		assertThat(appDao).isNotNull();
	}

}
