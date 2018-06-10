package vw.util;

import java.io.File;
import net.minecraft.client.Minecraft;
import net.minecraftforge.common.MinecraftForge;

public final class SpecificationVersion 
{
	
	public static final byte SCAN = 1;
	
	private SpecificationVersion()
	{
		
		;
		
	}
	
	//===
	
	public static final String series = "G";
	public static final byte typeHigh = 1;
	public static final byte typeLow =  1;
	
	public static final String number = "013";
	
	public static final String versionPublic = String.valueOf(false);
	
	public static final String specialName = null;
	
	public static final String copyright =          "CC Virtual World";
	public static final String copyrightMinecraft = "Minecraft \u00ae/TM & \u00a9 2009-2014 Mojang";
	
	public static final String name =          "Virtual World";
	public static final String nameMinecraft = "Minecraft";
	
	public static final String versionMinecraft = MinecraftForge.MC_VERSION;
	
	//===
	
	public static final String specialExist = String.valueOf(false);
	
	public static final String full()
	{
		
		return String.format("%s %s-%s.%s", name, series, typeHigh, typeLow);
		
	}
	
	public static final String authors()
	{
		
		final int value0 = authorsAll().length - 1;
		int value1 = 0;
		String value2 = "";
		
		while(value0 != value1)
		{
			
			value2 = value2 + authorsAll()[value1] + ", ";
			value1++;
			
		}
		
		return value2 + authorsAll()[value0];
		
	}
	
	public static final String fullMinecraft()
	{
		
		return String.format("%s %s", nameMinecraft, versionMinecraft);
		
	}
	
	private static final String[] authorsAll()
	{
		
		if(Minecraft.getMinecraft() != null)
			new Cocreators(new File(Minecraft.getMinecraft().mcDataDir, "vw"));
		
		final String[] value = new String[Cocreators.namesOfficial.size()];
		
		for(int i = 0; i < Cocreators.namesOfficial.size(); i++)
			value[i] = (String) Cocreators.namesOfficial.get(i);
		
		return value;
		
	}

}
