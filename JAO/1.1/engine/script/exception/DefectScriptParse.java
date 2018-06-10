package javax.jao.script.exception;

public class DefectScriptParse extends DefectScript
{
	
	private static final long serialVersionUID = 6852765175875948527L;

	public DefectScriptParse(final String content, final String reason)
	{
		super((reason != null ? reason : "Parse error in the script code") + ": " + content);
	}
	
	public DefectScriptParse(final String content)
	{
		this(content, null);
	}
	
}
