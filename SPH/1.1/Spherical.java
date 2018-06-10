package net.polishgames.konkol.pszemeksoft.spherical;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import javax.jnf.lwjgl.Color;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public final class Spherical implements ActionListener
{
	
	public static int choice;
	
	protected final Frame frame;
	protected final Visualizer visualizer;
	protected final DecimalFormat formatter;
	
	protected Spherical()
	{
		final String title = "Spherical";
		this.frame = new Frame(title);
		this.formatter = new DecimalFormat("###,###.##");
		for(int i = 2; i < 8; i++)
			this.frame.label[i].setHorizontalAlignment(SwingConstants.RIGHT);
		this.frame.label[8].setVerticalAlignment(SwingConstants.TOP);
		for(int i = 0; i < this.frame.textfield.length - 2; i++)
			this.frame.textfield[i].setEnabled(false);
		this.frame.textfield[this.frame.textfield.length - 2].setText("#FFFFFF");
		this.frame.textfield[this.frame.textfield.length - 1].setText("#000000");
		this.frame.label[2].setText("Figura:");
		this.frame.label[6].setText("Kolor figury:");
		this.frame.label[7].setText("Kolor t³a:");
		this.frame.combobox[0].setModel(new DefaultComboBoxModel<String>(new String[]{null, "Kula"}));
		this.frame.combobox[0].addActionListener(this);
		for(final JTextField textfield : this.frame.textfield)
			textfield.addActionListener(this);
		this.visualizer = new Visualizer(title);
	}

	@Override
	public final void actionPerformed(final ActionEvent event)
	{
		final Object source = event.getSource();
		if(event.getActionCommand().equals("comboBoxChanged") && source == this.frame.combobox[0])
		{
			for(int i = 0; i < this.frame.textfield.length - 2; i++)
			{
				final JTextField textfield = this.frame.textfield[i];
				textfield.setText(null);
				textfield.setEnabled(false);
			}
			for(int i = 3; i < 6; i++)
				this.frame.label[i].setText(null);
			this.frame.label[8].setText(null);
			switch(this.frame.combobox[0].getSelectedIndex())
			{
				case 1:
				{
					this.frame.label[3].setText("Promieñ:");
					this.frame.textfield[0].setEnabled(true);
				} break;
			}
			this.refresh();
		}
		else if(source instanceof JTextField)
		{
			int id = 0;
			if(source == this.frame.textfield[this.frame.textfield.length - 1])
				id = 1;
			else if(source == this.frame.textfield[this.frame.textfield.length - 2])
				id = 2;
			else
				this.refresh();
			if(id > 0)
			{
				boolean error = true;
				final String text = this.frame.textfield[this.frame.textfield.length - id].getText();
				if(text.startsWith("#") && text.length() == 7)
				{
					try
					{
						final int
							r = Integer.parseInt(text.substring(1, 3), 16),
							g = Integer.parseInt(text.substring(3, 5), 16),
							b = Integer.parseInt(text.substring(5, 7), 16);
						final Color color = new Color(r, g, b);
						switch(id)
						{
							case 1:
							{
								Visualizer.backgroundColor = color;
								error = false;
							} break;
							case 2:
							{
								Visualizer.figureColor = color;
								error = false;
							} break;
						}
					}
					catch(final NumberFormatException e) {}
				}
				if(error)
				{
					this.frame.textfield[this.frame.textfield.length - id].setText(id == 1 ? "#000000" : id == 2 ? "#FFFFFF" : null);
					switch(id)
					{
						case 1:
							Visualizer.backgroundColor = Color.BLACK;
							break;
						case 2:
							Visualizer.figureColor = Color.WHITE;
							break;
					}
				}
			}
		}
	}
	
	public final void refresh()
	{
		final int index = this.frame.combobox[0].getSelectedIndex();
		boolean success = false;
		switch(index)
		{
			case 1:
			{
				final String text = this.frame.textfield[0].getText();
				Double number = null;
				if(text != null && !text.isEmpty())
					try
					{
						number = Double.valueOf(text.replace(',', '.'));
					}
					catch(final NumberFormatException e) {}
				success = number != null;
				this.frame.label[8].setText(success ? "<html><body>Dana jest trójwymiarowa kula o promieniu " + this.formatter.format(number) + "<br><ul><li>Objêtoœæ: " + this.formatter.format(Math.PI*4/3*Math.pow(number, 3)) + "</li><li>Pole powierzchni: " + this.formatter.format(4*Math.PI*Math.pow(number, 2)) + "</li></ul></body></html>" : null);
			} break;
		}
		Spherical.choice = success ? index : 0;
	}
	
	public static final void main(final String[] args)
	{
		new Spherical();
	}
	
}
