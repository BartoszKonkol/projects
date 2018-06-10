package vw.loading.certificates;

import java.math.BigInteger;
import javax.jnf.exception.Defect;
import javax.jnf.importation.Certificate;
import org.reflections.Reflections;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;
import org.reflections.util.FilterBuilder;
import vw.util.DataTexts;

public final class CertificateSecurity extends Certificate
{

	public CertificateSecurity()
	{
		
		super(
				CertificateTypes.SECURITY,
				new BigInteger("88593031031776405425"),
				new String[] {"\u00A9 Bartosz Konkol"},
				new String[] {DataTexts.nameVW},
				CertificateFunctions.SECURITY_ECERTIFICATE, CertificateFunctions.SECURITY_SCAN
			);
		
		
		this.scan
				(new byte[]{
						// public static final byte SCAN = 1;
						// vw.*.SCAN,
					vw.util.DataCalculations.SCAN,
					vw.util.DataTexts.SCAN,
					vw.util.ElementsName.SCAN,
					vw.util.ListIdentifiers.SCAN,
					vw.util.SpecificationVersion.SCAN,
					vw.inventory.SlotAchievement.SCAN,
					1/* vw.elements.world.biome.BiomeGenData.SCAN */,
					1/* vw.elements.world.biome.BiomeGenMeltingValley.SCAN */,
					1/* vw.elements.world.biome.BiomeGenRainbowGlade.SCAN */,
					vw.block.BlockAquamarineblock.SCAN,
					vw.block.BlockAquamarineore.SCAN,
					vw.block.BlockBlackblock.SCAN,
					vw.block.BlockBlockkilling.SCAN,
					vw.block.BlockBlockofdarkmatter.SCAN,
					vw.block.BlockBlockofwhitematter.SCAN,
					vw.block.BlockFlowerControl.SCAN,
					vw.block.BlockImprovedrainbowblock.SCAN,
					vw.block.BlockMeltingsnow.SCAN,
					vw.block.BlockPlantblue.SCAN,
					vw.block.BlockRainbowblock.SCAN,
					vw.block.BlockSoftControl.SCAN,
					vw.block.BlockStairrainbowblock.SCAN,
					vw.block.BlockStairsControl.SCAN,
					vw.block.BlockTransparentblock.SCAN,
					vw.block.BlockWaterydiamond.SCAN,
					vw.block.BlockWhiteblock.SCAN,
					vw.entity.projectile.EntityDagger.SCAN,
					vw.entity.passive.EntityHuman.SCAN,
					vw.item.ItemAquamarineitem.SCAN,
					vw.item.ItemDarkmatter.SCAN,
					vw.item.ItemData.SCAN,
					vw.item.ItemWaterydiamond.SCAN,
					vw.item.ItemWaterydiamonddust.SCAN,
					vw.item.ItemWaterydiamondshard.SCAN,
					vw.item.ItemWhitedust.SCAN,
					vw.item.ItemWhitematter.SCAN,
					vw.item.EnumArmorMaterialControl.SCAN,
					vw.item.ItemArmorControl.SCAN,
					vw.item.ItemArmorData.SCAN,
					vw.item.ItemBootsRainbow.SCAN,
					vw.item.ItemHelmetRainbow.SCAN,
					vw.item.ItemLegsRainbow.SCAN,
					vw.item.ItemPlateRainbow.SCAN,
					vw.item.ItemBlood.SCAN,
					vw.item.ItemBloodbowl.SCAN,
					vw.item.ItemBloodpotion.SCAN,
					vw.item.ItemBrain.SCAN,
					vw.item.ItemFoodData.SCAN,
					vw.entity.monster.EntityHerobrine.SCAN,
					vw.entity.projectile.EntityDagger.SCAN,
					vw.item.ItemDaggerControl.SCAN,
					vw.item.ItemDaggerRainbow.SCAN,
					vw.item.ItemDaggerWateryDiamond.SCAN,
					vw.entity.projectile.EntityDaggerLightning.SCAN,
					vw.item.ItemDaggerAquamarine.SCAN,
					vw.item.ItemDaggerDiamond.SCAN,
					vw.item.ItemDaggerGold.SCAN,
					vw.item.ItemDaggerIron.SCAN,
					vw.item.ItemDaggerStone.SCAN,
					vw.item.ItemDaggerWood.SCAN,
					vw.block.BlockRadio.SCAN,
					vw.dispenser.DispenserBehaviorDagger.SCAN,
					vw.client.renderer.entity.RenderHerobrine.SCAN,
					vw.client.renderer.entity.RenderHuman.SCAN,
					vw.item.ItemDaggerLightning.SCAN,
					vw.dispenser.DispenserBehaviorDaggerLightning.SCAN,
					vw.item.ItemBrewingData.SCAN,
					vw.item.ItemEnchantedWand.SCAN,
					vw.item.ItemMagic.SCAN,
					vw.item.ItemWand.SCAN,
					vw.command.CommandData.SCAN,
					vw.command.CommandHerobrine.SCAN,
					vw.command.CommandRealTime.SCAN,
					vw.command.RealTime.SCAN,
					vw.block.BlockAquamarineDamagedBlock.SCAN,
					vw.block.BlockMagicBlock.SCAN,
					vw.block.BlockMagicRock.SCAN,
					vw.dispenser.DispenserBehaviorStoneTransformations.SCAN,
					vw.entity.projectile.EntityStoneTransformations.SCAN,
					vw.item.ItemBlockAquamarineDamagedBlock.SCAN,
					vw.item.ItemBookMagic.SCAN,
					vw.item.ItemStoneTransformations.SCAN,
					vw.item.ItemWandTeleportation.SCAN,
					vw.world.WorldProviderDreamland.SCAN,
					vw.world.biome.BiomeDreamlandDecorator.SCAN,
					1/* vw.elements.world.biome.BiomeGenDreamland.SCAN */,
					1/* vw.elements.world.biome.BiomeGenDreamlandBase.SCAN */,
					vw.world.biome.WorldChunkManagerDreamland.SCAN,
					vw.world.gen.ChunkProviderDreamland.SCAN,
					vw.util.Cocreators.SCAN,
					vw.util.ID.SCAN,
					vw.util.Language.SCAN,
					vw.item.ItemCrystalCosmos.SCAN,
					vw.util.ChestContent.SCAN,
					vw.world.gen.feature.WorldGeneratorOres.SCAN,
					vw.world.gen.structure.StructureGreatHallGenerate.SCAN,
					vw.world.gen.structure.StructuresNewGenerate.SCAN,
					vw.client.gui.GuiScreenVW.SCAN,
					vw.client.gui.GuiScreenMenu.SCAN,
					vw.client.gui.GuiScreenInformation.SCAN,
					vw.client.gui.GuiScreenConfiguration.SCAN,
					vw.util.Util.SCAN,
					vw.util.ThreadSub.SCAN,
					vw.item.ItemSubtypesControl.SCAN,
					vw.util.Sound.SCAN,
					vw.item.ItemSunshade.SCAN,
					vw.block.BlockSunshade.SCAN,
					vw.item.ItemBlockSunshade.SCAN,
					vw.item.ItemBlockControl.SCAN,
					vw.block.BlockSubControl.SCAN,
					vw.tileentity.TileEntitySunshade.SCAN,
					vw.client.particle.FactoryNoteColor.SCAN,
					vw.item.ItemArmorRainbowControl.SCAN,
				});
		
	}
	
	@Override
	protected final void setStart()
	{
		
		System.out.println("Certyfikat Zabezpiecze\u0144 modyfikacji " + DataTexts.versionVW);
		
	}
	
	@Override
	protected final void setError()
	{
		
		if(this.getBlock() && this.getCloseError())
		{
			
			System.err.println("Na skutek awarii, gra zostanie zamkni\u0119ta.");
			
			try
			{
				
				throw new Defect("Odpowied\u017a lub liczba klas jest nieprawid\u0142owa!");
				
			}
			catch (final Defect e)
			{
				
				System.err.println(e.toString());
				e.exit();
				
			}
			
		}
		
	}
	
	protected final void scan(final byte[] args1)
	{
		
		for(int i = 0; i < args1.length; i++)
			if(args1[i] != 1)
				this.getError();
		
		if(new Reflections(new ConfigurationBuilder().setScanners(new SubTypesScanner(false)).setUrls(ClasspathHelper.forClassLoader()).filterInputsBy(new FilterBuilder().include("vw\\..*"))).getStore().getValuesCount() != 138)
			this.getError();
		
	}
	
}
