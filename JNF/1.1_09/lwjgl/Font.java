
/**
 * 
 * Java New Functions
 * JNF
 * 1.1_05
 * 
 * © Bartosz Konkol
 * 
 * javax.jnf.lwjgl.Font
 * 2014-04-04 - 2014-04-05 [JNF 1.1_05]
 * 2014-04-27 - 2014-04-27 [JNF 1.1_06]
 * 
 */

package javax.jnf.lwjgl;

import java.io.File;
import javax.jnf.technical.constants.Mathematical;
import javax.jnf.technical.maths.Conversion;
import static org.lwjgl.opengl.GL11.*;

/**
 * 
 * Support for LWJGL<br>Font
 * 		<p>
 * Class of font tools.
 * 
 * @since   1.1_05         <! PERMANENT >
 * @version 1.1_06         <! VARIABLE >
 * @author  Bartosz Konkol <! VARIABLE >
 *
 */

public class Font extends LWJGL implements GenerateGL
{
	
	/**
	 * The font file.          <! VARIABLE >
	 * @since   1.1_05         <! PERMANENT >
	 * @version 1.1_05         <! VARIABLE >
	 * @author  Bartosz Konkol <! VARIABLE >
	 */
	protected final File font;
	/**
	 * Dimensions of the canvas. <! VARIABLE >
	 * @since   1.1_05           <! PERMANENT >
	 * @version 1.1_05           <! VARIABLE >
	 * @author  Bartosz Konkol   <! VARIABLE >
	 */
	protected final int canvasHeight, canvasWidth;
	/**
	 * Sizes of displayed the letters. <! VARIABLE >
	 * @since   1.1_05                 <! PERMANENT >
	 * @version 1.1_05                 <! VARIABLE >
	 * @author  Bartosz Konkol         <! VARIABLE >
	 */
	protected final float sizeHeight, sizeWidth;
	/**
	 * Volume of displayed the letters. <! VARIABLE >
	 * @since   1.1_05                  <! PERMANENT >
	 * @version 1.1_05                  <! VARIABLE >
	 * @author  Bartosz Konkol          <! VARIABLE >
	 */
	protected final float length, breadth;
	/**
	 * Starting position of the text. <! VARIABLE >
	 * @since   1.1_05                <! PERMANENT >
	 * @version 1.1_05                <! VARIABLE >
	 * @author  Bartosz Konkol        <! VARIABLE >
	 */
	protected final float positionX, positionY, positionZ;
	/**
	 * The displayed text.     <! VARIABLE >
	 * @since   1.1_05         <! PERMANENT >
	 * @version 1.1_05         <! VARIABLE >
	 * @author  Bartosz Konkol <! VARIABLE >
	 */
	protected final String text;
	
	/**
	 * 
	 * Class of management the font.
	 * 
	 * @param font         <! VARIABLE >
	 * @param canvasHeight <! VARIABLE >
	 * @param canvasWidth  <! VARIABLE >
	 * @param sizeHeight   <! VARIABLE >
	 * @param sizeWidth    <! VARIABLE >
	 * @param length       <! VARIABLE >
	 * @param breadth      <! VARIABLE >
	 * @param positionX    <! VARIABLE >
	 * @param positionY    <! VARIABLE >
	 * @param positionZ    <! VARIABLE >
	 * @param text         <! VARIABLE >
	 * 
	 * @since   1.1_05         <! PERMANENT >
	 * @version 1.1_05         <! VARIABLE >
	 * @author  Bartosz Konkol <! VARIABLE >
	 * 
	 */
	public Font(final File font, final int canvasHeight, final int canvasWidth, final float sizeHeight, final float sizeWidth, final float length, final float breadth, final float positionX, final float positionY, final float positionZ, final String text)
	{
		this.breadth = breadth;
		this.canvasHeight = canvasHeight;
		this.canvasWidth = canvasWidth;
		this.font = font;
		this.length = length;
		this.positionX = positionX;
		this.positionY = positionY;
		this.positionZ = positionZ;
		this.sizeHeight = (float) Conversion.changesInSign(sizeHeight);
		this.sizeWidth = sizeWidth;
		this.text = text.trim().replace('\t', '\u0020').replace('\r', '\n');
	}
	
	/**
	 * 
	 * Class of management the font.
	 * 
	 * @param font       <! VARIABLE >
	 * @param sizeHeight <! VARIABLE >
	 * @param sizeWidth  <! VARIABLE >
	 * @param length     <! VARIABLE >
	 * @param breadth    <! VARIABLE >
	 * @param positionX  <! VARIABLE >
	 * @param positionY  <! VARIABLE >
	 * @param positionZ  <! VARIABLE >
	 * @param text       <! VARIABLE >
	 * 
	 * @since   1.1_05         <! PERMANENT >
	 * @version 1.1_05         <! VARIABLE >
	 * @author  Bartosz Konkol <! VARIABLE >
	 * 
	 */
	public Font(final File font, final float sizeHeight, final float sizeWidth, final float length, final float breadth, final float positionX, final float positionY, final float positionZ, final String text)
	{
		this(font, 0x10, 0x10, sizeHeight, sizeWidth, length, breadth, positionX, positionY, positionZ, text);
	}
	
	/**
	 * 
	 * Class of management the font.
	 * 
	 * @param font       <! VARIABLE >
	 * @param sizeHeight <! VARIABLE >
	 * @param sizeWidth  <! VARIABLE >
	 * @param length     <! VARIABLE >
	 * @param breadth    <! VARIABLE >
	 * @param positionX  <! VARIABLE >
	 * @param positionY  <! VARIABLE >
	 * @param text       <! VARIABLE >
	 * 
	 * @since   1.1_05         <! PERMANENT >
	 * @version 1.1_05         <! VARIABLE >
	 * @author  Bartosz Konkol <! VARIABLE >
	 * 
	 */
	public Font(final File font, final float sizeHeight, final float sizeWidth, final float length, final float breadth, final float positionX, final float positionY, final String text)
	{
		this(font, sizeHeight, sizeWidth, length, breadth, positionX, positionY, (float) Mathematical.mathematicalConstants("zero"), text);
	}
	
	/**
	 * 
	 * Class of management the font.
	 * 
	 * @param font         <! VARIABLE >
	 * @param canvasHeight <! VARIABLE >
	 * @param canvasWidth  <! VARIABLE >
	 * @param sizeHeight   <! VARIABLE >
	 * @param sizeWidth    <! VARIABLE >
	 * @param positionX    <! VARIABLE >
	 * @param positionY    <! VARIABLE >
	 * @param positionZ    <! VARIABLE >
	 * @param text         <! VARIABLE >
	 * 
	 * @since   1.1_05         <! PERMANENT >
	 * @version 1.1_05         <! VARIABLE >
	 * @author  Bartosz Konkol <! VARIABLE >
	 * 
	 */
	public Font(final File font, final int canvasHeight, final int canvasWidth, final float sizeHeight, final float sizeWidth, final float positionX, final float positionY, final float positionZ, final String text)
	{
		this(font, canvasHeight, canvasWidth, sizeHeight, sizeWidth, (float) Mathematical.mathematicalConstants("one"), 0.5F, positionX, positionY, positionZ, text);
	}
	
	/**
	 * 
	 * Class of management the font.
	 * 
	 * @param font       <! VARIABLE >
	 * @param sizeHeight <! VARIABLE >
	 * @param sizeWidth  <! VARIABLE >
	 * @param positionX  <! VARIABLE >
	 * @param positionY  <! VARIABLE >
	 * @param positionZ  <! VARIABLE >
	 * @param text       <! VARIABLE >
	 * 
	 * @since   1.1_05         <! PERMANENT >
	 * @version 1.1_05         <! VARIABLE >
	 * @author  Bartosz Konkol <! VARIABLE >
	 * 
	 */
	public Font(final File font, final float sizeHeight, final float sizeWidth, final float positionX, final float positionY, final float positionZ, final String text)
	{
		this(font, 0x10, 0x10, sizeHeight, sizeWidth, positionX, positionY, positionZ, text);
	}
	
	/**
	 * 
	 * Class of management the font.
	 * 
	 * @param font       <! VARIABLE >
	 * @param sizeHeight <! VARIABLE >
	 * @param sizeWidth  <! VARIABLE >
	 * @param positionX  <! VARIABLE >
	 * @param positionY  <! VARIABLE >
	 * @param text       <! VARIABLE >
	 * 
	 * @since   1.1_05         <! PERMANENT >
	 * @version 1.1_05         <! VARIABLE >
	 * @author  Bartosz Konkol <! VARIABLE >
	 * 
	 */
	public Font(final File font, final float sizeHeight, final float sizeWidth, final float positionX, final float positionY, final String text)
	{
		this(font, sizeHeight, sizeWidth, positionX, positionY, (float) Mathematical.mathematicalConstants("zero"), text);
	}
	
	/**
	 * 
	 * Class of management the font.
	 * 
	 * @param font      <! VARIABLE >
	 * @param positionX <! VARIABLE >
	 * @param positionY <! VARIABLE >
	 * @param positionZ <! VARIABLE >
	 * @param text      <! VARIABLE >
	 * 
	 * @since   1.1_05         <! PERMANENT >
	 * @version 1.1_05         <! VARIABLE >
	 * @author  Bartosz Konkol <! VARIABLE >
	 * 
	 */
	public Font(final File font, final float positionX, final float positionY, final float positionZ, final String text)
	{
		this(font, 0x20F, 0x20F, positionX, positionY, positionZ, text);
	}
	
	/**
	 * 
	 * Class of management the font.
	 * 
	 * @param font         <! VARIABLE >
	 * @param canvasHeight <! VARIABLE >
	 * @param canvasWidth  <! VARIABLE >
	 * @param sizeHeight   <! VARIABLE >
	 * @param sizeWidth    <! VARIABLE >
	 * @param length       <! VARIABLE >
	 * @param breadth      <! VARIABLE >
	 * @param positionX    <! VARIABLE >
	 * @param positionY    <! VARIABLE >
	 * @param text         <! VARIABLE >
	 * 
	 * @since   1.1_05         <! PERMANENT >
	 * @version 1.1_05         <! VARIABLE >
	 * @author  Bartosz Konkol <! VARIABLE >
	 * 
	 */
	public Font(final File font, final int canvasHeight, final int canvasWidth, final float sizeHeight, final float sizeWidth, final float length, final float breadth, final float positionX, final float positionY, final String text)
	{
		this(font, canvasHeight, canvasWidth, sizeHeight, sizeWidth, length, breadth, positionX, positionY, (float) Mathematical.mathematicalConstants("zero"), text);
	}
	
	/**
	 * 
	 * Class of management the font.
	 * 
	 * @param font         <! VARIABLE >
	 * @param canvasHeight <! VARIABLE >
	 * @param canvasWidth  <! VARIABLE >
	 * @param sizeHeight   <! VARIABLE >
	 * @param sizeWidth    <! VARIABLE >
	 * @param length       <! VARIABLE >
	 * @param breadth      <! VARIABLE >
	 * @param text         <! VARIABLE >
	 * 
	 * @since   1.1_05         <! PERMANENT >
	 * @version 1.1_05         <! VARIABLE >
	 * @author  Bartosz Konkol <! VARIABLE >
	 * 
	 */
	public Font(final File font, final int canvasHeight, final int canvasWidth, final float sizeHeight, final float sizeWidth, final float length, final float breadth, final String text)
	{
		this(font, canvasHeight, canvasWidth, sizeHeight, sizeWidth, length, breadth, (float) Mathematical.mathematicalConstants("zero"), (float) Mathematical.mathematicalConstants("zero"), text);
	}
	
	/**
	 * 
	 * Class of management the font.
	 * 
	 * @param font         <! VARIABLE >
	 * @param canvasHeight <! VARIABLE >
	 * @param canvasWidth  <! VARIABLE >
	 * @param sizeHeight   <! VARIABLE >
	 * @param sizeWidth    <! VARIABLE >
	 * @param text         <! VARIABLE >
	 * 
	 * @since   1.1_05         <! PERMANENT >
	 * @version 1.1_05         <! VARIABLE >
	 * @author  Bartosz Konkol <! VARIABLE >
	 * 
	 */
	public Font(final File font, final int canvasHeight, final int canvasWidth, final float sizeHeight, final float sizeWidth, final String text)
	{
		this(font, canvasHeight, canvasWidth, sizeHeight, sizeWidth, (float) Mathematical.mathematicalConstants("one"), 0.5F, text);
	}
	
	/**
	 * 
	 * Class of management the font.
	 * 
	 * @param font       <! VARIABLE >
	 * @param sizeHeight <! VARIABLE >
	 * @param sizeWidth  <! VARIABLE >
	 * @param text       <! VARIABLE >
	 * 
	 * @since   1.1_05         <! PERMANENT >
	 * @version 1.1_05         <! VARIABLE >
	 * @author  Bartosz Konkol <! VARIABLE >
	 * 
	 */
	public Font(final File font, final float sizeHeight, final float sizeWidth, final String text)
	{
		this(font, 0x10, 0x10, sizeHeight, sizeWidth, text);
	}
	
	/**
	 * 
	 * Class of management the font.
	 * 
	 * @param font <! VARIABLE >
	 * @param text <! VARIABLE >
	 * 
	 * @since   1.1_05         <! PERMANENT >
	 * @version 1.1_05         <! VARIABLE >
	 * @author  Bartosz Konkol <! VARIABLE >
	 * 
	 */
	public Font(final File font, final String text)
	{
		this(font, 0x20F, 0x20F, text);
	}
	
	/**
	 * javax.jnf.lwjgl.Font.doGenerateFont()
	 * @action <! VARIABLE >
	 * Generating a specific text using the configured font.
	 * 
	 * @since   1.1_05         <! PERMANENT >
	 * @version 1.1_05         <! VARIABLE >
	 * @author  Bartosz Konkol <! VARIABLE >
	 */
	public void doGenerateFont()
	{
		final String[] lines = this.text.split("\n", 2);
		
		Generation.doGenerate(new Texture(this.font)
		{
			/**
			 * The function of behavior of the elements that are to be textured. <! VARIABLE >
			 * @since   1.1_05                                                   <! PERMANENT >
			 * @version 1.1_05                                                   <! VARIABLE >
			 * @author  Bartosz Konkol                                           <! VARIABLE >
			 */@Override
			protected void doAction() throws Exception
			{
		        glBegin(GL_QUADS);
		        for(int i = (int) Mathematical.mathematicalConstants("zero"); i < lines[0].length(); i++)
		        {
		            final char character = lines[0].charAt(i);
		            final float size = (float) Mathematical.mathematicalConstants("one") / canvasHeight;
		            final float x = (int) (character % canvasWidth) * size;
		            final float y = (int) (character / canvasWidth) * size;
		            
		            glTexCoord2f(x, y + size);
		            glVertex3f(i * sizeWidth * breadth + positionX, positionY, positionZ);
		            
		            glTexCoord2f(x + size, y + size);
		            glVertex3f((i + (float) Mathematical.mathematicalConstants("one")) * sizeWidth * breadth + positionX, positionY, positionZ);
		            
		            glTexCoord2f(x + size, y);
		            glVertex3f((i + (float) Mathematical.mathematicalConstants("one")) * sizeWidth * breadth + positionX, positionY + sizeHeight * length, positionZ);
		            
		            glTexCoord2f(x, y);
		            glVertex3f(i * sizeWidth * breadth + positionX, positionY + sizeHeight * length, positionZ);
		        }
		        glEnd();
			}
			
		});
		
		if(lines.length > Mathematical.mathematicalConstants("one"))
			Generation.doGenerate(new Font(this.font, this.canvasHeight, this.canvasWidth, (float) Conversion.changesInSign(this.sizeHeight), this.sizeWidth, this.length, this.breadth, this.positionX, this.positionY - this.sizeHeight, this.positionZ, lines[1]));
	}
	
	/**
	 * The function of generating. <! VARIABLE >
	 * @since   1.1_05             <! PERMANENT >
	 * @version 1.1_05             <! VARIABLE >
	 * @author  Bartosz Konkol     <! VARIABLE >
	 */@Override
	public final void doGenerateGL(Generation generation)
	{
		this.doGenerateFont();
	}
	
}
