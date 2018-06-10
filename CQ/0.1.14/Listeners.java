package net.polishgames.rhenowar.conquest;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByBlockEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.util.BlockIterator;

public class Listeners implements Listener
{
	
	private final Conquest conquest;
	
	public Listeners()
	{
		this.conquest = Conquest.giveConquest();
	}
	
	public final Conquest giveConquest()
	{
		return this.conquest;
	}
	
	protected Inventory menu;
	
	public final void doRegister()
	{
		this.giveConquest().getServer().getPluginManager().registerEvents(this.giveConquest().giveListener(), this.giveConquest());
		this.menu = Bukkit.createInventory(null, 27, "Wybierz dru¿ynê");
		final ItemStack red = new ItemStack(Material.WOOL, 1, (short) Team.RED.giveBlockData());
		final ItemMeta redMeta = red.getItemMeta();
		redMeta.setDisplayName("§4Czerwoni");
		red.setItemMeta(redMeta);
		this.menu.setItem(11, red);
		final ItemStack blue = new ItemStack(Material.WOOL, 1, (short) Team.BLUE.giveBlockData());
		final ItemMeta blueMeta = red.getItemMeta();
		blueMeta.setDisplayName("§1Niebiescy");
		blue.setItemMeta(blueMeta);
		this.menu.setItem(15, blue);
	}
	
	@EventHandler
	public void onPlayerChangedWorld(final PlayerChangedWorldEvent event)
	{
		final Player player = event.getPlayer();
		final World world = Conquest.giveConquest().giveLobby().getWorld();
		if(player.getWorld() == world)
		{
			if(!this.isPlayerProperWorld(player))
				return;
			final PlayerControl control = PlayerControl.givePlayerControl(player);
			control.onJoin(event);
			final BukkitScheduler scheduler = player.getServer().getScheduler();
			final int pause = this.giveConquest().giveFileConfig().getInt("conquest.LoopTime");
			scheduler.scheduleSyncDelayedTask(this.giveConquest(), new Runnable()
			{
				private int time;
				private volatile boolean continueLoop;
				private volatile Thread thread;
				
				@Override
				public final void run()
				{
					control.onJoinSchedule(event, menu);
					this.doScheduleLoop();
				}
				
				private final void doScheduleLoop()
				{
					this.time += pause;
					scheduler.scheduleSyncDelayedTask(giveConquest(), new Runnable()
					{
						@Override
						public void run()
						{
							thread = new Thread(new Runnable()
							{
								@Override
								public final void run()
								{
									continueLoop = control.inLoop(player, scheduler, Integer.valueOf(time));
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
								if(continueLoop)
									doScheduleLoop();
							}
							catch(final InterruptedException e)
							{
								e.printStackTrace();
							}
						}
					}).start();
				}
			}, 30);
		}
		else if(event.getFrom() == world)
		{
//			if(this.isPlayerProperWorld(player))
			PlayerControl.givePlayerControl(player).onQuit(event);
		}
	}
	
	@EventHandler
	public void onPlayerTeleport(final PlayerTeleportEvent event)
	{
		final Player player = event.getPlayer();
		if(this.isPlayerProperWorld(player))
			PlayerControl.givePlayerControl(player).onTeleport(event);
	}
	
	@EventHandler
	public void onInventoryClick(final InventoryClickEvent event)
	{
		if(event.getInventory() != null)
		{
			final ItemStack item = event.getCurrentItem();
			if(item != null && item.getItemMeta() != null)
				{
					final HumanEntity human = event.getWhoClicked();
					if(human instanceof Player)
					{
						final Player player = (Player) human;
						if(this.isPlayerProperWorld(player))
							PlayerControl.givePlayerControl(player).onInventoryClick(event, this.menu);
					}
				}
		}
	}
	
	@EventHandler
	public void onProjectileHit(final ProjectileHitEvent event)
	{
		final Projectile projectile = event.getEntity();
		if(projectile instanceof Arrow)
		{
			final Arrow arrow = (Arrow) projectile;
			@SuppressWarnings("deprecation")
			final LivingEntity living = arrow.getShooter();
			if(living instanceof Player)
			{
				final Player player = (Player) living;
				if(!this.isPlayerProperWorld(player))
					return;
				final BlockIterator iterator = new BlockIterator(arrow.getWorld(), arrow.getLocation().toVector(), arrow.getVelocity().clone().normalize(), 0, 4);
				Block block = null;
				while(iterator.hasNext())
					if((block = iterator.next()).getType() != Material.AIR)
						break;
				if(block != null && block.getType() == Material.WOOL)
				{
					final Block finalBlock = block;
					player.getServer().getScheduler().scheduleSyncDelayedTask(this.giveConquest(), new Runnable()
					{
						@Override
						public void run()
						{
							if(!arrow.isDead())
								PlayerControl.givePlayerControl(player).onArrowHitWool(player, finalBlock, arrow);
						}
					}, 1);
				}
			}
			Bukkit.getScheduler().scheduleSyncDelayedTask(this.giveConquest(), new Runnable()
			{
				@Override
				public void run()
				{
					if(!arrow.isDead())
						arrow.remove();
				}
			}, 2);
		}
	}
	
	@EventHandler
	public void onEntityDamage(final EntityDamageEvent event)
	{
		final Entity entity = event.getEntity();
		if(entity instanceof Player)
		{
			final Player player = (Player) entity;
			if(!this.isPlayerProperWorld(player))
				return;
			final PlayerControl control = PlayerControl.givePlayerControl(player);
			if(event instanceof EntityDamageByEntityEvent)
			{
				final EntityDamageByEntityEvent subevent = (EntityDamageByEntityEvent) event;
				final Entity damager = subevent.getDamager();
				if(damager instanceof Arrow)
				{
					final Arrow arrow = (Arrow) damager;
					@SuppressWarnings("deprecation")
					final LivingEntity shooter = arrow.getShooter();
					if(shooter instanceof Player && control.onArrowDamageShoot(player, PlayerControl.givePlayerControl((Player) shooter), arrow))
						control.doCancel(subevent);
				}
				else
					control.onDamageByEntity(subevent);
				
			}
			else if(event instanceof EntityDamageByBlockEvent)
				control.onDamageByBlock((EntityDamageByBlockEvent) event);
			else
				control.onDamage(event);
		}
	}
	
	@EventHandler
	public void onPlayerDeath(final PlayerDeathEvent event)
	{
		final Player player = event.getEntity();
		if(this.isPlayerProperWorld(player))
			PlayerControl.givePlayerControl(player).onDeath(event);
	}
	
	@EventHandler
	public void onBlockPlace(final BlockPlaceEvent event)
	{
		final Player player = event.getPlayer();
		if(this.isPlayerProperWorld(player))
			PlayerControl.givePlayerControl(player).onBlockPlace(event);
	}
	
	@EventHandler
	public void onBlockBreak(final BlockBreakEvent event)
	{
		final Player player = event.getPlayer();
		if(this.isPlayerProperWorld(player))
			PlayerControl.givePlayerControl(player).onBlockBreak(event);
	}
	
	@EventHandler
	public void onPlayerInteract(final PlayerInteractEvent event)
	{
		final ItemStack item = event.getItem();
		if(item != null && item.getItemMeta() != null)
		{
			final Player player = event.getPlayer();
			if(this.isPlayerProperWorld(player))
				PlayerControl.givePlayerControl(player).onInteract(event, this.menu);
		}
	}
	
	@EventHandler
	public void onPlayerDropItem(final PlayerDropItemEvent event)
	{
		final Player player = event.getPlayer();
		if(this.isPlayerProperWorld(player))
			PlayerControl.givePlayerControl(player).onDropItem(event);
	}
	
	public boolean isPlayerProperWorld(final Player player)
	{
		return player.getWorld().getName().equals(this.giveConquest().giveLobby().getWorld().getName());
	}
	
}
