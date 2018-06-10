package vw.util;

import java.util.Random;

public final class DataCalculations 
{
	
	public static final byte SCAN = 1;
	
	private DataCalculations()
	{
		
		;
		
	}
	
	public static final byte byteMax = 127;
	public static final byte byteMini = -128;
	public static final short shorteMax = 32767;
	public static final short shortMini = -32768;
	public static final int intMax = 2147483647;
	public static final int intMini = -2147483648;
	
	public static final Random random = new Random();
	
	public static final int giveRandomNumber(int from, int to, Random random)
	{
		
		return random.nextInt(to - from + 1) + from;
		
	}
	
	public static final int giveRandomNumber(int from, int to)
	{
		
		return giveRandomNumber(from, to, random);
		
	}

}
