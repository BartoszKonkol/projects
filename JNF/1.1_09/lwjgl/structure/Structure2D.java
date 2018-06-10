
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
 * 2014-04-27 - 2014-05-10 [JNF 1.1_06]
 * 
 */

package javax.jnf.lwjgl.structure;

import java.awt.Point;
import javax.jnf.lwjgl.*;

/**
 * 
 * Support for LWJGL<br>Structure<br>Structure 2D
 * 
 * @since   1.1_05         <! PERMANENT >
 * @version 1.1_06         <! VARIABLE >
 * @author  Bartosz Konkol <! VARIABLE >
 *
 */

public abstract class Structure2D extends LWJGL implements Structure
{
	
	/**
	 * 
	 * Class allows you to define the basic properties of the structure 2D.
	 * 
	 * @param points <! VARIABLE >
	 * @param colors <! VARIABLE >
	 * 
	 * @since   1.1_05         <! PERMANENT >
	 * @version 1.1_06         <! VARIABLE >
	 * @author  Bartosz Konkol <! VARIABLE >
	 * 
	 * @see Structure
	 * 
	 * @deprecated Was replaced by '{@link #Structure2D(Point2D[], Color[]) Structure2D}'.
	 * 
	 */@Deprecated
	public Structure2D(final Point[] points, final Color[] colors)
	{
		this.setPoints(points);
		this.setColors(colors);
	}
	 
	 /**
	 * 
	 * Class allows you to define the basic properties of the structure 2D.
	 * 
	 * @param points <! VARIABLE >
	 * @param colors <! VARIABLE >
	 * 
	 * @since   1.1_06         <! PERMANENT >
	 * @version 1.1_06         <! VARIABLE >
	 * @author  Bartosz Konkol <! VARIABLE >
	 * 
	 * @see Structure
	 * 
	 */
	public Structure2D(final Point2D[] points, final Color[] colors)
	{
		this.setPoints2D(points);
		this.setColors(colors);
	}
	
	private Point2D[] points;
	private Color[] colors;
	
	/**
	 * Specify the points of the structure. <! VARIABLE >
	 * @since   1.1_05                      <! PERMANENT >
	 * @version 1.1_06                      <! VARIABLE >
	 * @author  Bartosz Konkol              <! VARIABLE >
	 * @deprecated Was replaced by '{@link #setPoints2D}'.
	 */@Deprecated
	public final Structure2D setPoints(final Point[] points)
	{
		final Point2D[] points2D = new Point2D[points.length];
		for(int i = 0; i < points2D.length; i++)
			points2D[i] = new Point2D(points[i]);
		this.points = points2D;
		return this;
	}
	
	/**
	 * Specify the points of the structure. <! VARIABLE >
	 * @since   1.1_06                      <! PERMANENT >
	 * @version 1.1_06                      <! VARIABLE >
	 * @author  Bartosz Konkol              <! VARIABLE > 
	 */
	public final Structure2D setPoints2D(final Point2D[] points)
	{
		this.points = points;
		return this;
	}
	
	/**
	 * Return the points of the structure. <! VARIABLE >
	 * @since   1.1_05                     <! PERMANENT >
	 * @version 1.1_06                     <! VARIABLE >
	 * @author  Bartosz Konkol             <! VARIABLE >
	 * @deprecated Was replaced by '{@link #getPoints2D}'.
	 */@Deprecated
	public final Point[] getPoints()
	{
		final Point[] points = new Point[this.points.length];
		for(int i = 0; i < points.length; i++)
			points[i] = this.points[i].givePoint();
		return points;
	}
	
	/**
	 * Return the points of the structure. <! VARIABLE >
	 * @since   1.1_06                     <! PERMANENT >
	 * @version 1.1_06                     <! VARIABLE >
	 * @author  Bartosz Konkol             <! VARIABLE >
	 */
	public final Point2D[] getPoints2D()
	{
		return this.points;
	}
	
	/**
	 * Specify the colors of the structure. <! VARIABLE >
	 * @since   1.1_05                      <! PERMANENT >
	 * @version 1.1_05                      <! VARIABLE >
	 * @author  Bartosz Konkol              <! VARIABLE >
	 */
	public final Structure2D setColors(final Color[] colors)
	{
		this.colors = colors;
		return this;
	}
	
	/**
	 * Return the colors of the structure. <! VARIABLE >
	 * @since   1.1_05                     <! PERMANENT >
	 * @version 1.1_05                     <! VARIABLE >
	 * @author  Bartosz Konkol             <! VARIABLE >
	 */
	public final Color[] getColors()
	{
		return this.colors;
	}
	
	/**
	 * The function of generating. <! VARIABLE >
	 * @since   1.1_05             <! PERMANENT >
	 * @version 1.1_05             <! VARIABLE >
	 * @author  Bartosz Konkol     <! VARIABLE >
	 */@Override
	public final void doGenerateGL(final Generation generation)
	{
		 this.doGenerate();
	}
	
}
