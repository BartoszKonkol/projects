package javax.jao.script.exception;

import javax.jnf.exception.Defect;

public abstract class DefectScript extends Defect
{

	private static final long serialVersionUID = 7821270656310245717L;

	public DefectScript()
	{
		super();
	}
	
	public DefectScript(final String reason)
	{
		super(reason);
	}
	
}
