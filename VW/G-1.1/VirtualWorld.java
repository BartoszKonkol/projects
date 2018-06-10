package vw;

import java.io.File;
import javax.jnf.exception.Defect;
import javax.jnf.lwjgl.Color;
import org.apache.logging.log4j.LogManager;
import net.minecraft.block.Block;
import net.minecraft.block.BlockDispenser;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.EffectRenderer;
import net.minecraft.client.particle.IParticleFactory;
import net.minecraft.client.renderer.ItemModelMesher;
import net.minecraft.client.renderer.entity.RenderSnowball;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.Achievement;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.common.BiomeManager;
import net.minecraftforge.common.BiomeManager.BiomeEntry;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.common.BiomeManager.BiomeType;
import vw.block.BlockAquamarineDamagedBlock;
import vw.block.BlockAquamarineblock;
import vw.block.BlockAquamarineore;
import vw.block.BlockBlackblock;
import vw.block.BlockBlockkilling;
import vw.block.BlockBlockofdarkmatter;
import vw.block.BlockBlockofwhitematter;
import vw.block.BlockImprovedrainbowblock;
import vw.block.BlockMagicBlock;
import vw.block.BlockMagicRock;
import vw.block.BlockMeltingsnow;
import vw.block.BlockPlantblue;
import vw.block.BlockRadio;
import vw.block.BlockRainbowblock;
import vw.block.BlockStairrainbowblock;
import vw.block.BlockSunshade;
import vw.block.BlockTransparentblock;
import vw.block.BlockWaterydiamond;
import vw.block.BlockWhiteblock;
import vw.client.particle.FactoryNoteColor;
import vw.client.renderer.entity.RenderHerobrine;
import vw.client.renderer.entity.RenderHuman;
import vw.dispenser.DispenserBehaviorDagger;
import vw.dispenser.DispenserBehaviorDaggerLightning;
import vw.dispenser.DispenserBehaviorStoneTransformations;
import vw.entity.monster.EntityHerobrine;
import vw.entity.passive.EntityHuman;
import vw.entity.projectile.EntityDagger;
import vw.entity.projectile.EntityDaggerLightning;
import vw.entity.projectile.EntityStoneTransformations;
import vw.item.ItemArmorData;
import vw.item.ItemBlockAquamarineDamagedBlock;
import vw.item.ItemBlockSunshade;
import vw.item.ItemBrewingData;
import vw.item.ItemDaggerControl;
import vw.item.ItemData;
import vw.item.ItemEnchantedWand;
import vw.item.ItemFoodData;
import vw.item.ItemToolData;
import vw.loading.certificates.CertificateSecurity;
import vw.proxy.CommonProxy;
import vw.util.DataTexts;
import vw.util.ID;
import vw.util.Language;
import vw.util.ListIdentifiers;
import vw.util.Sound;
import vw.world.WorldProviderDreamland;
import vw.world.biome.BiomeGenData;
import vw.world.gen.feature.WorldGeneratorOres;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;

/**
 * 
 * <p><b><u>
 * <!> Virtual World G-1.1
 * </u></b></p>
 * 
 * <p><b>Name:</b><br>&emsp;&emsp;&emsp;
 * <!> Virtual World
 * </p><p><b>Version:</b><br>&emsp;&emsp;&emsp;
 * <!> G-1.1
 * </p><p><b>Authors:</b><br>&emsp;&emsp;&emsp;
 * <!> CC Virtual World (Bartosz Konkol, Marcin Auer)
 * </p>
 * 
 * <p><b>Mod_modid:</b><br>&emsp;&emsp;&emsp;
 * <!> VW
 * </p><p><b>Mod_name:</b><br>&emsp;&emsp;&emsp;
 * <!> Virtual World G-1.1
 * </p><p><b>Mod_version:</b><br>&emsp;&emsp;&emsp;
 * <!> VW-013_MC-1.8
 * </p>
 * 
 * <p><img src="./../assets/vw/files/graphics/vw_logo.png"></p>
 * 
 **/

@Mod(modid = "VW", name = "Virtual World G-1.1", version = "VW-013_MC-1.8")

public final class VirtualWorld
{

	protected final void init(final FMLPreInitializationEvent par1FMLPreInitializationEvent)
	{
		
		final Minecraft minecraft = Minecraft.getMinecraft();
		
		ID.doClear();
		
		ID.addBlock(blockOfDarkMatter, "blockofdarkmatter", DataTexts.elementNameBlockOfDarkMatter);
		ID.addBlock(blackBlock, "blackblock", DataTexts.elementNameBlackBlock);
		ID.addBlock(blockOfWhiteMatter, "blockofwhitematter", DataTexts.elementNameBlockOfWhiteMatter);
		ID.addBlock(transparentBlock, "transparentblock", DataTexts.elementNameTransparentBlock);
		ID.addBlock(rainbowBlock, "rainbowblock", DataTexts.elementNameRainbowBlock);
		ID.addBlock(improvedRainbowBlock, "improvedrainbowblock", DataTexts.elementNameImprovedRainbowBlock);
		ID.addBlock(whiteBlock, "whiteblock", DataTexts.elementNameWhiteBlock);
		ID.addBlock(blockKilling, "blockkilling", DataTexts.elementNameBlockKilling);
		ID.addBlock(meltingSnow, "meltingsnow", DataTexts.elementNameMeltingSnow);
		ID.addBlock(stairRainbowBlock, "stairsrainbowblock", DataTexts.elementNameStairRainbowBlock);
		ID.addBlock(plantBlue, "plantblue", DataTexts.elementNamePlantBlue);
		ID.addBlock(aquamarineOre, "aquamarineore", DataTexts.elementNameAquamarineOre);
		ID.addBlock(aquamarineOreGlowing, "aquamarineore_glowing", DataTexts.elementNameAquamarineOre);
		ID.addBlock(aquamarineBlock, "aquamarineblock", DataTexts.elementNameAquamarineBlock);
		ID.addBlock(blockWateryDiamond, "blockwaterydiamond", DataTexts.elementNameBlockWateryDiamond);
		ID.addBlock(radio, "radio", DataTexts.elementNameRadio);
		ID.addBlock(magicRock, "magicrock", DataTexts.elementNameMagicRock);
		ID.addBlock(magicBlock, "magicblock", DataTexts.elementNameMagicBlock);
		
		ID.addItemBlock(new ItemBlockAquamarineDamagedBlock(aquamarineDamagedBlock), "aquamarinedamagedblock");
		ID.addItemBlock(new ItemBlockSunshade(sunshadeBlock), "sunshadeBlock", DataTexts.elementNameSunshade);
		
		ID.addItem(wateryDiamond, "waterydiamond", DataTexts.elementNameWateryDiamond);
		ID.addItem(darkMatter, "darkmatter", DataTexts.elementNameDarkMatter);
		ID.addItem(whiteDust, "whitedust", DataTexts.elementNameWhiteDust);
		ID.addItem(whiteMatter, "whitematter", DataTexts.elementNameWhiteMatter);
		ID.addItem(brain, "brain", DataTexts.elementNameBrain);
		ID.addItem(blood, "blood", DataTexts.elementNameBlood);
		ID.addItem(bloodPotion, "bloodpotion", DataTexts.elementNameBloodPotion);
		ID.addItem(bloodBowl, "bloodbowl", DataTexts.elementNameBloodBowl);
		ID.addItem(helmetRainbow, "helmetRainbow", DataTexts.elementNameHelmetRainbow);
		ID.addItem(plateRainbow, "plateRainbow", DataTexts.elementNamePlateRainbow);
		ID.addItem(legsRainbow, "legsRainbow", DataTexts.elementNameLegsRainbow);
		ID.addItem(bootsRainbow, "bootsRainbow", DataTexts.elementNameBootsRainbow);
		ID.addItem(aquamarineItem, "aquamarineitem", DataTexts.elementNameAquamarineItem);
		ID.addItem(wateryDiamondDust, "waterydiamonddust", DataTexts.elementNameWateryDiamondDust);
		ID.addItem(wateryDiamondShard, "waterydiamondshard", DataTexts.elementNameWateryDiamondShard);
		ID.addItem(daggerRainbow, "daggerRainbow", DataTexts.elementNameDaggerRainbow);
		ID.addItem(daggerWateryDiamond, "daggerWateryDiamond", DataTexts.elementNameDaggerWateryDiamond);
		ID.addItem(daggerModel, "daggerModel");
		ID.addItem(daggerWood, "daggerWood", DataTexts.elementNameDaggerWood);
		ID.addItem(daggerStone, "daggerStone", DataTexts.elementNameDaggerStone);
		ID.addItem(daggerIron, "daggerIron", DataTexts.elementNameDaggerIron);
		ID.addItem(daggerGold, "daggerGold", DataTexts.elementNameDaggerGold);
		ID.addItem(daggerDiamond, "daggerDiamond", DataTexts.elementNameDaggerDiamond);
		ID.addItem(daggerAquamarine, "daggerAquamarine", DataTexts.elementNameDaggerAquamarine);
		ID.addItem(daggerLightning, "daggerLightning", DataTexts.elementNameDaggerLightning);
		ID.addItem(wand, "wand", DataTexts.elementNameWand);
		ID.addItem(magic, "magic");
		ID.addItem(enchantedWand, "enchantedwand", DataTexts.elementNameEnchantedWand);
		ID.addItem(bookMagic, "bookmagic", DataTexts.elementNameBookMagic);
		ID.addItem(stoneTransformations , "stonetransformations", DataTexts.elementNameStoneTransformations);
		ID.addItem(wandTeleportation, "wandteleportation", DataTexts.elementNameWandTeleportation);
		ID.addItem(crystalCosmos, "crystalcosmos", DataTexts.elementNameCrystalCosmos);
		ID.addItem(sunshade, "sunshade", DataTexts.elementNameSunshade);
		
		ID.doRegister();
		
		GameRegistry.addRecipe(new ItemStack(this.blockOfDarkMatter, 1), new Object[] {"XXX", "XYX", "XXX", 'X', this.darkMatter, 'Y', this.wateryDiamond});
		GameRegistry.addRecipe(new ItemStack(this.blackBlock, 2), new Object[] {"XYX", 'X', this.blockOfDarkMatter, 'Y', this.darkMatter});
		GameRegistry.addRecipe(new ItemStack(this.blockOfWhiteMatter, 1), new Object[] {"XXX", "X X", "XXX", 'X', this.whiteMatter});
		GameRegistry.addRecipe(new ItemStack(this.transparentBlock, 10), new Object[] {"XXX", "XXX", "XXX", 'X', Blocks.glass});
		GameRegistry.addRecipe(new ItemStack(this.improvedRainbowBlock, 1), new Object[] {"XYX", 'X', this.rainbowBlock, 'Y', Blocks.diamond_block});
		GameRegistry.addRecipe(new ItemStack(this.whiteBlock, 1), new Object[] {"XX", "XX", 'X', this.blockOfWhiteMatter});
		GameRegistry.addRecipe(new ItemStack(this.blockKilling, 1), new Object[] {"XX", "XX", 'X', this.blackBlock});
		GameRegistry.addRecipe(new ItemStack(this.stairRainbowBlock, 4), new Object[] {"X  ", "XX ", "XXX", 'X', this.rainbowBlock});
		GameRegistry.addRecipe(new ItemStack(this.aquamarineBlock, 1), new Object[] {"XXX", "XXX", "XXX", 'X', this.aquamarineItem});
		GameRegistry.addRecipe(new ItemStack(this.blockWateryDiamond, 1), new Object[] {"XXX", "XXX", "XXX", 'X', this.wateryDiamond});
		GameRegistry.addRecipe(new ItemStack(this.radio, 1), new Object[] {"XYX", "XZX", "XXX", 'X', Blocks.planks, 'Y', Items.redstone, 'Z', this.aquamarineBlock});
		GameRegistry.addRecipe(new ItemStack(this.magicBlock, 1), new Object[] {"XXX", "XXX", "XXX", 'X', new ItemStack(magic, 1, 1)});
		
		GameRegistry.addRecipe(new ItemStack(this.darkMatter, 1), new Object[] {"XXX", "YYY", "ZAZ", 'X', Items.diamond, 'Y', this.wateryDiamond, 'Z', Items.gold_ingot, 'A', Items.iron_ingot});
		GameRegistry.addRecipe(new ItemStack(this.whiteDust, 3), new Object[] {"XXX", 'X', this.wateryDiamond});
		GameRegistry.addRecipe(new ItemStack(this.whiteMatter, 1), new Object[] {"XYX", "ZXZ", "XAX", 'X', this.whiteDust, 'Y', this.darkMatter, 'Z', this.wateryDiamond, 'A', Items.diamond});
		GameRegistry.addRecipe(new ItemStack(this.helmetRainbow, 1), new Object[]{"XXX", "X X", 'X', this.rainbowBlock});
		GameRegistry.addRecipe(new ItemStack(this.plateRainbow, 1), new Object[]{"X X", "XXX", "XXX", 'X', this.rainbowBlock});
		GameRegistry.addRecipe(new ItemStack(this.legsRainbow, 1), new Object[]{"XXX", "X X", "X X", 'X', this.rainbowBlock});
		GameRegistry.addRecipe(new ItemStack(this.bootsRainbow, 1), new Object[]{"X X", "X X", 'X', this.rainbowBlock});
		GameRegistry.addRecipe(new ItemStack(Items.dye, 1, 6), new Object[]{"X", 'X', this.plantBlue});
		GameRegistry.addRecipe(new ItemStack(this.aquamarineItem, 9), new Object[]{"X", 'X', this.aquamarineBlock});
		GameRegistry.addRecipe(new ItemStack(this.wateryDiamond, 9), new Object[]{"X", 'X', this.blockWateryDiamond});
		GameRegistry.addRecipe(new ItemStack(this.wateryDiamondShard, 9), new Object[] {"X", 'X', this.wateryDiamond});
		GameRegistry.addRecipe(new ItemStack(this.wateryDiamond, 1), new Object[] {"XXX", "XXX", "XXX", 'X', this.wateryDiamondShard});
		GameRegistry.addRecipe(new ItemStack(this.wateryDiamondDust, 9), new Object[] {"X", 'X', this.wateryDiamondShard});
		GameRegistry.addRecipe(new ItemStack(this.wateryDiamondShard, 1), new Object[] {"XXX", "XXX", "XXX", 'X', this.wateryDiamondDust});
		GameRegistry.addRecipe(new ItemStack(this.daggerRainbow, 1), new Object[] {"X", "Y", 'X', this.rainbowBlock, 'Y', Items.stick});
		GameRegistry.addRecipe(new ItemStack(this.daggerWateryDiamond, 1), new Object[] {"X", "Y", 'X', this.wateryDiamond, 'Y', Items.stick});
		GameRegistry.addRecipe(new ItemStack(this.daggerWood, 1), new Object[] {"X", "Y", 'X', Blocks.planks, 'Y', Items.stick});
		GameRegistry.addRecipe(new ItemStack(this.daggerStone, 1), new Object[] {"X", "Y", 'X', Blocks.cobblestone, 'Y', Items.stick});
		GameRegistry.addRecipe(new ItemStack(this.daggerIron, 1), new Object[] {"X", "Y", 'X', Items.iron_ingot, 'Y', Items.stick});
		GameRegistry.addRecipe(new ItemStack(this.daggerGold, 1), new Object[] {"X", "Y", 'X', Items.gold_ingot, 'Y', Items.stick});
		GameRegistry.addRecipe(new ItemStack(this.daggerDiamond, 1), new Object[] {"X", "Y", 'X', Items.diamond, 'Y', Items.stick});
		GameRegistry.addRecipe(new ItemStack(this.daggerAquamarine, 1), new Object[] {"X", "Y", 'X', this.aquamarineItem, 'Y', Items.stick});
		GameRegistry.addRecipe(new ItemStack(this.wand, 1), new Object[] {"X", "X", 'X', Items.stick});
		GameRegistry.addRecipe(new ItemStack(this.daggerLightning, 1), new Object[] {"X", "Y", 'X', new ItemStack(this.enchantedWand, 1, 1), 'Y', Items.stick});
		GameRegistry.addRecipe(new ItemStack(this.magic, 9, 1), new Object[]{"X", 'X', this.magicBlock});
		GameRegistry.addRecipe(new ItemStack(this.wandTeleportation, 1), new Object[] {"X", "Y", "Z", 'X', new ItemStack(this.magic, 1, 1), 'Y', this.stoneTransformations, 'Z', this.wand});
		
		GameRegistry.addShapelessRecipe(new ItemStack(this.bloodPotion, 1), new Object[]{this.blood, Items.glass_bottle});
		GameRegistry.addShapelessRecipe(new ItemStack(this.bloodBowl, 1), new Object[]{this.brain, this.bloodPotion, Items.bowl});
		GameRegistry.addShapelessRecipe(new ItemStack(this.wateryDiamond, 1), new Object[]{Items.diamond, Blocks.snow});
		GameRegistry.addShapelessRecipe(new ItemStack(this.enchantedWand, 1, 0), new Object[]{this.wand, Items.experience_bottle});
		for(int i = 0; i < ((ItemEnchantedWand) this.enchantedWand).giveQuantityTypes(); i++)
		{
			
			GameRegistry.addShapelessRecipe(new ItemStack(this.enchantedWand, 2, i + 1), new Object[]{this.wand, new ItemStack(this.magic, 1, i)});
			GameRegistry.addShapelessRecipe(new ItemStack(this.enchantedWand, 2, i + 1), new Object[]{new ItemStack(this.enchantedWand, 1, 0), new ItemStack(this.magic, 1, i)});
			GameRegistry.addShapelessRecipe(new ItemStack(this.bookMagic, 1), new Object[]{Items.book, new ItemStack(this.enchantedWand, 1, i + 1)});
			
		}
		GameRegistry.addShapelessRecipe(new ItemStack(this.stoneTransformations, 1), new Object[]{this.aquamarineItem, this.bookMagic});
		
		GameRegistry.addShapelessRecipe(new ItemStack(this.aquamarineBlock, 2), new Object[]{new ItemStack(this.aquamarineDamagedBlock, 1, 1), this.aquamarineBlock});
		GameRegistry.addShapelessRecipe(new ItemStack(this.aquamarineDamagedBlock, 2, 1), new Object[]{new ItemStack(this.aquamarineDamagedBlock, 1, 0), new ItemStack(this.aquamarineDamagedBlock, 1, 1)});
		GameRegistry.addShapelessRecipe(new ItemStack(this.aquamarineBlock, 1), new Object[]{new ItemStack(this.aquamarineDamagedBlock, 1, 1), new ItemStack(this.aquamarineDamagedBlock, 1, 1)});
		GameRegistry.addShapelessRecipe(new ItemStack(this.aquamarineDamagedBlock, 1, 1), new Object[]{new ItemStack(this.aquamarineDamagedBlock, 1, 0), new ItemStack(this.aquamarineDamagedBlock, 1, 0)});
		
		GameRegistry.addSmelting(this.improvedRainbowBlock, new ItemStack(this.wateryDiamond, 5), 0.5F);
		GameRegistry.addSmelting(this.wateryDiamond, new ItemStack(Items.diamond, 1), 0.5F);
		
		EntityRegistry.registerGlobalEntityID(EntityHuman.class, "Human", ListIdentifiers.idEntityHuman, new Color(0,179,241).getColor(), new Color(234,147,147).getColor());
		EntityRegistry.registerGlobalEntityID(EntityHerobrine.class, "Herobrine", ListIdentifiers.idEntityHerobrine, new Color(0,179,241).getColor(), new Color(235,235,235).getColor());
		
		EntityRegistry.addSpawn(EntityHuman.class, 2, 0, 3, EnumCreatureType.CREATURE, this.biomesStandard);
		
		RenderingRegistry.registerEntityRenderingHandler(EntityDagger.class, new RenderSnowball(minecraft.getRenderManager(), this.daggerModel, minecraft.getRenderItem()));
		RenderingRegistry.registerEntityRenderingHandler(EntityDaggerLightning.class, new RenderSnowball(minecraft.getRenderManager(), this.daggerLightning, minecraft.getRenderItem()));
		RenderingRegistry.registerEntityRenderingHandler(EntityStoneTransformations.class, new RenderSnowball(minecraft.getRenderManager(), this.stoneTransformations, minecraft.getRenderItem()));
		
		RenderingRegistry.registerEntityRenderingHandler(EntityHuman.class, new RenderHuman());
		RenderingRegistry.registerEntityRenderingHandler(EntityHerobrine.class, new RenderHerobrine());
		
		this.addDispenserDagger((ItemDaggerControl) this.daggerRainbow, 4);
		this.addDispenserDagger((ItemDaggerControl) this.daggerWateryDiamond, 8);
		this.addDispenserDagger((ItemDaggerControl) this.daggerModel, 0);
		this.addDispenserDagger((ItemDaggerControl) this.daggerWood, 1);
		this.addDispenserDagger((ItemDaggerControl) this.daggerStone, 2);
		this.addDispenserDagger((ItemDaggerControl) this.daggerIron, 5);
		this.addDispenserDagger((ItemDaggerControl) this.daggerGold, 6);
		this.addDispenserDagger((ItemDaggerControl) this.daggerDiamond, 7);
		this.addDispenserDagger((ItemDaggerControl) this.daggerAquamarine, 10);
		
		BlockDispenser.dispenseBehaviorRegistry.putObject(this.daggerLightning, new DispenserBehaviorDaggerLightning());
		BlockDispenser.dispenseBehaviorRegistry.putObject(this.stoneTransformations, new DispenserBehaviorStoneTransformations());
		
		DimensionManager.registerProviderType(ListIdentifiers.idDimensionDreamland, WorldProviderDreamland.class, false);
		
		DimensionManager.registerDimension(ListIdentifiers.idDimensionDreamland, ListIdentifiers.idDimensionDreamland);
		
		GameRegistry.registerWorldGenerator(new WorldGeneratorOres(), 0);
		
		BiomeManager.addSpawnBiome(BiomeGenData.rainbowGlade);
		BiomeManager.addSpawnBiome(BiomeGenData.meltingValley);
		
		BiomeManager.addBiome(BiomeType.WARM, new BiomeEntry(BiomeGenData.rainbowGlade, 10));
		BiomeManager.addBiome(BiomeType.COOL, new BiomeEntry(BiomeGenData.rainbowGlade, 10));
		BiomeManager.addBiome(BiomeType.COOL, new BiomeEntry(BiomeGenData.meltingValley, 10));
		
		
	}
	
	public static final void things()
	{
		
		blockOfDarkMatter = new BlockBlockofdarkmatter().setHardness(20.0F).setResistance(1000.0F).setStepSound(Block.soundTypeStone).setLightLevel(0.1F).setUnlocalizedName("blockofdarkmatter").setCreativeTab(CreativeTabs.tabBlock);
		blackBlock = new BlockBlackblock().setHardness(20.0F).setResistance(1000.0F).setStepSound(Block.soundTypeStone).setUnlocalizedName("blackblock").setCreativeTab(CreativeTabs.tabBlock);
		blockOfWhiteMatter = new BlockBlockofwhitematter().setHardness(20.0F).setResistance(1000.0F).setStepSound(Block.soundTypeStone).setLightLevel(0.2F).setUnlocalizedName("blockofwhitematter").setCreativeTab(CreativeTabs.tabBlock);
		transparentBlock = new BlockTransparentblock(Material.glass).setHardness(0.3F).setStepSound(Block.soundTypeGlass).setUnlocalizedName("transparentblock").setCreativeTab(CreativeTabs.tabBlock);
		rainbowBlock = new BlockRainbowblock().setHardness(2.0F).setResistance(10.0F).setStepSound(Block.soundTypeCloth).setUnlocalizedName("rainbowblock").setCreativeTab(CreativeTabs.tabBlock);
		improvedRainbowBlock = new BlockImprovedrainbowblock().setHardness(2.0F).setResistance(10.0F).setStepSound(Block.soundTypeCloth).setUnlocalizedName("improvedrainbowblock").setCreativeTab(CreativeTabs.tabBlock);
		whiteBlock = new BlockWhiteblock().setHardness(0.1F).setResistance(1000.0F).setStepSound(Block.soundTypeGlass).setLightLevel(1.0F).setUnlocalizedName("whiteblock").setCreativeTab(CreativeTabs.tabBlock);
		blockKilling = new BlockBlockkilling().setHardness(2.5F).setResistance(12.5F).setStepSound(Block.soundTypeStone).setUnlocalizedName("blockkilling").setCreativeTab(CreativeTabs.tabBlock);
		meltingSnow = new BlockMeltingsnow().setHardness(0.2F).setStepSound(Block.soundTypeSnow).setUnlocalizedName("meltingsnow");
		stairRainbowBlock = new BlockStairrainbowblock(rainbowBlock.getDefaultState()).setHardness(2.0F).setUnlocalizedName("stairsrainbowblock").setCreativeTab(CreativeTabs.tabBlock);
		plantBlue = new BlockPlantblue().setHardness(0.0F).setStepSound(Block.soundTypeGrass).setUnlocalizedName("plantblue");
		aquamarineOre = new BlockAquamarineore(false).setHardness(3.0F).setResistance(5.0F).setStepSound(Block.soundTypeStone).setUnlocalizedName("aquamarineore").setCreativeTab(CreativeTabs.tabBlock);
		aquamarineOreGlowing = new BlockAquamarineore(true).setLightLevel(0.625F).setHardness(3.0F).setResistance(5.0F).setStepSound(Block.soundTypeStone).setUnlocalizedName("aquamarineore");
		aquamarineBlock = new BlockAquamarineblock().setHardness(5.0F).setResistance(10.0F).setStepSound(Block.soundTypeMetal).setUnlocalizedName("aquamarineblock").setCreativeTab(CreativeTabs.tabBlock);
		blockWateryDiamond = new BlockWaterydiamond().setHardness(5.0F).setResistance(10.0F).setStepSound(Block.soundTypeMetal).setUnlocalizedName("blockwaterydiamond").setCreativeTab(CreativeTabs.tabBlock);
		radio = new BlockRadio().setHardness(2.0F).setResistance(10.0F).setStepSound(Block.soundTypeWood).setUnlocalizedName("radio").setCreativeTab(CreativeTabs.tabDecorations);
		magicRock = new BlockMagicRock().setHardness(3.0F).setResistance(5.0F).setStepSound(Block.soundTypeStone).setUnlocalizedName("magicrock").setCreativeTab(CreativeTabs.tabBlock);
		magicBlock = new BlockMagicBlock().setHardness(5.0F).setResistance(10.0F).setStepSound(Block.soundTypeMetal).setUnlocalizedName("magicblock").setCreativeTab(CreativeTabs.tabBlock);
		aquamarineDamagedBlock = new BlockAquamarineDamagedBlock().setHardness(5.0F).setResistance(10.0F).setStepSound(Block.soundTypeMetal).setUnlocalizedName("aquamarinedamagedblock").setCreativeTab(CreativeTabs.tabBlock);
		sunshadeBlock = new BlockSunshade().setHardness(0.4F).setStepSound(Block.soundTypeCloth).setUnlocalizedName("sunshadeBlock");
		
		wateryDiamond = ItemData.wateryDiamond;
		darkMatter = ItemData.darkMatter;
		whiteDust = ItemData.whiteDust;
		whiteMatter = ItemData.whiteMatter;
		brain = ItemFoodData.brain;
		blood = ItemFoodData.blood;
		bloodPotion = ItemFoodData.bloodPotion;
		bloodBowl = ItemFoodData.bloodBowl;
		helmetRainbow = ItemArmorData.helmetRainbow;
		plateRainbow = ItemArmorData.plateRainbow;
		legsRainbow = ItemArmorData.legsRainbow;
		bootsRainbow = ItemArmorData.bootsRainbow;
		aquamarineItem = ItemData.aquamarineItem;
		wateryDiamondDust = ItemData.wateryDiamondDust;
		wateryDiamondShard = ItemData.wateryDiamondShard;
		daggerRainbow = ItemToolData.daggerRainbow;
		daggerWateryDiamond = ItemToolData.daggerWateryDiamond;
		daggerModel = ItemToolData.daggerModel;
		daggerWood = ItemToolData.daggerWood;
		daggerStone = ItemToolData.daggerStone;
		daggerIron = ItemToolData.daggerIron;
		daggerGold = ItemToolData.daggerGold;
		daggerDiamond = ItemToolData.daggerDiamond;
		daggerAquamarine = ItemToolData.daggerAquamarine;
		daggerLightning = ItemToolData.daggerLightning;
		wand = ItemBrewingData.wand;
		magic = ItemBrewingData.magic;
		enchantedWand = ItemBrewingData.enchantedWand;
		bookMagic = ItemBrewingData.bookMagic;
		stoneTransformations = ItemBrewingData.stoneTransformations;
		wandTeleportation = ItemBrewingData.wandTeleportation;
		crystalCosmos = ItemBrewingData.crystalCosmos;
		sunshade = ItemData.sunshade;
		
		achievementVirtualWorld = new Achievement("achievement.VirtualWorld", "VirtualWorld", -3, -1, Blocks.grass, null);
		achievementVirtualWorld.initIndependentStat();
		achievementVirtualWorld.setSpecial().registerStat();
		achievementWateryDiamond = new Achievement("achievement.WateryDiamond", "WateryDiamond", -6, -1, wateryDiamond, achievementVirtualWorld);
		achievementWateryDiamond.registerStat();
		
		Language.clean();
		
		Language.add(new ItemBlockAquamarineDamagedBlock((BlockAquamarineDamagedBlock) aquamarineDamagedBlock).getUnlocalizedName(new ItemStack(aquamarineDamagedBlock, 1, 0)) + ".name", DataTexts.elementNameAquamarineDamagedBlockStrongly);
		Language.add(new ItemBlockAquamarineDamagedBlock((BlockAquamarineDamagedBlock) aquamarineDamagedBlock).getUnlocalizedName(new ItemStack(aquamarineDamagedBlock, 1, 1)) + ".name", DataTexts.elementNameAquamarineDamagedBlockSlightly);
		
		Language.add(magic.getUnlocalizedName(new ItemStack(magic, 1, 0)) + ".name", DataTexts.elementNameMagicLightning);
		Language.add(magic.getUnlocalizedName(new ItemStack(magic, 1, 1)) + ".name", DataTexts.elementNameMagicMagicStone);
		
		Language.add("entity.Human.name", DataTexts.elementNameHuman);
		Language.add("entity.Herobrine.name", DataTexts.elementNameHerobrine);
		Language.add("commands.herobrine.usage", DataTexts.elementNameCommandHerobrineUsage);
		Language.add("commands.herobrine.remove.true", DataTexts.elementNameCommandHerobrineRemoveTrue);
		Language.add("commands.herobrine.remove.false", DataTexts.elementNameCommandHerobrineRemoveFalse);
		Language.add("commands.herobrine.remove.usage", DataTexts.elementNameCommandHerobrineRemoveUsage);
		Language.add("commands.realtime.usage", DataTexts.elementNameCommandRealTimeUsage);
		Language.add("commands.realtime.true", DataTexts.elementNameCommandRealTimeTrue);
		Language.add("commands.realtime.false", DataTexts.elementNameCommandRealTimeFalse);
		Language.add("tile.radio.message", DataTexts.elementNameRadioMessage);
		Language.add("entity.Herobrine.message", DataTexts.elementNameHerobrineMessage);
		Language.add(achievementVirtualWorld.statId, DataTexts.elementNameAchievemenVirtualWorld);
		Language.add(achievementVirtualWorld.statId + ".desc", DataTexts.elementNameAchievemenVirtualWorldDesc);
		Language.add(achievementWateryDiamond.statId, DataTexts.elementNameAchievemenWateryDiamond);
		Language.add(achievementWateryDiamond.statId + ".desc", DataTexts.elementNameAchievemenWateryDiamondDesc);
		
		Language.register();
		
		biomesStandard = new BiomeGenBase[]{
				BiomeGenBase.plains,			BiomeGenBase.desert,			BiomeGenBase.extremeHills,		BiomeGenBase.forest,		BiomeGenBase.taiga,
				BiomeGenBase.swampland,			BiomeGenBase.icePlains,			BiomeGenBase.iceMountains,		BiomeGenBase.desertHills,	BiomeGenBase.forestHills,
				BiomeGenBase.taigaHills,		BiomeGenBase.extremeHillsEdge,	BiomeGenBase.jungle,			BiomeGenBase.jungleHills,	BiomeGenBase.jungleEdge,
				BiomeGenBase.birchForest,		BiomeGenBase.birchForestHills,	BiomeGenBase.roofedForest,		BiomeGenBase.coldTaiga,		BiomeGenBase.coldTaigaHills,
				BiomeGenBase.coldTaigaHills,	BiomeGenBase.megaTaigaHills,	BiomeGenBase.extremeHillsPlus,	BiomeGenBase.savanna,		BiomeGenBase.savannaPlateau,
				BiomeGenBase.mesa,				BiomeGenBase.mesaPlateau_F,		BiomeGenBase.mesaPlateau,		//BiomeGenBase.rainbowGlade,	BiomeGenBase.meltingValley,
			};
		
	}
	
	public static final void registerItems(final ItemModelMesher trustee)
	{
		
		registerItem(trustee, wateryDiamond, "waterydiamond");
		
	}
	
	public static final void registerParticles(final EffectRenderer trustee)
	{
		
		addParticle(trustee, EnumParticleTypes.NOTE_COLOR, new FactoryNoteColor());
		
	}
	
	public static final void manageSound(final String type)
	{
		
		try
		{
			
			final Minecraft minecraft = Minecraft.getMinecraft();
			
			switch(type)
			{
				
				case "load":
				{
					
					VirtualWorld.radioSound = new Sound(new File(minecraft.mcDataDir, "vw\\sounds.dat"), "radio");
					if(VirtualWorld.radioSound != null)
						VirtualWorld.radioSound.doLoad();
					else
						throw new Defect("Class of radio sounds is not registered.");
					break;
					
				}
				
				case "pause":
				{
					
					break;
					
				}
				
				case "resume":
				{
					
					((BlockRadio) VirtualWorld.radio).doUpdate(minecraft.theWorld);
					break;
					
				}
				
				default:
					throw new Defect("Type '" + type + "' is undefined.");
				
			}
			
		}
		catch (final Exception e)
		{
			
			LogManager.getLogger().error("Couldn't make command sound manager", e);
			
		}
		
	}
	
	public static Block blockOfDarkMatter;
	public static Block blackBlock;
	public static Block blockOfWhiteMatter;
	public static Block transparentBlock;
	public static Block rainbowBlock;
	public static Block improvedRainbowBlock;
	public static Block whiteBlock;
	public static Block blockKilling;
	public static Block meltingSnow;
	public static Block stairRainbowBlock;
	public static Block plantBlue;
	public static Block aquamarineOre;
	public static Block aquamarineOreGlowing;
	public static Block aquamarineBlock;
	public static Block blockWateryDiamond;
	public static Block radio;
	public static Block magicRock;
	public static Block magicBlock;
	public static Block aquamarineDamagedBlock;
	public static Block sunshadeBlock;
	
	public static Item wateryDiamond;
	public static Item darkMatter;
	public static Item whiteDust;
	public static Item whiteMatter;
	public static Item brain;
	public static Item blood;
	public static Item bloodPotion;
	public static Item bloodBowl;
	public static Item helmetRainbow;
	public static Item plateRainbow;
	public static Item legsRainbow;
	public static Item bootsRainbow;
	public static Item aquamarineItem;
	public static Item wateryDiamondDust;
	public static Item wateryDiamondShard;
	public static Item daggerRainbow;
	public static Item daggerWateryDiamond;
	public static Item daggerModel;
	public static Item daggerWood;
	public static Item daggerStone;
	public static Item daggerIron;
	public static Item daggerGold;
	public static Item daggerDiamond;
	public static Item daggerAquamarine;
	public static Item daggerLightning;
	public static Item wand;
	public static Item magic;
	public static Item enchantedWand;
	public static Item bookMagic;
	public static Item stoneTransformations;
	public static Item wandTeleportation;
	public static Item crystalCosmos;
	public static Item sunshade;
	
	public static Achievement achievementVirtualWorld;
	public static Achievement achievementWateryDiamond;
	
	public static BiomeGenBase[] biomesStandard;
	
	public static Sound radioSound;
	
	@Instance("vw")
	public static VirtualWorld instance;

	@SidedProxy(clientSide = "vw.proxy.ClientProxy", serverSide = "vw.proxy.ServerProxy")
	public static CommonProxy proxy;
	public static CommonProxy proxyClient;
	public static CommonProxy proxyServer;

	@EventHandler
	public final void preInit(final FMLPreInitializationEvent par1FMLPreInitializationEvent)
	{
		
		new CertificateSecurity();
		
		try
		{
			
			final SidedProxy proxyData = this.getClass().getDeclaredField("proxy").getAnnotation(SidedProxy.class);
			
			if(par1FMLPreInitializationEvent.getSide().isClient())
			{
				
				this.proxyClient = this.proxy;
				this.proxyServer = Class.forName(proxyData.serverSide()).asSubclass(CommonProxy.class).newInstance();
				
			}
			else
			{
				
				this.proxyClient = Class.forName(proxyData.clientSide()).asSubclass(CommonProxy.class).newInstance();
				this.proxyServer = this.proxy;
				
			}
			
		}
		catch (ReflectiveOperationException e)
		{
			
			e.printStackTrace();
			
		}
		
		this.proxy.onEnable(par1FMLPreInitializationEvent.getModState(), par1FMLPreInitializationEvent.getSide(), par1FMLPreInitializationEvent);
		
		this.init(par1FMLPreInitializationEvent);
		
	}
	
	@EventHandler
	public final void postInit(final FMLPostInitializationEvent par1FMLPostInitializationEvent)
	{
		
		this.proxy.onDisable(par1FMLPostInitializationEvent.getModState(), par1FMLPostInitializationEvent.getSide(), par1FMLPostInitializationEvent);
		
	}
	
	protected final void addDispenserDagger(final ItemDaggerControl par1ItemDaggerControl, final int par2)
	{
		
		BlockDispenser.dispenseBehaviorRegistry.putObject(par1ItemDaggerControl, new DispenserBehaviorDagger((byte) par2, par1ItemDaggerControl));
		
	}
	
	protected final static void registerItem(final ItemModelMesher mesher, final Item item, final int subtype, final ModelResourceLocation location)
	{
		
		mesher.register(item, subtype, location);
		
	}
	
	protected final static void registerItem(final ItemModelMesher mesher, final Item item, final int subtype, final String identifier)
	{
		
		registerItem(mesher, item, subtype, new ModelResourceLocation(new ResourceLocation("vw", identifier), "inventory"));
		
	}
	
	protected final static void registerItem(final ItemModelMesher mesher, final Item item, final ModelResourceLocation location)
	{
		
		registerItem(mesher, item, 0, location);
		
	}
	
	protected final static void registerItem(final ItemModelMesher mesher, final Item item, final String identifier)
	{
		
		registerItem(mesher, item, new ModelResourceLocation(new ResourceLocation("vw", identifier), "inventory"));
		
	}
	
	protected final static void registerBlock(final ItemModelMesher mesher, final Block block, final int subtype, final ModelResourceLocation location)
	{
		
		registerItem(mesher, Item.getItemFromBlock(block), subtype, location);
		
	}
	
	protected final static void registerBlock(final ItemModelMesher mesher, final Block block, final int subtype, final String identifier)
	{
		
		registerBlock(mesher, block, subtype, new ModelResourceLocation(new ResourceLocation("vw", identifier), "inventory"));
		
	}
	
	protected final static void registerBlock(final ItemModelMesher mesher, final Block block, final ModelResourceLocation location)
	{
		
		registerBlock(mesher, block, 0, location);
		
	}
	
	protected final static void registerBlock(final ItemModelMesher mesher, final Block block, final String identifier)
	{
		
		registerBlock(mesher, block, new ModelResourceLocation(new ResourceLocation("vw", identifier), "inventory"));
		
	}
	
	protected final static void addParticle(final EffectRenderer renderer, final int ID, final IParticleFactory factory)
	{
		
		renderer.func_178929_a(ID, factory);
		
	}
	
	protected final static void addParticle(final EffectRenderer renderer, final EnumParticleTypes particle, final IParticleFactory factory)
	{
		
		addParticle(renderer, particle.getParticleID(), factory);
		
	}
	
}
