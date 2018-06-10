package com.digitalpgs.explory;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.RenderedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.TreeSet;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
import javax.imageio.ImageIO;
import javax.imageio.stream.MemoryCacheImageInputStream;
import javax.imageio.stream.MemoryCacheImageOutputStream;
import org.imgscalr.Scalr;
import net.polishgames.vis2.server.api.MultipartElement;
import net.polishgames.vis2.server.api.RequestHTTP;
import net.polishgames.vis2.server.api.ResponseHTTP;
import net.polishgames.vis2.server.jweb.SpecialGroup;
import net.polishgames.vis2.server.jweb.api.BasicGroup;
import net.polishgames.vis2.server.jweb.api.Plugin;
import net.polishgames.vis2.server.jweb.jws.IScript;
import net.polishgames.vis2.server.jweb.jws.JWebScript;
import net.polishgames.vis2.server.jweb.jws.operation.EventJWS;
import net.polishgames.vis2.server.jweb.jws.operation.ImportJWS;
import net.polishgames.vis2.server.jweb.jws.operation.OperationJWS;
import net.polishgames.vis2.server.jweb.jws.parameter.NullJWS;
import net.polishgames.vis2.server.jweb.jws.parameter.ParameterJWS;
import net.polishgames.vis2.server.jweb.jws.parameter.StringJWS;

public class Explory extends Plugin
{
	
	protected Charset charset;
	protected File tmpdir;
	protected Map<String, Map<Integer, byte[]>> steps;
	protected Map<String, Map<String, Integer>> percents;
	
	@Override
	public void onLoad() throws Exception
	{
		this.charset = this.hasJWeb() ? this.getJWeb().giveCharset() : Charset.forName("UTF-8");
		this.tmpdir = new File(System.getProperty("java.io.tmpdir"));
		this.steps = new HashMap<String, Map<Integer, byte[]>>();
		this.percents = new HashMap<String, Map<String, Integer>>();
	}
	
	@Override
	public boolean onWebsite(final BasicGroup group, final Map<String, Object> server, final String resource) throws Exception
	{
		boolean interrupt = false;
		final String id = String.valueOf(server.get("ID"));
		final File workspace = new File(group.hasWorkSpace() ? group.getWorkSpace() : new File(this.hasJWeb() ? this.getJWeb().giveDumps() : this.tmpdir, "explory"), "leafprint"), details = new File(workspace, "details");
		details.mkdirs();
		final RequestHTTP request = group.giveRequest();
		final ResponseHTTP response = group.getResponse();
		final boolean get = request.giveMethod().equalsIgnoreCase("get"), post = request.giveMethod().equalsIgnoreCase("post");
		if(resource.startsWith("/jweb/explory/form/"))
		{
			boolean error = false;
			String page = resource.split("/", 5)[4];
			switch(page)
			{
				case "2":
				{
					if(post && request.hasMultipart())
					{
						InputStream photo = null;
						for(final MultipartElement multipart : request.giveMultipart())
							if(multipart.giveDisposition().getProperty("name").equals("photo"))
							{
								final byte[] content = multipart.giveContent();
								if(content.length > 0)
									photo = new ByteArrayInputStream(content);
								break;
							}
						if(photo != null)
						{
							final Map<Integer, byte[]> leafprint = this.giveLeafPrint(photo);
							final BufferedImage image = this.doReadImage(leafprint.get(leafprint.size() - 1));
							final Map<String, Integer> percents = new HashMap<String, Integer>();
							for(final File file : workspace.listFiles())
							{
								if(file.isFile())
								{
									final String[] name = file.getName().split("\\.", 2);
									if(name.length == 2 && name[1].equalsIgnoreCase("dat"))
									{
										final String leafID = name[0];
										Map<String, Object> map = null;
										try
										{
											map = this.doLoadFile(leafID, workspace);
										}
										catch(final IOException e){}
										if(map != null)
										{
											final int width = image.getWidth(), height = image.getHeight();
											float level = 0;
											final BufferedImage leaf = this.doReadImage((byte[]) map.get("leaf"));
											for(int x = 0; x < width; x++)
												for(int y = 0; y < height; y++)
													if(image.getRGB(x, y) == leaf.getRGB(x, y))
														level++;
											percents.put((String) map.get("refer"), Math.round(level / (width * height) * 100));
										}
									}
								}
							}
							this.steps.put(id, leafprint);
							this.percents.put(id, percents);
						}
						else
							error |= true;
					}
				} break;
				case "3":
				{
					if(post)
					{
						InputStream photo = null;
						final Properties properties = new Properties();
						if(request.hasMultipart())
							for(final MultipartElement multipart : request.giveMultipart())
							{
								final String name = multipart.giveDisposition().getProperty("name");
								if(name != null && !name.isEmpty())
								{
									final byte[] content = multipart.giveContent();
									if(content.length > 0)
									{
										if(name.equals("photo"))
											photo = new ByteArrayInputStream(content);
										else
											properties.setProperty(name, new String(content, this.charset));
									}
								}
							}
						if(photo != null && !properties.isEmpty())
						{
							final Map<Integer, byte[]> leafprint = this.giveLeafPrint(photo);
							this.doSaveFile(id, details, null, leafprint.get(0), leafprint.get(leafprint.size() - 1), properties);
							this.doSaveFile(this.getJWeb().addID(new Date()), workspace, id, null, leafprint.get(leafprint.size() - 1), null);
							this.steps.put(id, leafprint);
						}
						else
							error |= true;
					}
				} break;
				default:
					error |= true;
			}
			if(error)
				page = "error.form";
			response.giveStatusCode().set(303, "See Other");
			response.giveHeader().setProperty("Location", "http://localhost/jweb/explory/?page=" + page + "&id=" + id);
			interrupt |= true;
		}
		else if(resource.contains("/jweb/explory/photo/"))
		{
			final String stepsID = resource.split("/", 5)[4];
			boolean beyond = false;
			if(this.steps.containsKey(stepsID))
			{
				final  Map<Integer, byte[]> photo = this.steps.get(stepsID);
				int step = 0;
				if(get && request.hasContent())
				{
					final Properties content = request.giveContent();
					if(content.containsKey("step"))
						step = Integer.valueOf(content.getProperty("step"));
					else
						beyond = true;
				}
				else
					beyond = true;
				if(photo.containsKey(step))
				{
					response.setContent(photo.get(step));
					photo.remove(step);
					if(photo.size() == 0)
						this.steps.remove(stepsID);
				}
				else
					beyond = true;
			}
			else
				beyond = true;
			if(beyond)
				response.setContent((byte[]) this.doLoadFile(stepsID, details).get("init"));
			response.giveHeader().setProperty("Content-Type", "image/png");
			interrupt = true;
		}
		else if(resource.contains("/jweb/explory/"))
		{
			String page = null;
			String stepsID = null;
			Map<Integer, byte[]> steps = null;
			Map<String, Integer> percents = null;
			if(get && request.hasContent())
			{
				final Properties content = request.giveContent();
				if(content.containsKey("page"))
				{
					page = content.getProperty("page");
					if(content.containsKey("id"))
					{
						stepsID = content.getProperty("id");
						if(stepsID != null && !stepsID.isEmpty())
						{
							if(this.steps.containsKey(stepsID))
								steps = this.steps.get(stepsID);
							if(this.percents.containsKey(stepsID))
								percents = this.percents.get(stepsID);
						}
					}
				}
			}
			Function<ParameterJWS[], ParameterJWS> event = (parameters) ->
			{
				if(parameters.length == 1)
					return parameters[0];
				else
					return NullJWS.NULL;
			};
			if(page != null && !page.isEmpty())
			{
				if(page.startsWith("details."))
				{
					final String detailsID = page.split("\\.", 2)[1];
					Map<String, Object> file = null;
					try
					{
						file = this.doLoadFile(detailsID, details);
					}
					catch(final FileNotFoundException e){}
					final Properties data = file != null ? (Properties) file.get("data") : null;
					event = (parameters) ->
					{
						if(data != null)
						{
							final IScript jws = (IScript) group.giveSearch(IScript.class);
							if(jws != null)
							{
								for(final OperationJWS operation : jws.giveOperations())
									if(operation instanceof ImportJWS)
									{
										final ImportJWS importJWS = (ImportJWS) operation;
										if(jws.hasGroup())
										{
											final Properties phrases = jws.getGroup().givePhrases();
											phrases.setProperty("id", detailsID);
											phrases.setProperty("name", data.getProperty("namepl"));
											phrases.setProperty("google", data.getProperty("google"));
											phrases.setProperty("wiki", data.getProperty("wiki"));
											phrases.setProperty("desc", data.getProperty("description"));
											phrases.setProperty("latin", data.getProperty("latin_name_1") + " " + data.getProperty("latin_name_2"));
											return this.giveImportFTP(importJWS, "details");
										}
										break;
									}
							}
						}
						return new StringJWS("<div class=\"text-center\">Brak szczegó³ów dotycz¹cych okreœlonego kodu roœliny</div>");
					};
				}
				else
				{
					switch(page)
					{
						case "2":
						{
							if(steps != null && !steps.isEmpty() && percents != null && !percents.isEmpty())
							{
								final String stepsFinalID = stepsID;
								final Map<Integer, byte[]> stepsFinal = steps;
								final Map<String, Integer> percentsFinal = percents;
								event = (parameters) ->
								{
									final IScript jws = (IScript) group.giveSearch(IScript.class);
									if(jws != null)
									{
										for(final OperationJWS operation : jws.giveOperations())
											if(operation instanceof ImportJWS)
											{
												final ImportJWS importJWS = (ImportJWS) operation;
												if(jws.hasGroup())
												{
													final Properties phrases = jws.getGroup().givePhrases();
													phrases.setProperty("id", stepsFinalID);
													final Map<Integer, List<String>> cardsMap = new HashMap<Integer, List<String>>();
													String photos = "", cards = "";
													for(int i = 0; i < stepsFinal.size(); i++)
													{
														final String step = String.valueOf(i);
														phrases.setProperty("name", step);
														phrases.setProperty("step", step);
														photos += this.giveImportFTP(importJWS, "photo");
													}
													for(final String leafID : percentsFinal.keySet())
													{
														final int percent = percentsFinal.get(leafID);
														if(percent > 50)
														{
															Map<String, Object> file = null;
															try
															{
																file = this.doLoadFile(leafID, details);
															}
															catch(final IOException e){}
															if(file != null)
															{
																final Properties data = (Properties) file.get("data");
																phrases.setProperty("id", leafID);
																phrases.setProperty("name", data.getProperty("namepl"));
																phrases.setProperty("google", data.getProperty("google"));
																phrases.setProperty("wikipedia", data.getProperty("wiki"));
																phrases.setProperty("percent", String.valueOf(percent));
																phrases.setProperty("color", percent < 75 ? "danger" : percent < 90 ? "warning" : "success");
																if(!cardsMap.containsKey(percent))
																	cardsMap.put(percent, new ArrayList<String>());
																cardsMap.get(percent).add(this.giveImportFTP(importJWS, "card.2").toString());
															}
														}
													}
													for(final int percent : new TreeSet<Integer>(cardsMap.keySet()))
														for(final String card : cardsMap.get(percent))
															cards = card + cards;
													phrases.setProperty("photos", photos);
													phrases.setProperty("cards", cards);
												}
												return this.giveImportFTP(importJWS, "success");
											}
									}
									return new StringJWS("<div class=\"text-center\">Brak szczegó³ów dotycz¹cych okreœlonego kodu porównywania</div>");
								};
							}
						} break;
						case "3":
						{
							if(steps != null && !steps.isEmpty())
							{
								final Map<String, Object> file = this.doLoadFile(stepsID, details);
								final Properties data = (Properties) file.get("data");
								final String stepsFinalID = stepsID;
								final Map<Integer, byte[]> stepsFinal = steps;
								event = (parameters) ->
								{
									final IScript jws = (IScript) group.giveSearch(IScript.class);
									if(jws != null)
									{
										for(final OperationJWS operation : jws.giveOperations())
											if(operation instanceof ImportJWS)
											{
												final ImportJWS importJWS = (ImportJWS) operation;
												if(jws.hasGroup())
												{
													final Properties phrases = jws.getGroup().givePhrases();
													phrases.setProperty("id", stepsFinalID);
													phrases.setProperty("name", data.getProperty("namepl"));
													phrases.setProperty("google", data.getProperty("google"));
													phrases.setProperty("wikipedia", data.getProperty("wiki"));
													String photos = "";
													for(int i = 0; i < stepsFinal.size(); i++)
													{
														phrases.setProperty("step", String.valueOf(i));
														photos += this.giveImportFTP(importJWS, "photo");
													}
													phrases.setProperty("photos", photos);
													phrases.setProperty("cards", this.giveImportFTP(importJWS, "card").toString());
												}
												return this.giveImportFTP(importJWS, "success");
											}
									}
									return new StringJWS("<div class=\"text-center\">Formularz zosta³ z powodzeniem dodany do bazy danych<br>Jednak¿e strona podsumowania nie mo¿e zostaæ wyœwietlona z powodu b³êdu</div>");
								};
							}
						} break;
						case "4":
						{
							Function<ParameterJWS[], ParameterJWS> eventFinal = event;
							event = (parameters) ->
							{
								final IScript jws = (IScript) group.giveSearch(IScript.class);
								if(jws != null)
								{
									for(final OperationJWS operation : jws.giveOperations())
										if(operation instanceof ImportJWS)
										{
											final ImportJWS importJWS = (ImportJWS) operation;
											if(jws.hasGroup())
											{
												String cards = "";
												final Properties phrases = jws.getGroup().givePhrases();
												for(final File file : details.listFiles())
												{
													if(file.isFile())
													{
														final String[] name = file.getName().split("\\.", 2);
														if(name.length == 2 && name[1].equalsIgnoreCase("dat"))
														{
															Map<String, Object> map = null;
															try
															{
																map = this.doLoadFile(name[0], details);
															}
															catch(final IOException e){}
															if(map != null)
															{
																final Properties data = (Properties) map.get("data");
																phrases.setProperty("id", name[0]);
																phrases.setProperty("name", data.getProperty("namepl"));
																phrases.setProperty("google", data.getProperty("google"));
																phrases.setProperty("wikipedia", data.getProperty("wiki"));
																cards += this.giveImportFTP(importJWS, "card");
															}
														}
													}
												}
												group.givePhrases().setProperty("cards", cards);
											}
											break;
										}
								}
								return eventFinal.apply(parameters);
							};
						} break;
					}
				}
			}
			final Function<ParameterJWS[], ParameterJWS> eventFinal = event;
			final Consumer<OperationJWS> execution = (operation) ->
			{
				if(operation instanceof EventJWS)
					((EventJWS) operation).addEvent("subpage", eventFinal);
			};
			IScript jws = (IScript) group.giveSearch(IScript.class);
			if(jws == null)
			{
				jws = new JWebScript().setGroup(group);
				for(final Function<IScript, OperationJWS> function : this.getJWeb().giveOperations())
				{
					final OperationJWS operation = function.apply(jws);
					execution.accept(operation);
					jws.addOperation(operation);
				}
				if(group instanceof SpecialGroup)
					((SpecialGroup) group).setJWS(jws);
				if(this.getJWeb() instanceof net.polishgames.vis2.server.jweb.JWeb)
					((net.polishgames.vis2.server.jweb.JWeb) this.getJWeb()).doAdjustGlobalVariables(request, server, null, null, null, jws);
			}
			else
				for(final OperationJWS operation : jws.giveOperations())
					execution.accept(operation);
		}
		
		return interrupt;
	}
	
	protected Map<Integer, byte[]> giveLeafPrint(final InputStream photo) throws IOException
	{
		final Map<Integer, byte[]> map = new HashMap<Integer, byte[]>();
		final BufferedImage image = this.doReadImage(photo);
		this.doInsertImage(map, image);
		final int width = image.getWidth(), height = image.getHeight(), centerWidth = width / 5, centerHeight = height / 5;
		final Set<Integer> centerColors = new TreeSet<Integer>();
		this.doSearchPixels(centerWidth * 2, centerHeight * 2, centerWidth * 3, centerHeight * 3, (x, y) ->
		{
			centerColors.add(this.giveColorRound(image, x, y).getRGB());
		});
		this.doSearchPixels(width, height, (x, y) ->
		{
			if(!centerColors.contains(this.giveColorRound(image, x, y).getRGB()))
				image.setRGB(x, y, Color.WHITE.getRGB());
		});
		this.doInsertImage(map, image);
		this.doSearchPixels(width, height, (x, y) ->
		{
			final Color color = this.giveColor(image, x, y);
			final int intensity = (color.getRed() + color.getGreen() + color.getBlue()) / 3;
			image.setRGB(x, y, new Color(intensity, intensity, intensity).getRGB());
		});
		this.doInsertImage(map, image);
		final BufferedImage easel = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		this.doSearchPixels(width, height, (x, y) ->
		{
			final float level = this.giveColorLevel(image, x, y);
			if(level > 0 && level < 10)
				easel.setRGB(x, y, new Color(0, 255 - Math.round(level * 2.55F), 0).getRGB());
			else if(level < 0 && level > -10)
				easel.setRGB(x, y, new Color(255 - Math.round(-level * 2.55F), 0, 0).getRGB());
			else
				easel.setRGB(x, y, Color.WHITE.getRGB());
		});
		image.flush();
		this.doInsertImage(map, easel);
		final BufferedImage scale = Scalr.resize(easel, width / 8, height / 8);
		easel.flush();
		this.doInsertImage(map, scale);
		this.doBlurImage(scale);
		this.doInsertImage(map, scale);
		BufferedImage outline = scale;
		for(int i = 60; i <= 80; i += 20)
		{
			final BufferedImage smooth = this.giveImageSmooth(outline, i);
			outline.flush();
			outline = smooth;
			this.doInsertImage(map, outline);
		}
		final BufferedImage trimmed = this.giveTrimmedImage(outline);
		outline.flush();
		this.doInsertImage(map, trimmed);
		final int trimmedWidth = trimmed.getWidth(), trimmedHeight = trimmed.getHeight(), size = Math.max(trimmedWidth, trimmedHeight);
		final BufferedImage cloth = new BufferedImage(size, size, BufferedImage.TYPE_INT_RGB);
		final Graphics2D graphics = cloth.createGraphics();
		graphics.setBackground(Color.WHITE);
		graphics.clearRect(0, 0, size, size);
		graphics.drawImage(trimmed, (size - trimmedWidth) / 2, (size - trimmedHeight) / 2, null);
		graphics.dispose();
		trimmed.flush();
		this.doInsertImage(map, cloth);
		final BufferedImage adapt = Scalr.resize(cloth, 256, 256);
		cloth.flush();
		this.doInsertImage(map, adapt);
		this.doBlurImage(adapt);
		this.doInsertImage(map, adapt);
		adapt.flush();
		return map;
	}
	
	protected Color giveColor(final BufferedImage image, final int x, final int y)
	{
		return new Color(image.getRGB(x, y));
	}
	
	protected Color giveColorRound(final BufferedImage image, final int x, final int y)
	{
		return this.giveColorRound(this.giveColor(image, x, y));
	}
	
	protected Color giveColorRound(final Color color)
	{
		return new Color(Math.round(color.getRed() / 16F), Math.round(color.getGreen() / 16F), Math.round(color.getBlue() / 16F));
	}
	
	protected float giveColorPercent(final Color color)
	{
		return (color.getRed() + color.getGreen() + color.getBlue()) / 7.65F;
	}
	
	protected BufferedImage giveTrimmedImage(final BufferedImage image)
	{
		final int width = image.getWidth(), height = image.getHeight();
		int xu = 0, xd = width - 1, yu = 0, yd = height - 1;
		while(xu < xd)
		{
			int value = 0;
			for(int y = 0; y < height; y++)
				if(image.getRGB(xu, y) != -1)
					value++;
			if(value * 100 < width)
				xu++;
			else
				break;
		}
		while(xd > xu)
		{
			int value = 0;
			for(int y = 0; y < height; y++)
				if(image.getRGB(xd, y) != -1)
					value++;
			if(value * 100 < width)
				xd--;
			else
				break;
		}
		while(yu < yd)
		{
			int value = 0;
			for(int x = 0; x < width; x++)
				if(image.getRGB(x, yu) != -1)
					value++;
			if(value * 100 < width)
				yu++;
			else
				break;
		}
		while(yd > yu)
		{
			int value = 0;
			for(int x = 0; x < width; x++)
				if(image.getRGB(x, yd) != -1)
					value++;
			if(value * 100 < width)
				yd--;
			else
				break;
		}
		return image.getSubimage(xu, yu, xd - xu + 1, yd - yu + 1);
	}
	
	protected float giveColorLevel(final BufferedImage image, final int x, final int y, final boolean average)
	{
		final int width = image.getWidth(), height = image.getHeight();
		final float color = this.giveColorPercent(this.giveColor(image, x, y));
		final List<Color> points = new ArrayList<Color>();
		if(x > 0)
			points.add(this.giveColor(image, x - 1, y));
		if(x < width - 1)
			points.add(this.giveColor(image, x + 1, y));
		if(y > 0)
			points.add(this.giveColor(image, x, y - 1));
		if(y < height - 1)
			points.add(this.giveColor(image, x, y + 1));
		if(x > 0 && y > 0)
			points.add(this.giveColor(image, x - 1, y - 1));
		if(x > 0 && y < height - 1)
			points.add(this.giveColor(image, x - 1, y + 1));
		if(x < width - 1 && y > 0)
			points.add(this.giveColor(image, x + 1, y - 1));
		if(x < width - 1 && y < height - 1)
			points.add(this.giveColor(image, x + 1, y + 1));
		int amount = 0;
		float level = 0F;
		for(final Color point : points)
		{
			final float value = this.giveColorPercent(point);
			if(color > value)
				level += color - value;
			else if(color < value)
				level -= value - color;
			else
				continue;
			amount++;
		}
		return average ? (amount == 0 ? 0 : level / amount) : level;
	}
	
	protected float giveColorLevel(final BufferedImage image, final int x, final int y)
	{
		return this.giveColorLevel(image, x, y, true);
	}
	
	protected BufferedImage giveImageSmooth(final BufferedImage image, final int percent)
	{
		final ColorModel model = image.getColorModel();
		final BufferedImage smooth = new BufferedImage(model, image.copyData(null), model.isAlphaPremultiplied(), null);
		this.doSearchPixels(smooth, (x, y) ->
		{
			final float level = this.giveColorLevel(image, x, y, false) / 8;
			if(level > percent)
				smooth.setRGB(x, y, Color.BLACK.getRGB());
			else if(level < -percent)
				smooth.setRGB(x, y, Color.WHITE.getRGB());
		});
		return smooth;
	}
	
	protected byte[] giveImageBytes(final RenderedImage image) throws IOException
	{
		final ByteArrayOutputStream stream = new ByteArrayOutputStream();
		this.doWriteImage(image, stream);
		return stream.toByteArray();
	}
	
	protected ParameterJWS giveImportFTP(final ImportJWS operation, final String name)
	{
		return operation.doExecute(new StringJWS("explory/" + name +".jw"));
	}
	
	protected BufferedImage doReadImage(final InputStream stream) throws IOException
	{
		return ImageIO.read(new MemoryCacheImageInputStream(stream));
	}
	
	protected BufferedImage doReadImage(final byte[] bytes) throws IOException
	{
		return this.doReadImage(new ByteArrayInputStream(bytes));
	}
	
	protected boolean doWriteImage(final RenderedImage image, final OutputStream stream) throws IOException
	{
		return ImageIO.write(image, "png", new MemoryCacheImageOutputStream(stream));
	}
	
	protected boolean doWriteImage(final RenderedImage image, final File file) throws IOException
	{
		return ImageIO.write(image, "png", file);
	}
	
	protected void doInsertImage(final Map<Integer, byte[]> map, final RenderedImage image) throws IOException
	{
		map.put(map.size(), this.giveImageBytes(image));
	}
	
	protected void doBlurImage(final BufferedImage image)
	{
		this.doSearchPixels(image, (x, y) ->
		{
			if(image.getRGB(x, y) != Color.WHITE.getRGB())
				image.setRGB(x, y, Color.BLACK.getRGB());
		});
	}
	
	protected void doSearchPixels(final int x, final int y, final int width, final int height, final BiConsumer<Integer, Integer> consumer)
	{
		for(int xn = x; xn < width; xn++)
			for(int yn = y; yn < height; yn++)
				consumer.accept(xn, yn);
	}
	
	protected void doSearchPixels(final int width, final int height, final BiConsumer<Integer, Integer> consumer)
	{
		this.doSearchPixels(0, 0, width, height, consumer);
	}
	
	protected void doSearchPixels(final RenderedImage image, final BiConsumer<Integer, Integer> consumer)
	{
		this.doSearchPixels(image.getWidth(), image.getHeight(), consumer);
	}
	
	protected void doSaveFile(final String id, final File workspace, final String refer, final byte[] init, final byte[] leaf, final Properties data) throws IOException
	{
		String scope = "";
		if(refer != null && !refer.isEmpty())
			scope += "r";
		if(init != null && init.length > 0)
			scope += "i";
		if(leaf != null && leaf.length > 0)
			scope += "l";
		if(data != null && !data.isEmpty())
			scope += "d";
		final OutputStream file = new FileOutputStream(new File(workspace, id + ".dat"));
		final ObjectOutputStream stream = new ObjectOutputStream(file);
		stream.writeUTF(id);
		stream.writeLong(System.currentTimeMillis());
		stream.writeUTF(scope);
		if(scope.contains("r"))
		{
			stream.writeChar('r');
			stream.writeUTF(refer);
		}
		if(scope.contains("i"))
		{
			stream.writeChar('i');
			stream.writeObject(init);
		}
		if(scope.contains("l"))
		{
			stream.writeChar('l');
			stream.writeObject(leaf);
		}
		if(scope.contains("d"))
		{
			stream.writeChar('d');
			final Writer writer = new StringWriter();
			data.store(writer, id);
			stream.writeUTF(writer.toString());
			writer.close();
		}
		stream.writeChar('\0');
		stream.close();
	}
	
	protected Map<String, Object> doLoadFile(final String id, final File workspace) throws IOException
	{
		final Map<String, Object> map = new HashMap<String, Object>();
		final InputStream file = new FileInputStream(new File(workspace, id + ".dat"));
		final ObjectInputStream stream = new ObjectInputStream(file);
		if(stream.readUTF().equals(id))
		{
			map.put("id", id);
			map.put("time", stream.readLong());
			final String scope = stream.readUTF();
			if(scope.contains("r") && stream.readChar() == 'r')
				map.put("refer", stream.readUTF());
			if(scope.contains("i") && stream.readChar() == 'i')
				try
				{
					map.put("init", stream.readObject());
				}
				catch(final ClassNotFoundException e)
				{
					e.printStackTrace();
				}
			if(scope.contains("l") && stream.readChar() == 'l')
				try
				{
					map.put("leaf", stream.readObject());
				}
				catch(final ClassNotFoundException e)
				{
					e.printStackTrace();
				}
			if(scope.contains("d") && stream.readChar() == 'd')
			{
				final Properties data = new Properties();
				final Reader reader = new StringReader(stream.readUTF());
				data.load(reader);
				reader.close();
				map.put("data", data);
			}
		}
		map.put("correct", stream.readChar() == '\0');
		stream.close();
		return map;
	}
	
}
