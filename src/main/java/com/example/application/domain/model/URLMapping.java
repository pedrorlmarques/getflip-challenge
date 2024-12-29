package com.example.application.domain.model;

import java.time.OffsetDateTime;

public record URLMapping(
		URLMappingId id,
		LongURL longUrl,
		ShortURL shortUrl,
		OffsetDateTime createdAt
) {

	public URLMapping(URLMappingId id,
	                  LongURL longUrl,
	                  ShortURL shortUrl) {
		this(id, longUrl, shortUrl, OffsetDateTime.now());
	}
}
