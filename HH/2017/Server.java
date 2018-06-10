package net.polishgames.apki.hackathon.edition2;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;
import java.util.Properties;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import org.luaj.vm2.LuaTable;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.Varargs;

public final class Server
{
	
	protected static final String CHARSET = "UTF-8";
	
	protected final ServerSocket server;
	protected final LuaTable table;
	protected final Connection connection;
	protected final File files;
	
	public Server(final String address, final int port, final LuaTable table, final Connection connection) throws IOException
	{
		this.server = new ServerSocket(port, 0, InetAddress.getByName(address));
		this.table = table;
		this.connection = connection;
		this.files = new File("files");
		if(!this.files.isDirectory())
			this.files.mkdirs();
	}
	
	public final void onReceive(final Socket client) throws IOException
	{
		final Properties properties = new Properties();
		String type = null;
		final BufferedReader reader = new BufferedReader(new InputStreamReader(client.getInputStream()));
		int index = 0;
		String line = null;
		while((line = reader.readLine()) != null && !line.isEmpty())
		{
			if(index > 0)
			{
				String[] split = line.split(":", 2);
				properties.setProperty(split[0].trim(), split[1].trim());
			}
			else
				type = line;
			index++;
		}
		if(type != null)
		{
			final String length = properties.getProperty("Content-Length");
			final char[] array = new char[length != null ? Integer.valueOf(length) : 0];
			if(array.length > 0)
				reader.read(array);
			final String content = new String(array);
			final byte[] website = this.giveWebSite(type, this.giveDecode(type.split(" ", 3)[1]).replace(':', '/').split("/"), properties, content);
			final OutputStream stream = client.getOutputStream();
			stream.write(("HTTP/1.1 200 OK\r\nContent-Length: " + website.length + "\r\n\r\n").getBytes());
			if(website.length > 0)
				stream.write(website);
			client.close();
			this.doAction(type, properties, content);
		}
		else
			client.close();
	}
	
	public final void doAction(final String type, final Properties properties, final String content)
	{
		if(type.split(" ")[0].trim().equalsIgnoreCase("post"))
		{
			final Properties post = new Properties();
			for(final String value : content.split("&"))
			{
				final String[] array = value.split("=", 2);
				post.setProperty(this.giveDecode(array[0]), this.giveDecode(array[1]));
			}
			final String fileName = System.currentTimeMillis() + ".jpg";
			boolean fileSave = false;
			final Properties db = new Properties();
			for(final Object obj : post.keySet())
				if(obj != null)
				{
					final Object objValue = post.get(obj);
					if(objValue != null)
					{
						final String key = obj.toString().toLowerCase().trim(), value = objValue.toString().trim();
						switch(key)
						{
							case "location":
							case "type":
							case "comments":
								db.setProperty(key, value);
								break;
							case "file":
							{
								final String[] array = value.split(",", 2);
								if(array.length == 2)
								{
									final File file = new File(this.files, fileName);
									if(!file.isFile())
										try
										{
											this.doSaveFile(file, Base64.getDecoder().decode(array[1]));
											fileSave |= true;
										}
										catch(final IllegalArgumentException e){}
								}
							} break;
						}
					}
				}
			if(fileSave)
				db.setProperty("file", fileName);
			final String time = String.valueOf(System.currentTimeMillis());
			db.setProperty("time", time);
			try
			{
				final Statement statement = this.connection.createStatement();
				String columns = "", values = "";
				for(final Object obj : db.keySet())
				{
					final String key = obj.toString();
					columns += "`" + key.replaceAll("\\W", "") + "`,";
					values += "'" + this.giveEncode(db.getProperty(key).replace("'", "''").replace("\uFFFD", "")) + "',";
				}
				statement.executeUpdate("INSERT INTO `" + this.table.get("database").tojstring().replaceAll("\\W", "") + "` (" + columns.substring(0, columns.length() - 1) + ") VALUES (" + values.substring(0, values.length() - 1) + ");");
				statement.close();
			}
			catch(final SQLException e)
			{
				e.printStackTrace();
			}
			final String location = db.getProperty("location");
			if(location != null && !location.isEmpty())
				try
				{
					this
						.doSaveFile(new File(this.files, "map_list_" + time + ".png"), this.giveURL("https://maps.googleapis.com/maps/api/staticmap?center=" + location + "&markers=" + location + "&maptype=hybrid&zoom=15&scale=2&size=300x200"))
						.doSaveFile(new File(this.files, "map_detail_" + time + ".png"), this.giveURL("https://maps.googleapis.com/maps/api/staticmap?center=" + location + "&markers=" + location + "&maptype=hybrid&zoom=16&size=400x300"));
				}
				catch(final IOException e)
				{
					e.printStackTrace();
				}
		}
	}
	
	public final byte[] giveWebSite(final String type, final String[] link, final Properties properties, final String content) throws IOException
	{
		if(link.length < 2)
		{
			if(link.length != 1 || link[0] != null)
				return this.giveWebSite(type, new String[]{null, "main"}, properties, content);
			return new String(this.giveWebSite(type, "scheme.html", properties, content)
					.replace("%charset%", Server.CHARSET)
					.replace("%style%", "resources/style.css")
					.replace("%script%", "resources/script.js")
					.replace("%title%", this.giveCharset(this.table.get("title").tojstring()))
					.replace("%menu%", this.giveWebSite(type, "menu.html", properties, content))).getBytes();
		}
		else if(link.length == 2)
		{
			final String postlink = link[1].toLowerCase().trim();
			String website = new String(this.giveWebSite(type, new String[]{null}, properties, content)).replace("%content%", (postlink.equalsIgnoreCase("list") ? (WebSite) this::giveWebSiteList : (WebSite) this::giveWebSite).giveWebSite(type, postlink + ".html", properties, content));
			switch(postlink)
			{
				case "form":
				{
					String option = "";
					final LuaValue value = this.table.get("option");
					if(value instanceof LuaTable)
					{
						LuaValue key = LuaValue.NIL;
						while(true)
						{
							final Varargs args = value.next(key);
							if((key = args.arg(1)).isnil())
								break;
							final String name = this.giveCharset(args.arg(2).tojstring());
							option += "<option value=\"" + name + "\">" + name + "</option>";
						}
					}
					website = website.replace("%option%", option);
				} break;
			}
			return website.getBytes();
		}
		else if(link.length == 3)
		{
			URL url = null;
			final String name = link[2].toLowerCase().trim();
			switch(link[1].toLowerCase().trim())
			{
				case "resources":
					url = this.getClass().getClassLoader().getResource(name);
					break;
				case "files":
					url = new File(this.files, name).toURI().toURL();
					break;
				case "list":
					try
					{
						final Statement statement = this.connection.createStatement();
						final ResultSet result = statement.executeQuery("SELECT * FROM `" + this.table.get("database").tojstring().replaceAll("\\W", "") + "` WHERE `time`='" + name.replaceAll("\\W", "").replace("'", "''") + "';");
						final boolean correct = result.next();
						String photo = null, typeRes = null, comments = null, location = null;
						if(correct)
						{
							photo = this.giveDecode(result.getString("file"));
							typeRes = this.giveDecode(result.getString("type"));
							comments = this.giveDecode(result.getString("comments"));
							location = this.giveDecode(result.getString("location"));
						}
						statement.close();
						if(correct)
						{
							String address = null;
							if(location != null && !location.isEmpty())
								address = this.giveAddress(location);
							return new String(this.giveWebSite(type, new String[]{null}, properties, content)).replace("%content%", this.giveWebSite(type, "detail.html", properties, content)
									.replace("%photo%", photo != null ? "files/" + photo : "")
									.replace("%address%", address != null ? address : "-")
									.replace("%type%", typeRes != null && !typeRes.isEmpty() ? this.giveCharset(typeRes) : "-")
									.replace("%comments%", comments != null && !comments.isEmpty() ? this.giveCharset(comments) : "-")
									.replace("%location%", location != null && !location.isEmpty() ? location : "-")
									.replace("%map%", location != null && !location.isEmpty() ? "files/map_detail_" + name + ".png" : "")
								).getBytes();
						}
					}
					catch(final SQLException e)
					{
						e.printStackTrace();
					}
					break;
			}
			if(url != null)
				try
				{
					return this.giveURL(url);
				}
				catch(final FileNotFoundException e){}
		}
		return new byte[0];
	}
	
	public final String giveWebSite(final String type, final String link, final Properties properties, final String content) throws IOException
	{
		return this.giveCharset(this.giveWebSite(type, new String[]{null, "resources", link}, properties, content));
	}
	
	public final String giveWebSiteList(final String type, final String link, final Properties properties, final String content) throws IOException
	{
		String list = "";
		final String element = this.giveWebSite(type, link, properties, content).replaceAll("[\t\r\n]", "");
		Statement statement;
		try
		{
			statement = this.connection.createStatement();
			final ResultSet result = statement.executeQuery("SELECT * FROM `" + this.table.get("database").tojstring().replaceAll("\\W", "") + "`;");
			while(result.next())
			{
				final String time = this.giveDecode(result.getString("time")), img = this.giveDecode(result.getString("file")), location = this.giveDecode(result.getString("location")), desc = this.giveDecode(result.getString("type"));
				final boolean timeExist = time != null && !time.isEmpty(), locationExist = location != null && !location.isEmpty();
				String timeFormat = null;
				if(timeExist)
					try
					{
						timeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(Long.valueOf(time)));
					}
					catch(final NumberFormatException e){}
				String address = null;
				if(locationExist)
					address = this.giveAddress(location);
				list += element
					.replace("%link%", timeExist ? "/list:" + time : "#")
					.replace("%img%", img != null && !img.isEmpty() ? "files/" + img : locationExist ? "files/map_list_" + time + ".png" : "")
					.replace("%desc%", this.giveCharset((desc != null && !desc.isEmpty() ? "<b>" + desc + "</b><br>" : "") + (timeFormat != null ? timeFormat + "<br>" : "") + (address != null ? address + "<br>" : "")));
			}
			statement.close();
		}
		catch(final SQLException e)
		{
			e.printStackTrace();
		}
		return list;
	}
	
	public final byte[] giveURL(final URL url) throws IOException
	{
		final InputStream stream = url.openStream();
		final ByteArrayOutputStream array = new ByteArrayOutputStream();
		int read;
		final byte[] data = new byte[1024];
		while((read = stream.read(data, 0, data.length)) != -1)
			array.write(data, 0, read);
		array.flush();
		stream.close();
		return array.toByteArray();
	}
	
	public final byte[] giveURL(final String url) throws IOException
	{
		return this.giveURL(new URL(url));
	}
	
	public final Server doSaveFile(final File file, final byte[] array)
	{
		try
		{
			final OutputStream stream = new FileOutputStream(file);
			stream.write(array);
			stream.close();
		}
		catch(final IOException e)
		{
			e.printStackTrace();
		}
		return this;
	}
	
	public final String giveAddress(final String location)
	{
		try
		{
			return new JsonParser().parse(new String(this.giveURL("http://maps.googleapis.com/maps/api/geocode/json?latlng=" + location))).getAsJsonObject().get("results").getAsJsonArray().get(0).getAsJsonObject().get("formatted_address").getAsString();
		}
		catch(final JsonSyntaxException | IOException e)
		{
			e.printStackTrace();
			return null;
		}
	}
	
	public final String giveDecode(final String content)
	{
		if(content == null)
			return null;
		try
		{
			return URLDecoder.decode(content, Server.CHARSET);
		}
		catch(final UnsupportedEncodingException | IllegalArgumentException e)
		{
			e.printStackTrace();
			return "";
		}
	}
	
	public final String giveEncode(final String content)
	{
		if(content == null)
			return null;
		try
		{
			return URLEncoder.encode(content, Server.CHARSET);
		}
		catch(final UnsupportedEncodingException | IllegalArgumentException e)
		{
			e.printStackTrace();
			return "";
		}
	}
	
	public final String giveCharset(final String text)
	{
		return new String(Charset.forName(Server.CHARSET).encode(text).array()).replace("\0", "");
	}
	
	public final String giveCharset(final byte[] array)
	{
		return this.giveCharset(new String(array));
	}
	
	@FunctionalInterface
	protected interface WebSite
	{
		public String giveWebSite(final String type, final String link, final Properties properties, final String content) throws IOException;
	}
	
}
