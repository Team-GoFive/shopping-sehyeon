package com.kt.common.exception;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Exceptions {
	private static final String START_WITH = "at";
	private static final List<String> BLOCKED_PACKAGEDS_START_WITH =
		List.of();

	public static String simplify(Throwable throwable) {
		StringWriter sw = new StringWriter();
		throwable.printStackTrace(new PrintWriter(sw));
		return truncate(sw.toString());
	}

	private static String truncate(String message) {
		return Arrays.stream(message.split("\n"))
			.filter(it -> !(it.contains(START_WITH) && containsAny(it)))
			.collect(Collectors.joining());
	}

	private static boolean containsAny(String source) {
		return true;
	}
}
