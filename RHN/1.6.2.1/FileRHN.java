package net.polishgames.rhenowar.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import net.polishgames.rhenowar.util.serialization.RhenowarSerializable;

public class FileRHN extends RhenowarObject implements Serializable, Closeable, Iterable<Object>
{
	
	private static final long serialVersionUID = RhenowarSerializable.giveSerialVersion("1.5.3.1");
	
	public static final Series SERIES = Series.giveValue("1.4.3.1");
	public static final String EXTENSION = "rhn";
	
	private final File file;
	private final boolean onlyRead;
	private final transient FileData fileData;
	protected final Map<String, Object> objects;
	
	public FileRHN(final File file, final boolean onlyRead) throws IOException, ClassNotFoundException
	{
		this.file = Util.nonNull(file);
		this.objects = new HashMap<String, Object>();
		FileData fileData = null;
		if(!(this.giveFile().exists() && this.giveFile().isFile()))
		{
			if(onlyRead)
				Util.doThrowFNFE();
			else
			{
				if(!(this.giveDirectory().exists() && this.giveDirectory().isDirectory()))
					this.giveDirectory().mkdirs();
				this.giveFile().createNewFile();
				this.doSave();
			}
		}
		else
			fileData = this.doLoad();
		this.fileData = fileData;
		this.onlyRead = onlyRead;
	}
	
	public FileRHN(final File file) throws IOException, ClassNotFoundException
	{
		this(file, false);
	}
	
	public FileRHN(final File parent, final String filename, final String extension, final boolean onlyRead) throws IOException, ClassNotFoundException
	{
		this(new File(parent, Util.nonEmpty(filename) + "." + (extension == null ? FileRHN.EXTENSION : extension)), onlyRead);
	}
	
	public FileRHN(final File parent, final String filename, final String extension) throws IOException, ClassNotFoundException
	{
		this(parent, filename, extension, false);
	}
	
	public FileRHN(final RhenowarPlugin plugin, final String filename, final String extension, final boolean onlyRead) throws IOException, ClassNotFoundException
	{
		this(plugin.getDataFolder(), filename, extension, onlyRead);
	}
	
	public FileRHN(final RhenowarPlugin plugin, final String filename, final String extension) throws IOException, ClassNotFoundException
	{
		this(plugin, filename, extension, false);
	}
	
	public FileRHN(final String filename, final String extension, final boolean onlyRead) throws IOException, ClassNotFoundException
	{
		this((File) null, filename, extension, onlyRead);
	}
	
	public FileRHN(final String filename, final String extension) throws IOException, ClassNotFoundException
	{
		this(filename, extension, false);
	}
	
	public FileRHN(final File parent, final String filename, final boolean onlyRead) throws IOException, ClassNotFoundException
	{
		this(parent, filename, null, onlyRead);
	}
	
	public FileRHN(final File parent, final String filename) throws IOException, ClassNotFoundException
	{
		this(parent, filename, false);
	}
	
	public FileRHN(final RhenowarPlugin plugin, final String filename, final boolean onlyRead) throws IOException, ClassNotFoundException
	{
		this(plugin.getDataFolder(), filename, onlyRead);
	}
	
	public FileRHN(final RhenowarPlugin plugin, final String filename) throws IOException, ClassNotFoundException
	{
		this(plugin, filename, false);
	}
	
	public FileRHN(final String filename, final boolean onlyRead) throws IOException, ClassNotFoundException
	{
		this((File) null, filename, onlyRead);
	}
	
	public FileRHN(final String filename) throws IOException, ClassNotFoundException
	{
		this(filename, false);
	}
	
	public FileRHN(final FileRHN file) throws IOException, ClassNotFoundException
	{
		this(Util.nonNull(file).giveFile(), file.isOnlyRead());
		if(file.hasTitle())
			this.setTitle(file.getTitle());
		if(file.hasDescription())
			this.setDescription(file.getDescription());
	}
	
	public FileRHN(final FileData fileData, final boolean onlyRead) throws IOException, ClassNotFoundException
	{
		this(Util.nonNull(fileData).giveFile(), onlyRead);
		if(fileData.hasTitle())
			this.setTitle(fileData.giveTitle());
		if(fileData.hasDescription())
			this.setDescription(fileData.giveDescription());
	}
	
	public FileRHN(final FileData fileData) throws IOException, ClassNotFoundException
	{
		this(fileData, false);
	}
	
	private String title, description;
	private volatile transient boolean closed;
	
	public final FileRHN setTitle(final String title)
	{
		if(this.isClosed() || this.isOnlyRead())
			return null;
		this.title = title;
		return this;
	}
	
	public final FileRHN setDescription(final String description)
	{
		if(this.isClosed() || this.isOnlyRead())
			return null;
		this.description = description;
		return this;
	}
	
	public final String getTitle()
	{
		return this.title;
	}
	
	public final String getDescription()
	{
		return this.description;
	}
	
	public final synchronized FileRHN addObject(final String key, final Serializable object)
	{
		if(this.isClosed() || this.isOnlyRead())
			return null;
		this.objects.put(Util.nonEmpty(key), Util.nonNull(object));
		return this;
	}
	
	public final synchronized FileRHN delObject(final String key)
	{
		if(this.isClosed() || this.isOnlyRead())
			return null;
		this.objects.remove(Util.nonEmpty(key));
		return this;
	}
	
	public final synchronized Object giveObject(final String key)
	{
		return this.objects.get(Util.nonEmpty(key));
	}
	
	public final synchronized boolean hasObject(final String key)
	{
		return this.objects.containsKey(Util.nonEmpty(key));
	}
	
	public final synchronized List<Object> giveObjects()
	{
		return Collections.unmodifiableList(new ArrayList<Object>(this.objects.values()));
	}
	
	public final File giveFile()
	{
		return this.file;
	}
	
	public final File giveDirectory()
	{
		return this.giveFile().getAbsoluteFile().getParentFile();
	}
	
	public final FileData giveFileData()
	{
		return this.fileData;
	}
	
	public final boolean hasFileData()
	{
		return this.giveFileData() != null;
	}
	
	public final boolean hasTitle()
	{
		return this.getTitle() != null;
	}
	
	public final boolean hasDescription()
	{
		return this.getDescription() != null;
	}
	
	public synchronized byte[] giveByteArray() throws IOException
	{
		final Map<String, Object> objects = new HashMap<String, Object>(this.objects);
		final int size = objects.size();
		final String[] keys = objects.keySet().toArray(new String[size]);
		final Object[] values = objects.values().toArray(new Object[size]);
		final ByteArrayOutputStream stream = new ByteArrayOutputStream();
		final DataOutputStream dataStream = new DataOutputStream(stream);
		stream.write(1);
		for(final String text : new String[]{FileRHN.EXTENSION.toUpperCase(), Util.hasUtil() ? Util.giveUtil().giveUtilName() : "", UtilPlugin.SERIES.giveVersion()})
		{
			this.doWrite(text, dataStream);
			stream.write(31);
		}
		this.doWrite(new String(Base64.getEncoder().encode(this.giveFile().getAbsolutePath().getBytes())), dataStream);
		stream.write(6);
		for(final String text : new String[]{this.getTitle(), this.getDescription()})
		{
			stream.write(2);
			if(text == null)
				stream.write(0);
			else
				this.doWrite(text, dataStream);
			stream.write(3);
		}
		stream.write(6);
		if(size > 0)
		{
			this.doWrite(String.valueOf(size), dataStream);
			stream.write(29);
			final ObjectOutputStream objectStream = new ObjectOutputStream(stream);
			for(int i = 0; i < size; i++)
			{
				if(i != 0)
					stream.write(30);
				this.doWrite(keys[i], dataStream);
				stream.write(31);
				objectStream.writeObject(values[i]);
			}
		}
		else
			stream.write(0);
		stream.write(4);
		return stream.toByteArray();
	}
	
	@Override
	public Map<String, Object> giveProperties(final Map<String, Object> map)
	{
		map.put("file", this.giveFile());
		map.put("fileData", this.giveFileData());
		map.put("title", this.getTitle());
		map.put("description", this.getDescription());
		map.put("closed", this.isClosed());
		map.put("onlyRead", this.isOnlyRead());
		map.put("objects", this.objects);
		return map;
	}
	
	public synchronized FileRHN doSave() throws IOException
	{
		if(this.isClosed() || this.isOnlyRead())
			return null;
		final byte[] bytes = this.giveByteArray();
		final FileOutputStream stream = new FileOutputStream(this.giveFile());
		this.doWrite(String.valueOf(bytes.length), new DataOutputStream(stream));
		stream.write(bytes);
		stream.close();
		return this;
	}
	
	public synchronized FileData doLoad(final byte[] bytes) throws IOException, ClassNotFoundException
	{
		if(!this.isClosed() && bytes.length > 0)
		{
			final ByteArrayInputStream stream = new ByteArrayInputStream(bytes);
			final DataInputStream dataStream = new DataInputStream(stream);
			if(stream.read() == 1 && FileRHN.EXTENSION.toUpperCase().equals(this.doRead(dataStream)) && stream.read() == 31 && (Util.hasUtil() ? Util.giveUtil().giveUtilName() : "").equals(this.doRead(dataStream)) && stream.read() == 31)
			{
				final String seriesName = this.doRead(dataStream);
				if(seriesName != null && !seriesName.isEmpty())
				{
					Series series = Series.giveValue(seriesName);
					if(series == null)
						series = UtilPlugin.SERIES;
					final short number = series.giveSeries();
					if(number >= FileRHN.SERIES.giveSeries() && number <= UtilPlugin.SERIES.giveSeries() && stream.read() == 31)
					{
						final String fileEncoded = this.doRead(dataStream);
						if(fileEncoded != null && !fileEncoded.isEmpty())
						{
							final String file = new String(Base64.getDecoder().decode(fileEncoded));
							if(!file.isEmpty() && stream.read() == 6)
							{
								final String[] data = new String[2];
								boolean next = true;
								for(int i = 0; i < data.length && next; i++)
								{
									if(stream.read() == 2)
									{
										data[i] = this.doRead(dataStream);
										if(stream.read() != 3)
											next = false;
									}
									else
										next = false;
								}
								if(next && stream.read() == 6)
								{
									Object[][] objects = null;
									try
									{
										final int size = Integer.valueOf(this.doRead(dataStream));
										if(size > 0 && stream.read() == 29)
										{
											final String[] keys = new String[size];
											final Object[] values = new Object[size];
											final ObjectInputStream objectStream = new ObjectInputStream(stream);
											for(int i = 0; i < size && next; i++)
											{
												if(i != 0 && stream.read() != 30)
													next = false;
												if(next)
												{
													final String key = this.doRead(dataStream);
													if(key != null && !key.isEmpty())
														keys[i] = key;
													else
														next = false;
													if(next && stream.read() != 31)
														next = false;
													if(next)
													{
														final Object value = objectStream.readObject();
														if(value != null)
															values[i] = value;
														else
															next = false;
													}
												}
											}
											if(next)
											{
												objects = new Object[2][size];
												for(int i = 0; i < size; i++)
												{
													final Object key = keys[i], value = values[i];
													if(key != null && value != null)
													{
														objects[0][i] = key;
														objects[1][i] = value;
													}
												}
											}
										}
									}
									catch(final NumberFormatException e) {}
									if(stream.read() == 4 && stream.read() == -1)
									{
										final Map<String, Object> map = new HashMap<String, Object>();
										String title = null, description = null;
										if(data.length == 2)
										{
											title = data[0];
											description = data[1];
											if(title != null && !title.isEmpty())
												this.setTitle(title);
											if(description != null && !description.isEmpty())
												this.setDescription(description);
										}
										if(objects != null && objects.length == 2)
										{
											final int sizeArray = objects[0].length;
											if(sizeArray == objects[1].length)
												for(int i = 0; i < sizeArray; i++)
												{
													final String key = objects[0][i].toString();
													final Object value = objects[1][i];
													if(!key.isEmpty() && value instanceof Serializable)
													{
														map.put(key, value);
														this.addObject(key, (Serializable) value);
													}
												}
										}
										return new FileData(new File(file), series, map, title, description);
									}
								}
							}
						}
					}
				}
			}
		}
		return null;
	}
	
	public synchronized FileData doLoad() throws IOException, ClassNotFoundException
	{
		if(this.isClosed())
			return null;
		final FileInputStream stream = new FileInputStream(this.giveFile());
		int length = 0;
		try
		{
			length = Integer.valueOf(this.doRead(new DataInputStream(stream)));
		}
		catch(final NumberFormatException e)
		{
			stream.close();
			e.printStackTrace();
		}
		if(length > 0)
		{
			final byte[] bytes = new byte[length];
			stream.read(bytes);
			stream.close();
			return this.doLoad(bytes);
		}
		else
			return null;
	}
	
	protected final FileRHN doWrite(final String text, final DataOutputStream stream) throws IOException
	{
		Util.nonNull(stream).write(33);
		stream.writeUTF(Util.nonNull(text));
		return this;
	}
	
	protected final String doRead(final DataInputStream stream) throws IOException
	{
		if(Util.nonNull(stream).read() == 33)
			return stream.readUTF();
		else
			return null;
	}
	
	public final boolean isOnlyRead()
	{
		return this.onlyRead;
	}
	
	public final boolean isClosed()
	{
		return this.closed;
	}
	
	protected void onClose() throws IOException
	{
		if(!this.isOnlyRead())
			this.doSave();
	}

	@Override
	public final void close() throws IOException
	{
		if(!this.isClosed())
		{
			this.onClose();
			this.closed = true;
		}
	}

	@Override
	public final Iterator<Object> iterator()
	{
		return this.giveObjects().iterator();
	}
	
	public static final class FileData extends RhenowarObject implements Serializable
	{
		
		private static final long serialVersionUID = FileRHN.SERIES.giveSeries();
		
		private final File file;
		private final Series series;
		private final Map<String, Object> objects;
		private final String title, description;
		
		public FileData(final File file, final Series series, final Map<String, Object> objects, final String title, final String description)
		{
			this.file = Util.nonNull(file);
			this.series = Util.nonNull(series);
			this.objects = Util.nonNull(objects);
			this.title = title;
			this.description = description;
		}
		
		public final File giveFile()
		{
			return this.file;
		}
		
		public final Series giveSeries()
		{
			return this.series;
		}
		
		public final Map<String, Object> giveObjects()
		{
			return Collections.unmodifiableMap(this.objects);
		}
		
		public final String giveTitle()
		{
			return this.title;
		}
		
		public final String giveDescription()
		{
			return this.description;
		}
		
		public final boolean hasObjects()
		{
			return !this.giveObjects().isEmpty();
		}
		
		public final boolean hasTitle()
		{
			return this.giveTitle() != null;
		}
		
		public final boolean hasDescription()
		{
			return this.giveDescription() != null;
		}

		@Override
		public Map<String, Object> giveProperties(final Map<String, Object> map)
		{
			map.put("file", this.giveFile());
			map.put("series", this.giveSeries());
			map.put("objects", this.giveObjects());
			map.put("title", this.giveTitle());
			map.put("description", this.giveDescription());
			return map;
		}
		
	}
	
}
