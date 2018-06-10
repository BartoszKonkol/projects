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
		
		final String[] text1 = this.doWriteWrap(this.giveTab() + DataTexts.versionVW + " - to modyfikacja do gry " + DataTexts.nameMC + " dodaj¹ca nowe biomy, bloki, itemy, komendy, moby, osi¹gniêcia, struktury, œwiaty, zbrojê, i wiele, wiele wiêcej. Godnymi uwagi s¹ takie bloki jak: Radio (po uruchomieniu gra muzyka), Miêkkie Bloki (czyli Topniej¹cy œnieg i Ulepszony oraz zwyk³y Têczowy Blok; s¹ bloki, w którym mo¿na siê zapadaæ), Ulepszony têczowy blok (daje mo¿liwoœæ wysokiego skakania), Blok Akwamarynu (umo¿liwia szybkie poruszanie siê w dowolnym kierunku) i Zabójczy blok (po dotkniêciu go przez moba/gracza, byt traci 20 punktów ¿ycia). Ciekawym mobem jest równie¿ Herobrine. Posiada on wiele efektów specjalnych. Z itemów najciekawszymi s¹ Sztylety (w 9 rodzajach) oraz Kamieñ Przemiany. W sumie (w wersji Beta 1.2) modyfikacja dodaje 3 biomy, 20 bloków (w tym 1 blok techniczny), 35 itemów (w tym 1 item techniczny i 4 czêœci zbroi), 2 komendy, 2 moby, 2 osi¹gniêcia, 1 strukturê, 1 œwiat i 1 zbrojê. Modyfikacja " + DataTexts.nameVW + " zmienia równie¿ wiele aspektów technicznych i graficznych gry " + DataTexts.nameMC + ".", 10, 0, 55);
		final String[] text2 = this.doWriteWrap(this.giveTab() + "Twórc" + (Cocreators.namesOfficial.size() > 1 ? "ami" : "¹") + " modyfikacji " + DataTexts.nameVW + " " + (Cocreators.namesOfficial.size() > 1 ? "s¹" : "jest") + " " + authors + ".", 10, 0, 60 + this.fontRendererObj.FONT_HEIGHT * text1.length);
		
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
