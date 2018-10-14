package me.noeffort.whitelister;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import me.noeffort.whitelister.command.WhitelistCommand;
import me.noeffort.whitelister.config.MessageConfig;
import me.noeffort.whitelister.config.WhitelistConfig;
import me.noeffort.whitelister.handler.PlayerHandler;
import me.noeffort.whitelister.handler.WhitelistCommandListener;

public class Main extends JavaPlugin {

	private static Main instance;
	
	private WhitelistConfig config;
	private MessageConfig msg;
	private PlayerHandler handler;
	
	public void onEnable() {
		instance = this;
		handler = new PlayerHandler();
		registerConfig();
		registerListener();
		registerCommand();
	}
	
	public void onDisable() {
		config.saveConfig();
	}
	
	private void registerCommand() {
		this.getCommand("whitelist").setExecutor(new WhitelistCommand());
	}
	
	private void registerListener() {
		Bukkit.getPluginManager().registerEvents(new WhitelistCommandListener(), this);
		Bukkit.getPluginManager().registerEvents(new PlayerHandler(), this);
	}
	
	private void registerConfig() {
		config = new WhitelistConfig();
		config.createFile();
		
		msg = new MessageConfig();
		msg.createFile();
	}
	
	public static Main get() {
		return instance;
	}
	
	public WhitelistConfig getWhitelist() {
		return config;
	}
	
	public MessageConfig getMessages() {
		return msg;
	}
	
	public PlayerHandler getHandler() {
		return handler;
	}
	
}
