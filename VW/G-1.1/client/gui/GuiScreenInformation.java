package vw.client.gui;

import javax.jnf.lwjgl.Color;
import org.lwjgl.opengl.GL11;
import vw.util.Cocreators;
import vw.util.DataTexts;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;

public class GuiScreenInformation extends GuiScreenVW
{
	
	public static final byte SCAN = 1;
	
	private GuiScreenMenu guiScreenMenu;

	public GuiScreenInformation(final GuiScreenMenu par1GuiScreenMenu)
	{
		
		super(par1GuiScreenMenu, par1GuiScreenMenu.getGuiOptions());
		this.setGuiScreenMenu(par1GuiScreenMenu);
		
	}

	@Override
	protected void giveButtons()
	{
		
		;
		
	}

	@Override
	protected void giveTransitionGui(final GuiButton par1GuiButton)
	{
		
		;
		
	}

	@Override
	protected void giveTextElements()
	{
		
		this.doWrite(	this.getGuiScreenMenu().giveLanguage(	"Informacje",	"Information"	),	30	);
		
		this.giveInformations();
		
	}
	
	private final void giveInformations()
	{
		
		String authors = "";
		if(Cocreators.namesOfficial.size() > 2)
		{
			
			for(int i = 0; i < Cocreators.namesOfficial.size() - 2; i++)
				authors += String.valueOf(Cocreators.namesOfficial.get(i)) + ", ";
			
			authors += String.valueOf(Cocreators.namesOfficial.get(Cocreators.namesOfficial.size() - 2)) + " i " + String.valueOf(Cocreators.namesOfficial.get(Cocreators.namesOfficial.size() - 1));
			
		}
		else if(Cocreators.namesOfficial.size() == 2)
			authors += String.valueOf(Cocreators.namesOfficial.get(Cocreators.namesOfficial.size() - 2)) + " i " + String.valueOf(Cocreators.namesOfficial.get(Cocreators.namesOfficial.size() - 1));
		else if(Cocreators.namesOfficial.size() == 1)
			authors += String.valueOf(Cocreators.namesOfficial.get(Cocreators.namesOfficial.size() - 1));
		else
			authors += "-?-";
		
		final String[] text1 = this.doWriteWrap(this.giveTab() + DataTexts.versionVW + " - to modyfikacja do gry " + DataTexts.nameMC + " dodaj�ca nowe biomy, bloki, itemy, komendy, moby, osi�gni�cia, struktury, �wiaty, zbroj�, i wiele, wiele wi�cej. Godnymi uwagi s� takie bloki jak: Radio (po uruchomieniu gra muzyka), Mi�kkie Bloki (czyli Topniej�cy �nieg i Ulepszony oraz zwyk�y T�czowy Blok; s� bloki, w kt�rym mo�na si� zapada�), Ulepszony t�czowy blok (daje mo�liwo�� wysokiego skakania), Blok Akwamarynu (umo�liwia szybkie poruszanie si� w dowolnym kierunku) i Zab�jczy blok (po dotkni�ciu go przez moba/gracza, byt traci 20 punkt�w �ycia). Ciekawym mobem jest r�wnie� Herobrine. Posiada on wiele efekt�w specjalnych. Z item�w najciekawszymi s� Sztylety (w 9 rodzajach) oraz Kamie� Przemiany. W sumie (w wersji Beta 1.2) modyfikacja dodaje 3 biomy, 20 blok�w (w tym 1 blok techniczny), 35 item�w (w tym 1 item techniczny i 4 cz�ci zbroi), 2 komendy, 2 moby, 2 osi�gni�cia, 1 struktur�, 1 �wiat i 1 zbroj�. Modyfikacja " + DataTexts.nameVW + " zmienia r�wnie� wiele aspekt�w technicznych i graficznych gry " + DataTexts.nameMC + ".", 10, 0, 55);
		final String[] text2 = this.doWriteWrap(this.giveTab() + "Tw�rc" + (Cocreators.namesOfficial.size() > 1 ? "ami" : "�") + " modyfikacji " + DataTexts.nameVW + " " + (Cocreators.namesOfficial.size() > 1 ? "s�" : "jest") + " " + authors + ".", 10, 0, 60 + this.fontRendererObj.FONT_HEIGHT * text1.length);
		
		this.doBackgroundShade(5, this.width - 5, 50, 65 + this.fontRendererObj.FONT_HEIGHT * (text1.length + text2.length));
		
	}
	
	protected final String giveTab()
	{
		
		return "    ";
		
	}
	
	protected final void setGuiScreenMenu(final GuiScreenMenu par1GuiScreenMenu)
	{
		
		this.guiScreenMenu = par1GuiScreenMenu;
		
	}
	
	protected final GuiScreenMenu getGuiScreenMenu()
	{
		
		return this.guiScreenMenu;
		
	}
	
	protected final String[] doWriteWrap(final String text, final int x,final int y)
	{
		
		return this.doWriteWrap(text, 0, x, y);
		
	}
	
	protected final String[] doWriteWrap(final String text, final int space, final int x, final int y)
	{
		
		return this.doWriteWrap(text, Color.WHITE, space, x, y);
		
	}
	
	protected final String[] doWriteWrap(final String text, final Color color, final int space, final int x, final int y)
	{
		
		return this.doWriteWrap(text, color, space, x, y, false);
		
	}
	
	protected final String[] doWriteWrap(final String text, final int y)
	{
		
		return this.doWriteWrap(text, 0, y, true);
		
	}
	
	protected final String[] doWriteWrap(final String text, final int x, final int y, final boolean center)
	{
		
		return this.doWriteWrap(text, 0, x, y, center);
		
	}
	
	protected final String[] doWriteWrap(final String text, final int space, final int x, final int y, final boolean center)
	{
		
		return this.doWriteWrap(text, Color.WHITE, space, x, y, center);
		
	}
	
	protected final String[] doWriteWrap(final String text, final Color color, final int space, final int x, final int y, final boolean center)
	{
		
		final Object[] tableObject = this.fontRendererObj.listFormattedStringToWidth(text, this.width - x - space * 2).toArray();
		final String[] tableString = new String[tableObject.length];
		
		for (int i = 0; i < tableObject.length; i++)
		{
			
			tableString[i] = String.valueOf(tableObject[i]);
			this.doWrite(tableString[i], color, x + space, y + i * this.fontRendererObj.FONT_HEIGHT, center);
			
		}
		
		return tableString;
		
	}
	
	protected final boolean doBackgroundShade(final double x1, final double x2, final double y1, final double y2)
	{
		
		return this.doBackgroundShade(x1, x2, y1, y2, new Color(0, 66));
		
	}
	
	protected final boolean doBackgroundShade(final double x1, final double x2, final double y1, final double y2, final Color color)
	{
		
		if(x1 != x2 && y1 != y2)
		{
		
			Tessellator tessellator = Tessellator.getInstance();
			WorldRenderer worldrenderer = tessellator.getWorldRenderer();
			
			GlStateManager.enableBlend();
			GlStateManager.disableTexture2D();
			GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
			worldrenderer.startDrawingQuads();
			worldrenderer.setColorRGBA_I(color.getColor(), (Color.MAX_VALUE_TRANSPARENT - color.getTransparent()) * 255 / Color.MAX_VALUE_TRANSPARENT);
			worldrenderer.addVertex(x1, y2, 0.0D);
			worldrenderer.addVertex(x2, y2, 0.0D);
			worldrenderer.addVertex(x2, y1, 0.0D);
			worldrenderer.addVertex(x1, y1, 0.0D);
			tessellator.draw();
			GlStateManager.enableTexture2D();
			GlStateManager.disableBlend();
			
			return true;
		
		}
		else
			return false;
		
	}

}
