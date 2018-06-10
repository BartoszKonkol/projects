/**
 * 
 * Java New Functions
 * JNF
 * 1.1_05
 * 
 * © Bartosz Konkol
 * 
 * javax.jnf.lwjgl.Generation
 * 2014-03-16 - 2014-03-16 [JNF 1.1_05]
 * 2014-04-27 - 2014-04-27 [JNF 1.1_06]
 * 
 */

package javax.jnf.lwjgl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 
 * Support for LWJGL<br>Generation
 * 		<p>
 * Class generating of elements.
 * 
 * @since   1.1_05         <! PERMANENT >
 * @version 1.1_06         <! VARIABLE >
 * @author  Bartosz Konkol <! VARIABLE >
 *
 */

public class Generation extends LWJGL
{
	
	/**
	 * 
	 * Class stores data of the generated element.
	 * 
	 * @param currentElement <! VARIABLE >
	 * @param allElements    <! VARIABLE >
	 * 
	 * @since   1.1_05         <! PERMANENT >
	 * @version 1.1_05         <! VARIABLE >
	 * @author  Bartosz Konkol <! VARIABLE >
	 * 
	 */
	protected Generation(final GenerateGL currentElement, final List<GenerateGL> allElements)
	{
		this.setElement(currentElement);
		this.setElementsList(allElements);
	}
	
	private GenerateGL element;
	private List<GenerateGL> elementsList;
	
	/**
	 * Specifies the element of function of generating GL. <! VARIABLE >
	 * @since   1.1_05                                     <! PERMANENT >
	 * @version 1.1_05                                     <! VARIABLE >
	 * @author  Bartosz Konkol                             <! VARIABLE >
	 */
	public final Generation setElement(final GenerateGL element)
	{
		this.element = element;
		return this;
	}
	
	/**
	 * Return the element of function of generating GL. <! VARIABLE >
	 * @since   1.1_05                                  <! PERMANENT >
	 * @version 1.1_05                                  <! VARIABLE >
	 * @author  Bartosz Konkol                          <! VARIABLE >
	 */
	public final GenerateGL getElement()
	{
		return this.element;
	}
	
	/**
	 * Specifies the elements list of function of generating GL. <! VARIABLE >
	 * @since   1.1_05                                           <! PERMANENT >
	 * @version 1.1_05                                           <! VARIABLE >
	 * @author  Bartosz Konkol                                   <! VARIABLE >
	 */
	public final Generation setElementsList(final List<GenerateGL> elementsList)
	{
		this.elementsList = elementsList;
		return this;
	}
	
	/**
	 * Return the elements list of function of generating GL. <! VARIABLE >
	 * @since   1.1_05                                        <! PERMANENT >
	 * @version 1.1_05                                        <! VARIABLE >
	 * @author  Bartosz Konkol                                <! VARIABLE >
	 */
	public final List<GenerateGL> getElementsList()
	{
		return this.elementsList;
	}
	
	/**
	 * javax.jnf.lwjgl.Generation.doGenerate(GenerateGL... elements)
	 * @param  elements Elements of function of generating GL.
	 * @action <! VARIABLE >
	 * Generating specific elements.
	 * @return <! VARIABLE >
	 *          If generation is positive, the result is 'true'. Otherwise, the result is 'false'. <br>
	 * [boolean]
	 * 
	 * @since   1.1_05         <! PERMANENT >
	 * @version 1.1_05         <! VARIABLE >
	 * @author  Bartosz Konkol <! VARIABLE >
	 */
	public static boolean doGenerate(final GenerateGL... elements)
	{
		final List<GenerateGL> elementsList = new ArrayList<GenerateGL>(Arrays.asList(elements));
		int result = 0;
		
		for(int i = 0; i < elements.length; i++)
		{
			elements[i].doGenerateGL(new Generation(elements[i], elementsList));
			result++;
		}
		
		return result == elements.length;
	}
	
	/**
	 * javax.jnf.lwjgl.Generation.doGenerate(GenerateGL element)
	 * @param  element Element of function of generating GL.
	 * @action <! VARIABLE >
	 * Generating specific element.
	 * @return <! VARIABLE >
	 *          If generation is positive, the result is 'true'. Otherwise, the result is 'false'. <br>
	 * [boolean]
	 * 
	 * @since   1.1_05         <! PERMANENT >
	 * @version 1.1_05         <! VARIABLE >
	 * @author  Bartosz Konkol <! VARIABLE >
	 */
	public static boolean doGenerate(final GenerateGL element)
	{
		return doGenerate(new GenerateGL[]{element});
	}
	
}
