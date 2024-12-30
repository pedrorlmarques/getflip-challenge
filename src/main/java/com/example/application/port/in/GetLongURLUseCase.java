package com.example.application.port.in;

import com.example.application.domain.model.URLMapping;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

public interface GetLongURLUseCase {
	URLMapping getLongURL(@Valid @NotNull GetLongURLCommand getLongURLCommand);
}
