package net.snofox.droidfox.commands;

import java.util.List;

import net.snofox.droidfox.User;
import de.congrace.exp4j.ExpressionBuilder;
import de.congrace.exp4j.UnknownFunctionException;
import de.congrace.exp4j.UnparsableExpressionException;

public class Calc extends Command {

	@Override
	public String getName() {
		return "calc";
	}

	@Override
	public void execute(User sender, String target, String command, String alias, List<String> args) {
		final ExpressionBuilder exp = new ExpressionBuilder(args.toString());
		try {
			final double result = exp.build().calculate();
			this.bot.sendMessage(target, sender.getNick() + ": " + result);
		} catch (UnknownFunctionException | UnparsableExpressionException e) {
			this.bot.sendMessage(target,
					"Sorry " + sender.getNick()
							+ ", I wasn't able to parse your expression ("
							+ args.toString() + ")");
		} catch (final ArithmeticException e) {
			this.bot.sendMessage(
					target,
					"Sorry "
							+ sender.getNick()
							+ ", there was an arithmetic error. Did you divide by zero? ("
							+ args.toString() + ")");
		}
	}

}
