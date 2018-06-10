package javax.jao.registry;

public enum KeyParentJAO
{

	MAIN,
	DATA,
	APPS,
	USER,
	TEMP,
	;
	
	public final String giveName()
	{
		return this.name().toLowerCase();
	}
	
	public final String giveNameForm()
	{
		return this.name().substring(0, 1) + this.giveName().substring(1);
	}
	
}
