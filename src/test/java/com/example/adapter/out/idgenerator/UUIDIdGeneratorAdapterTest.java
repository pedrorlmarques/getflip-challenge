package com.example.adapter.out.idgenerator;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class UUIDIdGeneratorAdapterTest {

	@Test
	void testLoadUUID() {
		assertThat(new UUIDIdGeneratorAdapter().load()).isNotNull();
	}
}