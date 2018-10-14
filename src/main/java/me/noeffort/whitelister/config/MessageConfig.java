package me.noeffort.whitelister.config;

import java.io.File;
import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import me.noeffort.whitelister.Main;

public class MessageConfig {

	Main plugin = Main.get();
	
	ConsoleCommandSender sender = Bukkit.getServer().getConsoleSender();
	
	private File file = null;
	private FileConfiguration config = null;
	
	public FileConfiguration getConfig() {
		if(config == null) {
			reloadConfig();
		}
		return config;
	}
	
	public File getFile() {
		return file;
	}
	
	public boolean createFile() {
		if(file == null) {
			if(!plugin.getDataFolder().exists()) {
				plugin.getDataFolder().mkdir();
				sender.sendMessage(ChatColor.GREEN + "Plugin folder created!");
			}
			
			file = new File(plugin.getDataFolder(), "messages.yml");
			
			if(!file.exists()) {
				try {
					file.createNewFile();
					sender.sendMessage(ChatColor.GREEN + "Messages file created!");
				} catch (IOException e) {
					e.printStackTrace();
					sender.sendMessage(ChatColor.RED + "Error creating Messages file!");
				}
			}
		}
		
		config = YamlConfiguration.loadConfiguration(file);
		
		loadDefaultConfiguration();
		return true;
	}
	
	private void loadDefaultConfiguration() {
		config.addDefault("messages.prefix", "&8[&fWhitelister&8]&7 ");
		config.addDefault("messages.reload.general", "&7Reloading files...");
		config.addDefault("messages.reload.success", "&aConfig files reloaded!");
		config.addDefault("messages.reload.failure", "&cError reloading config files!");
		config.addDefault("messages.help", "&fUsage: /whitelist [on:off] <time> <message>");
		config.addDefault("messages.missing.time", "&cNo time specified, whitelist is permanent.");
		config.addDefault("messages.missing.permission", "&cYou do not have the proper permissions.");
		config.addDefault("messages.missing.command", "&cThe command you entered is not valid!");
		config.addDefault("messages.missing.message", "&7No message specified, using default!");
		config.addDefault("messages.missing.usage", "&cInvalid usage, please try /whitelist ?");
		config.addDefault("messages.invalid.time", "&cTime specified is invalid, disabling whitelist!");
		config.addDefault("messages.invalid.message", "&cMessage specified cannot be found, using default!");
		config.addDefault("messages.values.over", "&cYou have entered too many arguments.");
		config.addDefault("messages.add", "&aUser added to whitelist!");
		config.addDefault("messages.remove", "&aUser removed from whitelist!");
		config.addDefault("messages.missing.user", "&cUser specified is not in the whitelist.");
		config.addDefault("messages.duplicate", "&7User specified is already in the whitelist.");
		config.addDefault("messages.empty", "&7Whitelist is empty.");
		config.addDefault("messages.enabled", "&7Whitelist is enabled.");
		config.addDefault("messages.disabled", "&7Whitelist is disabled.");
		config.addDefault("messages.error", "&cAn error has occured, please contact plugin developer(s)!");
		
		config.options().copyDefaults(true);
		
		try {
			config.save(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void reloadConfig() {
		config = YamlConfiguration.loadConfiguration(file);
	}
	
	public void saveConfig() {
		if(config == null || file == null) {
			return;
		}
		
		try {
			config.save(file);
		} catch(IOException ignored) {}
	}
	
	public void saveDefaultConfig() {
		if(file == null) {
			file = new File(plugin.getDataFolder(), "messages.yml");
			plugin.saveResource("messages.yml", false);
		}
		
		if(file.exists()) {
			plugin.saveResource("messages.yml", false);
		}
	}
	
	public String getPrefix() {
		return getConfig().getString("messages.prefix");
	}
	public String getReload() {
		return getConfig().getString("messages.reload.general");
	}
	public String getReloadSuccess() {
		return getConfig().getString("messages.reload.success");
	}
	public String getReloadFail() {
		return getConfig().getString("messages.reload.failure");
	}
	public String getHelp() {
		return getConfig().getString("messages.help");
	}
	public String getMissingTime() {
		return getConfig().getString("messages.missing.time");
	}
	public String getMissingPermission() {
		return getConfig().getString("messages.missing.permission");
	}
	public String getMissingCommand() {
		return getConfig().getString("messages.missing.command");
	}
	public String getMissingMessage() {
		return getConfig().getString("messages.missing.message");
	}
	public String getMissingUsage() {
		return getConfig().getString("messages.missing.usage");
	}
	public String getMissingUser() {
		return getConfig().getString("messages.missing.user");
	}
	public String getInvalidTime() {
		return getConfig().getString("messages.invalid.time");
	}
	public String getInvalidMessage() {
		return getConfig().getString("messages.invalid.message");
	}
	public String getTooManyArgs() {
		return getConfig().getString("messages.values.over");
	}
	public String getAdd() {
		return getConfig().getString("messages.add");
	}
	public String getRemove() {
		return getConfig().getString("messages.remove");
	}
	public String getEmpty() {
		return getConfig().getString("messages.empty");
	}
	public String getError() {
		return getConfig().getString("messages.error");
	}
	public String getDuplicate() {
		return getConfig().getString("messages.duplicate");
	}
	public String getEnabled() {
		return getConfig().getString("messages.enabled");
	}
	public String getDisabled() {
		return getConfig().getString("messages.disabled");
	}
	
	public String appendPrefix(String string) {
		String returnable = ChatColor.translateAlternateColorCodes('&', getPrefix() + string);
		return returnable;
	}
	
}
