
/**
 * 
 * Java New Functions
 * JNF
 * 1.1_05
 * 
 * © Bartosz Konkol
 * 
 * javax.jnf.lwjgl.structure.Disk
 * 2014-03-17 - 2014-03-17 [JNF 1.1_05]
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
 * Support for LWJGL<br>Structure<br>Disk
 * 		<p>
 * Class of generating the disk.
 * 
 * @since   1.1_05         <! PERMANENT >
 * @version 1.1_06         <! VARIABLE >
 * @author  Bartosz Konkol <! VARIABLE >
 *
 */

public class Disk extends Structure2D implements Round
{
	
	private final float lengthRadius;
	private final boolean circle;
	
	/**
	 * 
	 * Class a management of generating the disk.
	 * 
	 * @param point        <! VARIABLE >
	 * @param lengthRadius <! VARIABLE >
	 * @param circle       <! VARIABLE >
	 * @param color        <! VARIABLE >
	 * 
	 * @since   1.1_05         <! PERMANENT >
	 * @version 1.1_06         <! VARIABLE >
	 * @author  Bartosz Konkol <! VARIABLE >
	 * 
	 * @deprecated Was replaced by '{@link #Disk(Point2D, float, boolean, Color) Disk}'.
	 * 
	 */@Deprecated
	public Disk(Point point, float lengthRadius, boolean circle, Color color)
	{
		super(new Point[]{point}, color != null ? new Color[]{color} : null);
		this.lengthRadius = lengthRadius;
		this.circle = circle;
	}
	
	/**
	 * 
	 * Class a management of generating the disk.
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
	public Disk(Point2D point, float lengthRadius, boolean circle, Color color)
	{
		super(new Point2D[]{point}, color != null ? new Color[]{color} : null);
		this.lengthRadius = lengthRadius;
		this.circle = circle;
	}
	
	/**
	 * 
	 * Class a management of generating the disk.
	 * 
	 * @param point        <! VARIABLE >
	 * @param lengthRadius <! VARIABLE >
	 * @param color        <! VARIABLE >
	 * 
	 * @since   1.1_05         <! PERMANENT >
	 * @version 1.1_06         <! VARIABLE >
	 * @author  Bartosz Konkol <! VARIABLE >
	 * 
	 * @deprecated Was replaced by '{@link #Disk(Point2D, float, Color) Disk}'.
	 * 
	 */@Deprecated
	public Disk(Point point, float lengthRadius, Color color)
	{
		this(point, lengthRadius, false, color);
	}
	
	/**
	 * 
	 * Class a management of generating the disk.
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
	public Disk(Point2D point, float lengthRadius, Color color)
	{
		this(point, lengthRadius, false, color);
	}
	
	/**
	 * 
	 * Class a management of generating the disk.
	 * 
	 * @param point        <! VARIABLE >
	 * @param lengthRadius <! VARIABLE >
	 * @param circle       <! VARIABLE >
	 * 
	 * @since   1.1_05         <! PERMANENT >
	 * @version 1.1_06         <! VARIABLE >
	 * @author  Bartosz Konkol <! VARIABLE >
	 * 
	 * @deprecated Was replaced by '{@link #Disk(Point2D, float, boolean) Disk}'.
	 * 
	 */@Deprecated
	public Disk(Point point, float lengthRadius, boolean circle)
	{
		this(point, lengthRadius, circle, null);
	}
	
	/**
	 * 
	 * Class a management of generating the disk.
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
	public Disk(Point2D point, float lengthRadius, boolean circle)
	{
		this(point, lengthRadius, circle, null);
	}
	
	/**
	 * 
	 * Class a management of generating the disk.
	 * 
	 * @param point        <! VARIABLE >
	 * @param lengthRadius <! VARIABLE >
	 * 
	 * @since   1.1_05         <! PERMANENT >
	 * @version 1.1_06         <! VARIABLE >
	 * @author  Bartosz Konkol <! VARIABLE >
	 * 
	 * @deprecated Was replaced by '{@link #Disk(Point2D, float) Disk}'.
	 * 
	 */@Deprecated
	public Disk(Point point, float lengthRadius)
	{
		this(point, lengthRadius, false);
	}
	
	/**
	 * 
	 * Class a management of generating the disk.
	 * 
	 * @param point        <! VARIABLE >
	 * @param lengthRadius <! VARIABLE >
	 * 
	 * @since   1.1_06         <! PERMANENT >
	 * @version 1.1_06         <! VARIABLE >
	 * @author  Bartosz Konkol <! VARIABLE >
	 * 
	 */
	public Disk(Point2D point, float lengthRadius)
	{
		this(point, lengthRadius, false);
	}
	
	/**
	 * 
	 * Class a management of generating the disk.
	 * 
	 * @param lengthRadius <! VARIABLE >
	 * 
	 * @since   1.1_05         <! PERMANENT >
	 * @version 1.1_06         <! VARIABLE >
	 * @author  Bartosz Konkol <! VARIABLE >
	 * 
	 */
	public Disk(float lengthRadius)
	{
		this(new Point2D((float) Mathematical.mathematicalConstants("zero"), (float) Mathematical.mathematicalConstants("zero")), lengthRadius);
	}
	
	/**
	 * 
	 * Class a management of generating the disk.
	 * 
	 * @since   1.1_05         <! PERMANENT >
	 * @version 1.1_05         <! VARIABLE >
	 * @author  Bartosz Konkol <! VARIABLE >
	 * 
	 */
	public Disk()
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
		if(this.getColors() != null && this.getColors()[0] != null)
			Generation.doGenerate(this.getColors()[0]);
		
		glPushMatrix();
		glTranslated(this.getPoints2D()[0].getX(), this.getPoints2D()[0].getY(), Mathematical.mathematicalConstants("zero"));
		new org.lwjgl.util.glu.Disk().draw((float) (this.giveCircle() ? this.giveSideLength() - Mathematical.mathematicalConstants("one") : Mathematical.mathematicalConstants("zero")), this.giveSideLength(), (int) this.giveSideLength() / 3, (int) this.giveSideLength() / 3);
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
