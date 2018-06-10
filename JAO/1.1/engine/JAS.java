package javax.jao;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.JNF;
import javax.jao.script.Array;
import javax.jao.script.FileJAS;
import javax.jao.script.Qualifier;
import javax.jao.script.Type.BaseType;
import javax.jao.script.Value;
import javax.jao.script.exception.DefectScriptParse;

public class JAS extends Main
{
	
	@Override
	public boolean doRun(final String... args) throws Exception
	{
		{
			
//			final Pattern pattern = Pattern.compile("(.*?) (.*?) (.*?) (.*?)\\((.*?)\\)");
//			final Matcher matcher = pattern.matcher("method private|public t* nazwa   (int i, int j, String z)");
//			if(matcher.find())
//			{
//				System.out.println(matcher.groupCount());
//				System.out.println(matcher.group(1).trim());
//				System.out.println(matcher.group(2).trim());
//				System.out.println(BaseType.valueOfType(matcher.group(3).trim()));
//				System.out.println(matcher.group(4).trim());
//				System.out.println(matcher.group(5).trim().length()); //!
//			}
			
//			final Matcher matcher = Pattern.compile("(.*?) (.*?), ").matcher("aaa 111, bbb 222, ccc 333, ddd 444, eee 555");
//			while(matcher.find())
//			{
//				System.out.println("1:" + matcher.group(1).trim());
//				System.out.println("2:" + matcher.group(2).trim());
//			}
			
//			final String[] arg = "zzz,,lll".trim().split(",");
//			System.out.println(arg.length > 0 && arg[0].length() > 0);
			
//			final String arg = "".trim();
//			final String[] argx = arg.split(",");
//			if(argx.length > 0)
//			{
//				if(argx[0].length() > 0)
//					for(String str : argx)
//						if(str.trim().length() > 0)
//						{
//							final Matcher matcherArgument = Pattern.compile("(.*?) (.*)").matcher(str.trim());
//							if(matcherArgument.find())
//							{
//								System.out.println(matcherArgument.groupCount());
//								System.out.println(BaseType.valueOfType(matcherArgument.group(1).trim()));
//								System.out.println(matcherArgument.group(2).trim());
//							}
//						}
//						else
//							System.out.println("errorX");
//				else if(arg.length() != 0)
//					System.out.println("error");
//				else
//					System.out.println("else1");
//			}
//			else
//				System.out.println("else0");
			
//			System.out.println("@abc123".substring(1));
//			
//			final Matcher matcher = Pattern.compile("field (.*?) (.*?) (.*?)=(.*?);").matcher("field final int nazwa==x;");
//			while(matcher.find())
//			{
//				System.out.println("1:" + matcher.group(1).trim());
//				System.out.println("2:" + matcher.group(2).trim());
//				System.out.println("3:" + matcher.group(3).trim());
//				System.out.println("4:" + matcher.group(4).trim().matches("\\w+"));
//			}
			
//			String[] str = new String[]{"z", "a", "y", "x"};
//			for(String s : str)
//			{
//				if(s.equals("y"))
//					break;
//				System.out.println(s);
//			}
			
//			System.out.println(Qualifier.valueOf("CONSt".toUpperCase()));
			
//			final String line = "field final int -nazwa==xxx;";
//			final Pattern patternField = Pattern.compile("field (.*?) (.*?) (.*?)=(.*?);"), word = Pattern.compile("\\w+");
//			boolean error = false;
//			Qualifier qualifier = null;
//			BaseType type = null;
//			String name = null;
//			String value = null;
//			final Matcher matcherField = patternField.matcher(line);
//			if(matcherField.find())
//			{
//				if(matcherField.groupCount() != 4)
//					error = true;
//				if(!error)
//				{
//					qualifier = Qualifier.valueOf(matcherField.group(1).trim().toUpperCase());
//					type = BaseType.valueOfType(matcherField.group(2).trim());
//					name = matcherField.group(3).trim();
//					value = matcherField.group(4).trim();
//					if(!word.matcher(name).matches())
//						throw new DefectScriptParse(name, "The field name in the script contains invalid characters. Name").toDefectRuntime();
//				}
//				
//			}
//			else
//				error = true;
//			if(!error && (matcherField.find() || (name == null || qualifier == null || type == null)))
//				error = true;
//			if(error)
//				throw new DefectScriptParse(line).toDefectRuntime();
//			else
//				System.out.println(qualifier + ";" + type + ";" + name + ";" + value);

//			class XYZ
//			{
//				
//			}
//			class ABC extends XYZ
//			{
//				
//			}
////			System.out.println(BaseType.valueOfType("t*").giveClassType().isAssignableFrom(b.getClass()));
//			System.out.println(XYZ.class.isAssignableFrom(XYZ.class));
			
//			Object x = 1;
//			System.out.println((String) x);
			
//			boolean x = true;
//			Object y = new Object();
//			System.out.println(new Value(BaseType.STRING, y).giveValue());
			
//			String x = "123";
//			Class<?> y = Integer.class;
//			Object z = y.cast(x);
//			System.out.println(z.getClass());
			
//			System.out.println("system.125.start.maxTrimCosTam.Open2".trim().matches("[\\w\\.]+"));
//			Pattern.compile("").matcher("").
//			System.out.println("xyz".replace("[a-z]", "X"));
			
//			List<String> list = FileJAS.giveTransformations("xxx.y", "    zadanie.1:start     xxx.y    (   parametr 1   ,    par2    ,    zażółć gęślą jaźń   )   ;  ", "zadanie.1:start", ";");
//			for(String str : list)
//				System.out.println(str);
//			System.out.println(list.size());
			
//			System.out.println(".levels xxx".substring(8));
			
//			System.out.println(FileJAS.doReplace("ala ma kota, a kot ma ale.", ",./;'[]\\"));
			
			Array array = new Array(10);
//			array.set(0, "111");
//			array.set(1, "222");
//			array.set(2, "333");
//			array.set(3, "444");
//			array.set(4, "555");
			
			for(final String str : array)
				System.out.println(str);
			
			System.out.println(array.toString());
			
			System.exit(0);
			
		}
		final String file = JNF.giveFiles().loadText(args[0]);
		String script = "";
		for(final String line : file.replace('\r', '\n').split("\n"))
		{
			final String lineTrim = line.trim();
			final String[] signs = new String[]{";", "#", "//", "|", "'"};
			boolean code = true;
			for(final String sign : signs)
				if(lineTrim.startsWith(sign))
				{
					code = false;
					break;
				}
			if(code)
				script += line + '\n';
		}
		final Pattern pattern = Pattern.compile("(.*?)\\{(.*?)\\}", Pattern.DOTALL);
		final Matcher matcher = pattern.matcher(script);
		int end = 0;
		while(matcher.find())
		{
			System.out.println(":");
			String[] x = matcher.group(1).split("\n");
			if(x.length > 0)
			{
				for(int i = 0; i < x.length - 1; i++)
				{
					if(x[i].trim().length() > 0)
						System.out.println("!");
				}
				System.out.println(x[x.length - 1].trim());//!
			}
			else
				System.out.println(";");
			System.out.println(matcher.group(2).replace("\n", ""));
			end = matcher.end(2);
			System.out.println();
		}
		System.out.println(script.substring(++end, matcher.regionEnd()));
		return true;
	}
	
}
