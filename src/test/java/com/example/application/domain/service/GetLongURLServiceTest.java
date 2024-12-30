package com.example.application.domain.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.example.application.domain.exception.LongURLNotFoundException;
import com.example.application.domain.model.LongURL;
import com.example.application.domain.model.ShortURL;
import com.example.application.domain.model.URLMapping;
import com.example.application.domain.model.URLMappingId;
import com.example.application.port.in.GetLongURLCommand;
import com.example.application.port.out.LoadLongURLPort;

class GetLongURLServiceTest {

	private LoadLongURLPort loadLongURLPort;
	private GetLongURLService getLongURLService;

	@BeforeEach
	void setUp() {
		loadLongURLPort = mock(LoadLongURLPort.class);
		getLongURLService = new GetLongURLService(loadLongURLPort);
	}

	@Test
	void testGetLongURL() {
		var shortURL = new ShortURL("VuiVt3KL");
		var longURL = new LongURL("http://example.com");
		var urlMapping = new URLMapping(new URLMappingId(UUID.randomUUID()
		                                                     .toString()), longURL, shortURL);

		when(loadLongURLPort.load(shortURL)).thenReturn(Optional.of(urlMapping));

		var command = new GetLongURLCommand(shortURL);
		var result = getLongURLService.getLongURL(command);

		assertThat(result).isNotNull();
		assertThat(result.longUrl()).isEqualTo(longURL);
		assertThat(result.shortUrl()).isEqualTo(shortURL);
	}

	@Test
	void testGetLongURLNotFoundThrowsException() {
		var shortURL = new ShortURL("VuiVt3KL");

		when(loadLongURLPort.load(shortURL)).thenReturn(Optional.empty());

		var command = new GetLongURLCommand(shortURL);
		assertThatThrownBy(() -> getLongURLService.getLongURL(command))
				.isInstanceOf(LongURLNotFoundException.class)
				.hasMessage("Long URL not found");
	}
}