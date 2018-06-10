package net.polishgames.rhenowar.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.Map;
import java.util.Properties;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;
import net.polishgames.rhenowar.util.serialization.RhenowarSerializable;

public class FileRHNX extends FileRHN
{
	
	private static final long serialVersionUID = RhenowarSerializable.giveSerialVersion("1.5.3.1");
	
	public static final Series SERIES = Series.giveValue("1.5.3.1");
	public static final String SIGNATURE = "x", EXTENSION = FileRHN.EXTENSION + FileRHNX.SIGNATURE, COMMENT = FileRHN.EXTENSION + "-" + FileRHNX.SIGNATURE;
	
	public FileRHNX(final File file, final boolean onlyRead) throws IOException, ClassNotFoundException
	{
		super(file, onlyRead);
	}
	
	public FileRHNX(final File file) throws IOException, ClassNotFoundException
	{
		super(file);
	}
	
	public FileRHNX(final File parent, final String filename, final String extension, final boolean onlyRead) throws IOException, ClassNotFoundException
	{
		super(parent, filename, extension == null ? FileRHNX.EXTENSION : extension, onlyRead);
	}
	
	public FileRHNX(final File parent, final String filename, final String extension) throws IOException, ClassNotFoundException
	{
		super(parent, filename, extension == null ? FileRHNX.EXTENSION : extension);
	}
	
	public FileRHNX(final RhenowarPlugin plugin, final String filename, final String extension, final boolean onlyRead) throws IOException, ClassNotFoundException
	{
		super(plugin, filename, extension == null ? FileRHNX.EXTENSION : extension, onlyRead);
	}
	
	public FileRHNX(final RhenowarPlugin plugin, final String filename, final String extension) throws IOException, ClassNotFoundException
	{
		super(plugin, filename, extension == null ? FileRHNX.EXTENSION : extension);
	}
	
	public FileRHNX(final String filename, final String extension, final boolean onlyRead) throws IOException, ClassNotFoundException
	{
		super(filename, extension == null ? FileRHNX.EXTENSION : extension, onlyRead);
	}
	
	public FileRHNX(final String filename, final String extension) throws IOException, ClassNotFoundException
	{
		super(filename, extension == null ? FileRHNX.EXTENSION : extension);
	}
	
	public FileRHNX(final File parent, final String filename, final boolean onlyRead) throws IOException, ClassNotFoundException
	{
		this(parent, filename, null, onlyRead);
	}
	
	public FileRHNX(final File parent, final String filename) throws IOException, ClassNotFoundException
	{
		this(parent, filename, false);
	}
	
	public FileRHNX(final RhenowarPlugin plugin, final String filename, final boolean onlyRead) throws IOException, ClassNotFoundException
	{
		this(plugin.getDataFolder(), filename, onlyRead);
	}
	
	public FileRHNX(final RhenowarPlugin plugin, final String filename) throws IOException, ClassNotFoundException
	{
		this(plugin, filename, false);
	}
	
	public FileRHNX(final String filename, final boolean onlyRead) throws IOException, ClassNotFoundException
	{
		this((File) null, filename, onlyRead);
	}
	
	public FileRHNX(final String filename) throws IOException, ClassNotFoundException
	{
		this(filename, false);
	}
	
	public FileRHNX(final FileRHN file) throws IOException, ClassNotFoundException
	{
		super(file);
	}
	
	public FileRHNX(final FileRHNX file) throws IOException, ClassNotFoundException
	{
		this((FileRHN) file);
		this.setMultiply(file.getMultiply()).setLevel(file.getLevel()).setMethod(file.isMethodDeflated());
	}
	
	public FileRHNX(final FileData fileData, final boolean onlyRead) throws IOException, ClassNotFoundException
	{
		super(fileData, onlyRead);
	}
	
	public FileRHNX(final FileData fileData) throws IOException, ClassNotFoundException
	{
		super(fileData);
	}
	
	private byte factor;
	private transient boolean factorChanged;
	private transient ZipFile fileZip;
	
	public final FileRHNX setMultiply(int multiply)
	{
		if(this.isClosed() || this.isOnlyRead())
			return null;
		if(multiply < 1)
			multiply = 1;
		else if (multiply > 8)
			multiply = 8;
		multiply--;
		if(this.giveFactor() == 0 && !this.isFactorChanged())
			this.factor = -103;
		this.factor = (byte) (((this.giveFactor() - Byte.MIN_VALUE) & 0x1F) + (multiply << 5) + Byte.MIN_VALUE);
		this.factorChanged |= true;
		return this;
	}
	
	public final FileRHNX setLevel(int level)
	{
		if(this.isClosed() || this.isOnlyRead())
			return null;
		if(level < 0)
			level = 0;
		else if (level > 9)
			level = 9;
		this.factor = (byte) (((this.giveFactor() - Byte.MIN_VALUE) & 0xF0) + level + Byte.MIN_VALUE);
		this.factorChanged |= true;
		return this;
	}
	
	public final FileRHNX setMethod(final boolean deflated)
	{
		if(this.isClosed() || this.isOnlyRead())
			return null;
		this.factor = (byte) (((this.giveFactor() - Byte.MIN_VALUE) & 0xEF) + (deflated ? 0x10 : 0) + Byte.MIN_VALUE);
		this.factorChanged |= true;
		return this;
	}
	
	public final int getMultiply()
	{
		return (((this.giveFactor() - Byte.MIN_VALUE) & 0xE0) >> 5) + 1;
	}
	
	public final int getLevel()
	{
		return (this.giveFactor() - Byte.MIN_VALUE) & 0xF;
	}
	
	public final int getMethod()
	{
		return ((this.giveFactor() - Byte.MIN_VALUE) & 0x10) == 0 ? 0 : 8;
	}
	
	public final boolean isMethodDeflated()
	{
		return this.getMethod() == 8;
	}
	
	protected final boolean isFactorChanged()
	{
		return this.factorChanged;
	}
	
	public final byte giveFactor()
	{
		return this.factor;
	}
	
	public final ZipFile giveFileZip() throws IOException
	{
		if(this.isClosed())
			return null;
		else if(this.fileZip == null)
			this.fileZip = new ZipFile(this.giveFile());
		return this.fileZip;
	}
	
	public final ZipEntry giveEntryZip() throws IOException
	{
		if(this.isClosed())
			return null;
		return this.giveFileZip().getEntry("\000");
	}
	
	@Override
	public Map<String, Object> giveProperties(final Map<String, Object> map)
	{
		map.put("factor", this.giveFactor());
		map.put("factorChanged", this.isFactorChanged());
		map.put("fileZip", this.fileZip);
		return super.giveProperties(map);
	}
	
	@Override
	public synchronized FileRHNX doSave() throws IOException
	{
		if(this.isClosed() || this.isOnlyRead())
			return null;
		if(this.giveFactor() == 0 && !this.isFactorChanged())
			this.setMultiply(2);
		int multiply = 0;
		byte[] bytes = this.giveByteArray();
		for(int i = 0; i < this.getMultiply(); i++)
			if(bytes.length > 0)
			{
				bytes = this.doCompress(bytes);
				multiply++;
			}
		final String comment = FileRHNX.COMMENT.toUpperCase();
		final Properties properties = new Properties();
		properties.setProperty("l", String.valueOf(bytes.length));
		properties.setProperty("m", String.valueOf(multiply));
		properties.setProperty("s", String.valueOf(FileRHNX.SERIES.giveSeries()));
		final ByteArrayOutputStream streamProperties = new ByteArrayOutputStream();
		properties.storeToXML(streamProperties, comment);
		final byte[] bytesProperties = Base64.getEncoder().encode(streamProperties.toByteArray());
		final FileOutputStream stream = new FileOutputStream(this.giveFile());
		final DataOutputStream streamData = new DataOutputStream(stream);
		stream.write(0);
		this.doWrite(comment, streamData);
		this.doWrite(String.valueOf(bytesProperties.length), streamData);
		stream.write(0);
		stream.write(bytesProperties);
		stream.write(bytes);
		stream.write(0);
		stream.close();
		return this;
	}
	
	@Override
	public synchronized FileData doLoad() throws IOException, ClassNotFoundException
	{
		if(this.isClosed())
			return null;
		final FileInputStream stream = new FileInputStream(this.giveFile());
		final DataInputStream streamData = new DataInputStream(stream);
		if(stream.read() == 0)
		{
			final String comment = FileRHNX.COMMENT.toUpperCase();
			if(comment.equals(this.doRead(streamData)) && comment.equals(this.giveFileZip().getComment()) && FileRHNX.COMMENT.equals(this.giveEntryZip().getComment()))
			{
				int lengthProperties = 0;
				try
				{
					lengthProperties = Integer.valueOf(this.doRead(streamData));
				}
				catch(final NumberFormatException e) {}
				if(lengthProperties > 0 && stream.read() == 0)
				{
					final byte[] bytesProperties = new byte[lengthProperties];
					stream.read(bytesProperties);
					final Properties properties = new Properties();
					properties.loadFromXML(new ByteArrayInputStream(Base64.getDecoder().decode(bytesProperties)));
					if(properties.containsKey("l") && properties.containsKey("m") && properties.containsKey("s"))
					{
						int length = 0, multiply = 0;
						short series = -1;
						try
						{
							length = Integer.valueOf(properties.getProperty("l"));
							multiply = Integer.valueOf(properties.getProperty("m"));
							series = Short.valueOf(properties.getProperty("s"));
						}
						catch(final NumberFormatException e) {}
						if(length > 0 && multiply > 0 && series == FileRHNX.SERIES.giveSeries())
						{
							byte[] bytes = new byte[length];
							stream.read(bytes);
							if(stream.read() == 0 && stream.read() == -1)
							{
								for(int i = 0; i < multiply; i++)
									if(bytes.length > 0)
										bytes = this.doUncompress(bytes);
								final FileData result = this.doLoad(bytes);
								if(result != null)
								{
									this.setMultiply(multiply);
									return result;
								}
							}
						}
					}
				}
			}
		}
		return null;
	}
	
	protected synchronized byte[] doCompress(final byte[] bytes) throws IOException
	{
		final ByteArrayOutputStream stream = new ByteArrayOutputStream();
		final ZipOutputStream streamZip = new ZipOutputStream(stream);
		streamZip.setLevel(this.getLevel());
		streamZip.setMethod(this.getMethod());
		streamZip.setComment(FileRHNX.COMMENT.toUpperCase());
		final ZipEntry entry = new ZipEntry("\000");
		entry.setTime(0);
		entry.setComment(FileRHNX.COMMENT);
		streamZip.putNextEntry(entry);
		streamZip.write(bytes);
		streamZip.close();
		return stream.toByteArray();
	}
	
	protected synchronized byte[] doUncompress(final byte[] bytes) throws IOException
	{
		final ByteArrayOutputStream stream = new ByteArrayOutputStream();
		final ZipInputStream streamZip = new ZipInputStream(new ByteArrayInputStream(bytes));
		streamZip.getNextEntry();
		int value = -1;
		while((value = streamZip.read()) != -1)
			stream.write(value);
		streamZip.close();
		return stream.toByteArray();
	}
	
	@Override
	protected void onClose() throws IOException
	{
		super.onClose();
		if(this.fileZip != null)
			this.giveFileZip().close();
	}
	
}
