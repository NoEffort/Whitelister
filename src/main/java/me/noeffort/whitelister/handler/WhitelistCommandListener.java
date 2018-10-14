package me.noeffort.whitelister.handler;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

public class WhitelistCommandListener implements Listener {

	public WhitelistCommandListener() {}
	
	@EventHandler
	public void onCommand(PlayerCommandPreprocessEvent event) {
		
		String message = event.getMessage();
		
		if(message.startsWith("/minecraft:whitelist")) {
			event.setMessage("/whitelister:whitelist");
			return;
		}
		
	}
	
}
