
/**
 * 
 * Java New Functions
 * JNF
 * 1.1_05
 * 
 * © Bartosz Konkol
 * 
 * javax.jnf.lwjgl.map.BlockExpand
 * 2014-03-31 - 2014-04-01 [JNF 1.1_05]
 * 2014-04-22 - 2014-05-10 [JNF 1.1_06]
 * 
 */

package javax.jnf.lwjgl.map;

import java.io.File;
import javax.jnf.lwjgl.*;
import javax.jnf.lwjgl.exception.DefectLWJGL;
import javax.jnf.lwjgl.structure.Cube;
import javax.jnf.technical.constants.Mathematical;
import static javax.jnf.lwjgl.Texture.*;
import static org.lwjgl.opengl.GL11.*;

/**
 * 
 * Support for LWJGL<br>Map<br>Block Expand
 * 		<p>
 * Class of generating the expand block on the map.
 * 
 * @since   1.1_05         <! PERMANENT >
 * @version 1.1_06         <! VARIABLE >
 * @author  Bartosz Konkol <! VARIABLE >
 * 
 * @see javax.jnf.lwjgl.map.BlockBasic
 *
 */

public class BlockExpand extends BlockBasic
{
	
	/**
	 * Type of elements of appearance (color or file). <! VARIABLE >
	 * @since   1.1_05                                 <! PERMANENT >
	 * @version 1.1_05                                 <! VARIABLE >
	 * @author  Bartosz Konkol                         <! VARIABLE >
	 */
	protected final Class<?> type;
	/**
	 * Elements of appearance (color or file). <! VARIABLE >
	 * @since   1.1_05                         <! PERMANENT >
	 * @version 1.1_05                         <! VARIABLE >
	 * @author  Bartosz Konkol                 <! VARIABLE >
	 */
	protected final Object side, top, down;
	
	/**
	 * 
	 * Class a management of generating the expand block on the map.
	 * 
	 * @param x    <! VARIABLE >
	 * @param y    <! VARIABLE >
	 * @param z    <! VARIABLE >
	 * @param type <! VARIABLE >
	 * @param side <! VARIABLE >
	 * @param top  <! VARIABLE >
	 * @param down <! VARIABLE >
	 * 
	 * @since   1.1_05         <! PERMANENT >
	 * @version 1.1_06         <! VARIABLE >
	 * @author  Bartosz Konkol <! VARIABLE >
	 * 
	 */
	public <T> BlockExpand(final float x,final  float y, final float z, final Class<T> type, final T side, final T top, final T down)
	{
		super(x, y, z);
		if(type != null && (type == Color.class || type == File.class) && (side != null && top != null && down != null))
		{
			this.down = down;
			this.side = side;
			this.top = top;
			this.type = type;
		}
		else
		{
			this.down = this.side = this.top = Color.WHITE;
			this.type = Color.class;
		}
	}
	
	/**
	 * 
	 * Class a management of generating the expand block on the map.
	 * 
	 * @param x <! VARIABLE >
	 * @param y <! VARIABLE >
	 * @param z <! VARIABLE >
	 * 
	 * @since   1.1_05         <! PERMANENT >
	 * @version 1.1_06         <! VARIABLE >
	 * @author  Bartosz Konkol <! VARIABLE >
	 * 
	 */
	public BlockExpand(final float x, final float y, final float z)
	{
		this(x, y, z, null, null, null, null);
	}
	
	/**
	 * The function of generate block. <! VARIABLE >
	 * @since   1.1_06                 <! PERMANENT >
	 * @version 1.1_06                 <! VARIABLE >
	 * @author  Bartosz Konkol         <! VARIABLE >
	 */
	public void doGenerate(final boolean topGenerate, final boolean side1Generate, final boolean side2Generate, final boolean side3Generate, final boolean side4Generate, final boolean downGenerate)
	{
		if(topGenerate || side1Generate || side2Generate || side3Generate || side4Generate || downGenerate)
		{
			Generation.doGenerate(new Cube(new Point3D(this.x, this.y, this.z), (float) Mathematical.mathematicalConstants("one"))
			{
				/**
				* The function of generation the structure. <! VARIABLE >
				* @since   1.1_06                           <! PERMANENT >
				* @version 1.1_06                           <! VARIABLE >
				* @author  Bartosz Konkol                   <! VARIABLE >
				*/@Override
				public void doGenerate()
				{
					final float x = (float) (this.getPoints3D()[0].getX()) - this.giveSideLength() / (float) Mathematical.mathematicalConstants("two");
					final float y = (float) (this.getPoints3D()[0].getY()) - this.giveSideLength() / (float) Mathematical.mathematicalConstants("two");
					final float z = this.getPoints3D()[0].getZ() - this.giveSideLength() / (float) Mathematical.mathematicalConstants("two");
					
					try
					{
						if(type == Color.class)
						{
							glBegin(GL_QUADS);
							
							if(side1Generate || side2Generate || side3Generate || side4Generate)
							{
								Generation.doGenerate((Color) side);
								
								if(side1Generate)
								{
									glVertex3f(x, y, z);
									glVertex3f(x, y + this.giveSideLength(), z);
									glVertex3f(x + this.giveSideLength(), y + this.giveSideLength(), z);
									glVertex3f(x + this.giveSideLength(), y, z);
								}

								if(side2Generate)
								{
									glVertex3f(x, y, z + this.giveSideLength());
									glVertex3f(x, y + this.giveSideLength(), z + this.giveSideLength());
									glVertex3f(x + this.giveSideLength(), y + this.giveSideLength(), z + this.giveSideLength());
									glVertex3f(x + this.giveSideLength(), y, z + this.giveSideLength());
								}

								if(side3Generate)
								{
									glVertex3f(x, y, z);
									glVertex3f(x, y, z + this.giveSideLength());
									glVertex3f(x, y + this.giveSideLength(), z + this.giveSideLength());
									glVertex3f(x, y + this.giveSideLength(), z);
								}

								if(side4Generate)
								{
									glVertex3f(x + this.giveSideLength(), y, z);
									glVertex3f(x + this.giveSideLength(), y, z + this.giveSideLength());
									glVertex3f(x + this.giveSideLength(), y + this.giveSideLength(), z + this.giveSideLength());
									glVertex3f(x + this.giveSideLength(), y + this.giveSideLength(), z);
								}
							}
							
							if(topGenerate)
							{
								Generation.doGenerate((Color) top);
								
								glVertex3f(x, y + this.giveSideLength(), z);
								glVertex3f(x, y + this.giveSideLength(), z + this.giveSideLength());
								glVertex3f(x + this.giveSideLength(), y + this.giveSideLength(), z + this.giveSideLength());
								glVertex3f(x + this.giveSideLength(), y + this.giveSideLength(), z);
							}
							
							if(downGenerate)
							{
								Generation.doGenerate((Color) down);
								
								glVertex3f(x, y, z);
								glVertex3f(x, y, z + this.giveSideLength());
								glVertex3f(x + this.giveSideLength(), y, z + this.giveSideLength());
								glVertex3f(x + this.giveSideLength(), y, z);
							}
							
							glEnd();
						}
						else if(type == File.class)
						{
							final String[] way = new String[]{"sw", "nw", "ne", "se"};
							
							if(side1Generate || side2Generate || side3Generate || side4Generate)
							{
								Generation.doGenerate(new Texture((File) side)
								{
									/**
									 * The function of behavior of the elements that are to be textured. <! VARIABLE >
									 * @since   1.1_06                                                   <! PERMANENT >
									 * @version 1.1_06                                                   <! VARIABLE >
									 * @author  Bartosz Konkol                                           <! VARIABLE >
									 */@Override
									protected void doAction() throws Exception
									{
										glBegin(GL_QUADS);

										if(side1Generate)
										{
											setMirrored(true);
											doAssignCornerGL(x, y, z, way[0]);
											doAssignCornerGL(x, y + giveSideLength(), z, way[1]);
											doAssignCornerGL(x + giveSideLength(), y + giveSideLength(), z, way[2]);
											doAssignCornerGL(x + giveSideLength(), y, z, way[3]);
										}

										if(side2Generate)
										{
											setMirrored(false);
											doAssignCornerGL(x, y, z + giveSideLength(), way[0]);
											doAssignCornerGL(x, y + giveSideLength(), z + giveSideLength(), way[1]);
											doAssignCornerGL(x + giveSideLength(), y + giveSideLength(), z + giveSideLength(), way[2]);
											doAssignCornerGL(x + giveSideLength(), y, z + giveSideLength(), way[3]);
										}

										if(side3Generate)
										{
											setMirrored(true);
											doAssignCornerGL(x, y, z, way[3]);
											doAssignCornerGL(x, y, z + giveSideLength(), way[0]);
											doAssignCornerGL(x, y + giveSideLength(), z + giveSideLength(), way[1]);
											doAssignCornerGL(x, y + giveSideLength(), z, way[2]);
										}

										if(side4Generate)
										{
											setMirrored(false);
											doAssignCornerGL(x + giveSideLength(), y, z, way[3]);
											doAssignCornerGL(x + giveSideLength(), y, z + giveSideLength(), way[0]);
											doAssignCornerGL(x + giveSideLength(), y + giveSideLength(), z + giveSideLength(), way[1]);
											doAssignCornerGL(x + giveSideLength(), y + giveSideLength(), z, way[2]);
										}
										
										glEnd();
									}
								});
							}
							
							if(topGenerate)
							{
								Generation.doGenerate(new Texture((File) top)
								{
									/**
									 * The function of behavior of the elements that are to be textured. <! VARIABLE >
									 * @since   1.1_06                                                   <! PERMANENT >
									 * @version 1.1_06                                                   <! VARIABLE >
									 * @author  Bartosz Konkol                                           <! VARIABLE >
									 */@Override
									protected void doAction() throws Exception
									{
										glBegin(GL_QUADS);
										 
										setMirrored(true);
										doAssignCornerGL(x, y + giveSideLength(), z, way[2]);
										doAssignCornerGL(x, y + giveSideLength(), z + giveSideLength(), way[3]);
										doAssignCornerGL(x + giveSideLength(), y + giveSideLength(), z + giveSideLength(), way[0]);
										doAssignCornerGL(x + giveSideLength(), y + giveSideLength(), z, way[1]);
										
										glEnd();
									}
								});
							}
							
							if(downGenerate)
							{
								Generation.doGenerate(new Texture((File) down)
								{
									/**
									 * The function of behavior of the elements that are to be textured. <! VARIABLE >
									 * @since   1.1_06                                                   <! PERMANENT >
									 * @version 1.1_06                                                   <! VARIABLE >
									 * @author  Bartosz Konkol                                           <! VARIABLE >
									 */@Override
									protected void doAction() throws Exception
									{
										glBegin(GL_QUADS);
										
										setMirrored(false);
										doAssignCornerGL(x, y, z, way[0]);
										doAssignCornerGL(x, y, z + giveSideLength(), way[1]);
										doAssignCornerGL(x + giveSideLength(), y, z + giveSideLength(), way[2]);
										doAssignCornerGL(x + giveSideLength(), y, z, way[3]);
										
										glEnd();
									}
								});
							}
						}
						else
							throw new DefectLWJGL();
					}
					catch (DefectLWJGL e)
					{
						e.printStackTrace();
					}
					finally
					{
						Generation.doGenerate(Color.WHITE);
						setMirrored(false);
					}
				}
			});
		}
	}
	
	/**
	* The function of generating. <! VARIABLE >
	* @since   1.1_05             <! PERMANENT >
	* @version 1.1_06             <! VARIABLE >
	* @author  Bartosz Konkol     <! VARIABLE >
	*/@Override
	public void doGenerateGL(final Generation generation)
	{
		this.doGenerate(true, true, true, true, true, true);
	}
	
}
