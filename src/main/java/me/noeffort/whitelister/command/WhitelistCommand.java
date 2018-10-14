package me.noeffort.whitelister.command;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.scheduler.BukkitScheduler;

import me.noeffort.whitelister.Main;
import me.noeffort.whitelister.config.MessageConfig;
import me.noeffort.whitelister.config.WhitelistConfig;
import me.noeffort.whitelister.handler.PlayerHandler;

public class WhitelistCommand implements CommandExecutor {

	Main plugin = Main.get();
	WhitelistConfig whitelist;
	MessageConfig msg;
	PlayerHandler handler;
	
	private boolean isRunning = false;
	
	public WhitelistCommand() {}
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		
		whitelist = plugin.getWhitelist();
		msg = plugin.getMessages();
		
		if(command.getName().equalsIgnoreCase("whitelist")) {		
			
			if(args.length >= 1) {
				if(args[0].equals("on")) {
					whitelist.getConfig().set("whitelistEnabled", true);
					whitelist.saveConfig();
					whitelist.reloadConfig();
					sender.sendMessage(msg.appendPrefix(msg.getEnabled()));
					parseArgs(sender, args);
				}
				else if(args[0].equals("off")) {
					if(isRunning()) {
						Bukkit.getScheduler().cancelTask(getTaskID());
						setRunning(false);
					}
					whitelist.getConfig().set("whitelistEnabled", false);
					whitelist.saveConfig();
					whitelist.reloadConfig();
					sender.sendMessage(msg.appendPrefix(msg.getDisabled()));
					parseArgs(sender, args);
				}
				else if(args[0].equals("add")) {
					if(whitelist.getConfig().getStringList("players").isEmpty() || 
							whitelist.getConfig().getStringList("players").size() == 0) {
						List<String> list = whitelist.getConfig().getStringList("players");
						list.add(args[1]);
						whitelist.getConfig().set("players", list);
						sender.sendMessage(msg.appendPrefix(msg.getAdd()));
						whitelist.saveConfig();
						whitelist.reloadConfig();
						return true;
					}
					for(String str : whitelist.getConfig().getStringList("players")) {
						if(str.equals(args[1])) {
							sender.sendMessage(msg.appendPrefix(msg.getDuplicate()));
							return true;
						}
					}
					List<String> list = whitelist.getConfig().getStringList("players");
					list.add(args[1]);
					whitelist.getConfig().set("players", list);
					whitelist.saveConfig();
					whitelist.reloadConfig();
					sender.sendMessage(msg.appendPrefix(msg.getAdd()));
					return true;
				}
				else if(args[0].equals("remove")) {
					if(whitelist.getConfig().getStringList("players").isEmpty() || 
							whitelist.getConfig().getStringList("players").size() == 0) {
						sender.sendMessage(msg.appendPrefix(msg.getEmpty()));
						return true;
					}
					for(String str : whitelist.getConfig().getStringList("players")) {
						if(str.equals(args[1])) {
							List<String> list = whitelist.getConfig().getStringList("players");
							list.remove(args[1]);
							whitelist.getConfig().set("players", list);
							sender.sendMessage(msg.appendPrefix(msg.getRemove()));
							whitelist.saveConfig();
							whitelist.reloadConfig();
							return true;
						}
					}
					sender.sendMessage(msg.appendPrefix(msg.getMissingUser()));
					return true;
				}
				else if(args[0].equals("reload")) {
					sender.sendMessage(msg.appendPrefix(msg.getReload()));
					try {
						whitelist.reloadConfig();
						msg.reloadConfig();
						sender.sendMessage(msg.appendPrefix(msg.getReloadSuccess()));
						return true;
					} catch (Exception e) {
						sender.sendMessage(msg.appendPrefix(msg.getReloadFail()));
					}
					return true;
				}
				else if(args[0].equals("?") || args[0].equals("help")) {
					sender.sendMessage(msg.appendPrefix(msg.getHelp()));
					return true;
				} else {
					sender.sendMessage(msg.appendPrefix(msg.getMissingCommand()));
					return true;
				}
			} else {
				sender.sendMessage(msg.appendPrefix("&7Please specify on or off."));
				return true;
			}
		}
		return true;
	}
	
	private int taskID = 0;
	private static int value = 0;
	
	private void parseArgs(CommandSender sender, String[] args) {
		
		whitelist = plugin.getWhitelist();
		handler = plugin.getHandler();
		
		for(String i : args) {
			if(i.startsWith("t:")) {
				String val = i.replace("t:", "");
				
				if(val != null && val.length() > 0) {
					
					Character x = val.charAt(val.length() - 1);
					BukkitScheduler scheduler = Bukkit.getServer().getScheduler();
					
					switch(x.toString()) {
					case "s":
						int seconds = Integer.parseInt(val.substring(0, val.length() - 1));
						value = seconds;
						taskID = scheduler.scheduleSyncRepeatingTask(plugin, new Runnable() {
							
							@Override
							public void run() {
								
								setRunning(true);
								
								if(value > 0) {
									value--;
								} else {
									whitelist.getConfig().set("whitelistEnabled", false);
									whitelist.saveConfig();
									whitelist.reloadConfig();
									setRunning(false);
									sender.sendMessage(msg.appendPrefix(msg.getDisabled()));
									Bukkit.getScheduler().cancelTask(taskID);
								}
							}
							
						}, 0L, 20L);
						break;
					case "m":
						int minutes = Integer.parseInt(val.substring(0, val.length() - 1));
						value = (int) TimeUnit.MINUTES.toSeconds(minutes);
						taskID = scheduler.scheduleSyncRepeatingTask(plugin, new Runnable() {
							
							@Override
							public void run() {
								
								setRunning(true);
								
								if(value > 0) {
									value--;
								} else {
									whitelist.getConfig().set("whitelistEnabled", false);
									whitelist.saveConfig();
									whitelist.reloadConfig();
									setRunning(false);
									sender.sendMessage(msg.appendPrefix(msg.getDisabled()));
									Bukkit.getScheduler().cancelTask(taskID);
								}
							}
							
						}, 0L, 20L);
						break;
					case "h":
						int hours = Integer.parseInt(val.substring(0, val.length() - 1));
						value = (int) TimeUnit.HOURS.toSeconds(hours);
						taskID = scheduler.scheduleSyncRepeatingTask(plugin, new Runnable() {
							
							@Override
							public void run() {
								
								setRunning(true);
								
								if(value > 0) {
									value--;
								} else {
									whitelist.getConfig().set("whitelistEnabled", false);
									whitelist.saveConfig();
									whitelist.reloadConfig();
									setRunning(false);
									sender.sendMessage(msg.appendPrefix(msg.getDisabled()));
									Bukkit.getScheduler().cancelTask(taskID);
								}
							}
							
						}, 0L, 20L);
						break;
					case "d":
						int days = Integer.parseInt(val.substring(0, val.length() - 1));
						value = (int) TimeUnit.DAYS.toSeconds(days);
						taskID = scheduler.scheduleSyncRepeatingTask(plugin, new Runnable() {
							
							@Override
							public void run() {
								
								setRunning(true);
								
								if(value > 0) {
									value--;
								} else {
									whitelist.getConfig().set("whitelistEnabled", false);
									whitelist.saveConfig();
									whitelist.reloadConfig();
									setRunning(false);
									sender.sendMessage(msg.appendPrefix(msg.getDisabled()));
									Bukkit.getScheduler().cancelTask(taskID);
								}
							}
							
						}, 0L, 20L);
						break;
					default:
						sender.sendMessage(msg.appendPrefix(msg.getInvalidTime()));
						Bukkit.dispatchCommand(sender, "whitelist off");
						break;
					}
				} else {
					sender.sendMessage(msg.appendPrefix(msg.getInvalidTime()));
					Bukkit.dispatchCommand(sender, "whitelist off");
					break;
				}
			}
			if(i.startsWith("m:")) {
				String val = i.replace("m:", "");
				if(val.equals("") || val.isEmpty()) {
					sender.sendMessage(msg.appendPrefix(msg.getMissingMessage()));
					handler.setMessage(whitelist.getConfig().getString("messages.default"));
					break;
				}
				for(String str : whitelist.getConfig().getConfigurationSection("messages").getKeys(false)) {
					if(str.equals(val)) {
						handler.setMessage(whitelist.getConfig().getString("messages." + str));
						break;
					}
					if(!whitelist.getConfig().getConfigurationSection("messages").contains(val)) {
						sender.sendMessage(msg.appendPrefix(msg.getInvalidMessage()));
						handler.setMessage(whitelist.getConfig().getString("messages.default"));
						break;
					}
				}
			}
			
			StringBuilder sb = new StringBuilder();
			for(int z = 0; z < args.length; z++) {
				sb.append(args[z]);
				sb.append(" ");
			}
			String full = sb.toString();
			
			if(!full.contains("off")) {
				if(!full.contains("m:")) {
					handler.setMessage(whitelist.getConfig().getString("messages.default"));
				}
				if(!full.contains("t:")) {
					setRunning(true);
					whitelist.getConfig().set("whitelistEnabled", true);
					whitelist.saveConfig();
					whitelist.reloadConfig();
				}
			}
		}
	}
	
	public void stopTask(int task) {
		Bukkit.getScheduler().cancelTask(task);
	}
	
	public int getTaskID() {
		return taskID;
	}
	
	public boolean isRunning() {
		return isRunning;
	}
	
	public void setRunning(boolean running) {
		this.isRunning = running;
	}
	
}
