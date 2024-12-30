package com.example.application.domain.service;

import org.springframework.transaction.annotation.Transactional;

import com.example.application.domain.exception.LongURLNotFoundException;
import com.example.application.domain.model.URLMapping;
import com.example.application.port.in.GetLongURLCommand;
import com.example.application.port.in.GetLongURLUseCase;
import com.example.application.port.out.LoadLongURLPort;
import com.example.common.UseCase;

import io.micrometer.observation.annotation.Observed;

@UseCase
@Observed
@Transactional(readOnly = true)
class GetLongURLService implements GetLongURLUseCase {

	private final LoadLongURLPort loadLongURLPort;

	public GetLongURLService(LoadLongURLPort loadLongURLPort) {
		this.loadLongURLPort = loadLongURLPort;
	}

	@Override
	public URLMapping getLongURL(GetLongURLCommand getLongURLCommand) {
		return this.loadLongURLPort.load(getLongURLCommand.shortURL())
		                           .orElseThrow(() -> new LongURLNotFoundException("Long URL not found"));
	}
}
