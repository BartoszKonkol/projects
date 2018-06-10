package net.polishgames.rhenowar.util;

import java.io.File;
import java.util.Map;
import org.luaj.vm2.Globals;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.jse.JsePlatform;
import net.polishgames.rhenowar.util.RhenowarObject;
import net.polishgames.rhenowar.util.Util;

public class Script extends RhenowarObject
{
	
	private final Globals globals;
	
	public Script(final Globals globals)
	{
		this.globals = Util.nonNull(globals);
	}
	
	public Script(final File file, final String... tables)
	{
		final Globals globals = JsePlatform.standardGlobals();
		for(final String table : Util.nonNull(tables))
			globals.set(Util.nonEmpty(table), LuaValue.tableOf());
		globals.get("dofile").call(LuaValue.valueOf(Util.nonNull(file).getAbsolutePath()));
		this.globals = globals;
	}
	
	public final Globals giveGlobals()
	{
		return this.globals;
	}
	
	@Override
	public Map<String, Object> giveProperties(final Map<String, Object> map)
	{
		map.put("globals", this.giveGlobals());
		return map;
	}
	
}
