package com.example.application.port.in;

import com.example.application.domain.model.ShortURL;

import jakarta.validation.constraints.NotNull;

public record GetLongURLCommand(@NotNull ShortURL shortURL) {
}
