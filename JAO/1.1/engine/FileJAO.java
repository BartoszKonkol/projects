package javax.jao;

import java.io.Closeable;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public final class FileJAO implements Closeable
{
	
	private final JarFile file;
	private final Properties data;
	private final InputStream main;
	private final Map<File, InputStream> assets, libraries, different;
	
	public FileJAO(final File file) throws IOException
	{
		this.file = new JarFile(file);
		final Properties data = new Properties();
		data.load(this.giveFile().getInputStream(this.giveFile().getEntry("data.properties")));
		this.data = data;
		final Enumeration<JarEntry> entries = this.giveFile().entries();
		final Map<File, InputStream> components = new HashMap<File, InputStream>();
		final String fileName = this.giveFile().getName();
		final String fileAbsolutePath = new File(fileName).getAbsolutePath();
		while(entries.hasMoreElements())
		{
			final JarEntry entry = entries.nextElement();
			if(!entry.isDirectory())
				components.put(new File(fileName, entry.getName()), this.giveFile().getInputStream(entry));
		}
		InputStream main = null;
		final Map<File, InputStream> assets = new HashMap<File, InputStream>(), libraries = new HashMap<File, InputStream>(), different = new HashMap<File, InputStream>();
		for(final File component : components.keySet())
		{
			final InputStream stream = components.get(component);
			final String componentAbsolutePath = component.getAbsolutePath();
			if(component.getParentFile().getAbsolutePath().equals(fileAbsolutePath) && component.getName().equalsIgnoreCase(this.giveData().getProperty("main")))
				main = stream;
			else if(componentAbsolutePath.startsWith(new File(fileAbsolutePath, "assets").getAbsolutePath()))
				assets.put(component, stream);
			else if(componentAbsolutePath.startsWith(new File(fileAbsolutePath, "libraries").getAbsolutePath()))
				libraries.put(component, stream);
			else
				different.put(component, stream);
		}
		this.main = main;
		this.assets = Collections.unmodifiableMap(assets);
		this.libraries = Collections.unmodifiableMap(libraries);
		this.different = Collections.unmodifiableMap(different);
	}
	
	public FileJAO(final String name) throws IOException
	{
		this(new File(name).getAbsoluteFile());
	}
	
	public final JarFile giveFile()
	{
		return this.file;
	}
	
	public final Properties giveData()
	{
		return this.data;
	}
	
	public final InputStream giveMain()
	{
		return this.main;
	}
	
	public final Map<File, InputStream> giveAssets()
	{
		return this.assets;
	}
	
	public final Map<File, InputStream> giveLibraries()
	{
		return this.libraries;
	}
	
	public final Map<File, InputStream> giveDifferent()
	{
		return this.different;
	}
	
	public final boolean doExtractFile(final File file) throws IOException
	{
		return this.doExtractFile((File) null, file);
	}
	
	public final boolean doExtractFile(final File component, final File file) throws IOException
	{
		if(component == null)
			FileJAO.doExtractFile(this.giveMain(), file);
		else if(this.giveAssets().containsKey(component))
			FileJAO.doExtractFile(this.giveAssets().get(component), file);
		else if(this.giveLibraries().containsKey(component))
			FileJAO.doExtractFile(this.giveLibraries().get(component), file);
		else if(this.giveDifferent().containsKey(component))
			FileJAO.doExtractFile(this.giveDifferent().get(component), file);
		else
			return false;
		return true;
	}
	
	@Override
	public final void close() throws IOException
	{
		this.giveFile().close();
	}
	
	@Override
	public final String toString()
	{
		return this.getClass().getName() + "[" + this.giveFile().getName() + ", @" + Integer.toHexString(this.hashCode()) + "]";
	}
	
	protected static final void doExtractFile(final InputStream inputStream, final File file) throws IOException
	{
		if(!file.isFile())
			file.createNewFile();
		final FileOutputStream outputStream = new FileOutputStream(file);
		byte[] buffer = new byte[2 << 9];
		for(int i = inputStream.read(buffer); i > 0; i = inputStream.read(buffer))
			outputStream.write(buffer, 0, i);
		outputStream.close();
		inputStream.close();
	}
	
}
