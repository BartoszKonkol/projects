package net.polishgames.rhenowar.util.captcha;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import net.polishgames.rhenowar.util.RhenowarObject;
import net.polishgames.rhenowar.util.Util;
import net.polishgames.rhenowar.util.serialization.RhenowarSerializable;

public final class Captcha extends RhenowarObject implements Serializable
{
	
	private static final long serialVersionUID = RhenowarSerializable.giveSerialVersion("1.4.3.1");
	
	protected final List<CaptchaSign> signs;
	private final int whitespaces;

	public Captcha(final int whitespaces)
	{
		this.signs = new ArrayList<CaptchaSign>();
		this.whitespaces = whitespaces;
		this.text = "";
	}
	
	public Captcha(final int whitespaces, final String text)
	{
		this(whitespaces);
		for(char sign : Util.nonNull(text).toCharArray())
			this.addSign(sign);
	}

	public Captcha(final String text)
	{
		this(2, text);
	}

	public Captcha()
	{
		this("");
	}
	
	protected String text;
	
	public final List<CaptchaSign> giveSigns()
	{
		return Collections.unmodifiableList(this.signs);
	}
	
	public final int giveWhitespaces()
	{
		return this.whitespaces;
	}
	
	public final String giveText()
	{
		return this.text.toUpperCase();
	}

	@Override
	public Map<String, Object> giveProperties(final Map<String, Object> map)
	{
		map.put("signs", this.giveSigns());
		map.put("whitespaces", this.giveWhitespaces());
		map.put("text", this.text);
		return map;
	}
	
	public final Captcha addSign(final char sign)
	{
		return this.addSign(new CaptchaSign(sign));
	}

	public final Captcha addSign(final CaptchaSign sign)
	{
		this.signs.add(Util.nonNull(sign));
		this.text += sign.giveSign();
		return this;
	}

	@Override
	public final String toString()
	{
		final short height = CaptchaSign.HEIGHT, width = CaptchaSign.WIDTH;
		final String space = " ";
		final String[] array = new String[height];
		for(int i = 0; i < height; i++)
			array[i] = "";
		for(final CaptchaSign sign : this.giveSigns())
			for(int i = 0; i < height; i++)
			{
				final String str = sign.giveSignBig()[i];
				final int length = str.length();
				if(length > width)
					array[i] += str.substring(0, width);
				else
				{
					array[i] += str;
					if(length < width)
						for(int j = 0; j < width - length; j++)
							array[i] += space;
				}
				for(int j = 0; j < this.giveWhitespaces(); j++)
					array[i] += space;
			}
		String result = "";
		for(int i = 0; i < height; i++)
			result += array[i] + "\000";
		return result;
	}

}
