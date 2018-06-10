package javax.jao.setup;

import java.awt.Checkbox;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import javax.JNF;
import javax.jao.Util;
import javax.jao.WinReg;
import javax.jao.registry.KeyParentJAO;
import javax.jao.winapi.winreg.HKey;
import javax.jao.winapi.winreg.ValueOver;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JProgressBar;

public final class Install extends Setup
{

	public Install()
	{
		super(5);
	}
	
	private JLabel label2 = null;
	private JLabel label4 = null;
	private JButton button1 = null;
	private JButton button2 = null;
	private JProgressBar progressbar2 = null;
	private Checkbox checkbox1 = null;
	private ItemEvent checkbox1ItemEvent = null;

	@Override
	protected final void doInitialize(final JLabel label2, final JLabel label4, final JButton button1, final JButton button2, final JProgressBar progressbar2, final Checkbox checkbox1)
	{
		this.label2 = label2;
		this.label4 = label4;
		this.button1 = button1;
		this.button2 = button2;
		this.progressbar2 = progressbar2;
		this.checkbox1 = checkbox1;
		this.button1.addMouseListener(new MouseAdapter()
		{
			@Override
			public void mouseClicked(final MouseEvent event)
			{
				if(event.getComponent().isEnabled())
					Install.this.doNextScreen();
			}
		});
		this.button2.addMouseListener(new MouseAdapter()
		{
			@Override
			public void mouseClicked(final MouseEvent event)
			{
				if(event.getComponent().isEnabled())
					Install.this.doBackScreen();
			}
		});
		this.progressbar2.setVisible(false);
		this.checkbox1.setVisible(false);
		this.checkbox1.addItemListener(new ItemListener()
		{
			@Override
			public void itemStateChanged(final ItemEvent event)
			{
				switch(event.getStateChange())
				{
					case ItemEvent.SELECTED:
						Install.this.button1.setEnabled(true);
						break;
					case ItemEvent.DESELECTED:
						Install.this.button1.setEnabled(false);
						break;
				}
				Install.this.checkbox1ItemEvent = event;
			}
		});
	}

	@Override
	protected final void doScreen(final int screen)
	{
		switch(screen)
		{
			case 1:
			{
				this.label2.setText("1.");
				this.label4.setText("2.");
				this.button1.setText(BUTTON_NEXT);
				this.button1.setEnabled(true);
				this.button2.setText(BUTTON_BACK);
				this.button2.setEnabled(false);
				this.checkbox1.setVisible(false);
			} break;
			case 2:
			{
				this.label2.setText("3.");
				this.label4.setText("4.");
				this.button1.setText(BUTTON_NEXT);
				this.button1.setEnabled(this.checkbox1ItemEvent != null && this.checkbox1ItemEvent.getStateChange() == ItemEvent.SELECTED);
				this.button2.setEnabled(true);
				this.checkbox1.setLabel("X");
				this.checkbox1.setVisible(true);
			} break;
			case 3:
			{
				this.label2.setText("5.");
				this.label4.setText("6.");
				this.button1.setText(BUTTON_INSTALL);
				this.button1.setEnabled(true);
				this.button2.setVisible(true);
				this.progressbar2.setVisible(false);
				this.checkbox1.setVisible(false);
				this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			} break;
			case 4:
			{
				this.label2.setText("7.");
				this.label4.setText("8.");
				this.button1.setText(BUTTON_WAIT);
				this.button1.setEnabled(false);
				this.button2.setVisible(false);
				this.progressbar2.setVisible(true);
				this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
				new Thread(new Runnable()
				{
					@Override
					public void run()
					{
						Install.this.doInstall(Install.this.progressbar2);
						Install.this.doNextScreen();
					}
				}).start();
			} break;
			case 5:
			{
				this.label2.setText("9.");
				this.label4.setText("0.");
				this.button1.setText(BUTTON_EMPTY);
				this.button1.setVisible(false);
				this.progressbar2.setVisible(false);
				this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			} break;
		}
	}
	
	protected final void doInstall(final JProgressBar progress)
	{
		this.doProgress(10, progress);
		final WinReg reg = WinReg.giveWinReg();
		final ValueOver valueAppDir = new ValueOver(Util.giveDefaultWinRegKeyJAO(), "AppDir");
		final File directory = reg.giveEmptyValue(valueAppDir) ? null : new File(reg.getValue(valueAppDir));
		Util.doRegistryValueNull(directory == null || !directory.isDirectory(), valueAppDir);
		this.doProgress(20, progress);
		final ValueOver valueRegDir = new ValueOver(Util.giveDefaultWinRegKeyJAO(), "RegDir");
		if(reg.giveEmptyValue(valueRegDir))
			reg.setValue(valueRegDir, directory.getAbsolutePath());
		this.doProgress(30, progress);
		final KeyParentJAO[] values = KeyParentJAO.values();
		final float step1 = 10F / values.length;
		for(final KeyParentJAO parent : values)
		{
			final ValueOver value = new ValueOver(Util.giveDefaultWinRegKeyJAO(), "RegFile" + parent.giveNameForm());
			if(reg.giveEmptyValue(value))
				reg.setValue(value, new File(directory, "reg" + parent.giveName() + ".xml").getAbsolutePath());
			this.doProgress(Math.round(progress.getValue() + step1), progress);
			final File file = new File(reg.getValue(value));
			if(!file.isFile())
				JNF.giveFiles().saveText(file, "<!DOCTYPE properties SYSTEM \"http://java.sun.com/dtd/properties.dtd\">", "<properties/>");
			this.doProgress(Math.round(progress.getValue() + step1), progress);
		}
		this.doProgress(50, progress);
		//filename extension
		final String[] extensions = new String[]{"jao"};
		final float step2 = 25F / (extensions.length + 1);
		reg.setValueOld(HKey.CLASSES_ROOT, "." + "jaoreg", reg.getValueOld(HKey.CLASSES_ROOT, ".xml", ""));
		this.doProgress(Math.round(progress.getValue() + step2), progress);
		for(final String extension : extensions)
			this.doRegistrationFilenameExtension(extension, 1, Math.round(progress.getValue() + step2), reg, progress);
		this.doProgress(80, progress);
	}
	
	protected final void doRegistrationFilenameExtension(final String extension, final int number, final int percent, final WinReg reg, final JProgressBar progress)
	{
		final String key = String.format("PGS.JAO.%sfile.%d", extension, number);
		reg.setValueOld(HKey.CLASSES_ROOT, "." + extension, key);
		reg.setValueOld(HKey.CLASSES_ROOT, key, extension.toUpperCase() + " File");
		reg.setValueOld(HKey.CLASSES_ROOT, key + "\\shell\\open\\command", "%1 %*");
		reg.setValueOld(HKey.CLASSES_ROOT, key + "\\DefaultIcon", "%1");
		this.doProgress(percent, progress);
	}
	
}
