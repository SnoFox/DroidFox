package net.snofox.droidfox.commands;

import java.util.List;

import net.snofox.droidfox.User;

public class Test extends Command {
	@Override
	public String getName() {
		return "test";
	}

	@Override
	public String[] getAliases() {
		return new String[] { "testing", "tester" };
	}

	@Override
	public void execute(User sender, String target, String command, String alias, List<String> args) {
		this.bot.sendMessage(target, "Hey there, " + sender.getNick()
				+ ". You executed this test command using the alias \"" + alias
				+ "\"");
	}
}

/*
 * Location: Z:\droidfox.safe\ Qualified Name: net.snofox.droidfox.commands.Test
 * JD-Core Version: 0.6.2
 */
