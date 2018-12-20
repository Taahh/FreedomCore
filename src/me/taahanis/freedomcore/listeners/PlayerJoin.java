package me.taahanis.freedomcore.listeners;

import me.taahanis.freedomcore.FreedomCore;
import me.taahanis.freedomcore.ranking.Rank;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoin implements Listener {

    FreedomCore plugin = FreedomCore.getPlugin(FreedomCore.class);
    @EventHandler
    public void onJoin(PlayerJoinEvent event){
        if (!plugin.cm.playerExists(event.getPlayer().getUniqueId().toString())){
            plugin.cm.storePlayer(event.getPlayer().getUniqueId().toString(), event.getPlayer().getName(), event.getPlayer().getAddress().toString());
        }
        Player player = event.getPlayer();
        if (Rank.isAdmin(player)){
            player.setPlayerListName(Rank.getRank(player).getPrefix() + " " + ChatColor.AQUA + player.getName());
            event.setJoinMessage(ChatColor.AQUA + player.getName() + " is " + ChatColor.ITALIC + Rank.getRank(player).getLoginMsg() + "\n" + ChatColor.YELLOW + player.getName() + " has joined the game");
        }
    }


}
