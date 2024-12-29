package com.example.application.domain.service;

import org.springframework.context.annotation.Fallback;
import org.springframework.stereotype.Component;

import com.example.application.domain.model.ShortURL;
import com.example.application.domain.model.URLMapping;
import com.example.application.domain.model.URLMappingId;
import com.example.application.port.in.CreateShortURLCommand;
import com.example.application.port.out.LoadUniqueIDPort;
import com.example.common.Base62Utils;

import io.micrometer.observation.annotation.Observed;

@Observed
@Component
@Fallback
class Base62UniqueIDShortURLStrategy implements ShortURLStrategy {

	private final LoadUniqueIDPort loadUniqueIDPort;

	public Base62UniqueIDShortURLStrategy(LoadUniqueIDPort loadUniqueIDPort) {
		this.loadUniqueIDPort = loadUniqueIDPort;
	}

	@Override
	public URLMapping createShortURL(CreateShortURLCommand command) {

		var uniqueId = loadUniqueIDPort.load();

		var shortUrl = new ShortURL(Base62Utils.encode(uniqueId.getBytes()));

		return new URLMapping(new URLMappingId(uniqueId), command.longUrl(), shortUrl);
	}
}
