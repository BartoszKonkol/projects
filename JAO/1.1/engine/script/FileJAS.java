package javax.jao.script;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.JNF;
import javax.jao.script.Type.BaseType;
import javax.jao.script.exception.DefectScriptParse;

public final class FileJAS
{
	
	public FileJAS(final File file)
	{
		final String text = JNF.giveFiles().loadText(file);
		final String[] signs = new String[]{";", "#", "//", "|", "'"};
		String codeContent = "";
		for(final String line : text.replace('\r', '\n').split("\n"))
		{
			final String lineTrim = line.trim();
			boolean code = true;
			for(final String sign : signs)
				if(lineTrim.startsWith(sign))
				{
					code = false;
					break;
				}
			if(code)
				codeContent += line + '\n';
		}
		final List<Script> scripts = new ArrayList<Script>();
		final Matcher matcherScript = Pattern.compile("script (.*?) (.*?)\\{(.*?)\\}", Pattern.DOTALL).matcher(codeContent);
		while(matcherScript.find())
		{
			final String nameScript = matcherScript.group(1);
			final String interpreterFull = matcherScript.group(2);
			final String script = matcherScript.group(3);
			final Matcher matcherInterpreter = Pattern.compile("(.*?)\\((.*?)\\)", Pattern.DOTALL).matcher(interpreterFull);
			boolean interpreterError = !matcherInterpreter.find();
			String interpreterClass = null;
			List<String> interpreter = null;
			if(!interpreterError)
			{
				interpreterClass = matcherInterpreter.group(1);
				interpreter = giveTransformations(interpreterClass, interpreterFull);
			}
			if(interpreterClass == null || interpreter == null || matcherInterpreter.find())
				interpreterError = true;
			if(interpreterError)
				throw new DefectScriptParse(interpreterFull).toDefectRuntime();
			final Matcher matcher = Pattern.compile("(.*?)\\{(.*?)\\}", Pattern.DOTALL).matcher(script);
			final Pattern patternMethod = Pattern.compile("(.*?) (.*?) (.*?) (.*?)\\((.*?)\\)"), patternArgument = Pattern.compile("(.*?) (.*)"), patternField = Pattern.compile("field (.*?) (.*?) (.*?)=(.*?);"), word = Pattern.compile("\\w+");
			final List<Method> methods = new ArrayList<Method>();
			final List<Field> fields = new ArrayList<Field>();
			final List<String> space = new ArrayList<String>();
			int end = 0;
			while(matcher.find())
			{
				final String spaceGroup = matcher.group(1);
				final String[] spaceArray = spaceGroup.split("\n");
				if(spaceArray.length > 0)
				{
					final int point = spaceArray.length - 1;
					for(int i = 0; i < point; i++)
					{
						final String element = spaceArray[i].trim();
						if(element.length() > 0)
							space.add(element);
					}
					boolean error = false;
					boolean _public = false;
					BaseType type = null;
					String name = null;
					final List<Argument> arguments = new ArrayList<Argument>();
					final String methodData = spaceArray[point].trim();
					final Matcher matcherMethod = patternMethod.matcher(methodData);
					if(matcherMethod.find())
					{
						if(matcherMethod.groupCount() != 5)
							error = true;
						if(!error)
						{
							if(!matcherMethod.group(1).trim().equalsIgnoreCase("method"))
								error = true;
							if(!error)
							{
								switch(matcherMethod.group(2).trim().toLowerCase())
								{
									case "private":
										_public = false;
										break;
									case "public":
										_public = true;
										break;
									default:
										error = true;
								}
								if(!error)
								{
									final String typeText = matcherMethod.group(3).trim();
									if(!typeText.equalsIgnoreCase("void"))
										type = BaseType.valueOfType(typeText);
									name = matcherMethod.group(4).trim();
									if(!word.matcher(name).matches())
										throw new DefectScriptParse(name, "The method name in the script contains invalid characters. Name").toDefectRuntime();
									final String arg = matcherMethod.group(5).trim();
									final String[] args = arg.split(",");
									if(args.length > 0)
									{
										if(args[0].length() > 0)
											for(final String str : args)
											{
												final String strTrim = str.trim();
												if(strTrim.length() > 0)
												{
													if(error)
														break;
													BaseType typeArgument = null;
													String nameArgument = null;
													final Matcher matcherArgument = patternArgument.matcher(strTrim);
													if(matcherArgument.find())
													{
														if(matcherArgument.groupCount() != 2)
															error = true;
														if(!error)
														{
															typeArgument = BaseType.valueOfType(matcherArgument.group(1).trim());
															nameArgument = matcherArgument.group(2).trim();
															if(!word.matcher(nameArgument).matches())
																throw new DefectScriptParse(name, "The argument name in the method in the script contains invalid characters. Name").toDefectRuntime();
														}
													}
													else
														error = true;
													if(!error && (matcherArgument.find() || (nameArgument == null || typeArgument == null)))
														error = true;
													if(!error)
														arguments.add(new Argument(nameArgument, typeArgument));
												}
												else
													error = true;
											}
										else if(arg.length() != 0)
											error = true;
									}
								}
							}
						}
					}
					else
						error = true;
					if(!error && matcherMethod.find())
						error = true;
					if(error || name == null)
						throw new DefectScriptParse(methodData).toDefectRuntime();
					methods.add(new Method(name, _public, type, arguments, matcher.group(2)));
				}
				else
					throw new DefectScriptParse(spaceGroup.replace('\n', '\u0020'), "Error loading. Content of space between methods in the script is empty. Code").toDefectRuntime();
				end = matcher.end(2);
			}
			for(final String spaceExternal : script.substring(++end, matcher.regionEnd()).split("\n"))
			{
				final String element = spaceExternal.trim();
				if(element.length() > 0)
					space.add(element);
			}
			for(int i = 0; i < space.size(); i++)
			{
				final String line = space.get(i);
				if(line.startsWith("@"))
				{
					boolean error = false;
					final String extortion = line.substring(1).toLowerCase();
					if(extortion.equals("break"))
						break;
					else if(extortion.startsWith("skip"))
					{
						if(extortion.length() == 4)
							i++;
						else if(extortion.length() > 5 && extortion.charAt(4) == '\u003a')
							i = i + Integer.valueOf(extortion.substring(5));
						else
							error = true;
					}
					else
						error = true;
					if(error)
						throw new DefectScriptParse(line, "Unknown type of extortion in the script code").toDefectRuntime();
				}
				else
				{
					boolean error = false;
					Qualifier qualifier = null;
					BaseType type = null;
					String name = null;
					String value = null;
					final Matcher matcherField = patternField.matcher(line);
					if(matcherField.find())
					{
						if(matcherField.groupCount() != 4)
							error = true;
						if(!error)
						{
							qualifier = Qualifier.valueOf(matcherField.group(1).trim().toUpperCase());
							type = BaseType.valueOfType(matcherField.group(2).trim());
							name = matcherField.group(3).trim();
							value = matcherField.group(4).trim();
							if(!word.matcher(name).matches())
								throw new DefectScriptParse(name, "The field name in the script contains invalid characters. Name").toDefectRuntime();
						}
						
					}
					else
						error = true;
					if(!error && (matcherField.find() || (name == null || qualifier == null || type == null || value == null || value.length() <= 0)))
						error = true;
					if(error)
						throw new DefectScriptParse(line).toDefectRuntime();
					final Field field = new Field(name, qualifier, type);
					Object objValue = null;
					if(value.equalsIgnoreCase("nul") || value.equalsIgnoreCase("null"))
						objValue = new Object();
					else
					{
						final Class<?> typeClass = type.getClass();
						for(final BaseType baseType : BaseType.values())
							if(baseType.getClass().isAssignableFrom(typeClass))
								switch(baseType)
								{
									case ARRAY:
										{
											final List<String> list = giveTransformations(baseType.giveType(), value);
											if(list.size() <= 0)
												break;
											final Array array = new Array(Integer.valueOf(list.get(0)));
											for(int j = 1; j < list.size(); j++)
												array.set(j - 1, list.get(j));
										} break;
									case BOOLEAN:
										objValue = Boolean.parseBoolean(value);
										break;
									case BYTE:
										objValue = Byte.parseByte(value);
										break;
									case DOUBLE:
										objValue = Double.parseDouble(value);
										break;
									case FLOAT:
										objValue = Float.parseFloat(value);
										break;
									case INTEGER:
										objValue = Integer.parseInt(value);
										break;
									case LIST:
										{
											final List<String> objList = new ArrayList<String>();
											for(final String str : giveTransformations(baseType.giveType(), value))
											{
												final String strTrim = str.trim();
												if(strTrim.length() > 0)
													objList.add(strTrim);
											}
											objValue = new ArrayList<String>(objList);
										} break;
									case LONG:
										objValue = Long.parseLong(value);
										break;
									case MAP:
										{
											final Map<String, String> objMap = new HashMap<String, String>();
											final List<String> list = giveTransformations(baseType.giveType(), value);
											final int size = list.size();
											if(size % 2 != 0)
												break;
											for(int j = 0; j < size; j = j + 2)
											{
												final String keyMap = list.get(j).trim(), valueMap = list.get(j + 1).trim();
												if(keyMap.length() > 0 && valueMap.length() > 0)
													objMap.put(keyMap, valueMap);
											}
											objValue = new HashMap<String, String>(objMap);
										} break;
									case RANDOM:
										{
											final List<String> list = giveTransformations(baseType.giveType(), value);
											switch(list.size())
											{
												case 0:
													objValue = new Random();
													break;
												case 1:
													objValue = new Random(Integer.valueOf(list.get(0)));
													break;
											}
										} break;
									case SHORT:
										objValue = Short.parseShort(value);
										break;
									case STRING:
										objValue = value;
										break;
								}
					}
					field.setValue(new Value<Object>(type, objValue));
					fields.add(field);
				}
			}
			scripts.add(new Script());
		}
		
	}
	
	public static final List<String> giveTransformations(final String indicator, final String content)
	{
		return giveTransformations(indicator, content, "");
	}
	
	public static final List<String> giveTransformations(final String indicator, final String content, final String postfix)
	{
		return giveTransformations(indicator, content, "", postfix);
	}
	
	public static final List<String> giveTransformations(final String indicator, final String content, final String prefix, final String postfix)
	{
		final List<String> result = new ArrayList<String>();
		final String executor = "*+-.?^", indicatorTrim = indicator.trim(), prefixRegex = "(.*?)" + doReplace(prefix, executor) + " *?", postfixRegex = doReplace(postfix, executor) + "(.*)", regex = "[\\w\\.]+", regexFix = "[\\w!\\*\\+\\-\\.:;\\?\\^_]+";
		if(indicatorTrim.matches(regex) && prefix.trim().matches(regexFix) && postfix.trim().matches(regexFix))
		{
			final Matcher matcher = Pattern.compile((prefixRegex + indicatorTrim.replace(".", "\\.") + " *?\\((.*?)\\) *?" + postfixRegex).trim()).matcher(content.trim().toLowerCase());
			if(matcher.find())
			{
				final String[] args = matcher.group(2).trim().split(",");
				if(args.length > 0 && args[0].length() > 0 && matcher.group(1).trim().length() == 0 && matcher.group(3).trim().length() == 0)
				{
					for(final String arg : args)
					{
						final String argTrim = arg.trim();
						if(argTrim.length() > 0)
							result.add(argTrim);
					}
					if(!matcher.find())
						return Collections.unmodifiableList(result);
				}
			}
		}
		return null;
	}
	
	public static final String doReplace(final String target, final String replacement)
	{
		String result = target;
		char[] array = new char[replacement.length()];
		replacement.getChars(0, array.length, array, 0);
		for(final Character c : array)
			result = result.replace(c.toString(), "\\" + c);
		return result;
		
	}
	
}
