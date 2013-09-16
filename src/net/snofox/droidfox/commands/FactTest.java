package net.snofox.droidfox.commands;

import java.util.List;

import net.snofox.droidfox.User;
import net.snofox.droidfox.factoids.Factoid;
import net.snofox.droidfox.factoids.Reply;
import net.snofox.droidfox.storage.SimpleBDB;

public class FactTest extends Command {

	SimpleBDB<String, Factoid> database;

	@Override
	public String getName() {
		return "facttest";
	}

	@Override
	public void initialize() {
		this.database = new SimpleBDB<String, Factoid>(String.class,
				Factoid.class, "factoid_tests");
	}

	@Override
	public void execute(User sender, String target, String command, String alias, List<String> args) {
		if (args.size() < 2) {
			this.bot.sendMessage(target, "Bad syntax. Bad!");
			return;
		}
		final String subCmd = args.get(0).toLowerCase();
		final String factoid = args.get(1);
		Factoid fact = this.database.get(factoid.toLowerCase());
		if (subCmd.equals("del")) {
			if (fact != null) {
				this.database.remove(factoid);
				this.bot.sendMessage(target, "I forgot about " + fact.getName());
			} else {
				this.bot.sendMessage(target, "I don't know about " + factoid);
			}
			return;
		} else if (subCmd.equals("add")) {
			final StringBuilder sb = new StringBuilder();
			final List<String> tmp = args.subList(2, args.size());
			for (int x = 0; x < tmp.size(); ++x) {
				sb.append(tmp.get(x));
				if (x != tmp.size() - 1) {
					sb.append(' ');
				}
			}
			final String replyStr = sb.toString();
			final Reply reply = new Reply(replyStr, sender);
			if (fact == null) {
				fact = new Factoid(factoid, reply);
				this.database.put(factoid.toLowerCase(), fact);
				this.bot.sendMessage(target, "I learned about " + factoid
						+ " for the first time.");
			} else {
				fact.addReply(reply);
				this.database.put(factoid.toLowerCase(), fact);
				this.bot.sendMessage(target, "I learned more about " + factoid
						+ ".");
			}
			return;
		} else if (subCmd.equals("get")) {
			if (fact == null) {
				this.bot.sendMessage(target, "I don't know anything about "
						+ factoid);
			} else {
				fact.use();
				this.database.put(factoid.toLowerCase(), fact);
				final Reply reply = fact.getAReply();
				this.bot.sendMessage(target, "Here's a random thing about "
						+ factoid + ": " + reply.getLine() + ". "
						+ reply.getTeacher().getNick() + " <"
						+ reply.getTeacher().getIdent() + "@"
						+ reply.getTeacher().getHost()
						+ "> taught me about it on " + reply.getDate()
						+ ". This fact has been requested " + fact.useCount()
						+ " times.");
			}
			return;
		} else {
			this.bot.sendMessage(target, "Unknown subcommand.");
		}
	}
}
