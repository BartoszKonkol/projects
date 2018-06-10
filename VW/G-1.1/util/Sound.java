package vw.util;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.jnf.exception.Defect;
import javax.jnf.lwjgl.Point3D;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import net.minecraft.client.audio.ISound;
import net.minecraft.client.audio.SoundHandler;
import net.minecraft.client.audio.SoundManager;
import net.minecraft.client.audio.SoundManager.SoundSystemStarterThread;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;

public class Sound implements ISound
{
	
	public static final byte SCAN = 1;

	protected static final SoundManager              soundManager = SoundManager.giveSoundManager();
	protected static final SoundHandler              soundHandler = soundManager.sndHandler;
	protected static final SoundSystemStarterThread  soundSystem  = soundManager.sndSystem;
	
	protected final Logger logger = LogManager.getLogger(this.getClass().getSimpleName());
	
	public Sound(final ResourceLocation location, final List<Map<?, ?>> data, final boolean repeat, final int repeatDelay, final float volume, final float pitch, final Point3D position, final AttenuationType attenuation)
	{
		
		this.setLocation(location).setData(data).setRepeat(repeat).setRepeatDelay(repeatDelay).setVolume(volume).setPitch(pitch).setPosition(position).setAttenuation(attenuation);
		
	}
	
	public Sound(final ResourceLocation location, final List<Map<?, ?>> data, final boolean repeat, final int repeatDelay, final float volume, final float pitch, final AttenuationType attenuation)
	{
		
		this(location, data, repeat, repeatDelay, volume, pitch, new Point3D(0.0F, 0.0F, 0.0F), attenuation);
		
	}
	
	public Sound(final File file, final String name) throws Exception
	{
		
		final NBTTagCompound nbtFile = CompressedStreamTools.read(file);
		NBTTagCompound nbtSounds = null;
		
		try
		{
			
			nbtSounds = (NBTTagCompound) nbtFile.getTag("_sounds");
			
		}
		catch(final ClassCastException e)
		{
			
			;
			
		}
		
		if(nbtSounds != null)
		{
			
			try
			{
				
				final NBTTagCompound nbtSound = (NBTTagCompound) nbtSounds.getTag(name);

				final AttenuationType attenuation = nbtSound.getBoolean("_attenuation") ? AttenuationType.LINEAR : AttenuationType.NONE;
				final ResourceLocation location = new ResourceLocation(nbtSound.getString("_id"));
				final boolean repeat = nbtSound.getBoolean("_loop");
				final int repeatDelay = 0;
				final float pitch = nbtSound.getFloat("_pitch");
				final float volume = nbtSound.getFloat("_volume");
				final Point3D position = new Point3D(0.0F, 0.0F, 0.0F);
				final List<Map<?, ?>> data = new ArrayList<Map<?, ?>>();
				
				for(final Object component : nbtSound.getKeySet().toArray())
				{
					
					final String element = component.toString();
					
					if(element.charAt(0) != '_')
					{
						
						final NBTTagCompound nbtSelectedSound = (NBTTagCompound) nbtSound.getTag(element);
						final Map<String, Object> mapData = new HashMap<String, Object>(3);
						
						mapData.put("duration", nbtSelectedSound.getShort("_duration"));
						mapData.put("extension", nbtSelectedSound.getString("_extension"));
						mapData.put("location", nbtSelectedSound.getString("_location"));
						
						data.add(mapData);
						
					}
					
				}
				
				this.setLocation(location).setData(data).setRepeat(repeat).setRepeatDelay(repeatDelay).setVolume(volume).setPitch(pitch).setPosition(position).setAttenuation(attenuation);
				
			}
			catch(final ClassCastException e)
			{
				
				throw new Defect("In the data file NBT occurred mismatch (" + e.getMessage() + ").");
				
			}
			catch(final NullPointerException e)
			{
				
				e.printStackTrace();
				throw new Defect("In the data file NBT is not found required component.");
				
			}
			
		}
		else
			throw new Defect("The specified data file NBT is invalid.");
		
	}
	
	private ResourceLocation location;
	private List<Map<?, ?>> data;
	private boolean repeat;
	private int repeatDelay;
	private float volume;
	private float pitch;
	private Point3D position;
	private AttenuationType attenuation;
	
	public final Sound setLocation(final ResourceLocation location)
	{
		
		this.location = location;
		return this;
		
	}
	
	public final ResourceLocation getLocation()
	{
		
		return this.location;
		
	}
	
	@Override
	@Deprecated
	public final ResourceLocation getSoundLocation() // getPositionedSoundLocation
	{
		
		return this.getLocation();
		
	}
	
	public final Sound setData(final List<Map<?, ?>> data)
	{
		
		this.data = data;
		return this;
		
	}
	
	public final List<Map<?, ?>> getData()
	{
		
		return this.data;
		
	}
	
	public final Sound setRepeat(final boolean repeat)
	{
		
		this.repeat = repeat;
		return this;
		
	}

	public final boolean getRepeat()
	{
		
		return this.repeat;
		
	}
	
	@Override
	@Deprecated
	public final boolean canRepeat()
	{
		
		return this.getRepeat();
		
	}

	public final Sound setRepeatDelay(final int repeatDelay)
	{
		
		this.repeatDelay = repeatDelay;
		return this;
		
	}
	
	@Override
	public final int getRepeatDelay()
	{
		
		return this.repeatDelay;
		
	}

	public final Sound setVolume(final float volume)
	{
		
		this.volume = volume;
		return this;
		
	}
	
	@Override
	public final float getVolume()
	{
		
		return this.volume;
		
	}
	

	public final Sound setPitch(final float pitch)
	{
		
		this.pitch = pitch;
		return this;
		
	}
	
	@Override
	public final float getPitch()
	{
		
		return this.pitch;
		
	}
	
	public final Sound setPosition(final Point3D position)
	{
		
		this.position = position;
		return this;
		
	}
	
	public final Point3D getPosition()
	{
		
		return this.position;
		
	}
	
	@Override
	@Deprecated
	public final float getXPosF()
	{
		
		return this.getPosition().getX();
		
	}
	
	@Override
	@Deprecated
	public final float getYPosF()
	{
		
		return this.getPosition().getY();
		
	}
	
	@Override
	@Deprecated
	public final float getZPosF()
	{
		
		return this.getPosition().getZ();
		
	}
	
	public final Sound setAttenuation(final AttenuationType attenuation)
	{
		
		this.attenuation = attenuation;
		return this;
		
	}
	
	public final AttenuationType getAttenuation()
	{
		
		return this.attenuation;
		
	}
	
	@Override
	@Deprecated
	public final AttenuationType getAttenuationType()
	{
		
		return this.getAttenuation();
		
	}
	
	public final void doLoad()
	{
		
		this.doLoad(1.5F);
		
	}
	
	public void doLoad(final float delay)
	{
		
		if(delay > 0)
			Util.doPause(delay);
		
		for(final Map<String, Object> data : this.giveDataArray())
		{
			
			final ResourceLocation location = new ResourceLocation((String) data.get("location") + '.' + (String) data.get("extension"));
			
			try
			{
				
				this.soundSystem.loadSound(this.soundManager.getURLForSoundResource(location), location.toString());
				
			}
			catch(NullPointerException e)
			{
				
				final String[] soundLocation = ((String) data.get("location")).replace('\\', '/').split("/");
				this.logger.error("As a result of error the loader " + this.soundSystem.className() + ", sound '*/" + soundLocation[soundLocation.length - 2] + '/' + soundLocation[soundLocation.length - 1] + "' was not loaded.");
				
			}
			
		}
		
	}
	
	public void doPlay()
	{
		
		this.soundHandler.playSound(this);
		
	}
	
	public void doResume()
	{
		
		this.soundManager.resumeSound(this);
		
	}
	
	public void doPause()
	{
		
		this.soundManager.pauseSound(this);
		
	}
	
	public void doStop()
	{
		
		this.soundHandler.stopSound(this);
		
	}
	
	public boolean isPlaying()
	{
		
		return !this.isStopping() && !this.isPausing();
		
	}
	
	public boolean isPausing()
	{
		
		return this.soundManager.pausingSounds.get(this) != null;
		
	}
	
	public boolean isStopping()
	{
		
		return !this.soundManager.isSoundPlaying(this);
		
	}
	
	@SuppressWarnings("unchecked")
	public final Map<String, Object>[] giveDataArray()
	{
		
		return this.getData().toArray(new Map[this.getData().size()]);
		
	}
	
	public Map<String, Object> givePlayData()
	{
		
		try
		{
			
			for(final Map<String, Object> data : this.giveDataArray())
				if(((String) data.get("location") + '.' + (String) data.get("extension")).equals(this.soundManager.lastPlayResource.getResourcePath()))
					return data;
			
		}
		catch(NullPointerException e)
		{
			
			;
			
		}
		
		return null;
		
	}
	
	public Sound giveClone()
	{
		
		return new Sound(this.getLocation(), this.getData(), this.getRepeat(), this.getRepeatDelay(), this.getVolume(), this.getPitch(), this.getPosition(), this.getAttenuation());
		
	}
	
	public static final void doLoads(final Sound[] sounds)
	{
		
		doLoads(sounds, 1.5F);
		
	}
	
	public static void doLoads(final Sound[] sounds, final float delay)
	{
		
		if(delay > 0)
			Util.doPause(delay);
		
		for(final Sound sound : sounds)
			sound.doLoad(0.0F);
		
	}
	
	public static void doResumeAll()
	{
		
		soundHandler.resumeSounds();;
		
	}
	
	public static void doPauseAll()
	{
		
		soundHandler.pauseSounds();;
		
	}
	
	public static void doStopAll()
	{
		
		soundHandler.stopSounds();
		
	}
	
	public static Point3D giveTransformToPosition(final int x, final int y, final int z)
	{
		
		return new Point3D(x + 0.5F, y + 0.5F, z + 0.5F);
		
	}
	
	public static Point3D giveTransformToPosition(final Entity entity)
	{
		
		return new Point3D((float) entity.posX, (float) (entity.posY - entity.getYOffset()), (float) entity.posZ);
		
	}
	
	public static final List<Map<?, ?>> giveTransformToData(final short duration, final String extension, final String location) throws Defect
	{
		
		return giveTransformToData(new short[]{duration}, new String[]{extension}, new String[]{location});
		
	}
	
	public static List<Map<?, ?>> giveTransformToData(final short[] duration, final String[] extension, final String[] location) throws Defect
	{
		
		final int size = (duration.length + extension.length + location.length) / 3;
		final List<Map<?, ?>> data = new ArrayList<Map<?, ?>>();
		
		if(duration.length == size && extension.length == size && location.length == size)
			for(int i = 0; i < size; i++)
			{
				
				final Map<String, Object> mapData = new HashMap<String, Object>(3);
				
				mapData.put("duration", duration[i]);
				mapData.put("extension", extension[i]);
				mapData.put("location", location[i]);
				
				data.add(mapData);
				
			}
		else
			throw new Defect("The amount (~" + size + ") of the ingredients listed is not equal.");
		
		return data;
		
	}
	
}
