package net.polishgames.rhenowar.conquest;

public enum Data implements IEnum
{
	
	NAME("Conquest"),
	AUTHOR("Bartosz Konkol"),
	AUTHOR_NICK("BartoszKonkol"),
	AUTHOR_ISSUED("\u00A9 Bartosz Konkol"),
	SERVER("McLobby.pl");
	
	private final String text;
	
	private Data(final String text)
	{
		this.text = text;
	}
	
	public final String giveText()
	{
		return this.text;
	}
	
	@Override
	public final String toString()
	{
		return this.giveText();
	}
	
}
