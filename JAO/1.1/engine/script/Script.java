package javax.jao.script;

import javax.jao.script.exception.DefectScript;

public abstract class Script
{
	
	public Script(final String name)
	{
		
	}
	
	public abstract void onThrow(final DefectScript defect);
	
}
