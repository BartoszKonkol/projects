package net.polishgames.konkol.pszemeksoft.spherical;

import java.awt.Canvas;
import java.awt.Component;
//import java.awt.Toolkit;
import java.awt.event.WindowEvent;
import javax.jnf.importation.Window;
import javax.swing.GroupLayout;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.GroupLayout.Alignment;

public final class Frame extends Window
{
	
	private static final long serialVersionUID = 0L;
	
	public static Component frame;
	
	public final JComboBox<String>[] combobox;
	public final JLabel[] label;
	public final JSeparator[] separator;
	public final JTextField[] textfield;
	public final Canvas[] canvas;

	public Frame(final String title)
	{
		super(title, null, 0, 0, false, false, false);
		
		this.setVisible(false);
		
		this.combobox = new JComboBox[1];
		this.label = new JLabel[10];
		this.separator = new JSeparator[1];
		this.textfield = new JTextField[5];
		this.canvas = new Canvas[1];
		
		for(int i = 0; i < this.combobox.length; i++)
			this.combobox[i] = new JComboBox<String>();
		for(int i = 0; i < this.label.length; i++)
			this.label[i] = new JLabel();
		for(int i = 0; i < this.separator.length; i++)
			this.separator[i] = new JSeparator();
		for(int i = 0; i < this.textfield.length; i++)
			this.textfield[i] = new JTextField();
		for(int i = 0; i < this.canvas.length; i++)
			this.canvas[i] = new Canvas();
		
		final GroupLayout layout = new GroupLayout(this.getContentPane());
		this.getContentPane().setLayout(layout);
		layout.setHorizontalGroup(
			layout.createParallelGroup(Alignment.LEADING)
			.addGroup(layout.createSequentialGroup()
				.addGroup(layout.createParallelGroup(Alignment.LEADING)
					.addComponent(this.separator[0])
					.addGroup(layout.createSequentialGroup()
						.addGap(10, 10, 10)
						.addGroup(layout.createParallelGroup(Alignment.LEADING)
							.addComponent(this.label[0], GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
							.addComponent(this.label[1], GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
					.addGroup(layout.createSequentialGroup()
						.addGap(10, 10, 10)
						.addComponent(this.label[2], GroupLayout.PREFERRED_SIZE, 200, GroupLayout.PREFERRED_SIZE)
						.addGap(10, 10, 10)
						.addComponent(this.combobox[0], 0, 270, Short.MAX_VALUE))
					.addGroup(layout.createSequentialGroup()
						.addGap(10, 10, 10)
						.addComponent(this.label[3], GroupLayout.PREFERRED_SIZE, 150, GroupLayout.PREFERRED_SIZE)
						.addGap(10, 10, 10)
						.addComponent(this.textfield[0], GroupLayout.PREFERRED_SIZE, 1, Short.MAX_VALUE))
					.addGroup(layout.createSequentialGroup()
						.addGap(10, 10, 10)
						.addGroup(layout.createParallelGroup(Alignment.TRAILING)
							.addComponent(this.label[4], GroupLayout.PREFERRED_SIZE, 150, GroupLayout.PREFERRED_SIZE)
							.addComponent(this.label[5])
							.addComponent(this.label[6])
							.addComponent(this.label[7]))
						.addGap(10, 10, 10)
						.addGroup(layout.createParallelGroup(Alignment.LEADING)
							.addComponent(this.textfield[1], GroupLayout.PREFERRED_SIZE, 1, Short.MAX_VALUE)
							.addComponent(this.textfield[2], GroupLayout.PREFERRED_SIZE, 1, Short.MAX_VALUE)
							.addComponent(this.textfield[3], GroupLayout.PREFERRED_SIZE, 1, Short.MAX_VALUE)
							.addComponent(this.textfield[4], GroupLayout.PREFERRED_SIZE, 1, Short.MAX_VALUE)))
					.addGroup(Alignment.TRAILING, layout.createSequentialGroup()
						.addGap(10, 10, 10)
						.addGroup(layout.createParallelGroup(Alignment.LEADING)
							.addComponent(this.label[9], GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
							.addComponent(this.label[8], GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
				.addGap(10, 10, 10)
				.addComponent(this.canvas[0], GroupLayout.PREFERRED_SIZE, 700, GroupLayout.PREFERRED_SIZE))
		);
		layout.setVerticalGroup(
			layout.createParallelGroup(Alignment.LEADING)
			.addGroup(layout.createSequentialGroup()
				.addGap(10, 10, 10)
				.addComponent(this.label[0], GroupLayout.PREFERRED_SIZE, 120, GroupLayout.PREFERRED_SIZE)
				.addGap(10, 10, 10)
				.addComponent(this.label[1])
				.addGap(10, 10, 10)
				.addGroup(layout.createParallelGroup(Alignment.BASELINE)
					.addComponent(this.label[2])
					.addComponent(this.combobox[0], GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
				.addGap(10, 10, 10)
				.addGroup(layout.createParallelGroup(Alignment.BASELINE)
					.addComponent(this.label[3])
					.addComponent(this.textfield[0], GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
				.addGap(5, 5, 5)
				.addGroup(layout.createParallelGroup(Alignment.BASELINE)
					.addComponent(this.label[4])
					.addComponent(this.textfield[1], GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
				.addGap(5, 5, 5)
				.addGroup(layout.createParallelGroup(Alignment.BASELINE)
					.addComponent(this.label[5])
					.addComponent(this.textfield[2], GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
				.addGap(5, 5, 5)
				.addGroup(layout.createParallelGroup(Alignment.BASELINE)
					.addComponent(this.label[6])
					.addComponent(this.textfield[3], GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
				.addGap(5, 5, 5)
				.addGroup(layout.createParallelGroup(Alignment.BASELINE)
					.addComponent(this.label[7])
					.addComponent(this.textfield[4], GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
				.addGap(10, 10, 10)
				.addComponent(this.separator[0], GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
				.addGap(10, 10, 10)
				.addComponent(this.label[8], GroupLayout.PREFERRED_SIZE, 316, GroupLayout.PREFERRED_SIZE)
				.addGap(10, 10, 10)
				.addComponent(this.label[9], GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
			.addComponent(this.canvas[0], GroupLayout.DEFAULT_SIZE, 700, Short.MAX_VALUE)
		);
		
		this.pack();
		this.doAdjustLocation();
//		this.setLocation(Toolkit.getDefaultToolkit().getScreenSize().width - this.getSize().width - 100, (Toolkit.getDefaultToolkit().getScreenSize().height - this.getSize().height) / 2);

		Visualizer.parent = this.canvas[0];
		Frame.frame = this;
	}
	
	@Override
	protected final void processWindowEvent(final WindowEvent event)
	{
		if(event.getID() == 201)
			Visualizer.destroy = true;
		else
			super.processWindowEvent(event);
	}
	
}
