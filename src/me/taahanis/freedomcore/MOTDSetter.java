package me.taahanis.freedomcore;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.ListenerOptions;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.wrappers.WrappedGameProfile;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MOTDSetter {

    public static String motd = FreedomCore.plugin.getConfig().getString("server.motd");
    public static String versionname = FreedomCore.plugin.getConfig().getString("server.versionname");

    public static void setPlayerCountMsg()
    {
        versionname = versionname.replace("$onlineplayers", Integer.toString(Bukkit.getOnlinePlayers().size()));
        versionname = versionname.replace("$maxplayers", Integer.toString(Bukkit.getServer().getMaxPlayers()));

        final List<WrappedGameProfile> names = new ArrayList<WrappedGameProfile>();
        int i = 0;
        for (String thing : FreedomCore.plugin.getConfig().getStringList("server.lines")){
            names.add(new WrappedGameProfile(Integer.toString(i), ChatColor.translateAlternateColorCodes('&', thing)));
            i+= 1;
        }


        //If you want to add more message, copy 'names.add(new WrappedGameProfile("number", "ur message"));'
        //Make sure that 'number' goes in order. Instance:
        //names.add(new WrappedGameProfile("1", ChatColor.LIGHT_PURPLE + "This is message 1!"));
        //names.add(new WrappedGameProfile("2", ChatColor.GREEN + "This is message 2!"));
        //names.add(new WrappedGameProfile("3", ChatColor.GREEN + "This is message 3!"));
        //names.add(new WrappedGameProfile("4", ChatColor.GREEN + "This is message 4!"));
        ProtocolLibrary.getProtocolManager().addPacketListener(new PacketAdapter(FreedomCore.plugin, ListenerPriority.NORMAL,
                Arrays.asList(PacketType.Status.Server.OUT_SERVER_INFO), ListenerOptions.ASYNC) {
            @Override
            public void onPacketSending(PacketEvent event) {
                event.getPacket().getServerPings().read(0).setPlayers(names);
                event.getPacket().getServerPings().read(0).setVersionProtocol(3);
                event.getPacket().getServerPings().read(0).setVersionName(ChatColor.translateAlternateColorCodes('&', versionname));
                event.getPacket().getServerPings().read(0).setMotD(ChatColor.translateAlternateColorCodes('&', motd));
            }
        });
    }
}
