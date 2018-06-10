
/**
 * 
 * Java New Functions
 * JNF
 * 1.1_05
 * 
 * © Bartosz Konkol
 * 
 * javax.jnf.lwjgl.Sound
 * 2014-04-03 - 2014-04-03 [JNF 1.1_05]
 * 2014-04-27 - 2014-04-27 [JNF 1.1_06]
 * 
 */

package javax.jnf.lwjgl;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.nio.FloatBuffer;
import javax.jnf.lwjgl.exception.DefectLWJGL;
import javax.jnf.technical.constants.Mathematical;
import org.lwjgl.BufferUtils;
import org.lwjgl.util.WaveData;
import static org.lwjgl.openal.AL10.*;

/**
 * 
 * Support for LWJGL<br>Sound
 * 		<p>
 * Class of sound tools.
 * 
 * @since   1.1_05         <! PERMANENT >
 * @version 1.1_06         <! VARIABLE >
 * @author  Bartosz Konkol <! VARIABLE >
 *
 */

public class Sound extends LWJGL implements GenerateGL
{
	
	/**
	 * ID of managed sound.    <! VARIABLE >
	 * @since   1.1_05         <! PERMANENT >
	 * @version 1.1_05         <! VARIABLE >
	 * @author  Bartosz Konkol <! VARIABLE >
	 */
	protected final int source;
	
	/**
	 * 
	 * Class of management the sound.
	 * 
	 * @param file      <! VARIABLE >
	 * @param positionX <! VARIABLE >
	 * @param positionY <! VARIABLE >
	 * @param positionZ <! VARIABLE >
	 * @param speed     <! VARIABLE >
	 * 
	 * @since   1.1_05         <! PERMANENT >
	 * @version 1.1_05         <! VARIABLE >
	 * @author  Bartosz Konkol <! VARIABLE >
	 * 
	 * @throws FileNotFoundException
	 * 
	 */
	public Sound(final File file, final float positionX, final float positionY, final float positionZ, final float speed) throws FileNotFoundException
	{
		WaveData data = WaveData.create(new BufferedInputStream(new FileInputStream(file)));
		int buffer = alGenBuffers();
		alBufferData(buffer, data.format, data.data, data.samplerate);
		data.dispose();
		this.source = alGenSources();
		alSourcei(this.source, AL_BUFFER, buffer);
		alDeleteBuffers(buffer);
		
		this.setPosition(positionX, positionY, positionZ).doAssignPosition();
		this.setSpeed(speed).doAssignSpeed();
		
		this.exist = true;
	}
	
	/**
	 * 
	 * Class of management the sound.
	 * 
	 * @param file      <! VARIABLE >
	 * @param positionX <! VARIABLE >
	 * @param positionY <! VARIABLE >
	 * @param positionZ <! VARIABLE >
	 * 
	 * @since   1.1_05         <! PERMANENT >
	 * @version 1.1_05         <! VARIABLE >
	 * @author  Bartosz Konkol <! VARIABLE >
	 * 
	 * @throws FileNotFoundException
	 * 
	 */
	public Sound(final File file, final float positionX, final float positionY, final float positionZ) throws FileNotFoundException
	{
		this(file, positionX, positionY, positionZ, (float) Mathematical.mathematicalConstants("one"));
	}
	
	/**
	 * 
	 * Class of management the sound.
	 * 
	 * @param file  <! VARIABLE >
	 * @param speed <! VARIABLE >
	 * 
	 * @since   1.1_05         <! PERMANENT >
	 * @version 1.1_05         <! VARIABLE >
	 * @author  Bartosz Konkol <! VARIABLE >
	 * 
	 * @throws FileNotFoundException
	 * 
	 */
	public Sound(final File file, final float speed) throws FileNotFoundException
	{
		this(file, (float) Mathematical.mathematicalConstants("zero"), (float) Mathematical.mathematicalConstants("zero"), (float) Mathematical.mathematicalConstants("zero"), speed);
	}
	
	/**
	 * 
	 * Class of management the sound.
	 * 
	 * @param file <! VARIABLE >
	 * 
	 * @since   1.1_05         <! PERMANENT >
	 * @version 1.1_05         <! VARIABLE >
	 * @author  Bartosz Konkol <! VARIABLE >
	 * 
	 * @throws FileNotFoundException
	 * 
	 */
	public Sound(final File file) throws FileNotFoundException
	{
		this(file, (float) Mathematical.mathematicalConstants("one"));
	}
	
	private float positionX, positionY, positionZ, speed;
	
	/**
	 * Guidelines.<br>Do not change manually! <! VARIABLE >
	 * @since   1.1_05                        <! PERMANENT >
	 * @version 1.1_05                        <! VARIABLE >
	 * @author  Bartosz Konkol                <! VARIABLE >
	 */
	protected boolean run, exist;
	
	/**
	 * Specify the position.   <! VARIABLE >
	 * @since   1.1_05         <! PERMANENT >
	 * @version 1.1_05         <! VARIABLE >
	 * @author  Bartosz Konkol <! VARIABLE >
	 */
	public final Sound setPosition(final float x, final float y, final float z)
	{
		this.positionX = x;
		this.positionY = y;
		this.positionZ = z;
		return this;
	}
	
	/**
	 * Return the position.    <! VARIABLE >
	 * @since   1.1_05         <! PERMANENT >
	 * @version 1.1_05         <! VARIABLE >
	 * @author  Bartosz Konkol <! VARIABLE >
	 */
	public final float getPositionX()
	{
		return this.positionX;
	}
	
	/**
	 * Return the position.    <! VARIABLE >
	 * @since   1.1_05         <! PERMANENT >
	 * @version 1.1_05         <! VARIABLE >
	 * @author  Bartosz Konkol <! VARIABLE >
	 */
	public final float getPositionY()
	{
		return this.positionY;
	}
	
	/**
	 * Return the position.    <! VARIABLE >
	 * @since   1.1_05         <! PERMANENT >
	 * @version 1.1_05         <! VARIABLE >
	 * @author  Bartosz Konkol <! VARIABLE >
	 */
	public final float getPositionZ()
	{
		return this.positionZ;
	}
	
	/**
	 * Specify the speed.      <! VARIABLE >
	 * @since   1.1_05         <! PERMANENT >
	 * @version 1.1_05         <! VARIABLE >
	 * @author  Bartosz Konkol <! VARIABLE >
	 */
	public final Sound setSpeed(final float speed)
	{
		this.speed = speed;
		return this;
	}
	
	/**
	 * Return the speed.       <! VARIABLE >
	 * @since   1.1_05         <! PERMANENT >
	 * @version 1.1_05         <! VARIABLE >
	 * @author  Bartosz Konkol <! VARIABLE >
	 */
	public final float getSpeed()
	{
		return this.speed;
	}
	
	/**
	 * Assign something to the properties of sound. <! VARIABLE >
	 * @since   1.1_05                              <! PERMANENT >
	 * @version 1.1_05                              <! VARIABLE >
	 * @author  Bartosz Konkol                      <! VARIABLE >
	 */
	public void doAssignAny(final int type, final int value)
	{
		alSourcei(this.source, type, value);
	}
	
	/**
	 * Assign something to the properties of sound. <! VARIABLE >
	 * @since   1.1_05                              <! PERMANENT >
	 * @version 1.1_05                              <! VARIABLE >
	 * @author  Bartosz Konkol                      <! VARIABLE >
	 */
	public void doAssignAny(final int type, final float value)
	{
		alSourcef(this.source, type, value);
	}
	
	/**
	 * Assign something to the properties of sound. <! VARIABLE >
	 * @since   1.1_05                              <! PERMANENT >
	 * @version 1.1_05                              <! VARIABLE >
	 * @author  Bartosz Konkol                      <! VARIABLE >
	 */
	public void doAssignAny(final int type, final FloatBuffer value)
	{
		alSource(this.source, type, value);
	}
	
	/**
	 * Assign something to the properties of sound. <! VARIABLE >
	 * @since   1.1_05                              <! PERMANENT >
	 * @version 1.1_05                              <! VARIABLE >
	 * @author  Bartosz Konkol                      <! VARIABLE >
	 */
	public void doAssignAny(final int type, final float value1, final float value2, final float value3)
	{
		alSource3f(this.source, type, value1, value2, value3);
	}
	
	/**
	 * Assign the position to the properties of sound. <! VARIABLE >
	 * @since   1.1_05                                 <! PERMANENT >
	 * @version 1.1_05                                 <! VARIABLE >
	 * @author  Bartosz Konkol                         <! VARIABLE >
	 */
	public void doAssignPosition()
	{
		this.doAssignAny(AL_POSITION, this.getPositionX(), this.getPositionY(), this.getPositionZ());
	}
	
	/**
	 * Assign the speed to the properties of sound. <! VARIABLE >
	 * @since   1.1_05                              <! PERMANENT >
	 * @version 1.1_05                              <! VARIABLE >
	 * @author  Bartosz Konkol                      <! VARIABLE >
	 */
	public void doAssignSpeed()
	{
		this.doAssignAny(AL_PITCH, this.getSpeed());
	}
	
	/**
	 * Automatically identify and assign a sound position relative to the camera. <! VARIABLE >
	 * @since   1.1_05                                                            <! PERMANENT >
	 * @version 1.1_05                                                            <! VARIABLE >
	 * @author  Bartosz Konkol                                                    <! VARIABLE >
	 */
	public void doAutomaticallyDeterminePosition(final Camera camera, final float positionX, final float positionY, final float positionZ)
	{
		this.setPosition(positionX - camera.getPosition().getX(), positionY + camera.getPosition().getY(), positionZ - camera.getPosition().getZ()).doAssignPosition();
	}
	
	/**
	 * Give the value of the specified property of sound. <! VARIABLE >
	 * @since   1.1_05                                    <! PERMANENT >
	 * @version 1.1_05                                    <! VARIABLE >
	 * @author  Bartosz Konkol                            <! VARIABLE >
	 */@SuppressWarnings("unchecked")
	public <N extends Number> N giveDetachAny(final Class<N> result, final int type) throws DefectLWJGL
	{
		if(result == Integer.class || result == int.class)
			return (N) Integer.valueOf(alGetSourcei(this.source, type));
		else if(result == Float.class || result == float.class)
			return (N) Float.valueOf(alGetSourcef(this.source, type));
		else
			throw new DefectLWJGL();
	}
	
	 /**
	 * Give the value of the specified property of sound. <! VARIABLE >
	 * @since   1.1_05                                    <! PERMANENT >
	 * @version 1.1_05                                    <! VARIABLE >
	 * @author  Bartosz Konkol                            <! VARIABLE >
	 */
	public FloatBuffer giveDetachAny(final int type, final FloatBuffer buffer)
	{
		alGetSource(this.source, type, buffer);
		return buffer;
	}
	
	/**
	 * Give the value of the position of sound based on its properties. <! VARIABLE >
	 * @since   1.1_05                                                  <! PERMANENT >
	 * @version 1.1_05                                                  <! VARIABLE >
	 * @author  Bartosz Konkol                                          <! VARIABLE >
	 */
	public FloatBuffer giveDetachPosition()
	{
		final FloatBuffer positions = (FloatBuffer) BufferUtils.createFloatBuffer(3).put(new float[3]).flip();
		return this.giveDetachAny(AL_POSITION, positions);
	}
	
	/**
	 * Give the value of the speed of sound based on its properties. <! VARIABLE >
	 * @since   1.1_05                                               <! PERMANENT >
	 * @version 1.1_05                                               <! VARIABLE >
	 * @author  Bartosz Konkol                                       <! VARIABLE >
	 */
	public float giveDetachSpeed() throws DefectLWJGL
	{
		return this.giveDetachAny(Float.class, AL_PITCH);
	}
	
	/**
	 * Provides a guideline value of 'exist'. <! VARIABLE >
	 * @since   1.1_05                        <! PERMANENT >
	 * @version 1.1_05                        <! VARIABLE >
	 * @author  Bartosz Konkol                <! VARIABLE >
	 */
	public boolean giveExist()
	{
		return this.exist;
	}
	
	/**
	 * Provides a guideline value of 'run'. <! VARIABLE >
	 * @since   1.1_05                      <! PERMANENT >
	 * @version 1.1_05                      <! VARIABLE >
	 * @author  Bartosz Konkol              <! VARIABLE >
	 */
	public boolean giveRun()
	{
		return this.run;
	}
	
	/**
	 * Starts sound reproduction. <! VARIABLE >
	 * @since   1.1_05            <! PERMANENT >
	 * @version 1.1_05            <! VARIABLE >
	 * @author  Bartosz Konkol    <! VARIABLE >
	 */
	public void doPlay() throws DefectLWJGL
	{
		if(this.giveExist() && !this.giveRun())
		{
			alSourcePlay(this.source);
			this.run = true;
		}
		else
			throw new DefectLWJGL();
	}
	
	/**
	 * Stops sound reproduction. <! VARIABLE >
	 * @since   1.1_05           <! PERMANENT >
	 * @version 1.1_05           <! VARIABLE >
	 * @author  Bartosz Konkol   <! VARIABLE >
	 */
	public void doStop() throws DefectLWJGL
	{
		if(this.giveExist() && this.giveRun())
		{
			alSourceStop(this.source);
			this.run = false;
		}
		else
			throw new DefectLWJGL();
	}
	
	/**
	 * Repeats sound reproduction. <! VARIABLE >
	 * @since   1.1_05             <! PERMANENT >
	 * @version 1.1_05             <! VARIABLE >
	 * @author  Bartosz Konkol     <! VARIABLE >
	 */
	public void doReplay() throws DefectLWJGL
	{
		if(this.giveExist() && this.giveRun())
		{
			this.doStop();
			this.doPlay();
		}
		else
			throw new DefectLWJGL();
	}
	
	/**
	 * Clears sound data.      <! VARIABLE >
	 * @since   1.1_05         <! PERMANENT >
	 * @version 1.1_05         <! VARIABLE >
	 * @author  Bartosz Konkol <! VARIABLE >
	 */
	public void doRemove() throws DefectLWJGL
	{
		if(this.giveExist() && !this.giveRun())
		{
			alDeleteSources(this.source);
			System.gc();
		}
		else
			throw new DefectLWJGL();
		
		this.exist = false;
	}
	
	/**
	 * The function of generating. <! VARIABLE >
	 * @since   1.1_05             <! PERMANENT >
	 * @version 1.1_05             <! VARIABLE >
	 * @author  Bartosz Konkol     <! VARIABLE >
	 */@Override
	public final void doGenerateGL(final Generation generation)
	{
		try
		{
			this.doPlay();
		}
		catch (DefectLWJGL e)
		{
			e.printStackTrace();
		}
	}
	
}
