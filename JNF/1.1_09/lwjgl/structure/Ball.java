
/**
 * 
 * Java New Functions
 * JNF
 * 1.1_05
 * 
 * © Bartosz Konkol
 * 
 * javax.jnf.lwjgl.structure.Ball
 * 2014-03-17 - 2014-03-17 [JNF 1.1_05]
 * 2014-05-10 - 2014-02-10 [JNF 1.1_06]
 * 
 */

package javax.jnf.lwjgl.structure;

import java.awt.Point;
import javax.jnf.lwjgl.*;
import javax.jnf.technical.constants.Mathematical;
import org.lwjgl.util.glu.GLU;
import org.lwjgl.util.glu.Sphere;
import static org.lwjgl.opengl.GL11.*;

/**
 * 
 * Support for LWJGL<br>Structure<br>Ball
 * 		<p>
 * Class of generating the ball.
 * 
 * @since   1.1_05         <! PERMANENT >
 * @version 1.1_06         <! VARIABLE >
 * @author  Bartosz Konkol <! VARIABLE >
 *
 */

public class Ball extends Structure3D implements Round
{
	
	private final float lengthRadius;
	private final boolean circle;
	
	/**
	 * 
	 * Class a management of generating the ball.
	 * 
	 * @param point        <! VARIABLE >
	 * @param depth        <! VARIABLE >
	 * @param lengthRadius <! VARIABLE >
	 * @param circle       <! VARIABLE >
	 * @param color        <! VARIABLE >
	 * 
	 * @since   1.1_05         <! PERMANENT >
	 * @version 1.1_06         <! VARIABLE >
	 * @author  Bartosz Konkol <! VARIABLE >
	 * 
	 * @deprecated Was replaced by '{@link #Ball(Point3D, float, boolean, Color) Ball}'.
	 * 
	 */@Deprecated
	public Ball(final Point point, final float depth, final float lengthRadius, final boolean circle, final Color color)
	{
		super(new Point[]{point}, new float[]{depth}, color != null ? new Color[]{color} : null);
		this.lengthRadius = lengthRadius;
		this.circle = circle;
	}
	
	/**
	 * 
	 * Class a management of generating the ball.
	 * 
	 * @param point        <! VARIABLE >
	 * @param lengthRadius <! VARIABLE >
	 * @param circle       <! VARIABLE >
	 * @param color        <! VARIABLE >
	 * 
	 * @since   1.1_06         <! PERMANENT >
	 * @version 1.1_06         <! VARIABLE >
	 * @author  Bartosz Konkol <! VARIABLE >
	 * 
	 */
	public Ball(final Point3D point, final float lengthRadius, final boolean circle, final Color color)
	{
		super(new Point3D[]{point}, color != null ? new Color[]{color} : null);
		this.lengthRadius = lengthRadius;
		this.circle = circle;
	}
	
	/**
	 * 
	 * Class a management of generating the ball.
	 * 
	 * @param point        <! VARIABLE >
	 * @param depth        <! VARIABLE >
	 * @param lengthRadius <! VARIABLE >
	 * @param color        <! VARIABLE >
	 * 
	 * @since   1.1_05         <! PERMANENT >
	 * @version 1.1_06         <! VARIABLE >
	 * @author  Bartosz Konkol <! VARIABLE >
	 * 
	 * @deprecated Was replaced by '{@link #Ball(Point3D, float, Color) Ball}'.
	 * 
	 */@Deprecated
	public Ball(final Point point, final float depth, final float lengthRadius, final Color color)
	{
		this(point, depth, lengthRadius, false, color);
	}
	
	/**
	 * 
	 * Class a management of generating the ball.
	 * 
	 * @param point        <! VARIABLE >
	 * @param lengthRadius <! VARIABLE >
	 * @param color        <! VARIABLE >
	 * 
	 * @since   1.1_06         <! PERMANENT >
	 * @version 1.1_06         <! VARIABLE >
	 * @author  Bartosz Konkol <! VARIABLE >
	 * 
	 */
	public Ball(final Point3D point, final float lengthRadius, final Color color)
	{
		this(point, lengthRadius, false, color);
	}
	
	/**
	 * 
	 * Class a management of generating the ball.
	 * 
	 * @param point        <! VARIABLE >
	 * @param depth        <! VARIABLE >
	 * @param lengthRadius <! VARIABLE >
	 * @param circle       <! VARIABLE >
	 * 
	 * @since   1.1_05         <! PERMANENT >
	 * @version 1.1_06         <! VARIABLE >
	 * @author  Bartosz Konkol <! VARIABLE >
	 * 
	 * @deprecated Was replaced by '{@link #Ball(Point3D, float, boolean) Ball}'.
	 * 
	 */@Deprecated
	public Ball(final Point point, final float depth, final float lengthRadius, final boolean circle)
	{
		this(point, depth, lengthRadius, circle, null);
	}
	
	/**
	 * 
	 * Class a management of generating the ball.
	 * 
	 * @param point        <! VARIABLE >
	 * @param lengthRadius <! VARIABLE >
	 * @param circle       <! VARIABLE >
	 * 
	 * @since   1.1_06         <! PERMANENT >
	 * @version 1.1_06         <! VARIABLE >
	 * @author  Bartosz Konkol <! VARIABLE >
	 * 
	 */
	public Ball(final Point3D point, final float lengthRadius, final boolean circle)
	{
		this(point, lengthRadius, circle, null);
	}
	
	/**
	 * 
	 * Class a management of generating the ball.
	 * 
	 * @param point        <! VARIABLE >
	 * @param depth        <! VARIABLE >
	 * @param lengthRadius <! VARIABLE >
	 * 
	 * @since   1.1_05         <! PERMANENT >
	 * @version 1.1_06         <! VARIABLE >
	 * @author  Bartosz Konkol <! VARIABLE >
	 * 
	 * @deprecated Was replaced by '{@link #Ball(Point3D, float) Ball}'.
	 * 
	 */@Deprecated
	public Ball(final Point point, final float depth, final float lengthRadius)
	{
		this(point, depth, lengthRadius, false);
	}
	
	/**
	 * 
	 * Class a management of generating the ball.
	 * 
	 * @param point        <! VARIABLE >
	 * @param lengthRadius <! VARIABLE >
	 * 
	 * @since   1.1_06         <! PERMANENT >
	 * @version 1.1_06         <! VARIABLE >
	 * @author  Bartosz Konkol <! VARIABLE >
	 * 
	 */
	public Ball(final Point3D point, final float lengthRadius)
	{
		this(point, lengthRadius, false);
	}
	
	/**
	 * 
	 * Class a management of generating the ball.
	 * 
	 * @param point <! VARIABLE >
	 * @param depth <! VARIABLE >
	 * 
	 * @since   1.1_05         <! PERMANENT >
	 * @version 1.1_06         <! VARIABLE >
	 * @author  Bartosz Konkol <! VARIABLE >
	 * 
	 * @deprecated Was replaced by '{@link #Ball(Point3D) Ball}'.
	 * 
	 */@Deprecated
	public Ball(final Point point, final float depth)
	{
		this(point, depth, (float) Mathematical.mathematicalConstants("one"));
	}
	
	/**
	 * 
	 * Class a management of generating the ball.
	 * 
	 * @param point <! VARIABLE >
	 * 
	 * @since   1.1_06         <! PERMANENT >
	 * @version 1.1_06         <! VARIABLE >
	 * @author  Bartosz Konkol <! VARIABLE >
	 * 
	 */
	public Ball(final Point3D point)
	{
		this(point, (float) Mathematical.mathematicalConstants("one"));
	}
	
	/**
	 * 
	 * Class a management of generating the ball.
	 * 
	 * @param lengthRadius <! VARIABLE >
	 * 
	 * @since   1.1_05         <! PERMANENT >
	 * @version 1.1_06         <! VARIABLE >
	 * @author  Bartosz Konkol <! VARIABLE >
	 * 
	 */
	public Ball(final float lengthRadius)
	{
		this(new Point3D((float) Mathematical.mathematicalConstants("zero"), (float) Mathematical.mathematicalConstants("zero"), (float) Mathematical.mathematicalConstants("zero")), lengthRadius);
	}
	
	/**
	 * 
	 * Class a management of generating the ball.
	 * 
	 * @since   1.1_05         <! PERMANENT >
	 * @version 1.1_05         <! VARIABLE >
	 * @author  Bartosz Konkol <! VARIABLE >
	 * 
	 */
	public Ball()
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
		if(this.getColors() != null && this.getColors()[0] != null)
			Generation.doGenerate(this.getColors()[0]);
		
		glPushMatrix();
		glTranslated(this.getPoints3D()[0].getX(), this.getPoints3D()[0].getY(), this.getPoints3D()[0].getZ());
		Sphere sphere = new Sphere();
		if(this.giveCircle())
			sphere.setDrawStyle(GLU.GLU_LINE);
		sphere.draw(this.giveSideLength(), (int) this.giveSideLength() * 100 / 3, (int) this.giveSideLength() * 100 / 3);
		glPopMatrix();
	}
	
	/**
	 * Function defining the regular shape. <! VARIABLE >
	 * @since   1.1_05                      <! PERMANENT >
	 * @version 1.1_05                      <! VARIABLE >
	 * @author  Bartosz Konkol              <! VARIABLE >
	 */@Override
	public float giveSideLength()
	{
		return this.lengthRadius;
	}

	/**
	 * Function defining the round. <! VARIABLE >
	 * @since   1.1_05              <! PERMANENT >
	 * @version 1.1_05              <! VARIABLE >
	 * @author  Bartosz Konkol      <! VARIABLE >
	 */@Override
	public boolean giveCircle()
	{
		return this.circle;
	}
	
}
