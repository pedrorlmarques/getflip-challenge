package com.example.adapter.out.persistence;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.OffsetDateTime;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;
import org.springframework.context.annotation.Import;

import com.example.TestcontainersConfiguration;
import com.example.application.domain.model.LongURL;
import com.example.application.domain.model.ShortURL;
import com.example.application.domain.model.URLMapping;
import com.example.application.domain.model.URLMappingId;

@DataJdbcTest
@Import(value = {URLMappingPersistenceAdapter.class, TestcontainersConfiguration.class})
class URLMappingPersistenceAdapterTest {

	@Autowired
	private URLMappingPersistenceAdapter urlMappingPersistenceAdapter;

	@Autowired
	private URLMappingRepository urlMappingRepository;

	@Test
	void testLoadURLMapping() {

		var uniqueId = UUID.randomUUID()
		                   .toString();

		var createdAt = OffsetDateTime.now();

		var urlMapping = new URLMapping(new URLMappingId(uniqueId),
		                                new LongURL("https://example.com"),
		                                new ShortURL("VuZ9hpTA"),
		                                createdAt
		);

		this.urlMappingRepository.save(new URLMappingEntity(uniqueId,
		                                                    null,
		                                                    urlMapping.longUrl()
		                                                              .value(),
		                                                    urlMapping.shortUrl()
		                                                              .value(),
		                                                    createdAt));

		var loadedURLMapping = this.urlMappingPersistenceAdapter.load(urlMapping.shortUrl());

		assertThat(loadedURLMapping).isPresent()
		                            .hasValue(urlMapping);
	}


	@Test
	void testSaveURLMapping() {

		var uniqueId = UUID.randomUUID()
		                   .toString();

		var urlMapping = new URLMapping(new URLMappingId(uniqueId),
		                                new LongURL("https://example.com"),
		                                new ShortURL("VuZ9hpTA")
		);

		this.urlMappingPersistenceAdapter.save(urlMapping);

		var savedEntity = this.urlMappingRepository.findById(uniqueId);

		assertThat(savedEntity).isPresent();
		assertThat(savedEntity.get()
		                      .longUrl()).isEqualTo(urlMapping.longUrl()
		                                                      .value());
		assertThat(savedEntity.get()
		                      .shortUrl()).isEqualTo(urlMapping.shortUrl()
		                                                       .value());
	}
}