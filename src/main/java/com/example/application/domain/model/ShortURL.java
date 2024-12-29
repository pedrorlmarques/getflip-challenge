package com.example.application.domain.model;

import jakarta.validation.constraints.NotBlank;

public record ShortURL(@NotBlank String value) {
}
