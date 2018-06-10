package com.digitalpgs.nanokarrin.mp3md;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.function.Consumer;
import javax.imageio.ImageIO;
import javax.imageio.stream.MemoryCacheImageInputStream;
import javax.imageio.stream.MemoryCacheImageOutputStream;
import org.imgscalr.Scalr;
import com.mpatric.mp3agic.AbstractID3v2Tag;
import com.mpatric.mp3agic.BufferTools;
import com.mpatric.mp3agic.EncodedText;
import com.mpatric.mp3agic.ID3v1;
import com.mpatric.mp3agic.ID3v1Tag;
import com.mpatric.mp3agic.ID3v2;
import com.mpatric.mp3agic.ID3v23Tag;
import com.mpatric.mp3agic.ID3v2CommentFrameData;
import com.mpatric.mp3agic.ID3v2Frame;
import com.mpatric.mp3agic.ID3v2FrameSet;
import com.mpatric.mp3agic.Mp3File;
import net.polishgames.vis2.server.api.MultipartElement;
import net.polishgames.vis2.server.api.RequestHTTP;
import net.polishgames.vis2.server.api.ResponseHTTP;
import net.polishgames.vis2.server.jweb.api.BasicGroup;
import net.polishgames.vis2.server.jweb.api.Plugin;

public final class MP3MD extends Plugin
{
	
	@Override
	public boolean onWebsite(final BasicGroup group, final Map<String, Object> server, final String resource)
	{
		final RequestHTTP request = group.giveRequest();
		try
		{
			if(request.giveMethod().equalsIgnoreCase("POST") && request.hasMultipart() && !request.giveMultipart().isEmpty())
			{
				final String sender = request.giveSocketData().giveAddress().getHostAddress();
				System.out.println("Connected with " + sender);
				final Charset charset = this.hasJWeb() ? this.getJWeb().giveCharset() : Charset.forName("UTF-8");
				final BufferedImage logo = ImageIO.read(new URL("http://nanokarrin.digitalpgs.com/automatyzacja/mp3md/logo.png"));
				final File directory = new File(group.hasWorkSpace() ? group.getWorkSpace() : this.hasJWeb() ? this.getJWeb().giveDumps() : new File(System.getProperty("java.io.tmpdir")), "/nanokarrin/mp3md/");
				final ResponseHTTP response = group.getResponse();
				final Map<String, MultipartElement> multipart = new HashMap<String, MultipartElement>();
				for(final MultipartElement element : request.giveMultipart())
					if(element.giveContent().length > 0)
					{
						final String name = element.giveDisposition().getProperty("name");
						if(name != null)
							multipart.put(name.toLowerCase(), element);
					}
				final MultipartElement
					fileSrc = multipart.get("file-src"),
					fileImg = multipart.get("file-img"),
					titleMajor = multipart.get("title-major"),
					titleMinor = multipart.get("title-minor"),
					team = multipart.get("team"),
					year = multipart.get("year"),
					direction = multipart.get("direction"),
					vocal = multipart.get("vocal"),
					lyrics = multipart.get("lyrics"),
					realization = multipart.get("realization"),
					other = multipart.get("other");
				byte[] src = fileSrc != null ? fileSrc.giveContent() : new byte[0], img = new byte[0];
				if(src.length > 0)
				{
					String filename = fileSrc.giveDisposition().getProperty("filename");
					System.out.println("Received a MP3 file (sender: " + sender + ", filename: \"" + filename + "\")");
					if(filename == null || filename.isEmpty())
						filename = "file";
					filename += "." + System.currentTimeMillis() + ".%type%.mp3";
					final File fileIn = new File(directory, filename.replace("%type%", "in")), fileOut = new File(directory, filename.replace("%type%", "out"));
					final OutputStream fileStream = new FileOutputStream(fileIn);
					fileStream.write(src);
					fileStream.close();
					if(fileImg != null)
					{
						System.out.println("Received a image file (sender: " + sender + ", filename: \"" + fileImg.giveDisposition().getProperty("filename") + "\")");
						try
						{
							final BufferedImage
								image = ImageIO.read(new MemoryCacheImageInputStream(new ByteArrayInputStream(fileImg.giveContent()))),
								result = new BufferedImage(logo.getWidth(), logo.getHeight(), BufferedImage.TYPE_INT_RGB);
							final int
								width = image.getWidth(),
								height = image.getHeight(),
								size = Math.min(width, height);
							final Graphics2D graphics = result.createGraphics();
							graphics.drawImage(Scalr.resize(image.getSubimage((width - size) / 2, (height - size) / 2, size, size), logo.getWidth(), logo.getHeight()), 0, 0, null);
							graphics.drawImage(logo, 0, 0, null);
							graphics.dispose();
							final ByteArrayOutputStream stream = new ByteArrayOutputStream();
							ImageIO.write(result, "jpeg", new MemoryCacheImageOutputStream(stream));
							img = stream.toByteArray();
						}
						catch(final IOException e)
						{
							e.printStackTrace();
						}
					}
					final Mp3File fileMP3 = new Mp3File(fileIn);
					final ID3v1 id3v1 = new ID3v1Tag();
					final ID3v2 id3v2 = new ID3v23Tag();
					for(final ID3v1 id3 : new ID3v1[]{id3v1, id3v2})
					{
						final boolean v2 = id3 instanceof ID3v2;
						id3.setAlbum("The Best of NanoKarrin");
						final String title = titleMinor != null ? new String(titleMinor.giveContent(), charset) : "";
						if(!title.isEmpty() && (v2 || title.length() <= 30))
							id3.setTitle(title);
						if(vocal != null)
						{
							final String vocalText = new String(vocal.giveContent(), charset);
							if(v2 || vocalText.length() <= 30)
								id3.setArtist(vocalText);
						}
						if(year != null)
						{
							final String yearText = new String(year.giveContent(), charset);
							if(v2 || yearText.length() <= 4)
								id3.setYear(yearText);
						}
						final StringBuffer comment = new StringBuffer();
						for(final MultipartElement element : new MultipartElement[]{direction, lyrics, realization, other, team})
							if(element != null)
							{
								boolean colon = true;
								final String content = new String(element.giveContent(), charset);
								if(element.equals(direction))
									comment.append("Re¿yseria");
								else if(element.equals(lyrics))
									comment.append("Tekst piosenki");
								else if(element.equals(realization))
									comment.append("Realizacja dŸwiêku");
								else if(element.equals(team))
									comment.append("Dru¿yna bitwowa");
								else if(content.contains(":"))
									colon = false;
								else
									comment.append("Inne");
								if(colon)
									comment.append(": ");
								comment.append(content).append(" | ");
							}
						final String commentText = comment.toString();
						final int commentLength = commentText.length();
						if(commentLength > 3)
						{
							final String commentTextFormat = commentText.substring(0, commentLength - 3);
							if(v2)
							{
								final EncodedText commentTextEncoded = new EncodedText(commentTextFormat);
								new ID3v2CommentFrameData(false, null, new EncodedText("\0", commentTextEncoded.getTextEncoding()), commentTextEncoded)
								{
									{
										final Map<String, ID3v2FrameSet> frames = ((ID3v2) id3).getFrameSets();
										final String commentID = AbstractID3v2Tag.ID_COMMENT;
										if(!frames.containsKey(commentID))
											frames.put(commentID, new ID3v2FrameSet(commentID));
										final ID3v2FrameSet frame = frames.get(commentID);
										frame.clear();
										frame.addFrame(new ID3v2Frame(commentID, this.toBytes()));
									}
								};
							}
							else if(commentLength <= 33)
								id3.setComment(commentTextFormat);
						}
					}
					final List<Consumer<String>>
						listNK = new ArrayList<Consumer<String>>(),
						listULR = new ArrayList<Consumer<String>>();
					listNK.add(id3v2::setAlbumArtist);
					listNK.add(id3v2::setPublisher);
					listULR.add(id3v2::setUrl);
					listULR.add(id3v2::setPublisherUrl);
					for(final Consumer<String> consumer : listNK)
						consumer.accept("NanoKarrin");
					for(final Consumer<String> consumer : listULR)
						consumer.accept("http://nanokarrin.pl/");
					id3v2.setAudiofileUrl("http://nanokarrin.pl/mp3");
					id3v2.setAudioSourceUrl("https://www.youtube.com/user/fandubbing");
					if(img.length > 0)
						id3v2.setAlbumImage(img, "image/jpeg");
					final Map<String, ID3v2FrameSet> frames = id3v2.getFrameSets();
					final String genre = AbstractID3v2Tag.ID_GENRE;
					if(!frames.containsKey(genre))
						frames.put(genre, new ID3v2FrameSet(genre));
					final ID3v2FrameSet frame = frames.get(genre);
					frame.clear();
					final EncodedText genreText = new EncodedText("Fandub");
					final byte[] genreTextArray = genreText.toBytes(true);
					final int genreTextLength = genreTextArray.length;
					final byte[] genreArray = new byte[genreTextLength + 1];
					genreArray[0] = genreText.getTextEncoding();
					BufferTools.copyIntoByteBuffer(genreTextArray, 0, genreTextLength, genreArray, 1);
					frame.addFrame(new ID3v2Frame(genre, genreArray));
					fileMP3.setId3v1Tag(id3v1);
					fileMP3.setId3v2Tag(id3v2);
					fileMP3.save(fileOut.getAbsolutePath());
					if(fileOut.isFile())
					{
						final ByteArrayOutputStream array = new ByteArrayOutputStream();
						try
						{
							final InputStream stream = new FileInputStream(fileOut);
							int read;
							final byte[] data = new byte[0x400];
							while((read = stream.read(data, 0, data.length)) != -1)
								array.write(data, 0, read);
							stream.close();
						}
						catch(final IOException e)
						{
							e.printStackTrace();
						}
						src = array.toByteArray();
					}
				}
				response.setContent(src);
				final String
					titleMajorText = titleMajor != null ? new String(titleMajor.giveContent(), charset) : "",
					titleMinorText = titleMinor != null ? new String(titleMinor.giveContent(), charset) : "";
				final boolean
					isTitleMajor = !titleMajorText.isEmpty(),
					isTitleMinor = !titleMinorText.isEmpty();
				final StringBuffer disposition = new StringBuffer().append("attachment; filename=\"[NanoKarrin]");
				if(isTitleMajor)
				{
					disposition.append(" ").append(titleMajorText);
					if(isTitleMinor)
						disposition.append(" -");
				}
				if(isTitleMinor)
					disposition.append(" ").append(titleMinorText);
				disposition.append(".mp3\"");
				final String dispositionText = disposition.toString();
				final Properties header = response.giveHeader();
				header.setProperty("Content-Type", "audio/mpeg");
				header.setProperty("Content-Disposition", dispositionText);
				System.out.println("Sended a MP3 file with metadata (recipient: " + sender + ", disposition: " + dispositionText + ")");
				return true;
			}
			return false;
		}
		catch(final Throwable e)
		{
			request.giveStatusCode().set(500, "Internal Server Error");
			e.printStackTrace();
			return true;
		}
	}
	
}
