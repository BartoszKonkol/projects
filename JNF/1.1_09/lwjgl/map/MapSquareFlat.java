
/**
 * 
 * Java New Functions
 * JNF
 * 1.1_05
 * 
 * © Bartosz Konkol
 * 
 * javax.jnf.lwjgl.map.MapSquareFlat
 * 2014-03-30 - 2014-03-31 [JNF 1.1_05]
 * 
 */

package javax.jnf.lwjgl.map;

import java.awt.Point;
import java.io.File;
import javax.jnf.lwjgl.Camera;
import javax.jnf.lwjgl.Generation;
import javax.jnf.lwjgl.Texture;
import javax.jnf.lwjgl.Util;
import javax.jnf.technical.maths.Conversion;

/**
 * 
 * Support for LWJGL<br>Map<br>Map Square Flat
 * 
 * @since   1.1_05         <! PERMANENT >
 * @version 1.1_05         <! VARIABLE >
 * @author  Bartosz Konkol <! VARIABLE >
 *
 */

public class MapSquareFlat extends Map
{
	
	/**
	 * File of texture of the walls of all the blocks. <! VARIABLE >
	 * @since   1.1_05                                 <! PERMANENT >
	 * @version 1.1_05                                 <! VARIABLE >
	 * @author  Bartosz Konkol                         <! VARIABLE >
	 */
	protected final File texture;
	
	/**
	 * 
	 * Class a square and a flat of the map.
	 * 
	 * @param camera   <! VARIABLE >
	 * @param navigate <! VARIABLE >
	 * @param size     <! VARIABLE >
	 * @param height   <! VARIABLE >
	 * @param texture  <! VARIABLE >
	 * 
	 * @since   1.1_05         <! PERMANENT >
	 * @version 1.1_05         <! VARIABLE >
	 * @author  Bartosz Konkol <! VARIABLE >
	 * 
	 */
	public MapSquareFlat(final Camera camera, final NavigateCamera navigate, final int size, final float height, File texture)
	{
		super(camera, navigate, size, height);
		this.texture = texture;
	}
	
	/**
	 * 
	 * Class a square and a flat of the map.
	 * 
	 * @param camera   <! VARIABLE >
	 * @param navigate <! VARIABLE >
	 * @param size     <! VARIABLE >
	 * @param height   <! VARIABLE >
	 * 
	 * @since   1.1_05         <! PERMANENT >
	 * @version 1.1_05         <! VARIABLE >
	 * @author  Bartosz Konkol <! VARIABLE >
	 * 
	 */
	public MapSquareFlat(final Camera camera, final NavigateCamera navigate, final int size, final float height)
	{
		this(camera, navigate, size, height, null);
	}
	
	/**
	 * The function of generation the terrain. <! VARIABLE >
	 * @since   1.1_05                         <! PERMANENT >
	 * @version 1.1_05                         <! VARIABLE >
	 * @author  Bartosz Konkol                 <! VARIABLE >
	 */@Override
	protected void doGenerateTerrain(final int size, final float height, final Point site)
	{
		if(this.texture != null)
			Generation.doGenerate(new Texture(this.texture)
			{
				/**
				 * The function of behavior of the elements that are to be textured. <! VARIABLE >
				 * @since   1.1_05                                                   <! PERMANENT >
				 * @version 1.1_05                                                   <! VARIABLE >
				 * @author  Bartosz Konkol                                           <! VARIABLE >
				 */@Override
				protected void doAction() throws Exception
				{
					doGenerate(size, height, site);
				}
			});
		else
			this.doGenerate(size, height, site);
	}
	
	/**
	 * The function of motion. <! VARIABLE >
	 * @since   1.1_05         <! PERMANENT >
	 * @version 1.1_05         <! VARIABLE >
	 * @author  Bartosz Konkol <! VARIABLE >
	 */@Override
	protected void doMotion(final Camera camera, final float height, final int size)
	{
		if(Conversion.changesInSign(camera.getPosition().getY()) < height + 1.5F)
			camera.getPosition().setY(camera.getPosition().getY() - 0.05F);
	}
	
	 /**
	 * The function of generation the terrain of regardless the existence of texture. <! VARIABLE >
	 * @since   1.1_05                                                                <! PERMANENT >
	 * @version 1.1_05                                                                <! VARIABLE >
	 * @author  Bartosz Konkol                                                        <! VARIABLE >
	 */
	protected void doGenerate(final int size, final float height, final Point site)
	{
		for(int lx = 0; lx < size; lx++)
			for(int lz = 0; lz < size; lz++)
			{
				final int x = Util.giveDoubleToInteger(site.getX() * size + lx);
				final int y = Math.round(height);
				final int z = Util.giveDoubleToInteger(site.getY() * size + lz);
				
				Generation.doGenerate(new BlockBasic(x, y, z));
			}
	}
	
}
