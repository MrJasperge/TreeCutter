package jjj.treecutter.myplugin;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import jjj.treecutter.myplugin.command.CutTree;
import jjj.treecutter.myplugin.listener.CommandListener;

public class Main extends JavaPlugin {
	
	private static Main plugin;
	
	PluginDescriptionFile pdfFile;
	
	@Override
	public void onDisable() {
		
	}
	
	public static Main getPlugin() {
		return plugin;
	}
	
	@Override
	public void onEnable() {
		plugin = getPlugin(Main.class);
		PluginManager pm = getServer().getPluginManager();
		
//		this.getCommand("mycommand").setExecutor(new CutTree());
		
		pm.registerEvents(new CommandListener(), this);
		
		this.getLogger().info("Yay, " + this.pdfFile.getName() + " is now enabled!");
	}
	
	@EventHandler
	public void DetectBlockBreaking (BlockBreakEvent event) {
		this.getLogger().info("A block has been broken");
	}
}
