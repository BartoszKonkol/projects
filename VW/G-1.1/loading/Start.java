package vw.loading;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.StringReader;
import java.io.StringWriter;
import java.lang.reflect.InvocationTargetException;
import java.net.Proxy;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import javax.JNF;
import javax.jnf.exception.Defect;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import com.mojang.authlib.yggdrasil.YggdrasilAuthenticationService;
import net.minecraft.client.Minecraft;
import net.minecraft.client.main.Main;
import net.minecraft.launchwrapper.Launch;
import net.minecraftforge.common.MinecraftForge;
import vw.loading.certificates.CertificateAuthenticity;
import vw.util.Cocreators;
import vw.util.DataTexts;
import joptsimple.OptionParser;
import joptsimple.OptionSpec;

public final class Start
{
	
	private final File location;
	private final PrintStream streamOut;
	private final PrintStream streamErr;
	private final PrintStream streamOutOriginal;
	private final PrintStream streamErrOriginal;
	private final StringWriter writerLogsConsole;
	
	private Start(String[] args1String)
	{
		
		final OptionParser parser = new OptionParser();
		parser.allowsUnrecognizedOptions();
		final OptionSpec<File> gameDirOption = parser.accepts("gameDir").withRequiredArg().ofType(File.class).defaultsTo(new File("."));
		this.location = new File(parser.parse(args1String).valueOf(gameDirOption), "vw");
		
		this.doCreateCatalogs();
		
		new Cocreators(this.location);
		
		try
		{
			
			this.doDownloadIcon();
			
		}
		catch (final IOException e)
		{
			
			e.printStackTrace();
			
		}
		
		this.streamOutOriginal = System.out;
		this.streamErrOriginal = System.err;
		this.writerLogsConsole = new StringWriter();
		
//		System.setOut(this.doCreateStream(this.streamOutOriginal));
//		System.setErr(this.doCreateStream(this.streamErrOriginal));
		
		this.streamOut = System.out;
		this.streamErr = System.err;
		
		this.doLog(DataTexts.versionVW);
		this.doLog(DataTexts.copyrightVW);
		this.doLog(DataTexts.authorsVW);
		
		JNF.giveWindow().progressRegister(DataTexts.versionVW, new File(this.location, "icon.png"), "~~~~~	~~~~~	\n\n\n");
		String text = DataTexts.versionVW + "\n\u0141adowanie element\u00f3w modyfikacji " + DataTexts.nameVW + "...\n";
		JNF.giveWindow().progressHeadline(text + '\n');
		this.doLog(text.replace(DataTexts.versionVW, "").replace("\n", ""));
		JNF.giveWindow().progressIncrease(5.00D);
		
		JNF.giveWindow().progressHeadline(text + "Trwa pobieranie i sprawdzanie Certyfikatu Autentyczno\u015bci modyfikacji " + DataTexts.nameVW + "...");
		this.doLog("Trwa pobieranie i sprawdzanie Certyfikatu Autentyczno\u015bci modyfikacji " + DataTexts.nameVW + "...");
		this.doLoadCertificate();
		this.doLog("Uko\u0144czono pobieranie i sprawdzanie Certyfikatu Autentyczno\u015bci modyfikacji " + DataTexts.nameVW + ".");
		JNF.giveWindow().progressHeadline(text + '\n');
		JNF.giveWindow().progressIncrease(30.00D);

		JNF.giveWindow().progressHeadline(text + "Trwa sprawdzanie zgodno\u015bci wersji oprogramowania Java oraz gry " + DataTexts.nameMC + "...");
		this.doLog("Trwa sprawdzanie zgodno\u015bci wersji oprogramowania Java oraz gry " + DataTexts.nameMC + "...");
		this.doCheckCompliance();
		this.doLog("Uko\u0144czono sprawdzanie zgodno\u015bci wersji oprogramowania Java" + " oraz gry " + DataTexts.nameMC + "" + ".");
		JNF.giveWindow().progressHeadline(text + '\n');
		JNF.giveWindow().progressIncrease(30.00D);
		
		JNF.giveWindow().progressHeadline(text + "Trwa generowanie wymaganych plik\u00f3w...");
		this.doLog("Trwa generowanie wymaganych plik\u00f3w...");
		this.doGenerateFiles();
		this.doLog("Uko\u0144czono generowanie wymaganych plik\u00f3w.");
		JNF.giveWindow().progressHeadline(text + '\n');
		JNF.giveWindow().progressIncrease(30.00D);
		
		JNF.giveWindow().progressHeadline(text + "Trwa uruchamianie gry " + DataTexts.nameMC + " (z modyfikacj\u0105 " + DataTexts.nameVW + ")...");
		this.doLog("Trwa uruchamianie gry " + DataTexts.nameMC + " (z modyfikacj\u0105 " + DataTexts.nameVW + ")...");
		JNF.giveWindow().progressChange(100.00D);
		if(JNF.giveWindow().progressActual() == 100.00D)
			JNF.giveWindow().progressClose();
		
		if(!JNF.giveWindow().progressWorks())
		{
			
			if(!Boolean.valueOf(DataTexts.publicVW))
			{
				
				String username = null;
				for(int i = 0; i < args1String.length && username == null; i++)
					if(args1String[i] != null && args1String[i].equalsIgnoreCase("--username") && username == null)
						for(int j = 0; j < Cocreators.nicksUnofficial.size() && username == null; j++)
							if(args1String[i + 1] != null && args1String[i + 1].equals(Cocreators.nicksUnofficial.get(j)) && username == null)
								username = args1String[i + 1];
				if(username == null)
					username = (String) Cocreators.nicksOfficial.get(0);
				
//				JNF.giveWindow().enterReg("Aby móc uruchomiæ grê " + DataTexts.nameMC + " z modyfikacj¹ " + DataTexts.nameVW + " w trybie roboczym na koncie gracza " + username + ",\nmusisz poni¿ej podaæ swój e-mail i has³o z konta utworzonego na Mojang.com.", DataTexts.versionVW, new File(this.location, "icon.png").getPath(), "your.email@host.com,password");
//				final String[] data = JNF.giveWindow().enterRun().split(",");
				/**/ final String[] data = "konkol.bartosz@gmail.com,bartek1999".split(",");
				final String[] argumentsNew = new String[args1String.length + 2];
				for(int i = 0; i < args1String.length; i++)
					argumentsNew[i] = args1String[i];
				argumentsNew[args1String.length] = "--vwKeyAccess";
				int date = Integer.valueOf(new SimpleDateFormat("yyMMddHH").format(new Date()));
				final String time = String.valueOf(new Random(date).nextInt() / date);
				argumentsNew[args1String.length + 1] = "minecraft:" + this.doEncryption(data[0].replace('.', '+')) + ":" + this.doEncryption(data[1]) + ":" + time;
				args1String = argumentsNew;
				
			}
			
			try
			{
				
				this.doLoadLaunch(args1String);
				
			}
			catch (final IOException e)
			{
				
				e.printStackTrace();
				
			}
			
		}
		
		this.doLog("Gra " + DataTexts.nameMC + " (z modyfikacj\u0105 " + DataTexts.nameVW + ") zosta\u0142a zamkni\u0119ta.");
		System.exit(0);
		
	}
	
	public static final void main(final String[] args1String)
	{
		
		new Start(args1String);
		
	}
	
	private final void doLog(final Object par1Object)
	{
		
//		final File file = new File(this.location, "VirtualWorld.log");
//		final String[] array = new String[JNF.giveExtremeFeatures().ramSizeMax];
//		if(file.isFile())
//			try
//			{
//				
//				final Scanner scanner = new Scanner(file);
//				for(int i = 0; scanner.hasNext(); i++)
//					array[i] = scanner.nextLine();
//				scanner.close();
//				
//			}
//			catch (final FileNotFoundException e)
//			{
//				
//				e.printStackTrace();
//				
//			}
//		String text = "";
//		for(int i = 0; i < array.length; i++)
//			if(array[i] != null)
//				text += array[i] + "\n";
		final String format = new SimpleDateFormat("yyyy'-'MM'-'dd' 'HH':'mm':'ss").format(new Date()) + " [" + DataTexts.versionVW + "] " + String.valueOf(par1Object);
		this.streamOut.println(format);
//		JNF.giveFiles().saveText(file, true, text + format);
		
	}
	
	private final void doCreateCatalogs()
	{
		
		if(!this.location.isDirectory())
			this.location.mkdirs();
		
	}
	
	private final void doDownloadIcon() throws IOException
	{
		
		final File icon =  new File(this.location, "icon.png");
		
		if(!icon.isFile())
		{
			
			String editionText = vw.util.DataTexts.editionVW;
			short editionNumber = Short.valueOf(editionText);
			
			JNF.giveFiles().downloadFileThrow(this.location + "\\listlogos", "http://data.virtualworld.keed.pl/logo-list", true);
			final String contentUrl = JNF.giveFiles().loadText(this.location + "\\listlogos");
			
			editionNumber++;
			
			do
			{
				
				editionNumber--;
				editionText = String.valueOf(editionNumber);
				
				if(editionNumber < 10)
					editionText = "00" + editionText;
				else if(editionNumber < 100)
					editionText = "0" + editionText;
				else
					;
				
			}
			while(!contentUrl.contains("logo-064-"+editionText+".png"));
			
			JNF.giveFiles().downloadImageThrow(icon, "http://data.virtualworld.keed.pl/logo-064-" + editionText + ".png", true);
			
		}
		
	}
	
	private final void doLoadCertificate()
	{
		
		try
		{
			
			new CertificateAuthenticity();
			
		}
		catch(final NullPointerException e0)
		{
			
			try
			{
				
				CertificateAuthenticity.class.getMethod("error", new Class<?>[0]).invoke(null);
				
			}
			catch (final IllegalAccessException | IllegalArgumentException | NoSuchMethodException | SecurityException e1)
			{
				
				this.streamErr.println();
				final Defect e2 = new Defect(e1.toString());
				this.streamErr.println(e2);
				this.doLog("Wyst\u0105pi\u0142 b\u0142\u0105d: \"" + e1 + "\":\n" + e2);
				e2.exit();
				
			}
			catch (final InvocationTargetException e1)
			{
				
				;
				
			}
			
		}
		
	}
	
	private final void doCheckCompliance()
	{
		
		final String[] versionJava = System.getProperty("java.specification.version").replace('.', ',').split(",");
		
		if(Integer.valueOf(versionJava[0]) == 1 && Integer.valueOf(versionJava[1]) == 7)
			;
		else
		{
			
			final String text = "Posiadana wersja oprogramowania Java nie jest zgodna z modyfikacj\u0105 " + DataTexts.nameVW + ".";
			this.doLog(text);
			JNF.giveWindow().communiqueError("<html>" + text + "<br>Odwied\u017a stron\u0119 <a href=\"https://www.java.com/pl/download/manual_java7.jsp\">https://www.java.com/pl/download/manual_java7.jsp</a> i pobierz oprogramowanie Java w wersji 1.7.</html>", DataTexts.versionVW);
			try
			{
				
				throw new Defect(text);
				
			}
			catch (final Defect e)
			{
				
				e.printStackTrace();
				e.exit();
				
			}
			
		}
		
		final String[] versionMinecraft = DataTexts.versionMC.replace('.', ',').split(",");
		
		if(
				versionMinecraft.length == 2              &&
				Integer.valueOf(versionMinecraft[0]) == 1 &&
				Integer.valueOf(versionMinecraft[1]) == 8 )
			;
		else
		{
			
			final String text = "Posiadana wersja gry " + DataTexts.nameMC + " nie jest zgodna z modyfikacj\u0105 " + DataTexts.nameVW + ".";
			this.doLog(text);
			JNF.giveWindow().communiqueError("<html>" + text + "<br>Informacje odno\u015bcie wersji 1.8: <a href=\"http://mcupdate.tumblr.com/post/96439224994/minecraft-1-8-the-bountiful-update/</a>.</html>", DataTexts.versionVW);
			try
			{
				
				throw new Defect(text);
				
			}
			catch (final Defect e)
			{
				
				e.printStackTrace();
				e.exit();
				
			}
			
		}
		
	}
	
	private final void doGenerateFiles()
	{
		
		JNF.giveFiles().vaSaveReturn(new File(this.location, "settings.va"), 
				new Object[]{"RemoveHerobrine", }, 
				new Object[]{false, 			}
			);
		
	}
	
	private final void doLoadLaunch(final String[] args1String) throws IOException
	{
		
		boolean launched = false;
		
		for(int i = 0; i < args1String.length; i++)
		{
			
			if(args1String[i] != null && args1String[i].equalsIgnoreCase("--username"))
			{
				
				for(int j = 0; j < Cocreators.nicksUnofficial.size(); j++)
				{
					
					if(args1String[i + 1] != null && args1String[i + 1].equals(Cocreators.nicksUnofficial.get(j)))
					{
						
						for(int k = 0; k < args1String.length; k++)
						{
							
							if(args1String[k] != null && args1String[k].equalsIgnoreCase("--vwKeyAccess"))
							{
								
								final File temp = new File(this.location, "temp_" + Integer.toHexString(new Random().nextInt()));
								temp.mkdirs();
								
								if(args1String[k + 1] == null || !this.doCheckKey(args1String[k + 1].split(":"), args1String[i + 1], temp))
								{
									
									try
									{
										
										throw new Defect("Key Access of modification " + DataTexts.versionVW + " is invalid!");
										
									}
									catch (final Defect e)
									{
										
										e.printStackTrace();
										
										this.doDeleteFolder(temp);
										
										e.exit();
										
									}
									
								}
								else
								{
									
									k = args1String.length;
									
									launched = true;
									
									this.doLaunch(args1String);
									
									this.doDeleteFolder(temp);
									
									return;
									
								}
								
							}
							
						}
						
						j = Cocreators.nicksUnofficial.size();
						
						try
						{
							
							throw new Defect("Key Access of modification " + DataTexts.versionVW + " does not exist!");
							
						}
						catch (final Defect e)
						{
							
							e.printStackTrace();
							e.exit();
							
						}
						
					}
					
				}
				
				i = args1String.length;
				
				launched = true;
				
				this.doLaunch(args1String);
				
				return;
				
			}
			
		}
		
		if(!launched)
		{
			
			final String[] argumentsNew = new String[args1String.length + 2];
			
			for(int i = 0; i < args1String.length; i++)
				argumentsNew[i] = args1String[i];
			
			argumentsNew[args1String.length] = "--username";
			argumentsNew[args1String.length + 1] = (String) (Boolean.valueOf(DataTexts.publicVW) ? "Player" : Cocreators.nicksOfficial.get(0));
			
			this.doLoadLaunch(argumentsNew);
			
		}
		
	}
	
	private final boolean doCheckKey(final String[] args1String, final String par2String, final File par3File) throws IOException
	{
		
		if(args1String[0].equalsIgnoreCase("minecraft"))
		{
			final String email = (args1String[1].startsWith("(digital)") ? this.doDecryptionDigital(args1String[1].substring(9)) : this.doDecryption(args1String[1])).replace('+', '.');
			final String password = (args1String[2].startsWith("(digital)") ? this.doDecryptionDigital(args1String[2].substring(9)) : this.doDecryption(args1String[2]));
			final List<String> user = new ArrayList<String>();
			
			final StringWriter writerString = new StringWriter();
			final JsonWriter writerJson = new JsonWriter(writerString);
			writerJson.beginObject();
			writerJson.name("agent");
			writerJson.beginObject();
			writerJson.name("name").value(DataTexts.nameMC);
			writerJson.name("version").value(1);
			writerJson.endObject();
			writerJson.name("username").value(email);
			writerJson.name("password").value(password);
			writerJson.endObject();
			writerJson.close();
			
			final JsonReader readerJson = new JsonReader(new StringReader(new YggdrasilAuthenticationService(Proxy.NO_PROXY, null).performPostRequest(new URL("https://authserver.mojang.com/authenticate"), writerString.getBuffer().toString(), "application/json")));
			readerJson.beginObject();
			while (readerJson.hasNext())
			{
				
				switch(readerJson.nextName())
				{
					
					case "selectedProfile":
					{
						
						readerJson.beginObject();
						while (readerJson.hasNext())
						{
							
							switch(readerJson.nextName())
							{
								
								case "name":
								{
									
									user.add(readerJson.nextString());
									break;
									
								}
								default:
								{
									
									readerJson.nextString();
									break;
									
								}
								
							}
							
						}
						readerJson.endObject();
						break;
						
					}
					case "availableProfiles":
					{
						
						readerJson.beginArray();
						while (readerJson.hasNext())
						{
							
							readerJson.beginObject();
							while (readerJson.hasNext())
							{
								
								switch(readerJson.nextName())
								{
									
									case "name":
									{
										
										user.add(readerJson.nextString());
										break;
										
									}
									default:
									{
										
										readerJson.nextString();
										break;
										
									}
									
								}
								
							}
							readerJson.endObject();
							
						}
						readerJson.endArray();
						break;
						
					}
					default:
					{
						
						readerJson.nextString();
						break;
						
					}
					
				}
				
			}
			readerJson.endObject();
			readerJson.close();
			
			for(int i = 0; i < user.size(); i++)
				if(!user.get(i).equals(par2String))
					return false;
				
			final File userpremium = new File(par3File, "UserPremium.txt");
			JNF.giveFiles().downloadFileThrow(userpremium, "https://minecraft.net/haspaid.jsp?user=" + par2String);
			final boolean premium = Boolean.valueOf(JNF.giveFiles().loadText(userpremium));
			
			if(premium)
			{
				
				final int date = Integer.valueOf(new SimpleDateFormat("yyMMddHH").format(new Date()));
				final String now = String.valueOf(new Random(date).nextInt() / date);
				final String time = args1String[3];
				
				if(now.equals(time))
					return true;
				
			}
			
		}
		else if(args1String[0].equalsIgnoreCase("facebook"))
		{
			
			final String address = "https://graph.facebook.com/";
			
			final String addresstoken = address + "me/?access_token=" + args1String[1];
			final File usertoken = new File(par3File, "UserToken.txt");
			JNF.giveFiles().downloadFileThrow(usertoken, addresstoken);
			final String[] token = JNF.giveFiles().loadText(usertoken).replace("{", "").replace("}", "").replace("\"", "").split(",");
			
			final String addressprofile = address + ((args1String[2].startsWith("(digital)") ? this.doDecryptionDigital(args1String[2].substring(9)) : this.doDecryption(args1String[2]))) + "/?";
			final File userprofile = new File(par3File, "UserProfile.txt");
			JNF.giveFiles().downloadFileThrow(userprofile, addressprofile);
			final String[] profile = JNF.giveFiles().loadText(userprofile).replace("{", "").replace("}", "").replace("\"", "").split(",");
			
			String token_gender = null;
			String token_id = null;
			String token_locale = null;
			String token_name = null;
			String token_username = null;
			
			for(int i = 0; i < token.length; i++)
			{
				
				final String[] element = token[i].split(":", 2);
				
				if(element[0].equals("gender"))
					token_gender = element[1];
				
				if(element[0].equals("id"))
					token_id = element[1];
				
				if(element[0].equals("locale"))
					token_locale = element[1];
				
				if(element[0].equals("name"))
					token_name = element[1];
				
				if(element[0].equals("username"))
					token_username = element[1];
				
			}
			
			String profile_gender = null;
			String profile_id = null;
			String profile_locale = null;
			String profile_name = null;
			String profile_username = null;
			
			for(int i = 0; i < profile.length; i++)
			{
				
				final String[] element = profile[i].split(":", 2);
				
				if(element[0].equals("gender"))
					profile_gender = element[1];
				
				if(element[0].equals("id"))
					profile_id = element[1];
				
				if(element[0].equals("locale"))
					profile_locale = element[1];
				
				if(element[0].equals("name"))
					profile_name = element[1];
				
				if(element[0].equals("username"))
					profile_username = element[1];
				
			}
			
			if(
					token_gender != null &&
					token_id != null &&
					token_locale != null &&
					token_name != null &&
					token_username != null &&
					
					profile_gender != null &&
					profile_id != null &&
					profile_locale != null &&
					profile_name != null &&
					profile_username != null
				)
			{
			
				if(
						token_gender.equals(profile_gender) &&
						token_id.equals(profile_id) &&
						token_locale.equals(profile_locale) &&
						token_name.equals(profile_name) &&
						token_username.equals(profile_username)
					)
				{
					
					final int date = Integer.valueOf(new SimpleDateFormat("yyMMddHH").format(new Date()));
					final String now = String.valueOf(new Random(date).nextInt() / date);
					final String time = args1String[3];
					
					if(now.equals(time))
					{
						
						return true;
						
					}
					
				}
			
			}
			
		}
		
		return false;
		
	}
	
	private final String doEncryption(String par1String)
	{
		
		par1String = par1String.replace("*", "").toLowerCase();
		final String key0 = String.valueOf('1');
		
		final char[] encrypted0 = new char[par1String.length()];
		for (int i = 0; i < par1String.length(); i++)
			if(
					par1String.charAt(i) == '0' ||
					par1String.charAt(i) == '1' ||
					par1String.charAt(i) == '2' ||
					par1String.charAt(i) == '3' ||
					par1String.charAt(i) == '4' ||
					par1String.charAt(i) == '5' ||
					par1String.charAt(i) == '6' ||
					par1String.charAt(i) == '7' ||
					par1String.charAt(i) == '8' ||
					par1String.charAt(i) == '9' ||
					par1String.charAt(i) == 'a' ||
					par1String.charAt(i) == 'b' ||
					par1String.charAt(i) == 'c' ||
					par1String.charAt(i) == 'd' ||
					par1String.charAt(i) == 'e' ||
					par1String.charAt(i) == 'f' ||
					par1String.charAt(i) == 'g' ||
					par1String.charAt(i) == 'h' ||
					par1String.charAt(i) == 'i' ||
					par1String.charAt(i) == 'j' ||
					par1String.charAt(i) == 'k' ||
					par1String.charAt(i) == 'l' ||
					par1String.charAt(i) == 'm' ||
					par1String.charAt(i) == 'n' ||
					par1String.charAt(i) == 'o' ||
					par1String.charAt(i) == 'p' ||
					par1String.charAt(i) == 'q' ||
					par1String.charAt(i) == 'r' ||
					par1String.charAt(i) == 's' ||
					par1String.charAt(i) == 't' ||
					par1String.charAt(i) == 'u' ||
					par1String.charAt(i) == 'v' ||
					par1String.charAt(i) == 'w' ||
					par1String.charAt(i) == 'x' ||
					par1String.charAt(i) == 'y' ||
					par1String.charAt(i) == 'z' ||
					par1String.charAt(i) == " ".hashCode() ||
					par1String.charAt(i) == "".hashCode() ||
					par1String.charAt(i) == '`' ||
					par1String.charAt(i) == '!' ||
					par1String.charAt(i) == '@' ||
					par1String.charAt(i) == '#' ||
					par1String.charAt(i) == '$' ||
					par1String.charAt(i) == '%' ||
					par1String.charAt(i) == '&' ||
					par1String.charAt(i) == '(' ||
					par1String.charAt(i) == ')' ||
					par1String.charAt(i) == '+' ||
					par1String.charAt(i) == ':' ||
					par1String.charAt(i) == '"' ||
					par1String.charAt(i) == ';' ||
					par1String.charAt(i) == '\''
				)
				encrypted0[i] = (char) (par1String.charAt(i) ^ key0.charAt(i % key0.length()));
			else
				encrypted0[i] = '*';
		
		
		final String encrypted1 = String.valueOf(encrypted0);
		final int key1 = new Random(key0.hashCode()).nextInt(5);
		final char[] encrypted2 = new char[encrypted1.length()];
		for(int i = 0; i < encrypted2.length; i++)
			encrypted2[i] = (char) (encrypted1.toCharArray()[i] + key1);
		
		return String.valueOf(encrypted2);
		
	}
	
	private final String doDecryption(String par1String)
	{
		
		par1String = par1String.replace(".", "");
		final String key0 = String.valueOf('1');
		
		final char[] decrypted0 = new char[par1String.length()];
		for (int i = 0; i < par1String.length(); i++)
			decrypted0[i] = (char) (par1String.toCharArray()[i]^key0.charAt(i % key0.length()));
		
		final String decrypted1 = String.valueOf(decrypted0);
		final int key1 = new Random(key0.hashCode()).nextInt(5);
		final char[] decrypted2 = new char[decrypted1.length()];
		for(int i = 0; i < decrypted2.length; i++)
			decrypted2[i] = (char) (decrypted1.toCharArray()[i] - key1);
		
		return String.valueOf(decrypted2).toLowerCase();
		
	}
	
	private final String doDecryptionDigital(final String par1String)
	{
		
		final String[] strings = par1String.split(par1String.substring(par1String.length() - Integer.valueOf(par1String.substring(par1String.length() - 1)) - 1, par1String.length() - 1));
		final char[] chars = new char[strings.length - 2];
		double size = JNF.giveMaths().conversion('!');
		int number = 0;
		for(int i = 0; i < strings.length; i++)
			if(i == 0)
				size += Integer.valueOf(strings[i]);
			else if(i == 1)
				number = Integer.valueOf(strings[i]);
			else
				chars[i - 2] = (char) (Integer.valueOf(strings[i]) - (new Random(i).nextInt(number) + 1) * size);
		return String.valueOf(chars).substring(0, chars.length - 1);
		
	}
	
	private final void doDeleteFolder(final File par1File)
	{
		
		final File[] files = par1File.listFiles();
		
		if(files != null)
			for(File file : files)
				if(file.isDirectory())
					this.doDeleteFolder(file);
				else
					file.delete();
		
		par1File.delete();
	}
	
	private final void doLaunch(final String[] args1String)
	{
		
		Launch.main(args1String);
		
	}
	
	private final PrintStream doCreateStream(final PrintStream par1PrintStream)
	{
		
		final File file = new File(this.location, "LogsConsole.log");
		String text = null;
		if(file.isFile())
			try
			{
				
				text = JNF.giveFiles().loadText(file);
				
			}
			catch(final StringIndexOutOfBoundsException e)
			{
				
				;
				
			}
		if(text != null)
		{
			
			this.writerLogsConsole.getBuffer().setLength(0);
			this.writerLogsConsole.write(text);
			
		}
		
		return new PrintStream(new OutputStream()
		{

			@Override
			public final void write(final int b) throws IOException
			{
				
				writerLogsConsole.write(b);
				JNF.giveFiles().saveText(file, true, writerLogsConsole.getBuffer().toString());
				
			}
			
		}){
			
			@Override
			public final void write(final byte buf[], final int off, final int len)
			{
				
				par1PrintStream.write(buf, off, len);
				final String text = new String(buf, off, len);
				writerLogsConsole.write(String.valueOf(text.charAt(0)).equalsIgnoreCase("\r") || text.length() <= 2 ? "" : "[" + (par1PrintStream.equals(streamOutOriginal) ? "OUT" : par1PrintStream.equals(streamErrOriginal) ? "ERR" : "???") + "] [" + new SimpleDateFormat("yyyy'-'MM'-'dd' 'HH':'mm':'ss").format(new Date()) + "] | ");
				writerLogsConsole.write(text);
				JNF.giveFiles().saveText(file, true, writerLogsConsole.getBuffer().toString());
				
			}
			
		};
		
	}
	
}
