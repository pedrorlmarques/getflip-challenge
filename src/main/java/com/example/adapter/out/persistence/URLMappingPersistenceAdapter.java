package com.example.adapter.out.persistence;

import java.util.Optional;
import java.util.function.Function;

import org.springframework.stereotype.Component;

import com.example.application.domain.model.LongURL;
import com.example.application.domain.model.ShortURL;
import com.example.application.domain.model.URLMapping;
import com.example.application.domain.model.URLMappingId;
import com.example.application.port.out.LoadLongURLPort;
import com.example.application.port.out.SaveURLMappingPort;

import io.micrometer.observation.annotation.Observed;

@Component
@Observed
class URLMappingPersistenceAdapter implements SaveURLMappingPort, LoadLongURLPort {

	private final URLMappingRepository urlMappingRepository;

	public URLMappingPersistenceAdapter(URLMappingRepository urlMappingRepository) {
		this.urlMappingRepository = urlMappingRepository;
	}

	@Override
	public void save(URLMapping urlMapping) {

		var entity = new URLMappingEntity(urlMapping.id()
		                                            .value(),
		                                  null,
		                                  urlMapping.longUrl()
		                                            .value(),
		                                  urlMapping.shortUrl()
		                                            .value(),
		                                  urlMapping.createdAt());

		this.urlMappingRepository.save(entity);
	}

	@Override
	public Optional<URLMapping> load(ShortURL shortURL) {
		return this.urlMappingRepository
				.findByShortUrl(shortURL.value())
				.map(toUrlMapping());
	}

	private Function<URLMappingEntity,URLMapping> toUrlMapping() {
		return entity -> new URLMapping(new URLMappingId(entity.id()),
		                                new LongURL(entity.longUrl()),
		                                new ShortURL(entity.shortUrl()),
		                                entity.createdAt());
	}
}
