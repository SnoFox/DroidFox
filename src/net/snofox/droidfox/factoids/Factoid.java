package net.snofox.droidfox.factoids;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import net.snofox.droidfox.utils.RandomFox;

public class Factoid implements Serializable {

	private static final long serialVersionUID = 2L;

	String name;
	List<Reply> replies;
	int useCount;

	public Factoid(String name, Reply reply) {
		this.name = name;
		this.replies = new ArrayList<Reply>();
		this.replies.add(reply);
		this.useCount = 0;
	}

	public Factoid(String name, Collection<Reply> replies) {
		this.name = name;
		this.replies = new ArrayList<Reply>();
		this.replies.addAll(replies);
	}

	public void addReply(Reply reply) {
		this.replies.add(reply);
	}

	public void addReplies(Collection<Reply> replies) {
		this.replies.addAll(replies);
	}

	public List<Reply> getReplies() {
		return this.replies;
	}

	public Reply getAReply() {
		return this.replies.get(RandomFox.nextInt(this.replies.size()));
	}

	public String getName() {
		return this.name;
	}

	public int useCount() {
		return this.useCount;
	}

	public void use() {
		this.useCount = this.useCount + 1;
	}
}
