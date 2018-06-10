
/**
 * 
 * Java New Functions
 * JNF
 * 1.1_05
 * 
 * © Bartosz Konkol
 * 
 * javax.jnf.lwjgl.map.MapThinRaise
 * 2014-03-27 - 2014-03-28 [JNF 1.1_05]
 * 
 */

package javax.jnf.lwjgl.map;

import java.awt.Point;
import javax.jnf.lwjgl.*;
import javax.jnf.technical.constants.Mathematical;
import javax.jnf.technical.maths.Conversion;
import static org.lwjgl.opengl.GL11.*;

/**
 * 
 * Support for LWJGL<br>Map<br>Map Thin Raise
 * 
 * @since   1.1_05         <! PERMANENT >
 * @version 1.1_05         <! VARIABLE >
 * @author  Bartosz Konkol <! VARIABLE >
 *
 */

public class MapThinRaise extends Map implements Heightmap
{
	
	private final float[][] heights;
	
	/**
	 * 
	 * Class a thin and a raise of the map.
	 * 
	 * @param camera   <! VARIABLE >
	 * @param navigate <! VARIABLE >
	 * @param size     <! VARIABLE >
	 * @param height   <! VARIABLE >
	 * @param heights  <! VARIABLE >
	 * 
	 * @since   1.1_05         <! PERMANENT >
	 * @version 1.1_05         <! VARIABLE >
	 * @author  Bartosz Konkol <! VARIABLE >
	 * 
	 */
	public MapThinRaise(final Camera camera, final NavigateCamera navigate, final int size, final float height, final float[][] heights)
	{
		super(camera, navigate, size, height);
		this.heights = heights;
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
		{
			glBegin(GL_TRIANGLE_STRIP);
			
			for (int x = (int) Mathematical.mathematicalConstants("zero"); x < size + Mathematical.mathematicalConstants("one"); x++)
			{
				final int xMap = this.giveHeights().length / (int) Mathematical.mathematicalConstants("two") + Util.giveDoubleToInteger(site.getX()) * size + x;
				final int zMap = this.giveHeights().length / (int) Mathematical.mathematicalConstants("two") + Util.giveDoubleToInteger(site.getY()) * size + z;
				
				this.doSpecificAction(x, this.giveHeights()[zMap][xMap], z, size, site);
				
				glVertex3d(	x - size / Mathematical.mathematicalConstants("two") + site.getX() * size,	this.giveHeights()[zMap][xMap] + height,																		z - size / Mathematical.mathematicalConstants("two") + site.getY() * size												);
				glVertex3d(	x - size / Mathematical.mathematicalConstants("two") + site.getX() * size,	this.giveHeights()[Util.giveDoubleToInteger(zMap + Mathematical.mathematicalConstants("one"))][xMap] + height,	z + Mathematical.mathematicalConstants("one") - size / Mathematical.mathematicalConstants("two") + site.getY() * size	);
			}
			
			glEnd();
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
		
		if(Conversion.changesInSign(camera.getPosition().getY()) < y + Mathematical.mathematicalConstants("one"))
			camera.getPosition().setY(camera.getPosition().getY() - 0.05F);
	}
	
	/**
	* The specific action during the generation of specific height points. <! VARIABLE >
	* @since   1.1_05                                                      <! PERMANENT >
	* @version 1.1_05                                                      <! VARIABLE >
	* @author  Bartosz Konkol                                              <! VARIABLE >
	*/
	protected void doSpecificAction(final float x, final float y, final float z, final int size, final Point site){}
	
	/**
	* The function of heights. <! VARIABLE >
	* @since   1.1_05          <! PERMANENT >
	* @version 1.1_05          <! VARIABLE >
	* @author  Bartosz Konkol  <! VARIABLE >
	*/@Override
	public final float[][] giveHeights()
	{
		return this.heights;
	}
	
}
