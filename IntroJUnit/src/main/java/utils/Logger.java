package utils;

public class Logger {

	private Logger() {

	}

	public static void log(String message, Object... args) {
		System.out.println(String.format(message, args));
	}
}
