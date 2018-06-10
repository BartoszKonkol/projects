package net.polishgames.apki.hackathon.edition2;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.BindException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import org.luaj.vm2.Globals;
import org.luaj.vm2.LuaTable;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.jse.JsePlatform;

public final class Main
{
	
	protected static volatile boolean run = false, restart = true;
	
	static
	{
		final DateFormat format = new SimpleDateFormat("[yyyy-MM-dd HH:mm:ss] ");
		System.setOut(new PrintStream(System.out)
		{
			@Override
			public void print(String text)
			{
				if(text != null && !text.isEmpty())
					text = format.format(new Date()) + text;
				super.print(text);
			}
		});
	}
	
	public static final void main(final String[] args)
	{
		System.out.println("Starting the server...");
		Main.run = false;
		Main.restart = true;
		final String[] tables = new String[]{"database", "server", "scheme"};
		final Globals globals = JsePlatform.standardGlobals();
		for(final String table : tables)
			globals.set(table, LuaValue.tableOf());
		globals.get("dofile").call(LuaValue.valueOf(new File("config.lua").getAbsolutePath()));
		final Map<String, LuaTable> tablesMap = new HashMap<String, LuaTable>();
		for(final String table : tables)
			tablesMap.put(table, (LuaTable) globals.get(table));
		System.out.println(tablesMap.get("scheme").get("title").tojstring());
		Connection connection = null;
		Statement statementBasic = null;
		try
		{
			connection = DriverManager.getConnection("jdbc:mysql://" + tablesMap.get("database").get("host") + "?zeroDateTimeBehavior=convertToNull&useSSL=false", tablesMap.get("database").get("user").tojstring(), tablesMap.get("database").get("pass").tojstring());
			statementBasic = connection.createStatement();
			statementBasic.execute("CREATE DATABASE IF NOT EXISTS `" + tablesMap.get("database").get("name") + "`;");
			statementBasic.execute("USE " + tablesMap.get("database").get("name") + ";");
		}
		catch(final SQLException e)
		{
			e.printStackTrace();
		}
		if(connection != null)
		{
			try
			{
				final Statement statement = connection.createStatement();
				String columns = "";
				for(final String column : new String[]{"location", "type", "comments", "file", "time"})
					columns += "`" + column.replaceAll("\\W", "") + "` VARCHAR(1024) CHARACTER SET utf8,";
				statement.executeUpdate("CREATE TABLE IF NOT EXISTS `" + tablesMap.get("scheme").get("database").tojstring().replaceAll("\\W", "") + "` (id INT PRIMARY KEY AUTO_INCREMENT," + columns.substring(0, columns.length() - 1) + ");");
				statement.close();
			}
			catch(final SQLException e)
			{
				e.printStackTrace();
			}
			Server server = null;
			try
			{
				server = new Server(tablesMap.get("server").get("address").tojstring(), tablesMap.get("server").get("port").toint(), tablesMap.get("scheme"), connection);
			}
			catch(final IOException e)
			{
				if(e instanceof BindException)
					Main.restart = false;
				e.printStackTrace();
			}
			if(server != null)
			{
				final ServerSocket socket = server.server;
				final Server serverFinal = server;
				final Thread listener = new Thread()
				{
					@Override
					public final void run()
					{
						final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
						while(Main.run && !this.isInterrupted())
						{
							String command = null;
							try
							{
								command = reader.readLine().trim().toLowerCase();
							}
							catch(IOException e)
							{
								e.printStackTrace();
							}
							if(command != null && !command.isEmpty())
								switch(command)
								{
									case "?":
									case "help":
										System.out.println(
												"\n=============================================================" +
												"\n  Commands list:" +
												"\n    - 'help'/'?'                   Shows the commands list." +
												"\n    - 'kill'/'stop'/'exit'/'end'   Disables the server." +
												"\n=============================================================");
										break;
									case "exit":
									case "stop":
									case "kill":
									case "end":
										Main.restart = false;
										Main.run = false;
										break;
									default:
										System.out.println("Unknown command (" + command + "). Type 'help' or '?' for commands list.");
								}
						}
					}
				}, executor = new Thread()
				{
					@Override
					public final void run()
					{
						while(Main.run && !this.isInterrupted())
							if(socket.isClosed())
								break;
							else
								try
								{
									final Socket client = socket.accept();
									new Thread(() ->
									{
										try
										{
											serverFinal.onReceive(client);
										}
										catch(final IOException e)
										{
											e.printStackTrace();
										}
									}).start();
								}
								catch(final SocketException e){}
								catch(final Throwable e)
								{
									e.printStackTrace();
								}
						Main.run = false;
					}
				};
				final long time = System.currentTimeMillis() + 43200000L;
				Main.run = true;
				listener.start();
				executor.start();
				while(Main.run && System.currentTimeMillis() < time)
					;
				System.out.println("Stopping the server...");
				listener.interrupt();
				executor.interrupt();
				try
				{
					socket.close();
				}
				catch(final IOException e)
				{
					e.printStackTrace();
				}
			}
			if(statementBasic != null)
				try
				{
					statementBasic.close();
				}
				catch(final SQLException e)
				{
					e.printStackTrace();
				}
			try
			{
				connection.close();
			}
			catch(SQLException e)
			{
				e.printStackTrace();
			}
			if(Main.restart)
				Main.main(args);
		}
	}
	
}
