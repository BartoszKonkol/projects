
/**
 * 
 * Java New Functions
 * JNF
 * 1.1_06
 * 
 * © Bartosz Konkol
 * 
 * javax.jnf.lwjgl.model.Model
 * 2014-05-03 - 2014-05-08 [JNF 1.1_06]
 * 
 */

package javax.jnf.lwjgl.model;

import javax.jnf.lwjgl.*;
import javax.jnf.lwjgl.exception.DefectLWJGL;
import javax.jnf.technical.constants.Mathematical;
import static org.lwjgl.opengl.GL11.*;

/**
 * 
 * Support for LWJGL<br>Model<br>Model
 * 
 * @since   1.1_06         <! PERMANENT >
 * @version 1.1_06         <! VARIABLE >
 * @author  Bartosz Konkol <! VARIABLE >
 *
 */

public class Model extends LWJGL implements GenerateGL
{
	
	/**
	 * Returns the name and version of Model.
	 * @author Bartosz Konkol
	 *
	 * @since   1.1_06 <! PERMANENT >
	 * @version 1.1_06 <! VARIABLE >
	 */
	public final static String NAME = "Model 1.1.1";
	
	private final String[] commandLines;
	
	/**
	 * The starting point of the model. <! VARIABLE >
	 * @since   1.1_06                  <! PERMANENT >
	 * @version 1.1_06                  <! VARIABLE >
	 * @author  Bartosz Konkol          <! VARIABLE >
	 */
	protected final Point3D point;
	
	/**
	 * 
	 * Class of model.
	 * 
	 * @param commandLines <! VARIABLE >
	 * @param point        <! VARIABLE >
	 * 
	 * @since   1.1_06         <! PERMANENT >
	 * @version 1.1_06         <! VARIABLE >
	 * @author  Bartosz Konkol <! VARIABLE >
	 * 
	 */
	public Model(String[] commandLines, Point3D point)
	{
		this.commandLines = commandLines;
		this.point = point;
	}
	
	/**
	 * 
	 * Class of model.
	 * 
	 * @param commandLines <! VARIABLE >
	 * @param point        <! VARIABLE >
	 * 
	 * @since   1.1_06         <! PERMANENT >
	 * @version 1.1_06         <! VARIABLE >
	 * @author  Bartosz Konkol <! VARIABLE >
	 * 
	 */
	public Model(String[] commandLines, Point2D point)
	{
		this(commandLines, new Point3D(point, (float) Mathematical.mathematicalConstants("zero")));
	}
	
	/**
	 * 
	 * Class of model.
	 * 
	 * @param commandLines <! VARIABLE >
	 * 
	 * @since   1.1_06         <! PERMANENT >
	 * @version 1.1_06         <! VARIABLE >
	 * @author  Bartosz Konkol <! VARIABLE >
	 * 
	 */
	public Model(String[] commandLines)
	{
		this(commandLines, new Point2D((float) Mathematical.mathematicalConstants("zero"), (float) Mathematical.mathematicalConstants("zero")));
	}
	
	/**
	 * Provides an array of the command line. <! VARIABLE >
	 * @since   1.1_06                        <! PERMANENT >
	 * @version 1.1_06                        <! VARIABLE >
	 * @author  Bartosz Konkol                <! VARIABLE >
	 */
	public final String[] giveCommandLines()
	{
		return this.commandLines;
	}
	
	/**
	 * The function of generating the model. <! VARIABLE >
	 * @since   1.1_06                       <! PERMANENT >
	 * @version 1.1_06                       <! VARIABLE >
	 * @author  Bartosz Konkol               <! VARIABLE >
	 */
	public void doGenerateModel() throws DefectLWJGL
	{
		for(String line : this.giveCommandLines())
		{
			line = line.trim().toLowerCase();
			
			if(!line.equals(""))
			{
				final char type = line.charAt((int) Mathematical.mathematicalConstants("zero"));
				final String command = line.substring((int) Mathematical.mathematicalConstants("one")).trim();
				
				switch(type)
				{
					case '#':
						continue;
					case ';':
					{
						final float[] values = this.giveNumbers(command);
						if(values.length > 2 && values.length < 5)
							Generation.doGenerate(new Color(Math.round(values[0]), Math.round(values[1]), Math.round(values[2]), values.length == 4 ? Math.round(values[3]) : Color.MIN_VALUE));
						else
							this.doError("The number of values is incorrect!");
						break;
					}
					case '!':
					{
						final float[] values = this.giveNumbers(command);
						if(values.length != 1)
							this.doError("The number of values is incorrect!");
						else
						{
							final int vertices = Math.round(values[0]);
							if(vertices > 0)
								switch(vertices)
								{
									case 1:
										glBegin(GL_POINTS);
										break;
									case 2:
										glBegin(GL_LINES);
										break;
									case 3:
										glBegin(GL_TRIANGLES);
										break;
									case 4:
										glBegin(GL_QUADS);
										break;
									default:
										glBegin(GL_POLYGON);
										break;
								}
							else
								this.doError("The number of vertices is less than or equal to zero!");
						}
						break;
					}
					case ':':
					{
						final float[] values = this.giveNumbers(command);
						if(values.length > 1 && values.length < 9 && values.length != 7)
						{
							if(values.length == 6)
								glNormal3f(values[3], values[4], values[5]);
							if(values.length == 8)
								glNormal3f(values[5], values[6], values[7]);
							if(values.length == 4)
								glTexCoord2f(values[2], values[3]);
							if(values.length == 5 || values.length == 8)
								glTexCoord2f(values[3], values[4]);
							final float x = this.point.getX() + values[0];
							final float y = this.point.getY() + values[1];
							float z = this.point.getZ();
							if(values.length != 2 && values.length != 4 && values.length != 7)
								z += values[2];
							glVertex3f(x, y, z);
						}
						else
							this.doError("The number of values is incorrect!");
						break;
					}
					case '$':
						if(command.equals(""))
							glEnd();
						else
							this.doError("The command is not adapted to the arguments!");
						break;
					case '|':
						/* Reserved for future use. */
						break;
					default:
						this.doError("The command '" + type + "' (text lines: '" + line + "') is incorrect!");
						break;
				}
			}
		}
	}
	
	/**
	 * Invokes the error.      <! VARIABLE >
	 * @since   1.1_06         <! PERMANENT >
	 * @version 1.1_06         <! VARIABLE >
	 * @author  Bartosz Konkol <! VARIABLE >
	 */
	protected final void doError(String message) throws DefectLWJGL
	{
		System.err.println(message);
		throw new DefectLWJGL();
	}
	
	/**
	 * Converts a collection of numbers on their arrays. <! VARIABLE >
	 * @since   1.1_06                                   <! PERMANENT >
	 * @version 1.1_06                                   <! VARIABLE >
	 * @author  Bartosz Konkol                           <! VARIABLE >
	 */
	protected final float[] giveNumbers(String collection) throws DefectLWJGL
	{
		final String[] parameters = collection.split(",");
		final int quantity = parameters.length;
		final float[] result = new float[quantity];
		try
		{
			for(int i = 0; i < quantity; i++)
				result[i] = Util.giveStringToFloat(parameters[i].trim());
		}
		catch(NumberFormatException e)
		{
			e.printStackTrace();
			this.doError("In the isolation of a set of numbers occurred mistake!");
		}
		return result;
	}
	
	/**
	 * The function of generating. <! VARIABLE >
	 * @since   1.1_06             <! PERMANENT >
	 * @version 1.1_06             <! VARIABLE >
	 * @author  Bartosz Konkol     <! VARIABLE >
	 */@Override
	public void doGenerateGL(Generation generation)
	{
		try
		{
			this.doGenerateModel();
		}
		catch (DefectLWJGL e)
		{
			e.printStackTrace();
		}
	}
	
}
