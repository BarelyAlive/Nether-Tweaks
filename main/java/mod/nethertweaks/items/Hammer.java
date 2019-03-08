package mod.nethertweaks.items;

import ibxm.Player;
import mod.nethertweaks.Konstanten;
import mod.nethertweaks.NTMBlocks;
import mod.nethertweaks.NTMItems;
import mod.nethertweaks.NetherTweaksMod;
import mod.nethertweaks.handler.RecipeHandler;
import mod.sfhcore.proxy.IVariantProvider;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

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

public class Hammer extends ItemTool implements IVariantProvider{
	
	public static final Set blocksEffectiveAgainst = Sets.newHashSet(new Block[] {Blocks.STONE, Blocks.COBBLESTONE, Blocks.GRAVEL, Blocks.SAND, Blocks.OBSIDIAN, Blocks.NETHERRACK, Blocks.ICE});
	EntityItem entityItem;
	
	public Hammer(float effective, ToolMaterial tool) {
		
		super(effective, -2.8F, tool, blocksEffectiveAgainst);
		
		setCreativeTab(NetherTweaksMod.tabNetherTweaksMod);
		setMaxStackSize(1);
		if(tool == ToolMaterial.STONE){
			setMaxDamage(204);
		}
		if(tool == ToolMaterial.GOLD){
			setMaxDamage(105);	
				}
		if(tool == ToolMaterial.IRON){
			setMaxDamage(323);
		}
		if(tool == ToolMaterial.DIAMOND){
			setMaxDamage(1634);
		}
	}
	
	@Override
	public boolean onBlockStartBreak(ItemStack itemstack, BlockPos pos, EntityPlayer player) {
		World worldIn = player.worldObj;
		
		if(!player.worldObj.isRemote) {

			Block block = worldIn.getBlockState(pos).getBlock();
			
			if(block == Blocks.COBBLESTONE) {
				entityItem = new EntityItem(worldIn, pos.getX()+0.5D, pos.getY()+0.5D, pos.getZ()+0.5D, new ItemStack(Blocks.GRAVEL, 1));
				worldIn.destroyBlock(pos, false);
				worldIn.spawnEntityInWorld(entityItem);
			}
			
			if(block == Blocks.GRAVEL) {
				entityItem = new EntityItem(worldIn, pos.getX()+0.5D, pos.getY()+0.5D, pos.getZ()+0.5D, new ItemStack(Blocks.SAND, 1));
				worldIn.destroyBlock(pos, false);
				worldIn.spawnEntityInWorld(entityItem);
			}
			
			if(block == Blocks.SAND) {
				entityItem = new EntityItem(worldIn, pos.getX()+0.5D, pos.getY()+0.5D, pos.getZ()+0.5D, new ItemStack(NTMBlocks.blockDust, 1));
				worldIn.destroyBlock(pos, false);
				worldIn.spawnEntityInWorld(entityItem);			}
			
			if(block == Blocks.NETHERRACK) {
				entityItem = new EntityItem(worldIn, pos.getX()+0.5D, pos.getY()+0.5D, pos.getZ()+0.5D, new ItemStack(Blocks.GRAVEL, 1));
				worldIn.destroyBlock(pos, false);
				worldIn.spawnEntityInWorld(entityItem);
			}
			
			if(block == Blocks.STONE) {
				entityItem = new EntityItem(worldIn, pos.getX()+0.5D, pos.getY()+0.5D, pos.getZ()+0.5D, new ItemStack(Blocks.COBBLESTONE, 1));
				worldIn.destroyBlock(pos, false);
				worldIn.spawnEntityInWorld(entityItem);
			}
			
			if(block == Blocks.ICE) {
				entityItem = new EntityItem(worldIn, pos.getX()+0.5D, pos.getY()+0.5D, pos.getZ()+0.5D, new ItemStack(Blocks.SNOW, 1));
				worldIn.destroyBlock(pos, false);
				worldIn.spawnEntityInWorld(entityItem);
			}
			
			if(block == Blocks.OBSIDIAN) {
				entityItem = new EntityItem(worldIn, pos.getX()+0.5D, pos.getY()+0.5D, pos.getZ()+0.5D, new ItemStack(Konstanten.OBSIDIANDUST.getItem(), 4, Konstanten.OBSIDIANDUST.getItemDamage()));
				worldIn.destroyBlock(pos, false);
				worldIn.spawnEntityInWorld(entityItem);
			}
			return true;
		}
		if(!player.capabilities.isCreativeMode)
			itemstack.damageItem(1, player);
		return false;
	}
	
	@Override
    public List<Pair<Integer, String>> getVariants()
    {
        List<Pair<Integer, String>> ret = new ArrayList<Pair<Integer, String>>();
            ret.add(new ImmutablePair<Integer, String>(0, "type=normal"));
        return ret;
    }
	
}
