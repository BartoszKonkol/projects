package net.polishgames.rhenowar.util.captcha;

import java.io.Serializable;
import java.util.Map;
import net.polishgames.rhenowar.util.RhenowarObject;
import net.polishgames.rhenowar.util.Util;
import net.polishgames.rhenowar.util.serialization.RhenowarSerializable;

public final class CaptchaSign extends RhenowarObject implements Serializable
{
	
	private static final long serialVersionUID = RhenowarSerializable.giveSerialVersion("1.4.3.1");
	
	public static final short HEIGHT = 5, WIDTH = 5;
	
	private final char sign;
	private final String[] signBig;
	
	public CaptchaSign(final char sign)
	{
		this.sign = Util.nonNull(sign);
		this.signBig = new String[CaptchaSign.HEIGHT];
		for(int i = 0; i < this.signBig.length; i++)
			this.signBig[i] = "";
		switch(this.sign)
		{
			case 'a':
			case 'A':
				this.signBig[0] = " ###";
				this.signBig[1] = "#   #";
				this.signBig[2] = "#####";
				this.signBig[3] = "#   #";
				this.signBig[4] = "#   #";
				break;
			case 'b':
			case 'B':
				this.signBig[0] = "####";
				this.signBig[1] = "#   #";
				this.signBig[2] = "####";
				this.signBig[3] = "#   #";
				this.signBig[4] = "####";
				break;
			case 'c':
			case 'C':
				this.signBig[0] = " ###";
				this.signBig[1] = "#";
				this.signBig[2] = "#";
				this.signBig[3] = "#";
				this.signBig[4] = " ###";
				break;
			case 'd':
			case 'D':
				this.signBig[0] = "####";
				this.signBig[1] = "#   #";
				this.signBig[2] = "#   #";
				this.signBig[3] = "#   #";
				this.signBig[4] = "####";
				break;
			case 'e':
			case 'E':
				this.signBig[0] = "#####";
				this.signBig[1] = "#";
				this.signBig[2] = "###";
				this.signBig[3] = "#";
				this.signBig[4] = "#####";
				break;
			case 'f':
			case 'F':
				this.signBig[0] = "#####";
				this.signBig[1] = "#";
				this.signBig[2] = "###";
				this.signBig[3] = "#";
				this.signBig[4] = "#";
				break;
			case 'g':
			case 'G':
				this.signBig[0] = " ####";
				this.signBig[1] = "#";
				this.signBig[2] = "#  ##";
				this.signBig[3] = "#   #";
				this.signBig[4] = " ###";
				break;
			case 'h':
			case 'H':
				this.signBig[0] = "#   #";
				this.signBig[1] = "#   #";
				this.signBig[2] = "#####";
				this.signBig[3] = "#   #";
				this.signBig[4] = "#   #";
				break;
			case 'i':
			case 'I':
				this.signBig[0] = "#####";
				this.signBig[1] = "  #";
				this.signBig[2] = "  #";
				this.signBig[3] = "  #";
				this.signBig[4] = "#####";
				break;
			case 'j':
			case 'J':
				this.signBig[0] = "#####";
				this.signBig[1] = "    #";
				this.signBig[2] = "    #";
				this.signBig[3] = "    #";
				this.signBig[4] = " ###";
				break;
			case 'k':
			case 'K':
				this.signBig[0] = "#   #";
				this.signBig[1] = "#  #";
				this.signBig[2] = "##";
				this.signBig[3] = "# #";
				this.signBig[4] = "#  #";
				break;
			case 'l':
			case 'L':
				this.signBig[0] = "#";
				this.signBig[1] = "#";
				this.signBig[2] = "#";
				this.signBig[3] = "#";
				this.signBig[4] = "#####";
				break;
			case 'm':
			case 'M':
				this.signBig[0] = "#   #";
				this.signBig[1] = "## ##";
				this.signBig[2] = "# # #";
				this.signBig[3] = "#   #";
				this.signBig[4] = "#   #";
				break;
			case 'n':
			case 'N':
				this.signBig[0] = "#   #";
				this.signBig[1] = "##  #";
				this.signBig[2] = "# # #";
				this.signBig[3] = "#  ##";
				this.signBig[4] = "#   #";
				break;
			case 'o':
			case 'O':
				this.signBig[0] = " ###";
				this.signBig[1] = "#   #";
				this.signBig[2] = "#   #";
				this.signBig[3] = "#   #";
				this.signBig[4] = " ###";
				break;
			case 'p':
			case 'P':
				this.signBig[0] = "####";
				this.signBig[1] = "#   #";
				this.signBig[2] = "####";
				this.signBig[3] = "#";
				this.signBig[4] = "#";
				break;
			case 'q':
			case 'Q':
				this.signBig[0] = " ###";
				this.signBig[1] = "#   #";
				this.signBig[2] = "# # #";
				this.signBig[3] = "#  ##";
				this.signBig[4] = " ###";
				break;
			case 'r':
			case 'R':
				this.signBig[0] = "####";
				this.signBig[1] = "#   #";
				this.signBig[2] = "####";
				this.signBig[3] = "# #";
				this.signBig[4] = "#  #";
				break;
			case 's':
			case 'S':
				this.signBig[0] = " ###";
				this.signBig[1] = "#";
				this.signBig[2] = " ###";
				this.signBig[3] = "    #";
				this.signBig[4] = " ###";
				break;
			case 't':
			case 'T':
				this.signBig[0] = "#####";
				this.signBig[1] = "  #";
				this.signBig[2] = "  #";
				this.signBig[3] = "  #";
				this.signBig[4] = "  #";
				break;
			case 'u':
			case 'U':
				this.signBig[0] = "#   #";
				this.signBig[1] = "#   #";
				this.signBig[2] = "#   #";
				this.signBig[3] = "#   #";
				this.signBig[4] = " ###";
				break;
			case 'v':
			case 'V':
				this.signBig[0] = "#   #";
				this.signBig[1] = "#   #";
				this.signBig[2] = " # #";
				this.signBig[3] = " # #";
				this.signBig[4] = "  #";
				break;
			case 'w':
			case 'W':
				this.signBig[0] = "#   #";
				this.signBig[1] = "#   #";
				this.signBig[2] = "# # #";
				this.signBig[3] = "## ##";
				this.signBig[4] = "#   #";
				break;
			case 'x':
			case 'X':
				this.signBig[0] = "#   #";
				this.signBig[1] = " # #";
				this.signBig[2] = "  #";
				this.signBig[3] = " # #";
				this.signBig[4] = "#   #";
				break;
			case 'y':
			case 'Y':
				this.signBig[0] = "#   #";
				this.signBig[1] = " # #";
				this.signBig[2] = "  #";
				this.signBig[3] = "  #";
				this.signBig[4] = "  #";
				break;
			case 'z':
			case 'Z':
				this.signBig[0] = "#####";
				this.signBig[1] = "   #";
				this.signBig[2] = "  #";
				this.signBig[3] = " #";
				this.signBig[4] = "#####";
				break;
			case '0':
				this.signBig[0] = " ###";
				this.signBig[1] = "##  #";
				this.signBig[2] = "# # #";
				this.signBig[3] = "#  ##";
				this.signBig[4] = " ###";
				break;
			case '1':
				this.signBig[0] = "  #";
				this.signBig[1] = " ##";
				this.signBig[2] = "# #";
				this.signBig[3] = "  #";
				this.signBig[4] = "#####";
				break;
			case '2':
				this.signBig[0] = " ###";
				this.signBig[1] = "#  #";
				this.signBig[2] = "  #";
				this.signBig[3] = " #";
				this.signBig[4] = "#####";
				break;
			case '3':
				this.signBig[0] = "####";
				this.signBig[1] = "    #";
				this.signBig[2] = "  ###";
				this.signBig[3] = "    #";
				this.signBig[4] = "####";
				break;
			case '4':
				this.signBig[0] = "   #";
				this.signBig[1] = "  ##";
				this.signBig[2] = " # #";
				this.signBig[3] = "#####";
				this.signBig[4] = "   #";
				break;
			case '5':
				this.signBig[0] = "#####";
				this.signBig[1] = "#";
				this.signBig[2] = "####";
				this.signBig[3] = "    #";
				this.signBig[4] = "####";
				break;
			case '6':
				this.signBig[0] = " ###";
				this.signBig[1] = "#";
				this.signBig[2] = "####";
				this.signBig[3] = "#   #";
				this.signBig[4] = " ###";
				break;
			case '7':
				this.signBig[0] = "#####";
				this.signBig[1] = "   #";
				this.signBig[2] = " ###";
				this.signBig[3] = " #";
				this.signBig[4] = "#";
				break;
			case '8':
				this.signBig[0] = " ###";
				this.signBig[1] = "#   #";
				this.signBig[2] = " ###";
				this.signBig[3] = "#   #";
				this.signBig[4] = " ###";
				break;
			case '9':
				this.signBig[0] = " ###";
				this.signBig[1] = "#   #";
				this.signBig[2] = " ####";
				this.signBig[3] = "    #";
				this.signBig[4] = " ###";
				break;
			case ' ':
				break;
			case ':':
				this.signBig[1] = "  #";
				this.signBig[3] = "  #";
				break;
			case ';':
				this.signBig[1] = "  #";
				this.signBig[3] = "  #";
				this.signBig[4] = " #";
				break;
			case '`':
				this.signBig[0] = "##";
				this.signBig[1] = "  #";
				break;
			case '~':
				this.signBig[1] = " #";
				this.signBig[2] = "# # #";
				this.signBig[3] = "   #";
				break;
			case '!':
				this.signBig[0] = "  #";
				this.signBig[1] = "  #";
				this.signBig[2] = "  #";
				this.signBig[4] = "  #";
				break;
			case '@':
				this.signBig[0] = " ###";
				this.signBig[1] = "# # #";
				this.signBig[2] = "## ##";
				this.signBig[3] = "# ###";
				this.signBig[4] = " ##";
				break;
			case '#':
				this.signBig[0] = " # #";
				this.signBig[1] = "#####";
				this.signBig[2] = " # #";
				this.signBig[3] = "#####";
				this.signBig[4] = " # #";
				break;
			case '$':
				this.signBig[0] = " ###";
				this.signBig[1] = "# #";
				this.signBig[2] = " ###";
				this.signBig[3] = "  # #";
				this.signBig[4] = " ###";
				break;
			case '%':
				this.signBig[0] = " #  #";
				this.signBig[1] = "   #";
				this.signBig[2] = "  #";
				this.signBig[3] = " #";
				this.signBig[4] = "#  #";
				break;
			case '^':
				this.signBig[0] = "  #";
				this.signBig[1] = " # #";
				break;
			case '&':
				this.signBig[0] = " ##";
				this.signBig[1] = "#  #";
				this.signBig[2] = " ## #";
				this.signBig[3] = "#  #";
				this.signBig[4] = "### #";
				break;
			case '*':
				this.signBig[1] = " ###";
				this.signBig[2] = "#####";
				this.signBig[3] = " ###";
				break;
			case '(':
				this.signBig[0] = "  #";
				this.signBig[1] = " #";
				this.signBig[2] = " #";
				this.signBig[3] = " #";
				this.signBig[4] = "  #";
				break;
			case ')':
				this.signBig[0] = "  #";
				this.signBig[1] = "   #";
				this.signBig[2] = "   #";
				this.signBig[3] = "   #";
				this.signBig[4] = "  #";
				break;
			case '-':
				this.signBig[2] = " ###";
				break;
			case '_':
				this.signBig[4] = "#####";
				break;
			case '=':
				this.signBig[1] = " ###";
				this.signBig[3] = " ###";
				break;
			case '+':
				this.signBig[1] = "  #";
				this.signBig[2] = " ###";
				this.signBig[3] = "  #";
				break;
			case '[':
				this.signBig[0] = " ##";
				this.signBig[1] = " #";
				this.signBig[2] = " #";
				this.signBig[3] = " #";
				this.signBig[4] = " ##";
				break;
			case ']':
				this.signBig[0] = "  ##";
				this.signBig[1] = "   #";
				this.signBig[2] = "   #";
				this.signBig[3] = "   #";
				this.signBig[4] = "  ##";
				break;
			case '{':
				this.signBig[0] = "  ##";
				this.signBig[1] = "  #";
				this.signBig[2] = " #";
				this.signBig[3] = "  #";
				this.signBig[4] = "  ##";
				break;
			case '}':
				this.signBig[0] = " ##";
				this.signBig[1] = "  #";
				this.signBig[2] = "   #";
				this.signBig[3] = "  #";
				this.signBig[4] = " ##";
				break;
			case '\'':
				this.signBig[0] = "  #";
				this.signBig[1] = "  #";
				break;
			case '"':
				this.signBig[0] = " # #";
				this.signBig[1] = " # #";
				break;
			case ',':
				this.signBig[3] = "  #";
				this.signBig[4] = " #";
				break;
			case '.':
				this.signBig[4] = "  #";
				break;
			case '<':
				this.signBig[1] = "  ###";
				this.signBig[2] = "##";
				this.signBig[3] = "  ###";
				break;
			case '>':
				this.signBig[1] = "###";
				this.signBig[2] = "   ##";
				this.signBig[3] = "###";
				break;
			case '/':
				this.signBig[0] = "   #";
				this.signBig[1] = "   #";
				this.signBig[2] = "  #";
				this.signBig[3] = " #";
				this.signBig[4] = " #";
				break;
			case '?':
				this.signBig[0] = "  ##";
				this.signBig[1] = "    #";
				this.signBig[2] = "   #";
				this.signBig[3] = "  #";
				this.signBig[4] = "  #";
				break;
			case '|':
				this.signBig[0] = "  #";
				this.signBig[1] = "  #";
				this.signBig[2] = "  #";
				this.signBig[3] = "  #";
				this.signBig[4] = "  #";
				break;
			case '\\':
				this.signBig[0] = " #";
				this.signBig[1] = " #";
				this.signBig[2] = "  #";
				this.signBig[3] = "   #";
				this.signBig[4] = "   #";
				break;
			default:
				this.signBig[0] = "#####";
				this.signBig[1] = "#####";
				this.signBig[2] = "#####";
				this.signBig[3] = "#####";
				this.signBig[4] = "#####";
				break;
		}
	}

	public final char giveSign()
	{
		return this.sign;
	}

	public final String[] giveSignBig()
	{
		return this.signBig;
	}

	@Override
	public Map<String, Object> giveProperties(final Map<String, Object> map)
	{
		map.put("sign", this.giveSign());
		map.put("signBig", this.giveSignBig());
		return map;
	}
}
