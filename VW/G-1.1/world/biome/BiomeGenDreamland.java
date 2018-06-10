package vw.world.biome;

import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.passive.EntityBat;
import net.minecraft.entity.passive.EntityPig;

public class BiomeGenDreamland extends BiomeGenDreamlandBase
{
	
	//public static final byte SCAN = 1;

	@SuppressWarnings("unchecked")
	public BiomeGenDreamland(final int par1)
	{
		
		super(par1);
		
		this.doSpecifyBiomeColor(0, 170, 170);
		
		this.spawnableCreatureList.add(new SpawnListEntry(EntityPig.class, 10, 4, 4));
		this.spawnableMonsterList.add(new SpawnListEntry(EntityZombie.class, 10, 4, 4));
		this.spawnableCaveCreatureList.add(new SpawnListEntry(EntityBat.class, 10, 8, 8));
		
		this.theBiomeDecorator.treesPerChunk = 2;
		this.theBiomeDecorator.flowersPerChunk = 4;
		this.theBiomeDecorator.grassPerChunk = 7;
		
		this.theBiomeDecorator.generateLakes = false;
		
	}
	
}
