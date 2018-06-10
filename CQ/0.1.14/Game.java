package net.polishgames.rhenowar.conquest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import com.sk89q.worldedit.Vector;

public class Game
{
	
	public void doPrepare()
	{
		this.conquest = Conquest.giveConquest();
		this.scores = new TeamScores();
		final World world = this.giveConquest().giveLobby().getWorld();
		final Location spawnBlue = RegionDetermine.giveLocation(VectorType.SPAWN_BLUE, world);
		final Location spawnRed = RegionDetermine.giveLocation(VectorType.SPAWN_RED, world);
		final Vector vectorBlue = RegionDetermine.giveVector(spawnBlue);
		final Vector vectorRed = RegionDetermine.giveVector(spawnRed);
		if(spawnBlue.getBlockX() == spawnRed.getBlockX())
			this.dimension = DimensionPosition.Z;
		else if(spawnBlue.getBlockZ() == spawnRed.getBlockZ())
			this.dimension = DimensionPosition.X;
		this.area = RegionDetermine.giveRegion(RegionType.AREA, world);
		this.battle = RegionDetermine.giveRegion(RegionType.BATTLE, world);
		this.battleDualRegion = this.giveBattle().giveCutHalf(this.giveDimension());
		if(vectorBlue.distance(this.giveBattleDualRegion().giveFirst().giveMin()) < vectorRed.distance(this.giveBattleDualRegion().giveFirst().giveMin()) && vectorBlue.distance(this.giveBattleDualRegion().giveFirst().giveMax()) < vectorRed.distance(this.giveBattleDualRegion().giveFirst().giveMax()))
		{
			this.battleBlue = this.giveBattleDualRegion().giveFirst();
			this.battleRed = this.giveBattleDualRegion().giveSecond();
		}
		else
		{
			this.battleBlue = this.giveBattleDualRegion().giveSecond();
			this.battleRed = this.giveBattleDualRegion().giveFirst();
		}
		this.giveScores().setBlue((int) Math.round(this.giveBattleBlue().giveSize(this.giveDimension())));
		this.giveScores().setRed((int) Math.round(this.giveBattleRed().giveSize(this.giveDimension())));
		this.interrupt = false;
		this.doResume();
		this.teams = new HashMap<PlayerControl, Team>();
		final int pause = this.giveConquest().giveFileConfig().getInt("conquest.LoopTime");
		new Thread(new Runnable()
		{
			private int time;
			private volatile Thread thread;

			@Override
			public void run()
			{
				this.time += pause;
				Bukkit.getScheduler().scheduleSyncDelayedTask(giveConquest(), new Runnable()
				{
					@Override
					public void run()
					{
						thread = new Thread(new Runnable()
						{
							@Override
							public final void run()
							{
								if(isAlive())
									inLoop(Integer.valueOf(time));
							}
						});
					}
				}, pause);
				new Thread(new Runnable()
				{
					@Override
					public final void run()
					{
						try
						{
							while(thread == null)
								Thread.sleep(1);
							thread.start();
							while(thread.isAlive())
								Thread.sleep(1);
							thread.interrupt();
							thread = null;
							if(!isInterrupted())
								doAgain();
						}
						catch(final InterruptedException e)
						{
							e.printStackTrace();
						}
					}
				}).start();
			}
			
			private final void doAgain()
			{
				this.run();
			}
		}).start();
	}
	
	private Conquest conquest;
	private TeamScores scores;
	private DimensionPosition dimension;
	private Region area, battle, battleBlue, battleRed;
	private DualRegion battleDualRegion;
	private boolean alive;
	
	protected Map<PlayerControl, Team> teams;
	protected volatile boolean interrupt;
	
	public final Conquest giveConquest()
	{
		return this.conquest;
	}
	
	public final TeamScores giveScores()
	{
		return this.scores;
	}
	
	public final DimensionPosition giveDimension()
	{
		return this.dimension;
	}
	
	public final Region giveRegionArea()
	{
		return this.area;
	}
	
	public final Region giveBattle()
	{
		return this.battle;
	}
	
	public final DualRegion giveBattleDualRegion()
	{
		return this.battleDualRegion;
	}
	
	public final Region giveBattleBlue()
	{
		return this.battleBlue;
	}
	
	public final Region giveBattleRed()
	{
		return this.battleRed;
	}
	
	public final Map<PlayerControl, Team> giveTeams()
	{
		return this.teams;
	}
	
	public final boolean isAlive()
	{
		return !this.isInterrupted() && this.alive;
	}
	
	public final boolean isInterrupted()
	{
		return this.interrupt;
	}
	
	public final void doResume()
	{
		if(!this.isAlive())
			this.alive = true;
	}
	
	public final void doSuspend()
	{
		if(this.isAlive())
			this.alive = false;
	}
	
	public final void doInterrupt()
	{
		this.interrupt = true;
	}
	
	public void doReload()
	{
		if(this.isAlive())
		{
			this.doSuspend();
			this.doInterrupt();
			this.conquest = null;
			this.scores = null;
			this.dimension = null;
			this.area = null;
			this.battle = null;
			this.battleBlue = null;
			this.battleRed = null;
			this.battleDualRegion = null;
			this.teams = null;
			Bukkit.getScheduler().scheduleSyncDelayedTask(Conquest.giveConquest(), new Runnable()
			{
				@Override
				public void run()
				{
					doPrepare();
				}
			}, 5);
		}
	}
	
	@SuppressWarnings("deprecation")
	public List<Player> givePlayers()
	{
		final List<Player> list = new ArrayList<Player>();
		for(final Player player : Bukkit.getOnlinePlayers())
			if(((Listeners) this.giveConquest().giveListener()).isPlayerProperWorld(player) && this.giveConquest().isRegionConfigurationAlready(player.getWorld()))
				list.add(player);
		return list;
		
	}
	
	public List<PlayerControl> givePlayersControl()
	{
		final List<PlayerControl> list = new ArrayList<PlayerControl>();
		for(final Player player : this.givePlayers())
			list.add(PlayerControl.givePlayerControl(player));
		return list;
	}
	
	public boolean doPlayerTeleportToTeamSpawn(final PlayerControl player)
	{
		player.doAdjustInventoryBasedTime(player.givePlayer().getInventory());
		return player.givePlayer().teleport(RegionDetermine.giveLocation(this.giveTeams().get(player).giveVectorType(), player.givePlayer().getWorld()));
	}
	
	boolean isGame;
	protected void inLoop(final int time)
	{
		this.doStabilizePlayers();
		if(this.doStabilizeAmountPlayers())
		{
			isGame = true;
			this.doStabilizePlayersTeleportToArea();
			this.doStabilizePlayersRegionBattle();
			this.doStabilizePlayersRecentlyDied();
			this.doStabilizeBlocksArea();
			this.doStabilizeSidebar();
			this.doStabilizeTimes(time);
			if(this.giveScores().getBlue() <= 0 || this.giveScores().getRed() <= 0)
			{
				this.doWin(this.giveScores().getBlue() > 0 ? Team.BLUE : this.giveScores().getRed() > 0 ? Team.RED : null);
				this.doStabilizePlayersLeaveArea();
				this.doCleanSidebar();
				this.doCleanArea();
				this.doReload();
				isGame = false;
			}
		}
		else if(isGame)
		{
			this.doStabilizePlayersLeaveArea();
			this.doCleanSidebar();
			this.doCleanArea();
			this.doReload();
			isGame = false;
		}
	}
	
	protected void doStabilizePlayers()
	{
		final List<PlayerControl> players = this.givePlayersControl();
		final Map<PlayerControl, Team> map = new HashMap<PlayerControl, Team>(this.giveTeams());
		for(final PlayerControl player : map.keySet())
			if(!players.contains(player) || map.get(player) != player.getTeam())
				this.giveTeams().remove(player);
		for(final PlayerControl player : players)
			if(!map.containsKey(player) && player.getTeam() != null)
				this.giveTeams().put(player, player.getTeam());
	}
	
	protected boolean doStabilizeAmountPlayers(final Map<Team, Integer> amountPlayers)
	{
		return amountPlayers.get(Team.BLUE) >= 1 && amountPlayers.get(Team.RED) >= 1;
	}
	
	protected boolean doStabilizeAmountPlayers()
	{
		return this.doStabilizeAmountPlayers(this.giveAmountPlayers());
	}
	
	protected void doStabilizePlayersTeleportToArea()
	{
		for(final PlayerControl player : this.givePlayersControl())
			if(this.giveTeams().containsKey(player) && !player.isGame())
			{
				this.doPlayerTeleportToTeamSpawn(player);
				final Team team = this.giveTeams().get(player);
				Conquest.doSend(ChatColor.GRAY + "Do³¹czono do " + (team == Team.BLUE ? ChatColor.DARK_BLUE + "niebieskich" : team == Team.RED ? ChatColor.DARK_RED + "czerwonych" : null) + ChatColor.GRAY + ".", player.givePlayer());
			}
	}
	
	protected void doStabilizePlayersRecentlyDied()
	{
		for(final PlayerControl player : this.givePlayersControl())
			if(this.giveTeams().containsKey(player) && player.isGame() && player.isRecentlyDied())
			{
				player.doResetRecentlyDied();
				this.doPlayerTeleportToTeamSpawn(player);
				final Team team = this.giveTeams().get(player);
				final int distance = Conquest.giveConquest().giveFileConfig().getInt("conquest.DivisorDistance");
				this.giveScores().delMultiple(team, distance);
				switch(team)
				{
					case BLUE:
					{
						if(this.giveBattleDualRegion().giveFirst() == this.giveBattleBlue())
						{
							this.giveBattleBlue().doSlide(this.giveDimension(), 0, -distance);
							this.giveBattleRed().doSlide(this.giveDimension(), -distance, 0);
						}
						else if(this.giveBattleDualRegion().giveSecond() == this.giveBattleBlue())
						{
							this.giveBattleBlue().doSlide(this.giveDimension(), distance, 0);
							this.giveBattleRed().doSlide(this.giveDimension(), 0, distance);
						}
					} break;
					case RED:
					{
						if(this.giveBattleDualRegion().giveFirst() == this.giveBattleRed())
						{
							this.giveBattleBlue().doSlide(this.giveDimension(), -distance, 0);
							this.giveBattleRed().doSlide(this.giveDimension(), 0, -distance);
						}
						else if(this.giveBattleDualRegion().giveSecond() == this.giveBattleRed())
						{
							this.giveBattleBlue().doSlide(this.giveDimension(), 0, distance);
							this.giveBattleRed().doSlide(this.giveDimension(), distance, 0);
						}
					} break;
				}
			}
	}
	
	protected void doStabilizeBlocksArea()
	{
		final World world = this.giveConquest().giveLobby().getWorld();
		RegionDetermine.doReplaceBlocks(159, Team.RED.giveBlockData(), 159, Team.BLUE.giveBlockData(), this.giveBattleBlue(), world);
		RegionDetermine.doReplaceBlocks(159, Team.BLUE.giveBlockData(), 159, Team.RED.giveBlockData(), this.giveBattleRed(), world);
		RegionDetermine.doReplaceBlocks(35, Team.RED.giveBlockData(), 0, 0, this.giveBattleBlue(), world);
		RegionDetermine.doReplaceBlocks(35, Team.BLUE.giveBlockData(), 0, 0, this.giveBattleRed(), world);
		RegionDetermine.doReplaceBlocks(35, Team.RED.giveBlockHardData(), 0, 0, this.giveBattleBlue(), world);
		RegionDetermine.doReplaceBlocks(35, Team.BLUE.giveBlockHardData(), 0, 0, this.giveBattleRed(), world);
	}
	
	protected void doStabilizePlayersRegionBattle()
	{
		for(final PlayerControl player : this.givePlayersControl())
			if(this.giveTeams().containsKey(player) && player.isGame())
			{
				boolean back = false;
				final Location location = player.giveLocation();
				switch(this.giveTeams().get(player))
				{
					case BLUE:
					{
						if(this.battleRed.isContains(location))
							back = true;
					} break;
					case RED:
					{
						if(this.battleBlue.isContains(location))
							back = true;
					} break;
				}
				if(back)
				{
					player.givePlayer().damage(1.0F);
					this.doPlayerTeleportToTeamSpawn(player);
				}
			}
	}
	
	protected void doStabilizeSidebar()
	{
		final Scoreboard scoreboard = this.giveConquest().giveScoreboard();
		scoreboard.resetScores("");
		final Objective objective = scoreboard.getObjective("sidebar");
		objective.getScore(ChatColor.DARK_BLUE + "Niebiescy").setScore(this.giveScores().getBlue());
		objective.getScore(ChatColor.DARK_RED + "Czerwoni").setScore(this.giveScores().getRed());
	}
	
	protected int timeBuild, timeFight;
	
	protected void doStabilizeTimes(final int time)
	{
		final int timeLoop = Conquest.giveConquest().giveFileConfig().getInt("conquest.LoopTime");
		if(this.timeBuild < Conquest.giveConquest().giveFileConfig().getInt("conquest.BuildTime"))
			this.timeBuild += timeLoop;
		else if(this.timeFight < Conquest.giveConquest().giveFileConfig().getInt("conquest.FightTime"))
			this.timeFight += timeLoop;
		else
		{
			this.timeBuild = 0;
			this.timeFight = 0;
		}
		for(final PlayerControl player : this.givePlayersControl())
		{
			if(player.isGame())
			{
				final PlayerInventory inventory = player.givePlayer().getInventory();
				if(this.timeBuild < Conquest.giveConquest().giveFileConfig().getInt("conquest.BuildTime"))
				{
					if(player.getTime() != Time.BUILD)
					{
						player.setTime(Time.BUILD);
						player.doAdjustInventoryBasedTime(inventory);
					}
				}
				else if(this.timeFight < Conquest.giveConquest().giveFileConfig().getInt("conquest.FightTime"))
				{
					if(player.getTime() != Time.FIGHT)
					{
						player.setTime(Time.FIGHT);
						player.doAdjustInventoryBasedTime(inventory);
					}
				}
				if(player.getTime() == Time.FIGHT && time % Conquest.giveConquest().giveFileConfig().getInt("conquest.AddArrowTime") == 0)
				{
					if(inventory.getItem(8) != null)
						inventory.addItem(new ItemStack(Material.ARROW));
					else
						inventory.setItem(8, new ItemStack(Material.ARROW));
				}
			}
			else if(player.getTime() != null)
					player.setTime(null);
		}
	}
	
	protected void doStabilizePlayersLeaveArea()
	{
		for(final PlayerControl player : this.givePlayersControl())
			this.doLeavePlayerArea(player);
	}
	
	protected void doLeavePlayerArea(final PlayerControl player)
	{
		if(this.giveTeams().containsKey(player) && player.isGame())
		{
			player.setTeam(null);
			player.setTime(null);
//			player.doResetTimes();
			player.doTeleportLobby();
		}
	}
	
	protected void doCleanArea()
	{
		final World world = this.giveConquest().giveLobby().getWorld();
		RegionDetermine.doReplaceBlocks(35, Team.BLUE.giveBlockData(), 0, 0, this.giveBattle(), world);
		RegionDetermine.doReplaceBlocks(35, Team.BLUE.giveBlockHardData(), 0, 0, this.giveBattle(), world);
		RegionDetermine.doReplaceBlocks(35, Team.RED.giveBlockData(), 0, 0, this.giveBattle(), world);
		RegionDetermine.doReplaceBlocks(35, Team.RED.giveBlockHardData(), 0, 0, this.giveBattle(), world);
	}
	
	protected void doCleanSidebar()
	{
		final Scoreboard scoreboard = this.giveConquest().giveScoreboard();
		scoreboard.resetScores(ChatColor.DARK_BLUE + "Niebiescy");
		scoreboard.resetScores(ChatColor.DARK_RED + "Czerwoni");
		scoreboard.getObjective("sidebar").getScore("").setScore(0);
	}
	
	@SuppressWarnings("static-access")
	protected void doWin(final Team team)
	{
		for(final PlayerControl control : this.givePlayersControl())
			if(this.giveTeams().containsKey(control) && control.isGame())
			{
				final Player player = control.givePlayer();
				final String blue = ChatColor.BLUE + "niebieskich", red = ChatColor.RED + "czerwonych", teamNameWin = team == Team.BLUE ? blue : team == Team.RED ? red : null, teamNameLose = team == Team.BLUE ? red : team == Team.RED ? blue : null;
				try
				{
					this.giveConquest().giveActionBar().sendRawSubtitle(player, ChatColor.YELLOW + "Zagraj ponownie!");
				}
				catch(final NullPointerException e) {}
				if(team == this.giveTeams().get(control))
				{
					Conquest.doSendDebugToPlayer("Wygra³eœ!", player);
					Conquest.doSend(ChatColor.DARK_GREEN + "Wygra³eœ razem z dru¿yn¹ " + teamNameWin + ChatColor.DARK_GREEN + "!", player);
					try
					{
						this.giveConquest().giveActionBar().sendRawTitle(player, ChatColor.DARK_GREEN + "W Y G R A N A");
					}
					catch(final NullPointerException e) {}
				}
				else
				{
					Conquest.doSendDebugToPlayer("Przegra³eœ!", player);
					Conquest.doSend(ChatColor.DARK_RED + "Dru¿yna " + teamNameLose + ChatColor.DARK_RED + " przegra³a razem z Tob¹.", player);
					try
					{
						this.giveConquest().giveActionBar().sendRawTitle(player, ChatColor.AQUA + "Wygra³a dru¿yna przeciwna");
					}
					catch(final NullPointerException e) {}
				}
				Conquest.doSendDebugToPlayer("Wygra³a dru¿yna " + team + ".", player);
			}
	}
	
	protected Map<Team, Integer> giveAmountPlayers()
	{
		int blue = 0, red = 0;
		for(final Team team : this.giveTeams().values())
			switch(team)
			{
				case BLUE:
					blue++;
					break;
				case RED:
					red++;
					break;
			}
		final Map<Team, Integer> map = new HashMap<Team, Integer>();
		map.put(Team.BLUE, blue);
		map.put(Team.RED, red);
		return map;
	}
	
}
