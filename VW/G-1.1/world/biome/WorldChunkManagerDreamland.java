package vw.world.biome;

import java.util.Arrays;
import net.minecraft.world.WorldType;
import net.minecraft.world.biome.BiomeCache;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.biome.WorldChunkManager;

public class WorldChunkManagerDreamland extends WorldChunkManager
{
	
	public static final byte SCAN = 1;
	
	protected final float rainfallDefault;
	protected final BiomeCache biomeCache;

	public WorldChunkManagerDreamland(final long par1, final WorldType par2WorldType)
	{
		
		super(par1, par2WorldType, "");
		
		this.rainfallDefault = 0.0F;
		this.biomeCache = new BiomeCache(this);
		
	}
	
	@Override
	public BiomeGenBase[] getBiomesForGeneration(final BiomeGenBase[] args1BiomeGenBase, final int par2, final int par3, final int par4, final int par5)
	{
		
		return this.biomeGenerate(new BiomeGenDreamlandBase[par4 * par5], par4, par5);
		
	}
	
	@Override
	public BiomeGenBase[] getBiomeGenAt(BiomeGenBase[] args1BiomeGenBase, final int par2, final int par3, final int par4, final int par5, final boolean par6)
	{
		
		if (par6 && par4 == 16 && par5 == 16 && (par2 & 15) == 0 && (par3 & 15) == 0)
		{
			
			if (args1BiomeGenBase == null || args1BiomeGenBase.length < par4 * par5)
				args1BiomeGenBase = new BiomeGenBase[par4 * par5];
			
			BiomeGenBase[] biomegenbase = this.biomeCache.getCachedBiomes(par2, par3);
			System.arraycopy(biomegenbase, 0, args1BiomeGenBase, 0, par4 * par5);
			
			return args1BiomeGenBase;
			
		}
		else
			return this.biomeGenerate(args1BiomeGenBase, par4, par5);
		
	}
	
/*
	public float[] getTemperatures(float[] args1, final int par2, final int par3, final int par4, final int par5)
	{
		
		if (args1 == null || args1.length < par4 * par5)
			args1 = new float[par4 * par5];

		Arrays.fill(args1, 0, par4 * par5, this.temperatureDefault);
		
		return args1;
		
	}
*/

	@Override
	public float[] getRainfall(float[] args1, final int par2, final int par3, final int par4, final int par5)
	{
		
		if (args1 == null || args1.length < par4 * par5)
			args1 = new float[par4 * par5];

		Arrays.fill(args1, 0, par4 * par5, this.rainfallDefault);
		
		return args1;
		
	}
	
	protected BiomeGenBase[] biomeGenerate(final BiomeGenBase[] args1BiomeGenBase, final int par1, final int par2)
	{
		
		int i = 0;
		final int j = BiomeGenDreamlandBase.biomeList.length;
		
		for(int k = 0; k < par1 * par2; k++)
		{
			
			if(!(i < j))
				i = 0;
			
			args1BiomeGenBase[k] = BiomeGenDreamlandBase.biomeList[i];
			
			i++;
			
		}

		return args1BiomeGenBase;
		
	}
	
}
