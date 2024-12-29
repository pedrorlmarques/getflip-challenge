package com.example.common;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class Base62UtilsTest {

	@Test
	void testEncodeEmptyInput() {
		var input = new byte[0];
		var encoded = Base62Utils.encode(input);
		assertThat(encoded).isEmpty();
	}

	@Test
	void testDecodeEmptyInput() {
		var input = "";
		var decoded = Base62Utils.decode(input);
		assertThat(decoded).isEmpty();
	}

	@Test
	void testEncodeAndDecode() {
		var input = "example".getBytes();
		var encoded = Base62Utils.encode(input);
		var decoded = Base62Utils.decode(encoded);
		assertThat(decoded).isEqualTo(input);
	}
}