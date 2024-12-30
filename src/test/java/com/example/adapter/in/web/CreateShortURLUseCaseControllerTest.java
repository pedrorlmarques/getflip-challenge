package com.example.adapter.in.web;

import static com.atlassian.oai.validator.mockmvc.OpenApiValidationMatchers.openApi;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.example.application.domain.model.LongURL;
import com.example.application.domain.model.ShortURL;
import com.example.application.domain.model.URLMapping;
import com.example.application.domain.model.URLMappingId;
import com.example.application.port.in.CreateShortURLCommand;
import com.example.application.port.in.CreateShortURLUseCase;

@WebMvcTest(CreateShortURLUseCaseController.class)
class CreateShortURLUseCaseControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockitoBean
	private CreateShortURLUseCase createShortURLUseCase;

	@Test
	void testCreateShortURL() throws Exception {
		var longURL = "https://example.com";
		var shortURL = "VuiVt3KL";
		var urlMapping = new URLMapping(new URLMappingId(UUID.randomUUID()
		                                                     .toString()), new LongURL(longURL), new ShortURL(shortURL));

		when(this.createShortURLUseCase.createShortURL(new CreateShortURLCommand(new LongURL(longURL))))
				.thenReturn(urlMapping);

		this.mockMvc.perform(post("/api/v1/url/shorten")
				                     .param("long_url", longURL))
		            .andExpect(status().isOk())
		            .andExpect(openApi().isValid("openapi.yaml"))
		            .andExpect(jsonPath("$.short_url").value(shortURL));
	}
}