package vw.block;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;
import javax.jnf.lwjgl.Color;
import javax.jnf.lwjgl.Point3D;
import vw.VirtualWorld;
import vw.util.DataCalculations;
import vw.util.Sound;
import vw.util.Util;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

public class BlockRadio extends Block
{
	
	public static final byte SCAN = 1;
	
	protected final Map<String, Sound> sounds;
	protected final Map<String, Boolean> powers;
	protected final Map<String, Boolean> pauses;
	
	public BlockRadio()
	{
		
		super(Material.wood);
		this.setCreativeTab(CreativeTabs.tabDecorations);
		this.setTickRandomly(true);
		
		this.sounds = new HashMap<String, Sound>();
		this.powers = new HashMap<String, Boolean>();
		this.pauses = new HashMap<String, Boolean>();
		
	}
	
	protected WorldClient worldClient;
	protected WorldServer worldServer;
	
	@Override
	public boolean onBlockActivated(final World par1World, final BlockPos par2BlockPos, final IBlockState par3IBlockState, final EntityPlayer par4EntityPlayer, final EnumFacing par5EnumFacing, final float par6, final float par7, final float par8)
	{
		
		this.doPerform(par1World, par2BlockPos);
		return true;
		
	}

	@Override
	public void onBlockClicked(final World par1World, final BlockPos par2BlockPos, final EntityPlayer par3EntityPlayer)
	{
		
		this.doPerform(par1World, par2BlockPos);
		
	}
	
	@Override
	public void onNeighborBlockChange(final World par1World, final BlockPos par2BlockPos, final IBlockState par3IBlockState, final Block par4Block)
	{
		
		final boolean powered = this.doAssignWorld(par1World).isBlockIndirectlyGettingPowered(par2BlockPos) > 0;
		final String id = this.giveLocationID(par2BlockPos);
		
		if(!this.powers.containsKey(id))
			this.powers.put(id, false);
		
		if(this.powers.get(id) != powered)
		{
			
			if(powered)
				this.doPerform(this.worldClient, par2BlockPos);
			
			this.powers.put(id, powered);
		
		}
		
	}
	
	protected void doPerform(final World par1World, final BlockPos par2BlockPos)
	{
		
		if(this.doAssignWorld(par1World) == this.worldClient)
		{
			
			final Point3D position = Sound.giveTransformToPosition(par2BlockPos.getX(), par2BlockPos.getY(), par2BlockPos.getZ());
			final String id = this.giveLocationID(position);
			
			this.pauses.put(id, false);
			
			boolean message = true;
			
			if(this.sounds.containsKey(id))
			{
				
				final Sound sound = this.sounds.get(id);
				
				if(sound.isPlaying())
				{
					
					sound.doPause();
					this.pauses.put(id, true);
					message = false;
					
				}
				else if(sound.isPausing())
					sound.doResume();
				else if(sound.isStopping())
					sound.doPlay();
				
			}
			else
			{
				
				this.sounds.put(id, VirtualWorld.radioSound.giveClone().setPosition(position));
				this.sounds.get(id).doPlay();
				
			}
			
			if(message)
				Util.doPrintChat("tile.radio.message", " === ", " ===");
			
			this.doNoteGenerate(par2BlockPos);
			
		}
		
	}
	
	@Override
	public void updateTick(final World par1World, final BlockPos par2BlockPos, IBlockState par3IBlockState, final Random par4Random)
	{
		
		this.doAssignWorld(par1World);
		
		final String id = this.giveLocationID(par2BlockPos);
		
		if(this.sounds.containsKey(id))
		{
			
			final Sound sound = this.sounds.get(id);
			
			if(sound.isPlaying() && this.pauses.get(id))
				sound.doPause();
			
		}
		
	}
	
	@Override
	public void randomDisplayTick(final World par1World, final BlockPos par2BlockPos, IBlockState par3IBlockState, final Random par4Random)
	{
		
		this.doAssignWorld(par1World);
		
		if(par4Random.nextInt(3) == 0)
			this.doNoteGenerate(par2BlockPos);
		
	}
	
	protected void doNoteGenerate(final BlockPos par1BlockPos)
	{
		
		Color color = Color.WHITE;
		final Random random = this.worldClient.rand;
		
		switch(DataCalculations.giveRandomNumber(1, 15, random))
		{
			
			case 1:
				color = Color.RED;
				break;
			case 2:
				color = Color.GREEN;
				break;
			case 3:
				color = Color.BLUE;
				break;
			case 4:
				color = Color.YELLOW;
				break;
			case 5:
				color = new Color(Color.MIN_VALUE, Color.MAX_VALUE_COLOR, Color.MAX_VALUE_COLOR);
				break;
			case 6:
				color = new Color(Color.MAX_VALUE_COLOR, Color.MIN_VALUE, Color.MAX_VALUE_COLOR);
				break;
			case 7:
				color = Color.ORANGE;
				break;
			case 8:
				color = new Color(Byte.MAX_VALUE, Color.MAX_VALUE_COLOR, Color.MIN_VALUE);
				break;
			case 9:
				color = new Color(Color.MIN_VALUE, Color.MAX_VALUE_COLOR, Byte.MAX_VALUE);
				break;
			case 10:
				color = new Color(Color.MIN_VALUE, Byte.MAX_VALUE, Color.MAX_VALUE_COLOR);
				break;
			case 11:
				color = new Color(Color.MAX_VALUE_COLOR, Color.MIN_VALUE, Byte.MAX_VALUE);
				break;
			case 12:
				color = new Color(Byte.MAX_VALUE, Color.MIN_VALUE, Color.MAX_VALUE_COLOR);
				break;
			case 13:
				color = Color.DARK_RED;
				break;
			case 14:
				color = Color.DARK_GREEN;
				break;
			case 15:
				color = Color.DARK_BLUE;
				break;
			
		}
		
		this.worldClient.spawnParticle(EnumParticleTypes.NOTE_COLOR, par1BlockPos.getX() + DataCalculations.giveRandomNumber(4, 6, random) / 10.0D, par1BlockPos.getY() + 1.2D, par1BlockPos.getZ() + DataCalculations.giveRandomNumber(4, 6, random) / 10.0D, 0.0D, 0.0D, 0.0D, color.getColor());
		
	}
	
	@Override
	public Item getItemDropped(final IBlockState par1IBlockState, final Random par2Random, final int par3) // idDropped
	{
		
		return Item.getItemFromBlock(VirtualWorld.radio);
		
	}
	
	@Override
	public void breakBlock(final World par1World, final BlockPos par2BlockPos, IBlockState par3IBlockState)
	{
		
		final String id = this.giveLocationID(par2BlockPos);
		
		if(this.sounds.containsKey(id))
			this.sounds.get(id).doStop();
		
		this.powers.put(id, false);
		this.pauses.put(id, false);
		
		super.breakBlock(this.doAssignWorld(par1World), par2BlockPos, par3IBlockState);
		
	}
	
	@Override
	public boolean isNormalCube(final IBlockAccess par1IBlockAccess, final BlockPos par2BlockPos)
	{
		
		return false;
		
	}
	
	@Override
	public void onBlockPlacedBy(final World par1World, final BlockPos par2BlockPos, IBlockState par3IBlockState, final EntityLivingBase par4EntityLivingBase, final ItemStack par5ItemStack)
	{
		
		this.doAssignWorld(par1World).setBlockState /* setBlockMetadataWithNotify */ (par2BlockPos, this.getStateFromMeta(MathHelper.floor_double(par4EntityLivingBase.rotationYaw * 4.0F / 360.0F + 2.5D) & 3), 2);
		
	}
	
	public void doUpdate(final World par1World)
	{
		
		final Iterator<String> ids = this.sounds.keySet().iterator();
		
		while(ids.hasNext())
		{
			
			final String id = ids.next();
			final Point3D position = this.givePositionFromLocationID(id);
			
			if(!this.sounds.get(id).isStopping() & (this.doAssignWorld(par1World) == this.worldServer || this.worldServer != null))
				this.worldServer.scheduleUpdate /* scheduleBlockUpdate */ (new BlockPos((int) (position.getX() - 0.5F), (int) (position.getY() - 0.5F), (int) (position.getZ() - 0.5F)), VirtualWorld.radio, 1);
			
		}
		
	}
	
	protected World doAssignWorld(final World par1World)
	{
		
		if(par1World instanceof WorldClient)
		{
			
			this.worldClient = (WorldClient) par1World;
			return this.worldClient;
			
		}
		else if(par1World instanceof WorldServer)
		{
			
			this.worldServer = (WorldServer) par1World;
			return this.worldServer;
			
		}
		
		return null;
		
	}
	
	protected final String giveLocationID(final BlockPos par1BlockPos)
	{
		
		return this.giveLocationID(Sound.giveTransformToPosition(par1BlockPos.getX(), par1BlockPos.getY(), par1BlockPos.getZ()));
		
	}
	
	protected final String giveLocationID(final Point3D par1Point3D)
	{
		
		return "[class=" + this.toString() + ";position=" + par1Point3D.getX() + ',' + par1Point3D.getY() + ',' + par1Point3D.getZ() + ']';
		
	}
	
	protected final Point3D givePositionFromLocationID(final String par1String)
	{
		
		Point3D result = null;
		
		if(par1String.charAt(0) == '[' && par1String.charAt(par1String.length() - 1) == ']')
			for(final String content : par1String.substring(1, par1String.length() - 1).split(";"))
			{
				
				final String[] parameter = content.split("=", 2);
				final String key = parameter[0];
				final String value = parameter[1];
				
				switch(key)
				{
					
					case "class":
						if(!value.equals(this.toString()))
							return null;
						break;
					case "position":
						final String[] position = value.split(",", 3);
						result = new Point3D(Util.giveStringToFloat(position[0]), Util.giveStringToFloat(position[1]), Util.giveStringToFloat(position[2]));
						break;
					default:
						return null;
					
				}
				
			}
		
		return result;
		
	}

}
