package com.example.adapter.out.persistence;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import io.micrometer.observation.annotation.Observed;

@Observed
interface URLMappingRepository extends CrudRepository<URLMappingEntity,String> {

	Optional<URLMappingEntity> findByShortUrl(String shortUrl);

}
