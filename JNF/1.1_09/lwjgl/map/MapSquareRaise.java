
/**
 * 
 * Java New Functions
 * JNF
 * 1.1_05
 * 
 * © Bartosz Konkol
 * 
 * javax.jnf.lwjgl.map.MapSquareRaise
 * 2014-03-31 - 2014-04-01 [JNF 1.1_05]
 * 2014-04-29 - 2014-04-29 [JNF 1.1_06]
 * 
 */

package javax.jnf.lwjgl.map;

import java.awt.Point;
import java.io.File;
import javax.jnf.lwjgl.*;
import javax.jnf.technical.constants.Mathematical;
import javax.jnf.technical.maths.Conversion;

/**
 * 
 * Support for LWJGL<br>Map<br>Map Square Raise
 * 
 * @since   1.1_05         <! PERMANENT >
 * @version 1.1_05         <! VARIABLE >
 * @author  Bartosz Konkol <! VARIABLE >
 *
 */

public class MapSquareRaise extends Map implements Heightmap
{
	
	private final float[][] heights;
	
	/**
	 * Guidelines the appearance of the standard block. <! VARIABLE >
	 * @since   1.1_05                                   <! PERMANENT >
	 * @version 1.1_05                                   <! VARIABLE >
	 * @author  Bartosz Konkol                           <! VARIABLE >
	 */
	protected final AppearanceBlock appearance;
	
	/**
	 * 
	 * Class a square and a raise of the map.
	 * 
	 * @param camera     <! VARIABLE >
	 * @param navigate   <! VARIABLE >
	 * @param size       <! VARIABLE >
	 * @param height     <! VARIABLE >
	 * @param heights    <! VARIABLE >
	 * @param appearance <! VARIABLE >
	 * 
	 * @since   1.1_05         <! PERMANENT >
	 * @version 1.1_05         <! VARIABLE >
	 * @author  Bartosz Konkol <! VARIABLE >
	 * 
	 */
	public MapSquareRaise(final Camera camera, final NavigateCamera navigate, final int size, final float height, final float[][] heights, final AppearanceBlock appearance)
	{
		super(camera, navigate, size, height);
		this.heights = heights;
		this.appearance = appearance;
	}
	
	/**
	 * The function of generation the terrain. <! VARIABLE >
	 * @since   1.1_05                         <! PERMANENT >
	 * @version 1.1_05                         <! VARIABLE >
	 * @author  Bartosz Konkol                 <! VARIABLE >
	 */@Override
	protected void doGenerateTerrain(final int size, final float height, final Point site)
	{
		for (int z = (int) Mathematical.mathematicalConstants("zero"); z < size; z++)
			for (int x = (int) Mathematical.mathematicalConstants("zero"); x < size + Mathematical.mathematicalConstants("one"); x++)
			{
				final int xMap = this.giveHeights().length / (int) Mathematical.mathematicalConstants("two") + Util.giveDoubleToInteger(site.getX()) * size + x;
				final int zMap = this.giveHeights().length / (int) Mathematical.mathematicalConstants("two") + Util.giveDoubleToInteger(site.getY()) * size + z;
				
				for(int y = Math.round(height); y <= Math.round(this.giveHeights()[zMap][xMap] + height); y++)
					if(this.appearance.color)
						Generation.doGenerate(new BlockExpand((float) (x - size / Mathematical.mathematicalConstants("two") + site.getX() * size), y, (float) (z - size / Mathematical.mathematicalConstants("two") + site.getY() * size), Color.class, this.appearance.sideColor, this.appearance.topColor, this.appearance.downColor));
					else if(this.appearance.file)
						Generation.doGenerate(new BlockExpand((float) (x - size / Mathematical.mathematicalConstants("two") + site.getX() * size), y, (float) (z - size / Mathematical.mathematicalConstants("two") + site.getY() * size), File.class, this.appearance.sideFile, this.appearance.topFile, this.appearance.downFile));
					else
						Generation.doGenerate(new BlockExpand((float) (x - size / Mathematical.mathematicalConstants("two") + site.getX() * size), y, (float) (z - size / Mathematical.mathematicalConstants("two") + site.getY() * size)));
			}
	}
	
	/**
	 * The function of motion. <! VARIABLE >
	 * @since   1.1_05         <! PERMANENT >
	 * @version 1.1_05         <! VARIABLE >
	 * @author  Bartosz Konkol <! VARIABLE >
	 */@Override
	protected void doMotion(final Camera camera, final float height, final int size)
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
		final double y = this.giveHeights()[Util.giveDoubleToInteger(z)][Util.giveDoubleToInteger(x)] + height;
		
		if(Conversion.changesInSign(camera.getPosition().getY()) < y + 1.5F)
			camera.getPosition().setY(camera.getPosition().getY() - 0.05F);
	}
	
	/**
	 * The function of heights. <! VARIABLE >
	 * @since   1.1_05          <! PERMANENT >
	 * @version 1.1_05          <! VARIABLE >
	 * @author  Bartosz Konkol  <! VARIABLE >
	 */@Override
	public float[][] giveHeights()
	{
		return this.heights;
	}
	
	 /**
	  * 
	  * Support for LWJGL<br>Map<br>Map Square Raise<br>Appearance Block
	  * 
	  * @since   1.1_05         <! PERMANENT >
	  * @version 1.1_06         <! VARIABLE >
	  * @author  Bartosz Konkol <! VARIABLE >
	  *
	  */
	public static final class AppearanceBlock extends LWJGL
	{
		
		/**
		 * 
		 * Class the appearance of block.
		 * 
		 * @param side <! VARIABLE >
		 * @param top  <! VARIABLE >
		 * @param down <! VARIABLE >
		 * 
		 * @since   1.1_05         <! PERMANENT >
		 * @version 1.1_05         <! VARIABLE >
		 * @author  Bartosz Konkol <! VARIABLE >
		 * 
		 */
		public AppearanceBlock(final File side, final File top, final File down)
		{
			this.downFile = down;
			this.sideFile = side;
			this.topFile = top;
			
			this.downColor = this.sideColor = this.topColor = null;

			this.color = false;
			this.file = true;
		}
		
		/**
		 * 
		 * Class the appearance of block.
		 * 
		 * @param file <! VARIABLE >
		 * 
		 * @since   1.1_05         <! PERMANENT >
		 * @version 1.1_05         <! VARIABLE >
		 * @author  Bartosz Konkol <! VARIABLE >
		 * 
		 */
		public AppearanceBlock(final File file)
		{
			this(file, file, file);
		}
		
		/**
		 * 
		 * Class the appearance of block.
		 * 
		 * @param side <! VARIABLE >
		 * @param top  <! VARIABLE >
		 * @param down <! VARIABLE >
		 * 
		 * @since   1.1_05         <! PERMANENT >
		 * @version 1.1_05         <! VARIABLE >
		 * @author  Bartosz Konkol <! VARIABLE >
		 * 
		 */
		public AppearanceBlock(final Color side, final Color top, final Color down)
		{
			this.downColor = down;
			this.sideColor = side;
			this.topColor = top;
			
			this.downFile = this.sideFile = this.topFile = null;

			this.color = true;
			this.file = false;
		}
		
		/**
		 * 
		 * Class the appearance of block.
		 * 
		 * @param color <! VARIABLE >
		 * 
		 * @since   1.1_05         <! PERMANENT >
		 * @version 1.1_05         <! VARIABLE >
		 * @author  Bartosz Konkol <! VARIABLE >
		 * 
		 */
		public AppearanceBlock(final Color color)
		{
			this(color, color, color);
		}
		
		/**
		 * 
		 * Class the appearance of block.
		 * 
		 * @since   1.1_05         <! PERMANENT >
		 * @version 1.1_05         <! VARIABLE >
		 * @author  Bartosz Konkol <! VARIABLE >
		 * 
		 */
		public AppearanceBlock()
		{
			this.downColor = this.sideColor = this.topColor = null;
			
			this.downFile = this.sideFile = this.topFile = null;
			
			this.color = this.file = false;
		}
		
		/**
		 * Type of data.           <! VARIABLE >
		 * @since   1.1_05         <! PERMANENT >
		 * @version 1.1_05         <! VARIABLE >
		 * @author  Bartosz Konkol <! VARIABLE >
		 */
		public final boolean file, color;
		/**
		 * Appearance of the block (a file texture). <! VARIABLE >
		 * @since   1.1_05                           <! PERMANENT >
		 * @version 1.1_05                           <! VARIABLE >
		 * @author  Bartosz Konkol                   <! VARIABLE >
		 */
		public final File sideFile, topFile, downFile;
		/**
		 * Appearance of the block (a color). <! VARIABLE >
		 * @since   1.1_05                    <! PERMANENT >
		 * @version 1.1_05                    <! VARIABLE >
		 * @author  Bartosz Konkol            <! VARIABLE >
		 */
		public final Color sideColor, topColor, downColor;
		
	}
	
}
