
/**
 * 
 * Java New Functions
 * JNF
 * 1.1_06
 * 
 * © Bartosz Konkol
 * 
 * javax.jnf.lwjgl.model.ConverterOBJ
 * 2014-05-05 - 2014-05-09 [JNF 1.1_06]
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

/**
 * 
 * Support for LWJGL<br>Model<br>Converter OBJ
 * 
 * @since   1.1_06         <! PERMANENT >
 * @version 1.1_06         <! VARIABLE >
 * @author  Bartosz Konkol <! VARIABLE >
 *
 */

public class ConverterOBJ extends LWJGL
{
	
	/**
	 * The file a wavefront.   <! VARIABLE >
	 * @since   1.1_06         <! PERMANENT >
	 * @version 1.1_06         <! VARIABLE >
	 * @author  Bartosz Konkol <! VARIABLE >
	 */
	protected final File file;
	
	/**
	 * The scale of model.     <! VARIABLE >
	 * @since   1.1_06         <! PERMANENT >
	 * @version 1.1_06         <! VARIABLE >
	 * @author  Bartosz Konkol <! VARIABLE >
	 */
	protected final float scale;
	
	/**
	 * The starting point of the model. <! VARIABLE >
	 * @since   1.1_06                  <! PERMANENT >
	 * @version 1.1_06                  <! VARIABLE >
	 * @author  Bartosz Konkol          <! VARIABLE >
	 */
	protected final Point3D point;
	
	/**
	 * 
	 * Class of converter the OBJ.
	 * 
	 * @param file  <! VARIABLE >
	 * @param scale <! VARIABLE >
	 * @param point <! VARIABLE >
	 * 
	 * @since   1.1_06         <! PERMANENT >
	 * @version 1.1_06         <! VARIABLE >
	 * @author  Bartosz Konkol <! VARIABLE >
	 * 
	 */
	public ConverterOBJ(File file, float scale, Point3D point)
	{
		this.file = file;
		this.scale = scale;
		this.point = point;
		this.model = null;
	}
	
	/**
	 * 
	 * Class of converter the OBJ.
	 * 
	 * @param file  <! VARIABLE >
	 * @param point <! VARIABLE >
	 * 
	 * @since   1.1_06         <! PERMANENT >
	 * @version 1.1_06         <! VARIABLE >
	 * @author  Bartosz Konkol <! VARIABLE >
	 * 
	 */
	public ConverterOBJ(File file, Point3D point)
	{
		this(file, (float) Mathematical.mathematicalConstants("one"), point);
	}
	
	/**
	 * 
	 * Class of converter the OBJ.
	 * 
	 * @param file  <! VARIABLE >
	 * @param scale <! VARIABLE >
	 * @param point <! VARIABLE >
	 * 
	 * @since   1.1_06         <! PERMANENT >
	 * @version 1.1_06         <! VARIABLE >
	 * @author  Bartosz Konkol <! VARIABLE >
	 * 
	 */
	public ConverterOBJ(File file, float scale, Point2D point)
	{
		this(file, scale, new Point3D(point, (float) Mathematical.mathematicalConstants("zero")));
	}
	
	/**
	 * 
	 * Class of converter the OBJ.
	 * 
	 * @param file  <! VARIABLE >
	 * @param point <! VARIABLE >
	 * 
	 * @since   1.1_06         <! PERMANENT >
	 * @version 1.1_06         <! VARIABLE >
	 * @author  Bartosz Konkol <! VARIABLE >
	 * 
	 */
	public ConverterOBJ(File file, Point2D point)
	{
		this(file, (float) Mathematical.mathematicalConstants("one"), point);
	}
	
	/**
	 * 
	 * Class of converter the OBJ.
	 * 
	 * @param file  <! VARIABLE >
	 * @param scale <! VARIABLE >
	 * 
	 * @since   1.1_06         <! PERMANENT >
	 * @version 1.1_06         <! VARIABLE >
	 * @author  Bartosz Konkol <! VARIABLE >
	 * 
	 */
	public ConverterOBJ(File file, float scale)
	{
		this(file, scale, new Point2D((float) Mathematical.mathematicalConstants("zero"), (float) Mathematical.mathematicalConstants("zero")));
	}
	
	/**
	 * 
	 * Class of converter the OBJ.
	 * 
	 * @param file <! VARIABLE >
	 * 
	 * @since   1.1_06         <! PERMANENT >
	 * @version 1.1_06         <! VARIABLE >
	 * @author  Bartosz Konkol <! VARIABLE >
	 * 
	 */
	public ConverterOBJ(File file)
	{
		this(file, (float) Mathematical.mathematicalConstants("one"));
	}
	
	/**
	 * The parameter of model. <! VARIABLE >
	 * @since   1.1_06         <! PERMANENT >
	 * @version 1.1_06         <! VARIABLE >
	 * @author  Bartosz Konkol <! VARIABLE >
	 */
	protected Model model;
	
	/**
	 * Provides the model.     <! VARIABLE >
	 * @since   1.1_06         <! PERMANENT >
	 * @version 1.1_06         <! VARIABLE >
	 * @author  Bartosz Konkol <! VARIABLE >
	 */
	public final Model giveModel()
	{
		return this.model;
	}
	
	/**
	 * The function of convert. <! VARIABLE >
	 * @since   1.1_06          <! PERMANENT >
	 * @version 1.1_06          <! VARIABLE >
	 * @author  Bartosz Konkol  <! VARIABLE >
	 */
	public ConverterOBJ doConvert() throws RuntimeException
	{
		final String[] lines = Load.text(this.file, (int) Mathematical.mathematicalConstants("zero")).replace('\r', '\n').split("\n");
		final List<Point3D> v  = new ArrayList<Point3D>();
		final List<Point3D> vt = new ArrayList<Point3D>();
		final List<Point3D> vn = new ArrayList<Point3D>();
		final List<int[]> f  = new ArrayList<int[]>();
		final List<int[]> ft = new ArrayList<int[]>();
		final List<int[]> fn = new ArrayList<int[]>();
		
		for(String line : lines)
		{
			line = line.trim().toLowerCase();
			
			if(line.length() > 0 && line.charAt((int) Mathematical.mathematicalConstants("zero")) != '#')
			{
				final String[] values = line.split(" ");
				final String type = values[0];
				
				switch(type)
				{
					case "v":
						v.add(new Point3D(Util.giveStringToFloat(values[1]), Util.giveStringToFloat(values[2]), Util.giveStringToFloat(values[3])));
						break;
					case "vt":
						vt.add(new Point3D(Util.giveStringToFloat(values[1]), Util.giveStringToFloat(values[2]), Util.giveStringToFloat(values[3])));
						break;
					case "vn":
						vn.add(new Point3D(Util.giveStringToFloat(values[1]), Util.giveStringToFloat(values[2]), Util.giveStringToFloat(values[3])));
						break;
					case "f":
					{
						final int quantity  = (int) (values.length - Mathematical.mathematicalConstants("one"));
						final int[] arrayF  = new int[values.length - 1];
						final int[] arrayFT = new int[values.length - 1];
						final int[] arrayFN = new int[values.length - 1];
						for(int i = 0; i < quantity; i++)
						{
							final String[] references = values[i + 1].split("/");
							arrayF[i] = (int) (Util.giveStringToInteger(references[0]) - Mathematical.mathematicalConstants("one"));
							if(!references[1].equals(""))
								arrayFT[i] = (int) (Util.giveStringToInteger(references[1]) - Mathematical.mathematicalConstants("one"));
							else
								arrayFT[i] =  Integer.MIN_VALUE;
							if(references.length > 2 && !references[2].equals(""))
								arrayFN[i] = (int) (Util.giveStringToInteger(references[2]) - Mathematical.mathematicalConstants("one"));
							else
								arrayFN[i] =  Integer.MIN_VALUE;
						}
						f.add(arrayF);
						ft.add(arrayFT);
						fn.add(arrayFN);
						break;
					}
				}
			}
		}
		
		final List<String> commands = new ArrayList<String>();
		commands.add("");
		commands.add("# " + Model.NAME);
		commands.add("");
		commands.add("# " + this.giveNameClass());
		commands.add("# " + new SimpleDateFormat("yyyy'-'MM'-'dd' 'HH':'mm':'ss").format(new Date()));
		commands.add("");
		int vertices = (int) Mathematical.mathematicalConstants("zero");
		boolean makeEnd = false;
		for(int i = 0; i < f.size(); i++)
		{
			final int[] arrayF  = f.get(i);
			final int[] arrayFT = ft.get(i);
			final int[] arrayFN = fn.get(i);
			if(arrayF.length != vertices)
			{
				if(makeEnd)
					commands.add("$ ");
				vertices = arrayF.length;
				commands.add("! " + vertices);
				makeEnd = true;
			}
			for(int j = 0; j < vertices; j++)
			{
				final Point3D pointF  = v.get(arrayF[j]);
				final Point3D pointFT = arrayFT[j] != Integer.MIN_VALUE ? vt.get(arrayFT[j]) : null;
				final Point3D pointFN = arrayFN[j] != Integer.MIN_VALUE ? vn.get(arrayFN[j]) : null;
				String command = ": " + (pointF.getX() * this.scale) + "," + (pointF.getY() * this.scale) + "," + (pointF.getZ() * this.scale);
				if(pointFT != null)
					command += "," + pointFT.getX() + "," + pointFT.getY();
				if(pointFN != null)
					command += "," + pointFN.getX() + "," + pointFN.getY() + "," + pointFN.getZ();
				commands.add(command);
			}
			if(i == f.size() - Mathematical.mathematicalConstants("one"))
				commands.add("$ ");
		}
		commands.add("");
		commands.add("### Convert on " + new SimpleDateFormat("yyyy'-'MM'-'dd").format(new Date()) + ". ###");
		
		this.model = new Model(commands.toArray(new String[commands.size()]), this.point);
		
		return this;
	}
	
}
