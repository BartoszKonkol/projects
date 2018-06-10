package net.polishgames.rhenowar.conquest;

import org.bukkit.Color;

public enum Team implements IEnum
{

	BLUE(VectorType.SPAWN_BLUE, 11, 7, Color.BLUE),
	RED(VectorType.SPAWN_RED, 14, 15, Color.RED);
	
	private final VectorType type;
	private final int data, dataHard;
	private final Color color;
	
	private Team(final VectorType type, final int data, final int dataHard, final Color color)
	{
		this.type = type;
		this.data = data;
		this.dataHard = dataHard;
		this.color = color;
	}
	
	public VectorType giveVectorType()
	{
		return this.type;
	}
	
	public int giveBlockData()
	{
		return this.data;
	}
	
	public int giveBlockHardData()
	{
		return this.dataHard;
	}
	
	public Color giveBukkitColor()
	{
		return this.color;
	}
	
}
