package jjj.treecutter.myplugin.listener;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Server;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

import jjj.treecutter.myplugin.Main;

public class CommandListener implements Listener {
	
	static final int MAX = 64;
	
	static final Material[] Logs = {
			Material.ACACIA_LOG, Material.BIRCH_LOG, Material.DARK_OAK_LOG,
			Material.JUNGLE_LOG, Material.OAK_LOG, Material.SPRUCE_LOG
	};
	
	static final Material[] Ores = {
			Material.COAL_ORE, Material.DIAMOND_ORE, Material.EMERALD_ORE,
			Material.GOLD_ORE, Material.IRON_ORE, Material.LAPIS_ORE, 
			Material.REDSTONE_ORE, Material.NETHER_QUARTZ_ORE
	};
	
	public boolean isTree (Block block) {
		for (int i = 0; i < Logs.length; i++) {
			if (block.getType() == Logs[i]) return true;
		}
		return false;
	}
	
	public void ConstructQueue (List<Location> Q, Block block, Block startingBlock) {
		int X = block.getLocation().getBlockX();
		int Y = block.getLocation().getBlockY();
		int Z = block.getLocation().getBlockZ();
		World world = block.getLocation().getWorld();
		Block stem = startingBlock.getRelative(0, -1, 0);
		
		// iterate over every neighbouring block
		for (int xoff = -1; xoff <= 1; xoff++) {
			for (int zoff = -1; zoff <= 1; zoff++) {
				for (int yoff = -1; yoff <= 1; yoff++) {
					Block nextBlock = world.getBlockAt(X+xoff, Y+yoff, Z+zoff);
					
					
					// in case of a tree, make sure the stem stays in place
					if (isTree(nextBlock)) {
						if (nextBlock.equals(stem)) {
							continue;
						}
					}
					
					// add next block to queue, only if it's the same type as the starting block
					// and the queue isn't full
					if (nextBlock.getType() == block.getType() && Q.size() < MAX) {
						if (!Q.contains(nextBlock.getLocation()) && !nextBlock.equals(startingBlock)) {
							Q.add(nextBlock.getLocation());
							ConstructQueue(Q, nextBlock, startingBlock);
						}
					}
				}
			}
		}
	}
	
	public void BreakB (List<Location> Q, int i, Block block) {
		Block b = Q.get(i).getBlock();
		if (b.getType() == block.getType()) {
			b.breakNaturally();
		}
	}
	
	public Plugin BreakBLog (List<Location> Q) {
		for (int i = 0; i < Q.size(); i++) {
			Q.get(i).getBlock().breakNaturally();
		}
		return null;
	}
	
	@EventHandler
	public void BlockDetectEvent (BlockBreakEvent event) {
		Player player = event.getPlayer();
		Block block = event.getBlock();
		
		
		boolean isLog = false, isOre = false;
		for (int i = 0; i < Logs.length; i++) {
			if(block.getType() == Logs[i]) isLog = true;
		}
		for (int i = 0; i < Ores.length; i++) {
			if(block.getType() == Ores[i]) isOre = true;
		}
		
		if (isLog || isOre) {
			if (player.isSneaking()) {
				
				List<Location> Q = new ArrayList<Location>();
				Q.clear();
				
				ConstructQueue(Q, block, block);

//				new BukkitRunnable() {
//				     @Override
//				     public void run() {
//				          //methods
//				          cancel();
//				     }
//				}.runTaskTimer(BreakBLog(Q), 1, 1);
				
				for (int i = 0; i < Q.size(); i++) {
					BreakB (Q, i, block);
				}
				
				player.sendMessage("You broke a log or ore while sneaking, " + Q.size() + " blocks were attached!");
				
				Q.clear();
			}
		}
	}
}