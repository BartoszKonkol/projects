package net.polishgames.rhenowar.util.world;

import java.util.Map;
import java.util.Random;
import org.bukkit.World;
import org.bukkit.util.Vector;
import org.bukkit.util.noise.OctaveGenerator;
import org.bukkit.util.noise.PerlinNoiseGenerator;
import org.bukkit.util.noise.PerlinOctaveGenerator;
import org.bukkit.util.noise.SimplexOctaveGenerator;
import net.polishgames.rhenowar.util.RhenowarObject;
import net.polishgames.rhenowar.util.Util;
import net.polishgames.rhenowar.util.craft.Tool;

public final class NoiseGenerator extends RhenowarObject
{

	private final Random random;
	private final PerlinNoiseGenerator generatorPerlinNoise;
	private final Tool tool;
	
	public NoiseGenerator(final Random random)
	{
		this.random = Util.nonNull(random);
		this.generatorPerlinNoise = new PerlinNoiseGenerator(this.giveRandom());
		Tool tool = null;
		if(Util.hasUtil())
		{
			final Util util = Util.giveUtil();
			try
			{
				tool = util.giveClass(util.giveCraft().giveClassTool(this.giveClassSimpleName()), this.giveRandom());
			}
			catch(final ReflectiveOperationException e)
			{
				e.printStackTrace();
			}
		}
		this.tool = tool;
	}
	
	public NoiseGenerator(final long seed)
	{
		this(new Random(seed));
	}
	
	public NoiseGenerator(final World world)
	{
		this(Util.nonNull(world).getSeed());
	}
	
	public final Random giveRandom()
	{
		return this.random;
	}
	
	public final Tool giveTool()
	{
		return this.tool;
	}
	
	public final boolean hasTool()
	{
		return this.giveTool() != null;
	}
	
	public final PerlinNoiseGenerator givePerlinNoise()
	{
		return this.generatorPerlinNoise;
	}
	
	public final double givePerlinNoise(final PerlinNoiseGenerator noise, final double x)
	{
		return Util.nonNull(noise).noise(x);
	}
	
	public final double givePerlinNoise(final double x)
	{
		return this.givePerlinNoise(this.givePerlinNoise(), x);
	}
	
	public final double givePerlinNoise(final PerlinNoiseGenerator noise, final double x, final double y)
	{
		return Util.nonNull(noise).noise(x, y);
	}
	
	public final double givePerlinNoise(final double x, final double y)
	{
		return this.givePerlinNoise(this.givePerlinNoise(), x, y);
	}
	
	public final double givePerlinNoise(final PerlinNoiseGenerator noise, final double x, final double y, final double z)
	{
		return Util.nonNull(noise).noise(x, y, z);
	}
	
	public final double givePerlinNoise(final double x, final double y, final double z)
	{
		return this.givePerlinNoise(this.givePerlinNoise(), x, y, z);
	}
	
	public final double givePerlinNoise(final PerlinNoiseGenerator noise, final double x, final int octaves, final double frequency, final double amplitude)
	{
		return Util.nonNull(noise).noise(x, octaves, frequency, amplitude);
	}
	
	public final double givePerlinNoise(final double x, final int octaves, final double frequency, final double amplitude)
	{
		return this.givePerlinNoise(this.givePerlinNoise(), x, octaves, frequency, amplitude);
	}
	
	public final double givePerlinNoise(final PerlinNoiseGenerator noise, final double x, final double y, final int octaves, final double frequency, final double amplitude)
	{
		return Util.nonNull(noise).noise(x, y, octaves, frequency, amplitude);
	}
	
	public final double givePerlinNoise(final double x, final double y, final int octaves, final double frequency, final double amplitude)
	{
		return this.givePerlinNoise(this.givePerlinNoise(), x, y, octaves, frequency, amplitude);
	}
	
	public final double givePerlinNoise(final PerlinNoiseGenerator noise, final double x, final double y, final double z, final int octaves, final double frequency, final double amplitude)
	{
		return Util.nonNull(noise).noise(x, y, z, octaves, frequency, amplitude);
	}
	
	public final double givePerlinNoise(final double x, final double y, final double z, final int octaves, final double frequency, final double amplitude)
	{
		return this.givePerlinNoise(this.givePerlinNoise(), x, y, z, octaves, frequency, amplitude);
	}
	
	public final double givePerlinNoise(final PerlinNoiseGenerator noise, final double x, final int octaves, final double frequency, final double amplitude, final boolean normalized)
	{
		return Util.nonNull(noise).noise(x, octaves, frequency, amplitude, normalized);
	}
	
	public final double givePerlinNoise(final double x, final int octaves, final double frequency, final double amplitude, final boolean normalized)
	{
		return this.givePerlinNoise(this.givePerlinNoise(), x, octaves, frequency, amplitude, normalized);
	}
	
	public final double givePerlinNoise(final PerlinNoiseGenerator noise, final double x, final double y, final int octaves, final double frequency, final double amplitude, final boolean normalized)
	{
		return Util.nonNull(noise).noise(x, y, octaves, frequency, amplitude, normalized);
	}
	
	public final double givePerlinNoise(final double x, final double y, final int octaves, final double frequency, final double amplitude, final boolean normalized)
	{
		return this.givePerlinNoise(this.givePerlinNoise(), x, y, octaves, frequency, amplitude, normalized);
	}
	
	public final double givePerlinNoise(final PerlinNoiseGenerator noise, final double x, final double y, final double z, final int octaves, final double frequency, final double amplitude, final boolean normalized)
	{
		return Util.nonNull(noise).noise(x, y, z, octaves, frequency, amplitude, normalized);
	}
	
	public final double givePerlinNoise(final double x, final double y, final double z, final int octaves, final double frequency, final double amplitude, final boolean normalized)
	{
		return this.givePerlinNoise(this.givePerlinNoise(), x, y, z, octaves, frequency, amplitude, normalized);
	}
	
	public final PerlinOctaveGenerator givePerlinOctave(final int octaves)
	{
		return new PerlinOctaveGenerator(this.giveRandom(), octaves);
	}
	
	public final SimplexOctaveGenerator giveSimplexOctave(final int octaves)
	{
		return new SimplexOctaveGenerator(this.giveRandom(), octaves);
	}
	
	public final double giveSimplexOctave(final SimplexOctaveGenerator octave, final double x, final double y, final double z, final double w, final double frequency, final double amplitude)
	{
		return Util.nonNull(octave).noise(x, y, z, w, frequency, amplitude);
	}
	
	public final double giveSimplexOctave(final SimplexOctaveGenerator octave, final double x, final double y, final double z, final double w, final double frequency, final double amplitude, final boolean normalized)
	{
		return Util.nonNull(octave).noise(x, y, z, w, frequency, amplitude, normalized);
	}
	
	public final double giveOctave(final OctaveGenerator octave, final double x, final double frequency, final double amplitude)
	{
		return Util.nonNull(octave).noise(x, frequency, amplitude);
	}
	
	public final double giveOctave(final OctaveGenerator octave, final double x, final double y, final double frequency, final double amplitude)
	{
		return Util.nonNull(octave).noise(x, y, frequency, amplitude);
	}
	
	public final double giveOctave(final OctaveGenerator octave, final double x, final double y, final double z, final double frequency, final double amplitude)
	{
		return Util.nonNull(octave).noise(x, y, z, frequency, amplitude);
	}
	
	public final double giveOctave(final OctaveGenerator octave, final double x, final double frequency, final double amplitude, final boolean normalized)
	{
		return Util.nonNull(octave).noise(x, frequency, amplitude, normalized);
	}
	
	public final double giveOctave(final OctaveGenerator octave, final double x, final double y, final double frequency, final double amplitude, final boolean normalized)
	{
		return Util.nonNull(octave).noise(x, y, frequency, amplitude, normalized);
	}
	
	public final double giveOctave(final OctaveGenerator octave, final double x, final double y, final double z, final double frequency, final double amplitude, final boolean normalized)
	{
		return Util.nonNull(octave).noise(x, y, z, frequency, amplitude, normalized);
	}
	
	public final Object giveOctaveNoiseGenerator(final int octaves)
	{
		return this.doInvokeTool("OctaveNoise", octaves);
	}
	
	public final double[] giveOctaveNoiseGenerate(final Object noise, final double[] array, final Vector offset, final Vector size, final Vector scale)
	{
		return (double[]) this.doInvokeTool("OctaveNoiseGenerate", Util.nonNull(noise), Util.nonNull(array), Util.nonNull(offset), Util.nonNull(size), Util.nonNull(scale));
	}
	
	public final double[] giveOctaveNoiseGenerate(final Object noise, final double[] array, final int offsetX, final int offsetY, final int offsetZ, final int sizeX, final int sizeY, final int sizeZ, final double scaleX, final double scaleY, final double scaleZ)
	{
		return (double[]) this.doInvokeTool("OctaveNoiseGenerate", Util.nonNull(noise), Util.nonNull(array), offsetX, offsetY, offsetZ, sizeX, sizeY, sizeZ, scaleX, scaleY, scaleZ);
	}
	
	public final double[] giveOctaveNoiseGenerate(final Object noise, final double[] array, final int offsetX, final int offsetZ, final int sizeX, final int sizeZ, final double scaleX, final double scaleZ, final double scaleNoise)
	{
		return (double[]) this.doInvokeTool("OctaveNoiseGenerate", Util.nonNull(noise), Util.nonNull(array), offsetX, offsetZ, sizeX, sizeZ, scaleX, scaleZ, scaleNoise);
	}
	
	public final double[] giveOctaveNoiseGenerate(final Object noise, final double[] array, final int offsetX, final int offsetZ, final int sizeX, final int sizeZ, final double scaleX, final double scaleZ)
	{
		return (double[]) this.doInvokeTool("OctaveNoiseGenerate", Util.nonNull(noise), Util.nonNull(array), offsetX, offsetZ, sizeX, sizeZ, scaleX, scaleZ);
	}
	
	public final Object givePerlinNoiseGenerator()
	{
		return this.doInvokeTool("PerlinNoise");
	}
	
	public final double[] givePerlinNoisePopulate(final Object noise, final double[] array, final Vector offset, final Vector size, final Vector scale, final double scaleNoise)
	{
		return (double[]) this.doInvokeTool("PerlinNoisePopulate", Util.nonNull(noise), Util.nonNull(array), Util.nonNull(offset), Util.nonNull(size), Util.nonNull(scale), scaleNoise);
	}
	
	public final double[] givePerlinNoisePopulate(final double[] array, final Vector offset, final Vector size, final Vector scale, final double scaleNoise)
	{
		return (double[]) this.doInvokeTool("PerlinNoisePopulate", Util.nonNull(array), Util.nonNull(offset), Util.nonNull(size), Util.nonNull(scale), scaleNoise);
	}
	
	public final double[] givePerlinNoisePopulate(final Object noise, final double[] array, final double offsetX, final double offsetY, final double offsetZ, final int sizeX, final int sizeY, final int sizeZ, final double scaleX, final double scaleY, final double scaleZ, final double scaleNoise)
	{
		return (double[]) this.doInvokeTool("PerlinNoisePopulate", Util.nonNull(noise), Util.nonNull(array), offsetX, offsetY, offsetZ, sizeX, sizeY, sizeZ, scaleX, scaleY, scaleZ, scaleNoise);
	}
	
	public final double[] givePerlinNoisePopulate(final double[] array, final double offsetX, final double offsetY, final double offsetZ, final int sizeX, final int sizeY, final int sizeZ, final double scaleX, final double scaleY, final double scaleZ, final double scaleNoise)
	{
		return (double[]) this.doInvokeTool("PerlinNoisePopulate", Util.nonNull(array), offsetX, offsetY, offsetZ, sizeX, sizeY, sizeZ, scaleX, scaleY, scaleZ, scaleNoise);
	}
	
	protected final Object doInvokeTool(final String method, final Object... parameters)
	{
		if(Util.hasUtil() && this.hasTool())
			try
			{
				return Util.giveUtil().doInvokeMethod("give" + Util.nonEmpty(method), this.giveTool(), parameters);
			}
			catch(final ReflectiveOperationException e)
			{
				e.printStackTrace();
			}
		return null;
	}
	
	@Override
	public Map<String, Object> giveProperties(final Map<String, Object> map)
	{
		map.put("random", this.giveRandom());
		map.put("tool", this.giveTool());
		return map;
	}
	
}
