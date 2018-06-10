
/**
 * 
 * Java New Functions
 * JNF
 * 1.1_05
 * 
 * © Bartosz Konkol
 * 
 * javax.jnf.lwjgl.map.DiamondSquare
 * 2014-03-27 - 2014-03-27 [JNF 1.1_05]
 * 2014-04-27 - 2014-04-27 [JNF 1.1_06]
 * 
 */

package javax.jnf.lwjgl.map;

import java.util.Random;
import javax.jnf.lwjgl.Color;
import javax.jnf.lwjgl.LWJGL;
import javax.jnf.lwjgl.Util;
import javax.jnf.technical.maths.Automatic;

/**
 * 
 * Support for LWJGL<br>Map<br>Diamond Square
 * 		<p>
 * Class of Diamond-Square algorithm.
 * 
 * @since   1.1_05                                                <! PERMANENT >
 * @version 1.1_06                                                <! VARIABLE >
 * @author  Bartosz Konkol                                        <! VARIABLE >
 * @author  Tomek Gubala / tomek@vgtworld.pl / http://vgtworld.pl <! VARIABLE >
 *
 */

public final class DiamondSquare extends LWJGL
{

	private final int contrast, heightMax, heightMin, size;
	private final Random random;
	
	/**
	 * 
	 * Management class of generating the height of points by the algorithm Diamond-Square.
	 * 
	 * @param size      <! VARIABLE >
	 * @param heightMax <! VARIABLE >
	 * @param heightMin <! VARIABLE >
	 * @param contrast  <! VARIABLE >
	 * @param seed      <! VARIABLE >
	 * 
	 * @since   1.1_05         <! PERMANENT >
	 * @version 1.1_05         <! VARIABLE >
	 * @author  Bartosz Konkol <! VARIABLE >
	 * 
	 */
	public DiamondSquare(final int size, final int heightMax, final int heightMin, final int contrast, final Random random)
	{
		this.contrast = contrast;
		this.heightMax = heightMax;
		this.heightMin = heightMin;
		this.random = random;
		this.size = size;
	}
	
	/**
	 * 
	 * Management class of generating the height of points by the algorithm Diamond-Square.
	 * 
	 * @param size      <! VARIABLE >
	 * @param heightMax <! VARIABLE >
	 * @param heightMin <! VARIABLE >
	 * @param seed      <! VARIABLE >
	 * 
	 * @since   1.1_05         <! PERMANENT >
	 * @version 1.1_05         <! VARIABLE >
	 * @author  Bartosz Konkol <! VARIABLE >
	 * 
	 */
	public DiamondSquare(final int size, final int heightMax, final int heightMin, final Random random)
	{
		this(size, heightMax, heightMin, 0x50, random);
	}
	
	/**
	 * 
	 * Management class of generating the height of points by the algorithm Diamond-Square.
	 * 
	 * @param size <! VARIABLE >
	 * @param seed <! VARIABLE >
	 * 
	 * @since   1.1_05         <! PERMANENT >
	 * @version 1.1_05         <! VARIABLE >
	 * @author  Bartosz Konkol <! VARIABLE >
	 * 
	 */
	public DiamondSquare(final int size, final Random random)
	{
		this(size, Color.MAX_VALUE_COLOR, Color.MIN_VALUE, random);
	}
	
	/**
	 * 
	 * Management class of generating the height of points by the algorithm Diamond-Square.
	 * 
	 * @param size      <! VARIABLE >
	 * @param heightMax <! VARIABLE >
	 * @param heightMin <! VARIABLE >
	 * @param contrast  <! VARIABLE >
	 * 
	 * @since   1.1_05         <! PERMANENT >
	 * @version 1.1_05         <! VARIABLE >
	 * @author  Bartosz Konkol <! VARIABLE >
	 * 
	 */
	public DiamondSquare(final int size, final int heightMax, final int heightMin, final int contrast)
	{
		this(size, heightMax, heightMin, contrast, new Random());
	}
	
	/**
	 * 
	 * Management class of generating the height of points by the algorithm Diamond-Square.
	 * 
	 * @param size      <! VARIABLE >
	 * @param heightMax <! VARIABLE >
	 * @param heightMin <! VARIABLE >
	 * 
	 * @since   1.1_05         <! PERMANENT >
	 * @version 1.1_05         <! VARIABLE >
	 * @author  Bartosz Konkol <! VARIABLE >
	 * 
	 */
	public DiamondSquare(final int size, final int heightMax, final int heightMin)
	{
		this(size, heightMax, heightMin, 0x50);
	}
	
	/**
	 * 
	 * Management class of generating the height of points by the algorithm Diamond-Square.
	 * 
	 * @param size     <! VARIABLE >
	 * @param contrast <! VARIABLE >
	 * 
	 * @since   1.1_05         <! PERMANENT >
	 * @version 1.1_05         <! VARIABLE >
	 * @author  Bartosz Konkol <! VARIABLE >
	 * 
	 */
	public DiamondSquare(final int size, final int contrast)
	{
		this(size, Color.MAX_VALUE_COLOR, Color.MIN_VALUE, contrast);
	}
	
	/**
	 * 
	 * Management class of generating the height of points by the algorithm Diamond-Square.
	 * 
	 * @param size <! VARIABLE >
	 * 
	 * @since   1.1_05         <! PERMANENT >
	 * @version 1.1_05         <! VARIABLE >
	 * @author  Bartosz Konkol <! VARIABLE >
	 * 
	 */
	public DiamondSquare(final int size)
	{
		this(size, 0x50);
	}
	
	/**
	 * 
	 * Management class of generating the height of points by the algorithm Diamond-Square.
	 * 
	 * @since   1.1_05         <! PERMANENT >
	 * @version 1.1_05         <! VARIABLE >
	 * @author  Bartosz Konkol <! VARIABLE >
	 * 
	 */
	public DiamondSquare()
	{
		this(Util.givePowerOfTwo(Util.givePowerOfTwo(3)));
	}
	
	private float randomHeightChange = 0;
	private float[][] area = null;
	
	/**
	 * javax.jnf.lwjgl.map.DiamondSquare.giveHeightPoints()
	 * @action <! VARIABLE >
	 * Generating the points height of Diamond-Square algorithm.
	 * @return <! VARIABLE >
	 *          Returns the generated the height points. <br>
	 * [{{float, ...}, {float, ...}}]
	 * 
	 * @since   1.1_05                                                <! PERMANENT >
	 * @version 1.1_05                                                <! VARIABLE >
	 * @author  Bartosz Konkol                                        <! VARIABLE >
	 * @author  Tomek Gubala / tomek@vgtworld.pl / http://vgtworld.pl <! VARIABLE >
	 */
	public float[][] giveHeightPoints()
	{
		int areaSize = 0;
		int baseSize = 1;
		int iterations = 0;
		while (baseSize + 1 < this.size)
		{
			baseSize *= 2;
			++iterations;
		}
		areaSize = baseSize + 1;
		this.area = new float[areaSize][areaSize];
		this.area[0][0] = this.giveRandomHeight();
		this.area[0][areaSize - 1] = this.giveRandomHeight();
		this.area[areaSize - 1][0] = this.giveRandomHeight();
		this.area[areaSize - 1][areaSize - 1] = this.giveRandomHeight();
		this.randomHeightChange = areaSize;
		for(int i = 1; i <= iterations; ++i)
			this.doCalculateIteration(i);
		if (this.area.length > this.size)
		{
			final float[][] newArea = new float[this.size][this.size];
			for (int i = 0; i < this.size; ++i)
				for (int j = 0; j < this.size; ++j)
					newArea[i][j] = this.area[i][j];
			this.area = newArea;
		}
		return this.area;
	}
	
	private void doCalculateIteration(int iteration)
	{
		final int baseSize = this.area.length - 1;
		int step = baseSize;
		for (int i = 1; i < iteration; ++i)
			step = step / 2;
		int x, y;
		for(x = step / 2; x <= baseSize; x += step)
			for(y = step / 2; y <= baseSize; y += step)
				this.doCalculateDiamond(x, y, step / 2);
		for(x = step / 2; x <= baseSize; x += step)
			for(y = step / 2; y <= baseSize; y += step)
				this.doCalculateSquares(x, y, step / 2);
		this.randomHeightChange = this.randomHeightChange / 2;
	}
	
	private void doCalculateSquares(int x, int y, int radius)
	{
		int leftX, rightX, topY, bottomY;
		leftX = x - radius;
		rightX = x + radius;
		topY = y - radius;
		bottomY = y + radius;
		if(topY == 0)
			this.doCalculateSquare(x, topY, radius);
		this.doCalculateSquare(rightX, y, radius);
		this.doCalculateSquare(x, bottomY, radius);
		if(leftX == 0)
			this.doCalculateSquare(leftX, y, radius);
	}
	
	private void doCalculateSquare(int x, int y, int radius)
	{
		float value = 0;
		int leftX, rightX, topY, bottomY;
		final int size = this.area.length;
		int count = 0;
		leftX = x - radius;
		rightX = x + radius;
		topY = y - radius;
		bottomY = y + radius;
		if (topY >= 0)
		{
			++count;
			value += this.area[x][topY];
		}
		if (bottomY < size)
		{
			++count;
			value += this.area[x][bottomY];
		}
		if (leftX >= 0)
		{
			++count;
			value += this.area[leftX][y];
		}
		if (rightX < size)
		{
			++count;
			value += this.area[rightX][y];
		}
		value = value / count;
		if (value > this.heightMax)
			value = this.heightMax;
		if (value < this.heightMin)
			value = this.heightMin;
		this.area[x][y] = value;
	}
	
	private void doCalculateDiamond(int x, int y, int radius)
	{
		float value = 0;
		int leftX, rightX, topY, bottomY;
		leftX = x - radius;
		rightX = x + radius;
		topY = y - radius;
		bottomY = y + radius;
		value += this.area[leftX][topY];
		value += this.area[rightX][topY];
		value += this.area[leftX][bottomY];
		value += this.area[rightX][bottomY];
		value = value / 4;
		final float change = (float) (this.randomHeightChange * (this.random.nextFloat() * 2 - 1) * Automatic.quotient(this.contrast, 100));
		value = value + change;
		if (value > this.heightMax)
			value = this.heightMax;
		if (value < this.heightMin)
			value = this.heightMin;
		this.area[x][y] = value;
	}
	
	private float giveRandomHeight()
	{
		final int amplitude = this.heightMax - this.heightMin;
		double rand = this.random.nextDouble() * amplitude;
		rand += this.heightMin;
		return (float) rand;
	}
	
}
