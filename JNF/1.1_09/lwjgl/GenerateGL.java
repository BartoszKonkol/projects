
/**
 * 
 * Java New Functions
 * JNF
 * 1.1_05
 * 
 * © Bartosz Konkol
 * 
 * javax.jnf.lwjgl.GenerateGL
 * 2014-03-16 - 2014-03-16 [JNF 1.1_05]
 * 2014-04-27 - 2014-05-10 [JNF 1.1_06]
 * 
 */

package javax.jnf.lwjgl;

/**
 * 
 * Support for LWJGL<br>Generate GL
 * 
 * @since   1.1_05         <! PERMANENT >
 * @version 1.1_06         <! VARIABLE >
 * @author  Bartosz Konkol <! VARIABLE >
 *
 */

public interface GenerateGL extends ILWJGL
{
	
	/**
	 * The function of generating. <! VARIABLE >
	 * @since   1.1_05             <! PERMANENT >
	 * @version 1.1_06             <! VARIABLE >
	 * @author  Bartosz Konkol     <! VARIABLE >
	 */
	public void doGenerateGL(final Generation generation);
	
}
