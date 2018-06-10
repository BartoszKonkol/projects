
/**
 * 
 * Java New Functions
 * JNF
 * 1.1_05
 * 
 * © Bartosz Konkol
 * 
 * javax.jnf.lwjgl.map.Map
 * 2014-03-27 - 2014-03-27 [JNF 1.1_05]
 * 2014-04-27 - 2014-04-27 [JNF 1.1_06]
 * 
 */

package javax.jnf.lwjgl.map;

import javax.jnf.lwjgl.ILWJGL;

/**
 * 
 * Support for LWJGL<br>Map<br>Heightmap
 * 
 * @since   1.1_05         <! PERMANENT >
 * @version 1.1_06         <! VARIABLE >
 * @author  Bartosz Konkol <! VARIABLE >
 *
 */

public interface Heightmap extends ILWJGL
{
	
	/**
	 * The function of heights. <! VARIABLE >
	 * @since   1.1_05          <! PERMANENT >
	 * @version 1.1_05          <! VARIABLE >
	 * @author  Bartosz Konkol  <! VARIABLE >
	 */
	public float[][] giveHeights();
	
}
