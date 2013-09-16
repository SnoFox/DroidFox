package net.snofox.droidfox.factoids;

import java.io.Serializable;
import java.util.Date;

import net.snofox.droidfox.User;

public class Reply implements Serializable {

	private static final long serialVersionUID = 1L;
	private final String reply;
	private final User teacher;
	private final Date created;
	private int confidence;

	public Reply(String reply, User teacher) {
		this.reply = reply;
		this.teacher = teacher;
		this.created = new Date();
	}

	public String getLine() {
		return this.reply;
	}

	public User getTeacher() {
		return this.teacher;
	}

	public int getConfidence() {
		return this.confidence;
	}

	public void addConfidence(int x) {
		this.confidence += x;
	}

	public Date getDate() {
		return this.created;
	}
}
