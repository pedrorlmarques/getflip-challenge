package com.example.adapter.in.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.application.domain.model.ShortURL;
import com.example.application.port.in.GetLongURLCommand;
import com.example.application.port.in.GetLongURLUseCase;

@RestController
@RequestMapping("/api/v1/url")
class GetLongURLUseCaseController {

	private final GetLongURLUseCase getLongURLUseCase;

	public GetLongURLUseCaseController(GetLongURLUseCase getLongURLUseCase) {
		this.getLongURLUseCase = getLongURLUseCase;
	}

	@GetMapping("/{short-url}")
	public GetLongURLResponse getLongUrl(@PathVariable("short-url") String shortUrl) {

		var urlMapping = this.getLongURLUseCase
				.getLongURL(new GetLongURLCommand(new ShortURL(shortUrl)));

		return new GetLongURLResponse(urlMapping.longUrl()
		                                        .value());
	}

	record GetLongURLResponse(String longURL) {
	}
}
