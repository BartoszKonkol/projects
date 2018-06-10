
/**
 * 
 * Java New Functions
 * JNF
 * 1.1_06
 * 
 * © Bartosz Konkol
 * 
 * javax.jnf.lwjgl.model.ModelFile
 * 2014-05-09 - 2014-05-09 [JNF 1.1_06]
 * 
 */

package javax.jnf.lwjgl.model;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.jnf.lwjgl.*;
import javax.jnf.technical.constants.Mathematical;
import javax.jnf.technical.files.Load;
import javax.jnf.technical.files.Save;

/**
 * 
 * Support for LWJGL<br>Model<br>Model File
 * 
 * @since   1.1_06         <! PERMANENT >
 * @version 1.1_06         <! VARIABLE >
 * @author  Bartosz Konkol <! VARIABLE >
 *
 */

public final class ModelFile extends LWJGL
{
	
	private ModelFile(){}
	
	/**
	 * Load a model.           <! VARIABLE >
	 * @since   1.1_06         <! PERMANENT >
	 * @version 1.1_06         <! VARIABLE >
	 * @author  Bartosz Konkol <! VARIABLE >
	 */
	public static final Model doLoadModel(File file, Point3D point)
	{
		return new Model(Load.text(file, (int) Mathematical.mathematicalConstants("zero")).replace('\r', '\n').concat("\n\n### Load on " + new SimpleDateFormat("yyyy'-'MM'-'dd").format(new Date()) + ". ###").split("\n"), point);
	}
	
	/**
	 * Load a model.           <! VARIABLE >
	 * @since   1.1_06         <! PERMANENT >
	 * @version 1.1_06         <! VARIABLE >
	 * @author  Bartosz Konkol <! VARIABLE >
	 */
	public static final Model doLoadModel(String name, Point3D point)
	{
		return doLoadModel(new File(name), point);
	}
	
	/**
	 * Load a model.           <! VARIABLE >
	 * @since   1.1_06         <! PERMANENT >
	 * @version 1.1_06         <! VARIABLE >
	 * @author  Bartosz Konkol <! VARIABLE >
	 */
	public static final Model doLoadModel(File file, Point2D point)
	{
		return doLoadModel(file, new Point3D(point, (float) Mathematical.mathematicalConstants("zero")));
	}
	
	/**
	 * Load a model.           <! VARIABLE >
	 * @since   1.1_06         <! PERMANENT >
	 * @version 1.1_06         <! VARIABLE >
	 * @author  Bartosz Konkol <! VARIABLE >
	 */
	public static final Model doLoadModel(String name, Point2D point)
	{
		return doLoadModel(new File(name), point);
	}
	
	/**
	 * Load a model.           <! VARIABLE >
	 * @since   1.1_06         <! PERMANENT >
	 * @version 1.1_06         <! VARIABLE >
	 * @author  Bartosz Konkol <! VARIABLE >
	 */
	public static final Model doLoadModel(File file)
	{
		return doLoadModel(file, new Point2D((float) Mathematical.mathematicalConstants("zero"), (float) Mathematical.mathematicalConstants("zero")));
	}
	
	/**
	 * Load a model.           <! VARIABLE >
	 * @since   1.1_06         <! PERMANENT >
	 * @version 1.1_06         <! VARIABLE >
	 * @author  Bartosz Konkol <! VARIABLE >
	 */
	public static final Model doLoadModel(String name)
	{
		return doLoadModel(new File(name));
	}
	
	/**
	 * Save a model.           <! VARIABLE >
	 * @since   1.1_06         <! PERMANENT >
	 * @version 1.1_06         <! VARIABLE >
	 * @author  Bartosz Konkol <! VARIABLE >
	 */
	public static final void doSaveModel(File file, String[] lines, boolean overwrite)
	{
		if(overwrite || !file.isFile())
		{
			String horizontalLine = " ";
			for(int i = 0; i < Model.NAME.length() + 4; i++)
				horizontalLine += '#';
			final List<String> list = new ArrayList<String>();
			list.add("");
			list.add(horizontalLine);
			list.add(" # " + Model.NAME + " #");
			list.add(horizontalLine);
			list.add("");
			for(int i = 0; i < lines.length; i++)
				list.add(lines[i]);
			list.add("");
			list.add("### Save on " + new SimpleDateFormat("yyyy'-'MM'-'dd").format(new Date()) + ". ###");
			Save.text(file, true, list.toArray(new String[list.size()]));
		}
	}
	
	/**
	 * Save a model.           <! VARIABLE >
	 * @since   1.1_06         <! PERMANENT >
	 * @version 1.1_06         <! VARIABLE >
	 * @author  Bartosz Konkol <! VARIABLE >
	 */
	public static final void doSaveModel(File file, String text, boolean overwrite)
	{
		doSaveModel(file, text.replace('\r', '\n').split("\n"), overwrite);
	}
	
	/**
	 * Save a model.           <! VARIABLE >
	 * @since   1.1_06         <! PERMANENT >
	 * @version 1.1_06         <! VARIABLE >
	 * @author  Bartosz Konkol <! VARIABLE >
	 */
	public static final void doSaveModel(File file, String[] lines)
	{
		doSaveModel(file, lines, false);
	}
	
	/**
	 * Save a model.           <! VARIABLE >
	 * @since   1.1_06         <! PERMANENT >
	 * @version 1.1_06         <! VARIABLE >
	 * @author  Bartosz Konkol <! VARIABLE >
	 */
	public static final void doSaveModel(File file, String text)
	{
		doSaveModel(file, text.replace('\r', '\n').split("\n"));
	}
	
	/**
	 * Save a model.           <! VARIABLE >
	 * @since   1.1_06         <! PERMANENT >
	 * @version 1.1_06         <! VARIABLE >
	 * @author  Bartosz Konkol <! VARIABLE >
	 */
	public static final void doSaveModel(String name, String[] lines, boolean overwrite)
	{
		doSaveModel(new File(name), lines, overwrite);
	}
	
	/**
	 * Save a model.           <! VARIABLE >
	 * @since   1.1_06         <! PERMANENT >
	 * @version 1.1_06         <! VARIABLE >
	 * @author  Bartosz Konkol <! VARIABLE >
	 */
	public static final void doSaveModel(String name, String text, boolean overwrite)
	{
		doSaveModel(name, text.replace('\r', '\n').split("\n"), overwrite);
	}
	
	/**
	 * Save a model.           <! VARIABLE >
	 * @since   1.1_06         <! PERMANENT >
	 * @version 1.1_06         <! VARIABLE >
	 * @author  Bartosz Konkol <! VARIABLE >
	 */
	public static final void doSaveModel(String name, String[] lines)
	{
		doSaveModel(name, lines, false);
	}
	
	/**
	 * Save a model.           <! VARIABLE >
	 * @since   1.1_06         <! PERMANENT >
	 * @version 1.1_06         <! VARIABLE >
	 * @author  Bartosz Konkol <! VARIABLE >
	 */
	public static final void doSaveModel(String name, String text)
	{
		doSaveModel(name, text.replace('\r', '\n').split("\n"));
	}
	
	/**
	 * Save a model.           <! VARIABLE >
	 * @since   1.1_06         <! PERMANENT >
	 * @version 1.1_06         <! VARIABLE >
	 * @author  Bartosz Konkol <! VARIABLE >
	 */
	public static final void doSaveModel(File file, Model model, boolean overwrite)
	{
		doSaveModel(file, model.giveCommandLines(), overwrite);
	}
	
	/**
	 * Save a model.           <! VARIABLE >
	 * @since   1.1_06         <! PERMANENT >
	 * @version 1.1_06         <! VARIABLE >
	 * @author  Bartosz Konkol <! VARIABLE >
	 */
	public static final void doSaveModel(File file, Model model)
	{
		doSaveModel(file, model, false);
	}
	
	/**
	 * Save a model.           <! VARIABLE >
	 * @since   1.1_06         <! PERMANENT >
	 * @version 1.1_06         <! VARIABLE >
	 * @author  Bartosz Konkol <! VARIABLE >
	 */
	public static final void doSaveModel(String name, Model model, boolean overwrite)
	{
		doSaveModel(new File(name), model, overwrite);
	}
	
	/**
	 * Save a model.           <! VARIABLE >
	 * @since   1.1_06         <! PERMANENT >
	 * @version 1.1_06         <! VARIABLE >
	 * @author  Bartosz Konkol <! VARIABLE >
	 */
	public static final void doSaveModel(String name, Model model)
	{
		doSaveModel(name, model, false);
	}
	
}
