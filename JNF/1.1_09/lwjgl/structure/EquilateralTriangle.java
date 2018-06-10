
/**
 * 
 * Java New Functions
 * JNF
 * 1.1_06
 * 
 * © Bartosz Konkol
 * 
 * javax.jnf.lwjgl.structure.EquilateralTriangle
 * 2014-05-09 - 2014-05-10 [JNF 1.1_06]
 * 
 */

package javax.jnf.lwjgl.structure;

import static org.lwjgl.opengl.GL11.*;
import javax.jnf.lwjgl.*;
import javax.jnf.technical.constants.Mathematical;
import javax.jnf.technical.maths.Automatic;

/**
 * 
 * Support for LWJGL<br>Structure<br>Equilateral Triangle
 * 		<p>
 * Class of generating the equilateral triangle.
 * 
 * @since   1.1_06         <! PERMANENT >
 * @version 1.1_06         <! VARIABLE >
 * @author  Bartosz Konkol <! VARIABLE >
 *
 */

public class EquilateralTriangle extends Structure2D implements RegularShape
{
	
	private final float lengthSide;
	
	/**
	 * 
	 * Class a management of generating the equilateral triangle.
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
	public EquilateralTriangle(final Point2D point, final float lengthSide, final Color... colors)
	{
		super(new Point2D[]{point}, colors);
		this.lengthSide = lengthSide;
	}
	
	/**
	 * 
	 * Class a management of generating the equilateral triangle.
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
	public EquilateralTriangle(Point2D point, float lengthSide, Color color)
	{
		this(point, lengthSide, color != null ? new Color[]{color} : null);
	}
	
	/**
	 * 
	 * Class a management of generating the equilateral triangle.
	 * 
	 * @param point      <! VARIABLE >
	 * @param lengthSide <! VARIABLE >
	 * 
	 * @since   1.1_06         <! PERMANENT >
	 * @version 1.1_06         <! VARIABLE >
	 * @author  Bartosz Konkol <! VARIABLE >
	 * 
	 */
	public EquilateralTriangle(Point2D point, float lengthSide)
	{
		this(point, lengthSide, (Color) null);
	}
	
	/**
	 * 
	 * Class a management of generating the equilateral triangle.
	 * 
	 * @param point <! VARIABLE >
	 * 
	 * @since   1.1_06         <! PERMANENT >
	 * @version 1.1_06         <! VARIABLE >
	 * @author  Bartosz Konkol <! VARIABLE >
	 * 
	 */
	public EquilateralTriangle(Point2D point)
	{
		this(point, 100);
	}
	
	/**
	 * 
	 * Class a management of generating the equilateral triangle.
	 * 
	 * @param lengthSide <! VARIABLE >
	 * 
	 * @since   1.1_06         <! PERMANENT >
	 * @version 1.1_06         <! VARIABLE >
	 * @author  Bartosz Konkol <! VARIABLE >
	 * 
	 */
	public EquilateralTriangle(float lengthSide)
	{
		this(new Point2D((float) Mathematical.mathematicalConstants("zero"), (float) Mathematical.mathematicalConstants("zero")), lengthSide);
	}
	
	/**
	 * 
	 * Class a management of generating the equilateral triangle.
	 * 
	 * @since   1.1_06         <! PERMANENT >
	 * @version 1.1_06         <! VARIABLE >
	 * @author  Bartosz Konkol <! VARIABLE >
	 * 
	 */
	public EquilateralTriangle()
	{
		this(100);
	}
	
	/**
	 * The function of generation the structure. <! VARIABLE >
	 * @since   1.1_06                           <! PERMANENT >
	 * @version 1.1_06                           <! VARIABLE >
	 * @author  Bartosz Konkol                   <! VARIABLE >
	 */@Override
	public void doGenerate()
	{
		final Point2D point = new Point2D((float) this.getPoints2D()[0].getX(), (float) (this.getPoints2D()[0].getY() - Automatic.root(3) * this.giveSideLength() / 6));
		
		glBegin(GL_TRIANGLES);
		
		if(this.getColors() != null && this.getColors().length >= 1 && this.getColors()[0] != null)
			Generation.doGenerate(this.getColors()[0]);
		
		glVertex2d(point.getX() - this.giveSideLength() / 2, point.getY());
		
		if(this.getColors() != null && this.getColors().length >= 2 && this.getColors()[1] != null)
			Generation.doGenerate(this.getColors()[1]);
		
		glVertex2d(point.getX() + this.giveSideLength() / 2, point.getY());
		
		if(this.getColors() != null && this.getColors().length >= 3 && this.getColors()[2] != null)
			Generation.doGenerate(this.getColors()[2]);
		
		glVertex2d(point.getX(), point.getY() + Automatic.root(3) * this.giveSideLength() / 2);
		
		glEnd();
	}
	
	/**
	 * Function defining the regular shape. <! VARIABLE >
	 * @since   1.1_06                      <! PERMANENT >
	 * @version 1.1_06                      <! VARIABLE >
	 * @author  Bartosz Konkol              <! VARIABLE >
	 */@Override
	public final float giveSideLength()
	{
		return this.lengthSide;
	}
	
}
