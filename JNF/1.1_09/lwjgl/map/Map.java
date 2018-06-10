
/**
 *
 * Java New Functions
 * JNF
 * 1.1_05
 *
 * © Bartosz Konkol
 *
 * javax.jnf.lwjgl.map.Map
 * 2014-03-21 - 2014-04-01 [JNF 1.1_05]
 * 2014-04-27 - 2014-04-29 [JNF 1.1_06]
 * 2014-12-23 - 2014-12-23 [JNF 1.1_07]
 *
 */

package javax.jnf.lwjgl.map;

import java.awt.Point;
import java.nio.FloatBuffer;
import javax.jnf.lwjgl.*;
import javax.jnf.lwjgl.exception.DefectLWJGL;
import javax.jnf.technical.constants.Mathematical;
import javax.jnf.technical.maths.Automatic;
import javax.jnf.technical.maths.Conversion;
import static org.lwjgl.opengl.GL11.*;

/**
 *
 * Support for LWJGL<br>Map<br>Map
 *
 * @since   1.1_05         <! PERMANENT >
 * @version 1.1_06         <! VARIABLE >
 * @author  Bartosz Konkol <! VARIABLE >
 *
 */

public abstract class Map extends LWJGL implements GenerateGL
{
	
	private final Camera camera;
	private final float height;
	private final int size;
	private final NavigateCamera navigate;
	
	/**
	 *
	 * Class the basic map.
	 *
	 * @param camera   <! VARIABLE >
	 * @param navigate <! VARIABLE >
	 * @param size     <! VARIABLE >
	 * @param height   <! VARIABLE >
	 *
	 * @since   1.1_05         <! PERMANENT >
	 * @version 1.1_05         <! VARIABLE >
	 * @author  Bartosz Konkol <! VARIABLE >
	 *
	 */
	public Map(final Camera camera, final NavigateCamera navigate, final int size, final float height)
	{
		this.camera = camera;
		this.height = height;
		this.navigate = navigate;
		this.size = size;
		
		glFog(GL_FOG_COLOR, (FloatBuffer) Util.giveColorToBuffer(Util.giveBackgroundColor()));
		glFogf(GL_FOG_DENSITY, (float) Automatic.quotient(Mathematical.mathematicalConstants("one"), Automatic.quotient(this.size, 5)));
		glEnable(GL_FOG);
	}
	
	/**
	 * The function of generation the terrain. <! VARIABLE >
	 * @since   1.1_05                         <! PERMANENT >
	 * @version 1.1_05                         <! VARIABLE >
	 * @author  Bartosz Konkol                 <! VARIABLE >
	 */
	protected abstract void doGenerateTerrain(final int size, final float height, final Point site);
	/**
	 * The function of motion. <! VARIABLE >
	 * @since   1.1_05         <! PERMANENT >
	 * @version 1.1_05         <! VARIABLE >
	 * @author  Bartosz Konkol <! VARIABLE >
	 */
	protected abstract void doMotion(final Camera camera, final float height, final int size);
	
	/**
	 * The function of operation the map. <! VARIABLE >
	 * @since   1.1_05                    <! PERMANENT >
	 * @version 1.1_05                    <! VARIABLE >
	 * @author  Bartosz Konkol            <! VARIABLE >
	 */
	public final void doOperate()
	{
		final Point position = new Point(Math.round(this.camera.getPosition().getX()), Math.round(this.camera.getPosition().getZ()));
		final Point site = new Point(this.giveCalculateChunk(position.getX()), this.giveCalculateChunk(position.getY()));
		
		this.doGenerateTerrain(this.size, this.height, site);
		
		this.doGenerateTerrain(this.size, this.height, new Point(Util.giveDoubleToInteger(	site.getX())													,	Util.giveDoubleToInteger(site.getY()) - (int) Mathematical.mathematicalConstants("one")	));
		this.doGenerateTerrain(this.size, this.height, new Point(Util.giveDoubleToInteger(	site.getX()) + (int) Mathematical.mathematicalConstants("one")	,	Util.giveDoubleToInteger(site.getY()) - (int) Mathematical.mathematicalConstants("one")	));
		this.doGenerateTerrain(this.size, this.height, new Point(Util.giveDoubleToInteger(	site.getX()) + (int) Mathematical.mathematicalConstants("one")	,	Util.giveDoubleToInteger(site.getY())													));
		this.doGenerateTerrain(this.size, this.height, new Point(Util.giveDoubleToInteger(	site.getX()) + (int) Mathematical.mathematicalConstants("one")	,	Util.giveDoubleToInteger(site.getY()) + (int) Mathematical.mathematicalConstants("one")	));
		this.doGenerateTerrain(this.size, this.height, new Point(Util.giveDoubleToInteger(	site.getX())													,	Util.giveDoubleToInteger(site.getY()) + (int) Mathematical.mathematicalConstants("one")	));
		this.doGenerateTerrain(this.size, this.height, new Point(Util.giveDoubleToInteger(	site.getX()) - (int) Mathematical.mathematicalConstants("one")	,	Util.giveDoubleToInteger(site.getY()) + (int) Mathematical.mathematicalConstants("one")	));
		this.doGenerateTerrain(this.size, this.height, new Point(Util.giveDoubleToInteger(	site.getX()) - (int) Mathematical.mathematicalConstants("one")	,	Util.giveDoubleToInteger(site.getY())													));
		this.doGenerateTerrain(this.size, this.height, new Point(Util.giveDoubleToInteger(	site.getX()) - (int) Mathematical.mathematicalConstants("one")	,	Util.giveDoubleToInteger(site.getY()) - (int) Mathematical.mathematicalConstants("one")	));
		
		glLoadIdentity();
		
		this.camera.doNavigateGL(this.navigate.pilot, this.navigate.speedMovementMouse, this.navigate.speedMovementSurface, this.navigate.speedMovementHeight, this.navigate.speedMovementTurn);
		
		this.doMotion(this.camera, this.height, this.size);
	}
	
	/**
	 * The function of external of generation the terrain. <! VARIABLE >
	 * @since   1.1_05                                     <! PERMANENT >
	 * @version 1.1_05                                     <! VARIABLE >
	 * @author  Bartosz Konkol                             <! VARIABLE >
	 */
	public final void doOuterGenerateTerrain(final Object authorization, final int size, final float height, final Point site) throws DefectLWJGL
	{
		if(authorization != null)
		{
			final boolean approvals =
					authorization instanceof ThriftyMap;
			
			if(approvals)
			{
				if(authorization instanceof ThriftyMap)
				{
					if(((ThriftyMap) authorization).connect)
					{
						this.doGenerateTerrain(size, height, site);
						return;
					}
				}
			}
		}
		
		throw new DefectLWJGL();
	}
	
	/**
	 * The function of external of operation the map. <! VARIABLE >
	 * @since   1.1_05                                <! PERMANENT >
	 * @version 1.1_05                                <! VARIABLE >
	 * @author  Bartosz Konkol                        <! VARIABLE >
	 */
	public final void doOuterMotion(final Object authorization, final Camera camera, final float height, final int size) throws DefectLWJGL
	{
		if(authorization != null)
		{
			final boolean approvals =
					authorization instanceof ThriftyMap;
			
			if(approvals)
			{
				if(authorization instanceof ThriftyMap)
				{
					if(((ThriftyMap) authorization).connect)
					{
						this.doMotion(camera, height, size);
						return;
					}
				}
			}
		}
		
		throw new DefectLWJGL();
	}
	
	/**
	 * The function of external of returns the specified parameter. <! VARIABLE >
	 * @since   1.1_05                                              <! PERMANENT >
	 * @version 1.1_07                                              <! VARIABLE >
	 * @author  Bartosz Konkol                                      <! VARIABLE >
	 */@SuppressWarnings("unchecked")
	public final <T> T giveOuterParameter(final Object authorization, final Class<T> type, String name) throws DefectLWJGL
	{
		if(authorization != null)
		{
			final boolean approvals =
					authorization instanceof ThriftyMap;
			
			if(approvals)
			{
				if(authorization instanceof ThriftyMap)
				{
					if(((ThriftyMap) authorization).connect)
					{
						switch(name)
						{
							case "camera":
								if(type.isAssignableFrom(this.camera.getClass()))
									return (T) this.camera;
								else
									break;
							case "height":
								if(type.isAssignableFrom(Float.valueOf(this.height).getClass()))
									return (T) Float.valueOf(this.height);
								else
									break;
							case "size":
								if(type.isAssignableFrom(Integer.valueOf(this.size).getClass()))
									return (T) Integer.valueOf(this.size);
								else
									break;
							case "navigate":
								if(type.isAssignableFrom(this.navigate.getClass()))
									return (T) this.navigate;
								else
									break;
						}
					}
				}
			}
		}
		
		throw new DefectLWJGL();
	}
	
	/**
	 * The function of generating. <! VARIABLE >
	 * @since   1.1_05             <! PERMANENT >
	 * @version 1.1_05             <! VARIABLE >
	 * @author  Bartosz Konkol     <! VARIABLE >
	 */@Override
	public final void doGenerateGL(Generation generation)
	{
		this.doOperate();
	}
	
	/**
	 * Calculating position in the chunk. <! VARIABLE >
	 * @since   1.1_05                    <! PERMANENT >
	 * @version 1.1_05                    <! VARIABLE >
	 * @author  Bartosz Konkol            <! VARIABLE >
	 */
	public final int giveCalculateChunk(final double position)
	{
		return Util.giveDoubleToInteger(Conversion.changesInSign(Math.round((Util.giveDoubleToInteger(position) / (this.size / (int) Mathematical.mathematicalConstants("two")) + (Util.giveDoubleToInteger(position) < (int) Mathematical.mathematicalConstants("zero") ? (int) Conversion.changesInSign(Mathematical.mathematicalConstants("one")) : (int) Mathematical.mathematicalConstants("one"))) / (int) Mathematical.mathematicalConstants("two"))));
	}
	
	/**
	 *
	 * Support for LWJGL<br>Map<br>Map<br>Navigate Camera
	 *
	 * @since   1.1_05         <! PERMANENT >
	 * @version 1.1_06         <! VARIABLE >
	 * @author  Bartosz Konkol <! VARIABLE >
	 *
	 */
	public static final class NavigateCamera extends LWJGL
	{
		
		/**
		 *
		 * Class the navigation of camera.
		 *
		 * @param pilot                <! VARIABLE >
		 * @param speedMovementMouse   <! VARIABLE >
		 * @param speedMovementSurface <! VARIABLE >
		 * @param speedMovementHeight  <! VARIABLE >
		 * @param speedMovementTurn    <! VARIABLE >
		 *
		 * @since   1.1_05         <! PERMANENT >
		 * @version 1.1_05         <! VARIABLE >
		 * @author  Bartosz Konkol <! VARIABLE >
		 *
		 */
		public NavigateCamera(final PilotKeys pilot, final float speedMovementMouse, final float speedMovementSurface, final float speedMovementHeight, final float speedMovementTurn)
		{
			this.speedMovementTurn = speedMovementTurn;
			this.pilot = pilot;
			this.speedMovementHeight = speedMovementHeight;
			this.speedMovementMouse = speedMovementMouse;
			this.speedMovementSurface = speedMovementSurface;
		}
		
		/**
		 *
		 * Class the navigation of camera.
		 *
		 * @param pilot <! VARIABLE >
		 *
		 * @since   1.1_05         <! PERMANENT >
		 * @version 1.1_05         <! VARIABLE >
		 * @author  Bartosz Konkol <! VARIABLE >
		 *
		 */
		public NavigateCamera(final PilotKeys pilot)
		{
			this(pilot, 0.05F, 10.0F, 0.05F, 0.1F);
		}
		
		/**
		 * The keys control the camera. <! VARIABLE >
		 * @since   1.1_05              <! PERMANENT >
		 * @version 1.1_05              <! VARIABLE >
		 * @author  Bartosz Konkol      <! VARIABLE >
		 */
		public final PilotKeys pilot;
		/**
		 * The speed the control.  <! VARIABLE >
		 * @since   1.1_05         <! PERMANENT >
		 * @version 1.1_05         <! VARIABLE >
		 * @author  Bartosz Konkol <! VARIABLE >
		 */
		public final float speedMovementMouse, speedMovementSurface, speedMovementHeight, speedMovementTurn;
		
	}
	
}
