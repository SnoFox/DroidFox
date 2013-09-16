package net.snofox.droidfox;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import net.snofox.droidfox.commands.Command;

import org.jibble.pircbot.PircBot;

public class CommandHandler {
	protected static Set<Class<? extends Command>> commands = new HashSet<>();
	protected Set<Command> activeCommands;
	protected PircBot bot;
	protected boolean baked = false;

	public CommandHandler(PircBot bot) {
		this.bot = bot;
	}

	public static boolean register(Class<? extends Command> clazz) {
		Logger.debug("Registered command " + clazz.getSimpleName());
		return commands.add(clazz);
	}

	public boolean bake() {
		if (this.activeCommands != null) {
			Logger.warning("Something tried to re-bake the command list.");
			return false;
		}
		this.activeCommands = new HashSet<Command>();
		for (final Class<? extends Command> command : commands) {
			try {
				final Command cmd = (Command) command.newInstance();
				cmd.setPircBot(this.bot);
				cmd.initialize();
				this.activeCommands.add(cmd);
				Logger.debug("Instantiated command handle " + cmd.getName());
			} catch (InstantiationException | IllegalAccessException
					| IllegalArgumentException | SecurityException e) {
				Logger.severe("Unable to instantiate the command: "
						+ command.getName());
				e.printStackTrace();
			}
		}
		this.baked = true;
		return true;
	}

	public boolean handle(User user, String target, String message) {
		boolean foundCommand = false;
		List<String> args = Arrays.asList(message.split(" "));
		final String cmd = ((String) args.get(0)).substring(1);
		if (args.size() > 1) {
			args = args.subList(1, args.size());
		} else {
			args = new ArrayList<String>();
		}

		for (final Command command : this.activeCommands) {
			if (command.getName().equalsIgnoreCase(cmd)) {
				command.execute(user, target, command.getName(), cmd, args);
				foundCommand = true;
			} else if (command.getAliases() != null) {
				for (final String alias : command.getAliases()) {
					if (alias.equalsIgnoreCase(cmd)) {
						command.execute(user, target, command.getName(), alias,
								args);
						foundCommand = true;
					}
				}
			}
		}
		return foundCommand;
	}
}
