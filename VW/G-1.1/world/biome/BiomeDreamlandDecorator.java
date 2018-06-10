package vw.world.biome;

import vw.VirtualWorld;
import vw.util.Util;
import net.minecraft.util.BlockPos;
import net.minecraft.world.biome.BiomeDecorator;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.gen.feature.WorldGenMinable;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraftforge.event.terraingen.TerrainGen;
import static net.minecraftforge.event.terraingen.DecorateBiomeEvent.Decorate.EventType.FLOWERS;
import static net.minecraftforge.event.terraingen.OreGenEvent.GenerateMinable.EventType.DIRT;

public class BiomeDreamlandDecorator extends BiomeDecorator
{
	
	public static final byte SCAN = 1;
	
	protected final WorldGenerator magicRockGen;
	
	public BiomeDreamlandDecorator()
	{
		
		this.magicRockGen = new WorldGenMinable(VirtualWorld.magicRock.getDefaultState(), 3);
		
	}
	
	@Override
	protected void genDecorations(final BiomeGenBase par1BiomeGenBase) // decorate
	{
		
		super.genDecorations(par1BiomeGenBase);
		
		boolean doGen = TerrainGen.decorate(currentWorld, this.randomGenerator, this.field_180294_c, FLOWERS);
		for(int i = 0; doGen && i < (int) (this.flowersPerChunk * 0.75F); ++i)
			Util.doGenerateFlower(VirtualWorld.plantBlue.getDefaultState(), new BlockPos(this.field_180294_c.getX() + this.randomGenerator.nextInt(16) + 8, this.randomGenerator.nextInt(256), this.field_180294_c.getZ() + this.randomGenerator.nextInt(16) + 8), this.currentWorld, this.randomGenerator);
		
	}
	
	@Override
	protected void generateOres()
	{
		
		if (TerrainGen.generateOre(this.currentWorld, this.randomGenerator, this.dirtGen, this.field_180294_c, DIRT))
			this.genStandardOre1(20, this.dirtGen, 0, 256);
		
		this.genStandardOre1(2, this.magicRockGen, 0, 256);
		
	}
	
}
