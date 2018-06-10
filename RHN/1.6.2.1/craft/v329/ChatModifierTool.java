package net.polishgames.rhenowar.util.craft.v329;

import java.util.Map;
import org.bukkit.craftbukkit.v1_12_R1.util.CraftChatMessage;
import net.minecraft.server.v1_12_R1.EnumChatFormat;
import net.minecraft.server.v1_12_R1.IChatBaseComponent;
import net.minecraft.server.v1_12_R1.IChatBaseComponent.ChatSerializer;
import net.polishgames.rhenowar.util.GenericList;
import net.polishgames.rhenowar.util.Util;
import net.polishgames.rhenowar.util.craft.Tool;

public final class ChatModifierTool extends Tool
{
	
	private final String text;
	
	public ChatModifierTool(final String text)
	{
		this.text = Util.nonNull(text);
	}
	
	public final String giveText()
	{
		return this.text;
	}
	
	public final GenericList<String> giveEncode()
	{
		final GenericList<String> result = new GenericList<String>(String.class);
		for(final IChatBaseComponent component : CraftChatMessage.fromString(this.giveText()))
		{
			final String element = ChatSerializer.a(component);
			if(element != null && element.length() > 0)
				result.add(element);
		}
		return result;
	}
	
	public final GenericList<String> giveDecode()
	{
		final GenericList<String> result = new GenericList<String>(String.class);
		for(final IChatBaseComponent component : IChatBaseComponent.ChatSerializer.a(Util.nonEmpty(this.giveText())))
		{
			String element = "";
			for(final IChatBaseComponent sibling : component.a())
			{
				final EnumChatFormat color = sibling.getChatModifier().getColor();
				element += (color == null ? "" : color) + sibling.getText();
			}
			if(element.length() > 0)
				result.add(element);
		}
		return result;
	}
	
	@Override
	public Map<String, Object> giveProperties(final Map<String, Object> map)
	{
		map.put("text", this.giveText());
		return map;
	}
	
}
