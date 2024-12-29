package com.example.adapter.out.idgenerator;

import java.util.UUID;

import org.springframework.context.annotation.Fallback;
import org.springframework.stereotype.Component;

import com.example.application.port.out.LoadUniqueIDPort;

/**
 * This class is responsible for generating a unique ID using UUID.
 * UUID is a 128-bit number used to uniquely identify information.
 * Is unique across both space and time, for practical purposes.
 */
@Component
@Fallback
class UUIDIdGeneratorAdapter implements LoadUniqueIDPort {

	@Override
	public String load() {
		return UUID.randomUUID()
		           .toString()
		           .substring(0, 6);
	}
}
