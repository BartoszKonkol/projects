package javax.jao.setup;

import java.awt.AWTException;
import java.awt.Checkbox;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;
import java.security.AccessController;
import javax.JNF;
import javax.imageio.ImageIO;
import javax.jnf.importation.Window;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JProgressBar;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.plaf.basic.BasicLabelUI;
import sun.awt.OSInfo;
import sun.awt.OSInfo.OSType;
import com.sun.java.swing.plaf.windows.WindowsLookAndFeel;

public abstract class Setup extends Window
{
	
	protected static final String TITLE = "Java Application Open", VERSION = "1.1.1", BUTTON_NEXT = "Dalej >", BUTTON_BACK = "< Wstecz", BUTTON_INSTALL = "Instaluj", BUTTON_CANCEL = "Anuluj", BUTTON_FINISH = "Zakończ", BUTTON_YES = "Tak", BUTTON_NO = "Nie", BUTTON_OK = "OK", BUTTON_WAIT = "Czekaj...", BUTTON_EMPTY = "\t";
	
	private final JLabel label3;
	private final JButton button3;
	private final JProgressBar progressbar1;
	private final int screens;

	public Setup(final int screens)
	{
		super(TITLE + " (" + VERSION + ")", null, 600, 300, false, true, false);
		this.setVisible(false);
		if(AccessController.doPrivileged(OSInfo.getOSTypeAction()) != OSType.WINDOWS)
			Main.doError(4);
		this.setTitle(this.getTitle() + " – " + this.getClass().getSimpleName());
		try
		{
			UIManager.setLookAndFeel(new WindowsLookAndFeel());
		}
		catch(final UnsupportedLookAndFeelException e)
		{
			e.printStackTrace();
			JNF.giveWindow().communiqueError("Wystąpił błąd podczas stosowania zasobów Look&Feel:\n    " + e.getMessage() + "\n\nInstalacja będzie kontynuowana, jednakże wygląd okien może mieć gorszą jakość.", TITLE);
		}
		Image icon = null, image = null;
		try
		{
			icon = ImageIO.read(this.getClass().getResource("/icon.png"));
			image = ImageIO.read(this.getClass().getResource("/image.png"));
		}
		catch(final Exception e)
		{
			e.printStackTrace();
		}
		if(icon != null)
			this.setIconImage(icon);
		final JLabel label1 = new JLabel();
		final JLabel label2 = new JLabel();
		this.label3 = new JLabel();
		final JLabel label4 = new JLabel();
		final JButton button1 = new JButton();
		final JButton button2 = new JButton();
		this.button3 = new JButton();
		this.progressbar1 = new JProgressBar();
		final JProgressBar progressbar2 = new JProgressBar();
		final Checkbox checkbox1 = new Checkbox();
		final String fontName = "Microsoft Sans Serif";
		final Font font = new Font(fontName, Font.PLAIN, 11);
		if(image != null)
			label1.setIcon(new ImageIcon(image));
		else
			label1.setUI(new BasicLabelUI()
			{
				@Override
				public void paint(final Graphics graphics, final JComponent component)
				{
					if(component != label1)
						super.paint(graphics, component);
					else
					{
						final JLabel label = (JLabel) component;
						final Insets insets = label.getInsets();
						((Graphics2D) graphics).rotate(Math.PI / -2);
						this.paintEnabledText(label, graphics, this.layoutCL(label, graphics.getFontMetrics(font), "Wystąpił błąd podczas ładowania grafiki.", null, new Rectangle(insets.left, insets.top, label.getHeight() - insets.top + insets.bottom, label.getWidth() - insets.left + insets.right), new Rectangle(), new Rectangle()), 50 - label.getHeight(), 50);
					}
				}
			});
		label2.setFont(new Font(fontName, Font.BOLD, 11));
		this.label3.setFont(new Font(fontName, Font.PLAIN, 10));
		label4.setFont(font);
		label4.setVerticalAlignment(SwingConstants.TOP);
		button1.setFont(font);
		button1.setText(BUTTON_EMPTY);
		button2.setFont(font);
		button2.setText(BUTTON_EMPTY);
		this.button3.setText(BUTTON_EMPTY);
		this.button3.setFont(font);
		this.button3.addMouseListener(new MouseAdapter()
		{
			@Override
			public void mouseClicked(final MouseEvent event)
			{
				if(event.getComponent().isEnabled())
					Setup.this.doClose();
			}
		});
		this.progressbar1.setFont(font);
		progressbar2.setFont(font);
		progressbar2.setStringPainted(true);
		checkbox1.setFont(font);
		checkbox1.setFocusable(false);
		final GroupLayout layout = new GroupLayout(this.getContentPane());
		this.getContentPane().setLayout(layout);
		layout.setHorizontalGroup(
			layout.createParallelGroup(Alignment.LEADING)
			.addGroup(layout.createSequentialGroup()
				.addComponent(label1, GroupLayout.PREFERRED_SIZE, 128, GroupLayout.PREFERRED_SIZE)
				.addGroup(layout.createParallelGroup(Alignment.LEADING)
					.addGroup(layout.createSequentialGroup()
						.addGroup(layout.createParallelGroup(Alignment.TRAILING)
							.addGroup(Alignment.LEADING, layout.createSequentialGroup()
								.addPreferredGap(ComponentPlacement.UNRELATED)
								.addComponent(checkbox1, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
							.addGroup(Alignment.LEADING, layout.createSequentialGroup()
								.addGap(5, 5, 5)
								.addGroup(layout.createParallelGroup(Alignment.LEADING)
									.addGroup(layout.createSequentialGroup()
										.addComponent(this.button3, GroupLayout.PREFERRED_SIZE, 100, GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
										.addComponent(button2, GroupLayout.PREFERRED_SIZE, 100, GroupLayout.PREFERRED_SIZE)
										.addGap(5, 5, 5)
										.addComponent(button1, GroupLayout.PREFERRED_SIZE, 100, GroupLayout.PREFERRED_SIZE))
									.addComponent(this.progressbar1, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
									.addGroup(layout.createSequentialGroup()
										.addGap(25, 25, 25)
										.addComponent(label2, GroupLayout.PREFERRED_SIZE, 350, GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
										.addComponent(this.label3))
									.addComponent(progressbar2, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
						.addGap(10, 10, 10))
					.addGroup(Alignment.TRAILING, layout.createSequentialGroup()
						.addPreferredGap(ComponentPlacement.RELATED, 42, Short.MAX_VALUE)
						.addComponent(label4, GroupLayout.PREFERRED_SIZE, 420, GroupLayout.PREFERRED_SIZE)
						.addContainerGap())))
		);
		layout.setVerticalGroup(
			layout.createParallelGroup(Alignment.LEADING)
			.addComponent(label1, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
			.addGroup(Alignment.TRAILING, layout.createSequentialGroup()
				.addGroup(layout.createParallelGroup(Alignment.LEADING)
					.addGroup(layout.createSequentialGroup()
						.addGap(10, 10, 10)
						.addComponent(label2, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE))
					.addGroup(layout.createSequentialGroup()
						.addContainerGap()
						.addComponent(this.label3, GroupLayout.PREFERRED_SIZE, 15, GroupLayout.PREFERRED_SIZE)))
				.addGap(12, 12, 12)
				.addComponent(label4, GroupLayout.DEFAULT_SIZE, 150, Short.MAX_VALUE)
				.addGap(0, 0, 0)
				.addComponent(checkbox1, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE)
				.addGap(0, 0, 0)
				.addComponent(progressbar2, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE)
				.addGap(5, 5, 5)
				.addComponent(this.progressbar1, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE)
				.addGap(10, 10, 10)
				.addGroup(layout.createParallelGroup(Alignment.BASELINE)
					.addComponent(button1)
					.addComponent(button2)
					.addComponent(this.button3))
				.addGap(10, 10, 10))
		);
		this.pack();
		this.screens = screens <= 0 ? 1 : screens;
		this.doInitialize(label2, label4, button1, button2, progressbar2, checkbox1);
		this.setVisible(true);
		this.doBackScreen();
		while(this.isEnabled())
			try
			{
				Thread.sleep(10);
			}
			catch(final InterruptedException e)
			{
				e.printStackTrace();
				Main.doError(5);
			}
	}
	
	private int screen = 0;
	private boolean confirmEnd;
	
	private final void doScreenImpl()
	{
		this.label3.setText(this.screen + "/" + this.screens);
		this.progressbar1.setValue(100 * this.screen / this.screens);
		this.doScreen(this.screen);
	}
	
	protected final Setup setConfirmEnd(final boolean confirmEnd)
	{
		this.confirmEnd = confirmEnd;
		return this;
	}
	
	protected final boolean getConfirmEnd()
	{
		return this.confirmEnd;
	}
	
	@Override
	public void setDefaultCloseOperation(final int operation)
	{
		if(this.button3 != null)
		{
			if(operation == JFrame.DO_NOTHING_ON_CLOSE)
				this.button3.setEnabled(false);
			else
				this.button3.setEnabled(true);
		}
		super.setDefaultCloseOperation(operation);
	}
	
	@Override
	protected void processWindowEvent(final WindowEvent event)
	{
		if(event.getID() == WindowEvent.WINDOW_CLOSING && this.getDefaultCloseOperation() != JFrame.DO_NOTHING_ON_CLOSE && this.getConfirmEnd())
		{
			JNF.giveWindow().optionsReg("Czy na pewno chcesz zakończyć działanie instalatora?", TITLE);
			JNF.giveWindow().optionsAdd(BUTTON_YES);
			JNF.giveWindow().optionsAdd(BUTTON_NO);
			final int defaultCloseOperation = this.getDefaultCloseOperation();
			if(JNF.giveWindow().optionsRun() != 1)
				this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
			super.processWindowEvent(event);
			this.setDefaultCloseOperation(defaultCloseOperation);
			JNF.giveWindow().optionsRub();
		}
		else
			super.processWindowEvent(event);
	}
	
	protected final void doClose()
	{
		this.dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
	}
	
	protected final void doNextScreen()
	{
		this.screen++;
		if(this.screen > this.screens)
			this.doClose();
		else
		{
			if(this.screen == this.screens)
			{
				this.button3.setText(BUTTON_FINISH);
				this.setConfirmEnd(false);
			}
			this.doScreenImpl();
		}
	}
	
	protected final void doBackScreen()
	{
		if(this.screen > 0)
			this.screen--;
		this.button3.setText(BUTTON_CANCEL);
		this.setConfirmEnd(true);
		if(this.screen <= 0)
			this.doNextScreen();
		else
			this.doScreenImpl();
	}
	
	protected final void doProgress(int percent, final JProgressBar progress)
	{
		if(percent > 100)
			percent = 100;
		else if(percent < 0)
			percent = 0;
		Robot robot = null;
		try
		{
			robot = new Robot();
		}
		catch(final AWTException e)
		{
			e.printStackTrace();
		}
		if(percent > 0)
		{
			final int value = progress.getValue();
			for(int i = 0; i < percent - value; i++)
			{
				progress.setValue(progress.getValue() + 1);
				if(robot != null)
					robot.delay(100);
			}
		}
	}
	
	protected abstract void doInitialize(final JLabel label2, final JLabel label4, final JButton button1, final JButton button2, final JProgressBar progressbar2, final Checkbox checkbox1);
	protected abstract void doScreen(final int screen);
	
}
