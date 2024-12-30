package com.example.adapter.in.web;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.application.domain.model.LongURL;
import com.example.application.port.in.CreateShortURLCommand;
import com.example.application.port.in.CreateShortURLUseCase;

@RestController
@RequestMapping("/api/v1/url")
class CreateShortURLUseCaseController {

	private final CreateShortURLUseCase createShortURLUseCase;

	public CreateShortURLUseCaseController(CreateShortURLUseCase createShortURLUseCase) {
		this.createShortURLUseCase = createShortURLUseCase;
	}

	@PostMapping("/shorten")
	public CreateShortURLResponse getShortURL(@RequestParam("long_url") String longUrl) {
		var urlMapping = this.createShortURLUseCase
				.createShortURL(new CreateShortURLCommand(new LongURL(longUrl)));

		return new CreateShortURLResponse(urlMapping.shortUrl()
		                                            .value());
	}

	record CreateShortURLResponse(String shortURL) {
	}
}

