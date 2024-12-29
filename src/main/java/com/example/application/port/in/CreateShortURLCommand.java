package com.example.application.port.in;

import com.example.application.domain.model.LongURL;

import jakarta.validation.constraints.NotNull;

public record CreateShortURLCommand(@NotNull LongURL longUrl) {
}
