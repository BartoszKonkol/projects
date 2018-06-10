package vw.util;

import javax.JNF;

public final class ListIdentifiers
{
	
	public static final byte SCAN = 1;
	
	private ListIdentifiers()
	{
		
		;
		
	}
	
	private static final byte idBasic0 =  (byte)  (DataCalculations.byteMax*1.97); //250
	private static final byte idBasic1 =  (byte)  (DataCalculations.byteMax*0.31); //39
	private static final byte idBasic2 =  (byte)  (DataCalculations.byteMax*0.21); //26
	private static final byte idBasic3 =  (byte)  (JNF.giveConstants().mathematical("one")); //1
	
	public static final byte idEntityHuman =     (byte) (idBasic0+1);
	public static final byte idEntityHerobrine = (byte) (idEntityHuman+1);
	
	public static final byte idBiomeRainbowGlade =  (byte) (idBasic1+1);
	public static final byte idBiomeMeltingValley = (byte) (idBiomeRainbowGlade+1);
	public static final byte idBiomeDreamland =     (byte) (idBiomeMeltingValley+1);
	
	public static final byte  idAchievementVirtualWorld = (byte) (idBasic2+1);
	
	public static final byte idDimensionDreamland = (byte) (idBasic3+1);
	
}
