package net.polishgames.rhenowar.util.protocol;

import java.util.List;
import org.bukkit.block.BlockState;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.reflect.StructureModifier;
import com.comphenix.protocol.wrappers.nbt.NbtCompound;
import com.comphenix.protocol.wrappers.nbt.NbtFactory;
import com.comphenix.protocol.wrappers.nbt.NbtWrapper;
import net.polishgames.rhenowar.util.RhenowarPlugin;
import net.polishgames.rhenowar.util.Util;

public class PacketServerMapChunk extends Packet
{
	
	public static final PacketType PACKET = PacketType.Play.Server.MAP_CHUNK;

	public PacketServerMapChunk(final RhenowarPlugin plugin)
	{
		super(plugin, PacketServerMapChunk.PACKET);
	}
	
	@Override
	public void onPacketSending(final PacketEvent packetEvent)
	{
		if(!PacketServerUpdateSign.OLD)
		{
			@SuppressWarnings("rawtypes")
			final StructureModifier<List> modifier = Util.nonNull(packetEvent).getPacket().getSpecificModifier(List.class);
			final List<Object> entities = modifier.read(0);
			boolean modified = false;
			for(int i = 0; i < entities.size(); i++)
			{
				final NbtCompound nbt = NbtFactory.fromNMSCompound(entities.get(i));
				if(nbt instanceof NbtWrapper && nbt.getString("id").equals("Sign"))
				{
					final Player player = packetEvent.getPlayer();
					final BlockState state = player.getWorld().getBlockAt(nbt.getInteger("x"), nbt.getInteger("y"), nbt.getInteger("z")).getState();
					if(state instanceof Sign)
					{
						PacketServerUpdateSign.doWriteNBT(this, nbt, player, (Sign) state);
						entities.set(i, ((NbtWrapper<?>) nbt).getHandle());
						modified |= true;
					}
				}
			}
			if(modified)
				modifier.write(0, entities);
		}
	}
	
}
