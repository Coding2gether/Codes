package me.coding2gether.chestlocker.main;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import me.coding2gether.chestlocker.listener.onBlockPlace;

public class Main extends JavaPlugin{
	
	@Override
	public void onEnable() {
		Bukkit.getConsoleSender().sendMessage("Das Plugin startet...");
		Bukkit.getPluginManager().registerEvents(new onBlockPlace(), this);
		
		Bukkit.getConsoleSender().sendMessage("Das Plugin ist gestartet!");
		
	}

}
