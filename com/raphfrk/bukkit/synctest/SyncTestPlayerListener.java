package com.raphfrk.bukkit.synctest;

import org.bukkit.entity.Player;
import org.bukkit.event.Event.Type;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.event.player.PlayerListener;

class SyncTestPlayerListener extends PlayerListener {

	SyncTest p;

	public SyncTestPlayerListener ( SyncTest plugin ) {
		p = plugin;
	};
	
    public void onPlayerCommand(PlayerChatEvent event) {
    	
    	String message = event.getMessage();
    	
    	Player player = event.getPlayer();
    	
    	String[] split = message.split(" ");
    	
    	event.getPlayer().sendMessage("split 0: " + split[0] );
    	
    	if( split[0].equals("/say")) {
    		
    		PlayerChatEvent playerChatEvent = new PlayerChatEvent(Type.PLAYER_CHAT,player,message);
    		
    		Long delay = Long.parseLong(split[1]);
    		
    		event.getPlayer().sendMessage("Adding to async queue: " + delay);
    		p.getServer().getAsyncEventManager().callAsyncEvent(playerChatEvent, delay);
    		
    	}
    	
    	event.setCancelled(true);
    	
    }
    
    public void onPlayerChat(PlayerChatEvent event) {
    	
    	event.getPlayer().sendMessage("Message received: " + event.getMessage());
    	
    }
}
