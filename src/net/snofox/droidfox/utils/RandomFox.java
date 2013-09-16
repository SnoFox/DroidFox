package net.snofox.droidfox.utils;

import java.util.Random;

public class RandomFox {
	private static RandomFox instance;
	private final Random random;

	public RandomFox() {
		instance = this;
		this.random = new Random();
	}

	public static Random getRandom() {
		return instance.random;
	}

	public static int nextInt() {
		return instance.random.nextInt();
	}

	public static int nextInt(int upperBound) {
		return instance.random.nextInt(upperBound);
	}
}
