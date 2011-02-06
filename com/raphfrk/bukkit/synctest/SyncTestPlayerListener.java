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

		if( split[0].equals("/sleeper")) {

			int taskId = p.getServer().getScheduler().scheduleAsyncDelayedTask(p, new Runnable() {

				public void run() {

					try {
						Thread.sleep(500000);
					} catch (InterruptedException e) {
						System.out.println( "Sleeper Interrupted" );
					}

				}

			});

			player.sendMessage("TaskId:" + taskId);
			event.setCancelled(true);

		}

		if( split[0].equals("/kill") && split.length > 1) {

			p.getServer().getScheduler().cancelTask(Integer.parseInt(split[1]));
			event.setCancelled(true);

		}

		if( split[0].equals("/syncdelay")) {

			Long delay = Long.parseLong(split[1]);

			final Player finalPlayer = player;
			final String finalMessage = message;

			int taskId = p.getServer().getScheduler().scheduleSyncDelayedTask(p, new Runnable() {

				public void run() {

					finalPlayer.sendMessage(finalMessage);

				}

			}, delay);

			player.sendMessage("Added to sync queue: " + delay + " id=" + taskId);
			event.setCancelled(true);

		}

		if( split[0].equals("/asyncdelay")) {

			Long delay = Long.parseLong(split[1]);

			final Player finalPlayer = player;
			final String finalMessage = message;

			int taskId = p.getServer().getScheduler().scheduleAsyncDelayedTask(p, new Runnable() {

				public void run() {

					p.getServer().getScheduler().scheduleSyncDelayedTask(p, new Runnable() {
						public void run() {
							finalPlayer.sendMessage(finalMessage);
						}
					});

				}

			}, delay);

			player.sendMessage("Added to async queue: " + delay + " id=" + taskId);
			event.setCancelled(true);

		}

		if( split[0].equals("/syncrepeat")) {

			Long delay = Long.parseLong(split[1]);

			final Player finalPlayer = player;
			final String finalMessage = message;

			int taskId = p.getServer().getScheduler().scheduleSyncRepeatingTask(p, new Runnable() {

				public void run() {

					finalPlayer.sendMessage(finalMessage);

				}

			}, delay, 40L);

			player.sendMessage("Added to sync queue: " + delay + " id=" + taskId);
			event.setCancelled(true);

		}

		if( split[0].equals("/asyncrepeat")) {

			Long delay = Long.parseLong(split[1]);

			final Player finalPlayer = player;
			final String finalMessage = message;

			int taskId = p.getServer().getScheduler().scheduleAsyncRepeatingTask(p, new Runnable() {

				public void run() {

					p.getServer().getScheduler().scheduleSyncDelayedTask(p, new Runnable() {
						public void run() {
							finalPlayer.sendMessage(finalMessage);
						}
					});

				}

			}, delay, 40L);

			player.sendMessage("Added to async queue: " + delay + " id=" + taskId);
			event.setCancelled(true);

		}

		if( split[0].equals("/cancelthis")) {
			p.getServer().getScheduler().cancelTasks(p);
			event.setCancelled(true);
		}

		if( split[0].equals("/cancelall")) {
			p.getServer().getScheduler().cancelAllTasks();
			event.setCancelled(true);
		}

		if( split[0].equals("/disable")) {
			p.getServer().getPluginManager().disablePlugin(p);
			event.setCancelled(true);
		}

		if( split[0].equals("/reaptest")) {

			Long delay = Long.parseLong(split[1]);

			final Player finalPlayer = player;
			final String finalMessage = message;

			for( int cnt=0;cnt<50;cnt++) {

				int taskId = p.getServer().getScheduler().scheduleAsyncDelayedTask(p, new Runnable() {

					public void run() {
						
						try {
							Thread.sleep(1000);
						} catch (InterruptedException e) {
							System.out.println( "Sleeper Interrupted" );
						}

						p.getServer().getScheduler().scheduleSyncDelayedTask(p, new Runnable() {
							public void run() {
								finalPlayer.sendMessage(finalMessage);
							}
						});

					}

				}, delay);

				player.sendMessage("Id: " + taskId);
				
			}

		}



	}

	public void onPlayerChat(PlayerChatEvent event) {

		event.getPlayer().sendMessage("Message received: " + event.getMessage());

	}
}
