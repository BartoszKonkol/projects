
/**
 * 
 * Java New Functions
 * JNF
 * 1.1_05
 * 
 * © Bartosz Konkol
 * 
 * javax.jnf.lwjgl.CalculatorFPS
 * 2014-03-17 - 2014-03-17 [JNF 1.1_05]
 * 2014-04-22 - 2014-04-27 [JNF 1.1_06]
 * 
 */

package javax.jnf.lwjgl;

import javax.jnf.technical.constants.Mathematical;
import javax.jnf.technical.maths.Automatic;
import javax.jnf.technical.maths.Conversion;

/**
 * 
 * Support for LWJGL<br>Calculator FPS
 * 		<p>
 * Class of calculate the fps.
 * 
 * @since   1.1_05         <! PERMANENT >
 * @version 1.1_06         <! VARIABLE >
 * @author  Bartosz Konkol <! VARIABLE >
 *
 */

public class CalculatorFPS extends LWJGL
{
	
	/**
	 * 
	 * Class of management the calculating FPS.
	 * 
	 * @param accuracy <! VARIABLE >
	 * 
	 * @since   1.1_05         <! PERMANENT >
	 * @version 1.1_05         <! VARIABLE >
	 * @author  Bartosz Konkol <! VARIABLE >
	 * 
	 */
	public CalculatorFPS(byte accuracy)
	{
		this.setAccuracy(accuracy);
		this.setTime(CalculatorFPS.giveTime());
	}
	
	/**
	 * 
	 * Class of management the calculating FPS.
	 * 
	 * @since   1.1_05         <! PERMANENT >
	 * @version 1.1_05         <! VARIABLE >
	 * @author  Bartosz Konkol <! VARIABLE >
	 * 
	 */
	public CalculatorFPS()
	{
		this((byte) 3);
	}

	private byte accuracy;
	private long time;
	private int progress;
	private float fps;
	
	/**
	 * Specify the accuracy.   <! VARIABLE >
	 * @since   1.1_05         <! PERMANENT >
	 * @version 1.1_05         <! VARIABLE >
	 * @author  Bartosz Konkol <! VARIABLE >
	 */
	public final CalculatorFPS setAccuracy(byte accuracy)
	{
		this.accuracy = accuracy;
		return this;
	}
	
	/**
	 * Return the accuracy.    <! VARIABLE >
	 * @since   1.1_05         <! PERMANENT >
	 * @version 1.1_05         <! VARIABLE >
	 * @author  Bartosz Konkol <! VARIABLE >
	 */
	public final byte getAccuracy()
	{
		return this.accuracy;
	}
	
	/**
	 * Specify the time.       <! VARIABLE >
	 * @since   1.1_05         <! PERMANENT >
	 * @version 1.1_05         <! VARIABLE >
	 * @author  Bartosz Konkol <! VARIABLE >
	 */
	public final CalculatorFPS setTime(long time)
	{
		this.time = time;
		return this;
	}
	
	/**
	 * Return the time.        <! VARIABLE >
	 * @since   1.1_05         <! PERMANENT >
	 * @version 1.1_05         <! VARIABLE >
	 * @author  Bartosz Konkol <! VARIABLE >
	 */
	public final long getTime()
	{
		return this.time;
	}
	
	/**
	 * Specify the progress.   <! VARIABLE >
	 * @since   1.1_05         <! PERMANENT >
	 * @version 1.1_05         <! VARIABLE >
	 * @author  Bartosz Konkol <! VARIABLE >
	 */
	public final CalculatorFPS setProgress(int progress)
	{
		this.progress = progress;
		return this;
	}
	
	/**
	 * Return the progress.    <! VARIABLE >
	 * @since   1.1_05         <! PERMANENT >
	 * @version 1.1_05         <! VARIABLE >
	 * @author  Bartosz Konkol <! VARIABLE >
	 */
	public final int getProgress()
	{
		return this.progress;
	}
	
	/**
	 * Specify the fps.        <! VARIABLE >
	 * @since   1.1_05         <! PERMANENT >
	 * @version 1.1_05         <! VARIABLE >
	 * @author  Bartosz Konkol <! VARIABLE >
	 */
	public final CalculatorFPS setFPS(float fps)
	{
		this.fps = fps;
		return this;
	}
	
	/**
	 * Return the fps.         <! VARIABLE >
	 * @since   1.1_05         <! PERMANENT >
	 * @version 1.1_05         <! VARIABLE >
	 * @author  Bartosz Konkol <! VARIABLE >
	 */
	public final float getFPS()
	{
		return this.fps;
	}
	
	/**
	 * Calculates the level of FPS. <! VARIABLE >
	 * @since   1.1_05              <! PERMANENT >
	 * @version 1.1_05              <! VARIABLE >
	 * @author  Bartosz Konkol      <! VARIABLE >
	 */
	public CalculatorFPS doCalculateFPS()
	{
		if(CalculatorFPS.giveTime() - this.getTime() >= Automatic.power(10, this.getAccuracy()))
			this
				.setFPS((float) (this.getProgress() * Automatic.power(10, Conversion.changesInSign(this.getAccuracy()) + 3)))
				.setProgress((int) Mathematical.mathematicalConstants("zero"))
				.setTime(CalculatorFPS.giveTime())
			;
		
		return this.setProgress((int) (this.getProgress() + Mathematical.mathematicalConstants("one")));
	}
	
	/**
	 * Shows the current time. <! VARIABLE >
	 * @since   1.1_05         <! PERMANENT >
	 * @version 1.1_05         <! VARIABLE >
	 * @author  Bartosz Konkol <! VARIABLE >
	 */
	public static final long giveTime()
	{
		return System.currentTimeMillis();
	}
	
}
