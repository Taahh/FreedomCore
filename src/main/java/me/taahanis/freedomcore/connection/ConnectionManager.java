package me.taahanis.freedomcore.connection;

import com.mongodb.*;
import me.taahanis.freedomcore.FreedomCore;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ConnectionManager {
	public DBCollection players, punishments;
	public DB mcserverdb;
	public MongoClient client;

	public String ip, password, username, database;
	public int port;

	private FreedomCore plugin;

	public ConnectionManager(FreedomCore plugin) {
		this.plugin = plugin;
	}

	@SuppressWarnings({ "unused", "deprecation" })
	public boolean connect() {

		ip = plugin.getConfig().getString("database.ip"); // freedomcore125a
		port = plugin.getConfig().getInt("database.port");

		username = plugin.getConfig().getString("database.username");
		password = plugin.getConfig().getString("database.password");
		database = plugin.getConfig().getString("database.name");

		ServerAddress addr = new ServerAddress(ip, port);
		List<MongoCredential> credentials = new ArrayList<>();
		credentials.add(MongoCredential.createCredential(username, database, password.toCharArray()));

		try {
			MongoClientURI uri = new MongoClientURI(
					"mongodb://" + username + ":" + password + "@" + ip + "/?authSource=" + database,
					MongoClientOptions.builder().socketTimeout(120));
			client = new MongoClient(uri);

			plugin.getLogger().info("#######################");
			plugin.getLogger().info("## MongoDB Connected ##");
			plugin.getLogger().info("## Logged in as " + Objects.requireNonNull(client.getCredential()).getUserName()
					+ "@" + client.getCredential().getSource() + "##");
			plugin.getLogger().info("#######################");
		} catch (Exception e) {
			plugin.getLogger().severe(e.toString());
			plugin.getLogger()
					.severe("Could not connect to database. Please set the database in settings - disabling for now.");
			plugin.disableOnFailureLogSupressor();
			plugin.getServer().getPluginManager().disablePlugin(plugin);
			return false;
		}

		mcserverdb = client.getDB(database);
		players = mcserverdb.getCollection("players");
		punishments = mcserverdb.getCollection("punishments");
		return true;
	}

	@SuppressWarnings("unused")
	public void storePlayer(String uuid, String name, String ip) {
		try {
			DBObject doc = new BasicDBObject("UUID", uuid);
			DBObject found = players.findOne(doc);
			doc.put("Name", name);
			doc.put("IP", ip);
			doc.put("Is_Super", false);
			doc.put("Is_Telnet", false);
			doc.put("Is_Senior", false);
			players.insert(doc);
		} catch (MongoException e) {
			plugin.getLogger().severe(e.toString());
			plugin.getLogger().severe("Database problem: Could not store player info");
		}
	}

	public boolean playerExists(String uuid) {
		try {
			DBObject doc = new BasicDBObject("UUID", uuid);
			DBObject found = players.findOne(doc);
			if (found != null) {
				return true;
			}
			return false;
		} catch (MongoException e) {
			plugin.getLogger().warning(e.toString());
			plugin.getLogger().warning("Could not find player in database.");
		}
		return false;
	}

}
