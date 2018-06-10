package net.polishgames.rhenowar.conquest;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import com.sk89q.worldedit.Vector;

public class Commands implements CommandExecutor
{
	
	private final Conquest conquest;
	
	public Commands()
	{
		this.conquest = Conquest.giveConquest();
	}
	
	public final Conquest giveConquest()
	{
		return this.conquest;
	}
	
	protected boolean doCommand(final String command, final Player executor, final int argc, final String... argv)
	{
		switch(command)
		{
			case "conquest":
			{
				if(argc >= 1)
				{
					switch(argv[0].toLowerCase())
					{
						case "set":
						{
							if(this.giveConquest().giveManagers().contains(executor.getName()))
							{
								final Location location = executor.getLocation();
								final String path = "conquest." + executor.getWorld().getName() + ".";
								final FileConfiguration fileRegion = this.giveConquest().giveFileRegion();
								final String nameRegion = "region";
								switch(argc)
								{
									case 2:
									{
										switch(argv[1].toLowerCase())
										{
											case "lobby":
												return this.doSetLocationInConfiguration(path + "lobby.", location, fileRegion, nameRegion);
											case "*":
											{
												boolean result = Bukkit.dispatchCommand(executor, "reload");
												final String pathLobby = path + "lobby.";
												final String pathSpawn = path + "spawn.";
												final String pathSpawnBlue= pathSpawn + "blue.";
												final String pathSpawnRed = pathSpawn + "red.";
												final String pathRegion = path + "region.";
												final String pathRegionArea = pathRegion + "area.";
												final String pathRegionAreaMin = pathRegionArea + "min.";
												final String pathRegionAreaMax = pathRegionArea + "max.";
												final String pathRegionBattle = pathRegion + "battle.";
												final String pathRegionBattleMin = pathRegionBattle + "min.";
												final String pathRegionBattleMax = pathRegionBattle + "max.";
												result &= fileRegion.isDouble(pathLobby + "x");
												result &= fileRegion.isDouble(pathLobby + "y");
												result &= fileRegion.isDouble(pathLobby + "z");
												result &= fileRegion.isDouble(pathLobby + "yaw");
												result &= fileRegion.isDouble(pathLobby + "pitch");
												result &= fileRegion.isDouble(pathSpawnBlue + "x");
												result &= fileRegion.isDouble(pathSpawnBlue + "y");
												result &= fileRegion.isDouble(pathSpawnBlue + "z");
												result &= fileRegion.isDouble(pathSpawnBlue + "yaw");
												result &= fileRegion.isDouble(pathSpawnBlue + "pitch");
												result &= fileRegion.isDouble(pathSpawnRed + "x");
												result &= fileRegion.isDouble(pathSpawnRed + "y");
												result &= fileRegion.isDouble(pathSpawnRed + "z");
												result &= fileRegion.isDouble(pathSpawnRed + "yaw");
												result &= fileRegion.isDouble(pathSpawnRed + "pitch");
												result &= fileRegion.isDouble(pathRegionAreaMin + "x");
												result &= fileRegion.isDouble(pathRegionAreaMin + "y");
												result &= fileRegion.isDouble(pathRegionAreaMin + "z");
												result &= fileRegion.isDouble(pathRegionAreaMax + "x");
												result &= fileRegion.isDouble(pathRegionAreaMax + "y");
												result &= fileRegion.isDouble(pathRegionAreaMax + "z");
												result &= fileRegion.isDouble(pathRegionBattleMin + "x");
												result &= fileRegion.isDouble(pathRegionBattleMin + "y");
												result &= fileRegion.isDouble(pathRegionBattleMin + "z");
												result &= fileRegion.isDouble(pathRegionBattleMax + "x");
												result &= fileRegion.isDouble(pathRegionBattleMax + "y");
												result &= fileRegion.isDouble(pathRegionBattleMax + "z");
												if(result)
												{
													final World world = executor.getWorld();
													final Region regionArea = RegionDetermine.giveRegion(RegionType.AREA, world);
													final Region regionBattle = RegionDetermine.giveRegion(RegionType.BATTLE, world);
													final Vector vectorLobby = RegionDetermine.giveVector(VectorType.LOBBY, world);
													final Vector vectorSpawnRed = RegionDetermine.giveVector(VectorType.SPAWN_RED, world);
													final Vector vectorSpawnBlue = RegionDetermine.giveVector(VectorType.SPAWN_BLUE, world);
													result &= !regionBattle.isContains(vectorSpawnRed);
													result &= regionArea.isContains(vectorSpawnRed);
													result &= !regionBattle.isContains(vectorSpawnBlue);
													result &= regionArea.isContains(vectorSpawnBlue);
													result &= regionArea.isContains(regionBattle.giveMin());
													result &= regionArea.isContains(regionBattle.giveMax());
													result &= !regionArea.isContains(vectorLobby);
												}
												fileRegion.set(path + "already", result);
												return result & this.doSaveConfiguration(fileRegion, nameRegion);
											}
										}
									} break;
									case 3:
									{
										switch(argv[1].toLowerCase())
										{
											case "spawn":
											{
												final String pathSpawn = path + "spawn.";
												switch(argv[2].toLowerCase())
												{
													case "blue":
														return this.doSetLocationInConfiguration(pathSpawn + "blue.", location, fileRegion, nameRegion);
													case "red":
														return this.doSetLocationInConfiguration(pathSpawn + "red.", location, fileRegion, nameRegion);
												}
											} break;
											case "region":
											{
												final String pathRegion = path + "region.";
												switch(argv[2].toLowerCase())
												{
													case "area":
														return this.doSetRegionInConfiguration(pathRegion + "area.", Region.giveRegion(executor), fileRegion, nameRegion);
													case "battle":
														return this.doSetRegionInConfiguration(pathRegion + "battle.", Region.giveRegion(executor), fileRegion, nameRegion);
												}
											} break;
										}
									} break;
								}
							}
							else
							{
								Conquest.doSendSevereToPlayer("Nie masz uprawnieñ wymaganych do wykonania tej komendy.", executor);
								return true;
							}
						} break;
						case "tp":
							if(argc == 1)
								return executor.teleport(this.giveConquest().giveLobby());
							else
								break;
						case "report":
							if(argc >= 2)
							{
								new Thread()
								{
									@Override
									public void run()
									{
										final Conquest conquest = Conquest.giveConquest();
										String text = executor.getName() + new SimpleDateFormat("; YYYY-MM-dd HH.mm.ss:").format(new Date());
										for(int i = 1; i < argc; i++)
											text += " " + argv[i];
										try
										{
											conquest.giveLoaderJNF().doElicitClass("javax.jnf.Files").giveLastObject().getClass().getMethod("saveText", File.class, String[].class).invoke(null, conquest.giveFileTXT(argv[0].toLowerCase()), new String[]{text});
										}
										catch(final ReflectiveOperationException e)
										{
											e.printStackTrace();
										}
									}
								}.start();
								return true;
							}
							else
								break;
					}
				}
			} break;
		}
		return false;
	}
	
	protected boolean doSetLocationInConfiguration(final String path, final Location location, final FileConfiguration file, final String name)
	{
		file.set(path + "x", location.getX());
		file.set(path + "y", location.getY());
		file.set(path + "z", location.getZ());
		file.set(path + "yaw", location.getYaw());
		file.set(path + "pitch", location.getPitch());
		return this.doSaveConfiguration(file, name);
	}
	
	protected boolean doSetRegionInConfiguration(final String path, final Region region, final FileConfiguration file, final String name)
	{
		final String pathMin = path + "min.";
		final String pathMax = path + "max.";
		final Vector vectorMin = region.giveMin();
		final Vector vectorMax = region.giveMax();
		file.set(pathMin + "x", vectorMin.getX());
		file.set(pathMin + "y", vectorMin.getY());
		file.set(pathMin + "z", vectorMin.getZ());
		file.set(pathMax + "x", vectorMax.getX());
		file.set(pathMax + "y", vectorMax.getY());
		file.set(pathMax + "z", vectorMax.getZ());
		return this.doSaveConfiguration(file, name);
	}
	
	protected final boolean doSaveConfiguration(final FileConfiguration file, final String name)
	{
		try
		{
			this.giveConquest().doSaveConfiguration(file, name);
		}
		catch(final IOException e)
		{
			e.printStackTrace();
			return false;
		}
		return true;
	}

	@Override
	public boolean onCommand(final CommandSender sender, final Command command, final String label, final String[] args)
	{
		final Player player = Bukkit.getPlayer(sender.getName());
		final String tag = label.equalsIgnoreCase("conquest") || label.equalsIgnoreCase("cq") ? "conquest" : label;
		if(player != null)
		{
			final boolean result = this.doCommand(tag, player, args.length, args);
			if(tag.equals("conquest"))
			{
				if(result)
					Conquest.doSendPositiveToPlayer("Podana komenda zosta³a wykonana z powodzeniem.", player);
				else
				{
					Conquest.doSendSevereToPlayer("Podana komenda ma z³¹ sk³adnie lub wyst¹pi³ b³¹d. Przyk³ad u¿ycia: " + ChatColor.WHITE + "/" + label + " <" + ChatColor.GREEN + "set <" + ChatColor.GOLD + "lobby" + ChatColor.GREEN + "|" + ChatColor.GOLD + "spawn <" + ChatColor.BLUE + "blue" + ChatColor.GOLD + "|" + ChatColor.RED + "red" + ChatColor.GOLD + ">" + ChatColor.GREEN + "|" + ChatColor.GOLD + "region <" + ChatColor.DARK_PURPLE + "area" + ChatColor.GOLD + "|" + ChatColor.DARK_PURPLE + "battle" + ChatColor.GOLD + ">" + ChatColor.GREEN + "|" + ChatColor.GOLD + "*" + ChatColor.GREEN + ">" + ChatColor.WHITE + "|" + ChatColor.GREEN + "tp" + ChatColor.WHITE + "|" + ChatColor.GREEN + "report <" + ChatColor.GOLD + "TREŒÆ MELDUNKU" + ChatColor.GREEN + ">" + ChatColor.WHITE + ">", player);
					return true;
				}
			}
			return result;
		}
		return false;
	}
	
}
