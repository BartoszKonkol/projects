package vw.client.gui;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiOptions;

public class GuiScreenMenu extends GuiScreenVW
{

	public static final byte SCAN = 1;
	
	public GuiScreenMenu(final GuiOptions par1GuiOptions)
	{
		
		super(par1GuiOptions);
		
	}

	@Override
	protected void giveButtons()
	{
		
		this.addButtonLeft(		001,	this.giveLanguage(	"Informacje",	"Information"	),	40	);
		this.addButtonRight(	002,	this.giveLanguage(	"Konfiguracja",	"Configuration"	),	40	);
		
	}

	@Override
	protected void giveTransitionGui(final GuiButton par1GuiButton)
	{
		
		this.doTransitionGui(	par1GuiButton,	001,	new GuiScreenInformation(this)		);
		this.doTransitionGui(	par1GuiButton,	002,	new GuiScreenConfiguration(this)	);
		
	}

	@Override
	protected void giveTextElements()
	{
		
		;
		
	}
	
	protected final String giveLanguage(final String textPL, final String textEN)
	{
		
		return this.getLanguage().getLanguageCode().equals("pl_PL") ? textPL : textEN;
		
	}
	
}
