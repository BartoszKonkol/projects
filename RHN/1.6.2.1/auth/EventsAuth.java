package net.polishgames.rhenowar.util.auth;

import java.sql.SQLException;
import java.util.Map;
import org.bukkit.entity.Entity;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.entity.EntityCombustEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityEvent;
import org.bukkit.event.entity.EntityInteractEvent;
import org.bukkit.event.entity.EntityRegainHealthEvent;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.entity.EntityTargetEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.entity.PotionSplashEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.event.hanging.HangingBreakByEntityEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerAnimationEvent;
import org.bukkit.event.player.PlayerBedEnterEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerEditBookEvent;
import org.bukkit.event.player.PlayerEvent;
import org.bukkit.event.player.PlayerExpChangeEvent;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.event.player.PlayerShearEntityEvent;
import org.bukkit.event.player.PlayerStatisticIncrementEvent;
import org.bukkit.event.player.PlayerToggleFlightEvent;
import org.bukkit.scheduler.BukkitTask;
import net.polishgames.rhenowar.util.AbstractEvents;
import net.polishgames.rhenowar.util.FieldData;
import net.polishgames.rhenowar.util.Password;
import net.polishgames.rhenowar.util.RhenowarPlugin;
import net.polishgames.rhenowar.util.TaskSchedulerAsync;
import net.polishgames.rhenowar.util.TaskSchedulerSync;
import net.polishgames.rhenowar.util.Util;
import net.polishgames.rhenowar.util.auth.task.AsyncChatTask;
import net.polishgames.rhenowar.util.auth.task.AsyncJoinTask;
import net.polishgames.rhenowar.util.auth.task.AsyncLeaveTask;
import net.polishgames.rhenowar.util.auth.task.SyncInventoryCloseTask;
import net.polishgames.rhenowar.util.event.PlayerLeaveEvent;
import net.polishgames.rhenowar.util.event.PlayerPressQEvent;
import net.polishgames.rhenowar.util.event.PlayerSignClickEvent;
import net.polishgames.rhenowar.util.event.PlayerSignTeleportEvent;

public final class EventsAuth extends AbstractEvents
{

	public EventsAuth(final RhenowarPlugin plugin)
	{
		super(plugin);
	}
	
	protected boolean error;

	@Override
	public Map<String, Object> giveProperties(final Map<String, Object> map)
	{
		map.put("error", this.error);
		return super.giveProperties(map);
	}

	@EventHandler(priority = EventPriority.LOWEST)
	public void onPlayerJoin(final PlayerJoinEvent event)
	{
		new AsyncJoinTask(this.givePlayerAuth(event)).runTaskLaterAsynchronously(this.givePlugin(), 10);
	}

	@EventHandler(priority = EventPriority.LOWEST)
	public void onPlayerLeave(final PlayerLeaveEvent event)
	{
		event.setCancelled(false);
		if(!this.error)
			this.doRunTaskAsync(new AsyncLeaveTask(this.givePlayerAuth(event)));
	}

	@EventHandler(priority = EventPriority.LOWEST)
	public void onAsyncPlayerChat(final AsyncPlayerChatEvent event)
	{
		final PlayerAuth playerAuth = this.givePlayerAuth(event);
		if(playerAuth != null && playerAuth.doCancelEvent(event))
			this.doRunTaskAsync(new AsyncChatTask(playerAuth, new Password(event.getMessage())));
	}

	@EventHandler(ignoreCancelled = true, priority = EventPriority.HIGHEST)
	public void onPlayerMove(final PlayerMoveEvent event)
	{
		this.doCancelEvent(event);
	}

	@EventHandler(ignoreCancelled = true, priority = EventPriority.HIGHEST)
	public void onInventoryOpen(final InventoryOpenEvent event)
	{
		final HumanEntity entity = event.getPlayer();
		if(this.doCancelEvent(event, entity))
			new SyncInventoryCloseTask(entity).runTaskLater(this.givePlugin(), 1L);
	}

	@EventHandler(ignoreCancelled = true, priority = EventPriority.HIGHEST)
	public void onInventoryClick(final InventoryClickEvent event)
	{
		this.doCancelEvent(event, event.getWhoClicked());
	}

	@EventHandler(ignoreCancelled = true, priority=EventPriority.HIGHEST)
	public void onInventoryDrag(final InventoryDragEvent event)
	{
		this.doCancelEvent(event, event.getWhoClicked());
	}

	@EventHandler(ignoreCancelled = true, priority=EventPriority.HIGHEST)
	public void onPotionSplash(final PotionSplashEvent event)
	{
		for(final LivingEntity entity : event.getAffectedEntities())
			if(entity != null && entity instanceof HumanEntity && !this.givePlayerAuth((HumanEntity) entity).isLogged())
				event.setIntensity(entity, 0.0D);
	}

	@EventHandler(ignoreCancelled = true, priority = EventPriority.HIGHEST)
	public void onProjectileLaunch(final ProjectileLaunchEvent event)
	{
		final Projectile projectile = event.getEntity();
		if(projectile != null)
			try
			{
				final Object entity = new FieldData(projectile, "entity").giveField().get(projectile);
				if(entity != null)
				{
					final Object shooter = new FieldData(entity, "projectileSource").giveField().get(entity);
					if(shooter != null && shooter instanceof Entity)
						this.doCancelEvent(event, (Entity) shooter);
				}
			}
			catch(final IllegalAccessException e)
			{
				e.printStackTrace();
			}
			catch(final NoSuchFieldException e) {}
	}

	@EventHandler(ignoreCancelled = true, priority=EventPriority.HIGHEST)
	public void onPlayerExpChange(final PlayerExpChangeEvent event)
	{
		final PlayerAuth playerAuth = this.givePlayerAuth(event);
		if(playerAuth != null && !playerAuth.isLogged())
			event.setAmount(0);
	}

	@EventHandler(ignoreCancelled = true, priority = EventPriority.HIGHEST)
	public void onSignChange(final SignChangeEvent event)
	{
		this.doCancelEvent(event, event.getPlayer());
	}
	
	@EventHandler(ignoreCancelled = true, priority = EventPriority.HIGHEST)
	public void onBlockPlace(final BlockPlaceEvent event)
	{
		this.doCancelEvent(event, event.getPlayer());
	}

	@EventHandler(ignoreCancelled = true, priority = EventPriority.HIGHEST)
	public void onBlockBreak(final BlockBreakEvent event)
	{
		this.doCancelEvent(event, event.getPlayer());
	}

	@EventHandler(ignoreCancelled = true, priority=EventPriority.HIGHEST)
	public void onHangingBreakByEntity(final HangingBreakByEntityEvent event)
	{
		this.doCancelEvent(event, event.getRemover());
	}

	@EventHandler(ignoreCancelled = true, priority=EventPriority.HIGHEST)
	public void onEntityCombust(final EntityCombustEvent event)
	{
		this.doCancelEvent(event, event.getEntity());
	}
	
	@EventHandler(ignoreCancelled = true, priority = EventPriority.HIGHEST)
	public void onEntityDamage(final EntityDamageEvent event)
	{
		if(this.doCancelEvent(event))
		{
			event.getEntity().setFireTicks(0);
			event.setDamage(0.0D);
		}
	}

	@EventHandler(ignoreCancelled = true, priority = EventPriority.HIGHEST)
	public void onEntityTarget(final EntityTargetEvent event)
	{
		if(this.doCancelEvent(event))
			event.setTarget(null);
	}

	@EventHandler(ignoreCancelled = true, priority = EventPriority.HIGHEST)
	public void onEntityRegainHealth(final EntityRegainHealthEvent event)
	{
		if(this.doCancelEvent(event))
			event.setAmount(0.0D);
	}

	@EventHandler(ignoreCancelled = true, priority = EventPriority.HIGHEST)
	public void onEntityDamageByEntity(final EntityDamageByEntityEvent event)
	{
		this.doCancelEvent(event);
	}

	@EventHandler(ignoreCancelled = true, priority = EventPriority.HIGHEST)
	public void onEntityInteract(final EntityInteractEvent event)
	{
		this.doCancelEvent(event);
	}

	@EventHandler(ignoreCancelled = true, priority = EventPriority.HIGHEST)
	public void onEntityShootBow(final EntityShootBowEvent event)
	{
		this.doCancelEvent(event);
	}

	@EventHandler(ignoreCancelled = true, priority = EventPriority.HIGHEST)
	public void onFoodLevelChange(final FoodLevelChangeEvent event)
	{
		this.doCancelEvent(event);
	}
	
	@EventHandler(ignoreCancelled = true, priority = EventPriority.HIGHEST)
	public void onPlayerCommandPreprocess(final PlayerCommandPreprocessEvent event)
	{
		this.doCancelEvent(event);
	}

	@EventHandler(ignoreCancelled = true, priority = EventPriority.HIGHEST)
	public void onPlayerPickupItem(final PlayerPickupItemEvent event)
	{
		this.doCancelEvent(event);
	}

	@EventHandler(ignoreCancelled = true, priority = EventPriority.HIGHEST)
	public void onPlayerInteract(final PlayerInteractEvent event)
	{
		this.doCancelEvent(event);
	}

	@EventHandler(ignoreCancelled = true, priority = EventPriority.HIGHEST)
	public void onPlayerItemConsume(final PlayerItemConsumeEvent event)
	{
		this.doCancelEvent(event);
	}

	@EventHandler(ignoreCancelled = true, priority = EventPriority.HIGHEST)
	public void onPlayerInteractEntity(final PlayerInteractEntityEvent event)
	{
		this.doCancelEvent(event);
	}

	@EventHandler(ignoreCancelled = true, priority = EventPriority.HIGHEST)
	public void onPlayerDropItem(final PlayerDropItemEvent event)
	{
		this.doCancelEvent(event);
	}

	@EventHandler(ignoreCancelled = true, priority = EventPriority.HIGHEST)
	public void onPlayerPressQ(final PlayerPressQEvent event)
	{
		this.doCancelEvent(event);
	}

	@EventHandler(ignoreCancelled = true, priority = EventPriority.HIGHEST)
	public void onPlayerBedEnter(final PlayerBedEnterEvent event)
	{
		this.doCancelEvent(event);
	}

	@EventHandler(ignoreCancelled = true, priority = EventPriority.HIGHEST)
	public void onPlayerShearEntity(final PlayerShearEntityEvent event)
	{
		this.doCancelEvent(event);
	}

	@EventHandler(ignoreCancelled = true, priority = EventPriority.HIGHEST)
	public void onPlayerFish(final PlayerFishEvent event)
	{
		this.doCancelEvent(event);
	}
	
	@EventHandler(ignoreCancelled = true, priority = EventPriority.HIGHEST)
	public void onPlayerEditBook(final PlayerEditBookEvent event)
	{
		this.doCancelEvent(event);
	}
	
	@EventHandler(ignoreCancelled = true, priority = EventPriority.HIGHEST)
	public void onPlayerAchievementAwarded(@SuppressWarnings("deprecation") final org.bukkit.event.player.PlayerAchievementAwardedEvent event)
	{
		this.doCancelEvent(event);
	}

	@EventHandler(ignoreCancelled = true, priority=EventPriority.HIGHEST)
	public void onPlayerAnimation(final PlayerAnimationEvent event)
	{
		this.doCancelEvent(event);
	}

	@EventHandler(ignoreCancelled = true, priority=EventPriority.HIGHEST)
	public void onPlayerStatisticIncrement(final PlayerStatisticIncrementEvent event)
	{
		this.doCancelEvent(event);
	}

	@EventHandler(ignoreCancelled = true, priority=EventPriority.HIGHEST)
	public void onPlayerToggleFlight(final PlayerToggleFlightEvent event)
	{
		this.doCancelEvent(event);
	}

	@EventHandler(ignoreCancelled = true, priority = EventPriority.HIGHEST)
	public void onPlayerSignTeleport(final PlayerSignTeleportEvent event)
	{
		this.doCancelEvent(event);
	}

	@EventHandler(ignoreCancelled = true, priority = EventPriority.HIGHEST)
	public void onPlayerSignClick(final PlayerSignClickEvent event)
	{
		this.doCancelEvent(event);
	}
	
	protected final PlayerAuth givePlayerAuth(final Player player)
	{
		PlayerAuth result = null;
		if(player != null)
			try
			{
				result = PlayerAuth.givePlayerAuth(Util.nonNull(player));
			}
			catch(final SQLException | NullPointerException e)
			{
				if(Util.hasUtil())
				{
					final Util util = Util.giveUtil();
					final String message = util.giveMessage(util.giveUtilConfigPrefix() + "message.ErrorSQL", player);
					this.error = true;
					util.doUtilSendMessage(player, message);
					util.doKick(player, message);
					if(e instanceof SQLException)
						util.doReportErrorSQL((SQLException) e);
					else
						e.printStackTrace();
				}
				else
					e.printStackTrace();
			}
			finally
			{
				this.error = result == null;
			}
		return result;
	}
	
	protected final PlayerAuth givePlayerAuth(final HumanEntity entity)
	{
		if(entity != null && entity instanceof Player)
			return this.givePlayerAuth((Player) entity);
		else
			return null;
	}
	
	protected final PlayerAuth givePlayerAuth(final PlayerEvent event)
	{
		return this.givePlayerAuth(Util.nonNull(event).getPlayer());
	}
	
	protected final PlayerAuth givePlayerAuth(final EntityEvent event)
	{
		final Entity entity = Util.nonNull(event).getEntity();
		if(entity != null && entity instanceof Player)
			return this.givePlayerAuth((Player) entity);
		else
			return null;
	}
	
	protected final boolean doCancelEvent(final Event event)
	{
		if(Util.nonNull(event) instanceof Cancellable)
		{
			PlayerAuth playerAuth = null;
			if(event instanceof PlayerEvent)
				playerAuth = this.givePlayerAuth((PlayerEvent) event);
			else if(event instanceof EntityEvent)
				playerAuth = this.givePlayerAuth((EntityEvent) event);
			if(playerAuth != null)
				return playerAuth.doCancelEvent((Cancellable) event);
		}
		return false;
	}
	
	protected final boolean doCancelEvent(final Cancellable event, final HumanEntity entity)
	{
		final PlayerAuth playerAuth = this.givePlayerAuth(entity);
		if(playerAuth != null)
			return playerAuth.doCancelEvent(event);
		else
			return false;
	}
	
	protected final boolean doCancelEvent(final Cancellable event, final Entity entity)
	{
		if(entity != null && entity instanceof HumanEntity)
			return this.doCancelEvent(event, (HumanEntity) entity);
		else
			return false;
	}
	
	protected final BukkitTask doRunTaskSync(final TaskSchedulerSync task)
	{
		return task.runTask(this.givePlugin());
	}
	
	protected final BukkitTask doRunTaskAsync(final TaskSchedulerAsync task)
	{
		return task.runTaskAsynchronously(this.givePlugin());
	}
	
}
