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
import com.example.application.port.in.CreateShortURLCommand;
import com.example.application.port.out.LoadUniqueIDPort;
import com.example.common.Base62Utils;

@ExtendWith(MockitoExtension.class)
class Base62UniqueIDShortURLStrategyTest {

	@Mock
	private LoadUniqueIDPort loadUniqueIDPort;

	@InjectMocks
	private Base62UniqueIDShortURLStrategy strategy;

	@Test
	void testCreateShortURL() {

		var uniqueId = UUID.randomUUID()
		                   .toString();

		var command = new CreateShortURLCommand(new LongURL("https://example.com"));

		when(loadUniqueIDPort.load()).thenReturn(uniqueId);

		var urlMapping = strategy.createShortURL(command);

		var expectedShortUrl = Base62Utils.encode(uniqueId.getBytes());

		assertThat(urlMapping).isNotNull();
		assertThat(urlMapping.shortUrl()
		                     .value()).isEqualTo(expectedShortUrl);
		assertThat(urlMapping.longUrl()).isEqualTo(command.longUrl());

		verify(loadUniqueIDPort, times(1)).load();
	}

}