package net.snofox.droidfox.factoids;


import net.snofox.droidfox.User;

public class Factoids {

	public static Factoid tryFactoid(User who, String to, String message) {
		// Factoid?
		// What is factoid?
		// How do I factoid?
		// How does noun factoid?
		if(message.matches("^(who|what|where)\\s+(is|was|are)\\s+(.+?)[\\?!\\.]*$")) {
			
		}
		return null;
	}
}
