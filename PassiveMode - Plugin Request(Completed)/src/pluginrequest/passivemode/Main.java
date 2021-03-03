package pluginrequest.passivemode;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {
	
	private static Main mainClass;
	
	@Override
	public void onEnable() {
		System.out.println("PassiveMode[Enabled]!");
		getCommand("passivemode").setExecutor(new passivemodecommand());
		getServer().getPluginManager().registerEvents(new passivemodecommand(), this);
		this.getConfig();
		mainClass = this;
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("passivemodereload")) {
			if (sender.hasPermission("passivemode.reload")) {
					sender.sendMessage("PassiveMode has been reloaded!");
					this.reloadConfig();
					this.saveDefaultConfig();
					
				} else {
					sender.sendMessage("You don't have permission for this!");
				}
				
			}
			
		
		return false;
	}
	

	
	@Override
	public void onDisable() {
		System.out.println("PassiveMode[Disabled]!");
	}

	public static int CoolDown;
	
	public int getCoolDown(){
		CoolDown = this.getConfig().getInt("cooldowntimer");
		return CoolDown;
	}
	
	public static Main getMainClass() {
		return mainClass;
	}
	
}

