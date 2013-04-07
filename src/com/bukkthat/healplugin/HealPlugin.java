package com.bukkthat.healplugin;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Main HealPlugin class that handles all of the
 * plugin functionality.
 * 
 * @author gomeow
 */
public class HealPlugin extends JavaPlugin {

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(args.length == 0) {
            //Sender could be a console, need to check to avoid a ClassCastException
            if(sender instanceof Player) {
                //We can now safely cast the CommandSender to a Player
                Player player = (Player) sender;
                //Permissions check, does the player have the heal.self node?
                if(player.hasPermission("heal.self")) {
                    //Set their health back to the maximum
                    player.setHealth(player.getMaxHealth());
                    //Feed them to 20/20
                    player.setFoodLevel(20);
                    //Send them a chat message
                    player.sendMessage(ChatColor.GREEN + "You have been fed and healed!");
                } else {
                    player.sendMessage(ChatColor.RED + "You do not have permission to do that!");
                }
            } else {
                sender.sendMessage(ChatColor.RED + "Only a player has health!");
            }
        } else if(args.length == 1) {
            //Permissions check, does the player have the heal.others node?
            if(sender.hasPermission("heal.others")) {
                //Get the player using the username supplied in the first argument
                Player target = Bukkit.getPlayer(args[0]);
                //Make sure the player actually exists - avoids a NullPointerException
                if(target == null) {
                    sender.sendMessage(ChatColor.RED + "That player is not online!");
                } else {
                    //Set their health back to the maximum
                    target.setHealth(target.getMaxHealth());
                    //Feed them to 20/20
                    target.setFoodLevel(20);
                    sender.sendMessage(ChatColor.GREEN + target.getName() + " was healed and fed!");
                    target.sendMessage(ChatColor.GREEN + "You were healed and fed!");
                }
            } else {
                //Permissions error message
                sender.sendMessage(ChatColor.RED + "You do not have permission to do that!");
            }
        } else {
            //Return false, this will output the usage information from the plugin.yml file
            return false;
        }

        return true;
    }

}
