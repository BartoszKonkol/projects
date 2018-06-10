
/**
 * 
 * Java New Functions
 * JNF
 * 1.1_05
 * 
 * © Bartosz Konkol
 * 
 * javax.jnf.lwjgl.GenerateGL
 * 2014-03-16 - 2014-03-17 [JNF 1.1_05]
 * 2014-05-10 - 2014-02-10 [JNF 1.1_06]
 * 
 */

package javax.jnf.lwjgl.structure;

import java.awt.Point;
import javax.jnf.lwjgl.*;
import javax.jnf.technical.constants.Mathematical;
import static org.lwjgl.opengl.GL11.*;

/**
 * 
 * Support for LWJGL<br>Structure<br>Square
 * 		<p>
 * Class of generating the square.
 * 
 * @since   1.1_05         <! PERMANENT >
 * @version 1.1_06         <! VARIABLE >
 * @author  Bartosz Konkol <! VARIABLE >
 *
 */

public class Square extends Structure2D implements RegularShape
{
	
	private final float lengthSide;
	
	/**
	 * 
	 * Class a management of generating the square.
	 * 
	 * @param point      <! VARIABLE >
	 * @param lengthSide <! VARIABLE >
	 * @param colors     <! VARIABLE >
	 * 
	 * @since   1.1_05         <! PERMANENT >
	 * @version 1.1_06         <! VARIABLE >
	 * @author  Bartosz Konkol <! VARIABLE >
	 * 
	 * @deprecated Was replaced by '{@link #Square(Point2D, float, Color[]) Square}'.
	 * 
	 */@Deprecated
	public Square(Point point, float lengthSide, Color... colors)
	{
		super(new Point[]{point}, colors);
		this.lengthSide = lengthSide;
	}
		
	/**
	 * 
	 * Class a management of generating the square.
	 * 
	 * @param point      <! VARIABLE >
	 * @param lengthSide <! VARIABLE >
	 * @param colors     <! VARIABLE >
	 * 
	 * @since   1.1_06         <! PERMANENT >
	 * @version 1.1_06         <! VARIABLE >
	 * @author  Bartosz Konkol <! VARIABLE >
	 * 
	 */
	public Square(Point2D point, float lengthSide, Color... colors)
	{
		super(new Point2D[]{point}, colors);
		this.lengthSide = lengthSide;
	}
	
	/**
	 * 
	 * Class a management of generating the square.
	 * 
	 * @param point      <! VARIABLE >
	 * @param lengthSide <! VARIABLE >
	 * @param color      <! VARIABLE >
	 * 
	 * @since   1.1_05         <! PERMANENT >
	 * @version 1.1_06         <! VARIABLE >
	 * @author  Bartosz Konkol <! VARIABLE >
	 * 
	 * @deprecated Was replaced by '{@link #Square(Point2D, float, Color) Square}'.
	 * 
	 */@Deprecated
	public Square(Point point, float lengthSide, Color color)
	{
		this(point, lengthSide, color != null ? new Color[]{color} : null);
	}
	
	/**
	 * 
	 * Class a management of generating the square.
	 * 
	 * @param point      <! VARIABLE >
	 * @param lengthSide <! VARIABLE >
	 * @param color      <! VARIABLE >
	 * 
	 * @since   1.1_06         <! PERMANENT >
	 * @version 1.1_06         <! VARIABLE >
	 * @author  Bartosz Konkol <! VARIABLE >
	 * 
	 */
	public Square(Point2D point, float lengthSide, Color color)
	{
		this(point, lengthSide, color != null ? new Color[]{color} : null);
	}
	
	/**
	 * 
	 * Class a management of generating the square.
	 * 
	 * @param point      <! VARIABLE >
	 * @param lengthSide <! VARIABLE >
	 * 
	 * @since   1.1_05         <! PERMANENT >
	 * @version 1.1_06         <! VARIABLE >
	 * @author  Bartosz Konkol <! VARIABLE >
	 * 
	 * @deprecated Was replaced by '{@link #Square(Point2D, float) Square}'.
	 * 
	 */@Deprecated
	public Square(Point point, float lengthSide)
	{
		this(point, lengthSide, (Color) null);
	}
	
	/**
	 * 
	 * Class a management of generating the square.
	 * 
	 * @param point      <! VARIABLE >
	 * @param lengthSide <! VARIABLE >
	 * 
	 * @since   1.1_06         <! PERMANENT >
	 * @version 1.1_06         <! VARIABLE >
	 * @author  Bartosz Konkol <! VARIABLE >
	 * 
	 */
	public Square(Point2D point, float lengthSide)
	{
		this(point, lengthSide, (Color) null);
	}
	
	/**
	 * 
	 * Class a management of generating the square.
	 * 
	 * @param point <! VARIABLE >
	 * 
	 * @since   1.1_05         <! PERMANENT >
	 * @version 1.1_06         <! VARIABLE >
	 * @author  Bartosz Konkol <! VARIABLE >
	 * 
	 * @deprecated Was replaced by '{@link #Square(Point2D) Square}'.
	 * 
	 */@Deprecated
	public Square(Point point)
	{
		this(point, 100);
	}
	
	/**
	 * 
	 * Class a management of generating the square.
	 * 
	 * @param point <! VARIABLE >
	 * 
	 * @since   1.1_06         <! PERMANENT >
	 * @version 1.1_06         <! VARIABLE >
	 * @author  Bartosz Konkol <! VARIABLE >
	 * 
	 */
	public Square(Point2D point)
	{
		this(point, 100);
	}
	
	/**
	 * 
	 * Class a management of generating the square.
	 * 
	 * @param lengthSide <! VARIABLE >
	 * 
	 * @since   1.1_05         <! PERMANENT >
	 * @version 1.1_06         <! VARIABLE >
	 * @author  Bartosz Konkol <! VARIABLE >
	 * 
	 */
	public Square(float lengthSide)
	{
		this(new Point2D((float) Mathematical.mathematicalConstants("zero"), (float) Mathematical.mathematicalConstants("zero")), lengthSide);
	}
	
	/**
	 * 
	 * Class a management of generating the square.
	 * 
	 * @since   1.1_05         <! PERMANENT >
	 * @version 1.1_05         <! VARIABLE >
	 * @author  Bartosz Konkol <! VARIABLE >
	 * 
	 */
	public Square()
	{
		this(100);
	}
	
	/**
	 * The function of generation the structure. <! VARIABLE >
	 * @since   1.1_05                           <! PERMANENT >
	 * @version 1.1_06                           <! VARIABLE >
	 * @author  Bartosz Konkol                   <! VARIABLE >
	 */@Override
	public void doGenerate()
	{
		glBegin(GL_QUADS);
		
		if(this.getColors() != null && this.getColors().length >= 1 && this.getColors()[0] != null)
			Generation.doGenerate(this.getColors()[0]);
		
		glVertex2d(this.getPoints2D()[0].getX(), this.getPoints2D()[0].getY());
		
		if(this.getColors() != null && this.getColors().length >= 2 && this.getColors()[1] != null)
			Generation.doGenerate(this.getColors()[1]);
		
		glVertex2d(this.getPoints2D()[0].getX(), this.getPoints2D()[0].getY() + this.giveSideLength());
		
		if(this.getColors() != null && this.getColors().length >= 3 && this.getColors()[2] != null)
			Generation.doGenerate(this.getColors()[2]);
		
		glVertex2d(this.getPoints2D()[0].getX() + this.giveSideLength(), this.getPoints2D()[0].getY() + this.giveSideLength());
		
		if(this.getColors() != null && this.getColors().length >= 4 && this.getColors()[3] != null)
			Generation.doGenerate(this.getColors()[3]);
		
		glVertex2d(this.getPoints2D()[0].getX() + this.giveSideLength(), this.getPoints2D()[0].getY());
		
		glEnd();
	}
	
	/**
	 * Function defining the regular shape. <! VARIABLE >
	 * @since   1.1_05                      <! PERMANENT >
	 * @version 1.1_05                      <! VARIABLE >
	 * @author  Bartosz Konkol              <! VARIABLE >
	 */@Override
	public float giveSideLength()
	{
		return this.lengthSide;
	}
	
}
