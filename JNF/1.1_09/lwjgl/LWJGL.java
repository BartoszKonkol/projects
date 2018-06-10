
/**
 * 
 * Java New Functions
 * JNF
 * 1.1_06
 * 
 * © Bartosz Konkol
 * 
 * javax.jnf.lwjgl.LWJGL
 * 2014-04-22 - 2014-04-27 [JNF 1.1_06]
 * 
 */

package javax.jnf.lwjgl;

import java.util.Random;
import javax.jnf.exception.Defect;
import javax.jnf.lwjgl.exception.DefectLWJGL;
import javax.jnf.technical.constants.Mathematical;
import javax.jnf.technical.constants.Textual;
import javax.jnf.technical.maths.Conversion;

/**
 * 
 * Support for LWJGL<br>LWJGL
 * 		<p>
 * Class primary of JNF support for LWJGL.
 * 
 * @since   1.1_06         <! PERMANENT >
 * @version 1.1_06         <! VARIABLE >
 * @author  Bartosz Konkol <! VARIABLE >
 *
 */

public abstract class LWJGL implements ILWJGL
{
	
	private final Object[] bufferObject;
	private final Object[] bufferObjectInterim;
	private final ILWJGL[] bufferILWJGL;
	private final ILWJGL[] bufferILWJGLInterim;
	private final int uniqueCode;
	
	/**
	 * 
	 * The primary class of classes of support JNF for LWJGL.
	 * 
	 * @param sizeBuffer <! VARIABLE >
	 * @param seedCode   <! VARIABLE >
	 * 
	 * @since   1.1_06         <! PERMANENT >
	 * @version 1.1_06         <! VARIABLE >
	 * @author  Bartosz Konkol <! VARIABLE >
	 * 
	 */
	public LWJGL(final int sizeBuffer, final long seedCode)
	{
		this.bufferObject =        new Object[sizeBuffer];
		this.bufferObjectInterim = new Object[sizeBuffer];
		this.bufferILWJGL =        new ILWJGL[sizeBuffer];
		this.bufferILWJGLInterim = new ILWJGL[sizeBuffer];
		
		int code = new Random(seedCode).nextInt();
		if(code == 0)
			code = new Random(seedCode).nextInt(Short.MAX_VALUE) + (int) Mathematical.mathematicalConstants("one");
		else if(code < 0)
			code = Util.giveDoubleToInteger(Conversion.changesInSign(code));
		this.uniqueCode = code;
		
		try
		{
			this.doGo();
		}
		catch (final Exception e0)
		{
			e0.printStackTrace();
			
			try
			{
				this.doExitError();
			}
			catch (final Exception e1)
			{
				;
			}
		}
	}
	
	/**
	 * 
	 * The primary class of classes of support JNF for LWJGL.
	 * 
	 * @param sizeBuffer <! VARIABLE >
	 * 
	 * @since   1.1_06         <! PERMANENT >
	 * @version 1.1_06         <! VARIABLE >
	 * @author  Bartosz Konkol <! VARIABLE >
	 * 
	 */
	public LWJGL(final int sizeBuffer)
	{
		this(sizeBuffer, new Random().nextLong());
	}
	
	/**
	 * 
	 * The primary class of classes of support JNF for LWJGL.
	 * 
	 * @param seedCode   <! VARIABLE >
	 * 
	 * @since   1.1_06         <! PERMANENT >
	 * @version 1.1_06         <! VARIABLE >
	 * @author  Bartosz Konkol <! VARIABLE >
	 * 
	 */
	public LWJGL(final long seedCode)
	{
		this(defaultSizeBuffer, seedCode);
	}
	
	/**
	 * 
	 * The primary class of classes of support JNF for LWJGL.
	 * 
	 * @since   1.1_06         <! PERMANENT >
	 * @version 1.1_06         <! VARIABLE >
	 * @author  Bartosz Konkol <! VARIABLE >
	 * 
	 */
	public LWJGL()
	{
		this(defaultSizeBuffer);
	}
	
	private final static int defaultSizeBuffer = Util.givePowerOfTwo(10);
	
	private boolean work;
	private boolean pause;
	
	/**
	 * The function of initiating the class action. <! VARIABLE >
	 * @since   1.1_06                              <! PERMANENT >
	 * @version 1.1_06                              <! VARIABLE >
	 * @author  Bartosz Konkol                      <! VARIABLE >
	 * @throws Exception
	 */@Override
	public void doGo() throws Exception
	{
		if(this.isWork() || this.isPause())
			throw new DefectLWJGL();
		
		this.doCleanBuffersInterim();
		this.doCleanBuffers();
		
		this.work = true;
	}
	
	/**
	 * The function of resume the class action. <! VARIABLE >
	 * @since   1.1_06                          <! PERMANENT >
	 * @version 1.1_06                          <! VARIABLE >
	 * @author  Bartosz Konkol                  <! VARIABLE >
	 * @throws Exception
	 */@Override
	public void doRe() throws Exception
	{
		if(!this.isWork() || !this.isPause())
			throw new DefectLWJGL();
		
		this.doCleanBuffers();
		this.doExchangeBuffers();
		
		this.pause = false;
	}
	
	/**
	 * The function of inhibit the class action. <! VARIABLE >
	 * @since   1.1_06                           <! PERMANENT >
	 * @version 1.1_06                           <! VARIABLE >
	 * @author  Bartosz Konkol                   <! VARIABLE >
	 * @throws Exception
	 */@Override
	public void doDry() throws Exception
	{
		if(!this.isWork() || this.isPause())
			throw new DefectLWJGL();
		
		this.doCleanBuffersInterim();
		this.doExchangeBuffers();
		
		this.pause = true;
	}
	
	/**
	 * The function of closing the class action. <! VARIABLE >
	 * @since   1.1_06                           <! PERMANENT >
	 * @version 1.1_06                           <! VARIABLE >
	 * @author  Bartosz Konkol                   <! VARIABLE >
	 * @throws Exception
	 */@Override
	public void doShut() throws Exception
	{
		if(!this.isWork() || !this.isPause())
			throw new DefectLWJGL();
		
		this.work = false;
		this.pause = false;
		
		this.doCleanBuffersInterim();
		this.doCleanBuffers();
		
		System.gc();
	}
	 
	/**
	 * If the class is acted.  <! VARIABLE >
	 * @since   1.1_06         <! PERMANENT >
	 * @version 1.1_06         <! VARIABLE >
	 * @author  Bartosz Konkol <! VARIABLE >
	 */
	public final boolean isWork()
	{
		return this.work;
	}
	
	/**
	 * If the class is stopped. <! VARIABLE >
	 * @since   1.1_06          <! PERMANENT >
	 * @version 1.1_06          <! VARIABLE >
	 * @author  Bartosz Konkol  <! VARIABLE >
	 */
	public final boolean isPause()
	{
		return this.pause;
	}
	
	/**
	 * javax.jnf.lwjgl.LWJGL.giveBufferObject()
	 * @return <! VARIABLE >
	 *                Returns the buffer of type Object. <br>
	 * [{Object, ...}]
	 * 
	 * @since   1.1_06         <! PERMANENT >
	 * @version 1.1_06         <! VARIABLE >
	 * @author  Bartosz Konkol <! VARIABLE >
	 */
	public final Object[] giveBufferObject()
	{
		return this.bufferObject;
	}
	
	/**
	 * javax.jnf.lwjgl.LWJGL.giveBufferILWJGL()
	 * @return <! VARIABLE >
	 *                Returns the buffer of type ILWJGL. <br>
	 * [{ILWJGL, ...}]
	 * 
	 * @since   1.1_06         <! PERMANENT >
	 * @version 1.1_06         <! VARIABLE >
	 * @author  Bartosz Konkol <! VARIABLE >
	 */
	public final ILWJGL[] giveBufferILWJGL()
	{
		return this.bufferILWJGL;
	}
	
	/**
	 * javax.jnf.lwjgl.LWJGL.giveStackTrace()
	 * @return <! VARIABLE >
	 *                Returns the stack trace. <br>
	 * [{String, ...}]
	 * 
	 * @since   1.1_06         <! PERMANENT >
	 * @version 1.1_06         <! VARIABLE >
	 * @author  Bartosz Konkol <! VARIABLE >
	 */
	public final String[] giveStackTrace()
	{
		final StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
		final int size = stackTrace.length - (int) Mathematical.mathematicalConstants("one");
		final String[] elements = new String[size];
		for(int i = (int) Mathematical.mathematicalConstants("zero"); i < size; i++)
		{
			final int id = i + (int) Mathematical.mathematicalConstants("one");
			elements[i] = stackTrace[id].getFileName() + ',' + stackTrace[id].getClassName() + ',' + stackTrace[id].getMethodName() + ',' + stackTrace[id].getLineNumber();
		}
		return elements;
	}
	
	/**
	 * javax.jnf.lwjgl.LWJGL.giveUniqueCode()
	 * @return <! VARIABLE >
	 *      Returns the unique code. <br>
	 * [int]
	 * 
	 * @since   1.1_06         <! PERMANENT >
	 * @version 1.1_06         <! VARIABLE >
	 * @author  Bartosz Konkol <! VARIABLE >
	 */
	public final int giveUniqueCode()
	{
		return this.uniqueCode;
	}
	
	/**
	 * javax.jnf.lwjgl.LWJGL.giveNameClass()
	 * @return <! VARIABLE >
	 *         Returns the name class. <br>
	 * [String]
	 * 
	 * @since   1.1_06         <! PERMANENT >
	 * @version 1.1_06         <! VARIABLE >
	 * @author  Bartosz Konkol <! VARIABLE >
	 */
	public String giveNameClass()
	{
		return this.getClass().getSimpleName();
	}
	
	/**
	 * javax.jnf.lwjgl.LWJGL.doExit(int code)
	 * @param  code Code of closure.
	 * @action <! VARIABLE >
	 * Close the program with the specified of code closure.
	 * 
	 * @since   1.1_06         <! PERMANENT >
	 * @version 1.1_06         <! VARIABLE >
	 * @author  Bartosz Konkol <! VARIABLE >
	 * @throws  Exception
	 */
	public void doExit(final int code) throws Exception
	{
		if(!this.isPause() && this.isWork())
			this.doDry();
		if(this.isWork())
			this.doShut();
		
		System.err.println("Closing program from the class " + this.giveNameClass() + " with the code error " + code + " (@" + Integer.toHexString(code).toUpperCase() + ").");
		
		Defect defect;
		if(code != Mathematical.mathematicalConstants("zero"))
			defect = new Defect();
		else
			defect = new DefectLWJGL();
		defect.exit(code);
	}
	
	/**
	 * javax.jnf.lwjgl.LWJGL.doExit()
	 * @action <! VARIABLE >
	 * Close the program with the closing code '0'.
	 * 
	 * @since   1.1_06         <! PERMANENT >
	 * @version 1.1_06         <! VARIABLE >
	 * @author  Bartosz Konkol <! VARIABLE >
	 * @throws  Exception
	 */
	public final void doExit() throws Exception
	{
		this.doExit((int) Mathematical.mathematicalConstants("zero"));
	}
	
	/**
	 * javax.jnf.lwjgl.LWJGL.doExitError()
	 * @action <! VARIABLE >
	 * An emergency close the program.
	 * 
	 * @since   1.1_06         <! PERMANENT >
	 * @version 1.1_06         <! VARIABLE >
	 * @author  Bartosz Konkol <! VARIABLE >
	 * @throws  Exception
	 */
	public final void doExitError() throws Exception
	{
		this.doExit(Util.giveDoubleToInteger(Conversion.changesInSign(this.giveUniqueCode())));
	}
	
	/**
	 * javax.jnf.lwjgl.LWJGL.doCleanBuffers()
	 * @action <! VARIABLE >
	 * Clears the buffers.
	 * 
	 * @since   1.1_06         <! PERMANENT >
	 * @version 1.1_06         <! VARIABLE >
	 * @author  Bartosz Konkol <! VARIABLE >
	 */
	protected final void doCleanBuffers()
	{
		for(int i = (int) Mathematical.mathematicalConstants("zero"); i < this.bufferObject.length; i++)
			this.bufferObject[i] = null;
		
		for(int i = (int) Mathematical.mathematicalConstants("zero"); i < this.bufferILWJGL.length; i++)
			this.bufferILWJGL[i] = null;
	}
	
	/**
	 * javax.jnf.lwjgl.LWJGL.doCleanBuffersInterim()
	 * @action <! VARIABLE >
	 * Cleans the temporary buffers.
	 * 
	 * @since   1.1_06         <! PERMANENT >
	 * @version 1.1_06         <! VARIABLE >
	 * @author  Bartosz Konkol <! VARIABLE >
	 */
	protected final void doCleanBuffersInterim()
	{
		for(int i = (int) Mathematical.mathematicalConstants("zero"); i < this.bufferObjectInterim.length; i++)
			this.bufferObjectInterim[i] = null;
		
		for(int i = (int) Mathematical.mathematicalConstants("zero"); i < this.bufferILWJGLInterim.length; i++)
			this.bufferILWJGLInterim[i] = null;
	}
	
	/**
	 * javax.jnf.lwjgl.LWJGL.doExchangeBuffers()
	 * @action <! VARIABLE >
	 * Replaces buffers with temporary buffers and temporary buffers with buffers.
	 * 
	 * @since   1.1_06         <! PERMANENT >
	 * @version 1.1_06         <! VARIABLE >
	 * @author  Bartosz Konkol <! VARIABLE >
	 */
	protected final void doExchangeBuffers()
	{
		for(int i = (int) Mathematical.mathematicalConstants("zero"); i < javax.jnf.technical.operators.Mathematical.mathematicalOperators("=", this.bufferObject.length, this.bufferObjectInterim.length); i++)
		{
			final Object originalBuffer =        this.bufferObject[i];
			final Object originalBufferInterim = this.bufferObjectInterim[i];
			
			this.bufferObject[i] =        originalBufferInterim;
			this.bufferObjectInterim[i] = originalBuffer;
		}

		for(int i = (int) Mathematical.mathematicalConstants("zero"); i < javax.jnf.technical.operators.Mathematical.mathematicalOperators("=", this.bufferILWJGL.length, this.bufferILWJGLInterim.length); i++)
		{
			final ILWJGL originalBuffer =        this.bufferILWJGL[i];
			final ILWJGL originalBufferInterim = this.bufferILWJGLInterim[i];
			
			this.bufferILWJGL[i] =        originalBufferInterim;
			this.bufferILWJGLInterim[i] = originalBuffer;
		}
	}
	
	/**
	 * javax.jnf.lwjgl.LWJGL.toString()
	 * @return <! VARIABLE >
	 *         Returns a string representation of the class. <br>
	 * [String]
	 * 
	 * @since   1.1_06         <! PERMANENT >
	 * @version 1.1_06         <! VARIABLE >
	 * @author  Bartosz Konkol <! VARIABLE >
	 * 
	 * @see {@link java.lang.Object#toString()} <! VARIABLE >
	 */@Override
	public String toString()
	{
		final String[] stack = this.giveStackTrace();
		String path = "";
		for(int i = stack.length - (int) Mathematical.mathematicalConstants("one"); i > Mathematical.mathematicalConstants("zero"); i--)
			path += stack[i].split(",")[1] + (i > (int) Mathematical.mathematicalConstants("one") ? " > " : Textual.textualConstants("no"));
		return this.giveNameClass() + " (" + path + ") @" + Integer.toHexString(this.giveUniqueCode() + this.hashCode());
	}
	
}
