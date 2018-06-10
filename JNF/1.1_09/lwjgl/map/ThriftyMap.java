
/**
 * 
 * Java New Functions
 * JNF
 * 1.1_05
 * 
 * © Bartosz Konkol
 * 
 * javax.jnf.lwjgl.map.ThriftyMap
 * 2014-04-01 - 2014-04-01 [JNF 1.1_05]
 * 2014-04-27 - 2014-04-27 [JNF 1.1_06]
 * 
 */

package javax.jnf.lwjgl.map;

import static org.lwjgl.opengl.GL11.*;
import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.jnf.lwjgl.*;
import javax.jnf.lwjgl.exception.DefectLWJGL;
import javax.jnf.lwjgl.map.Map.NavigateCamera;
import javax.jnf.technical.constants.Mathematical;

/**
 * 
 * Support for LWJGL<br>Map<br>Thrifty Map
 * 		<p>
 * Class of thrifty the management of maps.
 * 
 * @since   1.1_05         <! PERMANENT >
 * @version 1.1_06         <! VARIABLE >
 * @author  Bartosz Konkol <! VARIABLE >
 *
 */

public class ThriftyMap extends LWJGL implements GenerateGL
{
	
	/**
	 * The parameter 'camera' of the map. <! VARIABLE >
	 * @since   1.1_05                    <! PERMANENT >
	 * @version 1.1_05                    <! VARIABLE >
	 * @author  Bartosz Konkol            <! VARIABLE >
	 */
	protected final Camera cameraOfMap;
	/**
	 * The parameter 'height' of the map. <! VARIABLE >
	 * @since   1.1_05                    <! PERMANENT >
	 * @version 1.1_05                    <! VARIABLE >
	 * @author  Bartosz Konkol            <! VARIABLE >
	 */
	protected final float heightOfMap;
	/**
	 * The parameter 'navigate' of the map. <! VARIABLE >
	 * @since   1.1_05                      <! PERMANENT >
	 * @version 1.1_05                      <! VARIABLE >
	 * @author  Bartosz Konkol              <! VARIABLE >
	 */
	protected final NavigateCamera navigateOfMap;
	/**
	 * The parameter 'size' of the map. <! VARIABLE >
	 * @since   1.1_05                  <! PERMANENT >
	 * @version 1.1_05                  <! VARIABLE >
	 * @author  Bartosz Konkol          <! VARIABLE >
	 */
	protected final int sizeOfMap;
	
	/**
	 * 
	 * Class the thrifty of management of generating the maps.
	 * 
	 * @param map              <! VARIABLE >
	 * @param refresh          <! VARIABLE >
	 * @param rememberInternal <! VARIABLE >
	 * @param rememberExternal <! VARIABLE >
	 * 
	 * @since   1.1_05         <! PERMANENT >
	 * @version 1.1_05         <! VARIABLE >
	 * @author  Bartosz Konkol <! VARIABLE >
	 * 
	 * @throws DefectLWJGL
	 * 
	 */
	public ThriftyMap(final Map map, final int refresh, final boolean rememberInternal, final boolean rememberExternal) throws DefectLWJGL
	{
		this.setMap(map);
		this.setRefresh(refresh);
		this.setRemember(rememberInternal, rememberExternal);
		
		this.doConnectStart();
		this.cameraOfMap =		this.getMap().giveOuterParameter(	this,	Camera.class,			"camera"	);
		this.heightOfMap =		this.getMap().giveOuterParameter(	this,	Float.class,			"height"	);
		this.navigateOfMap =	this.getMap().giveOuterParameter(	this,	NavigateCamera.class,	"navigate"	);
		this.sizeOfMap =		this.getMap().giveOuterParameter(	this,	Integer.class,			"size"		);
		this.doConnectStop();
		
		this.doCleanLoads();
	}
	
	/**
	 * 
	 * Class the thrifty of management of generating the maps.
	 * 
	 * @param map              <! VARIABLE >
	 * @param rememberInternal <! VARIABLE >
	 * @param rememberExternal <! VARIABLE >
	 * 
	 * @since   1.1_05         <! PERMANENT >
	 * @version 1.1_05         <! VARIABLE >
	 * @author  Bartosz Konkol <! VARIABLE >
	 * 
	 * @throws DefectLWJGL
	 * 
	 */
	public ThriftyMap(final Map map, final boolean rememberInternal, final boolean rememberExternal) throws DefectLWJGL
	{
		this(map, 60, rememberInternal, rememberExternal);
	}
	
	/**
	 * 
	 * Class the thrifty of management of generating the maps.
	 * 
	 * @param map              <! VARIABLE >
	 * @param rememberExternal <! VARIABLE >
	 * 
	 * @since   1.1_05         <! PERMANENT >
	 * @version 1.1_05         <! VARIABLE >
	 * @author  Bartosz Konkol <! VARIABLE >
	 * 
	 * @throws DefectLWJGL
	 * 
	 */
	public ThriftyMap(final Map map, final boolean rememberExternal) throws DefectLWJGL
	{
		this(map, false, rememberExternal);
	}
	
	/**
	 * 
	 * Class the thrifty of management of generating the maps.
	 * 
	 * @param map              <! VARIABLE >
	 * @param refresh          <! VARIABLE >
	 * @param rememberExternal <! VARIABLE >
	 * 
	 * @since   1.1_05         <! PERMANENT >
	 * @version 1.1_05         <! VARIABLE >
	 * @author  Bartosz Konkol <! VARIABLE >
	 * 
	 * @throws DefectLWJGL
	 * 
	 */
	public ThriftyMap(final Map map, final int refresh, final boolean rememberExternal) throws DefectLWJGL
	{
		this(map, refresh, false, rememberExternal);
	}
	
	/**
	 * 
	 * Class the thrifty of management of generating the maps.
	 * 
	 * @param map     <! VARIABLE >
	 * @param refresh <! VARIABLE >
	 * 
	 * @since   1.1_05         <! PERMANENT >
	 * @version 1.1_05         <! VARIABLE >
	 * @author  Bartosz Konkol <! VARIABLE >
	 * 
	 * @throws DefectLWJGL
	 * 
	 */
	public ThriftyMap(final Map map, final int refresh) throws DefectLWJGL
	{
		this(map, refresh, true);
	}
	
	/**
	 * 
	 * Class the thrifty of management of generating the maps.
	 * 
	 * @param map <! VARIABLE >
	 * 
	 * @since   1.1_05         <! PERMANENT >
	 * @version 1.1_05         <! VARIABLE >
	 * @author  Bartosz Konkol <! VARIABLE >
	 * 
	 * @throws DefectLWJGL
	 * 
	 */
	public ThriftyMap(final Map map) throws DefectLWJGL
	{
		this(map, 60);
	}
	
	private Map map;
	private int refresh;
	private boolean rememberInternal, rememberExternal;
	
	/**
	 * Variable a connection.<br>Do not change manually! <! VARIABLE >
	 * @since   1.1_05                                   <! PERMANENT >
	 * @version 1.1_05                                   <! VARIABLE >
	 * @author  Bartosz Konkol                           <! VARIABLE >
	 */
	boolean connect = false;
	
	/**
	 * Variable a time.<br>Do not change manually! <! VARIABLE >
	 * @since   1.1_05                             <! PERMANENT >
	 * @version 1.1_05                             <! VARIABLE >
	 * @author  Bartosz Konkol                     <! VARIABLE >
	 */
	protected int time = 0;
	
	/**
	 * Map the position of chunks and their corresponding  IDs of LWJGL list. <! VARIABLE >
	 * @since   1.1_05                                                        <! PERMANENT >
	 * @version 1.1_05                                                        <! VARIABLE >
	 * @author  Bartosz Konkol                                                <! VARIABLE >
	 */
	protected final java.util.Map<Point, Integer> idsList = new HashMap<Point, Integer>();
	/**
	 * Technical list of elements of chunks. <! VARIABLE >
	 * @since   1.1_05                       <! PERMANENT >
	 * @version 1.1_05                       <! VARIABLE >
	 * @author  Bartosz Konkol               <! VARIABLE >
	 */
	protected final List<Point> idsTechnicalList = new ArrayList<Point>();
	
	/**
	 * Specify the map.        <! VARIABLE >
	 * @since   1.1_05         <! PERMANENT >
	 * @version 1.1_05         <! VARIABLE >
	 * @author  Bartosz Konkol <! VARIABLE >
	 */
	public final ThriftyMap setMap(final Map map)
	{
		this.map = map;
		return this;
	}
	
	/**
	 * Return the map.         <! VARIABLE >
	 * @since   1.1_05         <! PERMANENT >
	 * @version 1.1_05         <! VARIABLE >
	 * @author  Bartosz Konkol <! VARIABLE >
	 */
	public final Map getMap()
	{
		return this.map;
	}
	
	/**
	 * Specify the refresh rate. <! VARIABLE >
	 * @since   1.1_05           <! PERMANENT >
	 * @version 1.1_05           <! VARIABLE >
	 * @author  Bartosz Konkol   <! VARIABLE >
	 */
	public final ThriftyMap setRefresh(final int refresh)
	{
		this.refresh = refresh;
		return this;
	}
	
	/**
	 * Return the refresh rate. <! VARIABLE >
	 * @since   1.1_05          <! PERMANENT >
	 * @version 1.1_05          <! VARIABLE >
	 * @author  Bartosz Konkol  <! VARIABLE >
	 */
	public final int getRefresh()
	{
		return this.refresh;
	}
	
	/**
	 * Specify the rules of memorizing. <! VARIABLE >
	 * @since   1.1_05                  <! PERMANENT >
	 * @version 1.1_05                  <! VARIABLE >
	 * @author  Bartosz Konkol          <! VARIABLE >
	 */
	public final ThriftyMap setRemember(final boolean rememberInternal, final boolean rememberExternal)
	{
		this.rememberExternal = rememberExternal;
		this.rememberInternal = rememberInternal;
		return this;
	}
	
	/**
	 * Return the rules of memorizing. <! VARIABLE >
	 * @since   1.1_05                 <! PERMANENT >
	 * @version 1.1_05                 <! VARIABLE >
	 * @author  Bartosz Konkol         <! VARIABLE >
	 */
	public final boolean getRememberInternal()
	{
		return this.rememberInternal;
	}
	
	/**
	 * Return the rules of memorizing. <! VARIABLE >
	 * @since   1.1_05                 <! PERMANENT >
	 * @version 1.1_05                 <! VARIABLE >
	 * @author  Bartosz Konkol         <! VARIABLE >
	 */
	public final boolean getRememberExternal()
	{
		return this.rememberExternal;
	}
	
	/**
	 * Return the rules of memorizing. <! VARIABLE >
	 * @since   1.1_05                 <! PERMANENT >
	 * @version 1.1_05                 <! VARIABLE >
	 * @author  Bartosz Konkol         <! VARIABLE >
	 */
	public final boolean getRemember()
	{
		return this.getRememberExternal() && this.getRememberInternal();
	}
	
	/**
	 * Opening connections.    <! VARIABLE >
	 * @since   1.1_05         <! PERMANENT >
	 * @version 1.1_05         <! VARIABLE >
	 * @author  Bartosz Konkol <! VARIABLE >
	 */
	protected final void doConnectStart()
	{
		this.connect = true;
	}
	
	/**
	 * Exiting connections.    <! VARIABLE >
	 * @since   1.1_05         <! PERMANENT >
	 * @version 1.1_05         <! VARIABLE >
	 * @author  Bartosz Konkol <! VARIABLE >
	 */
	protected final void doConnectStop()
	{
		this.connect = false;
	}
	
	/**
	 * Generating of the chunk. <! VARIABLE >
	 * @since   1.1_05          <! PERMANENT >
	 * @version 1.1_05          <! VARIABLE >
	 * @author  Bartosz Konkol  <! VARIABLE >
	 */
	protected final void doGenerateChunk(final Point site) throws DefectLWJGL
	{
		if(this.idsList.get(site) != null)
			glDeleteLists(this.idsList.get(site), (int) Mathematical.mathematicalConstants("one"));
		this.idsList.put(site, glGenLists((int) Mathematical.mathematicalConstants("one")));
	
		glNewList(this.idsList.get(site), GL_COMPILE);
		
		this.doConnectStart();
		this.getMap().doOuterGenerateTerrain(this, this.sizeOfMap, this.heightOfMap, site);
		this.doConnectStop();
		
		glEndList();
		
		this.idsTechnicalList.add(site);
		
		glCallList(this.idsList.get(site));
	}
	
	/**
	 * Generating of the chunk. <! VARIABLE >
	 * @since   1.1_05          <! PERMANENT >
	 * @version 1.1_05          <! VARIABLE >
	 * @author  Bartosz Konkol  <! VARIABLE >
	 */
	protected final void doGenerateChunk(final int x, final int z) throws DefectLWJGL
	{
		this.doGenerateChunk(new Point(x, z));
	}
	
	/**
	 * Control of the generation of chunks and behavior of the map. <! VARIABLE >
	 * @since   1.1_05                                              <! PERMANENT >
	 * @version 1.1_05                                              <! VARIABLE >
	 * @author  Bartosz Konkol                                      <! VARIABLE >
	 */
	protected void doManagement() throws DefectLWJGL
	{
		this.doTimerControl();
		
		final Point position = new Point(Math.round(this.cameraOfMap.getPosition().getX()), Math.round(this.cameraOfMap.getPosition().getZ()));
		final int x = this.getMap().giveCalculateChunk(position.getX());
		final int z = this.getMap().giveCalculateChunk(position.getY());
		
		final Point chunk1 = new Point(	x													,	z													);
		final Point chunk2 = new Point(	x													,	z - (int) Mathematical.mathematicalConstants("one")	);
		final Point chunk3 = new Point(	x + (int) Mathematical.mathematicalConstants("one")	,	z - (int) Mathematical.mathematicalConstants("one")	);
		final Point chunk4 = new Point(	x + (int) Mathematical.mathematicalConstants("one")	,	z													);
		final Point chunk5 = new Point(	x + (int) Mathematical.mathematicalConstants("one")	,	z + (int) Mathematical.mathematicalConstants("one")	);
		final Point chunk6 = new Point(	x													,	z + (int) Mathematical.mathematicalConstants("one")	);
		final Point chunk7 = new Point(	x - (int) Mathematical.mathematicalConstants("one")	,	z + (int) Mathematical.mathematicalConstants("one")	);
		final Point chunk8 = new Point(	x - (int) Mathematical.mathematicalConstants("one")	,	z													);
		final Point chunk9 = new Point(	x - (int) Mathematical.mathematicalConstants("one")	,	z - (int) Mathematical.mathematicalConstants("one")	);
		
		if(this.idsList.get(chunk1) == null || !this.getRememberInternal())
			this.doGenerateChunk(chunk1);
		else
			glCallList(this.idsList.get(chunk1));
		
		if(this.idsList.get(chunk2) == null || !this.getRememberExternal())
			this.doGenerateChunk(chunk2);
		else
			glCallList(this.idsList.get(chunk2));
		
		if(this.idsList.get(chunk3) == null || !this.getRememberExternal())
			this.doGenerateChunk(chunk3);
		else
			glCallList(this.idsList.get(chunk3));
		
		if(this.idsList.get(chunk4) == null || !this.getRememberExternal())
			this.doGenerateChunk(chunk4);
		else
			glCallList(this.idsList.get(chunk4));
		
		if(this.idsList.get(chunk5) == null || !this.getRememberExternal())
			this.doGenerateChunk(chunk5);
		else
			glCallList(this.idsList.get(chunk5));
		
		if(this.idsList.get(chunk6) == null || !this.getRememberExternal())
			this.doGenerateChunk(chunk6);
		else
			glCallList(this.idsList.get(chunk6));
		
		if(this.idsList.get(chunk7) == null || !this.getRememberExternal())
			this.doGenerateChunk(chunk7);
		else
			glCallList(this.idsList.get(chunk7));
		
		if(this.idsList.get(chunk8) == null || !this.getRememberExternal())
			this.doGenerateChunk(chunk8);
		else
			glCallList(this.idsList.get(chunk8));
		
		if(this.idsList.get(chunk9) == null || !this.getRememberExternal())
			this.doGenerateChunk(chunk9);
		else
			glCallList(this.idsList.get(chunk9));
		
		this.cameraOfMap.doNavigateGL(this.navigateOfMap.pilot, this.navigateOfMap.speedMovementMouse, this.navigateOfMap.speedMovementSurface, this.navigateOfMap.speedMovementHeight, this.navigateOfMap.speedMovementTurn);
		
		this.doConnectStart();
		this.getMap().doOuterMotion(this, this.cameraOfMap, this.heightOfMap, this.sizeOfMap);
		this.doConnectStop();
	}
	
	/**
	 * Event management of time. <! VARIABLE >
	 * @since   1.1_05           <! PERMANENT >
	 * @version 1.1_05           <! VARIABLE >
	 * @author  Bartosz Konkol   <! VARIABLE >
	 */
	protected void doTimerControl()
	{
		if(this.getRefresh() > Mathematical.mathematicalConstants("zero"))
		{
			if(this.time >= this.getRefresh())
			{
				this.doCleanLoads();
				
				this.time = (int) Mathematical.mathematicalConstants("zero");
			}
			
			this.time++;
		}
	}
	
	/**
	 * Cleaning the letter and the freeing memory. <! VARIABLE >
	 * @since   1.1_05                             <! PERMANENT >
	 * @version 1.1_05                             <! VARIABLE >
	 * @author  Bartosz Konkol                     <! VARIABLE >
	 */
	public final void doCleanLoads()
	{
		for(int i = (int) Mathematical.mathematicalConstants("zero"); i < this.idsTechnicalList.size(); i++)
			glDeleteLists(this.idsList.get(this.idsTechnicalList.get(i)), (int) Mathematical.mathematicalConstants("one"));
		
		this.idsTechnicalList.clear();
		this.idsList.clear();
		
		System.gc();
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
			this.doManagement();
		}
		catch (DefectLWJGL e)
		{
			e.printStackTrace();
		}
	}
	
}
