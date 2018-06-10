
/**
 * 
 * Java New Functions
 * JNF
 * 1.1_06
 * 
 * © Bartosz Konkol
 * 
 * javax.jnf.lwjgl.Point3D
 * 2014-05-03 - 2014-05-05 [JNF 1.1_06]
 * 
 */

package javax.jnf.lwjgl;

import java.awt.Point;

/**
 * 
 * Support for LWJGL<br>Point 3D
 * 		<p>
 * Class of point 3D.
 * 
 * @since   1.1_06         <! PERMANENT >
 * @version 1.1_06         <! VARIABLE >
 * @author  Bartosz Konkol <! VARIABLE >
 *
 */

public class Point3D extends Point2D
{
	
	/**
	 * 
	 * Class of stores data the point 3D.
	 * 
	 * @param x <! VARIABLE >
	 * @param y <! VARIABLE >
	 * @param z <! VARIABLE >
	 * 
	 * @since   1.1_06         <! PERMANENT >
	 * @version 1.1_06         <! VARIABLE >
	 * @author  Bartosz Konkol <! VARIABLE >
	 * 
	 */
	public Point3D(float x, float y, float z)
	{
		super(x, y);
		this.z = z;
	}
	
	/**
	 * 
	 * Class of stores data the point 3D.
	 * 
	 * @param point <! VARIABLE >
	 * @param depth <! VARIABLE >
	 * 
	 * @since   1.1_06         <! PERMANENT >
	 * @version 1.1_06         <! VARIABLE >
	 * @author  Bartosz Konkol <! VARIABLE >
	 * 
	 */
	public Point3D(Point point, float depth)
	{
		super(point);
		this.z = depth;
	}
	
	/**
	 * 
	 * Class of stores data the point 3D.
	 * 
	 * @param point <! VARIABLE >
	 * @param depth <! VARIABLE >
	 * 
	 * @since   1.1_06         <! PERMANENT >
	 * @version 1.1_06         <! VARIABLE >
	 * @author  Bartosz Konkol <! VARIABLE >
	 * 
	 */
	public Point3D(org.lwjgl.util.Point point, float depth)
	{
		super(point);
		this.z = depth;
	}
	
	/**
	 * 
	 * Class of stores data the point 3D.
	 * 
	 * @param point <! VARIABLE >
	 * @param depth <! VARIABLE >
	 * 
	 * @since   1.1_06         <! PERMANENT >
	 * @version 1.1_06         <! VARIABLE >
	 * @author  Bartosz Konkol <! VARIABLE >
	 * 
	 */
	public Point3D(Point2D point, float depth)
	{
		super(point.getX(), point.getY());
		this.z = depth;
	}
	
	private float z;
	
	/**
	 * Specify the position Z.   <! VARIABLE >
	 * @since   1.1_06           <! PERMANENT >
	 * @version 1.1_06           <! VARIABLE >
	 * @author  Bartosz Konkol   <! VARIABLE >
	 */
	public final void setZ(float z)
	{
		this.z = z;
	}
	
	/**
	 * Return the position Z.   <! VARIABLE >
	 * @since   1.1_06          <! PERMANENT >
	 * @version 1.1_06          <! VARIABLE >
	 * @author  Bartosz Konkol  <! VARIABLE >
	 */
	public final float getZ()
	{
		return this.z;
	}
	
}
