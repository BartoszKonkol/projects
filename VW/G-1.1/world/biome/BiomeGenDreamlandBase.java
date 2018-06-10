package vw.world.biome;

import vw.util.DataTexts;
import vw.util.ListIdentifiers;
import net.minecraft.util.BlockPos;
import net.minecraft.world.ColorizerFoliage;
import net.minecraft.world.ColorizerGrass;
import net.minecraft.world.biome.BiomeGenBase;

public class BiomeGenDreamlandBase extends BiomeGenBase 
{
	
	//public static final byte SCAN = 1;
	
	public static final BiomeGenDreamlandBase[] biomeList = new BiomeGenDreamlandBase[1];
	
	public static final BiomeGenDreamlandBase dreamland = (BiomeGenDreamlandBase) (new BiomeGenDreamland(ListIdentifiers.idBiomeDreamland)).setBiomeName(DataTexts.elementNameDreamland);
	
	public BiomeGenDreamlandBase(final int par1)
	{
		
		super(par1);

		this.temperature = 0.7F;
		this.rainfall = 0.0F;
		
		this.spawnableCreatureList.clear();
		this.spawnableWaterCreatureList.clear();
		this.spawnableMonsterList.clear();

		this.setDisableRain();
		
		this.theBiomeDecorator = new BiomeDreamlandDecorator();
		
		this.biomeList[par1 - ListIdentifiers.idBiomeDreamland] = this;
		
	}
	
	private int biomeColorRed;
	private int biomeColorGreen;
	private int biomeColorBlue;
	
	@Override
	public int getGrassColorAtPos(final BlockPos par1BlockPos) // getBiomeGrassColor
	{
		
		return this.giveBiomeColor("Grass", this.biomeColorRed, this.biomeColorGreen, this.biomeColorBlue);
		
	}
	
	@Override
	public int getFoliageColorAtPos(final BlockPos par1BlockPos) // getBiomeFoliageColor
	{
		
		return this.giveBiomeColor("Foliage", this.biomeColorRed, this.biomeColorGreen, this.biomeColorBlue);
		
	}
	
	protected final int giveBiomeColor(final String type, final int red, final int green, final int blue)
	{
		
		if(type.equalsIgnoreCase("Grass"))
			return ((ColorizerGrass.getGrassColor(this.temperature, this.rainfall) & 16711422) + red*(0x10000/*65536*//*2^16*/) + green*(0x100/*256*//*2^8*/) + blue*(0x1/*1*//*2^0*/)) / 2;
		else if(type.equalsIgnoreCase("Foliage"))
			return ((ColorizerFoliage.getFoliageColor(this.temperature, this.rainfall) & 16711422) + red*(0x10000/*65536*//*2^16*/) + green*(0x100/*256*//*2^8*/) + blue*(0x1/*1*//*2^0*/)) / 2;
		else
			return 0;
		
	}
	
	public final void doSpecifyBiomeColor(final int red, final int green, final int blue)
	{
		
		this.biomeColorRed = red;
		this.biomeColorGreen = green;
		this.biomeColorBlue = blue;
		
	}
	
}
