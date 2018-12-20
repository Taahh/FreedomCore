package me.taahanis.freedomcore.connection;

import com.mongodb.*;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import me.taahanis.freedomcore.FreedomCore;
import org.bson.Document;
import org.bukkit.Bukkit;


import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class ConnectionManager {
    public MongoCollection<Document> players, punishments;
    public MongoDatabase mcserverdb;
    public MongoClient client;


    private String ip, password, username, database;
    private int port;


    FreedomCore plugin;
    public ConnectionManager(FreedomCore plugin){
        this.plugin = plugin;
    }
    public boolean connect() {

        ip = plugin.getConfig().getString("database.ip"); //freedomcore125a
        port = plugin.getConfig().getInt("database.port");

        username = plugin.getConfig().getString("database.username");
        password = plugin.getConfig().getString("database.password");
        database = plugin.getConfig().getString("database.name");

        ServerAddress addr = new ServerAddress(ip, port);
        List<MongoCredential> credentials = new ArrayList<>();
        credentials.add(MongoCredential.createCredential(username, database, password.toCharArray()));



        try {
            MongoClientURI uri = new MongoClientURI("mongodb://" + username + ":" + password + "@" + ip + "/?authSource=" + database, MongoClientOptions.builder().socketTimeout(120));
            client = new MongoClient(uri);


            Bukkit.getLogger().info("#######################");
            Bukkit.getLogger().info("## MongoDB Connected ##");
            Bukkit.getLogger().info("## Logged in as " + Objects.requireNonNull(client.getCredential()).getUserName() + "@" + client.getCredential().getSource() + "##");
            Bukkit.getLogger().info("#######################");
        } catch (Exception e) {
            System.out.print("Could not connect to database!");
            e.printStackTrace();
            return false;
        }

        mcserverdb = client.getDatabase(database);
        players = mcserverdb.getCollection("players");
        punishments = mcserverdb.getCollection("punishments");
        return true;
    }


    public void storePlayer(String uuid, String name, String ip) {
        try {
            Document doc = new Document("UUID", uuid);
            Document found = (Document) players.find(doc).first();
            doc.append("Name", name);
            doc.append("IP", ip);
            doc.append("Is_Super", false);
            doc.append("Is_Telnet", false);
            doc.append("Is_Senior", false);
            players.insertOne(doc);
        } catch (MongoException e){
            e.printStackTrace();
        }
    }
    public boolean playerExists(String uuid) {
        try {
            Document doc = new Document("UUID", uuid);
            Document found = (Document) players.find(doc).first();
            if (found != null){
                return true;
            }
            return false;
        } catch(MongoException e){
            e.printStackTrace();
            Bukkit.getLogger().info("PLAYER EXISTS FUNCTION");
        }
        return false;
    }
}
