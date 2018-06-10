
/**
 * 
 * Java New Functions
 * JNF
 * 1.1_05
 * 
 * © Bartosz Konkol
 * 
 * javax.jnf.lwjgl.structure.Cube
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
 * Support for LWJGL<br>Structure<br>Cube
 * 		<p>
 * Class of generating the cube.
 * 
 * @since   1.1_05         <! PERMANENT >
 * @version 1.1_06         <! VARIABLE >
 * @author  Bartosz Konkol <! VARIABLE >
 *
 */

public class Cube extends Structure3D implements RegularShape
{
	
	private final float lengthSide;
	
	/**
	 * 
	 * Class a management of generating the cube.
	 * 
	 * @param point      <! VARIABLE >
	 * @param depth      <! VARIABLE >
	 * @param lengthSide <! VARIABLE >
	 * @param colors     <! VARIABLE >
	 * 
	 * @since   1.1_05         <! PERMANENT >
	 * @version 1.1_06         <! VARIABLE >
	 * @author  Bartosz Konkol <! VARIABLE >
	 * 
	 * @deprecated Was replaced by '{@link #Cube(Point3D, float, Color[]) Cube}'.
	 * 
	 */@Deprecated
	public Cube(final Point point, final float depth, final float lengthSide, final Color... colors)
	{
		super(new Point[]{point}, new float[]{depth}, colors);
		this.lengthSide = lengthSide;
	}
	
	/**
	 * 
	 * Class a management of generating the cube.
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
	public Cube(final Point3D point, final float lengthSide, final Color... colors)
	{
		super(new Point3D[]{point}, colors);
		this.lengthSide = lengthSide;
	}
	
	/**
	 * 
	 * Class a management of generating the cube.
	 * 
	 * @param point      <! VARIABLE >
	 * @param depth      <! VARIABLE >
	 * @param lengthSide <! VARIABLE >
	 * @param color      <! VARIABLE >
	 * 
	 * @since   1.1_05         <! PERMANENT >
	 * @version 1.1_06         <! VARIABLE >
	 * @author  Bartosz Konkol <! VARIABLE >
	 * 
	 * @deprecated Was replaced by '{@link #Cube(Point3D, float, Color) Cube}'.
	 * 
	 */@Deprecated
	public Cube(final Point point, final float depth, final float lengthSide, final Color color)
	{
		this(point, depth, lengthSide, color != null ? new Color[]{color} : null);
	}
	
	/**
	 * 
	 * Class a management of generating the cube.
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
	public Cube(final Point3D point, final float lengthSide, final Color color)
	{
		this(point, lengthSide, color != null ? new Color[]{color} : null);
	}
	
	/**
	 * 
	 * Class a management of generating the cube.
	 * 
	 * @param point      <! VARIABLE >
	 * @param depth      <! VARIABLE >
	 * @param lengthSide <! VARIABLE >
	 * 
	 * @since   1.1_05         <! PERMANENT >
	 * @version 1.1_06         <! VARIABLE >
	 * @author  Bartosz Konkol <! VARIABLE >
	 * 
	 * @deprecated Was replaced by '{@link #Cube(Point3D, float) Cube}'.
	 * 
	 */@Deprecated
	public Cube(final Point point, final float depth, final float lengthSide)
	{
		this(point, depth, lengthSide, (Color) null);
	}
	
	/**
	 * 
	 * Class a management of generating the cube.
	 * 
	 * @param point      <! VARIABLE >
	 * @param lengthSide <! VARIABLE >
	 * 
	 * @since   1.1_06         <! PERMANENT >
	 * @version 1.1_06         <! VARIABLE >
	 * @author  Bartosz Konkol <! VARIABLE >
	 * 
	 */
	public Cube(final Point3D point, final float lengthSide)
	{
		this(point, lengthSide, (Color) null);
	}
	
	/**
	 * 
	 * Class a management of generating the cube.
	 * 
	 * @param point <! VARIABLE >
	 * @param depth <! VARIABLE >
	 * 
	 * @since   1.1_05         <! PERMANENT >
	 * @version 1.1_06         <! VARIABLE >
	 * @author  Bartosz Konkol <! VARIABLE >
	 * 
	 * @deprecated Was replaced by '{@link #Cube(Point3D) Cube}'.
	 * 
	 */@Deprecated
	public Cube(final Point point, final float depth)
	{
		this(point, depth, (float) Mathematical.mathematicalConstants("one"));
	}
	
	/**
	 * 
	 * Class a management of generating the cube.
	 * 
	 * @param point <! VARIABLE >
	 * 
	 * @since   1.1_06         <! PERMANENT >
	 * @version 1.1_06         <! VARIABLE >
	 * @author  Bartosz Konkol <! VARIABLE >
	 * 
	 */
	public Cube(final Point3D point)
	{
		this(point, (float) Mathematical.mathematicalConstants("one"));
	}
	
	/**
	 * 
	 * Class a management of generating the cube.
	 * 
	 * @param lengthSide <! VARIABLE >
	 * 
	 * @since   1.1_05         <! PERMANENT >
	 * @version 1.1_06         <! VARIABLE >
	 * @author  Bartosz Konkol <! VARIABLE >
	 * 
	 */
	public Cube(final float lengthSide)
	{
		this(new Point3D((float) Mathematical.mathematicalConstants("zero"), (float) Mathematical.mathematicalConstants("zero"), (float) Mathematical.mathematicalConstants("zero")), lengthSide);
	}
	
	/**
	 * 
	 * Class a management of generating the cube.
	 * 
	 * @since   1.1_05         <! PERMANENT >
	 * @version 1.1_05         <! VARIABLE >
	 * @author  Bartosz Konkol <! VARIABLE >
	 * 
	 */
	public Cube()
	{
		this((float) Mathematical.mathematicalConstants("one"));
	}
	
	/**
	 * The function of generation the structure. <! VARIABLE >
	 * @since   1.1_05                           <! PERMANENT >
	 * @version 1.1_06                           <! VARIABLE >
	 * @author  Bartosz Konkol                   <! VARIABLE >
	 */@Override
	public void doGenerate()
	{
		final double x = this.getPoints3D()[0].getX() - this.giveSideLength() / 2;
		final double y = this.getPoints3D()[0].getY() - this.giveSideLength() / 2;
		final double z = this.getPoints3D()[0].getZ() - this.giveSideLength() / 2;
		
		glBegin(GL_QUADS);

		if(this.getColors() != null && this.getColors().length >= 1 && this.getColors()[0] != null)
			Generation.doGenerate(this.getColors()[0]);
		
		glVertex3d(x, y, z);

		if(this.getColors() != null && this.getColors().length >= 2 && this.getColors()[1] != null)
			Generation.doGenerate(this.getColors()[1]);
		
		glVertex3d(x, y + this.giveSideLength(), z);

		if(this.getColors() != null && this.getColors().length >= 3 && this.getColors()[2] != null)
			Generation.doGenerate(this.getColors()[2]);
		
		glVertex3d(x + this.giveSideLength(), y + this.giveSideLength(), z);

		if(this.getColors() != null && this.getColors().length >= 4 && this.getColors()[3] != null)
			Generation.doGenerate(this.getColors()[3]);
		
		glVertex3d(x + this.giveSideLength(), y, z);

		if(this.getColors() != null && this.getColors().length >= 5 && this.getColors()[4] != null)
			Generation.doGenerate(this.getColors()[4]);
		
		glVertex3d(x, y, z + this.giveSideLength());

		if(this.getColors() != null && this.getColors().length >= 6 && this.getColors()[5] != null)
			Generation.doGenerate(this.getColors()[5]);
		
		glVertex3d(x, y + this.giveSideLength(), z + this.giveSideLength());

		if(this.getColors() != null && this.getColors().length >= 7 && this.getColors()[6] != null)
			Generation.doGenerate(this.getColors()[6]);
		
		glVertex3d(x + this.giveSideLength(), y + this.giveSideLength(), z + this.giveSideLength());

		if(this.getColors() != null && this.getColors().length >= 8 && this.getColors()[7] != null)
			Generation.doGenerate(this.getColors()[7]);
		
		glVertex3d(x + this.giveSideLength(), y, z + this.giveSideLength());

		if(this.getColors() != null && this.getColors().length >= 9 && this.getColors()[8] != null)
			Generation.doGenerate(this.getColors()[8]);
		
		glVertex3d(x, y, z);

		if(this.getColors() != null && this.getColors().length >= 10 && this.getColors()[9] != null)
			Generation.doGenerate(this.getColors()[9]);
		
		glVertex3d(x, y, z + this.giveSideLength());

		if(this.getColors() != null && this.getColors().length >= 11 && this.getColors()[10] != null)
			Generation.doGenerate(this.getColors()[10]);
		
		glVertex3d(x + this.giveSideLength(), y, z + this.giveSideLength());

		if(this.getColors() != null && this.getColors().length >= 12 && this.getColors()[11] != null)
			Generation.doGenerate(this.getColors()[11]);
		
		glVertex3d(x + this.giveSideLength(), y, z);

		if(this.getColors() != null && this.getColors().length >= 13 && this.getColors()[12] != null)
			Generation.doGenerate(this.getColors()[12]);
		
		glVertex3d(x, y + this.giveSideLength(), z);

		if(this.getColors() != null && this.getColors().length >= 14 && this.getColors()[13] != null)
			Generation.doGenerate(this.getColors()[13]);
		
		glVertex3d(x, y + this.giveSideLength(), z + this.giveSideLength());

		if(this.getColors() != null && this.getColors().length >= 15 && this.getColors()[14] != null)
			Generation.doGenerate(this.getColors()[14]);
		
		glVertex3d(x + this.giveSideLength(), y + this.giveSideLength(), z + this.giveSideLength());

		if(this.getColors() != null && this.getColors().length >= 16 && this.getColors()[15] != null)
			Generation.doGenerate(this.getColors()[15]);
		
		glVertex3d(x + this.giveSideLength(), y + this.giveSideLength(), z);

		if(this.getColors() != null && this.getColors().length >= 17 && this.getColors()[16] != null)
			Generation.doGenerate(this.getColors()[16]);
		
		glVertex3d(x, y, z);

		if(this.getColors() != null && this.getColors().length >= 18 && this.getColors()[17] != null)
			Generation.doGenerate(this.getColors()[17]);
		
		glVertex3d(x, y, z + this.giveSideLength());

		if(this.getColors() != null && this.getColors().length >= 19 && this.getColors()[18] != null)
			Generation.doGenerate(this.getColors()[18]);
		
		glVertex3d(x, y + this.giveSideLength(), z + this.giveSideLength());

		if(this.getColors() != null && this.getColors().length >= 20 && this.getColors()[19] != null)
			Generation.doGenerate(this.getColors()[19]);
		
		glVertex3d(x, y + this.giveSideLength(), z);

		if(this.getColors() != null && this.getColors().length >= 21 && this.getColors()[20] != null)
			Generation.doGenerate(this.getColors()[20]);
		
		glVertex3d(x + this.giveSideLength(), y, z);

		if(this.getColors() != null && this.getColors().length >= 22 && this.getColors()[21] != null)
			Generation.doGenerate(this.getColors()[21]);
		
		glVertex3d(x + this.giveSideLength(), y, z + this.giveSideLength());

		if(this.getColors() != null && this.getColors().length >= 23 && this.getColors()[22] != null)
			Generation.doGenerate(this.getColors()[22]);
		
		glVertex3d(x + this.giveSideLength(), y + this.giveSideLength(), z + this.giveSideLength());

		if(this.getColors() != null && this.getColors().length >= 24 && this.getColors()[23] != null)
			Generation.doGenerate(this.getColors()[23]);
		
		glVertex3d(x + this.giveSideLength(), y + this.giveSideLength(), z);
		
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
