package net.polishgames.rhenowar.conquest;

public final class TeamScores
{
	
	protected int blue, red;
	
	public final int add(final Team team)
	{
		switch(team)
		{
			case BLUE:
				return this.blue++;
			case RED:
				return this.red++;
			default:
				return 0;
		}
	}
	
	public final int del(final Team team)
	{
		switch(team)
		{
			case BLUE:
				return this.blue--;
			case RED:
				return this.red--;
			default:
				return 0;
		}
	}
	
	public final int addBlue()
	{
		this.del(Team.RED);
		return this.add(Team.BLUE);
	}
	
	public final int delBlue()
	{
		this.add(Team.RED);
		return this.del(Team.BLUE);
	}
	
	public final int setBlue(final int amount)
	{
		return this.blue = amount;
	}
	
	public final int getBlue()
	{
		return this.blue;
	}
	
	public final int addRed()
	{
		this.del(Team.BLUE);
		return this.add(Team.RED);
	}
	
	public final int delRed()
	{
		this.add(Team.BLUE);
		return this.del(Team.RED);
	}
	
	public final int setRed(final int amount)
	{
		return this.red = amount;
	}
	
	public final int getRed()
	{
		return this.red;
	}
	
	public final int addMultiple(final Team team, final int repeat)
	{
		int result = 0;
		switch(team)
		{
			case BLUE:
			{
				for(int i = 0; i < repeat; i++)
					result = this.addBlue();
			} break;
			case RED:
			{
				for(int i = 0; i < repeat; i++)
					result = this.addRed();
			} break;
		}
		return result;
	}
	
	public final int delMultiple(final Team team, final int repeat)
	{
		int result = 0;
		switch(team)
		{
			case BLUE:
			{
				for(int i = 0; i < repeat; i++)
					result = this.delBlue();
			} break;
			case RED:
			{
				for(int i = 0; i < repeat; i++)
					result = this.delRed();
			} break;
		}
		return result;
	}
	
}
