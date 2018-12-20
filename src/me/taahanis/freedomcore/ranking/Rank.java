package me.taahanis.freedomcore.ranking;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import me.taahanis.freedomcore.FreedomCore;
import org.bukkit.ChatColor;
import org.bson.Document;
import org.bukkit.entity.Player;

public enum Rank {

    OP(1, "an OP", Type.PLAYER, "", ChatColor.RED),
    SUPER_ADMIN(2, "a Super Administrator", Type.ADMIN, "SA", ChatColor.AQUA),
    TELNET_ADMIN(3, "a Telnet Administrator", Type.ADMIN, "STA", ChatColor.DARK_GREEN),
    SENIOR_ADMIN(4, "a Senior Administrator", Type.ADMIN, "SrA", ChatColor.LIGHT_PURPLE);


    String loginMsg, prefix;
    int level;
    ChatColor color;
    Type type;

    private Rank(int l, String lM, Type t, String prfx, ChatColor c){
        this.level = l;
        this.loginMsg = lM;
        this.prefix = c + prfx;
        this.color = c;
    }

    public boolean isAtLeast(Rank rank){
        return level >= rank.getLevel();
    }
    public static Rank findRank(String string)
    {
        try
        {
            return Rank.valueOf(string.toUpperCase());
        }
        catch (Exception ignored)
        {
        }

        return Rank.OP;
    }
    public int getLevel(){
        return level;
    }

    public String getLoginMsg(){
        return color + loginMsg;
    }
    public String getPrefix(){
        return ChatColor.DARK_GRAY + "[" + color + prefix + ChatColor.DARK_GRAY + "]";
    }
    public ChatColor getColor() {
        return color;
    }
    public Type getType(){
        return type;
    }

    public static boolean isAdmin(Player player){
        return isSuper(player) || isTelnet(player) || isSenior(player);
    }

    public static boolean isSuper(Player player) {

        DBObject query = new BasicDBObject("UUID", player.getUniqueId().toString());
        DBObject project = new BasicDBObject("Is_Super", true);

        DBObject result = FreedomCore.plugin.cm.players.findOne(query, project);
        boolean issuper = (boolean) result.get("Is_Super");
        return issuper;
    }
    public static boolean isTelnet(Player player) {
        DBObject query = new BasicDBObject("UUID", player.getUniqueId().toString());
        DBObject project = new BasicDBObject("Is_Telnet", true);

        DBObject result = FreedomCore.plugin.cm.players.findOne(query, project);
        boolean istelnet = (boolean) result.get("Is_Telnet");
        return istelnet;
    }
    public static boolean isSenior(Player player){
        DBObject query = new BasicDBObject("UUID", player.getUniqueId().toString());
        DBObject project = new BasicDBObject("Is_Senior", true);

        DBObject result = FreedomCore.plugin.cm.players.findOne(query, project);
        boolean issenior = (boolean) result.get("Is_Senior");
        return issenior;
    }


    public static Rank getRank(Player player){
        if (isSuper(player)){
            return Rank.SUPER_ADMIN;
        }
        if (isTelnet(player)){
            return Rank.TELNET_ADMIN;
        }
        if (isSenior(player)){
            return Rank.SENIOR_ADMIN;
        }
        return Rank.OP;
    }
    public enum Type {
        ADMIN,
        PLAYER
    }
}

