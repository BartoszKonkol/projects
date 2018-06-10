
/**
 * 
 * Java New Functions
 * JNF
 * 1.1_05
 * 
 * © Bartosz Konkol
 * 
 * javax.jnf.lwjgl.Camera
 * 2014-03-12 - 2014-03-14 [JNF 1.1_05]
 * 2014-04-27 - 2014-04-27 [JNF 1.1_06]
 * 
 */

package javax.jnf.lwjgl;

import static org.lwjgl.opengl.Display.destroy;
import static org.lwjgl.input.Mouse.*;
import static org.lwjgl.opengl.Display.*;
import static org.lwjgl.opengl.GL11.*;
import javax.jnf.technical.constants.Mathematical;
import org.lwjgl.Sys;
import org.lwjgl.util.vector.Vector3f;

/**
 * 
 * Support for LWJGL<br>Camera
 * 		<p>
 * Class that manages the camera perspective.
 * 
 * @since   1.1_05         <! PERMANENT >
 * @version 1.1_06         <! VARIABLE >
 * @author  Bartosz Konkol <! VARIABLE >
 *
 */

public class Camera extends LWJGL
{
	
	/**
	 * 
	 * Class generate a camera at the level of zero.
	 * 
	 * @since   1.1_05         <! PERMANENT >
	 * @version 1.1_05         <! VARIABLE >
	 * @author  Bartosz Konkol <! VARIABLE >
	 * 
	 */
	public Camera()
	{
		this((float) Mathematical.mathematicalConstants("zero"), (float) Mathematical.mathematicalConstants("zero"), (float) Mathematical.mathematicalConstants("zero"));
	}
	
	/**
	 * 
	 * Class generate a camera at the level specified.
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
	public Camera(final float x, final float y, final float z)
	{
		this.defaultX = x;
		this.defaultY = y;
		this.defaultZ = z;
		this.setPosition(this.defaultX, this.defaultY, this.defaultZ);
		this.setKeyExit(Keys.ESC);
		this.doStart();
	}
	
	/**
	 * 
	 * Class generate a camera at the level specified.
	 * 
	 * @param position <! VARIABLE >
	 * 
	 * @since   1.1_05         <! PERMANENT >
	 * @version 1.1_05         <! VARIABLE >
	 * @author  Bartosz Konkol <! VARIABLE >
	 * 
	 */
	public Camera(final Vector3f position)
	{
		this.setPosition(position);
		this.defaultX = this.getPosition().getX();
		this.defaultY = this.getPosition().getY();
		this.defaultZ = this.getPosition().getZ();
		this.setKeyExit(Keys.ESC);
		this.doStart();
	}
	
	/**
	 * The initial value of the position of the camera. <! VARIABLE >
	 * @since   1.1_05                                  <! PERMANENT >
	 * @version 1.1_05                                  <! VARIABLE >
	 * @author  Bartosz Konkol                          <! VARIABLE >
	 */
	protected final float defaultX, defaultY, defaultZ;
	
	private Vector3f position;
	private float pitch, roll, yaw;
	private boolean active;
	private Keys keyExit;
	
	/**
	 * The value of time required for motion control. <! VARIABLE >
	 * @since   1.1_05                                <! PERMANENT >
	 * @version 1.1_05                                <! VARIABLE >
	 * @author  Bartosz Konkol                        <! VARIABLE >
	 */
	protected float time;
	/**
	 * The value of stopping the camera. <! VARIABLE >
	 * @since   1.1_05                   <! PERMANENT >
	 * @version 1.1_05                   <! VARIABLE >
	 * @author  Bartosz Konkol           <! VARIABLE >
	 */
	protected boolean detained;
	/**
	 * The value of disable the camera. <! VARIABLE >
	 * @since   1.1_05                  <! PERMANENT >
	 * @version 1.1_05                  <! VARIABLE >
	 * @author  Bartosz Konkol          <! VARIABLE >
	 */
	protected boolean closure;
	
	/**
	 * Specify the position of the camera. <! VARIABLE >
	 * @since   1.1_05                     <! PERMANENT >
	 * @version 1.1_05                     <! VARIABLE >
	 * @author  Bartosz Konkol             <! VARIABLE >
	 */
	public final Camera setPosition(final float x, final float y, final float z)
	{
		this.setPosition(new Vector3f(x, y ,z));
		return this;
	}
	
	/**
	 * Specify the position of the camera. <! VARIABLE >
	 * @since   1.1_05                     <! PERMANENT >
	 * @version 1.1_05                     <! VARIABLE >
	 * @author  Bartosz Konkol             <! VARIABLE >
	 */
	public final Camera setPosition(final Vector3f position)
	{
		if(this.position != null)
			this.position.set(position);
		else
			this.position = position;
		return this;
	}
	
	/**
	 * Return the position of the camera. <! VARIABLE >
	 * @since   1.1_05                    <! PERMANENT >
	 * @version 1.1_05                    <! VARIABLE >
	 * @author  Bartosz Konkol            <! VARIABLE >
	 */
	public final Vector3f getPosition()
	{
		return this.position;
	}
	
	/**
	 * Specify the pitch of the camera. <! VARIABLE >
	 * @since   1.1_05                  <! PERMANENT >
	 * @version 1.1_05                  <! VARIABLE >
	 * @author  Bartosz Konkol          <! VARIABLE >
	 */
	public final Camera setPitch(final float pitch)
	{
		this.pitch = pitch;
		return this;
	}
	
	/**
	 * Return the pitch of the camera. <! VARIABLE >
	 * @since   1.1_05                 <! PERMANENT >
	 * @version 1.1_05                 <! VARIABLE >
	 * @author  Bartosz Konkol         <! VARIABLE >
	 */
	public final float getPitch()
	{
		return this.pitch;
	}
	
	/**
	 * Specify the roll of the camera. <! VARIABLE >
	 * @since   1.1_05                 <! PERMANENT >
	 * @version 1.1_05                 <! VARIABLE >
	 * @author  Bartosz Konkol         <! VARIABLE >
	 */
	public final Camera setRoll(final float roll)
	{
		this.roll = roll;
		return this;
	}
	
	/**
	 * Return the roll of the camera. <! VARIABLE >
	 * @since   1.1_05                <! PERMANENT >
	 * @version 1.1_05                <! VARIABLE >
	 * @author  Bartosz Konkol        <! VARIABLE >
	 */
	public final float getRoll()
	{
		return this.roll;
	}
	
	/**
	 * Specify the yaw of the camera. <! VARIABLE >
	 * @since   1.1_05                <! PERMANENT >
	 * @version 1.1_05                <! VARIABLE >
	 * @author  Bartosz Konkol        <! VARIABLE >
	 */
	public final Camera setYaw(final float yaw)
	{
		this.yaw = yaw;
		return this;
	}
	
	/**
	 * Return the yaw of the camera. <! VARIABLE >
	 * @since   1.1_05               <! PERMANENT >
	 * @version 1.1_05               <! VARIABLE >
	 * @author  Bartosz Konkol       <! VARIABLE >
	 */
	public final float getYaw()
	{
		return this.yaw;
	}
	
	/**
	 * Increase the pitch of the camera. <! VARIABLE >
	 * @since   1.1_05                   <! PERMANENT >
	 * @version 1.1_05                   <! VARIABLE >
	 * @author  Bartosz Konkol           <! VARIABLE >
	 */
	public final Camera addPitch(final float valuePitch)
	{
		return this.setPitch(this.getPitch() + valuePitch);
	}
	
	/**
	 * Increase the roll of the camera. <! VARIABLE >
	 * @since   1.1_05                  <! PERMANENT >
	 * @version 1.1_05                  <! VARIABLE >
	 * @author  Bartosz Konkol          <! VARIABLE >
	 */
	public final Camera addRoll(final float valueRoll)
	{
		return this.setRoll(this.getRoll() + valueRoll);
	}
	
	/**
	 * Increase the yaw of the camera. <! VARIABLE >
	 * @since   1.1_05                 <! PERMANENT >
	 * @version 1.1_05                 <! VARIABLE >
	 * @author  Bartosz Konkol         <! VARIABLE >
	 */
	public final Camera addYaw(final float valueYaw)
	{
		return this.setYaw(this.getYaw() + valueYaw);
	}
	
	/**
	 * Specify whether the camera is active. <! VARIABLE >
	 * @since   1.1_05                       <! PERMANENT >
	 * @version 1.1_05                       <! VARIABLE >
	 * @author  Bartosz Konkol               <! VARIABLE >
	 */
	public final Camera setActive(final boolean active)
	{
		this.active = active;
		return this;
	}
	
	/**
	 * Return whether the camera is active. <! VARIABLE >
	 * @since   1.1_05                      <! PERMANENT >
	 * @version 1.1_05                      <! VARIABLE >
	 * @author  Bartosz Konkol              <! VARIABLE >
	 */
	public final boolean getActive()
	{
		return this.active;
	}
	
	/**
	 * Specify the key closing. <! VARIABLE >
	 * @since   1.1_05          <! PERMANENT >
	 * @version 1.1_05          <! VARIABLE >
	 * @author  Bartosz Konkol  <! VARIABLE >
	 */
	public final Camera setKeyExit(final Keys keyExit)
	{
		this.keyExit = keyExit;
		return this;
	}
	
	/**
	 * Return the key closing. <! VARIABLE >
	 * @since   1.1_05         <! PERMANENT >
	 * @version 1.1_05         <! VARIABLE >
	 * @author  Bartosz Konkol <! VARIABLE >
	 */
	public final Keys getKeyExit()
	{
		return this.keyExit;
	}
	
	/**
	 * Move forward.           <! VARIABLE >
	 * @since   1.1_05         <! PERMANENT >
	 * @version 1.1_05         <! VARIABLE >
	 * @author  Bartosz Konkol <! VARIABLE >
	 */
	protected final void doMoveFront(final float distance)
	{
		final float x = this.getPosition().getX();
		final float z = this.getPosition().getZ();
		
		this.getPosition().setX(x - distance * (float) Math.sin(Math.toRadians(this.getYaw())));
		this.getPosition().setZ(z + distance * (float) Math.cos(Math.toRadians(this.getYaw())));
	}
	
	/**
	 * Move backward.          <! VARIABLE >
	 * @since   1.1_05         <! PERMANENT >
	 * @version 1.1_05         <! VARIABLE >
	 * @author  Bartosz Konkol <! VARIABLE >
	 */
	protected final void doMoveBack(final float distance)
	{
		final float x = this.getPosition().getX();
		final float z = this.getPosition().getZ();
		
		this.getPosition().setX(x + distance * (float) Math.sin(Math.toRadians(this.getYaw())));
		this.getPosition().setZ(z - distance * (float) Math.cos(Math.toRadians(this.getYaw())));
	}
	
	/**
	 * Move left.              <! VARIABLE >
	 * @since   1.1_05         <! PERMANENT >
	 * @version 1.1_05         <! VARIABLE >
	 * @author  Bartosz Konkol <! VARIABLE >
	 */
	protected final void doMoveLeft(final float distance)
	{
		final float x = this.getPosition().getX();
		final float z = this.getPosition().getZ();
		
		this.getPosition().setX(x - distance * (float) Math.sin(Math.toRadians(this.getYaw() - 90)));
		this.getPosition().setZ(z + distance * (float) Math.cos(Math.toRadians(this.getYaw() - 90)));
	}
	
	/**
	 * Move right.              <! VARIABLE >
	 * @since   1.1_05         <! PERMANENT >
	 * @version 1.1_05         <! VARIABLE >
	 * @author  Bartosz Konkol <! VARIABLE >
	 */
	protected final void doMoveRight(final float distance)
	{
		final float x = this.getPosition().getX();
		final float z = this.getPosition().getZ();
		
		this.getPosition().setX(x - distance * (float) Math.sin(Math.toRadians(this.getYaw() + 90)));
		this.getPosition().setZ(z + distance * (float) Math.cos(Math.toRadians(this.getYaw() + 90)));
	}
	
	/**
	 * Move up.                <! VARIABLE >
	 * @since   1.1_05         <! PERMANENT >
	 * @version 1.1_05         <! VARIABLE >
	 * @author  Bartosz Konkol <! VARIABLE >
	 */
	protected final void doMoveUp(final float distance)
	{
		final float y = this.getPosition().getY();
		
		this.getPosition().setY(y - distance);
	}
	
	/**
	 * Move down.              <! VARIABLE >
	 * @since   1.1_05         <! PERMANENT >
	 * @version 1.1_05         <! VARIABLE >
	 * @author  Bartosz Konkol <! VARIABLE >
	 */
	protected final void doMoveDown(final float distance)
	{
		final float y = this.getPosition().getY();
		
		this.getPosition().setY(y + distance);
	}
	
	/**
	 * javax.jnf.lwjgl.Camera.doControlDuringLock()
	 * @action <! VARIABLE >
	 * Control behavior while lock of the camera.
	 * 
	 * @since   1.1_05         <! PERMANENT >
	 * @version 1.1_05         <! VARIABLE >
	 * @author  Bartosz Konkol <! VARIABLE >
	 */
	protected void doControlDuringLock()
	{
		if(!this.getActive())
		{
			if(isButtonDown((int) Mathematical.mathematicalConstants("zero")))
			{
				this.doStart();
			}
			else if(!this.detained && this.getKeyExit().isKeyPress())
			{
				this.closure = true;
				destroy();
			}
		}
	}
	
	/**
	 * javax.jnf.lwjgl.Camera.doNavigateGL(PilotKeys pilot)
	 * @action <! VARIABLE >
	 * Navigation camera movement with the default rate.
	 * 
	 * @param pilot
	 * 
	 * @since   1.1_05         <! PERMANENT >
	 * @version 1.1_05         <! VARIABLE >
	 * @author  Bartosz Konkol <! VARIABLE >
	 */
	public void doNavigateGL(final PilotKeys pilot)
	{
		this.doNavigateGL(pilot, 0.05F, 10.0F, 0.05F, 0.1F);
	}
	
	/**
	 * javax.jnf.lwjgl.Camera.doNavigateGL(PilotKeys pilot, float speedMovementMouse, float speedMovementSurface, float speedMovementHeight, float speedMovementTurn)
	 * @action <! VARIABLE >
	 * Navigation camera movement with the defined rate.
	 * 
	 * @param pilot
	 * @param speedMovementMouse
	 * @param speedMovementSurface
	 * @param speedMovementHeight
	 * @param speedMovementTurn
	 * 
	 * @since   1.1_05         <! PERMANENT >
	 * @version 1.1_05         <! VARIABLE >
	 * @author  Bartosz Konkol <! VARIABLE >
	 */
	public void doNavigateGL(final PilotKeys pilot, final float speedMovementMouse, final float speedMovementSurface, final float speedMovementHeight, final float speedMovementTurn)
	{
		final float timeNew = Sys.getTime();
		
		if(this.getActive())
		{
			this.addPitch(-getDY() * speedMovementMouse);
			this.addYaw(getDX() * speedMovementMouse);
			
			if(pilot.front != null)
				for(int i = 0; i < pilot.front.length; i++)
					if(pilot.front[i].isKeyPress())
						this.doMoveFront(speedMovementSurface * ((timeNew - time) / 1000.0F));
			if(pilot.back != null)
				for(int i = 0; i < pilot.back.length; i++)
					if(pilot.back[i].isKeyPress())
						this.doMoveBack(speedMovementSurface * ((timeNew - time) / 1000.0F));
			if(pilot.left != null)
				for(int i = 0; i < pilot.left.length; i++)
					if(pilot.left[i].isKeyPress())
						this.doMoveLeft(speedMovementSurface * ((timeNew - time) / 1000.0F));
			if(pilot.right != null)
				for(int i = 0; i < pilot.right.length; i++)
					if(pilot.right[i].isKeyPress())
						this.doMoveRight(speedMovementSurface * ((timeNew - time) / 1000.0F));
			if(pilot.up != null)
				for(int i = 0; i < pilot.up.length; i++)
					if(pilot.up[i].isKeyPress())
						this.doMoveUp(speedMovementHeight);
			if(pilot.down != null)
				for(int i = 0; i < pilot.down.length; i++)
					if(pilot.down[i].isKeyPress())
						this.doMoveDown(speedMovementHeight);
			if(pilot.turnLeft != null)
				for(int i = 0; i < pilot.turnLeft.length; i++)
					if(pilot.turnLeft[i].isKeyPress())
						this.addRoll(speedMovementTurn);
			if(pilot.turnRight != null)
				for(int i = 0; i < pilot.turnRight.length; i++)
					if(pilot.turnRight[i].isKeyPress())
						this.addRoll(-speedMovementTurn);
			if(pilot.reset != null)
				for(int i = 0; i < pilot.reset.length; i++)
					if(pilot.reset[i].isKeyPress())
						this.setPitch(0).setRoll(0).setYaw(0).setPosition(this.defaultX, this.defaultY, this.defaultZ);
			if(pilot.exit != null)
				for(int i = 0; i < pilot.exit.length; i++)
					if(pilot.exit[i].isKeyPress())
					{
						this.setKeyExit(pilot.exit[i]);
						this.doStop();
					}
		}
		else
		{
			if(!this.getKeyExit().isKeyPress())
				this.detained = false;
			
			this.doControlDuringLock();
		}
		
		time = timeNew;
		
		this.doUpdatePositionGL();
	}
	
	/**
	 * javax.jnf.lwjgl.Camera.doUpdatePositionGL()
	 * @action <! VARIABLE >
	 * Update camera position.                                   <br>
	 * Note! This function is automatically called
	 * by the '{@link #doNavigateGL(PilotKeys) doNavigateGL}' and
	 * by the '{@link #doNavigateGL(PilotKeys, float , float , float , float) doNavigateGL}'.
	 * 
	 * @since   1.1_05         <! PERMANENT >
	 * @version 1.1_05         <! VARIABLE >
	 * @author  Bartosz Konkol <! VARIABLE >
	 */
	public void doUpdatePositionGL()
	{
		if(!this.closure)
		{
			glLoadIdentity();
			glRotatef(this.getPitch(), 1.0f, 0.0f, 0.0f);
	        glRotatef(this.getYaw(),   0.0f, 1.0f, 0.0f);
			glRotatef(this.getRoll(),  0.0f, 0.0f, 1.0f);
	        glTranslatef(this.getPosition().getX(), this.getPosition().getY(), this.getPosition().getZ());
		}
	}
	
	/**
	 * Tasks startup of the camera. <! VARIABLE >
	 * @since   1.1_05              <! PERMANENT >
	 * @version 1.1_05              <! VARIABLE >
	 * @author  Bartosz Konkol      <! VARIABLE >
	 */
	protected void doStart()
	{
		if(!this.getActive())
		{
			setGrabbed(true);
			this.setActive(true);
		}
	}
	
	/**
	 * Tasks final of the camera. <! VARIABLE >
	 * @since   1.1_05            <! PERMANENT >
	 * @version 1.1_05            <! VARIABLE >
	 * @author  Bartosz Konkol    <! VARIABLE >
	 */
	protected void doStop()
	{
		if(this.getActive())
		{
			setGrabbed(false);
			setCursorPosition(getWidth() / 2, getHeight() / 2);
			this.detained = true;
			this.setActive(false);
		}
	}
	
}
