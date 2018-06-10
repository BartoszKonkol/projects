package vw.util;

import java.util.Random;
import java.util.UUID;
import javax.jnf.lwjgl.CalculatorFPS;
import javax.jnf.lwjgl.Color;
import javax.jnf.lwjgl.Generation;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.world.World;
import com.google.common.collect.Multimap;

public class Util extends javax.jnf.lwjgl.Util
{
	
	public static final byte SCAN = 1;
	
	private Util()
	{
		
		;
		
	}
	
	public static final void doGenerateColor(final float red, final float green, final float blue)
	{
		
		Generation.doGenerate(new Color(Math.round(red * Color.MAX_VALUE_COLOR), Math.round(green * Color.MAX_VALUE_COLOR), Math.round(blue * Color.MAX_VALUE_COLOR)));
		
	}
	
	public static final void doGenerateColor()
	{
		
		Generation.doGenerate(Color.WHITE);
		
	}
	
	public static final void doGenerateColor(final float red, final float green, final float blue, final float transparency)
	{
		
		Generation.doGenerate(new Color(Math.round(red * Color.MAX_VALUE_COLOR), Math.round(green * Color.MAX_VALUE_COLOR), Math.round(blue * Color.MAX_VALUE_COLOR), Math.round((1 - transparency) * Color.MAX_VALUE_TRANSPARENT)));
		
	}
	
	public static final void doPause(final long time, final float seconds)
	{
		
		for(long i = System.currentTimeMillis(); i < time + Math.round(seconds * 1000); i = System.currentTimeMillis())
			;
		
	}
	
	public static final void doPause(final float seconds)
	{
		
		doPause(giveTime(), seconds);
		
	}
	
	public static final long giveTime()
	{
		
		return CalculatorFPS.giveTime();
		
	}
	
	public static final Multimap<String, AttributeModifier> giveItemModifierDamage(final Multimap<String, AttributeModifier> multimap, final UUID uuid, final int damage)
	{
		
		multimap.put(SharedMonsterAttributes.attackDamage.getAttributeUnlocalizedName(), new AttributeModifier(uuid, "Weapon modifier", damage, 0));
		return multimap;
		
	}
	
	public static final void doPrintChat(String key, Object ... format)
	{
		
		Minecraft.getMinecraft().ingameGUI.getChatGUI().printChatMessage(new ChatComponentTranslation(key, format));
		
	}
	
	public static final void doGenerateFlower(IBlockState block, BlockPos pos, World world, Random seed)
	{
		
		for(int i = 0; i < 64; ++i)
		{
			
			BlockPos position = pos.add(seed.nextInt(8) - seed.nextInt(8), seed.nextInt(4) - seed.nextInt(4), seed.nextInt(8) - seed.nextInt(8));
			Block ground = world.getBlockState(position.down()).getBlock();
			
			if (world.isAirBlock(position) && (!world.provider.getHasNoSky() || position.getY() < 255) && (ground == Blocks.grass || ground == Blocks.dirt || ground == Blocks.farmland))
				world.setBlockState(position, block, 2);
			
		}
		
	}
	
}
