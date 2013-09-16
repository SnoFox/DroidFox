package net.snofox.droidfox.storage;

import java.io.File;
import java.net.URISyntaxException;
import java.util.Map;

import net.snofox.droidfox.Logger;
import net.snofox.droidfox.Main;

import com.sleepycat.bind.EntryBinding;
import com.sleepycat.bind.serial.ClassCatalog;
import com.sleepycat.bind.serial.SerialBinding;
import com.sleepycat.bind.serial.StoredClassCatalog;
import com.sleepycat.collections.StoredMap;
import com.sleepycat.collections.TransactionRunner;
import com.sleepycat.collections.TransactionWorker;
import com.sleepycat.je.Database;
import com.sleepycat.je.DatabaseConfig;
import com.sleepycat.je.DatabaseException;
import com.sleepycat.je.Environment;
import com.sleepycat.je.EnvironmentConfig;

public class SimpleBDB<K, V> {
	protected Map<K, V> data;
	protected String dbName;
	protected Environment env;
	protected Database catalogDB;
	protected ClassCatalog catalog;
	protected Database database;
	protected TransactionRunner runner;

	public SimpleBDB(Class<K> keyClass, Class<V> valueClass, String dbName) {
		this.dbName = dbName;
		File dbDir = null;
		try {
			dbDir = new File(Main.class.getProtectionDomain().getCodeSource()
					.getLocation().toURI());
			dbDir = new File(dbDir, "data");
			dbDir.mkdirs();
		} catch (final URISyntaxException e) {
			e.printStackTrace();
		}

		final EnvironmentConfig envConfig = new EnvironmentConfig();
		envConfig.setTransactional(true);
		envConfig.setAllowCreate(true);
		this.env = new Environment(dbDir, envConfig);

		final DatabaseConfig dbConfig = new DatabaseConfig();
		dbConfig.setTransactional(true);
		dbConfig.setAllowCreate(true);

		this.catalogDB = this.env.openDatabase(null, "java_class_catalog",
				dbConfig);
		this.catalog = new StoredClassCatalog(this.catalogDB);
		final EntryBinding<K> keyBinding = new SerialBinding<K>(this.catalog,
				keyClass);
		final EntryBinding<V> valueBinding = new SerialBinding<V>(this.catalog,
				valueClass);

		this.database = this.env.openDatabase(null, dbName, dbConfig);

		this.data = new StoredMap<K, V>(this.database, keyBinding, valueBinding, true);
		this.runner = new TransactionRunner(this.database.getEnvironment());
	}

	public void close() {
		this.catalog.close();
		this.catalogDB.close();
		this.database.close();
		this.env.close();
	}

	public V get(K key) {
		return this.data.get(key);
	}

	public void put(final K key, final V value) {
		try {
			this.runner.run(new TransactionWorker() {
				@Override
				public void doWork() throws Exception {
					SimpleBDB.this.data.put(key, value);
				}
			});
		} catch (final DatabaseException e) {
			Logger.severe("Error writing to database " + this.dbName
					+ "! You should check for corruption!");
			e.printStackTrace();
		} catch (final Exception e) {
			Logger.severe("Error writing to database. Not sure why: "
					+ e.getClass().getCanonicalName() + ": " + e.getMessage());
			e.printStackTrace();
		}
	}

	public void remove(final K key) {
		try {
			this.runner.run(new TransactionWorker() {
				@Override
				public void doWork() throws Exception {
					SimpleBDB.this.data.remove(key);
				}
			});
		} catch (final DatabaseException e) {
			Logger.severe("Error writing to database " + this.dbName
					+ "! You should check for corruption!");
			e.printStackTrace();
		} catch (final Exception e) {
			Logger.severe("Error writing to database. Not sure why: "
					+ e.getClass().getCanonicalName() + ": " + e.getMessage());
			e.printStackTrace();
		}
	}
}
