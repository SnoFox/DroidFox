package net.snofox.droidfox;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import net.snofox.droidfox.factoids.Factoids;

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
		final User user = new User(sender, login, hostname);
		if (message.charAt(0) == '!') {
			// Got command
			List<String> args = Arrays.asList(message.split(" "));
			final String cmd = args.get(0).substring(1);
			if (args.size() > 1) {
				args = args.subList(1, args.size());
			} else {
				args = new ArrayList<String>();
			}
			
			this.commandHandler.handle(user, channel, cmd, args);
		} else {
			// Factoid/AI stuff here
			// Questions -> Answers
			// Who is proper noun? -> Proper noun is blah.
			// What is noun? -> Noun is blah.
			// How is noun? -> Noun is adjective
			// When is noun? -> Noun is at blah.
			// Where is noun? -> Noun is at blah.
			// Why is noun? -> Because noun is blah.
			// How do I verb? -> You can verb by process.
			// How does noun verb? -> Verb works by blah.
			
			String nick = this.getNick();
			
			if(message.matches("^" + nick + "[:,] .+")) {
				List<String> args = Arrays.asList(message.split(" "));
				final String cmd = args.get(1);
				if (args.size() > 1) {
					args = args.subList(2, args.size());
				} else {
					args = new ArrayList<String>();
				}
				if(!this.commandHandler.handle(user, channel, cmd, args)) {
					// factoid takes presidence over command
					// Factoid stuff here
					this.sendMessage(channel, "My apologies, Master " + sender + ", but I am unable to acquire a function for your command.");
				}
			}
			Factoids.tryFactoid(user, channel, message);
		}
	}

	@Override
	public void log(String line) {
		Logger.log(line, Logger.LogLevel.NATIVE);
	}
}
