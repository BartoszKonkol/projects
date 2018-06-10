package vw.util;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.JNF;

public final class Cocreators
{
	
	public static final byte SCAN = 1;
	
	public static final List<String> namesOfficial =   new ArrayList<String>();
	public static final List<String> nicksOfficial =   new ArrayList<String>();
	public static final List<String> namesUnofficial = new ArrayList<String>();
	public static final List<String> nicksUnofficial = new ArrayList<String>();
	
	public Cocreators(final File par1File)
	{
		
		if(par1File != null)
		{
			
			final File file = new File(par1File, "cocreators.va");
			
			try
			{
				
				JNF.giveFiles().downloadFileThrow(file, "http://data.virtualworld.keed.pl/cocreators.va", true);
				
			}
			catch (final IOException e)
			{
				
				e.printStackTrace();
				
			}

			this.namesOfficial.clear();
			this.nicksOfficial.clear();
			this.namesUnofficial.clear();
			this.nicksUnofficial.clear();
			
			final String[] names = String.valueOf(JNF.giveFiles().vaLoadReturn(file, "name")).split("&");
			final String[] nicks = String.valueOf(JNF.giveFiles().vaLoadReturn(file, "nick")).split("&");
			
			for(int i = 0; i < JNF.giveOperators().mathematical("=", names.length, nicks.length); i++)
			{
				
				this.namesUnofficial.add(names[i]);
				this.nicksUnofficial.add(nicks[i]);
				
				switch(nicks[i])
				{
				
					case "BartoszKonkol":
					case "marcin020799":
						this.namesOfficial.add(names[i]);
						this.nicksOfficial.add(nicks[i]);
						break;
					
				}
				
			}
			
		}
			
	}
	
}
