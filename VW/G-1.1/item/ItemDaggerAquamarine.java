package vw.item;

public class ItemDaggerAquamarine extends ItemDaggerControl
{
	
	public static final byte SCAN = 1;
	
	@Override
	public byte power()
    {
    	
    	return 10;
    	
    }
	
	@Override
	public byte colorItemRed()
	{
		
		return -100;
		
	}
	
	@Override
	public byte colorItemGreen()
	{
		
		return -60;
		
	}
	
	@Override
	public byte colorItemBlue()
	{
		
		return 80;
		
	}
	
}
