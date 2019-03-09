package mod.nethertweaks.items;

import ibxm.Player;
import mod.nethertweaks.NetherTweaksMod;
import mod.nethertweaks.compatibility.MinefactoryReloaded;
import mod.nethertweaks.handler.BlockHandler;
import mod.nethertweaks.handler.ItemHandler;
import mod.nethertweaks.handler.RecipeHandler;

import java.util.Random;
import java.util.Set;
import com.google.common.collect.Sets;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTool;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.OreDictionary.OreRegisterEvent;

public class Hammer extends ItemTool {
	
	public static final Set blocksEffectiveAgainst = Sets.newHashSet(new Block[] {Blocks.STONE, Blocks.COBBLESTONE, Blocks.GRAVEL, Blocks.SAND, Blocks.NETHERRACK, Blocks.ICE});
	EntityItem entityItem;
	
	public Hammer(float effective, ToolMaterial tool) {
		
		super(effective, effective, tool, blocksEffectiveAgainst);
		
		setCreativeTab(NetherTweaksMod.tabNetherTweaksMod);
		setMaxStackSize(1);	
	}
	
	@Override
	public boolean onBlockDestroyed(ItemStack stack, World worldIn, IBlockState state, BlockPos pos,
			EntityLivingBase entityLiving) {
			
			EntityPlayer player = (EntityPlayer) entityLiving;

			if(!player.world.isRemote) {
	
			Block block = worldIn.getBlockState(pos).getBlock();
			
			if(block == Blocks.COBBLESTONE) {
				entityItem = new EntityItem(worldIn, pos.getX()+0.5D, pos.getY()+0.5D, pos.getZ()+0.5D, new ItemStack(Blocks.GRAVEL, 1));
				worldIn.spawnEntity(entityItem);
			}
			
			if(block == Blocks.GRAVEL) {
				entityItem = new EntityItem(worldIn, pos.getX()+0.5D, pos.getY()+0.5D, pos.getZ()+0.5D, new ItemStack(Blocks.SAND, 1));
				worldIn.spawnEntity(entityItem);
			}
			
			if(block == Blocks.SAND) {
				entityItem = new EntityItem(worldIn, pos.getX()+0.5D, pos.getY()+0.5D, pos.getZ()+0.5D, new ItemStack(BlockHandler.blockDust, 1));
				worldIn.spawnEntity(entityItem);
			}
			
			if(block == Blocks.NETHERRACK) {
				entityItem = new EntityItem(worldIn, pos.getX()+0.5D, pos.getY()+0.5D, pos.getZ()+0.5D, new ItemStack(Blocks.GRAVEL, 1));
				worldIn.spawnEntity(entityItem);
			}
			
			if(block == Blocks.STONE) {
				entityItem = new EntityItem(worldIn, pos.getX()+0.5D, pos.getY()+0.5D, pos.getZ()+0.5D, new ItemStack(Blocks.COBBLESTONE, 1));
				worldIn.spawnEntity(entityItem);
			}
			
			if(block == Blocks.ICE) {
				entityItem = new EntityItem(worldIn, pos.getX()+0.5D, pos.getY()+0.5D, pos.getZ()+0.5D, new ItemStack(Blocks.SNOW, 1));
				worldIn.spawnEntity(entityItem);
			}
			
			if(block == Blocks.OBSIDIAN) {
				entityItem = new EntityItem(worldIn, pos.getX()+0.5D, pos.getY()+0.5D, pos.getZ()+0.5D, new ItemStack(ItemHandler.itemBase, 4, 14));
				worldIn.spawnEntity(entityItem);
			}
			
			if(stack.getItemDamage() < stack.getMaxDamage()){
				stack.damageItem(1, player);
			}else{
				stack = null;
			}
		}
		return true;
	}
	
}
