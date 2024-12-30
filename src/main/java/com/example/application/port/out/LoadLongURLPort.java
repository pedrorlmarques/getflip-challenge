package com.example.application.port.out;

import java.util.Optional;

import com.example.application.domain.model.ShortURL;
import com.example.application.domain.model.URLMapping;

public interface LoadLongURLPort {
	Optional<URLMapping> load(ShortURL shortURL);
}
