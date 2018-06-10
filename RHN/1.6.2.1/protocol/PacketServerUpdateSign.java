package net.polishgames.rhenowar.util.protocol;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.bukkit.World;
import org.bukkit.block.BlockState;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.reflect.StructureModifier;
import com.comphenix.protocol.wrappers.nbt.NbtBase;
import com.comphenix.protocol.wrappers.nbt.NbtCompound;
import net.polishgames.rhenowar.util.ProtocolVersion;
import net.polishgames.rhenowar.util.RhenowarPlugin;
import net.polishgames.rhenowar.util.Util;
import net.polishgames.rhenowar.util.event.PlayerSignSendEvent;

public class PacketServerUpdateSign extends Packet
{

	public static final int ACTION = 9;
	public static final boolean OLD = Util.hasUtil() ? Util.giveUtil().giveServerVersion().giveVersion() < ProtocolVersion.giveValue("1.9.3-pre2").giveVersion() : false;
	
	@SuppressWarnings("deprecation")
	public static final PacketType PACKET = PacketServerUpdateSign.OLD ? PacketType.Play.Server.UPDATE_SIGN : PacketType.Play.Server.TILE_ENTITY_DATA;
	
	protected static final Pattern PATTERN = Pattern.compile("\\{\"text\":\"(.*?)\"\\}");
	
	public PacketServerUpdateSign(final RhenowarPlugin plugin)
	{
		super(plugin, PacketServerUpdateSign.PACKET);
	}
	
	@Override
	public void onPacketSending(final PacketEvent packetEvent)
	{
		final PacketContainer packet = Util.nonNull(packetEvent).getPacket();
		final Player player = packetEvent.getPlayer();
		if(PacketServerUpdateSign.OLD)
		{
			final List<Integer> coord = packet.getIntegers().getValues();
			final BlockState state = player.getWorld().getBlockAt(coord.get(0), coord.get(1), coord.get(2)).getState();
			if(state instanceof Sign)
				packet.getStringArrays().write(0, this.doCallEvent(new PlayerSignSendEvent(player, (Sign) state, packet.getStringArrays().getValues().get(0))).giveLines());
		}
		else if(packet.getIntegers().read(0).intValue() == PacketServerUpdateSign.ACTION)
		{
			final World world = player.getWorld();
			final BlockState state = world.getBlockAt(packet.getBlockPositionModifier().read(0).toLocation(world)).getState();
			if(state instanceof Sign)
			{
				final StructureModifier<NbtBase<?>> modifier = packet.getNbtModifier();
				final NbtBase<?> nbt = modifier.read(0);
				if(nbt instanceof NbtCompound)
				{
					PacketServerUpdateSign.doWriteNBT(this, (NbtCompound) nbt, player, (Sign) state);
					modifier.write(0, nbt);
				}
			}
		}
	}
	
	public static boolean doRequestSend(final Sign sign)
	{
		if(PacketServerUpdateSign.OLD)
		{
			for(final Player player : Util.nonNull(sign).getWorld().getPlayers())
				if(player.getEyeLocation().distance(sign.getLocation()) <= 50)
				{
					final ProtocolManager manager = ProtocolLibrary.getProtocolManager();
					final PacketContainer packet = manager.createPacket(PacketServerUpdateSign.PACKET);
					packet.getIntegers().write(0, sign.getX()).write(1, sign.getY()).write(2, sign.getZ());
					packet.getStringArrays().write(0, sign.getLines());
					try
					{
						manager.sendServerPacket(player, packet);
						return true;
					}
					catch(final InvocationTargetException e)
					{
						return false;
					}
				}
			return false;
		}
		else
		{
			sign.update();
			return true;
		}
	}
	
	public static void doWriteNBT(final Packet packet, final NbtCompound nbt, final Player player, final Sign sign)
	{
		if(!PacketServerUpdateSign.OLD)
		{
			final String[] lines = new String[4];
			for(int i = 1; i <= lines.length; i++)
			{
				final String line = nbt.getString("Text" + i);
				final boolean empty = line == null;
				final List<String> list = Util.hasUtil() && !empty ? Util.giveUtil().giveChatModifierDecode(line) : null;
				lines[i-1] = empty ? "" : list == null ? line : list.size() > 0 ? list.get(0) : null;
				if(lines[i-1] == null)
				{
					final Matcher matcher = PacketServerUpdateSign.PATTERN.matcher(line);
					lines[i-1] = matcher.find() ? matcher.group(1) : "";
				}
			}
			packet.doCallEvent(new PlayerSignSendEvent(player, sign, lines));
			for(int i = 1; i <= lines.length; i++)
			{
				final String line = lines[i-1];
				final boolean empty = line == null;
				final List<String> list = Util.hasUtil() ? Util.giveUtil().giveChatModifierEncode(empty ? "" : line) : null;
				nbt.put("Text" + i, list != null && list.size() > 0 ? list.get(0) : empty ? "" : line);
			}
		}
	}
	
}
