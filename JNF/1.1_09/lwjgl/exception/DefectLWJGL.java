
/**
 * 
 * Java New Functions
 * JNF
 * 1.1_05
 * 
 * © Bartosz Konkol
 * 
 * javax.jnf.lwjgl.exception.DefectLWJGL
 * 2014-03-10 - 2014-03-10 [JNF 1.1_05]
 * 2014-04-22 - 2014-04-27 [JNF 1.1_06]
 * 2015-07-22 - 2015-07-22 [JNF 1.1_09]
 * 
 */

package javax.jnf.lwjgl.exception;

import java.io.PrintStream;
import javax.jnf.exception.Defect;
import javax.jnf.lwjgl.ILWJGL;
import javax.jnf.technical.constants.Mathematical;
import org.lwjgl.opengl.Display;

/**
 * 
 * Support for LWJGL<br>Exception<br>Defect LWJGL
 * 
 * @since   1.1_05         <! PERMANENT >
 * @version 1.1_09         <! VARIABLE >
 * @author  Bartosz Konkol <! VARIABLE >
 *
 */

public class DefectLWJGL extends Defect implements ILWJGL
{
	
	private static final long serialVersionUID = 4578955618820329686L;

	/**
	 * javax.jnf.lwjgl.exception.DefectLWJGL.DefectLWJGL()
	 * 
	 * @since   1.1_05         <! PERMANENT >
	 * @version 1.1_09         <! VARIABLE >
	 * @author  Bartosz Konkol <! VARIABLE >
	 * 
	 * @see javax.jnf.exception.Defect
	 * @see javax.jnf.exception.Defect#Defect
	 */
	public DefectLWJGL()
	{
		super("An internal error occurred while using LWJGL support!");
	}
	
	/**
	 * void javax.jnf.lwjgl.exception.DefectLWJGL.exit(PrintStream s, int status)
	 * 
	 * @since   1.1_05         <! PERMANENT >
	 * @version 1.1_09         <! VARIABLE >
	 * @author  Bartosz Konkol <! VARIABLE >
	 * 
	 * @see javax.jnf.exception.Defect#exit(PrintStream, int)
	 */@Override
	public void exit(PrintStream s, int status)
	{
		if(status != Mathematical.mathematicalConstants("zero"))
		{
			s.println("An internal error occurred while closing an application!");
			for(StackTraceElement traceElement : this.getStackTrace())
				s.println("\tAn error was detected at: " + traceElement);
		}
		Display.destroy();
	}

	 /**
	 * The function of initiating the class action. <! VARIABLE >
	 * @since   1.1_06                              <! PERMANENT >
	 * @version 1.1_06                              <! VARIABLE >
	 * @author  Bartosz Konkol                      <! VARIABLE >
	 * @throws Exception
	 */@Override
	public void doGo() throws Exception{}

	 /**
	 * The function of resume the class action. <! VARIABLE >
	 * @since   1.1_06                          <! PERMANENT >
	 * @version 1.1_06                          <! VARIABLE >
	 * @author  Bartosz Konkol                  <! VARIABLE >
	 * @throws Exception
	 */@Override
	public void doRe() throws Exception{}

	 /**
	 * The function of inhibit the class action. <! VARIABLE >
	 * @since   1.1_06                           <! PERMANENT >
	 * @version 1.1_06                           <! VARIABLE >
	 * @author  Bartosz Konkol                   <! VARIABLE >
	 * @throws Exception
	 */@Override
	public void doDry() throws Exception{}

	 /**
	 * The function of closing the class action. <! VARIABLE >
	 * @since   1.1_06                           <! PERMANENT >
	 * @version 1.1_06                           <! VARIABLE >
	 * @author  Bartosz Konkol                   <! VARIABLE >
	 * @throws Exception
	 */@Override
	public void doShut() throws Exception{}
	
}
