package mod.nethertweaks.blocks;

import java.util.Random;

import mod.nethertweaks.INames;
import mod.nethertweaks.NetherTweaksMod;
import mod.nethertweaks.blocks.tileentities.TileEntityNetherrackFurnace;
import mod.nethertweaks.handler.BlockHandler;
import mod.nethertweaks.handler.BucketNFluidHandler;
import mod.sfhcore.blocks.Cube;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.entity.EntityLiving.SpawnPlacementType;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTool;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class HolyEarth extends Cube{

	public static int[] EntityIDList = {4, 23, 31, 50, 51, 52, 54, 55, 58, 59, 66, 65, 68, 98, 91, 92, 93, 94, 95, 98, 100, 101, 102, 103, 105, 120};
	public static int[]EntityWaterID = {4, 23, 68, 94};
	private int EntityID;
	private int minSpawnSec = 3;
	private Entity ent;
	private int spawnTick = 0;
	private int spawnSec;
	
	public HolyEarth() {
		super(Material.GRASS, 3.0F, 0.6F, 1, NetherTweaksMod.tabNTM, new ResourceLocation("nethertweaksmod", INames.HOLYEARTH));
		setCreativeTab(NetherTweaksMod.tabNTM);
		setLightLevel(15);
	}
	
	@Override
	public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
		if(!worldIn.isRemote && !worldIn.getBlockState(pos.add(0, 1, 0)).isOpaqueCube() 
				&& worldIn.getBlockState(pos.add(0, 1, 0)) != BucketNFluidHandler.blockDemonWater){
			if(spawnTick <= 0){
				spawnSec = rand.nextInt(5);
				spawnTick = (spawnSec*20) + (minSpawnSec*20);
				
			}
			if(spawnTick == 1){
				EntityID = rand.nextInt(EntityIDList.length);

				for(int id : EntityWaterID) {
					if(worldIn.getBlockState(pos.add(0, 1, 0)) == BucketNFluidHandler.blockDemonWater && id == EntityID) {
						ent = EntityList.createEntityByID(EntityIDList[EntityID], worldIn);
						ent.setLocationAndAngles(pos.getX()+0.5d, pos.getY()+1.0d, pos.getZ()+0.5d, MathHelper.wrapDegrees(180.0F), state.getBlockHardness(worldIn, pos));
						
						if(!ent.isInsideOfMaterial(Material.AIR)){
							worldIn.scheduleBlockUpdate(pos, this, 20, 0);
							return;
						}
						worldIn.spawnEntity(ent);
					}
					else {
						ent = EntityList.createEntityByID(EntityIDList[EntityID], worldIn);
						ent.setLocationAndAngles(pos.getX()+0.5d, pos.getY()+1.0d, pos.getZ()+0.5d, MathHelper.wrapDegrees(180.0F), state.getBlockHardness(worldIn, pos));
						
						if(!ent.isInsideOfMaterial(Material.AIR)){
							worldIn.scheduleBlockUpdate(pos, this, 20, 0);
							return;
						}
						worldIn.spawnEntity(ent);
					}
				}
				spreadHoly(worldIn, pos, rand);
				
			}
			spawnTick--;
		}
		if(worldIn.getBlockState(pos) != BucketNFluidHandler.blockDemonWater) {
			if(worldIn.getBlockState(pos.add(0, 1, 0)).isSideSolid(worldIn, pos, EnumFacing.UP)){
				worldIn.setBlockState(pos, Blocks.DIRT.getDefaultState());
			}
		}
		worldIn.scheduleBlockUpdate(pos, this, 20, 0);
	}
		
	@Override
	public boolean canCreatureSpawn(IBlockState state, IBlockAccess world, BlockPos pos, SpawnPlacementType type) {
		return true;
	}
	
	public void spreadHoly(World world, BlockPos pos, Random rand){
		int i = rand.nextInt(8);
		switch(i){
		case 0:
			Block block = world.getBlockState(pos.add(-1, 0, 0)).getBlock();
			if(block.equals(Blocks.DIRT) || block.equals(Blocks.GRASS)){
			world.setBlockState(pos.add(-1, 0, 0), BlockHandler.HOLYEARTH.getDefaultState());
			}
			return;
		
		case 1:
			Block block2 = world.getBlockState(pos.add(0, 0, -1)).getBlock();
			if(block2.equals(Blocks.DIRT) || block2.equals(Blocks.GRASS)){
			world.setBlockState(pos.add(0, 0, -1), BlockHandler.HOLYEARTH.getDefaultState());
			}
			return;
		
		case 2:
			Block block3 = world.getBlockState(pos.add(+1, 0, 0)).getBlock();
			if(block3.equals(Blocks.DIRT) || block3.equals(Blocks.GRASS)){
			world.setBlockState(pos.add(+1, 0, 0), BlockHandler.HOLYEARTH.getDefaultState());
			}
			return;
		
		case 3:
			Block block4 = world.getBlockState(pos.add(0, 0, +1)).getBlock();
			if(block4.equals(Blocks.DIRT) || block4.equals(Blocks.GRASS)){
			world.setBlockState(pos.add(0, 0, +1), BlockHandler.HOLYEARTH.getDefaultState());
			}
			return;
		
		case 4:
			Block block5 = world.getBlockState(pos.add(-1, 0, -1)).getBlock();
			if(block5.equals(Blocks.DIRT) || block5.equals(Blocks.GRASS)){
			world.setBlockState(pos.add(-1, 0, -1), BlockHandler.HOLYEARTH.getDefaultState());
			}
			return;
		
		case 5:
			Block block6 = world.getBlockState(pos.add(+1, 0, -1)).getBlock();
			if(block6.equals(Blocks.DIRT) || block6.equals(Blocks.GRASS)){
			world.setBlockState(pos.add(+1, 0, -1), BlockHandler.HOLYEARTH.getDefaultState());
			}
			return;
		
		case 6:
			Block block7 = world.getBlockState(pos.add(-1, 0, +1)).getBlock();
			if(block7.equals(Blocks.DIRT) || block7.equals(Blocks.GRASS)){
			world.setBlockState(pos.add(-1, 0, +1), BlockHandler.HOLYEARTH.getDefaultState());
			}
			return;
		
		case 7:
			Block block8 = world.getBlockState(pos.add(+1, 0, +1)).getBlock();
			if(block8.equals(Blocks.DIRT) || block8.equals(Blocks.GRASS)){
			world.setBlockState(pos.add(+1, 0, +1), BlockHandler.HOLYEARTH.getDefaultState());
			}
			return;
		
		default:
			return;	
		}
	}
	
	@Override
	public Item getItemDropped(IBlockState state, Random rand, int fortune) {
		return null;
	}
	
	@Override
	public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state) {
		worldIn.scheduleBlockUpdate(pos, this, 20, this.EntityID);
		super.onBlockAdded(worldIn, pos, state);
	}
	
	@Override
	protected boolean canSilkHarvest() {
		return true;
	}
		     
    @Override
    public boolean isFireSource(World world, BlockPos pos, EnumFacing side) {
    	return true;
    }
		     
	}