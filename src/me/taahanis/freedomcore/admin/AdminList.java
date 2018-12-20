package me.taahanis.freedomcore.admin;

import me.taahanis.freedomcore.FreedomCore;
import me.taahanis.freedomcore.ranking.Rank;
import org.bson.Document;
import org.bukkit.entity.Player;

public class AdminList
{
    FreedomCore plugin;
    public AdminList(FreedomCore plugin){
        this.plugin = plugin;
    }

    public void addAdmin(Player player){
        Document obj = new Document("UUID", player.getUniqueId().toString());
        Document r = new Document("UUID", player.getUniqueId().toString());
        Document found = plugin.cm.players.find(r).first();
        if (found == null){
            return;
        }
        Document set = new Document("$set", r);
        set.append("$set", new Document("Is_Super", true));

        plugin.cm.players.updateOne(found, obj);
    }

    public void setRank(Rank rank, Player player){
        if (rank == Rank.TELNET_ADMIN){
            Document obj = new Document("UUID", player.getUniqueId().toString());
            Document r = new Document("UUID", player.getUniqueId().toString());
            Document found = plugin.cm.players.find(r).first();
            if (found == null){
                return;
            }
            obj.put("Is_Super", false);
            obj.put("Is_Telnet", true);
            obj.put("Is_Senior", false);
            plugin.cm.players.updateMany(found, obj);
        }
        if (rank == Rank.SENIOR_ADMIN){
            Document obj = new Document("UUID", player.getUniqueId().toString());
            Document r = new Document("UUID", player.getUniqueId().toString());
            Document found = plugin.cm.players.find(r).first();
            if (found == null){
                return;
            }
            obj.put("Is_Super", false);
            obj.put("Is_Telnet", false);
            obj.put("Is_Senior", true);
            plugin.cm.players.updateOne(found, obj);
        }
        if (rank == Rank.SUPER_ADMIN){
            Document obj = new Document("UUID", player.getUniqueId().toString());
            Document r = new Document("UUID", player.getUniqueId().toString());
            Document found = plugin.cm.players.find(r).first();
            if (found == null){
                return;
            }
            obj.put("Is_Super", true);
            obj.put("Is_Telnet", false);
            obj.put("Is_Senior", false);
            plugin.cm.players.updateOne(found, obj);
        }
    }
}