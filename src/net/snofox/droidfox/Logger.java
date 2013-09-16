package net.snofox.droidfox;

public class Logger {

	public static void log(String message, LogLevel severity) {
		// if(severity.equals(LogLevel.NATIVE)) return;
		System.out.println("[" + severity.toString() + "] " + message);
	}

	public static void info(String message) {
		log(message, LogLevel.INFO);
	}

	public static void warning(String message) {
		log(message, LogLevel.WARNING);
	}

	public static void severe(String message) {
		log(message, LogLevel.SEVERE);
	}

	public static void debug(String message) {
		log(message, LogLevel.DEBUG);
	}

	public enum LogLevel {
		INFO, WARNING, SEVERE, DEBUG, RAW, NATIVE;

		@Override
		public String toString() {
			switch (this) {
				case INFO:
					return "Info";
				case WARNING:
					return "Warning";
				case SEVERE:
					return "Severe";
				case DEBUG:
					return "Debug";
				case NATIVE:
					return "Native"; // Lines from PircBot's logger
				default:
					return super.toString();
			}
		}
	}
}
