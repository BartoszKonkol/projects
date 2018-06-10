package net.polishgames.konkol.pszemeksoft.spherical;

import java.awt.Canvas;
import java.awt.Robot;
//import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import javax.jnf.lwjgl.Camera;
import javax.jnf.lwjgl.Color;
import javax.jnf.lwjgl.Keys;
import javax.jnf.lwjgl.KeysList;
import javax.jnf.lwjgl.PilotKeys;
import javax.jnf.lwjgl.Point3D;
import javax.jnf.lwjgl.ProjectionType;
import javax.jnf.lwjgl.Window;
import javax.jnf.lwjgl.structure.Ball;
import javax.jnf.lwjgl.structure.Structure2D;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;

public final class Visualizer extends Window
{
	
//	protected static final int SIZE = (int) (Toolkit.getDefaultToolkit().getScreenSize().height / 1.5F);
	
	public static boolean destroy;
	public static Canvas parent;
	public static Color backgroundColor, figureColor;
	
	public Visualizer(final String title)
	{
		super(title, new DisplayMode(/* Visualizer.SIZE, Visualizer.SIZE */ Visualizer.parent.getWidth(), Visualizer.parent.getHeight()), 60, false, false, ProjectionType.PERSPECTIVE.setFar(100.0F).setNear(0.1F).setPosition(ProjectionType.POSITION_PERSPECTIVE_CENTER));
	}
	
	protected Robot robot;
	protected Camera camera;
	protected PilotKeys pilot;
	protected Structure2D[] figure;
	
	private float ballRotate;
	
	private int whell;
	
	@Override
	protected final void doSpecify() throws Exception
	{
		super.doSpecify();
		
		Display.setParent(Visualizer.parent);
//		Display.setLocation(100, (Toolkit.getDefaultToolkit().getScreenSize().height - Display.getHeight()) / 2);
		
		this.robot = new Robot();
		this.camera = new Camera().setPosition(0.0F, 1.5F, 2.0F).setPitch(-90);
		this.pilot = new PilotKeys(
			new Keys[]{KeysList.PLUS, KeysList.EQUALS},
			new Keys[]{KeysList.MINUS, KeysList.HYPHEN},
			new Keys[]{KeysList.giveKeysList(Keys.LEFT), KeysList.giveKeysList(Keys.A)},
			new Keys[]{KeysList.giveKeysList(Keys.RIGHT), KeysList.giveKeysList(Keys.D)},
			new Keys[]{KeysList.giveKeysList(KeysList.UP), KeysList.giveKeysList(KeysList.W)},
			new Keys[]{KeysList.giveKeysList(KeysList.DOWN), KeysList.giveKeysList(KeysList.S)},
			new Keys[]{KeysList.giveKeysList(KeysList.NUMPAD_4), KeysList.giveKeysList(Keys.Q)},
			new Keys[]{KeysList.giveKeysList(KeysList.NUMPAD_6), KeysList.giveKeysList(Keys.E)},
			new Keys[]{KeysList.giveKeysList(Keys.NUMPAD_0), KeysList.giveKeysList(Keys.SPACE), KeysList.giveKeysList(Keys.ENTER)},
			new Keys[]{KeysList.giveKeysList(Keys.ESC)});
		this.figure = new Structure2D[]
		{
			new Ball(new Point3D(0, 0, -2), 1, true),
		};
		
		this.camera.setActive(false);
		Mouse.setGrabbed(false);
		Mouse.setCursorPosition(/* Toolkit.getDefaultToolkit().getScreenSize().width / 2 */ 0, Display.getHeight() / 2);
		Frame.frame.setVisible(true);
	}
	
	@Override
	protected final void doActions() throws Exception
	{
		if(Visualizer.backgroundColor != null)
		{
			final Color color = Visualizer.backgroundColor;
			GL11.glClearColor(
				Float.valueOf(color.getRed()) / Color.MAX_VALUE_COLOR,
				Float.valueOf(color.getGreen()) / Color.MAX_VALUE_COLOR,
				Float.valueOf(color.getBlue()) / Color.MAX_VALUE_COLOR,
				(float) (1 - Float.valueOf(color.getTransparent()) / Color.MAX_VALUE_TRANSPARENT));
			Visualizer.backgroundColor = null;
		}
		if(Visualizer.figureColor != null)
		{
			for(final Structure2D figure : this.figure)
				figure.setColors(new Color[]{Visualizer.figureColor});
			Visualizer.figureColor = null;
		}
		int key = -1;
		if(this.whell == 0)
			this.whell = Mouse.getDWheel() / 50;
		if(this.whell != 0)
		{
			key = this.whell < 0 ? KeyEvent.VK_MINUS: KeyEvent.VK_EQUALS;
			this.robot.keyPress(KeyEvent.VK_SHIFT);
			this.robot.keyPress(key);
		}
		this.camera.doNavigateGL(this.pilot, 0.05F, 2.0F, 0.05F, 0.25F);
		if(key >= 0)
		{
			this.whell += this.whell < 0 ? 1 : -1;
			if(this.whell == 0)
			{
				this.robot.keyRelease(key);
				this.robot.keyRelease(KeyEvent.VK_SHIFT);
			}
		}
		if(Visualizer.destroy)
			Display.destroy();
		if(Display.isCreated())
			switch(Spherical.choice)
			{
				case 1:
				{
					GL11.glRotatef(ballRotate += 0.1F, 0, 0, 1);
					this.figure[0].doGenerate();
				} break;
			}
	}
	
}
