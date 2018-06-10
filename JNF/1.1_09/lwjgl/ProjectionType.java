
/**
 * 
 * Java New Functions
 * JNF
 * 1.1_05
 * 
 * © Bartosz Konkol
 * 
 * javax.jnf.lwjgl.ProjectionType
 * 2014-03-10 - 2014-03-11 [JNF 1.1_05]
 * 2014-04-27 - 2014-04-27 [JNF 1.1_06]
 * 
 */

package javax.jnf.lwjgl;

import javax.jnf.lwjgl.exception.DefectLWJGL;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.GLU;

/**
 * 
 * Support for LWJGL<br>Projection Type
 * 		<p>
 * Class enabling determination of projection.
 * 
 * @since   1.1_05         <! PERMANENT >
 * @version 1.1_06         <! VARIABLE >
 * @author  Bartosz Konkol <! VARIABLE >
 *
 */

public enum ProjectionType implements ILWJGL
{
	
	/**
	 * Designation as a projection orthographic. <! VARIABLE >
	 * @since   1.1_05                           <! PERMANENT >
	 * @version 1.1_05                           <! VARIABLE >
	 * @author  Bartosz Konkol                   <! VARIABLE >
	 */
	
	ORTHOGRAPHIC,
	/**
	 * Designation as a projection perspective. <! VARIABLE >
	 * @since   1.1_05                          <! PERMANENT >
	 * @version 1.1_05                          <! VARIABLE >
	 * @author  Bartosz Konkol                  <! VARIABLE >
	 */
	PERSPECTIVE;
	
	/**
	 * Constant to determine the position of the upper right corner of the screen as the starting place for orthographic projection.
	 */
	public static final short POSITION_ORTHOGRAPHIC_NE = 0xA0;
	/**
	 * Constant to determine the position of the lower right corner of the screen as the starting place for orthographic projection.
	 */
	public static final short POSITION_ORTHOGRAPHIC_SE = 0xA1;
	/**
	 * Constant to determine the position of the lower left corner of the screen as the starting place for orthographic projection.
	 */
	public static final short POSITION_ORTHOGRAPHIC_SW = 0xA2;
	/**
	 * Constant to determine the position of the upper left corner of the screen as the starting place for orthographic projection.
	 */
	public static final short POSITION_ORTHOGRAPHIC_NW = 0xA3;
	/**
	 * Constant to determine the position of the center of the screen as the starting place for perspective projection.
	 */
	public static final short POSITION_PERSPECTIVE_CENTER = 0xB0;
	
	private float near;
	private float far;
	private short position;
	
	/**
	 * Specify the depth of near. <! VARIABLE >
	 * @since   1.1_05            <! PERMANENT >
	 * @version 1.1_05            <! VARIABLE >
	 * @author  Bartosz Konkol    <! VARIABLE >
	 */
	public final ProjectionType setNear(final float near)
	{
		this.near = near;
		return this;
	}
	
	/**
	 * Return the depth of near. <! VARIABLE >
	 * @since   1.1_05            <! PERMANENT >
	 * @version 1.1_05            <! VARIABLE >
	 * @author  Bartosz Konkol    <! VARIABLE >
	 */
	public final float getNear()
	{
		return this.near;
	}
	
	/**
	 * Specify the depth of far. <! VARIABLE >
	 * @since   1.1_05         <! PERMANENT >
	 * @version 1.1_05         <! VARIABLE >
	 * @author  Bartosz Konkol <! VARIABLE >
	 */
	public final ProjectionType setFar(final float far)
	{
		this.far = far;
		return this;
	}
	
	/**
	 * Return the depth of far. <! VARIABLE >
	 * @since   1.1_05         <! PERMANENT >
	 * @version 1.1_05         <! VARIABLE >
	 * @author  Bartosz Konkol <! VARIABLE >
	 */
	public final float getFar()
	{
		return this.far;
	}
	
	/**
	 * Specify the position.   <! VARIABLE >
	 * @since   1.1_05         <! PERMANENT >
	 * @version 1.1_05         <! VARIABLE >
	 * @author  Bartosz Konkol <! VARIABLE >
	 */
	public final ProjectionType setPosition(final short position)
	{
		this.position = position;
		return this;
	}
	
	/**
	 * Return the position.    <! VARIABLE >
	 * @since   1.1_05         <! PERMANENT >
	 * @version 1.1_05         <! VARIABLE >
	 * @author  Bartosz Konkol <! VARIABLE >
	 */
	public final short getPosition()
	{
		return this.position;
	}
	
	/**
	 * Performs the projection. <! VARIABLE >
	 * @since   1.1_05          <! PERMANENT >
	 * @version 1.1_05          <! VARIABLE >
	 * @author  Bartosz Konkol  <! VARIABLE >
	 * @throws DefectLWJGL
	 */
	public final void doWork() throws DefectLWJGL
	{
		if(this == ProjectionType.ORTHOGRAPHIC)
		{
			float left, right, bottom, top;
			left = right = bottom = top = 0.0F;
			
			switch(this.getPosition())
			{
				case POSITION_ORTHOGRAPHIC_NE:
				{
					left =   (float) Display.getWidth();
					bottom = (float) Display.getHeight();
					break;
				}
				case POSITION_ORTHOGRAPHIC_SE:
				{	
					left = (float) Display.getWidth();
					top =  (float) Display.getHeight();
					break;
				}
				case POSITION_ORTHOGRAPHIC_SW:
				{
					right = (float) Display.getWidth();
					top =   (float) Display.getHeight();
					break;
				}
				case POSITION_ORTHOGRAPHIC_NW:
				{
					right =  (float) Display.getWidth();
					bottom = (float) Display.getHeight();
					break;
				}
				default:
					throw new DefectLWJGL();
			}
			
			GL11.glOrtho(
				left,
				right,
				bottom,
				top,
				this.getNear(),
				this.getFar()
						);
		}
		else if(this == ProjectionType.PERSPECTIVE)
		{
			float aspect = 0;
			
			switch(this.getPosition())
			{
				case POSITION_PERSPECTIVE_CENTER:
				{
					aspect = (float) (Display.getWidth() / Display.getHeight());
					break;
				}
				default:
					throw new DefectLWJGL();
			}
			
			GLU.gluPerspective(
				90.0F,
				aspect,
				this.getNear(),
				this.getFar()
						);
		}
		else
			throw new DefectLWJGL();
	}

	/**
	 * The function of initiating the class action. <! VARIABLE >
	 * @since   1.1_06                              <! PERMANENT >
	 * @version 1.1_06                              <! VARIABLE >
	 * @author  Bartosz Konkol                      <! VARIABLE >
	 * @throws Exception
	 */@Override
	public void doGo() throws Exception{}

	 /**
	 * The function of resume the class action. <! VARIABLE >
	 * @since   1.1_06                          <! PERMANENT >
	 * @version 1.1_06                          <! VARIABLE >
	 * @author  Bartosz Konkol                  <! VARIABLE >
	 * @throws Exception
	 */@Override
	public void doRe() throws Exception{}

	 /**
	 * The function of inhibit the class action. <! VARIABLE >
	 * @since   1.1_06                           <! PERMANENT >
	 * @version 1.1_06                           <! VARIABLE >
	 * @author  Bartosz Konkol                   <! VARIABLE >
	 * @throws Exception
	 */@Override
	public void doDry() throws Exception{}

	 /**
	 * The function of closing the class action. <! VARIABLE >
	 * @since   1.1_06                           <! PERMANENT >
	 * @version 1.1_06                           <! VARIABLE >
	 * @author  Bartosz Konkol                   <! VARIABLE >
	 * @throws Exception
	 */@Override
	public void doShut() throws Exception{}
	
}
