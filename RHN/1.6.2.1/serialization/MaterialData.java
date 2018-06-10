package net.polishgames.rhenowar.util.serialization;

import java.util.Map;
import org.bukkit.Material;
import net.polishgames.rhenowar.util.Util;

public class MaterialData extends RhenowarSerializable
{
	
	private static final long serialVersionUID = RhenowarSerializable.giveSerialVersion("1.4.4.1");
	
	private final Material material;
	private final byte data;
	
	public MaterialData(final Material material, final byte data)
	{
		this.material = Util.nonNull(material);
		this.data = (byte) (data & 0xF);
	}
	
	@SuppressWarnings("deprecation")
	public MaterialData(final org.bukkit.material.MaterialData material)
	{
		this(material.getItemType(), (byte) (material.getData() & 0xF));
	}
	
	public final Material giveMaterial()
	{
		return this.material;
	}
	
	public final byte giveData()
	{
		return this.data;
	}
	
	@Override
	public Map<String, Object> giveProperties(final Map<String, Object> map)
	{
		map.put("material", this.giveMaterial());
		map.put("data", this.giveData());
		return map;
	}
	
	@SuppressWarnings("deprecation")
	public final org.bukkit.material.MaterialData toMaterialDataBukkit()
	{
		return new org.bukkit.material.MaterialData(this.giveMaterial(), this.giveData());
	}
	
	@Override
	public MaterialData clone()
	{
		return new MaterialData(this.giveMaterial(), this.giveData());
	}
	
}
