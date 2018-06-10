
/**
 * 
 * Java New Functions
 * JNF
 * 1.1_06
 * 
 * © Bartosz Konkol
 * 
 * javax.jnf.lwjgl.structure.Tetrahedron
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
 * Support for LWJGL<br>Structure<br>Tetrahedron
 * 		<p>
 * Class of generating the tetrahedron.
 * 
 * @since   1.1_05         <! PERMANENT >
 * @version 1.1_05         <! VARIABLE >
 * @author  Bartosz Konkol <! VARIABLE >
 *
 */

public class Tetrahedron extends Structure3D implements RegularShape
{
	
	private final float lengthSide;
	
	/**
	 * 
	 * Class a management of generating the tetrahedron.
	 * 
	 * @param point      <! VARIABLE >
	 * @param depth      <! VARIABLE >
	 * @param lengthSide <! VARIABLE >
	 * @param colors     <! VARIABLE >
	 * 
	 * @since   1.1_06         <! PERMANENT >
	 * @version 1.1_06         <! VARIABLE >
	 * @author  Bartosz Konkol <! VARIABLE >
	 * 
	 */
	public Tetrahedron(Point3D point, float lengthSide, Color... colors)
	{
		super(new Point3D[]{point}, colors);
		this.lengthSide = lengthSide;
	}
	
	/**
	 * 
	 * Class a management of generating the tetrahedron.
	 * 
	 * @param point      <! VARIABLE >
	 * @param depth      <! VARIABLE >
	 * @param lengthSide <! VARIABLE >
	 * @param color      <! VARIABLE >
	 * 
	 * @since   1.1_06         <! PERMANENT >
	 * @version 1.1_06         <! VARIABLE >
	 * @author  Bartosz Konkol <! VARIABLE >
	 * 
	 */
	public Tetrahedron(Point3D point, float lengthSide, Color color)
	{
		this(point, lengthSide, color != null ? new Color[]{color} : null);
	}
	
	/**
	 * 
	 * Class a management of generating the tetrahedron.
	 * 
	 * @param point      <! VARIABLE >
	 * @param depth      <! VARIABLE >
	 * @param lengthSide <! VARIABLE >
	 * 
	 * @since   1.1_06         <! PERMANENT >
	 * @version 1.1_06         <! VARIABLE >
	 * @author  Bartosz Konkol <! VARIABLE >
	 * 
	 */
	public Tetrahedron(Point3D point, float lengthSide)
	{
		this(point, lengthSide, (Color) null);
	}
	
	/**
	 * 
	 * Class a management of generating the tetrahedron.
	 * 
	 * @param point <! VARIABLE >
	 * @param depth <! VARIABLE >
	 * 
	 * @since   1.1_06         <! PERMANENT >
	 * @version 1.1_06         <! VARIABLE >
	 * @author  Bartosz Konkol <! VARIABLE >
	 * 
	 */
	public Tetrahedron(Point3D point)
	{
		this(point, (float) Mathematical.mathematicalConstants("one"));
	}
	
	/**
	 * 
	 * Class a management of generating the tetrahedron.
	 * 
	 * @param lengthSide <! VARIABLE >
	 * 
	 * @since   1.1_06         <! PERMANENT >
	 * @version 1.1_06         <! VARIABLE >
	 * @author  Bartosz Konkol <! VARIABLE >
	 * 
	 */
	public Tetrahedron(float lengthSide)
	{
		this(new Point3D((float) Mathematical.mathematicalConstants("zero"), (float) Mathematical.mathematicalConstants("zero"), (float) Mathematical.mathematicalConstants("zero")), lengthSide);
	}
	
	/**
	 * 
	 * Class a management of generating the tetrahedron.
	 * 
	 * @since   1.1_06         <! PERMANENT >
	 * @version 1.1_06         <! VARIABLE >
	 * @author  Bartosz Konkol <! VARIABLE >
	 * 
	 */
	public Tetrahedron()
	{
		this((float) Mathematical.mathematicalConstants("one"));
	}
	
	/**
	 * The function of generation the structure. <! VARIABLE >
	 * @since   1.1_06                           <! PERMANENT >
	 * @version 1.1_06                           <! VARIABLE >
	 * @author  Bartosz Konkol                   <! VARIABLE >
	 */@Override
	public void doGenerate()
	{
		final Point3D point = new Point3D((float) this.getPoints3D()[0].getX(), (float) (this.getPoints3D()[0].getY() - Automatic.root(6) * this.giveSideLength() / 3), (float) (this.getPoints3D()[0].getZ() - Automatic.root(3) * this.giveSideLength() / 6));
		
		glBegin(GL_TRIANGLES);
		
		if(this.getColors() != null && this.getColors().length >= 1 && this.getColors()[0] != null)
			Generation.doGenerate(this.getColors()[0]);
		
		glVertex3d(this.getPoints3D()[0].getX(), this.getPoints3D()[0].getY(), this.getPoints3D()[0].getZ());

		if(this.getColors() != null && this.getColors().length >= 2 && this.getColors()[1] != null)
			Generation.doGenerate(this.getColors()[1]);
		
		glVertex3d(point.getX() - this.giveSideLength() / 2, point.getY(), point.getZ());

		if(this.getColors() != null && this.getColors().length >= 3 && this.getColors()[2] != null)
			Generation.doGenerate(this.getColors()[2]);
		
		glVertex3d(point.getX() + this.giveSideLength() / 2, point.getY(), point.getZ());

		if(this.getColors() != null && this.getColors().length >= 4 && this.getColors()[3] != null)
			Generation.doGenerate(this.getColors()[3]);
		
		glVertex3d(this.getPoints3D()[0].getX(), this.getPoints3D()[0].getY(), this.getPoints3D()[0].getZ());

		if(this.getColors() != null && this.getColors().length >= 5 && this.getColors()[4] != null)
			Generation.doGenerate(this.getColors()[4]);
		
		glVertex3d(point.getX() + this.giveSideLength() / 2, point.getY(), point.getZ());

		if(this.getColors() != null && this.getColors().length >= 6 && this.getColors()[5] != null)
			Generation.doGenerate(this.getColors()[5]);
		
		glVertex3d(point.getX(), point.getY(), point.getZ() + Automatic.root(3) * this.giveSideLength() / 2);

		if(this.getColors() != null && this.getColors().length >= 7 && this.getColors()[6] != null)
			Generation.doGenerate(this.getColors()[6]);
		
		glVertex3d(this.getPoints3D()[0].getX(), this.getPoints3D()[0].getY(), this.getPoints3D()[0].getZ());

		if(this.getColors() != null && this.getColors().length >= 8 && this.getColors()[7] != null)
			Generation.doGenerate(this.getColors()[7]);
		
		glVertex3d(point.getX() - this.giveSideLength() / 2, point.getY(), point.getZ());

		if(this.getColors() != null && this.getColors().length >= 9 && this.getColors()[8] != null)
			Generation.doGenerate(this.getColors()[8]);
		
		glVertex3d(point.getX(), point.getY(), point.getZ() + Automatic.root(3) * this.giveSideLength() / 2);
		
		if(this.getColors() != null && this.getColors().length >= 10 && this.getColors()[9] != null)
			Generation.doGenerate(this.getColors()[9]);
		
		glVertex3d(point.getX() - this.giveSideLength() / 2, point.getY(), point.getZ());

		if(this.getColors() != null && this.getColors().length >= 11 && this.getColors()[10] != null)
			Generation.doGenerate(this.getColors()[10]);
		
		glVertex3d(point.getX() + this.giveSideLength() / 2, point.getY(), point.getZ());

		if(this.getColors() != null && this.getColors().length >= 12 && this.getColors()[11] != null)
			Generation.doGenerate(this.getColors()[11]);
		
		glVertex3d(point.getX(), point.getY(), point.getZ() + Automatic.root(3) * this.giveSideLength() / 2);
		
		glEnd();
	}
	
	 /**
	 * Function defining the regular shape. <! VARIABLE >
	 * @since   1.1_06                      <! PERMANENT >
	 * @version 1.1_06                      <! VARIABLE >
	 * @author  Bartosz Konkol              <! VARIABLE >
	 */@Override
	public float giveSideLength()
	{
		return this.lengthSide;
	}
	
}
