package javax.jao;

import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;
import java.util.jar.JarFile;
import javax.jao.api.Service;
import javax.jao.registry.KeyInductionJAO;
import javax.jao.registry.KeyJAO;
import javax.jao.registry.KeyParentJAO;
import javax.jao.registry.ValueJAO;
import javax.jao.winapi.winreg.ValueOver;

public final class JAO extends Main
{
	
	@Override
	public boolean doRun(final String... args) throws Exception
	{
		final FileJAO file = new FileJAO(args[0]);
		final WinReg reg = WinReg.giveWinReg();
		final ValueOver value = new ValueOver(Util.giveDefaultWinRegKeyJAO(), "AppDir");
		final String appName = new File(file.giveFile().getName()).getName().replace('.', ',').split(",")[0].toLowerCase(), appNumber = Long.toHexString(System.currentTimeMillis());
		final File directory = reg.giveEmptyValue(value) ? null : new File(reg.getValue(value), appName + "." + appNumber);
		Util.doRegistryValueNull(directory == null, value);
		directory.mkdirs();
		final File main = new File(directory, "$" + Long.toHexString(System.nanoTime()) + ".main.jar");
		file.doExtractFile(main);
		final String[] propertyLibraries = file.giveData().getProperty("libraries", "").split(";");
		final List<URL> libraries = new ArrayList<URL>();
		libraries.add(main.toURI().toURL());
		final File directoryLibraries = new File(directory, "lib");
		directoryLibraries.mkdirs();
		for(final File component : file.giveLibraries().keySet())
		{
			final String name = component.getName();
			for(final String library : propertyLibraries)
				if(name.equalsIgnoreCase(library))
				{
					final File fileLibrary = new File(directoryLibraries, name);
					file.doExtractFile(component, fileLibrary);
					libraries.add(fileLibrary.toURI().toURL());
				}
		}
		for(final File component : file.giveAssets().keySet())
			file.doExtractFile(component, new File(directory, component.getName()));
		file.close();
		final KeyJAO keyName = new KeyJAO(appName), keyNumber = new KeyJAO(appNumber), keyDir = new KeyJAO("dir");
		Service.setRegTmp(new RegJAO(new KeyInductionJAO(KeyParentJAO.TEMP, keyName, keyNumber)));
		Service.setRegApp(new RegJAO(new KeyInductionJAO(KeyParentJAO.APPS, keyName, keyNumber)));
		Service.setRegAppDir(new RegJAO(new KeyInductionJAO(((RegJAO) Service.getRegApp()).giveKeyInduction(), keyDir)));
		Service.getRegAppDir().setValue(new ValueJAO("main"), directory.getAbsolutePath());
		Service.getRegAppDir().setValue(new ValueJAO("libraries"), directoryLibraries.getAbsolutePath());
		Service.getRegAppDir().setValue(new ValueJAO("assets"), directory.getAbsolutePath());
		Service.setData(file.giveData());
		Service.setFile(file.giveFile());
		Service.setMain(new JarFile(main));
		Service.setMainManifest(Service.getMain().getManifest().getMainAttributes());
		final URLClassLoader loader = new URLClassLoader(libraries.toArray(new URL[libraries.size()]), this.giveLoader());
		Service.setLoader(loader);
		final Class<?> clazz = loader.loadClass(file.giveData().getProperty("main.class"));
		boolean result = true;
		if(App.class.isAssignableFrom(clazz))
		{
			final String[] array = new String[args.length - 1];
			for(int i = 1; i < args.length; i++)
				array[i - 1] = args[i];
			result = ((App) clazz.newInstance()).doRun(array);
		}
		else if(Runnable.class.isAssignableFrom(clazz))
			((Runnable) clazz.newInstance()).run();
		else
			result = false;
		loader.close();
		Service.getMain().close();
		Service.getRegTmp().doDeleteKey(null);
		Service.getRegAppDir().doDeleteKey(null);
		Service.getRegApp().doDeleteKey(null);
		Util.doDeleteDirectory(directory);
		return result & super.doRun(args);
	}
	
}
