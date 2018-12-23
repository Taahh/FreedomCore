package me.taahanis.freedomcore.listeners;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.ServerListPingEvent;

import me.taahanis.freedomcore.FreedomCore;

// This listener only gets implemented if ProtocolLib is not available

public class MOTDLegacyListener implements Listener {

	public static String motd = FreedomCore.plugin.getConfig().getString("server.motd");
	public static String versionname = FreedomCore.plugin.getConfig().getString("server.versionname");

	@EventHandler
	public void onServerListPingEvent(ServerListPingEvent event) {
		versionname = versionname.replace("$onlineplayers", Integer.toString(Bukkit.getOnlinePlayers().size()));
		versionname = versionname.replace("$maxplayers", Integer.toString(Bukkit.getServer().getMaxPlayers()));

		event.setMotd(ChatColor.translateAlternateColorCodes('&', motd) + "\n"
				+ ChatColor.translateAlternateColorCodes('&', versionname));
	}
}
