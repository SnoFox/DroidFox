/*    */package net.snofox.droidfox.commands;

/*    */
/*    */import java.util.List;

import net.snofox.droidfox.User;

/*    */
/*    */
/*    */
/*    */public class Xyzzy extends Command
/*    */{
	/*    */@Override
	public String getName()
	/*    */{
		/* 11 */return "xyzzy";
		/*    */}

	/*    */
	/*    */@Override
	public void execute(User sender, String target, String command, String alias, List<String> args)
	/*    */{
		/* 16 */this.bot.sendMessage(target, "Nothing happens.");
		/*    */}
	/*    */
}

/*
 * Location: Z:\droidfox.safe\ Qualified Name:
 * net.snofox.droidfox.commands.Xyzzy JD-Core Version: 0.6.2
 */