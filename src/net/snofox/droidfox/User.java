package net.snofox.droidfox;

import java.io.Serializable;

public class User implements Serializable {
	private static final long serialVersionUID = 1L;
	private final String nick;
	private final String ident;
	private final String host;

	public User(String fullUser) {
		String[] pieces = fullUser.split("!", 2);
		this.nick = pieces[0];
		pieces = pieces[1].split("@", 2);
		this.ident = pieces[0];
		this.host = pieces[1];
	}

	public User(String nick, String ident, String host) {
		this.nick = nick;
		this.ident = ident;
		this.host = host;
	}

	public String getNick() {
		return this.nick;
	}

	public String getIdent() {
		return this.ident;
	}

	public String getHost() {
		return this.host;
	}

	public String getFullHost() {
		return this.ident + "@" + this.host;
	}
}

/*
 * Location: Z:\droidfox.safe\ Qualified Name: net.snofox.droidfox.User JD-Core
 * Version: 0.6.2
 */
