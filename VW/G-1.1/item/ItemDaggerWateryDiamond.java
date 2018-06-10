package vw.item;

public class ItemDaggerWateryDiamond extends ItemDaggerControl
{
	
	public static final byte SCAN = 1;
	
	@Override
	public byte power()
    {
    	
    	return 8;
    	
    }
	
	@Override
	public byte colorItemRed()
	{
		
		return -128;
		
	}
	
	@Override
	public byte colorItemGreen()
	{
		
		return 0;
		
	}
	
}
