package vw.client.gui;

import javax.jnf.lwjgl.Color;
import vw.util.DataTexts;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiOptions;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.resources.Language;

public abstract class GuiScreenVW extends GuiScreen
{
	
	public static final byte SCAN = 1;
	
	private GuiScreen guiScreen;
	private GuiOptions guiOptions;
	private Language language;
	
	public GuiScreenVW(final GuiOptions par1GuiOptions)
	{
		
		this(par1GuiOptions, par1GuiOptions.mc.getLanguageManager().getCurrentLanguage());
		
	}
	
	public GuiScreenVW(final GuiOptions par1GuiOptions, final Language par2Language)
	{
		
		this(par1GuiOptions, par1GuiOptions, par2Language);
		
	}
	
	public GuiScreenVW(final GuiScreen par1GuiScreen, final GuiOptions par2GuiOptions)
	{
		
		this(par1GuiScreen, par2GuiOptions, par2GuiOptions.mc.getLanguageManager().getCurrentLanguage());
		
	}
	
	public GuiScreenVW(final GuiScreen par1GuiScreen, final GuiOptions par2GuiOptions, final Language par3Language)
	{
		
		this.setGuiScreen(par1GuiScreen);
		this.setGuiOptions(par2GuiOptions);
		this.setLanguage(par3Language);
		
	}
	
	protected abstract void giveButtons();
	
	protected abstract void giveTransitionGui(final GuiButton par1GuiButton);
	
	protected abstract void giveTextElements();
	
	@Override
	public void initGui()
	{
		
		this.addButton(0, I18n.format("gui.done"), this.width / 2 - 100, this.height - 30);
		
		this.giveButtons();
		
	}
	
	@Override
	protected void actionPerformed(final GuiButton par1GuiButton)
	{
		
		this.doTransitionGui(par1GuiButton, 0, this.getGuiScreen());
		
		this.giveTransitionGui(par1GuiButton);
		
	}
	
	@Override
	public void drawScreen(final int par1, final int par2, final float par3)
	{
		
		this.drawDefaultBackground();
		
		this.doWrite(DataTexts.nameVW);
		
		this.giveTextElements();
		
		super.drawScreen(par1, par2, par3);
		
	}
	
	protected final void setGuiScreen(final GuiScreen par1GuiScreen)
	{
		
		this.guiScreen = par1GuiScreen;
		
	}
	
	protected final GuiScreen getGuiScreen()
	{
		
		return this.guiScreen;
		
	}
	
	protected final void setGuiOptions(final GuiOptions par1GuiOptions)
	{
		
		this.guiOptions = par1GuiOptions;
		
	}
	
	protected final GuiOptions getGuiOptions()
	{
		
		return this.guiOptions;
		
	}
	
	protected final void setLanguage(final Language par1Language)
	{
		
		this.language = par1Language;
		
	}
	
	protected final Language getLanguage()
	{
		
		return this.language;
		
	}
	
	protected final GuiButton addButtonLeft(final int id, final String text, final int y)
	{
		
		return this.addButton(new GuiButton(id, this.width / 2 - 152, y, 150, 20, text));
		
	}
	
	protected final GuiButton addButtonRight(final int id, final String text, final int y)
	{
		
		return this.addButton(new GuiButton(id, this.width / 2 + 2, y, 150, 20, text));
		
	}
	
	protected final GuiButton addButton(final int id, final String text, final int x, final int y)
	{
		
		return this.addButton(new GuiButton(id, x, y, text));
		
	}
	
	@SuppressWarnings("unchecked")
	protected final GuiButton addButton(final GuiButton par1GuiButton)
	{
		
		this.buttonList.add(par1GuiButton);
		return par1GuiButton;
		
	}
	
	protected final String doWrite(final String text, final int x, final int y)
	{
		
		return this.doWrite(text, x, y, false);
		
	}
	
	protected final String doWrite(final String text)
	{
		
		return this.doWrite(text, 15);
		
	}
	
	protected final String doWrite(final String text, final int y)
	{
		
		return this.doWrite(text, 0, y, true);
		
	}
	
	protected final String doWrite(final String text, final int x, final int y, final boolean center)
	{
		
		return this.doWrite(text, Color.WHITE, x, y, center);
		
	}
	
	protected final String doWrite(final String text, final Color color, final int x, final int y)
	{
		
		return this.doWrite(text, color, x, y, false);
		
	}
	
	protected final String doWrite(final String text, final Color color, final int x, final int y, final boolean center)
	{
		
		if(center && x == 0)
			this.drawCenteredString(this.fontRendererObj, text, this.width / 2, y, color.getColor());
		else
			this.drawString(this.fontRendererObj, text, x, y, color.getColor());
		
		return text;
		
	}
	
	protected final GuiScreen doTransitionGui(final GuiButton button, final int id, final GuiScreen gui)
	{
		
		 if (button.enabled && button.id == id)
		 {
			 
			 this.mc.displayGuiScreen(gui);
			 return gui;
			 
		 }
		 
		 return null;
		
	}
	
}
