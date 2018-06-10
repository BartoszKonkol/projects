package vw.world.biome;

import vw.util.DataTexts;
import vw.util.ListIdentifiers;
import net.minecraft.world.biome.BiomeGenBase;

public abstract class BiomeGenData extends BiomeGenBase
{
	
	//public static final byte SCAN = 1;

	public BiomeGenData(final int par1) 
	{
		
		super(par1);
		
	}

	public static final BiomeGenData rainbowGlade = (BiomeGenData) (new BiomeGenRainbowGlade(ListIdentifiers.idBiomeRainbowGlade)).setBiomeName(DataTexts.elementNameRainbowGlade).setColor(353825).setTemperatureRainfall(0.7F, 0.75F);
	public static final BiomeGenData meltingValley = (BiomeGenData) (new BiomeGenMeltingValley(ListIdentifiers.idBiomeMeltingValley)).setBiomeName(DataTexts.elementNameMeltingValley).setColor(747097).setFillerBlockMetadata(5159473).setTemperatureRainfall(0.2F, 0.4F);
	
}
