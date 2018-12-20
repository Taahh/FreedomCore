package me.taahanis.freedomcore.listeners;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import me.taahanis.freedomcore.FreedomCore;
import me.taahanis.freedomcore.ranking.Rank;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.UUID;

public class PlayerJoin implements Listener {

    FreedomCore plugin = FreedomCore.getPlugin(FreedomCore.class);
    @EventHandler
    public void onJoin(PlayerJoinEvent event){
        if (!plugin.cm.playerExists(event.getPlayer().getUniqueId().toString())){
            plugin.cm.storePlayer(event.getPlayer().getUniqueId().toString(), event.getPlayer().getName(), event.getPlayer().getAddress().toString());
        }
    }


}
