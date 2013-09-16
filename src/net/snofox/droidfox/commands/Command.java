package net.snofox.droidfox.commands;

import java.util.List;

import net.snofox.droidfox.CommandHandler;
import net.snofox.droidfox.User;

import org.jibble.pircbot.PircBot;

public abstract class Command {
	protected PircBot bot;

	public abstract String getName();

	public abstract void execute(User paramUser, String paramString1, String paramString2, String paramString3, List<String> paramList);

	public Command() {
		register();
		initialize();
	}

	public String[] getAliases() {
		return null;
	}

	public void initialize() {
	}

	protected void register() {
		CommandHandler.register(getClass());
	}

	public void setPircBot(PircBot bot) {
		this.bot = bot;
	}
}
