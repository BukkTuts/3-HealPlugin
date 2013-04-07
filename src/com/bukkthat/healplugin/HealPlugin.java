package com.bukkthat.healplugin;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class HealPlugin extends JavaPlugin {

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(args.length == 0) {
            if(sender instanceof Player) { //Sender could be a console, need to check to avoid a ClassCastException
                Player player = (Player) sender; //We can now safely cast the CommandSender to a Player
                if(player.hasPermission("heal.self")) { //Permissions check, does the player have the heal.self node?
                    player.setHealth(player.getMaxHealth()); //Set their health back to the maximum
                    player.setFoodLevel(20); //Feed them to 20/20
                    player.sendMessage(ChatColor.GREEN + "You have been fed and healed!"); //Send them a chat message
                } else {
                    player.sendMessage(ChatColor.RED + "You do not have permission to do that!");
                }
            } else {
                sender.sendMessage(ChatColor.RED + "Only a player has health!");
            }
        } else if(args.length == 1) {
            if(sender.hasPermission("heal.others")) {//Permissions check, does the player have the heal.others node?
                Player target = Bukkit.getPlayer(args[0]);
                if(target == null) { //Make sure the player actually exists - avoids a NullPointerException
                    sender.sendMessage(ChatColor.RED + "That player is not online!");
                } else {
                    target.setHealth(target.getMaxHealth()); //Set their health back to the maximum
                    target.setFoodLevel(20); //Feed them to 20/20
                    sender.sendMessage(ChatColor.GREEN + target.getName() + " was healed and fed!");
                    target.sendMessage(ChatColor.GREEN + "You were healed and fed!");
                }
            } else {
                sender.sendMessage(ChatColor.RED + "You do not have permission to do that!"); //Permissions error message
            }
        } else {
            return false; //Return false, this will output the usage information from the plugin.yml file
        }

        return true;
    }

}
