package javax.jao;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Random;
import javax.jao.registry.IRegistry;
import javax.jao.registry.KeyInductionJAO;
import javax.jao.registry.KeyJAO;
import javax.jao.registry.KeyParentJAO;
import javax.jao.registry.ValueJAO;
import javax.jao.winapi.winreg.ValueOver;

public class RegJAO implements IRegistry<KeyJAO, ValueJAO, String>
{
	
	private final KeyInductionJAO keyInduction;
	private final File fileParent;
	private final Properties propertiesParent;
	
	public RegJAO(final KeyInductionJAO keyInduction) throws IOException
	{
		this.keyInduction = Util.doRequireNonNull(keyInduction);
		final WinReg reg = WinReg.giveWinReg();
		final ValueOver value = new ValueOver(Util.giveDefaultWinRegKeyJAO(), "RegFile" + this.giveKeyParent().giveNameForm());
		if(!Util.doRegistryValueNull(reg.giveEmptyValue(value), value))
		{
			this.fileParent = new File(reg.getValue(value));
			final Properties properties = new Properties();
			properties.loadFromXML(new FileInputStream(this.fileParent));
			this.propertiesParent = properties;
		}
		else
			throw new RuntimeException();
	}
	
	public final KeyInductionJAO giveKeyInduction()
	{
		return this.keyInduction;
	}
	
	public final KeyParentJAO giveKeyParent()
	{
		return this.keyInduction.giveKeyParent();
	}
	
	public final List<KeyJAO> giveKeys()
	{
		return this.keyInduction.giveKeys();
	}
	
	protected final File giveFileParent()
	{
		return this.fileParent;
	}
	
	protected final Properties givePropertiesParent()
	{
		return this.propertiesParent;
	}
	
	protected final void doSavePropertiesParent() throws IOException
	{
		this.propertiesParent.storeToXML(new FileOutputStream(this.fileParent), null);
	}
	
	protected ValueData giveValueData(final ValueJAO value) throws IOException
	{
		final String key = this.giveKeyInduction().giveKey() + (Util.doRequireNonNull(value).giveKey() != null ? "." + value.giveKey().giveKey() : "");
		final File file = new File(this.givePropertiesParent().getProperty(key));
		final Properties properties = new Properties();
		if(this.giveEmptyKey(value.giveKey()) || !file.isFile())
			return null;
		properties.loadFromXML(new FileInputStream(file));
		return new ValueData(key, file, properties);
	}
	
	protected String giveKeyString(final KeyJAO key)
	{
		String str = this.giveKeyInduction().giveKey();
		if(key != null)
			str += "." + key.giveKey();
		return str;
	}

	@Override
	public boolean doCreateKey(final KeyJAO key) throws IOException
	{
		if(!this.giveEmptyKey(key))
			return false;
		else
		{
			final List<KeyJAO> keys = new ArrayList<KeyJAO>();
			keys.addAll(this.giveKeys());
			if(key != null)
				keys.add(key);
			String str = "";
			final WinReg reg = WinReg.giveWinReg();
			final ValueOver value = new ValueOver(Util.giveDefaultWinRegKeyJAO(), "RegDir");
			final String dir = reg.giveEmptyValue(value) ? null : reg.getValue(value);
			Util.doRegistryValueNull(dir == null, value);
			for(int i = 0; i < keys.size(); i++)
			{
				if(!str.isEmpty())
					str += ".";
				str += i > 1 ? keys.get(i).giveKey() : keys.get(i).giveKey().toUpperCase();
				if(i > 1 && !this.givePropertiesParent().containsKey(str))
				{
					int number = str.hashCode();
					final Random random = new Random();
					File file = null;
					do
					{
						number += random.nextInt();
						file = new File(dir, "$" + Integer.toHexString(number) + ".jaoreg");
					}
					while(file.isFile());
					new Properties().storeToXML(new FileOutputStream(file), str);
					this.givePropertiesParent().setProperty(str, file.getAbsolutePath());
				}
			}
			this.doSavePropertiesParent();
			return true;
		}
	}
	
	public final boolean doCreateKey() throws IOException
	{
		return this.doCreateKey(null);
	}

	@Override
	public boolean setValue(final ValueJAO value, final String content) throws IOException
	{
		this.doCreateKey(Util.doRequireNonNull(value).giveKey());
		final ValueData data = this.giveValueData(value);
		if(!this.giveEmptyValue(value) || data == null)
			return false;
		data.giveProperties().setProperty(value.giveValue(), Util.doRequireNonNull(content));
		data.giveProperties().storeToXML(new FileOutputStream(data.giveFile()), data.giveKey());
		return true;
	}

	@Override
	public String getValue(final ValueJAO value) throws IOException
	{
		if(this.giveEmptyValue(value))
			return null;
		return this.giveValueData(value).giveProperties().getProperty(value.giveValue());
	}
	
	@Override
	public boolean doDeleteKey(final KeyJAO key) throws IOException
	{
		if(this.giveEmptyKey(key))
			return false;
		else
		{
			final String keyString = this.giveKeyString(key);
			for(final String str : Util.givePropertiesToKeys(this.givePropertiesParent()))
				if(str.startsWith(keyString))
					this.givePropertiesParent().remove(str);
			this.doSavePropertiesParent();
			return true;
		}
	}
	
	public final boolean doDeleteKey() throws IOException
	{
		return this.doDeleteKey(null);
	}

	@Override
	public boolean doDeleteValue(final ValueJAO value) throws IOException
	{
		final ValueData data = this.giveValueData(value);
		if(this.giveEmptyValue(value))
			return false;
		data.giveProperties().remove(value.giveValue());
		data.giveProperties().storeToXML(new FileOutputStream(data.giveFile()), data.giveKey());
		return true;
	}

	@Override
	public boolean giveEmptyKey(final KeyJAO key)
	{
		return !this.givePropertiesParent().containsKey(this.giveKeyString(key));
	}
	
	public final boolean giveEmptyKey()
	{
		return this.giveEmptyKey(null);
	}

	@Override
	public boolean giveEmptyValue(final ValueJAO value) throws IOException
	{
		final ValueData data = this.giveValueData(Util.doRequireNonNull(value));
		if(data == null)
			return true;
		return !data.giveProperties().containsKey(value.giveValue());
	}
	
	protected final class ValueData
	{
		
		private final String key;
		private final File file;
		private final Properties properties;
		
		protected ValueData(final String key, final File file, final Properties properties)
		{
			this.key = Util.doRequireNonNull(key);
			this.file = Util.doRequireNonNull(file);
			this.properties = Util.doRequireNonNull(properties);
		}
		
		protected final String giveKey()
		{
			return this.key;
		}
		
		protected final File giveFile()
		{
			return this.file;
		}
		
		protected final Properties giveProperties()
		{
			return this.properties;
		}
		
	}
	
}
