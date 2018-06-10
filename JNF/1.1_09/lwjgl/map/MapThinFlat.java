
/**
 * 
 * Java New Functions
 * JNF
 * 1.1_05
 * 
 * © Bartosz Konkol
 * 
 * javax.jnf.lwjgl.map.MapThinFlat
 * 2014-03-26 - 2014-03-28 [JNF 1.1_05]
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
 * Support for LWJGL<br>Map<br>Map Thin Flat
 * 
 * @since   1.1_05         <! PERMANENT >
 * @version 1.1_05         <! VARIABLE >
 * @author  Bartosz Konkol <! VARIABLE >
 *
 */

public class MapThinFlat extends Map
{
	
	/**
	 * 
	 * Class a thin and a flat of the map.
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
	public MapThinFlat(final Camera camera, final NavigateCamera navigate, final int size, final float height)
	{
		super(camera, navigate, size, height);
	}
	
	/**
	 * The function of generation the terrain. <! VARIABLE >
	 * @since   1.1_05                         <! PERMANENT >
	 * @version 1.1_05                         <! VARIABLE >
	 * @author  Bartosz Konkol                 <! VARIABLE >
	 */@Override
	protected void doGenerateTerrain(final int size, final float height, final Point site)
	{
		glBegin(GL_QUADS);
		glVertex3d(	site.getX() * size + size / Mathematical.mathematicalConstants("two"),	height,	site.getY() * size - size / Mathematical.mathematicalConstants("two")	);
		glVertex3d(	site.getX() * size + size / Mathematical.mathematicalConstants("two"),	height,	site.getY() * size + size / Mathematical.mathematicalConstants("two")	);
		glVertex3d(	site.getX() * size - size / Mathematical.mathematicalConstants("two"),	height,	site.getY() * size + size / Mathematical.mathematicalConstants("two")	);
		glVertex3d(	site.getX() * size - size / Mathematical.mathematicalConstants("two"),	height,	site.getY() * size - size / Mathematical.mathematicalConstants("two")	);
		glEnd();
	}
	
	 /**
	 * The function of motion. <! VARIABLE >
	 * @since   1.1_05         <! PERMANENT >
	 * @version 1.1_05         <! VARIABLE >
	 * @author  Bartosz Konkol <! VARIABLE >
	 */@Override
	protected void doMotion(final Camera camera, final float height, final int size)
	{
		if(Conversion.changesInSign(camera.getPosition().getY()) < height + Mathematical.mathematicalConstants("one"))
			camera.getPosition().setY(camera.getPosition().getY() - 0.05F);
	}
	
}
