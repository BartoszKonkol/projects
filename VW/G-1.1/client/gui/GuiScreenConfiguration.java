package vw.client.gui;

import javax.JNF;
import vw.entity.monster.EntityHerobrine;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.resources.I18n;

public class GuiScreenConfiguration extends GuiScreenVW
{
	
	public static final byte SCAN = 1;
	
	private GuiScreenMenu guiScreenMenu;
	
	protected final String textHerobrineRemove;

	public GuiScreenConfiguration(final GuiScreenMenu par1GuiScreenMenu)
	{
		
		super(par1GuiScreenMenu, par1GuiScreenMenu.getGuiOptions());
		this.setGuiScreenMenu(par1GuiScreenMenu);
		
		this.textHerobrineRemove = this.getGuiScreenMenu().giveLanguage(	"Tworzenie si\u0119 Herobrine'\u00f3w",	"Forming of Herobrine"	) + ": ";
		
	}
	
	protected GuiButton herobrineRemove;

	@Override
	protected void giveButtons()
	{
		
		this.herobrineRemove = this.addButtonLeft(	201,	this.textHerobrineRemove + (getHerobrineRemove() ? I18n.format("options.off") : I18n.format("options.on")),	55	);
		
	}

	@Override
	protected void giveTransitionGui(final GuiButton par1GuiButton)
	{
		
		if (par1GuiButton.enabled && par1GuiButton.id == this.herobrineRemove.id)
        {
        	
        	if(this.getHerobrineRemove())
        	{
        		
        		this.setHerobrineRemove(false);
        		this.herobrineRemove.displayString = this.textHerobrineRemove + I18n.format("options.on");
        		
        	}
        	else
        	{
        		
        		this.setHerobrineRemove(true);
        		this.herobrineRemove.displayString = this.textHerobrineRemove + I18n.format("options.off");
        		
        	}
        	
        }
		
	}

	@Override
	protected void giveTextElements()
	{
		
		this.doWrite(	this.getGuiScreenMenu().giveLanguage(	"Konfiguracja",	"Configuration"	),	30	);
		
	}
	
	protected final void setHerobrineRemove(final boolean remove)
	{
		
		EntityHerobrine.remove = remove;
		
		JNF.giveFiles().vaSaveReturn(this.mc.mcDataDir + "\\vw\\settings.va",
				new Object[]{"RemoveHerobrine", }, 
				new Object[]{remove,			},
			true);
		
	}
	
	protected final boolean getHerobrineRemove()
	{
		
		return Boolean.valueOf(String.valueOf(JNF.giveFiles().vaLoadReturn(this.mc.mcDataDir + "\\vw\\settings.va", "RemoveHerobrine")));
		
	}
	
	protected final void setGuiScreenMenu(final GuiScreenMenu par1GuiScreenMenu)
	{
		
		this.guiScreenMenu = par1GuiScreenMenu;
		
	}
	
	protected final GuiScreenMenu getGuiScreenMenu()
	{
		
		return this.guiScreenMenu;
		
	}

}
