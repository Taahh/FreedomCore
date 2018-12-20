package me.taahanis.freedomcore.ranking;

import me.taahanis.freedomcore.FreedomCore;
import net.md_5.bungee.api.ChatColor;
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
        this.prefix = ChatColor.DARK_GRAY + "[" + c + prfx + ChatColor.DARK_GRAY + "]";
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
        return loginMsg;
    }
    public String getPrefix(){
        return prefix;
    }

    public ChatColor getColor() {
        return color;
    }
    public Type getType(){
        return type;
    }

    public static boolean isAdmin(Player player){
        Document doc = new Document("UUID", player.getUniqueId().toString());
        Document found = FreedomCore.plugin.cm.players.find(doc).first();

        if (found == null){
            return false;
        }
        if (found.getBoolean("Is_Super") || found.getBoolean("Is_Telnet") || found.getBoolean("Is_Senior")){
            return true;
        }
        return false;
    }

    public static boolean isSuper(Player player) {

        Document doc = new Document("UUID", player.getUniqueId().toString());
        Document found = FreedomCore.plugin.cm.players.find(doc).first();
        if (found == null){
            return false;
        }

        return found.getBoolean("Is_Super");
    }
    public static boolean isTelnet(Player player) {
        Document doc = new Document("UUID", player.getUniqueId().toString());
        Document found = (Document) FreedomCore.plugin.cm.players.find(doc).first();
        if (found == null){
            return false;
        }
        return found.getBoolean("Is_Telnet");
    }
    public static boolean isSenior(Player player){
        Document doc = new Document("UUID", player.getUniqueId().toString());
        Document found = (Document) FreedomCore.plugin.cm.players.find(doc).first();
        if (found == null) {
            return false;
        }
        return found.getBoolean("Is_Senior");
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

