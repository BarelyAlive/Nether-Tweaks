package mod.nethertweaks.items;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

import mod.nethertweaks.NTMBlocks;
import mod.nethertweaks.NTMItems;
import mod.nethertweaks.NetherTweaksMod;
import mod.sfhcore.Constants;
import mod.sfhcore.helper.FluidHelper;
import mod.sfhcore.helper.Tools;
import mod.sfhcore.items.ItemThing;
import mod.sfhcore.proxy.IVariantProvider;
import net.java.games.input.Keyboard;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.audio.Sound;
import net.minecraft.client.audio.SoundManager;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.client.event.sound.SoundEvent;
import net.minecraftforge.client.event.sound.SoundEvent.SoundSourceEvent;
import net.minecraftforge.common.ForgeModContainer;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fluids.UniversalBucket;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.ItemFluidContainer;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import scala.Int;

public class Crystal extends ItemThing implements IVariantProvider{
	
	public static final IBlockState bob = Blocks.OBSIDIAN.getDefaultState();
	
	public Crystal() {
		super(null, 1, NetherTweaksMod.tabNetherTweaksMod);
	}
	
	@Override
	public boolean hasEffect(ItemStack stack) {
		return true;
	}
	
	@Override
	public ActionResult<ItemStack> onItemRightClick(ItemStack itemStackIn, World worldIn, EntityPlayer playerIn,
			EnumHand hand) {
		
		if(playerIn.isSneaking()){
			if(playerIn.inventory.hasItemStack(new ItemStack(Items.WATER_BUCKET))){
				playerIn.inventory.clearMatchingItems(Items.WATER_BUCKET, 0, 1, null);
				playerIn.inventory.addItemStackToInventory(UniversalBucket.getFilledBucket(ForgeModContainer.getInstance().universalBucket, NTMItems.fluidDemonWater));
			}
			if(playerIn.inventory.hasItemStack(new ItemStack(NTMItems.bucketStoneWater))){
				playerIn.inventory.clearMatchingItems(NTMItems.bucketStoneWater, 0, 1, null);
				playerIn.inventory.addItemStackToInventory(new ItemStack(NTMItems.bucketStoneDemonWater));
			}
			if(playerIn.inventory.hasItemStack(new ItemStack(NTMItems.bucketWoodWater))){
				playerIn.inventory.clearMatchingItems(NTMItems.bucketWoodWater, 0, 1, null);
				playerIn.inventory.addItemStackToInventory(new ItemStack(NTMItems.bucketWoodDemonWater));
			}
		}
		
		//bless tanks etc.
		for(ItemStack stack : playerIn.inventory.mainInventory){
			FluidHelper.switchFluids(stack, FluidRegistry.WATER, NTMItems.fluidDemonWater);			
		}
		
		if (playerIn.isSneaking() && itemStackIn.getItem() == NTMItems.itemSanctuaryCrystal){
			
		
			//Structure ok?
								
			int x =  (int) playerIn.posX;
			int y =  (int) playerIn.posY;
			int z =  (int) playerIn.posZ;

			BlockPos pos = new BlockPos(x-1, y, z-1);
			
			//block 2, 3, 5, 6, 8, 9, 11, 12, 14, 15, 17, 18, 20, 21, 23, 24 equals Obsidian
			//block 1, 4, 7, 10, 13, 16, 19, 21 equals glowstone
			//block 0 is enchantment table
			//rest is dirt
			
			//Obsidian säulen
			if(bob != worldIn.getBlockState(pos.add(-1, 0, 3)))
			{
				System.out.println(worldIn.getBlockState(pos.add(-1, 0, 3)).getBlock().getUnlocalizedName());
				System.out.println(pos.add(-1, 0, 3));
				return new ActionResult<ItemStack>(EnumActionResult.FAIL, itemStackIn);

			}
			
			if(bob != worldIn.getBlockState(pos.add(1, 0, 3)))
				return new ActionResult<ItemStack>(EnumActionResult.FAIL, itemStackIn);
			
			if(bob != worldIn.getBlockState(pos.add(-1, 0, -3)))
				return new ActionResult<ItemStack>(EnumActionResult.FAIL, itemStackIn);
			
			if(bob != worldIn.getBlockState(pos.add(-1, 0, -3)))
				return new ActionResult<ItemStack>(EnumActionResult.FAIL, itemStackIn);
			
			if(bob != worldIn.getBlockState(pos.add(3, 0, -1)))
				return new ActionResult<ItemStack>(EnumActionResult.FAIL, itemStackIn);
			
			if(bob != worldIn.getBlockState(pos.add(3, 0, 1)))
				return new ActionResult<ItemStack>(EnumActionResult.FAIL, itemStackIn);
			
			if(bob != worldIn.getBlockState(pos.add(-3, 0, 1)))
				return new ActionResult<ItemStack>(EnumActionResult.FAIL, itemStackIn);
			
			if(bob != worldIn.getBlockState(pos.add(-3, 0, -1)))
				return new ActionResult<ItemStack>(EnumActionResult.FAIL, itemStackIn);
			
			
			if(bob != worldIn.getBlockState(pos.add(-1, 1, 3)))
				return new ActionResult<ItemStack>(EnumActionResult.FAIL, itemStackIn);
			
			if(bob != worldIn.getBlockState(pos.add(1, 1, 3)))
				return new ActionResult<ItemStack>(EnumActionResult.FAIL, itemStackIn);
			
			if(bob != worldIn.getBlockState(pos.add(-1, 1, -3)))
				return new ActionResult<ItemStack>(EnumActionResult.FAIL, itemStackIn);
			
			if(bob != worldIn.getBlockState(pos.add(1, 1, -3)))
				return new ActionResult<ItemStack>(EnumActionResult.FAIL, itemStackIn);
			
			if(bob != worldIn.getBlockState(pos.add(3, 1, -1)))
				return new ActionResult<ItemStack>(EnumActionResult.FAIL, itemStackIn);
			
			if(bob != worldIn.getBlockState(pos.add(3, 1, 1)))
				return new ActionResult<ItemStack>(EnumActionResult.FAIL, itemStackIn);
			
			if(bob != worldIn.getBlockState(pos.add(-3, 1, 1)))
				return new ActionResult<ItemStack>(EnumActionResult.FAIL, itemStackIn);
			
			if(bob != worldIn.getBlockState(pos.add(-3, 1, -1)))
				return new ActionResult<ItemStack>(EnumActionResult.FAIL, itemStackIn);
			System.out.println("Obsidian Säulen sind ok");
			
			//GoldBlocke
			if(Blocks.GLOWSTONE.getDefaultState() != worldIn.getBlockState(pos.add(-1, 2, 3)))
				return new ActionResult<ItemStack>(EnumActionResult.FAIL, itemStackIn);
			
			if(Blocks.GLOWSTONE.getDefaultState() != worldIn.getBlockState(pos.add(1, 2, 3)))
				return new ActionResult<ItemStack>(EnumActionResult.FAIL, itemStackIn);
			
			if(Blocks.GLOWSTONE.getDefaultState() != worldIn.getBlockState(pos.add(-1, 2, -3)))
				return new ActionResult<ItemStack>(EnumActionResult.FAIL, itemStackIn);
			
			if(Blocks.GLOWSTONE.getDefaultState() != worldIn.getBlockState(pos.add(1, 2, -3)))
				return new ActionResult<ItemStack>(EnumActionResult.FAIL, itemStackIn);
			
			if(Blocks.GLOWSTONE.getDefaultState() != worldIn.getBlockState(pos.add(3, 2, -1)))
				return new ActionResult<ItemStack>(EnumActionResult.FAIL, itemStackIn);
			
			if(Blocks.GLOWSTONE.getDefaultState() != worldIn.getBlockState(pos.add(3, 2, 1)))
				return new ActionResult<ItemStack>(EnumActionResult.FAIL, itemStackIn);
			
			if(Blocks.GLOWSTONE.getDefaultState() != worldIn.getBlockState(pos.add(-3, 2, 1)))
				return new ActionResult<ItemStack>(EnumActionResult.FAIL, itemStackIn);
			
			if(Blocks.GLOWSTONE.getDefaultState() != worldIn.getBlockState(pos.add(-3, 2, -1)))
				return new ActionResult<ItemStack>(EnumActionResult.FAIL, itemStackIn);
			System.out.println("Glowstone Säulen sind ok");

			
			//rows
				
			if(!Tools.checkBlockArea(worldIn, Blocks.DIRT, pos.add(-3, -1, 3), 7, 7, 0)){
				return new ActionResult<ItemStack>(EnumActionResult.FAIL, itemStackIn);
			}
				
			System.out.println("dirt ist ok");

			
			if(worldIn.getBlockState(pos).equals(Blocks.ENCHANTING_TABLE.getDefaultState())){
				
				EntityLightningBolt entitybolt = new EntityLightningBolt(worldIn, 0D, 0D, 0D, false);
				
				entitybolt.setLocationAndAngles(pos.getX()+1, pos.getY()+2, pos.getZ()-3, 0, 0.0F);	
				worldIn.spawnEntityInWorld(entitybolt);
				worldIn.setBlockState(pos.add(1, 2, -3), Blocks.FIRE.getDefaultState());
				
				entitybolt.setLocationAndAngles(pos.getX()-1, pos.getY()+2, pos.getZ()-3, 0, 0.0F);	
				worldIn.spawnEntityInWorld(entitybolt);
				worldIn.setBlockState(pos.add(-1, 2, -3), Blocks.FIRE.getDefaultState());
				
				entitybolt.setLocationAndAngles(pos.getX()+1, pos.getY()+2, pos.getZ()+3, 0, 0.0F);	
				worldIn.spawnEntityInWorld(entitybolt);
				worldIn.setBlockState(pos.add(1, 2, 3), Blocks.FIRE.getDefaultState());
				
				entitybolt.setLocationAndAngles(pos.getX()-1, pos.getY()+2, pos.getZ()+3, 0, 0.0F);	
				worldIn.spawnEntityInWorld(entitybolt);
				worldIn.setBlockState(pos.add(-1, 2, 3), Blocks.FIRE.getDefaultState());
				
				entitybolt.setLocationAndAngles(pos.getX()+3, pos.getY()+2, pos.getZ()+1, 0, 0.0F);	
				worldIn.spawnEntityInWorld(entitybolt);
				worldIn.setBlockState(pos.add(3, 2, 1), Blocks.FIRE.getDefaultState());
				
				entitybolt.setLocationAndAngles(pos.getX()-3, pos.getY()+2, pos.getZ()-1, 0, 0.0F);	
				worldIn.spawnEntityInWorld(entitybolt);
				worldIn.setBlockState(pos.add(3, 2, -1), Blocks.FIRE.getDefaultState());
				
				entitybolt.setLocationAndAngles(pos.getX()-3, pos.getY()+2, pos.getZ()+1, 0, 0.0F);	
				worldIn.spawnEntityInWorld(entitybolt);
				worldIn.setBlockState(pos.add(-3, 2, 1), Blocks.FIRE.getDefaultState());
				
				entitybolt.setLocationAndAngles(pos.getX()-3, pos.getY()+2, pos.getZ()-1, 0, 0.0F);	
				worldIn.spawnEntityInWorld(entitybolt);
				worldIn.setBlockState(pos.add(-3, 2, -1), Blocks.FIRE.getDefaultState());
				
			}else{
				return new ActionResult<ItemStack>(EnumActionResult.FAIL, itemStackIn);
			}
			
			//Auswirkungen
			
			Tools.setBlockArea(worldIn, NTMBlocks.holyearth, pos.add(-3, -1, 3), 7, 7, 0);

				if(!playerIn.capabilities.isCreativeMode){
					playerIn.inventory.clearMatchingItems(NTMItems.itemSanctuaryCrystal, 0, 1, null);
				}
			}
			return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, itemStackIn);
	
	}

	@Override
    public List<Pair<Integer, String>> getVariants()
    {
        List<Pair<Integer, String>> ret = new ArrayList<Pair<Integer, String>>();
            ret.add(new ImmutablePair<Integer, String>(0, "type=normal"));
        return ret;
    }
}
