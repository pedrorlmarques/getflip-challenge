package com.example.application.domain.service;

import org.springframework.transaction.annotation.Transactional;

import com.example.application.domain.model.URLMapping;
import com.example.application.port.in.CreateShortURLCommand;
import com.example.application.port.in.CreateShortURLUseCase;
import com.example.application.port.out.SaveURLMappingPort;
import com.example.common.UseCase;

import io.micrometer.observation.annotation.Observed;

@UseCase
@Observed
@Transactional
class CreateShortURLService implements CreateShortURLUseCase {

	private final ShortURLStrategy shortURLStrategy;
	private final SaveURLMappingPort saveUrlMappingPort;

	public CreateShortURLService(ShortURLStrategy shortURLStrategy,
	                             SaveURLMappingPort saveUrlMappingPort) {
		this.shortURLStrategy = shortURLStrategy;
		this.saveUrlMappingPort = saveUrlMappingPort;
	}

	@Override
	public URLMapping createShortURL(CreateShortURLCommand command) {
		var urlMapping = this.shortURLStrategy.createShortURL(command);
		this.saveUrlMappingPort.save(urlMapping);
		return urlMapping;
	}
}
