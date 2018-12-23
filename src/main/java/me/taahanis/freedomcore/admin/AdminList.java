package me.taahanis.freedomcore.admin;

import com.mongodb.BasicDBObject;
import me.taahanis.freedomcore.FreedomCore;
import me.taahanis.freedomcore.ranking.Rank;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class AdminList {
	FreedomCore plugin;

	public AdminList(FreedomCore plugin) {
		this.plugin = plugin;
	}

	public void addAdmin(Player player) {
		BasicDBObject obj = new BasicDBObject();
		obj.append("$set", new BasicDBObject().append("Is_Super", true));

		BasicDBObject searchQuery = new BasicDBObject().append("UUID", player.getUniqueId().toString());

		plugin.cm.players.update(searchQuery, obj);
	}

	public void removeAdmin(Player player, CommandSender sender) {
		if (!Rank.isAdmin(player)) {
			sender.sendMessage(ChatColor.RED + "Player isn't an admin");
			return;
		}
		if (Rank.isSenior(player)) {
			unsetSenior(player);
		}
		if (Rank.isTelnet(player)) {
			unsetTelnet(player);
		}
		if (Rank.isSuper(player)) {
			unsetSuper(player);
		}

	}

	public void setRank(Rank rank, Player player) {
		if (rank == Rank.TELNET_ADMIN) {
			if (Rank.isSuper(player)) {
				unsetSuper(player);
			}
			if (Rank.isSenior(player)) {
				unsetSenior(player);
			}
			BasicDBObject obj = new BasicDBObject();
			obj.append("$set", new BasicDBObject().append("Is_Telnet", true));

			BasicDBObject searchQuery = new BasicDBObject().append("UUID", player.getUniqueId().toString());

			plugin.cm.players.update(searchQuery, obj);

		}
		if (rank == Rank.SENIOR_ADMIN) {
			if (Rank.isTelnet(player)) {
				unsetTelnet(player);
			}
			if (Rank.isSuper(player)) {
				unsetSuper(player);
			}
			BasicDBObject obj = new BasicDBObject();
			obj.append("$set", new BasicDBObject().append("Is_Senior", true));

			BasicDBObject searchQuery = new BasicDBObject().append("UUID", player.getUniqueId().toString());

			plugin.cm.players.update(searchQuery, obj);
		}
		if (rank == Rank.SUPER_ADMIN) {
			if (Rank.isSenior(player)) {
				unsetSenior(player);
			}
			if (Rank.isTelnet(player)) {
				unsetTelnet(player);
			}
			BasicDBObject obj = new BasicDBObject();
			obj.append("$set", new BasicDBObject().append("Is_Super", true));

			BasicDBObject searchQuery = new BasicDBObject().append("UUID", player.getUniqueId().toString());

			plugin.cm.players.update(searchQuery, obj);
		}
	}

	// these unset functions are for mongo because it's damn retarded
	public void unsetSenior(Player player) {
		BasicDBObject obj = new BasicDBObject();
		obj.append("$set", new BasicDBObject().append("Is_Senior", false));

		BasicDBObject searchQuery = new BasicDBObject().append("UUID", player.getUniqueId().toString());

		plugin.cm.players.update(searchQuery, obj);
	}

	public void unsetSuper(Player player) {
		BasicDBObject obj = new BasicDBObject();
		obj.append("$set", new BasicDBObject().append("Is_Super", false));

		BasicDBObject searchQuery = new BasicDBObject().append("UUID", player.getUniqueId().toString());

		plugin.cm.players.update(searchQuery, obj);
	}

	public void unsetTelnet(Player player) {
		BasicDBObject obj = new BasicDBObject();
		obj.append("$set", new BasicDBObject().append("Is_Telnet", false));

		BasicDBObject searchQuery = new BasicDBObject().append("UUID", player.getUniqueId().toString());

		plugin.cm.players.update(searchQuery, obj);
	}
}