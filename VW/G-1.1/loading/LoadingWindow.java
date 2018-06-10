package vw.loading;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.JNF;
import javax.imageio.ImageIO;
import javax.jnf.importation.Window;
import javax.swing.JPanel;
import vw.util.DataTexts;

public final class LoadingWindow extends Window implements Runnable
{
	
	public static boolean active = false;
	public static boolean close = false;
	
	public LoadingWindow(final File par1File, final File par2File)
	{
		
		super(
				DataTexts.fullNameMC + " & " + DataTexts.versionVW,
				new File(new File(par2File, "icons"), "icon_32x32.png"),
				WIDTH, HEIGHT,
				true, true, false);
		
		this.setVisible(false);
		
		final File image = new File(par1File, "NotificationLoading.png");
		
		try
        {
			
			JNF.giveFiles().downloadImageThrow(image, "http://data.virtualworld.keed.pl/" + image.getName());
			
			this.add(new JPanel()
			{
				
				final private BufferedImage bufferImage = ImageIO.read(image);
				
				{
					
					this.setPreferredSize(new Dimension(this.bufferImage.getWidth(), this.bufferImage.getHeight()));
					
				}
				
				@Override
			    public void paintComponent(final Graphics par1Graphics)
			    {
			    	
			    	par1Graphics.drawImage(this.bufferImage, 0, 0, this);
			        
			    }
				
			});
			
        }
        catch (final IOException e)
        {
        	
            e.printStackTrace();
            
        }
		
		this.setDefaultCloseOperation(this.DISPOSE_ON_CLOSE);
		
		this.pack();
		this.doAdjustLocation();
		
		this.setVisible(true);
		
		this.active = true;
		
	}

	@Override
	public final void run()
	{
		
		while(this.active)
		{
			
			if(this.close)
			{
				
				this.active = false;
				this.dispose();
				
			}
			
		}
		
	}

}
