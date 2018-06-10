
/**
 * 
 * Java New Functions
 * JNF
 * 1.1_05
 * 
 * © Bartosz Konkol
 * 
 * javax.jnf.lwjgl.map.BlockBasic
 * 2014-03-30 - 2014-03-30 [JNF 1.1_05]
 * 2014-04-22 - 2014-05-10 [JNF 1.1_06]
 * 
 */

package javax.jnf.lwjgl.map;

import javax.jnf.lwjgl.*;
import javax.jnf.lwjgl.exception.DefectLWJGL;
import javax.jnf.lwjgl.structure.Cube;
import javax.jnf.technical.constants.Mathematical;
import static javax.jnf.lwjgl.Texture.*;
import static org.lwjgl.opengl.GL11.*;

/**
 * 
 * Support for LWJGL<br>Map<br>Block Basic
 * 		<p>
 * Class of generating the basic block on the map.
 * 
 * @since   1.1_05         <! PERMANENT >
 * @version 1.1_06         <! VARIABLE >
 * @author  Bartosz Konkol <! VARIABLE >
 *
 */

public class BlockBasic extends LWJGL implements GenerateGL
{
	
	/**
	 * Positions of the block. <! VARIABLE >
	 * @since   1.1_05         <! PERMANENT >
	 * @version 1.1_05         <! VARIABLE >
	 * @author  Bartosz Konkol <! VARIABLE >
	 */
	protected final float x, y, z;
	
	/**
	 * 
	 * Class a management of generating the basic block on the map.
	 * 
	 * @param x <! VARIABLE >
	 * @param y <! VARIABLE >
	 * @param z <! VARIABLE >
	 * 
	 * @since   1.1_05         <! PERMANENT >
	 * @version 1.1_05         <! VARIABLE >
	 * @author  Bartosz Konkol <! VARIABLE >
	 * 
	 */
	public BlockBasic(float x, float y, float z)
	{
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	/**
	* The function of generating. <! VARIABLE >
	* @since   1.1_05             <! PERMANENT >
	* @version 1.1_06             <! VARIABLE >
	* @author  Bartosz Konkol     <! VARIABLE >
	*/@Override
	public void doGenerateGL(Generation generation)
	{
		Generation.doGenerate(new Cube(new Point3D(this.x, this.y, this.z), (float) Mathematical.mathematicalConstants("one"))
		{
			/**
			* The function of generation the structure. <! VARIABLE >
			* @since   1.1_05                           <! PERMANENT >
			* @version 1.1_05                           <! VARIABLE >
			* @author  Bartosz Konkol                   <! VARIABLE >
			*/@Override
			public void doGenerate()
			{
				final float x = (float) (this.getPoints3D()[0].getX()) - this.giveSideLength() / (float) Mathematical.mathematicalConstants("two");
				final float y = (float) (this.getPoints3D()[0].getY()) - this.giveSideLength() / (float) Mathematical.mathematicalConstants("two");
				final float z = this.getPoints3D()[0].getZ() - this.giveSideLength() / (float) Mathematical.mathematicalConstants("two");
				
				String[] way = new String[]{"sw", "nw", "ne", "se"};
				
				glBegin(GL_QUADS);
				
				try
				{
					setMirrored(true);
					doAssignCornerGL(x, y, z, way[0]);
					doAssignCornerGL(x, y + this.giveSideLength(), z, way[1]);
					doAssignCornerGL(x + this.giveSideLength(), y + this.giveSideLength(), z, way[2]);
					doAssignCornerGL(x + this.giveSideLength(), y, z, way[3]);
					
					setMirrored(false);
					doAssignCornerGL(x, y, z + this.giveSideLength(), way[0]);
					doAssignCornerGL(x, y + this.giveSideLength(), z + this.giveSideLength(), way[1]);
					doAssignCornerGL(x + this.giveSideLength(), y + this.giveSideLength(), z + this.giveSideLength(), way[2]);
					doAssignCornerGL(x + this.giveSideLength(), y, z + this.giveSideLength(), way[3]);
					
					setMirrored(false);
					doAssignCornerGL(x, y, z, way[0]);
					doAssignCornerGL(x, y, z + this.giveSideLength(), way[1]);
					doAssignCornerGL(x + this.giveSideLength(), y, z + this.giveSideLength(), way[2]);
					doAssignCornerGL(x + this.giveSideLength(), y, z, way[3]);
					
					setMirrored(true);
					doAssignCornerGL(x, y + this.giveSideLength(), z, way[2]);
					doAssignCornerGL(x, y + this.giveSideLength(), z + this.giveSideLength(), way[3]);
					doAssignCornerGL(x + this.giveSideLength(), y + this.giveSideLength(), z + this.giveSideLength(), way[0]);
					doAssignCornerGL(x + this.giveSideLength(), y + this.giveSideLength(), z, way[1]);
					
					setMirrored(true);
					doAssignCornerGL(x, y, z, way[3]);
					doAssignCornerGL(x, y, z + this.giveSideLength(), way[0]);
					doAssignCornerGL(x, y + this.giveSideLength(), z + this.giveSideLength(), way[1]);
					doAssignCornerGL(x, y + this.giveSideLength(), z, way[2]);
					
					setMirrored(false);
					doAssignCornerGL(x + this.giveSideLength(), y, z, way[3]);
					doAssignCornerGL(x + this.giveSideLength(), y, z + this.giveSideLength(), way[0]);
					doAssignCornerGL(x + this.giveSideLength(), y + this.giveSideLength(), z + this.giveSideLength(), way[1]);
					doAssignCornerGL(x + this.giveSideLength(), y + this.giveSideLength(), z, way[2]);
				}
				catch (DefectLWJGL e)
				{
					e.printStackTrace();
				}
				finally
				{
					glEnd();
					setMirrored(false);
				}
			}
		});
	}
	
}
