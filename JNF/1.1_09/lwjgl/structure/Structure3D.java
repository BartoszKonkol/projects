
/**
 * 
 * Java New Functions
 * JNF
 * 1.1_05
 * 
 * © Bartosz Konkol
 * 
 * javax.jnf.lwjgl.Keys
 * 2014-03-14 - 2014-03-16 [JNF 1.1_05]
 * 2014-05-10 - 2014-02-10 [JNF 1.1_06]
 * 
 */

package javax.jnf.lwjgl.structure;

import java.awt.Point;
import javax.jnf.lwjgl.*;

/**
 * 
 * Support for LWJGL<br>Structure<br>Structure 3D
 * 
 * @since   1.1_05         <! PERMANENT >
 * @version 1.1_06         <! VARIABLE >
 * @author  Bartosz Konkol <! VARIABLE >
 *
 */

public abstract class Structure3D extends Structure2D implements Structure
{
	
	/**
	 * 
	 * Class allows you to define the basic properties of the structure 3D.
	 * 
	 * @param points <! VARIABLE >
	 * @param depths <! VARIABLE >
	 * @param colors <! VARIABLE >
	 * 
	 * @since   1.1_05         <! PERMANENT >
	 * @version 1.1_06         <! VARIABLE >
	 * @author  Bartosz Konkol <! VARIABLE >
	 * 
	 * @see Structure2D
	 * @see Structure2D#Structure2D
	 * @see Structure
	 * 
	 * @deprecated Was replaced by '{@link #Structure3D(Point3D[], Color[]) Structure3D}'.
	 * 
	 */@Deprecated
	public Structure3D(final Point[] points, final float[] depths, final Color[] colors)
	{
		super(points, colors);
		this.setDepths(depths);
	}
	 
	 /**
	 * 
	 * Class allows you to define the basic properties of the structure 3D.
	 * 
	 * @param points <! VARIABLE >
	 * @param colors <! VARIABLE >
	 * 
	 * @since   1.1_06         <! PERMANENT >
	 * @version 1.1_06         <! VARIABLE >
	 * @author  Bartosz Konkol <! VARIABLE >
	 * 
	 * @see Structure2D
	 * @see Structure2D#Structure2D
	 * @see Structure
	 * 
	 */
	public Structure3D(final Point3D[] points, final Color[] colors)
	{
		super(points, colors);
		this.setPoints3D(points);
	}
	
	@Deprecated
	private float[] depths;
	private Point3D[] points;
	
	/**
	 * Specify the depths of the structure. <! VARIABLE >
	 * @since   1.1_05                      <! PERMANENT >
	 * @version 1.1_06                      <! VARIABLE >
	 * @author  Bartosz Konkol              <! VARIABLE >
	 * @deprecated Was replaced by '{@link #setPoints3D}'.
	 */@Deprecated
	public final Structure2D setDepths(final float[] depths)
	{
		final Point3D[] points = new Point3D[depths.length];
		for(int i = 0; i < points.length; i++)
			points[i] = new Point3D(this.getPoints2D()[i], depths[i]);
		this.setPoints3D(points);
		return this;
	}
	
	/**
	 * Return the depths of the structure. <! VARIABLE >
	 * @since   1.1_05                     <! PERMANENT >
	 * @version 1.1_06                     <! VARIABLE >
	 * @author  Bartosz Konkol             <! VARIABLE >
	 * @deprecated Was replaced by '{@link #getPoints3D}'.
	 */@Deprecated
	public final float[] getDepths()
	{
		return this.depths;
	}
	
	/**
	 * Specify the points of the structure. <! VARIABLE >
	 * @since   1.1_06                      <! PERMANENT >
	 * @version 1.1_06                      <! VARIABLE >
	 * @author  Bartosz Konkol              <! VARIABLE > 
	 */
	public final Structure3D setPoints3D(final Point3D[] points)
	{
		this.points = points;
		this.setPoints2D(points);
		final float[] depths = new float[points.length];
		for(int i = 0; i < points.length; i++)
			depths[i] = points[i].getZ();
		this.depths = depths;
		return this;
	}
	
	/**
	 * Return the points of the structure. <! VARIABLE >
	 * @since   1.1_06                     <! PERMANENT >
	 * @version 1.1_06                     <! VARIABLE >
	 * @author  Bartosz Konkol             <! VARIABLE >
	 */
	public final Point3D[] getPoints3D()
	{
		return this.points;
	}
	
}
