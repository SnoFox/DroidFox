package net.snofox.droidfox;

import java.io.IOException;

import org.jibble.pircbot.IrcException;
import org.jibble.pircbot.NickAlreadyInUseException;
import org.jibble.pircbot.PircBot;

public class DroidFox extends PircBot {

	CommandHandler commandHandler;

	DroidFox(String nick, String server) {
		super();
		this.setName(nick);
		this.setAutoNickChange(true);
		this.commandHandler = new CommandHandler(this);
		this.commandHandler.bake();
		try {
			this.connect(server);
		} catch (final NickAlreadyInUseException e) {
			Logger.info("Nick already in use; letting PircBot change it.");
		} catch (final IOException e) {
			e.printStackTrace();
		} catch (final IrcException e) {
			e.printStackTrace();
		}
	}

	@Override
	protected void onConnect() {
		super.onConnect();
		this.joinChannel("#foxden");
	}

	@Override
	protected void onMessage(String channel, String sender, String login, String hostname, String message) {
		// Logger.info(String.format("Message to %s from %s: %s", channel,
		// sender, message));
		if (message.charAt(0) == '!') {
			// Got command
			final User user = new User(sender, login, hostname);
			this.commandHandler.handle(user, channel, message);
		} else {
			// Factoid/AI stuff here
			// Questions -> Answers
			// Who is proper noun? -> Proper noun is blah.
			// What is noun? -> Noun is blah.
			// When is noun? -> Noun is at blah.
			// Why is noun? -> Because noun is blah.
			// Where is noun? -> Noun is at blah.
			// How is noun? -> Noun is adjective
			// How do I verb? -> You can verb by process.
			// How does noun verb? -> Verb works by blah.

		}
	}

	@Override
	public void log(String line) {
		Logger.log(line, Logger.LogLevel.NATIVE);
	}
}
