package me.taahanis.freedomcore;

import me.taahanis.freedomcore.admin.AdminList;
import me.taahanis.freedomcore.command.Command_saconfig;
import me.taahanis.freedomcore.connection.ConnectionManager;
import me.taahanis.freedomcore.listeners.ChatManager;
import me.taahanis.freedomcore.listeners.PlayerJoin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class FreedomCore extends JavaPlugin {

    public static FreedomCore plugin;

    public ConnectionManager cm;
    public AdminList al;

    @Override
    public void onEnable()
    {
        plugin = this;
        if (!this.getDataFolder().exists()){
            this.getDataFolder().mkdir();
        }

        getConfig().options().copyDefaults(true);
        saveConfig();

        cm = new ConnectionManager(this);
        cm.checkStuff();
        cm.connect();

        al = new AdminList(this);


        PluginManager pm = getServer().getPluginManager();
        pm.registerEvents(new PlayerJoin(), this);
        pm.registerEvents(new ChatManager(), this);


        getCommand("saconfig").setExecutor(new Command_saconfig());
    }

    @Override
    public void onDisable()
    {
        cm.client.close();
    }
}
