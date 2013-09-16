package net.snofox.droidfox;

import net.snofox.droidfox.commands.Calc;
import net.snofox.droidfox.commands.FactTest;
import net.snofox.droidfox.commands.Test;
import net.snofox.droidfox.commands.Xyzzy;
import net.snofox.droidfox.utils.RandomFox;

public class Main {

	public static void main(String[] args) {
		Logger.info("Initializing DroidFox");
		new RandomFox();
		CommandHandler.register(Test.class);
		CommandHandler.register(Calc.class);
		CommandHandler.register(Xyzzy.class);
		CommandHandler.register(FactTest.class);
		new DroidFox("DroidFox", "irc.ext3.net");
		Logger.info("Bot forked; exiting main thread.");
	}
}
