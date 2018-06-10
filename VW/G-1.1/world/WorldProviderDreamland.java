package vw.world;

import vw.util.ListIdentifiers;
import vw.world.biome.WorldChunkManagerDreamland;
import vw.world.gen.ChunkProviderDreamland;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.chunk.IChunkProvider;

public class WorldProviderDreamland extends WorldProvider 
{
	
	public static final byte SCAN = 1;

	@Override
	public String getDimensionName()
	{
		
		return "The Dreamland";
		
	}
	
	@Override
	public void registerWorldChunkManager()
	{
		
		this.worldChunkMgr = new WorldChunkManagerDreamland(this.worldObj.getSeed(), this.worldObj.getWorldInfo().getTerrainType());
		this.setDimension(ListIdentifiers.idDimensionDreamland);
		
	}
	
	@Override
	public IChunkProvider createChunkGenerator()
	{
		
		return new ChunkProviderDreamland(this.worldObj, this.worldObj.getSeed(), this, this.worldObj.getWorldInfo().getGeneratorOptions());
		
	}
	
	@Override
	public float getCloudHeight()
	{
		
		return 192.0F;
		
	}
	
	@Override
	public float calculateCelestialAngle(final long par1, final float par2)
	{
		
		return 1.0F;
		
	}
	
	@Override
	public String getSaveFolder()
	{
		
		return "TheDreamland";
		
	}

	@Override
	public String getInternalNameSuffix()
	{
		
		return "_dreamland";
		
	}
	
}
