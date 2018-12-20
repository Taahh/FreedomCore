package me.taahanis.freedomcore.command;

import me.taahanis.freedomcore.FreedomCore;
import me.taahanis.freedomcore.ranking.Rank;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Command_saconfig implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
        if (args.length == 0){
            return false;
        }

        if (sender instanceof Player){
            sender.sendMessage(ChatColor.RED + "Only CONSOLE users may perform this command.");
            return true;
        }
        if (args[0].equalsIgnoreCase("add")){

            Player player = Bukkit.getServer().getPlayer(args[1]);
            if (!player.isOnline()){
                sender.sendMessage(ChatColor.RED + "Player not found.");
                return true;
            }
            if (Rank.isAdmin(player)){
                sender.sendMessage(ChatColor.RED + "Player is already admin.");
                return true;
            }
            FreedomCore.plugin.al.addAdmin(player);
            Bukkit.broadcastMessage(ChatColor.RED + sender.getName() + " - Adding " + player.getName() + " to the superadmin list");
            return true;
        }
        //
        if (args[0].equalsIgnoreCase("setrank")){

            //command args0 args1 args2
            Player player = Bukkit.getServer().getPlayer(args[1]);
            if (!player.isOnline()){
                sender.sendMessage(ChatColor.RED + "Player not found.");
                return true;
            }
            if (!Rank.isAdmin(player)){
                sender.sendMessage(ChatColor.RED + "Player has no rank, please add them.");
                return true;
            }
            //
            Rank rank = Rank.findRank(args[2]);

            if (!rank.isAtLeast(Rank.SUPER_ADMIN)){
                sender.sendMessage(ChatColor.RED + "Rank must be super_admin or higher.");
                return true;
            }
            if (Rank.getRank(player) == rank){
                sender.sendMessage(ChatColor.RED + "Player is already that rank.");
                return true;
            }
            FreedomCore.plugin.al.setRank(rank, player);
            Bukkit.broadcastMessage(ChatColor.RED + sender.getName() + " - Setting " + player.getName() + "'s rank to: " + rank);
            return true;
        }
        //
        if (args[0].equalsIgnoreCase("remove")){

            Player player = Bukkit.getServer().getPlayer(args[1]);
            if (!player.isOnline()){
                sender.sendMessage(ChatColor.RED + "Player not found.");
                return true;
            }
            FreedomCore.plugin.al.removeAdmin(player, sender);
            Bukkit.broadcastMessage(ChatColor.RED + sender.getName() + " - Removing " + player.getName() + " from the superadmin list");
            return true;
        }
        return true;
    }
}
