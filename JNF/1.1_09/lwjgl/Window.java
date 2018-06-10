
/**
 * 
 * Java New Functions
 * JNF
 * 1.1_05
 * 
 * © Bartosz Konkol
 * 
 * javax.jnf.lwjgl.Window
 * 2014-03-10 - 2014-04-03 [JNF 1.1_05]
 * 2014-04-27 - 2014-05-06 [JNF 1.1_06]
 * 
 */

package javax.jnf.lwjgl;

import java.text.NumberFormat;
import javax.jnf.lwjgl.exception.DefectLWJGL;
import org.lwjgl.openal.AL;
import org.lwjgl.opengl.DisplayMode;
import static java.lang.System.*;
import static javax.jnf.technical.constants.Mathematical.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.Display.*;

/**
 * 
 * Support for LWJGL<br>Window
 * 		<p>
 * Class abstract of management the graphical window.
 * 
 * @since   1.1_05         <! PERMANENT >
 * @version 1.1_06         <! VARIABLE >
 * @author  Bartosz Konkol <! VARIABLE >
 * 
 * @see javax.jnf.lwjgl.Window#Window
 *
 */

public abstract class Window extends LWJGL
{
	
	/**
	 * Indicates whether the settings allow you to use 3D graphics. <! VARIABLE >
	 * @since   1.1_05                                              <! PERMANENT >
	 * @version 1.1_05                                              <! VARIABLE >
	 * @author  Bartosz Konkol                                      <! VARIABLE >
	 */
	protected final boolean accept3D;
	
	/**
	 * 
	 * Class displays a new window and adapts them to handle the graphics.
	 * 
	 * @param title      <! VARIABLE >
	 * @param size       <! VARIABLE >
	 * @param fps        <! VARIABLE >
	 * @param resizable  <! VARIABLE >
	 * @param fullscreen <! VARIABLE >
	 * @param projection <! VARIABLE >
	 * 
	 * @since   1.1_05         <! PERMANENT >
	 * @version 1.1_06         <! VARIABLE >
	 * @author  Bartosz Konkol <! VARIABLE >
	 * 
	 */
	public Window(String title, final DisplayMode size, final int fps, final boolean resizable, final boolean fullscreen, ProjectionType projection)
	{
		final long time = currentTimeMillis();
		err.println("The LWJGL starts running...");
		
		final NumberFormat secFormat = NumberFormat.getInstance();
		secFormat.setMaximumFractionDigits(3);
		
		if(projection == null)
			projection = ProjectionType.ORTHOGRAPHIC;
		this.setProjection(projection);
		
		this.accept3D = this.getProjection() == ProjectionType.PERSPECTIVE && this.getProjection().getNear() >= 0.001F && this.getProjection().getFar() >= 10.0F;
		
		try
		{
			if(fullscreen && !resizable && (size == null || (size.getHeight() == 0 && size.getWidth() == 0) || size.isFullscreenCapable()))
			{
				setDisplayMode(getDisplayMode());
				setFullscreen(true);
				setResizable(false);
			}
			else
			{
				setDisplayMode(size);
				setFullscreen(true);
				setResizable(resizable);
			}
			
			if(title == null)
				title = "";
			setTitle(title);
			
			create();
			AL.create();
			
			this.doSpecify();
			
			final float secStart = Util.giveStringToFloat(secFormat.format((currentTimeMillis() - time) / 1000F).replace(',', '.'));
			err.println("The LWJGL loaded after " + secStart + " second" + (secStart < mathematicalConstants("two") ? "." : "s."));
			
			while (isCreated() && !isCloseRequested() && this.isWork() && !this.isPause())
			{
				update();
				if(fps > 0)
					sync(fps);
				
				glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
				if(this.accept3D)
					glClearDepth(1.0f);
				
				this.doActions();
			}
			
			AL.destroy();
			destroy();
			
			final float secStop = Util.giveStringToFloat(secFormat.format((currentTimeMillis() - time) / 1000F - secStart).replace(',', '.'));
			err.println("The LWJGL finished running after " + secStop + " (" + secFormat.format(secStart + secStop).replace(',', '.') + ") second" + (secStop < mathematicalConstants("two") ? "." : "s."));
			
			gc();
			exit((int) mathematicalConstants("zero"));
		}
		catch(Exception e0)
		{
			try
			{
				throw new DefectLWJGL();
			}
			catch (DefectLWJGL e1)
			{
				e0.printStackTrace();
				if(!(e0 instanceof DefectLWJGL))
					e1.printStackTrace();
				
				final float secStop = Util.giveStringToFloat(secFormat.format((currentTimeMillis() - time) / 1000F).replace(',', '.'));
				err.println("The LWJGL will be closed due to crash (" + secStop + " second" + (secStop < mathematicalConstants("two") ? "" : "s") + " of starting).");
				
				e1.exit();
			}
		}
	}
	
	private ProjectionType projection;
	
	/**
	 * javax.jnf.lwjgl.Window.setProjection(ProjectionType projection)
	 * @param  projection Type of projection.
	 * @action <! VARIABLE >
	 * Specify the projection.
	 * 
	 * @since   1.1_05         <! PERMANENT >
	 * @version 1.1_05         <! VARIABLE >
	 * @author  Bartosz Konkol <! VARIABLE >
	 */
	public final void setProjection(ProjectionType projection)
	{
		this.projection = projection;
	}
	
	/**
	 * javax.jnf.lwjgl.Window.getProjection()
	 * @return <! VARIABLE >
	 *          Return the projection. <br>
	 * [ProjectionType]
	 * 
	 * @since   1.1_05 <! PERMANENT >
	 * @version 1.1_05 <! VARIABLE >
	 * @author  Bartosz Konkol <! VARIABLE >
	 */
	public final ProjectionType getProjection()
	{
		return this.projection;
	}
	
	/**
	 * javax.jnf.lwjgl.Window.doSpecify()
	 * @action <! VARIABLE >
	 * Specify of graphics.
	 * 
	 * @since   1.1_05         <! PERMANENT >
	 * @version 1.1_05         <! VARIABLE >
	 * @author  Bartosz Konkol <! VARIABLE >
	 * 
	 * @throws Exception
	 */
	protected void doSpecify() throws Exception
	{
		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();
		
		this.getProjection().doWork();
		
		glMatrixMode(GL_MODELVIEW);
		glLoadIdentity();
		
		if(this.accept3D)
		{
			glEnable(GL_DEPTH_TEST);
			glLoadIdentity();
		}
		
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
		glEnable(GL_BLEND);
		glLoadIdentity();
		
		glEnable(GL_TEXTURE_2D);
		glLoadIdentity();
	}
	
	/**
	 * javax.jnf.lwjgl.Window.doActions()
	 * @action <! VARIABLE >
	 * Tasks loop of graphics.
	 * 
	 * @since   1.1_05         <! PERMANENT >
	 * @version 1.1_05         <! VARIABLE >
	 * @author  Bartosz Konkol <! VARIABLE >
	 * 
	 * @throws Exception
	 */
	protected abstract void doActions() throws Exception;
	
}
