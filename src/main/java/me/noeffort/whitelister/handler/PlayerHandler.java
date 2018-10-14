package me.noeffort.whitelister.handler;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import me.noeffort.whitelister.Main;
import me.noeffort.whitelister.config.WhitelistConfig;

public class PlayerHandler implements Listener {

	Main plugin = Main.get();
	WhitelistConfig config;
	
	private static String message = "";
	
	public PlayerHandler() {}
	
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event) {
		
		config = plugin.getWhitelist();
		Player player = event.getPlayer();
		
		if(config.isEnabled()) {
			if(config.getConfig().getStringList("players").isEmpty() || 
					config.getConfig().getStringList("players").size() == 0) {
				if(getMessage() == null || getMessage().equals("")) {
					setMessage(config.getConfig().getString("messages.default"));
				}
				player.kickPlayer(ChatColor.translateAlternateColorCodes('&', getMessage()));
				return;
			} else {
				for(String str : config.getConfig().getStringList("players")) {
					if(!player.getName().equals(str)) {
						if(getMessage() == null || getMessage().equals("")) {
							setMessage(config.getConfig().getString("messages.default"));
						}
						player.kickPlayer(ChatColor.translateAlternateColorCodes('&', getMessage()));
						return;
					}
				}
			}
		}
	}
	
	public void setMessage(String message) {
		PlayerHandler.message = message;
	}
	
	public String getMessage() {
		return message;
	}
	
}
