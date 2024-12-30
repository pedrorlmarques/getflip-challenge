package com.example.adapter.in.web;


import static com.atlassian.oai.validator.mockmvc.OpenApiValidationMatchers.openApi;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.example.application.domain.exception.LongURLNotFoundException;
import com.example.application.domain.model.LongURL;
import com.example.application.domain.model.ShortURL;
import com.example.application.domain.model.URLMapping;
import com.example.application.domain.model.URLMappingId;
import com.example.application.port.in.GetLongURLCommand;
import com.example.application.port.in.GetLongURLUseCase;

@WebMvcTest(GetLongURLUseCaseController.class)
class GetLongURLUseCaseControllerITTest {

	@Autowired
	private MockMvc mockMvc;

	@MockitoBean
	private GetLongURLUseCase getLongURLUseCase;

	@Test
	void testGetLongURL() throws Exception {

		var shortURL = "VuiVt3KL";
		var longURL = "http://example.com";

		when(this.getLongURLUseCase.getLongURL(new GetLongURLCommand(new ShortURL(shortURL))))
				.thenReturn(
						new URLMapping(new URLMappingId(UUID.randomUUID()
						                                    .toString()),
						               new LongURL(longURL),
						               new ShortURL(shortURL))
				);

		this.mockMvc.perform(get("/api/v1/url/{short-url}", shortURL))
		            .andExpect(status().isOk())
		            .andExpect(openApi().isValid("openapi.yaml"))
		            .andExpect(jsonPath("$.long_url").value(longURL));
	}

	@Test
	void testNotFoundLongURL() throws Exception {

		var shortURL = "VuiVt3KL";

		when(this.getLongURLUseCase.getLongURL(new GetLongURLCommand(new ShortURL(shortURL))))
				.thenThrow(new LongURLNotFoundException("Long URL not found")
				);

		this.mockMvc.perform(get("/api/v1/url/{short-url}", shortURL))
		            .andExpect(status().isNotFound())
		            .andExpect(openApi().isValid("openapi.yaml"));
	}
}