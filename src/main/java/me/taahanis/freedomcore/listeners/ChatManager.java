package me.taahanis.freedomcore.listeners;

import me.taahanis.freedomcore.ranking.Rank;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class ChatManager implements Listener {

	@EventHandler
	public void onChat(AsyncPlayerChatEvent event) {
		Player player = event.getPlayer();
		if (Rank.getRank(player).getLevel() < 2) {
			event.setFormat(ChatColor.AQUA + player.getName() + ChatColor.DARK_GRAY + ": " + ChatColor.WHITE
					+ event.getMessage());
			return;
		}
		if (Rank.getRank(player).getLevel() >= 2) {
			event.setFormat(Rank.getRank(player).getPrefix() + " " + ChatColor.AQUA + player.getName()
					+ ChatColor.DARK_GRAY + ": " + ChatColor.WHITE + event.getMessage());
			return;
		}
	}
}