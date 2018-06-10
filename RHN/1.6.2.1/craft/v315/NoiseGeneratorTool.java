package net.polishgames.rhenowar.util.craft.v315;

import java.util.Map;
import java.util.Random;
import org.bukkit.util.Vector;
import net.minecraft.server.v1_11_R1.NoiseGeneratorOctaves;
import net.minecraft.server.v1_11_R1.NoiseGeneratorPerlin;
import net.polishgames.rhenowar.util.Util;
import net.polishgames.rhenowar.util.craft.Tool;

public final class NoiseGeneratorTool extends Tool
{
	
	private final Random random;
	private final NoiseGeneratorPerlin generatorPerlinNoise;
	
	public NoiseGeneratorTool(final Random random)
	{
		this.random = Util.nonNull(random);
		this.generatorPerlinNoise = new NoiseGeneratorPerlin(this.giveRandom());
	}
	
	public final Random giveRandom()
	{
		return this.random;
	}
	
	public final NoiseGeneratorOctaves giveOctaveNoise(final Integer octaves)
	{
		return new NoiseGeneratorOctaves(this.giveRandom(), Util.nonNull(octaves));
	}
	
	public final double[] giveOctaveNoiseGenerate(final NoiseGeneratorOctaves noise, final double[] array, final Vector offset, final Vector size, final Vector scale)
	{
		return Util.nonNull(noise).a(array, Util.nonNull(offset).getBlockX(), offset.getBlockY(), offset.getBlockZ(), Util.nonNull(size).getBlockX(), size.getBlockY(), size.getBlockZ(), Util.nonNull(scale).getX(), scale.getY(), scale.getZ());
	}
	
	public final double[] giveOctaveNoiseGenerate(final NoiseGeneratorOctaves noise, final double[] array, final Integer offsetX, final Integer offsetY, final Integer offsetZ, final Integer sizeX, final Integer sizeY, final Integer sizeZ, final Double scaleX, final Double scaleY, final Double scaleZ)
	{
		return this.giveOctaveNoiseGenerate(noise, array, new Vector(Util.nonNull(offsetX), Util.nonNull(offsetY), Util.nonNull(offsetZ)), new Vector(Util.nonNull(sizeX), Util.nonNull(sizeY), Util.nonNull(sizeZ)), new Vector(Util.nonNull(scaleX), Util.nonNull(scaleY), Util.nonNull(scaleZ)));
	}
	
	public final double[] giveOctaveNoiseGenerate(final NoiseGeneratorOctaves noise, final double[] array, final Integer offsetX, final Integer offsetZ, final Integer sizeX, final Integer sizeZ, final Double scaleX, final Double scaleZ, final Double scaleNoise)
	{
		return Util.nonNull(noise).a(array, Util.nonNull(offsetX), Util.nonNull(offsetZ), Util.nonNull(sizeX), Util.nonNull(sizeZ), Util.nonNull(scaleX), Util.nonNull(scaleZ), Util.nonNull(scaleNoise));
	}
	
	public final double[] giveOctaveNoiseGenerate(final NoiseGeneratorOctaves noise, final double[] array, final Integer offsetX, final Integer offsetZ, final Integer sizeX, final Integer sizeZ, final Double scaleX, final Double scaleZ)
	{
		return this.giveOctaveNoiseGenerate(noise, array, offsetX, offsetZ, sizeX, sizeZ, scaleX, scaleZ, 0.5D);
	}
	
	public final NoiseGeneratorPerlin givePerlinNoise()
	{
		return this.generatorPerlinNoise;
	}
	
	public final double[] givePerlinNoisePopulate(final NoiseGeneratorPerlin noise, final double[] array, final Vector offset, final Vector size, final Vector scale, final Double scaleNoise)
	{
		if(Util.nonNull(array).length == 0)
			Util.doThrowIAE();
		Util.nonNull(noise).a(array, Util.nonNull(offset).getX(), offset.getY(), offset.getZ(), Util.nonNull(size).getBlockX(), size.getBlockY(), size.getBlockZ(), Util.nonNull(scale).getX(), scale.getY(), scale.getZ(), Util.nonNull(scaleNoise));
		return array;
	}
	
	public final double[] givePerlinNoisePopulate(final double[] array, final Vector offset, final Vector size, final Vector scale, final Double scaleNoise)
	{
		return this.givePerlinNoisePopulate(this.givePerlinNoise(), array, offset, size, scale, scaleNoise);
	}
	
	public final double[] givePerlinNoisePopulate(final NoiseGeneratorPerlin noise, final double[] array, final Double offsetX, final Double offsetY, final Double offsetZ, final Integer sizeX, final Integer sizeY, final Integer sizeZ, final Double scaleX, final Double scaleY, final Double scaleZ, final Double scaleNoise)
	{
		return this.givePerlinNoisePopulate(noise, array, new Vector(Util.nonNull(offsetX), Util.nonNull(offsetY), Util.nonNull(offsetZ)), new Vector(Util.nonNull(sizeX), Util.nonNull(sizeY), Util.nonNull(sizeZ)), new Vector(Util.nonNull(scaleX), Util.nonNull(scaleY), Util.nonNull(scaleZ)), scaleNoise);
	}
	
	public final double[] givePerlinNoisePopulate(final double[] array, final Double offsetX, final Double offsetY, final Double offsetZ, final Integer sizeX, final Integer sizeY, final Integer sizeZ, final Double scaleX, final Double scaleY, final Double scaleZ, final Double scaleNoise)
	{
		return this.givePerlinNoisePopulate(this.givePerlinNoise(), array, offsetX, offsetY, offsetZ, sizeX, sizeY, sizeZ, scaleX, scaleY, scaleZ, scaleNoise);
	}
	
	@Override
	public Map<String, Object> giveProperties(final Map<String, Object> map)
	{
		map.put("random", this.giveRandom());
		return map;
	}
	
}
