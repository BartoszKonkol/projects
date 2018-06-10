
/**
 * 
 * Java New Functions
 * JNF
 * 1.1_06
 * 
 * © Bartosz Konkol
 * 
 * javax.jnf.lwjgl.Point2D
 * 2014-05-03 - 2014-05-04 [JNF 1.1_06]
 * 
 */

package javax.jnf.lwjgl;

import java.awt.Point;

/**
 * 
 * Support for LWJGL<br>Point 2D
 * 		<p>
 * Class of point 2D.
 * 
 * @since   1.1_06         <! PERMANENT >
 * @version 1.1_06         <! VARIABLE >
 * @author  Bartosz Konkol <! VARIABLE >
 *
 */

public class Point2D extends LWJGL
{
	
	/**
	 * 
	 * Class of stores data the point 2D.
	 * 
	 * @param x <! VARIABLE >
	 * @param y <! VARIABLE >
	 * 
	 * @since   1.1_06         <! PERMANENT >
	 * @version 1.1_06         <! VARIABLE >
	 * @author  Bartosz Konkol <! VARIABLE >
	 * 
	 */
	public Point2D(float x, float y)
	{
		this.x = x;
		this.y = y;
	}
	
	/**
	 * 
	 * Class of stores data the point 2D.
	 * 
	 * @param point <! VARIABLE >
	 * 
	 * @since   1.1_06         <! PERMANENT >
	 * @version 1.1_06         <! VARIABLE >
	 * @author  Bartosz Konkol <! VARIABLE >
	 * 
	 */
	public Point2D(Point point)
	{
		this.x = (float) point.getX();
		this.y = (float) point.getY();
	}
	
	/**
	 * 
	 * Class of stores data the point 2D.
	 * 
	 * @param point <! VARIABLE >
	 * 
	 * @since   1.1_06         <! PERMANENT >
	 * @version 1.1_06         <! VARIABLE >
	 * @author  Bartosz Konkol <! VARIABLE >
	 * 
	 */
	public Point2D(org.lwjgl.util.Point point)
	{
		this.x = (float) point.getX();
		this.y = (float) point.getY();
	}
	
	private float x, y;
	
	/**
	 * Specify the position X.   <! VARIABLE >
	 * @since   1.1_06           <! PERMANENT >
	 * @version 1.1_06           <! VARIABLE >
	 * @author  Bartosz Konkol   <! VARIABLE >
	 */
	public final void setX(float x)
	{
		this.x = x;
	}
	
	/**
	 * Specify the position Y.   <! VARIABLE >
	 * @since   1.1_06           <! PERMANENT >
	 * @version 1.1_06           <! VARIABLE >
	 * @author  Bartosz Konkol   <! VARIABLE >
	 */
	public final void setY(float y)
	{
		this.y = y;
	}
	
	/**
	 * Return the position X.   <! VARIABLE >
	 * @since   1.1_06          <! PERMANENT >
	 * @version 1.1_06          <! VARIABLE >
	 * @author  Bartosz Konkol  <! VARIABLE >
	 */
	public final float getX()
	{
		return this.x;
	}
	
	/**
	 * Return the position Y.   <! VARIABLE >
	 * @since   1.1_06          <! PERMANENT >
	 * @version 1.1_06          <! VARIABLE >
	 * @author  Bartosz Konkol  <! VARIABLE >
	 */
	public final float getY()
	{
		return this.y;
	}
	
	/**
	 * Provides a point as class a {@link Point}. <! VARIABLE >
	 * @since   1.1_06                            <! PERMANENT >
	 * @version 1.1_06                            <! VARIABLE >
	 * @author  Bartosz Konkol                    <! VARIABLE >
	 */
	public final Point givePoint()
	{
		return new Point(Math.round(this.getX()), Math.round(this.getY()));
	}
	
}
