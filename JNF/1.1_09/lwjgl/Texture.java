
/**
 * 
 * Java New Functions
 * JNF
 * 1.1_05
 * 
 * © Bartosz Konkol
 * 
 * javax.jnf.lwjgl.Texture
 * 2014-03-18 - 2014-03-31 [JNF 1.1_05]
 * 2014-04-27 - 2014-05-06 [JNF 1.1_06]
 * 
 */

package javax.jnf.lwjgl;

import java.awt.image.BufferedImage;
import java.io.File;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;
import javax.imageio.ImageIO;
import javax.jnf.lwjgl.exception.DefectLWJGL;
import javax.jnf.technical.constants.Mathematical;
import javax.jnf.technical.maths.Conversion;
import org.lwjgl.BufferUtils;
import static org.lwjgl.opengl.GL11.*;

/**
 * 
 * Support for LWJGL<br>Texture
 * 		<p>
 * Class abstract of management the texture components.
 * 
 * @since   1.1_05         <! PERMANENT >
 * @version 1.1_06         <! VARIABLE >
 * @author  Bartosz Konkol <! VARIABLE >
 *
 */

public abstract class Texture extends LWJGL implements GenerateGL
{
	
	/**
	 * 
	 * Class allows to define texture.
	 * 
	 * @param texture <! VARIABLE >
	 * 
	 * @since   1.1_05         <! PERMANENT >
	 * @version 1.1_05         <! VARIABLE >
	 * @author  Bartosz Konkol <! VARIABLE >
	 * 
	 */
	public Texture(File file)
	{
		this.setFile(file);
	}
	
	private File file;
	private int id;
	
	private static boolean mirrored;
	
	/**
	 * Specify the file texture. <! VARIABLE >
	 * @since   1.1_05           <! PERMANENT >
	 * @version 1.1_05           <! VARIABLE >
	 * @author  Bartosz Konkol   <! VARIABLE >
	 */
	public final Texture setFile(File file)
	{
		this.file = file;
		return this;
	}
	
	/**
	 * Return the file texture. <! VARIABLE >
	 * @since   1.1_05           <! PERMANENT >
	 * @version 1.1_05           <! VARIABLE >
	 * @author  Bartosz Konkol   <! VARIABLE >
	 */
	public final File getFile()
	{
		return this.file;
	}
	
	/**
	 * Specify the ID texture. <! VARIABLE >
	 * @since   1.1_05         <! PERMANENT >
	 * @version 1.1_05         <! VARIABLE >
	 * @author  Bartosz Konkol <! VARIABLE >
	 */
	public final Texture setID(int id)
	{
		this.id = id;
		return this;
	}
	
	/**
	 * Return the ID texture.  <! VARIABLE >
	 * @since   1.1_05         <! PERMANENT >
	 * @version 1.1_05         <! VARIABLE >
	 * @author  Bartosz Konkol <! VARIABLE >
	 */
	public final int getID()
	{
		return this.id;
	}
	
	/**
	 * Specify the mirror reflection of corner texture. <! VARIABLE >
	 * @since   1.1_05                                  <! PERMANENT >
	 * @version 1.1_05                                  <! VARIABLE >
	 * @author  Bartosz Konkol                          <! VARIABLE >
	 */
	public static final void setMirrored(boolean mirrored)
	{
		Texture.mirrored = mirrored;
	}
	
	/**
	 * Return the mirror reflection of corner texture. <! VARIABLE >
	 * @since   1.1_05                                 <! PERMANENT >
	 * @version 1.1_05                                 <! VARIABLE >
	 * @author  Bartosz Konkol                         <! VARIABLE >
	 */
	public static final boolean getMirrored()
	{
		return mirrored;
	}
	
	/**
	 * javax.jnf.lwjgl.Texture.doGenerateTextureGL() 
	 * @action <! VARIABLE >
	 * Generates the texture.
	 * 
	 * @since   1.1_05         <! PERMANENT >
	 * @version 1.1_05         <! VARIABLE >
	 * @author  Bartosz Konkol <! VARIABLE >
	 * 
	 * @throws Exception
	 */
	public void doGenerateTextureGL() throws Exception
	{
		glPushMatrix();
		
		final BufferedImage image = ImageIO.read(this.getFile());
		final int[] pixels = new int[image.getWidth() * image.getHeight()];
		image.getRGB(0, 0, image.getWidth(), image.getHeight(), pixels, 0, image.getWidth());
		final ByteBuffer buffer = BufferUtils.createByteBuffer(image.getWidth() * image.getHeight() * 4);
		for(int y = 0; y < image.getWidth(); y++)
			for(int x = 0; x < image.getHeight(); x++)
			{
				final int pixel = pixels[x + y * image.getWidth()];
				buffer.put((byte) ((pixel >> 0x10) & 0xFF));
                buffer.put((byte) ((pixel >> 0x8)  & 0xFF));
                buffer.put((byte) ((pixel >> 0x0)  & 0xFF));
                buffer.put((byte) ((pixel >> 0x18) & 0xFF));
			}
		buffer.flip();
		
		this.setID(glGenTextures());
		glBindTexture(GL_TEXTURE_2D, this.getID());
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
        glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA8, image.getWidth(), image.getHeight(), 0, GL_RGBA, GL_UNSIGNED_BYTE, buffer);
		
        new Color().doGenerateColorGL();
		this.doAction();
		new Color().doGenerateColorGL();
		
		glDeleteTextures(this.getID());
		
		glPopMatrix();
	}
	
	/**
	 * The function of generating. <! VARIABLE >
	 * @since   1.1_05             <! PERMANENT >
	 * @version 1.1_05             <! VARIABLE >
	 * @author  Bartosz Konkol     <! VARIABLE >
	 */@Override
	public final void doGenerateGL(Generation generation)
	{
		try
		{
			this.doGenerateTextureGL();
		}
		catch (Exception e0)
		{
			System.err.println('\n' + e0.toString() + '\n');
			
			try
			{
				throw new DefectLWJGL();
			}
			catch (DefectLWJGL e1)
			{
				e1.printStackTrace();
			}
		}
	}
	
	/**
	* Returns a list of corner of the texture and the her component's. <! VARIABLE >
	* @since   1.1_05                                                  <! PERMANENT >
	* @version 1.1_05                                                  <! VARIABLE >
	* @author  Bartosz Konkol                                          <! VARIABLE >
	*/@SuppressWarnings("unchecked")
	public static final <N extends Number> List<N> giveCornerListGL(final N x, final N y, final N z, final String align) throws DefectLWJGL
	{
		final List<N> parameters = new ArrayList<N>();
		
		parameters.add(x);
		parameters.add(y);
		parameters.add(z);
		
		N xTexture, yTexture;
		
		if(align != null)
		{
			if(align.length() != 2)
				throw new DefectLWJGL();
			
			final N zero =        (N) Double.valueOf(Mathematical.mathematicalConstants("zero"));
			final N onePositive = (N) Double.valueOf(Mathematical.mathematicalConstants("one"));
			final N oneNegative = (N) Double.valueOf(Conversion.changesInSign(Util.giveStringToDouble(String.valueOf(onePositive))));
			
			xTexture = yTexture = oneNegative;
			
			for(int i = 0; i < align.length(); i++)
				switch(align.toLowerCase().charAt(i))
				{
					case 'n':
						yTexture = onePositive;
						break;
					case 's':
						yTexture = zero;
						break;
					case 'w':
						xTexture = zero;
						break;
					case 'e':
						xTexture = onePositive;
						break;
				}
			
			if(xTexture == oneNegative || yTexture == oneNegative)
				throw new DefectLWJGL();
		}
		else
		{
			xTexture = x;
			yTexture = y;
		}
		
		if(getMirrored())
			parameters.add(xTexture);
		else
			parameters.add((N) Double.valueOf(Conversion.changesInSign(Util.giveStringToDouble(String.valueOf(xTexture)))));
		parameters.add((N) Double.valueOf(Conversion.changesInSign(Util.giveStringToDouble(String.valueOf(yTexture)))));
		
		return parameters;
	}
	
	/**
	 * Assigns the corner of the texture and the component. <! VARIABLE >
	 * @since   1.1_05                                      <! PERMANENT >
	 * @version 1.1_05                                      <! VARIABLE >
	 * @author  Bartosz Konkol                              <! VARIABLE >
	 */
	public static final <N extends Number> void doAssignCornerGL(List<N> parameters) throws DefectLWJGL
	{
		glTexCoord2d(Util.giveStringToDouble(String.valueOf(parameters.get(3))), Util.giveStringToDouble(String.valueOf(parameters.get(4))));
		glVertex3d(Util.giveStringToDouble(String.valueOf(parameters.get(0))), Util.giveStringToDouble(String.valueOf(parameters.get(1))), Util.giveStringToDouble(String.valueOf(parameters.get(2))));
	}
	
	/**
	 * Assigns the corner of the texture and the component. <! VARIABLE >
	 * @since   1.1_05                                      <! PERMANENT >
	 * @version 1.1_05                                      <! VARIABLE >
	 * @author  Bartosz Konkol                              <! VARIABLE >
	 */
	public static final void doAssignCornerGL(final int x, final int y) throws DefectLWJGL
	{
		doAssignCornerGL(x, y, null);
	}
	
	/**
	 * Assigns the corner of the texture and the component. <! VARIABLE >
	 * @since   1.1_05                                      <! PERMANENT >
	 * @version 1.1_05                                      <! VARIABLE >
	 * @author  Bartosz Konkol                              <! VARIABLE >
	 */
	public static final void doAssignCornerGL(final int x, final int y, final String align) throws DefectLWJGL
	{
		final List<Integer> parameters = giveCornerListGL(x, y, (int) Mathematical.mathematicalConstants("zero"), align);
		
		glTexCoord2f(Util.giveStringToFloat(String.valueOf(parameters.get(3))), Util.giveStringToFloat(String.valueOf(parameters.get(4))));
		glVertex2i(parameters.get(0), parameters.get(1));
	}
	
	/**
	 * Assigns the corner of the texture and the component. <! VARIABLE >
	 * @since   1.1_05                                      <! PERMANENT >
	 * @version 1.1_05                                      <! VARIABLE >
	 * @author  Bartosz Konkol                              <! VARIABLE >
	 */
	public static final void doAssignCornerGL(final int x, final int y, final int z) throws DefectLWJGL
	{
		doAssignCornerGL(x, y, z, null);
	}
	
	/**
	 * Assigns the corner of the texture and the component. <! VARIABLE >
	 * @since   1.1_05                                      <! PERMANENT >
	 * @version 1.1_05                                      <! VARIABLE >
	 * @author  Bartosz Konkol                              <! VARIABLE >
	 */
	public static final void doAssignCornerGL(final int x, final int y, final int z, final String align) throws DefectLWJGL
	{
		final List<Integer> parameters = giveCornerListGL(x, y, z, align);
		
		glTexCoord2f(Util.giveStringToFloat(String.valueOf(parameters.get(3))), Util.giveStringToFloat(String.valueOf(parameters.get(4))));
		glVertex3i(parameters.get(0), parameters.get(1), parameters.get(2));
	}
	
	/**
	 * Assigns the corner of the texture and the component. <! VARIABLE >
	 * @since   1.1_05                                      <! PERMANENT >
	 * @version 1.1_05                                      <! VARIABLE >
	 * @author  Bartosz Konkol                              <! VARIABLE >
	 */
	public static final void doAssignCornerGL(final float x, final float y) throws DefectLWJGL
	{
		doAssignCornerGL(x, y, null);
	}
	
	/**
	 * Assigns the corner of the texture and the component. <! VARIABLE >
	 * @since   1.1_05                                      <! PERMANENT >
	 * @version 1.1_05                                      <! VARIABLE >
	 * @author  Bartosz Konkol                              <! VARIABLE >
	 */
	public static final void doAssignCornerGL(final float x, final float y, final String align) throws DefectLWJGL
	{
		final List<Float> parameters = giveCornerListGL(x, y, (float) Mathematical.mathematicalConstants("zero"), align);
		
		glTexCoord2f(Util.giveStringToFloat(String.valueOf(parameters.get(3))), Util.giveStringToFloat(String.valueOf(parameters.get(4))));
		glVertex2f(parameters.get(0), parameters.get(1));
	}
	
	/**
	 * Assigns the corner of the texture and the component. <! VARIABLE >
	 * @since   1.1_05                                      <! PERMANENT >
	 * @version 1.1_05                                      <! VARIABLE >
	 * @author  Bartosz Konkol                              <! VARIABLE >
	 */
	public static final void doAssignCornerGL(final float x, final float y, final float z) throws DefectLWJGL
	{
		doAssignCornerGL(x, y, z, null);
	}
	
	/**
	 * Assigns the corner of the texture and the component. <! VARIABLE >
	 * @since   1.1_05                                      <! PERMANENT >
	 * @version 1.1_05                                      <! VARIABLE >
	 * @author  Bartosz Konkol                              <! VARIABLE >
	 */
	public static final void doAssignCornerGL(final float x, final float y, final float z, final String align) throws DefectLWJGL
	{
		final List<Float> parameters = giveCornerListGL(x, y, z, align);
		
		glTexCoord2f(Util.giveStringToFloat(String.valueOf(parameters.get(3))), Util.giveStringToFloat(String.valueOf(parameters.get(4))));
		glVertex3f(parameters.get(0), parameters.get(1), parameters.get(2));
	}
	
	/**
	 * Assigns the corner of the texture and the component. <! VARIABLE >
	 * @since   1.1_05                                      <! PERMANENT >
	 * @version 1.1_05                                      <! VARIABLE >
	 * @author  Bartosz Konkol                              <! VARIABLE >
	 */
	public static final void doAssignCornerGL(final double x, final double y) throws DefectLWJGL
	{
		doAssignCornerGL(x, y, null);
	}
	
	/**
	 * Assigns the corner of the texture and the component. <! VARIABLE >
	 * @since   1.1_05                                      <! PERMANENT >
	 * @version 1.1_05                                      <! VARIABLE >
	 * @author  Bartosz Konkol                              <! VARIABLE >
	 */
	public static final void doAssignCornerGL(final double x, final double y, final String align) throws DefectLWJGL
	{
		final List<Double> parameters = giveCornerListGL(x, y, Mathematical.mathematicalConstants("zero"), align);
		
		glTexCoord2d(parameters.get(3), parameters.get(4));
		glVertex2d(parameters.get(0), parameters.get(1));
	}
	
	/**
	 * Assigns the corner of the texture and the component. <! VARIABLE >
	 * @since   1.1_05                                      <! PERMANENT >
	 * @version 1.1_05                                      <! VARIABLE >
	 * @author  Bartosz Konkol                              <! VARIABLE >
	 */
	public static final void doAssignCornerGL(final double x, final double y, final double z) throws DefectLWJGL
	{
		doAssignCornerGL(x, y, z, null);
	}
	
	/**
	 * Assigns the corner of the texture and the component. <! VARIABLE >
	 * @since   1.1_05                                      <! PERMANENT >
	 * @version 1.1_05                                      <! VARIABLE >
	 * @author  Bartosz Konkol                              <! VARIABLE >
	 */
	public static final void doAssignCornerGL(final double x, final double y, final double z, final String align) throws DefectLWJGL
	{
		final List<Double> parameters = giveCornerListGL(x, y, z, align);
		
		glTexCoord2d(parameters.get(3), parameters.get(4));
		glVertex3d(parameters.get(0), parameters.get(1), parameters.get(2));
	}
	
	/**
	 * The function of behavior of the elements that are to be textured. <! VARIABLE >
	 * @since   1.1_05                                                   <! PERMANENT >
	 * @version 1.1_05                                                   <! VARIABLE >
	 * @author  Bartosz Konkol                                           <! VARIABLE >
	 */
	protected abstract void doAction() throws Exception;
	
}
