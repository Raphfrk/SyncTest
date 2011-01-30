package com.raphfrk.bukkit.synctest;

import java.io.File;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

import org.bukkit.Server;
import org.bukkit.event.Event;
import org.bukkit.event.Event.Priority;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginLoader;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class SyncTest extends JavaPlugin {
	
	static final String slash = System.getProperty("file.separator");
	
	final Server server = getServer();
	
	PluginManager pm = server.getPluginManager();

	static Logger log;

	public SyncTest(PluginLoader pluginLoader, Server instance, PluginDescriptionFile desc, File folder, File plugin, ClassLoader cLoader) {
		super(pluginLoader, instance, desc, folder, plugin, cLoader);
				
	}
	
	private SyncTestPlayerListener playerListener = new SyncTestPlayerListener( this );

	public void onEnable() {
		

		String name = "Sync Test";
		
		log = Logger.getLogger("Minecraft");
		log.info(name + "initialized");

		pm.registerEvent(Event.Type.PLAYER_COMMAND, playerListener, Priority.Normal, this);
		pm.registerEvent(Event.Type.PLAYER_CHAT, playerListener, Priority.Normal, this);
	}
	
	public void onDisable() {

	}
	
	
}