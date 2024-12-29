package com.example.application.domain.service;

import com.example.application.domain.model.URLMapping;
import com.example.application.port.in.CreateShortURLCommand;

interface ShortURLStrategy {
	URLMapping createShortURL(CreateShortURLCommand command);
}
