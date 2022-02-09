package com.example.cariadtest;

import com.example.cariadtest.controllers.StringsController;
import com.example.cariadtest.models.NumbersList;
import com.example.cariadtest.services.StringsService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class CariadTestApplicationTests {

	@Autowired
	private MockMvc mvc;

	@MockBean
	private StringsService stringsService;

	@Test
	public void getWithEmptyParams() throws Exception {
		mvc.perform(MockMvcRequestBuilders.get("/strings")).andExpect(status().isOk())
				.andExpect(content().string(containsString("{\"strings\":[]}")));
	}

	@Test
	public void getWithOneValidFibsUrl() throws Exception {
		String fiboEndpoint = "http://127.0.0.1:8090/fibo";
		NumbersList returnObject = new NumbersList();
		returnObject.setStrings(Arrays.asList("eight", "five", "one", "thirteen", "three", "twenty one", "two"));
		when(stringsService.processURLs(Arrays.asList(fiboEndpoint))).thenReturn(returnObject);

		mvc.perform(MockMvcRequestBuilders.get("/strings/?u="+fiboEndpoint)).andExpect(status().isOk())
				.andExpect(content().string(("{\"strings\":[\"eight\",\"five\",\"one\",\"thirteen\",\"three\",\"twenty one\",\"two\"]}")));
	}

}
