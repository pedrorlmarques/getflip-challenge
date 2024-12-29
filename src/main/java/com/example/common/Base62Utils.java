package com.example.common;

import java.math.BigInteger;

public final class Base62Utils {

	private static final String BASE62 = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
	private static final BigInteger BASE = BigInteger.valueOf(BASE62.length());

	private Base62Utils() {
		// Utility class, do not instantiate
	}

	public static String encode(byte[] input) {
		if (input == null || input.length == 0) {
			return "";
		}

		var value = new BigInteger(1, input);

		var result = new StringBuilder();

		while (value.compareTo(BigInteger.ZERO) > 0) {
			var divmod = value.divideAndRemainder(BASE);
			result.insert(0, BASE62.charAt(divmod[1].intValue()));
			value = divmod[0];
		}

		return result.toString();
	}

	public static byte[] decode(String input) {

		if (input == null || input.isEmpty()) {
			return new byte[0];
		}

		var value = BigInteger.ZERO;
		for (char c : input.toCharArray()) {
			value = value.multiply(BASE)
			             .add(BigInteger.valueOf(BASE62.indexOf(c)));
		}
		return value.toByteArray();
	}
}
