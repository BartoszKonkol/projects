
/**
 * 
 * Java New Functions
 * JNF
 * 1.1_05
 * 
 * © Bartosz Konkol
 * 
 * javax.jnf.lwjgl.Util
 * 2014-03-26 - 2014-03-27 [JNF 1.1_05]
 * 2014-04-22 - 2014-05-06 [JNF 1.1_06]
 * 
 */

package javax.jnf.lwjgl;

import java.nio.Buffer;
import java.nio.FloatBuffer;
import javax.jnf.technical.constants.Mathematical;
import javax.jnf.technical.maths.Automatic;
import org.lwjgl.BufferUtils;
import static org.lwjgl.opengl.GL11.*;

/**
 * 
 * Support for LWJGL<br>Util
 * 		<p>
 * Helpful and useful the items.
 * 
 * @since   1.1_05         <! PERMANENT >
 * @version 1.1_06         <! VARIABLE >
 * @author  Bartosz Konkol <! VARIABLE >
 *
 */

public class Util extends LWJGL
{
	
	/**
	 * To convert a number Double to number Integer. <! VARIABLE >
	 * @since   1.1_05                               <! PERMANENT >
	 * @version 1.1_05                               <! VARIABLE >
	 * @author  Bartosz Konkol                       <! VARIABLE >
	 */
	public static final Integer giveDoubleToInteger(final Double number)
	{
		return Long.valueOf(Math.round(number)).intValue();
	}
	
	/**
	 * Returns the background color. <! VARIABLE >
	 * @since   1.1_05               <! PERMANENT >
	 * @version 1.1_05               <! VARIABLE >
	 * @author  Bartosz Konkol       <! VARIABLE >
	 */
	public static final Color giveBackgroundColor()
	{
		final FloatBuffer colors = (FloatBuffer) BufferUtils.createFloatBuffer(16).put(new float[16]).flip();
		glGetFloat(GL_COLOR_CLEAR_VALUE, colors);
		return new Color(Math.round(colors.get(0) * Color.MAX_VALUE_COLOR), Math.round(colors.get(1) * Color.MAX_VALUE_COLOR), Math.round(colors.get(2) * Color.MAX_VALUE_COLOR), Math.round(colors.get(3) * Color.MAX_VALUE_TRANSPARENT));
	}
	
	/**
	 * To convert a Color to Buffer. <! VARIABLE >
	 * @since   1.1_05               <! PERMANENT >
	 * @version 1.1_05               <! VARIABLE >
	 * @author  Bartosz Konkol       <! VARIABLE >
	 */
	public static final Buffer giveColorToBuffer(final Color color)
	{
		return BufferUtils.createFloatBuffer(4).put(new float[]{
				(float) Automatic.quotient(	color.getRed(),			Color.MAX_VALUE_COLOR		),
				(float) Automatic.quotient(	color.getGreen(),		Color.MAX_VALUE_COLOR		),
				(float) Automatic.quotient(	color.getBlue(),		Color.MAX_VALUE_COLOR		),
				(float) Automatic.quotient(	color.getTransparent(),	Color.MAX_VALUE_TRANSPARENT	),
			}).flip();
	}
	
	/**
	 * Specifies the background color. <! VARIABLE >
	 * @since   1.1_05                 <! PERMANENT >
	 * @version 1.1_05                 <! VARIABLE >
	 * @author  Bartosz Konkol         <! VARIABLE >
	 */
	public static final void doDetermineBackgroundColor(final Color color)
	{
		final FloatBuffer colors = (FloatBuffer) giveColorToBuffer(color);
		glClearColor(colors.get(0), colors.get(1), colors.get(2), colors.get(3));
	}
	
	/**
	 * Returns the given a power of two. <! VARIABLE >
	 * @since   1.1_05                   <! PERMANENT >
	 * @version 1.1_05                   <! VARIABLE >
	 * @author  Bartosz Konkol           <! VARIABLE >
	 */
	public static final int givePowerOfTwo(int power)
	{
		return (int) Mathematical.mathematicalConstants("two") << (power - (int) Mathematical.mathematicalConstants("one"));
	}
	
	/**
	 * To convert a number String to number Double. <! VARIABLE >
	 * @since   1.1_06                              <! PERMANENT >
	 * @version 1.1_06                              <! VARIABLE >
	 * @author  Bartosz Konkol                      <! VARIABLE >
	 */
	public static final Double giveStringToDouble(final String number)
	{
		return Double.valueOf(number.replace('\r', '\n').replace('\n', '\t').replace("\t", " ").replace(" ", "").replace(',', '.'));
	}
	
	/**
	 * To convert a number String to number Double.  <! VARIABLE >
	 * @since   1.1_06                               <! PERMANENT >
	 * @version 1.1_06                               <! VARIABLE >
	 * @author  Bartosz Konkol                       <! VARIABLE >
	 */
	public static final Float giveStringToFloat(final String number)
	{
		return giveStringToDouble(number).floatValue();
	}
	
	/**
	 * To convert a number String to number Integer. <! VARIABLE >
	 * @since   1.1_06                               <! PERMANENT >
	 * @version 1.1_06                               <! VARIABLE >
	 * @author  Bartosz Konkol                       <! VARIABLE >
	 */
	public static final Integer giveStringToInteger(final String number)
	{
		return giveDoubleToInteger(giveStringToDouble(number));
	}
	
}
