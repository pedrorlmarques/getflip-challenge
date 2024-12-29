package com.example.application.domain.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.application.domain.model.LongURL;
import com.example.application.domain.model.ShortURL;
import com.example.application.domain.model.URLMapping;
import com.example.application.domain.model.URLMappingId;
import com.example.application.port.in.CreateShortURLCommand;
import com.example.application.port.out.SaveURLMappingPort;

@ExtendWith(MockitoExtension.class)
class CreateShortURLUseCaseControllerServiceTest {

	@Mock
	private ShortURLStrategy shortURLStrategy;
	@Mock
	private SaveURLMappingPort saveUrlMappingPort;

	@InjectMocks
	private CreateShortURLService createShortURLService;

	@Test
	void testCreateShortURL() {
		var command = new CreateShortURLCommand(new LongURL("https://example.com"));
		var urlMapping = new URLMapping(new URLMappingId(UUID.randomUUID().toString()), command.longUrl(), new ShortURL("short-url"));

		when(shortURLStrategy.createShortURL(command)).thenReturn(urlMapping);

		var result = createShortURLService.createShortURL(command);

		assertThat(result).isNotNull();
		assertThat(result.id().value()).isEqualTo(urlMapping.id().value());
		assertThat(result.shortUrl().value()).isEqualTo(urlMapping.shortUrl().value());
		assertThat(result.longUrl().value()).isEqualTo(command.longUrl().value());

		verify(shortURLStrategy, times(1)).createShortURL(command);
		verify(saveUrlMappingPort, times(1)).save(urlMapping);
	}

}