package net.polishgames.rhenowar.conquest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByBlockEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.scheduler.BukkitScheduler;
import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.MaxChangedBlocksException;
import com.sk89q.worldedit.Vector;
import com.sk89q.worldedit.blocks.BaseBlock;
import com.sk89q.worldedit.bukkit.BukkitWorld;

public class PlayerControl
{
	
	protected static final Map<Player, PlayerControl> PLAYERS = new HashMap<Player, PlayerControl>();
	protected static final float MAX_HEALTH = 2.0F;
	
	private final Player player;
	private final Conquest conquest;
	
	protected PlayerControl(final Player player)
	{
		this.player = player;
		this.conquest = Conquest.giveConquest();
	}
	
	private Team team;
	private Time time;
	
	protected boolean isGame;
	protected boolean isRecentlyDied;
//	protected int timeBuild, timeFight;
	
	public final Player givePlayer()
	{
		return this.player;
	}
	
	public final Conquest giveConquest()
	{
		return this.conquest;
	}
	
	public final PlayerControl setTeam(final Team team)
	{
		this.team = team;
		return this;
	}
	
	public final Team getTeam()
	{
		return this.team;
	}
	
	public final PlayerControl setTime(final Time time)
	{
		this.time = time;
		return this;
	}
	
	public final Time getTime()
	{
		return this.time;
	}
	
	public final boolean isOnline()
	{
		return this.givePlayer().isOnline();
	}
	
	public final boolean isGame()
	{
		return this.isGame;
	}
	
	public final boolean isRecentlyDied()
	{
		return this.isRecentlyDied;
	}
	
	public final Location giveLocation()
	{
		return this.givePlayer().getLocation();
	}
	
	public final Location giveLobby()
	{
		return this.giveConquest().giveLobby();
	}
	
	@SuppressWarnings("static-access")
	public void onJoin(final PlayerChangedWorldEvent event, final Object... objects)
	{
		if(this.isCorrectness(event.getPlayer()))
		{
			Conquest.doSendDebugToPlayer("Jesteœ kontrolowany przez plugin " + this.giveConquest().getName() + ". Wersja beta. Tryb raportuj¹cy.", this.givePlayer());
			final World world = this.givePlayer().getWorld();
			Conquest.doSendDebugToPlayer("Do³¹czy³eœ do gry w œwiecie " + world.getName() + ".", this.givePlayer());
			this.doTeleportLobby();
			final Location location = this.giveLocation();
			Conquest.doSendDebugToPlayer("Zosta³eœ przeniesiony do lobby. (x=" + location.getBlockX() + "; y=" + location.getBlockY() + "; z=" + location.getBlockZ() + ")", this.givePlayer());
			this.givePlayer().setGameMode(GameMode.SURVIVAL);
			this.givePlayer().setHealth(MAX_HEALTH);
			this.givePlayer().setMaxHealth(MAX_HEALTH);
			this.givePlayer().setScoreboard(this.giveConquest().giveScoreboard());
			try
			{
				this.giveConquest().giveActionBar().setTabHeader(this.givePlayer(), this.giveConquest().getName(), "&1\u2588 &f| &4\u2588");
			}
			catch(final NullPointerException e) {}
			try
			{
				this.giveConquest().giveBarAPI().setMessage(this.givePlayer(), ChatColor.GOLD + "Zauwa¿y³eœ b³¹d w tej mini-grze? Zg³oœ go: " + ChatColor.GREEN + "/cq report" + ChatColor.WHITE);
			}
			catch(final NullPointerException e) {}
			Conquest.doSendDebugToPlayer("Zosta³eœ przygotowany do gry. (ustawiono GameMode, Health, MaxHealth, Scoreboard i TabHeader)", this.givePlayer());
			final List<String> managers = this.giveConquest().giveManagers();
			this.doWriteInFrame(new int[]{3, 0, 1, 2, 2, 2, 2, 2, 0, 1},
					ChatColor.DARK_AQUA + "Witaj w mini-grze " + ChatColor.BLUE + this.giveConquest().getName() + ChatColor.DARK_AQUA + "!",
					"",
					ChatColor.GOLD + "Opiekunowie tej mini-gry:",
					managers.size() >= 1 ? ChatColor.GREEN + "- " + ChatColor.DARK_RED + managers.get(0) : null,
					managers.size() >= 2 ? ChatColor.GREEN + "- " + ChatColor.DARK_RED + managers.get(1) : null,
					managers.size() >= 3 ? ChatColor.GREEN + "- " + ChatColor.RED + managers.get(2) : null,
					managers.size() >= 4 ? ChatColor.GREEN + "- " + ChatColor.RED + managers.get(3) : null,
					managers.size() >= 5 ? ChatColor.GREEN + "- " + ChatColor.RED + managers.get(4) : null,
					"",
					ChatColor.GOLD + "Powodzenia!"
				);
		}
	}
	
	public void onJoinSchedule(final PlayerChangedWorldEvent event, final Object... objects)
	{
		if(this.isCorrectness(event.getPlayer()))
			this.givePlayer().openInventory((Inventory) objects[0]);
	}
	
	public void onTeleport(final PlayerTeleportEvent event, final Object... objects)
	{
		if(this.isCorrectness(event.getPlayer()))
		{
			final Location from = event.getFrom();
			final Location to = event.getTo();
			Conquest.doSendDebugToPlayer("Zosta³eœ przeteleportowany (z miejsca x=" + from.getBlockX() + ";y=" + from.getBlockY() + ";z=" + from.getBlockZ() + " do miejsca x=" + to.getBlockX() + ";y=" + to.getBlockY() + ";z=" + to.getBlockZ() + ").", this.givePlayer());
		}
	}
	
	public void onInventoryClick(final InventoryClickEvent event, final Object... objects)
	{
		if(this.isCorrectness(event.getWhoClicked()))
		{
			final Inventory menu = (Inventory) objects[0];
			boolean ready = false;
			if(!ready && event.getInventory().getName().equals(menu.getName()))
			{
				final String metaName = event.getCurrentItem().getItemMeta().getDisplayName();
				if(metaName.equalsIgnoreCase(menu.getItem(11).getItemMeta().getDisplayName()))
				{
					this.setTeam(Team.RED);
					Conquest.doSendDebugToPlayer("Do³¹czy³eœ do czerwonych.", this.givePlayer());
					Conquest.doSend(ChatColor.GRAY + "Zapisano do " + ChatColor.DARK_RED + "czerwonych" + ChatColor.GRAY + ".", this.givePlayer());
					ready = true;
				}
				if(metaName.equalsIgnoreCase(menu.getItem(15).getItemMeta().getDisplayName()))
				{
					this.setTeam(Team.BLUE);
					Conquest.doSendDebugToPlayer("Do³¹czy³eœ do niebieskich.", this.givePlayer());
					Conquest.doSend(ChatColor.GRAY + "Zapisano do " + ChatColor.DARK_BLUE + "niebieskich" + ChatColor.GRAY + ".", this.givePlayer());
					ready = true;
				}
			}
			if(ready)
			{
				event.setCancelled(true);
				this.givePlayer().setItemOnCursor(new ItemStack(Material.AIR, 1));
				this.givePlayer().updateInventory();
				this.givePlayer().closeInventory();
			}
		}
	}
	
	public void onDeath(final PlayerDeathEvent event, final Object... objects)
	{
		if(this.isCorrectness(event.getEntity()))
		{
			event.getDrops().clear();
			this.givePlayer().damage(0.0F);
			this.givePlayer().setHealth(MAX_HEALTH);
			if(!this.isGame())
				this.doTeleportLobby();
			else
			{
				event.setDeathMessage(MessageType.PREFIX + (this.getTeam() == Team.BLUE ? ChatColor.DARK_BLUE : this.getTeam() == Team.RED ? ChatColor.DARK_RED : null) + this + ChatColor.GRAY + " zgin¹³.");
				this.isRecentlyDied = true;
			}
		}
	}
	
	public void onInteract(final PlayerInteractEvent event, final Object... objects)
	{
		if(this.isCorrectness(event.getPlayer()) && event.getMaterial() == Material.NETHER_STAR)
			this.givePlayer().openInventory((Inventory) objects[0]);
	}
	
	@SuppressWarnings("static-access")
	public void onQuit(final PlayerChangedWorldEvent event, final Object... objects)
	{
//		if(this.isCorrectness(event.getPlayer()))
//		{
			final float health = 20.0F;
			this.givePlayer().setMaxHealth(health);
			this.givePlayer().setHealth(health);
//			this.givePlayer().setGameMode(GameMode.CREATIVE);
			Conquest.doSendDebugToPlayer("Opuœci³eœ serwer kontrolowany przez plugin " + this.giveConquest().getName() + ". (zresetowano MaxHealth, Health i GameMode)", this.givePlayer());
//		}
		this.givePlayer().setScoreboard(Bukkit.getScoreboardManager().getMainScoreboard());
		try
		{
			this.giveConquest().giveActionBar().setTabHeader(this.givePlayer(), "", "");
		}
		catch(final NullPointerException e) {}
		try
		{
			this.giveConquest().giveBarAPI().removeBar(this.givePlayer());
		}
		catch(final NullPointerException e) {}
		this.givePlayer().getInventory().clear();
	}
	
	public void onDropItem(final PlayerDropItemEvent event, final Object... objects)
	{
		if(this.isCorrectness(event.getPlayer()))
		{
			if(event.getItemDrop().getItemStack().getType() == Material.NETHER_STAR)
			{
				this.doAdjustInventoryNetherStar(this.givePlayer().getInventory());
				this.doRemove(event.getItemDrop());
			}
			else
				this.doCancel(event);
		}
	}
	
	public void onDamageByEntity(final EntityDamageByEntityEvent event, final Object... objects)
	{
		this.onDamage(event, objects);
	}
	
	public void onDamageByBlock(final EntityDamageByBlockEvent event, final Object... objects)
	{
		this.onDamage(event, objects);
	}
	
	public void onDamage(final EntityDamageEvent event, final Object... objects)
	{
		if(this.isCorrectness(event.getEntity()))
			this.doCancel(event);
	}
	
	public void onBlockPlace(final BlockPlaceEvent event, final Object... objects)
	{
		this.onBlock(event.getPlayer(), event.getBlock(), event);
	}
	
	public void onBlockBreak(final BlockBreakEvent event, final Object... objects)
	{
		this.onBlock(event.getPlayer(), event.getBlock(), event);
	}
	
	@SuppressWarnings("deprecation")
	public void onArrowHitWool(final Player player, final Block block, final Arrow arrow, final Object... objects)
	{
		if(this.isCorrectness(player))
		{
			if(this.giveConquest().giveGame().giveBattle().isContains(block.getLocation()))
			{
				final EditSession session = new EditSession(new BukkitWorld(this.giveConquest().giveLobby().getWorld()), -1);
				final Location location = block.getLocation();
				final Vector vector = new Vector(location.getX(), location.getY(), location.getBlockZ());
				int data = -1;
				final int id = session.getBlock(vector).getData();
				if(id == Team.RED.giveBlockHardData())
					data = Team.RED.giveBlockData();
				if(id == Team.BLUE.giveBlockHardData())
					data = Team.BLUE.giveBlockData();
				try
				{
					session.setBlock(vector, new BaseBlock(35, data));
				}
				catch(final MaxChangedBlocksException e)
				{
					e.printStackTrace();
				}
				if(id != 15 && id != 7)
					block.setType(Material.AIR);
				this.doPlaySound(Sound.DIG_WOOL);
			}
		}
	}
	
	public boolean onArrowDamageShoot(final Player player, final PlayerControl shooter, final Arrow arrow, final Object... objects)
	{
		if(this.isCorrectness(player))
		{
			if(this.getTeam() != null && shooter.getTeam() != null && this.getTeam() != shooter.getTeam())
			{
				this.doKill();
				this.doRemove(arrow);
				shooter.doPlaySound(Sound.SUCCESSFUL_HIT);
				Conquest.doSend(ChatColor.GRAY + "Zosta³eœ zabity przez " + (shooter.getTeam() == Team.BLUE ? ChatColor.DARK_BLUE : shooter.getTeam() == Team.RED ? ChatColor.DARK_RED : null) + shooter + ChatColor.GRAY + ".", this.givePlayer());
				Conquest.doSend(ChatColor.GRAY + "Ustrzeli³eœ " + (this.getTeam() == Team.BLUE ? ChatColor.DARK_BLUE : this.getTeam() == Team.RED ? ChatColor.DARK_RED : null) + this + ChatColor.GRAY + ".", shooter.givePlayer());
			}
			return true;
		}
		else
			return false;
	}
	
	@SuppressWarnings("deprecation")
	public void onBlock(final Player player, final Block block, final Cancellable cancellable)
	{
		if(this.isCorrectness(player))
		{
			final Region battle = RegionDetermine.giveRegion(RegionType.BATTLE, this.givePlayer().getWorld());
			if(battle.isContains(this.giveLocation()) && battle.isContains(block.getLocation()) && this.getTeam() != null && block.getType() == Material.WOOL && (block.getData() == this.getTeam().giveBlockData() || block.getData() == this.getTeam().giveBlockHardData()))
				return;
			this.doCancel(cancellable);
		}
	}
	
	public boolean inLoop(final Player player, final BukkitScheduler scheduler, final int time)
	{
		if(this.isCorrectness(player) && this.giveConquest().isRegionConfigurationAlready(this.givePlayer().getWorld()))
		{
			if(this.getTeam() != null && RegionDetermine.giveRegion(RegionType.AREA, this.givePlayer().getWorld()).isContains(this.giveLocation()))
			{
				if(!this.isGame())
				{
					this.isGame = true;
					Conquest.doSendDebugToPlayer("Jesteœ na arenie.", player);
				}
			}
			else
			{
				if(this.isGame())
				{
					this.isGame = false;
					Conquest.doSendDebugToPlayer("Opuœci³eœ arene.", player);
				}
			}
			if(this.giveLocation().getY() <= -5.0F)
				this.doKill();
//			if(this.isGame())
//			{
//				final int timeLoop = Conquest.giveConquest().giveFileConfig().getInt("conquest.LoopTime");
//				final PlayerInventory inventory = this.givePlayer().getInventory();
//				if(this.timeBuild < Conquest.giveConquest().giveFileConfig().getInt("conquest.BuildTime"))
//				{
//					this.timeBuild += timeLoop;
//					if(this.getTime() != Time.BUILD)
//					{
//						this.setTime(Time.BUILD);
//						this.doAdjustInventoryBasedTime(inventory);
//					}
//				}
//				else if(this.timeFight < Conquest.giveConquest().giveFileConfig().getInt("conquest.FightTime"))
//				{
//					this.timeFight += timeLoop;
//					if(this.getTime() != Time.FIGHT)
//					{
//						this.setTime(Time.FIGHT);
//						this.doAdjustInventoryBasedTime(inventory);
//					}
//				}
//				else
//					this.doResetTimes();
//				if(this.getTime() == Time.FIGHT && time % Conquest.giveConquest().giveFileConfig().getInt("conquest.AddArrowTime") == 0)
//				{
//					if(inventory.getItem(8) != null)
//						inventory.addItem(new ItemStack(Material.ARROW));
//					else
//						inventory.setItem(8, new ItemStack(Material.ARROW));
//				}
//			}
//			else
//			{
//				if(this.getTime() != null)
//				{
//					this.setTime(null);
//					this.doResetTimes();
//				}
//			}
			if(this.givePlayer().getFoodLevel() != 20)
				this.givePlayer().setFoodLevel(20);
			if(this.givePlayer().getSaturation() != this.givePlayer().getFoodLevel())
				this.givePlayer().setSaturation(this.givePlayer().getFoodLevel());
			return true;
		}
		return false;
	}
	
	public void doResetRecentlyDied()
	{
		if(this.isRecentlyDied())
			this.isRecentlyDied = false;
	}
	
//	public void doResetTimes()
//	{
//		this.timeBuild = 0;
//		this.timeFight = 0;
//	}
	
	public void doKill()
	{
		this.givePlayer().setHealth(0.0F);
		this.onDeath(new PlayerDeathEvent(this.givePlayer(), new ArrayList<ItemStack>(), 0, null));
	}
	
	public void doCancel(final Cancellable cancellable) 
	{
		cancellable.setCancelled(true);
	}
	
	public void doRemove(final Entity entity) 
	{
		entity.remove();
	}
	
	public void doPlaySound(final Sound sound)
	{
		this.givePlayer().playSound(this.giveLocation(), sound, 1.0F, 0.0F);
	}
	
	public void doTeleportLobby()
	{
		final PlayerInventory inventory = this.givePlayer().getInventory();
		inventory.setChestplate(new ItemStack(Material.AIR));
		this.doAdjustInventoryNetherStar(inventory);
		this.givePlayer().teleport(this.giveLobby());
	}
	
	public void doAdjustInventoryBasedTime(final PlayerInventory inventory)
	{
		if(this.getTime() != null && this.getTeam() != null)
		{
			inventory.clear();
			final ItemStack chestplate = new ItemStack(Material.LEATHER_CHESTPLATE);
			LeatherArmorMeta chestplateMeta = (LeatherArmorMeta) chestplate.getItemMeta();
			chestplateMeta.setColor(this.getTeam().giveBukkitColor());
			chestplate.setItemMeta(chestplateMeta);
			inventory.setChestplate(chestplate);
			switch(this.getTime())
			{
				case BUILD:
				{
					inventory.setItem(0, new ItemStack(Material.WOOL, 32, (short) this.getTeam().giveBlockData()));
//					inventory.setItem(1, new ItemStack(Material.WOOL, 32, (short) this.getTeam().giveBlockHardData()));
					inventory.setItem(8, new ItemStack(Material.SHEARS));
				} break;
				case FIGHT:
				{
					final ItemStack bow = new ItemStack(Material.BOW);
					bow.addEnchantment(Enchantment.DURABILITY, 2);
					inventory.setItem(0, bow);
					inventory.setItem(8, new ItemStack(Material.ARROW));
				} break;
			}
		}
	}
	
	public void doAdjustInventoryNetherStar(final PlayerInventory inventory)
	{
		inventory.clear();
		final ItemStack star = new ItemStack(Material.NETHER_STAR);
		final ItemMeta starMeta = star.getItemMeta();
		starMeta.setDisplayName(ChatColor.GOLD + "Do³¹cz do gry");
		star.setItemMeta(starMeta);
		inventory.setItemInHand(star);
	}
	
	public void doWriteInFrame(final int[] reserveColor, final String... messages)
	{
		if(reserveColor.length != messages.length)
			return;
		final String horizontal = "\u2500", vertical = "\u2502";
		String pre = "\u250c", suf = "\u2514";
		int size = 28;
		for(int i = 0; i < size; i++)
		{
			pre += horizontal;
			suf += horizontal;
		}
		pre += "\u2510";
		suf += "\u2518";
		size += 4;
		Conquest.doSend(pre, this.givePlayer());
		for(int i = 0; i < messages.length; i++)
		{
			final String message = messages[i];
			final int sizeNew = size + reserveColor[i] * 2;
			if(message != null)
				Conquest.doSend(vertical + "  " + (message.length() > sizeNew ? message.substring(0, sizeNew) : message), this.givePlayer());
		}
		Conquest.doSend(suf, this.givePlayer());
	}
	
	protected boolean isCorrectness(final Entity entity)
	{
		return (entity != null ? entity instanceof Player && (Player) entity == this.givePlayer() : false) && this.isOnline() && ((Listeners) this.giveConquest().giveListener()).isPlayerProperWorld(this.givePlayer());
	}
	
	protected final boolean isCorrectness()
	{
		return this.isCorrectness(null);
	}
	
	@Override
	public final String toString()
	{
		return this.givePlayer().getName();
	}
	
	public static PlayerControl givePlayerControl(final Player player)
	{
		final Map<Player, PlayerControl> map = new HashMap<Player, PlayerControl>(PLAYERS);
		if(map.containsKey(player))
			return map.get(player);
		else
		{
			PLAYERS.put(player, new PlayerControl(player));
			return givePlayerControl(player);
		}
	}
	
}
