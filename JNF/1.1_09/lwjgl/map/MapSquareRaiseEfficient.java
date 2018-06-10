
/**
 * 
 * Java New Functions
 * JNF
 * 1.1_06
 * 
 * © Bartosz Konkol
 * 
 * javax.jnf.lwjgl.map.MapSquareRaiseEfficient
 * 2014-04-29 - 2014-05-03 [JNF 1.1_06]
 * 
 */

package javax.jnf.lwjgl.map;

import java.awt.Point;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javax.jnf.lwjgl.*;
import javax.jnf.technical.constants.Mathematical;
import javax.jnf.technical.maths.Conversion;

/**
 * 
 * Support for LWJGL<br>Map<br>Map Square Raise Efficient
 * 
 * @since   1.1_06         <! PERMANENT >
 * @version 1.1_06         <! VARIABLE >
 * @author  Bartosz Konkol <! VARIABLE >
 *
 */

public class MapSquareRaiseEfficient extends MapSquareRaise
{
	
	/**
	 * The maximum height of the map. <! VARIABLE >
	 * @since   1.1_06                <! PERMANENT >
	 * @version 1.1_06                <! VARIABLE >
	 * @author  Bartosz Konkol        <! VARIABLE >
	 */
	protected final int maxHeight;
	/**
	 * The values of corrections motion. <! VARIABLE >
	 * @since   1.1_06                   <! PERMANENT >
	 * @version 1.1_06                   <! VARIABLE >
	 * @author  Bartosz Konkol           <! VARIABLE >
	 */
	protected final float correctionUp, correctionDown, speedCorrections;
	/**
	 * Limiting the lower rim of chunk. <! VARIABLE >
	 * @since   1.1_06                  <! PERMANENT >
	 * @version 1.1_06                  <! VARIABLE >
	 * @author  Bartosz Konkol          <! VARIABLE >
	 */
	protected final boolean limitRim;
	
	/**
	 * 
	 * Class a square and a raise of efficient of the map.
	 * 
	 * @param camera            <! VARIABLE >
	 * @param navigate          <! VARIABLE >
	 * @param size              <! VARIABLE >
	 * @param minHeight         <! VARIABLE >
	 * @param maxHeight         <! VARIABLE >
	 * @param heights           <! VARIABLE >
	 * @param appearanceDefault <! VARIABLE >
	 * @param correctionUp      <! VARIABLE >
	 * @param correctionDown    <! VARIABLE >
	 * @param speedCorrections  <! VARIABLE >
	 * @param limitRim          <! VARIABLE >
	 * 
	 * @since   1.1_06         <! PERMANENT >
	 * @version 1.1_06         <! VARIABLE >
	 * @author  Bartosz Konkol <! VARIABLE >
	 * 
	 */
	public MapSquareRaiseEfficient(Camera camera, NavigateCamera navigate, int size, int minHeight, int maxHeight, float[][] heights, AppearanceBlock appearanceDefault, float correctionUp, float correctionDown, float speedCorrections, boolean limitRim)
	{
		super(camera, navigate, size, minHeight, heights, appearanceDefault);
		this.maxHeight = (int) (maxHeight + Mathematical.mathematicalConstants("one"));
		this.correctionUp = correctionUp;
		this.correctionDown = correctionDown;
		this.speedCorrections = speedCorrections;
		this.limitRim = limitRim;
	}
	
	/**
	 * 
	 * Class a square and a raise of efficient of the map.
	 * 
	 * @param camera            <! VARIABLE >
	 * @param navigate          <! VARIABLE >
	 * @param size              <! VARIABLE >
	 * @param minHeight         <! VARIABLE >
	 * @param maxHeight         <! VARIABLE >
	 * @param heights           <! VARIABLE >
	 * @param appearanceDefault <! VARIABLE >
	 * @param correctionUp      <! VARIABLE >
	 * @param correctionDown    <! VARIABLE >
	 * @param limitRim          <! VARIABLE >
	 * 
	 * @since   1.1_06         <! PERMANENT >
	 * @version 1.1_06         <! VARIABLE >
	 * @author  Bartosz Konkol <! VARIABLE >
	 * 
	 */
	public MapSquareRaiseEfficient(Camera camera, NavigateCamera navigate, int size, int minHeight, int maxHeight, float[][] heights, AppearanceBlock appearanceDefault, float correctionUp, float correctionDown, boolean limitRim)
	{
		this(camera, navigate, size, minHeight, maxHeight, heights, appearanceDefault, correctionUp, correctionDown, 0.05F, limitRim);
	}
	
	/**
	 * 
	 * Class a square and a raise of efficient of the map.
	 * 
	 * @param camera            <! VARIABLE >
	 * @param navigate          <! VARIABLE >
	 * @param size              <! VARIABLE >
	 * @param minHeight         <! VARIABLE >
	 * @param maxHeight         <! VARIABLE >
	 * @param heights           <! VARIABLE >
	 * @param appearanceDefault <! VARIABLE >
	 * @param limitRim          <! VARIABLE >
	 * 
	 * @since   1.1_06         <! PERMANENT >
	 * @version 1.1_06         <! VARIABLE >
	 * @author  Bartosz Konkol <! VARIABLE >
	 * 
	 */
	public MapSquareRaiseEfficient(Camera camera, NavigateCamera navigate, int size, int minHeight, int maxHeight, float[][] heights, AppearanceBlock appearanceDefault, boolean limitRim)
	{
		this(camera, navigate, size, minHeight, maxHeight, heights, appearanceDefault, 1.5F, (float) Mathematical.mathematicalConstants("zero"), limitRim);
	}
	
	/**
	 * 
	 * Class a square and a raise of efficient of the map.
	 * 
	 * @param camera            <! VARIABLE >
	 * @param navigate          <! VARIABLE >
	 * @param size              <! VARIABLE >
	 * @param minHeight         <! VARIABLE >
	 * @param maxHeight         <! VARIABLE >
	 * @param heights           <! VARIABLE >
	 * @param appearanceDefault <! VARIABLE >
	 * @param correctionUp      <! VARIABLE >
	 * @param correctionDown    <! VARIABLE >
	 * @param speedCorrections  <! VARIABLE >
	 * 
	 * @since   1.1_06         <! PERMANENT >
	 * @version 1.1_06         <! VARIABLE >
	 * @author  Bartosz Konkol <! VARIABLE >
	 * 
	 */
	public MapSquareRaiseEfficient(Camera camera, NavigateCamera navigate, int size, int minHeight, int maxHeight, float[][] heights, AppearanceBlock appearanceDefault, float correctionUp, float correctionDown, float speedCorrections)
	{
		this(camera, navigate, size, minHeight, maxHeight, heights, appearanceDefault, correctionUp, correctionDown, speedCorrections, false);
	}
	
	/**
	 * 
	 * Class a square and a raise of efficient of the map.
	 * 
	 * @param camera            <! VARIABLE >
	 * @param navigate          <! VARIABLE >
	 * @param size              <! VARIABLE >
	 * @param minHeight         <! VARIABLE >
	 * @param maxHeight         <! VARIABLE >
	 * @param heights           <! VARIABLE >
	 * @param appearanceDefault <! VARIABLE >
	 * @param correctionUp      <! VARIABLE >
	 * @param correctionDown    <! VARIABLE >
	 * 
	 * @since   1.1_06         <! PERMANENT >
	 * @version 1.1_06         <! VARIABLE >
	 * @author  Bartosz Konkol <! VARIABLE >
	 * 
	 */
	public MapSquareRaiseEfficient(Camera camera, NavigateCamera navigate, int size, int minHeight, int maxHeight, float[][] heights, AppearanceBlock appearanceDefault, float correctionUp, float correctionDown)
	{
		this(camera, navigate, size, minHeight, maxHeight, heights, appearanceDefault, correctionUp, correctionDown, 0.05F);
	}
	
	/**
	 * 
	 * Class a square and a raise of efficient of the map.
	 * 
	 * @param camera            <! VARIABLE >
	 * @param navigate          <! VARIABLE >
	 * @param size              <! VARIABLE >
	 * @param minHeight         <! VARIABLE >
	 * @param maxHeight         <! VARIABLE >
	 * @param heights           <! VARIABLE >
	 * @param appearanceDefault <! VARIABLE >
	 * 
	 * @since   1.1_06         <! PERMANENT >
	 * @version 1.1_06         <! VARIABLE >
	 * @author  Bartosz Konkol <! VARIABLE >
	 * 
	 */
	public MapSquareRaiseEfficient(Camera camera, NavigateCamera navigate, int size, int minHeight, int maxHeight, float[][] heights, AppearanceBlock appearanceDefault)
	{
		this(camera, navigate, size, minHeight, maxHeight, heights, appearanceDefault, 1.5F, (float) Mathematical.mathematicalConstants("zero"));
	}
	
	/**
	 * The function of generation the terrain. <! VARIABLE >
	 * @since   1.1_06                         <! PERMANENT >
	 * @version 1.1_06                         <! VARIABLE >
	 * @author  Bartosz Konkol                 <! VARIABLE >
	 */@Override
	protected void doGenerateTerrain(final int size, final float minHeight, final Point site)
	{
		final AppearanceBlock[][][] blocks = new AppearanceBlock[size][this.maxHeight + 1][size];
		final List<Integer> heightsMin = new ArrayList<Integer>();
		final List<Integer> heightsMax = new ArrayList<Integer>();
		
		for (int z = (int) Mathematical.mathematicalConstants("zero"); z < size; z++)
			for (int x = (int) Mathematical.mathematicalConstants("zero"); x < size; x++)
			{
				final int xMap = this.giveHeights().length / (int) Mathematical.mathematicalConstants("two") + Util.giveDoubleToInteger(site.getX()) * size + x;
				final int zMap = this.giveHeights().length / (int) Mathematical.mathematicalConstants("two") + Util.giveDoubleToInteger(site.getY()) * size + z;
				
				heightsMin.add(Math.round(this.giveHeights()[zMap][xMap]));
				
				for(int y = 0; y <= heightsMin.get((int) (heightsMin.size() - Mathematical.mathematicalConstants("one"))) && y < this.maxHeight; y++)
					blocks[x][y][z] = this.doSpecifyBlock(x, y, z, xMap, zMap, size, site);
			}
		
		for(int i = 0; i < this.giveHeights().length; i++)
			for(int j = 0; j < this.giveHeights()[i].length; j++)
				heightsMax.add(Math.round(this.giveHeights()[i][j]));
				
		
		Generation.doGenerate(new Chunk(blocks, (int) (site.getX() * size - size / Mathematical.mathematicalConstants("two")), (int) minHeight, (int) (site.getY() * size - size / Mathematical.mathematicalConstants("two")), heightsMin, heightsMax, this.limitRim));
	}
	
	/**
	 * The function of motion. <! VARIABLE >
	 * @since   1.1_06         <! PERMANENT >
	 * @version 1.1_06         <! VARIABLE >
	 * @author  Bartosz Konkol <! VARIABLE >
	 */@Override
	protected void doMotion(final Camera camera, final float minHeight, final int size)
	{
		float px = camera.getPosition().getX() % size + size / (float) Mathematical.mathematicalConstants("two");
		if(px < Mathematical.mathematicalConstants("zero"))
			px += size;
		while(px > size)
			px -= size;
		px = size - px;
		
		float pz = camera.getPosition().getZ() % size + size / (float) Mathematical.mathematicalConstants("two");
		if(pz < Mathematical.mathematicalConstants("zero"))
			pz += size;
		while(pz > size)
			pz -= size;
		pz = size - pz;
		
		final double x = this.giveHeights().length / Mathematical.mathematicalConstants("two") + this.giveCalculateChunk(camera.getPosition().getX()) * size + px;
		final double z = this.giveHeights().length / Mathematical.mathematicalConstants("two") + this.giveCalculateChunk(camera.getPosition().getZ()) * size + pz;
		final double y = this.giveHeights()[(int) (z + 0.5F)][(int) (x + 0.5F)] + minHeight;
		
		if(this.correctionUp > Mathematical.mathematicalConstants("one"))
			if(Conversion.changesInSign(camera.getPosition().getY()) < y + this.correctionUp)
				camera.getPosition().setY(camera.getPosition().getY() - this.speedCorrections);
		if(this.correctionDown > Mathematical.mathematicalConstants("one"))
			if(Conversion.changesInSign(camera.getPosition().getY()) > y + this.correctionDown)
				camera.getPosition().setY(camera.getPosition().getY() + this.speedCorrections);
			
	}
	 
	/**
	 * The specific blocks during the generation of specific height points. <! VARIABLE >
	 * @since   1.1_06                                                      <! PERMANENT >
	 * @version 1.1_06                                                      <! VARIABLE >
	 * @author  Bartosz Konkol                                              <! VARIABLE >
	 */
	protected AppearanceBlock doSpecifyBlock(final int x, final int y, final int z, final int xMap, final int zMap, final int size, final Point site)
	{
		return this.appearance;
	}
	
	/**
	 * 
	 * Support for LWJGL<br>Map<br>Map Square Raise Efficient<br>Chunk
	 * 
	 * @since   1.1_06         <! PERMANENT >
	 * @version 1.1_06         <! VARIABLE >
	 * @author  Bartosz Konkol <! VARIABLE >
	 *
	 */
	public static class Chunk extends LWJGL implements GenerateGL
	{
		
		/**
		 * Table the blocks.       <! VARIABLE >
		 * @since   1.1_06         <! PERMANENT >
		 * @version 1.1_06         <! VARIABLE >
		 * @author  Bartosz Konkol <! VARIABLE >
		 */
		protected final AppearanceBlock[][][] blocks;
		/**
		 * Starting positions the chunk. <! VARIABLE >
		 * @since   1.1_06               <! PERMANENT >
		 * @version 1.1_06               <! VARIABLE >
		 * @author  Bartosz Konkol       <! VARIABLE >
		 */
		protected final int posX, posY, posZ;
		/**
		 * Lists of extreme height of chunk. <! VARIABLE >
		 * @since   1.1_06                   <! PERMANENT >
		 * @version 1.1_06                   <! VARIABLE >
		 * @author  Bartosz Konkol           <! VARIABLE >
		 */
		protected final List<Integer> heightsMin, heightsMax;
		/**
		 * Data extremes height of chunk. <! VARIABLE >
		 * @since   1.1_06                <! PERMANENT >
		 * @version 1.1_06                <! VARIABLE >
		 * @author  Bartosz Konkol        <! VARIABLE >
		 */
		protected final int minHeightMin, maxHeightMin, minHeightMax, maxHeightMax;
		/**
		 * Limiting the lower rim of chunk. <! VARIABLE >
		 * @since   1.1_06                  <! PERMANENT >
		 * @version 1.1_06                  <! VARIABLE >
		 * @author  Bartosz Konkol          <! VARIABLE >
		 */
		protected final boolean limitRim;
		
		/**
		 * 
		 * Class the chunk.
		 * 
		 * @param blocks     <! VARIABLE >
		 * @param posX       <! VARIABLE >
		 * @param posY       <! VARIABLE >
		 * @param posZ       <! VARIABLE >
		 * @param heightsMin <! VARIABLE >
		 * @param heightsMax <! VARIABLE >
		 * @param limitRim   <! VARIABLE >
		 * 
		 * @since   1.1_06         <! PERMANENT >
		 * @version 1.1_06         <! VARIABLE >
		 * @author  Bartosz Konkol <! VARIABLE >
		 * 
		 */
		public Chunk(final AppearanceBlock[][][] blocks, final int posX, final int posY, final int posZ, final List<Integer> heightsMin, final List<Integer> heightsMax, final boolean limitRim)
		{
			this.blocks = blocks;
			this.posX = posX;
			this.posY = posY;
			this.posZ = posZ;
			this.heightsMax = heightsMax;
			this.heightsMin = heightsMin;
			this.limitRim = limitRim;

			final Integer[] arrayHeightsMin = this.heightsMin.toArray(new Integer[this.heightsMin.size()]);
			final Integer[] arrayHeightsMax = this.heightsMax.toArray(new Integer[this.heightsMax.size()]);
			int valueMinHeightMin = arrayHeightsMin[new Random().nextInt(arrayHeightsMin.length)];
			int valueMaxHeightMin = arrayHeightsMin[new Random().nextInt(arrayHeightsMin.length)];
			int valueMinHeightMax = arrayHeightsMax[new Random().nextInt(arrayHeightsMax.length)];
			int valueMaxHeightMax = arrayHeightsMax[new Random().nextInt(arrayHeightsMax.length)];
			for(int i = (int) Mathematical.mathematicalConstants("zero"); i < arrayHeightsMin.length; i++)
			{
				if(arrayHeightsMin[i] < valueMinHeightMin)
					valueMinHeightMin = arrayHeightsMin[i];
				if(arrayHeightsMin[i] > valueMaxHeightMin)
					valueMaxHeightMin = arrayHeightsMin[i];
			}
			for(int i = (int) Mathematical.mathematicalConstants("zero"); i < arrayHeightsMax.length; i++)
			{
				if(arrayHeightsMax[i] < valueMinHeightMax)
					valueMinHeightMax = arrayHeightsMax[i];
				if(arrayHeightsMax[i] > valueMaxHeightMax)
					valueMaxHeightMax = arrayHeightsMax[i];
			}
			this.minHeightMin = valueMinHeightMin;
			this.maxHeightMin = valueMaxHeightMin;
			this.minHeightMax = valueMinHeightMax;
			this.maxHeightMax = valueMaxHeightMax;
		}
		
		/**
		 * The function of generation the block. <! VARIABLE >
		 * @since   1.1_06                       <! PERMANENT >
		 * @version 1.1_06                       <! VARIABLE >
		 * @author  Bartosz Konkol               <! VARIABLE >
		 */
		public void doGenerateBlock(final int x, final int y, final int z, final AppearanceBlock block, final boolean top, final boolean side1, final boolean side2, final boolean side3, final boolean side4, final boolean down)
		{
			BlockExpand generator;
			
			if(block.color)
				generator = new BlockExpand(x + this.posX, y + this.posY, z + this.posZ, Color.class, block.sideColor, block.topColor, block.downColor);
			else if(block.file)
				generator = new BlockExpand(x + this.posX, y + this.posY, z + this.posZ, File.class, block.sideFile, block.topFile, block.downFile);
			else
				generator = new BlockExpand(x + this.posX, y + this.posY, z + this.posZ);
			
			generator.doGenerate(top, side1, side2, side3, side4, down);
		}
		
		/**
		 * The function of generation the chunk. <! VARIABLE >
		 * @since   1.1_06                       <! PERMANENT >
		 * @version 1.1_06                       <! VARIABLE >
		 * @author  Bartosz Konkol               <! VARIABLE >
		 */
		public void doGenerate()
		{
			final int xMin = (int) Mathematical.mathematicalConstants("zero");
			final int xMax = (int) (this.blocks.length - Mathematical.mathematicalConstants("one"));
			for(int x = xMin; x <= xMax; x++)
			{
				final int yMin = this.minHeightMin;
				final int yMax = (int) (this.blocks[x].length - Mathematical.mathematicalConstants("one"));
				for(int y = yMin; y <= yMax; y++)
				{
					final int zMin = (int) Mathematical.mathematicalConstants("zero");
					final int zMax = (int) (this.blocks[x][y].length - Mathematical.mathematicalConstants("one"));
					for(int z = zMin; z <= zMax; z++)
					{
						boolean top, side1, side2, side3, side4, down;
						top = down = false;
						side1 = side2 = side3 = side4 = true;
						
						if(y - Mathematical.mathematicalConstants("one") >= yMin)
							if(this.blocks[x][y - 1][z] == null)
								down = true;
						if(y + Mathematical.mathematicalConstants("one") <= yMax)
							if(this.blocks[x][y + 1][z] == null)
								top = true;
						if(z - Mathematical.mathematicalConstants("one") >= zMin)
							if(this.blocks[x][y][z - 1] != null)
								side1 = false;
						if(z + Mathematical.mathematicalConstants("one") <= zMax)
							if(this.blocks[x][y][z + 1] != null)
								side2 = false;
						if(x - Mathematical.mathematicalConstants("one") >= xMin)
							if(this.blocks[x - 1][y][z] != null)
								side3 = false;
						if(x + Mathematical.mathematicalConstants("one") <= xMax)
							if(this.blocks[x + 1][y][z] != null)
								side4 = false;
						
						if(this.limitRim && y == yMin)
							side1 = side2 = side3 = side4 = false;
						
						if(blocks[x][y][z] != null && (top || side1 || side2 || side3 || side4 || down))
							this.doGenerateBlock(x, y, z, blocks[x][y][z], top, side1, side2, side3, side4, down);
					}
				}
			}
		}

		/**
		 * The function of generating. <! VARIABLE >
		 * @since   1.1_06             <! PERMANENT >
		 * @version 1.1_06             <! VARIABLE >
		 * @author  Bartosz Konkol     <! VARIABLE >
		 */@Override
		public void doGenerateGL(Generation generation)
		{
			this.doGenerate();
		}
		
	}
	
}
