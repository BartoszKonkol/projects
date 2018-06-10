package net.polishgames.rhenowar.util.world;

import org.bukkit.Material;
import org.bukkit.entity.Ambient;
import org.bukkit.entity.Animals;
import org.bukkit.entity.EnderDragon;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Ghast;
import org.bukkit.entity.IronGolem;
import org.bukkit.entity.ItemFrame;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.entity.Shulker;
import org.bukkit.entity.Slime;
import org.bukkit.entity.Snowman;
import org.bukkit.entity.Squid;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.hanging.HangingBreakByEntityEvent;
import org.bukkit.event.hanging.HangingPlaceEvent;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.weather.ThunderChangeEvent;
import org.bukkit.event.weather.WeatherChangeEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.projectiles.ProjectileSource;
import net.polishgames.rhenowar.util.AbstractEvents;
import net.polishgames.rhenowar.util.RhenowarPlugin;
import net.polishgames.rhenowar.util.Util;
import net.polishgames.rhenowar.util.WorldData;
import net.polishgames.rhenowar.util.event.PlayerFallEvent;

public final class EventsWorld extends AbstractEvents
{
	
	public EventsWorld(final RhenowarPlugin plugin)
	{
		super(plugin);
	}
	
	@EventHandler(priority = EventPriority.LOW)
	public void onPlayerJoin(final PlayerJoinEvent event)
	{
		this.doAdaptPlayer(event.getPlayer());
	}
	
	@EventHandler(priority = EventPriority.LOW)
	public void onPlayerChangedWorld(final PlayerChangedWorldEvent event)
	{
		this.doAdaptPlayer(event.getPlayer());
	}
	
	@EventHandler(ignoreCancelled = true, priority = EventPriority.LOW)
	public void onPlayerFall(final PlayerFallEvent event)
	{
		if(!WorldData.giveWorldData(event.getPlayer().getWorld()).getFall())
			event.setCancelled(true);
	}
	
	@EventHandler(ignoreCancelled = true, priority = EventPriority.LOW)
	public void onFoodLevelChange(final FoodLevelChangeEvent event)
	{
		if(!WorldData.giveWorldData(event.getEntity().getWorld()).getHunger())
			event.setCancelled(true);
	}
	
	@EventHandler(ignoreCancelled = true, priority = EventPriority.LOW)
	public void onCreatureSpawn(final CreatureSpawnEvent event)
	{
		final LivingEntity entity = event.getEntity();
		final WorldData world = WorldData.giveWorldData(entity.getWorld());
		if(
				(!world.getAnimals() && (
					entity instanceof Animals ||
					entity instanceof Ambient ||
					entity instanceof IronGolem ||
					entity instanceof Snowman ||
					entity instanceof Squid )
			) ||
				(!world.getMonsters() && (
					entity instanceof Monster ||
					entity instanceof EnderDragon ||
					entity instanceof Ghast ||
					entity instanceof Shulker ||
					entity instanceof Slime )
			) )
				event.setCancelled(true);
	}
	
	@EventHandler(ignoreCancelled = true, priority = EventPriority.LOW)
	public void onWeatherChange(final WeatherChangeEvent event)
	{
		if(event.toWeatherState() && !WorldData.giveWorldData(event.getWorld()).getWeather())
			event.setCancelled(true);
	}
	
	@EventHandler(ignoreCancelled = true, priority = EventPriority.LOW)
	public void onThunderChange(final ThunderChangeEvent event)
	{
		if(event.toThunderState() && !WorldData.giveWorldData(event.getWorld()).getWeather())
			event.setCancelled(true);
	}
	
	@EventHandler(ignoreCancelled=true, priority = EventPriority.LOW)
	public void onBlockPlace(final BlockPlaceEvent event)
	{
		if(Util.hasUtil() && Util.giveUtil().isBuildBlocked(event.getPlayer(), event.getBlock()))
			event.setCancelled(true);
	}
	
	@EventHandler(ignoreCancelled=true, priority = EventPriority.LOW)
	public void onBlockBreak(final BlockBreakEvent event)
	{
		if(Util.hasUtil() && Util.giveUtil().isBuildBlocked(event.getPlayer(), event.getBlock()))
			event.setCancelled(true);
	}
	
	@EventHandler(ignoreCancelled=true, priority = EventPriority.LOW)
	public void onHangingBreakByEntity(final HangingBreakByEntityEvent event)
	{
		final Entity entity = event.getEntity(), remover = event.getRemover();
		if(Util.hasUtil() && entity.getType() == EntityType.ITEM_FRAME && remover instanceof Player && Util.giveUtil().isBuildBlocked((Player) remover, entity.getLocation()))
			event.setCancelled(true);
	}
	
	@EventHandler(ignoreCancelled=true, priority = EventPriority.LOW)
	public void onHangingPlace(final HangingPlaceEvent event)
	{
		final Entity entity = event.getEntity();
		if(Util.hasUtil() && entity.getType() == EntityType.ITEM_FRAME && Util.giveUtil().isBuildBlocked(event.getPlayer(), entity.getLocation()))
			event.setCancelled(true);
	}
	
	@EventHandler(ignoreCancelled=true, priority = EventPriority.LOW)
	public void onPlayerInteractEntity(final PlayerInteractEntityEvent event)
	{
		final Entity entity = event.getRightClicked();
		if(Util.hasUtil() && entity.getType() == EntityType.ITEM_FRAME && entity instanceof ItemFrame)
		{
			final ItemStack item = ((ItemFrame) entity).getItem();
			if(item != null && item.getType() != Material.AIR && Util.giveUtil().isBuildBlocked(event.getPlayer(), entity.getLocation()))
				event.setCancelled(true);
		}
	}
	
	@EventHandler(ignoreCancelled=true, priority = EventPriority.LOW)
	public void onEntityDamage(final EntityDamageByEntityEvent event)
	{
		final Entity entity = event.getEntity(), damager = event.getDamager();
		if(damager instanceof Player)
		{
			if(entity instanceof Player)
			{
				if(!WorldData.giveWorldData(entity.getWorld()).getPvP())
					event.setCancelled(true);
			}
			else if(Util.hasUtil() && entity.getType() == EntityType.ITEM_FRAME && Util.giveUtil().isBuildBlocked((Player) damager, entity.getLocation()))
				event.setCancelled(true);
		}
		else if(Util.hasUtil() && damager instanceof Projectile && entity.getType() == EntityType.ITEM_FRAME)
		{
			final ProjectileSource shooter = ((Projectile) damager).getShooter();
			if(shooter instanceof Player && Util.giveUtil().isBuildBlocked((Player) shooter, entity.getLocation()))
				event.setCancelled(true);
		}
	}
	
	protected final void doAdaptPlayer(final Player player)
	{
		final WorldData data = WorldData.giveWorldData(player.getWorld());
		player.setGameMode(data.getGameMode());
		if(Util.hasUtil())
			player.teleport(Util.giveUtil().giveSpawnLocation(data));
	}
	
}
