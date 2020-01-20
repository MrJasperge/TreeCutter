package jjj.treecutter.myplugin.command;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import jjj.treecutter.myplugin.listener.CommandListener;

import java.lang.Object;

public class CutTree extends CommandListener {
	
	
//	@Override
//	public boolean onCommand(CommandSender sender, Command cmd, String cmdLabel, String[] args) {
//		sender.sendMessage("You ran /mycommand!");
//		sender.setOp(true);
//		return true;
//	}
	
	
	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent event) {
	    Player player = event.getPlayer();
	    if(event.getAction() == Action.LEFT_CLICK_BLOCK) {
	    	player.sendMessage("Helo");
	    	
	    }
	}
}