package com.bukkthat.healplugin;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class HealPlugin extends JavaPlugin {

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(cmd.getName().equals("heal")) {
			if(args.length == 0) {
				if(sender instanceof Player) {
					Player player = (Player) sender;
					if(player.hasPermission("heal.self")) {
						player.setHealth(player.getMaxHealth());
						player.setFoodLevel(20);
						player.sendMessage(ChatColor.GREEN + "You have been fed and healed!");
						return true;
					}
					else {
						player.sendMessage(ChatColor.RED + "You do not have permission to do that!");
						return true;
					}
				}
				else {
					sender.sendMessage(ChatColor.RED + "Only a player has health!");
					return true;
				}
			}
			else if(args.length == 1) {
				if(sender.hasPermission("heal.others")) {
					Player target = Bukkit.getPlayer(args[0]);
					if(target == null) {
						sender.sendMessage(ChatColor.RED + "That player is not online!");
						return true;
					}
					target.setHealth(target.getMaxHealth());
					target.setFoodLevel(20);
					sender.sendMessage(ChatColor.GREEN + target.getName() + " was healed and fed!");
					target.sendMessage(ChatColor.GREEN + "You were healed and fed!");
					return true;
				}
				else {
					sender.sendMessage(ChatColor.RED + "You do not have permission to do that!");
					return true;
				}
			}
			else {
				sender.sendMessage(ChatColor.RED + "Too many arguments!");
				sender.sendMessage(ChatColor.RED + "Usage: /heal - Heals yourself");
				sender.sendMessage(ChatColor.RED + "Usage: /heal <player> - Heals another player");
				return true;
			}
		}

		return true;
	}

}
