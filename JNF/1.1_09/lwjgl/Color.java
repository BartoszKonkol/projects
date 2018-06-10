
/**
 * 
 * Java New Functions
 * JNF
 * 1.1_05
 * 
 * © Bartosz Konkol
 * 
 * javax.jnf.lwjgl.Color
 * 2014-03-11 - 2014-03-16 [JNF 1.1_05]
 * 2014-04-27 - 2014-04-27 [JNF 1.1_06]
 * 2015-07-22 - 2015-07-22 [JNF 1.1_09]
 * 
 */

package javax.jnf.lwjgl;

import javax.jnf.lwjgl.exception.DefectLWJGL;
import javax.jnf.technical.constants.Mathematical;
import javax.jnf.technical.maths.Conversion;
import org.lwjgl.opengl.GL11;

/**
 * 
 * Support for LWJGL<br>Color
 * 		<p>
 * Class of colors management.
 * 
 * @since   1.1_05         <! PERMANENT >
 * @version 1.1_09         <! VARIABLE >
 * @author  Bartosz Konkol <! VARIABLE >
 *
 */

public class Color extends LWJGL implements GenerateGL, Cloneable
{
	
	/**
	 * The minimum value of settings the color and transparent. <! VARIABLE >
	 * @since   1.1_05                                          <! PERMANENT >
	 * @version 1.1_09                                          <! VARIABLE >
	 * @author  Bartosz Konkol                                  <! VARIABLE >
	 */
	public static final int MIN_VALUE =             0x0;
	/**
	 * The maximum value of settings the color. <! VARIABLE >
	 * @since   1.1_05                          <! PERMANENT >
	 * @version 1.1_05                          <! VARIABLE >
	 * @author  Bartosz Konkol                  <! VARIABLE >
	 */
	public static final int MAX_VALUE_COLOR =       0xFF;
	/**
	 * The maximum value of settings the transparent. <! VARIABLE >
	 * @since   1.1_05                                <! PERMANENT >
	 * @version 1.1_05                                <! VARIABLE >
	 * @author  Bartosz Konkol                        <! VARIABLE >
	 */
	public static final int MAX_VALUE_TRANSPARENT = 0x64;
	
	/**
	 * The generated color: white. <! VARIABLE >
	 * @since   1.1_05             <! PERMANENT >
	 * @version 1.1_09             <! VARIABLE >
	 * @author  Bartosz Konkol     <! VARIABLE >
	 */
	public final static Color WHITE =      new Color().clone();
	/**
	 * The generated color: black. <! VARIABLE >
	 * @since   1.1_05             <! PERMANENT >
	 * @version 1.1_09             <! VARIABLE >
	 * @author  Bartosz Konkol     <! VARIABLE >
	 */
	public final static Color BLACK =      new Color(MIN_VALUE).clone();
	/**
	 * The generated color: red. <! VARIABLE >
	 * @since   1.1_05           <! PERMANENT >
	 * @version 1.1_09           <! VARIABLE >
	 * @author  Bartosz Konkol   <! VARIABLE >
	 */
	public final static Color RED =        new Color(MAX_VALUE_COLOR, MIN_VALUE, MIN_VALUE).clone();
	/**
	 * The generated color: green. <! VARIABLE >
	 * @since   1.1_05             <! PERMANENT >
	 * @version 1.1_09             <! VARIABLE >
	 * @author  Bartosz Konkol     <! VARIABLE >
	 */
	public final static Color GREEN =      new Color(MIN_VALUE, MAX_VALUE_COLOR, MIN_VALUE).clone();
	/**
	 * The generated color: blue. <! VARIABLE >
	 * @since   1.1_05            <! PERMANENT >
	 * @version 1.1_09            <! VARIABLE >
	 * @author  Bartosz Konkol    <! VARIABLE >
	 */
	public final static Color BLUE =       new Color(MIN_VALUE, MIN_VALUE, MAX_VALUE_COLOR).clone();
	/**
	 * The generated color: yellow. <! VARIABLE >
	 * @since   1.1_05              <! PERMANENT >
	 * @version 1.1_09              <! VARIABLE >
	 * @author  Bartosz Konkol      <! VARIABLE >
	 */
	public final static Color YELLOW =     new Color(MAX_VALUE_COLOR, MAX_VALUE_COLOR, MIN_VALUE).clone();
	/**
	 * The generated color: grey. <! VARIABLE >
	 * @since   1.1_05            <! PERMANENT >
	 * @version 1.1_09            <! VARIABLE >
	 * @author  Bartosz Konkol    <! VARIABLE >
	 */
	public final static Color GRAY =       new Color(Byte.MAX_VALUE, Byte.MAX_VALUE, Byte.MAX_VALUE).clone();
	/**
	 * The generated color: dark red. <! VARIABLE >
	 * @since   1.1_05                <! PERMANENT >
	 * @version 1.1_09                <! VARIABLE >
	 * @author  Bartosz Konkol        <! VARIABLE >
	 */
	public final static Color DARK_RED =   new Color(Byte.MAX_VALUE, MIN_VALUE, MIN_VALUE).clone();
	/**
	 * The generated color: dark green. <! VARIABLE >
	 * @since   1.1_05                  <! PERMANENT >
	 * @version 1.1_09                  <! VARIABLE >
	 * @author  Bartosz Konkol          <! VARIABLE >
	 */
	public final static Color DARK_GREEN = new Color(MIN_VALUE, Byte.MAX_VALUE, MIN_VALUE).clone();
	/**
	 * The generated color: dark blue. <! VARIABLE >
	 * @since   1.1_05                 <! PERMANENT >
	 * @version 1.1_09                 <! VARIABLE >
	 * @author  Bartosz Konkol         <! VARIABLE >
	 */
	public final static Color DARK_BLUE =  new Color(MIN_VALUE, MIN_VALUE, Byte.MAX_VALUE).clone();
	/**
	 * The generated color: orange. <! VARIABLE >
	 * @since   1.1_05              <! PERMANENT >
	 * @version 1.1_09              <! VARIABLE >
	 * @author  Bartosz Konkol      <! VARIABLE >
	 */
	public final static Color ORANGE =     new Color(MAX_VALUE_COLOR, Byte.MAX_VALUE, MIN_VALUE).clone();
	
	/**
	 * 
	 * Class generates the standard (white) color and the standard (zero) transparency.
	 * 
	 * @since   1.1_05         <! PERMANENT >
	 * @version 1.1_05         <! VARIABLE >
	 * @author  Bartosz Konkol <! VARIABLE >
	 * 
	 */
	public Color()
	{
		this(MAX_VALUE_COLOR, MAX_VALUE_COLOR, MAX_VALUE_COLOR);
	}
	
	/**
	 * 
	 * Class generates the specified color and the standard (zero) transparency.
	 * 
	 * @param red   <! VARIABLE >
	 * @param green <! VARIABLE >
	 * @param blue  <! VARIABLE >
	 * 
	 * @since   1.1_05         <! PERMANENT >
	 * @version 1.1_05         <! VARIABLE >
	 * @author  Bartosz Konkol <! VARIABLE >
	 * 
	 */
	public Color(int red, int green, int blue)
	{
		this(red, green, blue, MIN_VALUE);
	}
	
	/**
	 * 
	 * Class generates the specified color and the specified transparency.
	 * 
	 * @param red         <! VARIABLE >
	 * @param green       <! VARIABLE >
	 * @param blue        <! VARIABLE >
	 * @param transparent <! VARIABLE >
	 * 
	 * @since   1.1_05         <! PERMANENT >
	 * @version 1.1_05         <! VARIABLE >
	 * @author  Bartosz Konkol <! VARIABLE >
	 * 
	 */
	public Color(int red, int green, int blue, int transparent)
	{
		if(
				red >= MIN_VALUE && red <= MAX_VALUE_COLOR &&
				green >= MIN_VALUE && green <= MAX_VALUE_COLOR &&
				blue >= MIN_VALUE && blue <= MAX_VALUE_COLOR
			)
			if(transparent >= MIN_VALUE && transparent <= MAX_VALUE_TRANSPARENT)
				this.setRed(red).setGreen(green).setBlue(blue).setTransparent(transparent);
			else
			{
				this.doReportError("Transparency is off the scale! Transparency is set to the default level (ie '0').");
				
				this.setRed(red).setGreen(green).setBlue(blue).setTransparent(MIN_VALUE);
			}
		else
		{
			
			this.doReportError("At least one color is off the scale! Colors is set to the default level (ie '255').");
			
			if(transparent >= MIN_VALUE && transparent <= MAX_VALUE_TRANSPARENT)
				this.setRed(MAX_VALUE_COLOR).setGreen(MAX_VALUE_COLOR).setBlue(MAX_VALUE_COLOR).setTransparent(transparent);
			else
			{
				this.doReportError("Transparency is off the scale! Transparency is set to the default level (ie '0').");
				
				this.setRed(MAX_VALUE_COLOR).setGreen(MAX_VALUE_COLOR).setBlue(MAX_VALUE_COLOR).setTransparent(MIN_VALUE);
			}
		}
	}
	
	/**
	 * 
	 * Class generates the specified color and the standard (zero) transparency.
	 * 
	 * @param color <! VARIABLE >
	 * 
	 * @since   1.1_05         <! PERMANENT >
	 * @version 1.1_05         <! VARIABLE >
	 * @author  Bartosz Konkol <! VARIABLE >
	 * 
	 */
	public Color(int color)
	{
		this(color, MIN_VALUE);
	}
	
	/**
	 * 
	 * Class generates the specified color and the specified transparency.
	 * 
	 * @param color       <! VARIABLE >
	 * @param transparent <! VARIABLE >
	 * 
	 * @since   1.1_05         <! PERMANENT >
	 * @version 1.1_05         <! VARIABLE >
	 * @author  Bartosz Konkol <! VARIABLE >
	 * 
	 */
	public Color(int color, int transparent)
	{
		if(this.setColor(color) != null)
			if(transparent >= MIN_VALUE && transparent <= MAX_VALUE_TRANSPARENT)
				this.setTransparent(transparent);
			else
			{
				this.doReportError("Transparency is off the scale! Transparency is set to the default level (ie '0').");
				
				this.setTransparent(MIN_VALUE);
			}
		else
		{
			this.doReportError("The color code is off scale! The color code is set to the default level (ie '16777215').");
			
			if(transparent >= MIN_VALUE && transparent <= MAX_VALUE_TRANSPARENT)
				this.setColor(16777215).setTransparent(transparent);
			else
			{
				this.doReportError("Transparency is off the scale! Transparency is set to the default level (ie '0').");
				
				this.setColor(16777215).setTransparent(MIN_VALUE);
			}
		}
	}
	
	private int red, green, blue, transparent;
	{
		this.red =
		this.green =
		this.blue =
		this.transparent =
			(int) Conversion.changesInSign(Mathematical.mathematicalConstants("one"));
	}
	
	/**
	 * javax.jnf.lwjgl.Color.setRed(int red)
	 * @param  red The value of the red color.
	 * @action <! VARIABLE >
	 * Specify the value of the red color.
	 * @return <! VARIABLE >
	 *          Return this color class, or (in the case of incorrect parameter) is null. <br>
	 * [Color]
	 * 
	 * @since   1.1_05         <! PERMANENT >
	 * @version 1.1_05         <! VARIABLE >
	 * @author  Bartosz Konkol <! VARIABLE >
	 */
	public final Color setRed(int red)
	{
		if(red >= MIN_VALUE && red <= MAX_VALUE_COLOR)
		{
			this.red = red;
			
			return this;
		}
		
		return null;
	}
	
	/**
	 * javax.jnf.lwjgl.Color.getRed()
	 * @return <! VARIABLE >
	 *          Return the value of the red color. <br>
	 * [int]
	 * 
	 * @since   1.1_05 <! PERMANENT >
	 * @version 1.1_05 <! VARIABLE >
	 * @author  Bartosz Konkol <! VARIABLE >
	 */
	public final int getRed()
	{
		return this.red;
	}
	
	/**
	 * javax.jnf.lwjgl.Color.setGreen(int green)
	 * @param  green The value of the green color.
	 * @action <! VARIABLE >
	 * Specify the value of the green color.
	 * @return <! VARIABLE >
	 *          Return this color class, or (in the case of incorrect parameter) is null. <br>
	 * [Color]
	 * 
	 * @since   1.1_05         <! PERMANENT >
	 * @version 1.1_05         <! VARIABLE >
	 * @author  Bartosz Konkol <! VARIABLE >
	 */
	public final Color setGreen(int green)
	{
		if(green >= MIN_VALUE && green <= MAX_VALUE_COLOR)
		{
			this.green = green;
			
			return this;
		}
		
		return null;
	}
	
	/**
	 * javax.jnf.lwjgl.Color.getGreen()
	 * @return <! VARIABLE >
	 *          Return the value of the green color. <br>
	 * [int]
	 * 
	 * @since   1.1_05 <! PERMANENT >
	 * @version 1.1_05 <! VARIABLE >
	 * @author  Bartosz Konkol <! VARIABLE >
	 */
	public final int getGreen()
	{
		return this.green;
	}
	
	/**
	 * javax.jnf.lwjgl.Color.setBlue(int blue)
	 * @param  blue The value of the blue color.
	 * @action <! VARIABLE >
	 * Specify the value of the blue color.
	 * @return <! VARIABLE >
	 *          Return this color class, or (in the case of incorrect parameter) is null. <br>
	 * [Color]
	 * 
	 * @since   1.1_05         <! PERMANENT >
	 * @version 1.1_05         <! VARIABLE >
	 * @author  Bartosz Konkol <! VARIABLE >
	 */
	public final Color setBlue(int blue)
	{
		if(blue >= MIN_VALUE && blue <= MAX_VALUE_COLOR)
		{
			this.blue = blue;
			
			return this;
		}
		
		return null;
	}
	
	/**
	 * javax.jnf.lwjgl.Color.getBlue()
	 * @return <! VARIABLE >
	 *          Return the value of the blue color. <br>
	 * [int]
	 * 
	 * @since   1.1_05 <! PERMANENT >
	 * @version 1.1_05 <! VARIABLE >
	 * @author  Bartosz Konkol <! VARIABLE >
	 */
	public final int getBlue()
	{
		return this.blue;
	}
	
	/**
	 * javax.jnf.lwjgl.Color.setTransparent(int percent)
	 * @param  red The value of the percentage of transparency.
	 * @action <! VARIABLE >
	 * Specify the value of the percentage of transparency.
	 * @return <! VARIABLE >
	 *          Return this color class, or (in the case of incorrect parameter) is null. <br>
	 * [Color]
	 * 
	 * @since   1.1_05         <! PERMANENT >
	 * @version 1.1_05         <! VARIABLE >
	 * @author  Bartosz Konkol <! VARIABLE >
	 */
	public final Color setTransparent(int percent)
	{
		if(percent >= MIN_VALUE && percent <= MAX_VALUE_TRANSPARENT)
		{
			this.transparent = percent;
			
			return this;
		}
		
		return null;
	}
	
	/**
	 * javax.jnf.lwjgl.Color.getTransparent()
	 * @return <! VARIABLE >
	 *          Return the value of the percentage of transparency. <br>
	 * [int]
	 * 
	 * @since   1.1_05 <! PERMANENT >
	 * @version 1.1_05 <! VARIABLE >
	 * @author  Bartosz Konkol <! VARIABLE >
	 */
	public final int getTransparent()
	{
		return this.transparent;
	}
	
	/**
	 * javax.jnf.lwjgl.Color.doGenerateColorGL()
	 * @action <! VARIABLE >
	 * Generating a specific color in GL graphics system.
	 * @return <! VARIABLE >
	 *          If the generation of complete successfully, the result is 'true'. <br>
	 *          Otherwise, the result is 'false'.                                 <br>
	 * [boolean]
	 * 
	 * @since   1.1_05         <! PERMANENT >
	 * @version 1.1_05         <! VARIABLE >
	 * @author  Bartosz Konkol <! VARIABLE >
	 */
	public boolean doGenerateColorGL()
	{
		if(
				this.getRed() >= MIN_VALUE && this.getRed() <= MAX_VALUE_COLOR &&
				this.getGreen() >= MIN_VALUE && this.getGreen() <= MAX_VALUE_COLOR &&
				this.getBlue() >= MIN_VALUE && this.getBlue() <= MAX_VALUE_COLOR &&
				this.getTransparent() >= MIN_VALUE && this.getTransparent() <= MAX_VALUE_TRANSPARENT
			)
		{
			final float R = Float.valueOf(this.getRed()) / MAX_VALUE_COLOR;
			final float G = Float.valueOf(this.getGreen()) / MAX_VALUE_COLOR;
			final float B = Float.valueOf(this.getBlue()) / MAX_VALUE_COLOR;
			final float A = (float) (Mathematical.mathematicalConstants("one") - Float.valueOf(this.getTransparent()) / MAX_VALUE_TRANSPARENT);
			
			if(A < Mathematical.mathematicalConstants("one"))
				GL11.glColor4f(R, G, B, A);
			else
				GL11.glColor3f(R, G, B);
			
			return true;
		}
		
		return false;
	}
	
	/**
	 * javax.jnf.lwjgl.Color.setColor(int color)
	 * @param  color The value of the color.
	 * @action <! VARIABLE >
	 * Specify the value of the color.
	 * @return <! VARIABLE >
	 *          Return this color class, or (in the case of incorrect parameter) is null. <br>
	 * [Color]
	 * 
	 * @since   1.1_05         <! PERMANENT >
	 * @version 1.1_05         <! VARIABLE >
	 * @author  Bartosz Konkol <! VARIABLE >
	 */
	public final Color setColor(int color)
	{
		if(color >= MIN_VALUE && color <= 0xFFFFFF)
		{
			final int red = color / (0x10000);
			final int green = color % (0x10000) / (0x100);
			final int blue = color % (0x10000) % (0x100) / (0x1);
			
			if(
					red >= MIN_VALUE && red <= MAX_VALUE_COLOR &&
					green >= MIN_VALUE && green <= MAX_VALUE_COLOR &&
					blue >= MIN_VALUE && blue <= MAX_VALUE_COLOR
				)
			{
				this.setRed(red).setGreen(green).setBlue(blue);
				
				return this;
			}
		}
		
		return null;
	}
	
	/**
	 * javax.jnf.lwjgl.Color.getColor()
	 * @return <! VARIABLE >
	 *          Return the value of the color. <br>
	 * [int]
	 * 
	 * @since   1.1_05 <! PERMANENT >
	 * @version 1.1_05 <! VARIABLE >
	 * @author  Bartosz Konkol <! VARIABLE >
	 */
	public final int getColor()
	{
		return this.getRed()*(0x10000) + this.getGreen()*(0x100) + this.getBlue()*(0x1);
	}
	
	/**
	 * javax.jnf.lwjgl.Color.doReportError(String text)
	 * @param  text The failure message.
	 * @action <! VARIABLE >
	 * Generating information about the resulting error message.
	 * 
	 * @since   1.1_05         <! PERMANENT >
	 * @version 1.1_05         <! VARIABLE >
	 * @author  Bartosz Konkol <! VARIABLE >
	 */
	protected void doReportError(String text)
	{
		if(text != null && !text.trim().equals(""))
			System.err.println('\n' + text + '\n');
		
		try
		{
			throw new DefectLWJGL();
		}
		catch (DefectLWJGL e)
		{
			e.printStackTrace();
		}
	}
	
	/**
	 * The function of generating. <! VARIABLE >
	 * @since   1.1_05             <! PERMANENT >
	 * @version 1.1_05             <! VARIABLE >
	 * @author  Bartosz Konkol     <! VARIABLE >
	 */@Override
	public final void doGenerateGL(Generation generation)
	{
		this.doGenerateColorGL();
	}
		
	/**
	 * @since   1.1_09             <! PERMANENT >
	 * @version 1.1_09             <! VARIABLE >
	 * @author  Bartosz Konkol     <! VARIABLE >
	 */@Override
	public Color clone()
	{
        return new Color(this.getColor(), this.getTransparent());
    }
	
}
