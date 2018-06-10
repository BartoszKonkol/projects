
/**
 * 
 * Java New Functions
 * JNF
 * 1.1_05
 * 
 * © Bartosz Konkol
 * 
 * javax.jnf.lwjgl.PilotKeys
 * 2014-03-14 - 2014-03-14 [JNF 1.1_05]
 * 2014-04-27 - 2014-04-27 [JNF 1.1_06]
 * 
 */

package javax.jnf.lwjgl;

/**
 * 
 * Support for LWJGL<br>Pilot Keys
 * 		<p>
 * Class allows you to define keys control the camera.
 * 
 * @since   1.1_05         <! PERMANENT >
 * @version 1.1_06         <! VARIABLE >
 * @author  Bartosz Konkol <! VARIABLE >
 *
 */

public class PilotKeys extends LWJGL
{
	
	/**
	 * 
	 * Defines the class of camera control keys.
	 * 
	 * @param front     <! VARIABLE >
	 * @param back      <! VARIABLE >
	 * @param left      <! VARIABLE >
	 * @param right     <! VARIABLE >
	 * @param up        <! VARIABLE >
	 * @param down      <! VARIABLE >
	 * @param turnLeft  <! VARIABLE >
	 * @param turnRight <! VARIABLE >
	 * 
	 * @since   1.1_05         <! PERMANENT >
	 * @version 1.1_05         <! VARIABLE >
	 * @author  Bartosz Konkol <! VARIABLE >
	 * 
	 */
	public PilotKeys(final Keys[] front, final Keys[] back, final Keys[] left, final Keys[] right, final Keys[] up, final Keys[] down, final Keys[] turnLeft, final Keys[] turnRight, final Keys[] reset, final Keys[] exit)
	{
		this.back = back;
		this.down = down;
		this.exit = exit;
		this.front = front;
		this.left = left;
		this.reset = reset;
		this.right = right;
		this.turnLeft = turnLeft;
		this.turnRight = turnRight;
		this.up = up;
	}
	
	/**
	 * Control keys in camera. <! VARIABLE >
	 * @since   1.1_05         <! PERMANENT >
	 * @version 1.1_05         <! VARIABLE >
	 * @author  Bartosz Konkol <! VARIABLE >
	 */
	public final Keys[] front, back, left, right, up, down, turnLeft, turnRight, reset, exit;
	
}
