package pluginrequest.passivemode;

import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;


public class passivemodecommand implements CommandExecutor,Listener {
	public static ArrayList<String> players = new ArrayList<String>();
	public HashMap<String, Long> cooldown = new HashMap<>();
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (sender instanceof Player) {
			
				Player player = (Player) sender;
				if (cooldown.containsKey(player.getName())) {
					if (!(args.length == 1 && args[0].equalsIgnoreCase("list"))) {
					if (System.currentTimeMillis() < cooldown.get(player.getName())) {
					
						long whenItEnds = cooldown.get(player.getName());
						long now2 = System.currentTimeMillis();
						long difference = whenItEnds - now2;
					
						int differenceInSeconds = (int) (difference / 1000);
						player.sendMessage(ChatColor.RED + "You must wait " + differenceInSeconds + " seconds to do this!");
						return false;
					}
			
				
				
				
			} } 
			int timer = Main.getMainClass().getCoolDown();
			long now = System.currentTimeMillis();
			long addtime = timer * 1000;
			long cooldownend = now + addtime;
			
			
			
				if (sender.hasPermission("passivemode.player") && (!(sender.hasPermission("passivemode.admin")))) {
					if (args.length == 0) {
						if (!(players.contains(player.getName()))) {
							players.add(player.getName());
							player.sendMessage(ChatColor.GREEN + "PassiveMode Enabled!");
							cooldown.put(player.getName(), cooldownend);
						} else if ((players.contains(player.getName()))) {
							players.remove(player.getName());
							player.sendMessage(ChatColor.RED + "PassiveMode Disabled!");
							cooldown.put(player.getName(), cooldownend);
						}
						// For [on:off]
					} else if (args.length == 1) {
						if (args[0].equalsIgnoreCase("on")) {
							if (!(players.contains(player.getName()))) {
							players.add(player.getName());
							player.sendMessage(ChatColor.GREEN + "PassiveMode Enabled!");
							cooldown.put(player.getName(), cooldownend);
							} else {
								player.sendMessage(ChatColor.GREEN + "PassiveMode is already enabled!");
							}
						} else if (args[0].equalsIgnoreCase("off")) {
							if ((players.contains(player.getName()))) {
							players.remove(player.getName());
							player.sendMessage(ChatColor.RED + "PassiveMode Disabled!");
							cooldown.put(player.getName(), cooldownend);
							} else {
								player.sendMessage(ChatColor.RED + "PassiveMode is already disabled!");
							}
						} else if (args[0].equalsIgnoreCase("list")) {
							if (player.hasPermission("passivemode.list")) {
							player.sendMessage("Users with PassiveMode enabled are: " + players.toString());
							} else {
								player.sendMessage("You don't have permission for this!");
							}
						} else {
							player.sendMessage("Invalid command arguments!");
						}
					}  else {
						player.sendMessage("Invalid command arguments");
					}
				} else if (sender.hasPermission("passivemode.admin")) {
					// if it's just the passivemode command
					if (args.length == 0) {
						if (!(players.contains(player.getName()))) {
							players.add(player.getName());
							player.sendMessage(ChatColor.GREEN + "PassiveMode Enabled!");
						} else if ((players.contains(player.getName()))) {
							players.remove(player.getName());
							player.sendMessage(ChatColor.RED + "PassiveMode Disabled!");
						}
						// For [on:off]
					} else if (args.length == 1) {
						if (args[0].equalsIgnoreCase("on")) {
							if (!(players.contains(player.getName()))) {
							players.add(player.getName());
							player.sendMessage(ChatColor.GREEN + "PassiveMode Enabled!");
							} else {
								player.sendMessage(ChatColor.GREEN + "PassiveMode is already enabled!");
							}
						} else if (args[0].equalsIgnoreCase("off")) {
							if ((players.contains(player.getName()))) {
							players.remove(player.getName());
							player.sendMessage(ChatColor.RED + "PassiveMode Disabled!");
							} else {
								player.sendMessage(ChatColor.RED + "PassiveMode is already disabled!");
							}
						} else if (args[0].equalsIgnoreCase("list")) {
							player.sendMessage("Users with PassiveMode enabled are: " + players.toString());
							
						} else {
							player.sendMessage("Invalid command arguments!");
						}
					} else if (args.length == 2) {
							if (args[0].equalsIgnoreCase("on")) {
								Player bplayer = Bukkit.getPlayer(args[1]);
								try {
									if (!(players.contains(bplayer.getName()))) {
										players.add(bplayer.getName());
										player.sendMessage(ChatColor.GREEN + "PassiveMode enabled for " + args[1] + "!");
									} else {
										player.sendMessage(ChatColor.GREEN + "PassiveMode is already enabled for " + args[1] + "!");
									}
								}catch (NullPointerException e){
									player.sendMessage("The player is not online!");
								}
							} else if (args[0].equalsIgnoreCase("off")) {
								Player bplayer = Bukkit.getPlayer(args[1]);
								try {
									if ((players.contains(bplayer.getName()))) {
										players.remove(bplayer.getName());
										player.sendMessage(ChatColor.RED + "PassiveMode disabled for " + args[1] + "!");
									} else {
										player.sendMessage(ChatColor.RED + "PassiveMode is already disabled for " + args[1] + "!");
									}
								} catch (NullPointerException e) {
									player.sendMessage("The player is not online!");
								}
							} else {
								player.sendMessage("Invalid command arguments!");
							}
						
						
					} else {
						player.sendMessage("An error has occured!");
					}
				} else {
					sender.sendMessage("You don't have permission for this!");
				}
		} else {
			sender.sendMessage("This command can only be executed as a player!");
		}
	
		return false;
	}
	
	@EventHandler
	public void onHit(EntityDamageByEntityEvent e) {
		
		if (e.getEntity() instanceof Player) {
			Player player = (Player) e.getEntity();
			String playername = player.getName();
			if (e.getDamager() instanceof Player) {
				Player Damager = (Player) e.getDamager();
				if (players.contains(playername) || players.contains(Damager.getName())) {
					Damager.sendMessage("This player has passivemode enabled!");
					e.setCancelled(true);
				} else {
					e.setCancelled(false);
				}
			
			} 
			
		}
			
		}

}
