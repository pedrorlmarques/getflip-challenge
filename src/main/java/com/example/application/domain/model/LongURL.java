package com.example.application.domain.model;

import jakarta.validation.constraints.NotBlank;

public record LongURL(@NotBlank String value) {
}
