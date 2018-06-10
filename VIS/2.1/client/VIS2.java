package net.polishgames.vis2.client;

import java.io.CharArrayWriter;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.Writer;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Properties;
import java.util.Random;
import java.util.function.Consumer;
import java.util.function.Supplier;

public final class VIS2 implements Runnable, Closeable
{
	
	public static final String MARKER = VIS2.class.getSimpleName().toUpperCase(), VERSION = "2.1", VERSION_FULL = "2.1.2.1", CORPORATION = "Digital PGS", ADDRESS = "vis2.digitalpgs.com";
	public static final Random RANDOM = new Random();
	
	private final Socket socket;
	private final List<Consumer<Error>> errorListeners;
	
	public VIS2(final String address, final int port) throws IOException
	{
		this.socket = new Socket(Objects.requireNonNull(address), port);
		this.errorListeners = new ArrayList<Consumer<Error>>();
	}
	
	public VIS2(final int port) throws IOException
	{
		this(VIS2.ADDRESS, port);
	}
	
	public VIS2() throws IOException
	{
		this(0x2FFF + VIS2.RANDOM.nextInt(3));
	}
	
	public VIS2(final InetAddress address, final int port) throws IOException
	{
		this.socket = new Socket(Objects.requireNonNull(address), port);
		this.errorListeners = new ArrayList<Consumer<Error>>();
	}
	
	public VIS2(final InetSocketAddress address) throws IOException
	{
		this(Objects.requireNonNull(address).getAddress(), address.getPort());
	}
	
	private Supplier<PrintWriter> print;
	private Supplier<Writer> writer;
	
	public final Socket giveSocket()
	{
		return this.socket;
	}
	
	public final List<Consumer<Error>> giveErrorListeners()
	{
		return this.errorListeners;
	}
	
	public final InputStream giveInput()
	{
		try
		{
			return this.giveSocket().getInputStream();
		}
		catch(final IOException e)
		{
			return null;
		}
	}
	
	public final OutputStream giveOutput()
	{
		try
		{
			return this.giveSocket().getOutputStream();
		}
		catch(final IOException e)
		{
			return null;
		}
	}
	
	public final PrintWriter givePrint()
	{
		if(this.isPrint())
			return this.print.get();
		else
			return null;
	}
	
	public final Writer giveWriter()
	{
		if(this.isWriter())
			return this.writer.get();
		else
			return null;
	}
	
	public final boolean isInput()
	{
		return this.giveInput() != null;
	}
	
	public final boolean isOutput()
	{
		return this.giveOutput() != null;
	}
	
	public final boolean isPrint()
	{
		return this.print != null;
	}
	
	public final boolean isWriter()
	{
		return this.writer != null;
	}
	
	public final VIS2 addErrorListener(final Consumer<Error> listener)
	{
		return this.giveErrorListeners().add(listener) ? this : null;
	}
	
	public final VIS2 delErrorListener(final Consumer<Error> listener)
	{
		return this.giveErrorListeners().remove(listener) ? this : null;
	}
	
	public final VIS2 onError(final Error error)
	{
		Objects.requireNonNull(error);
		for(final Consumer<Error> listener : this.giveErrorListeners())
			listener.accept(error);
		return this;
	}
	
	public final VIS2 doRead(final byte[] array) throws IOException
	{
		if(this.isInput() && array != null)
		{
			final int length = array.length;
			if(length > 0)
			{
				int offset = 0;
				try
				{
					do
					{
						int count = this.giveInput().read(array, offset, length - offset);
						if(count <= 0)
							break;
						offset += count;
					}
					while((length - offset) > 0);
				}
				catch(final SocketException | SocketTimeoutException e){}
				return this;
			}
		}
		return null;
	}
	
	public final VIS2 doRead(final Part part) throws IOException
	{
		if(this.isWriter() && this.giveWriter().toString().trim().equalsIgnoreCase(VIS2.MARKER))
		{
			final Properties header = new Properties();
			for(final String line : this.giveWriter().toString().split("\r\n"))
				if(!line.isEmpty())
				{
					final String[] split = line.split(" => ", 2);
					if(split.length == 2)
						header.setProperty(split[0].trim(), split[1].trim());
				}
			final String protocolHeader = header.getProperty("Protocol"), protocolVersion = header.getProperty("ProtocolVersion");
			if(protocolHeader != null && protocolVersion != null && !protocolHeader.isEmpty() && !protocolVersion.isEmpty() && protocolHeader.equalsIgnoreCase(VIS2.MARKER) && protocolVersion.equalsIgnoreCase(VIS2.VERSION))
			{
				if(header.containsKey("Error"))
				{
					final String errorHeader = header.getProperty("Error");
					if(errorHeader != null && !errorHeader.isEmpty())
					{
						final String[] error = errorHeader.split(",", 3);
						if(error.length == 3)
						{
							int size = 0;
							try
							{
								size = Integer.valueOf(error[1]);
							}
							catch(final NumberFormatException e){}
							if(size > 0)
							{
								final List<String> details = new ArrayList<String>();
								for(final String line : this.giveWriter().toString().split("\r\n"))
								{
									final String lineFormat = line.trim();
									if(!lineFormat.isEmpty())
										details.add(lineFormat);
								}
								if(size == details.size())
									return this.onError(new Error(error[0].equalsIgnoreCase("normal") ? ErrorType.NORMAL : error[0].equalsIgnoreCase("critical") ? ErrorType.CRITICAL : ErrorType.UNDEFINED, size, error[2], details));
							}
						}
					}
				}
				else if(part != null)
				{
					final String sender = header.getProperty("Sender"), lengthHeader = header.getProperty("Length");
					if(sender != null && lengthHeader != null && !sender.isEmpty() && !lengthHeader.isEmpty() && sender.equals(part.giveName()))
					{
						int length = 0;
						try
						{
							length = Integer.valueOf(lengthHeader);
						}
						catch(final NumberFormatException e){}
						if(length < 0)
							length = 0;
						final byte[] content = new byte[length], array = new byte[4];
						if(this.doRead(content) != null)
							this.doRead(array);
						part.onReceive(header, content);
						return this;
					}
				}
			}
		}
		return null;
	}
	
	public final VIS2 doWrite(final byte[] array) throws IOException
	{
		if(this.isOutput() && array != null)
		{
			final int length = array.length;
			if(length > 0)
			{
				final int parts = length / 0x400;
				try
				{
					for(int i = 0; i < parts; i++)
						this.giveOutput().write(array, i * 0x400, 0x400);
					this.giveOutput().write(array, parts * 0x400, length % 0x400);
				}
				catch(final SocketException e){}
			}
			return this;
		}
		return null;
	}
	
	public final VIS2 doWrite(final byte[][] dataset) throws IOException
	{
		boolean error = false;
		for(final byte[] array : Objects.requireNonNull(dataset))
			error |= this.doWrite(array) == null;
		return error ? null : this;
	}
	
	public final PrintWriter doWrite(final PrintWriter print, final Properties header)
	{
		Objects.requireNonNull(print);
		for(final String key : Objects.requireNonNull(header).stringPropertyNames())
		{
			print.print(key);
			print.print(" => ");
			print.println(header.getProperty(key));
		}
		print.println();
		return print;
	}
	
	public final PrintWriter doWrite(final Properties header)
	{
		if(this.isPrint())
			return this.doWrite(this.givePrint(), header);
		else
			return null;
	}
	
	public final VIS2 doWrite(final Part part) throws IOException
	{
		return this.doWrite(new byte[][]{this.doWrite(Objects.requireNonNull(part).giveHeader()).toString().getBytes(), part.giveContent(), part.hasContent() ? "\r\n\r\n".getBytes() : new byte[0]});
	}
	
	public final VIS2 doTransmit(final Part part) throws IOException
	{
		try
		{
			return this.doWrite(part).doRead(part);
		}
		catch(final NullPointerException e)
		{
			return null;
		}
	}
	
	public final VIS2 doConnect(final Part... parts) throws IOException
	{
		if(!this.isPrint())
			this.print = () -> new PrintWriter(new CharArrayWriter(0))
			{
				@Override
				public void println()
				{
					this.print("\r\n");
				}
				
				@Override
				public String toString()
				{
					final String result = this.out.toString();
					this.close();
					return result;
				}
			};
		if(!this.isWriter())
			this.writer = () ->
			{
				final CharArrayWriter writer = new CharArrayWriter(0);
				if(this.isInput())
				{
					int last = -1, crlf = 0, index = 0;
					while(true)
					{
						int unit = -1;
						try
						{
							unit = this.giveInput().read();
						}
						catch(final SocketException | SocketTimeoutException e){}
						catch(final IOException e)
						{
							e.printStackTrace();
						}
						if(unit == -1)
							break;
						if(unit == 10 && last == 13)
							crlf++;
						else if(last != 10)
							crlf = 0;
						if(crlf > 1)
							break;
						last = unit;
						writer.write(unit);
						if(++index > 0x1FFF)
							break;
					}
				}
				return writer;
			};
		final Properties header = new Properties();
		header.setProperty("Protocol", VIS2.MARKER);
		header.setProperty("ProtocolVersion", VIS2.VERSION);
		header.setProperty("Parts", String.valueOf(parts == null ? 0 : parts.length));
		final PrintWriter print = this.givePrint();
		print.println(VIS2.MARKER);
		print.println();
		this.doWrite(this.doWrite(print, header).toString().getBytes());
		if(parts != null)
		{
			for(final Part part : parts)
				if(part != null)
					this.doTransmit(part);
			if(parts.length > 0)
				this.doRead((Part) null);
		}
		return this;
	}

	@Override
	@Deprecated
	public final void run()
	{
		try
		{
			this.doConnect();
		}
		catch(final IOException e)
		{
			e.printStackTrace();
		}
	}

	@Override
	public final void close() throws IOException
	{
		if(!this.giveSocket().isClosed() && this.giveSocket().isConnected())
		{
			final Properties header = new Properties();
			header.setProperty("Command", "close;");
			this.doWrite(this.doWrite(header).toString().getBytes());
			this.giveSocket().close();
		}
	}
	
}
