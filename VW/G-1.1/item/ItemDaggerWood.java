package vw.item;

public class ItemDaggerWood extends ItemDaggerControl
{
	
	public static final byte SCAN = 1;
	
	@Override
	public byte power()
    {
    	
    	return 1;
    	
    }
	
	@Override
	public byte colorItemRed()
	{
		
		return -24;
		
	}
	
	@Override
	public byte colorItemGreen()
	{
		
		return -50;
		
	}
	
	@Override
	public byte colorItemBlue()
	{
		
		return -98;
		
	}
	
}
