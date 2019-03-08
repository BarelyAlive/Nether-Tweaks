package mod.nethertweaks.blocks;

import java.util.Random;

import mod.nethertweaks.INames;
import mod.nethertweaks.NTMBlocks;
import mod.nethertweaks.NTMItems;
import mod.nethertweaks.NetherTweaksMod;
import mod.nethertweaks.blocks.tileentities.TileEntityNetherrackFurnace;
import mod.sfhcore.blocks.Cube;
import net.minecraft.block.Block;
import net.minecraft.block.BlockDirt;
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
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class HolyEarth extends Cube{

	public static int[] EntityIDList = {50, 51, 52, 54, 55, 58, 59, 66, 65, 98, 91, 92, 93, 95, 98, 100, 120};
	private int EntityID;
	private int minSpawnSec = 10;
	private Entity ent;
	private int spawnTick = 0;
	private int spawnSec;
	
	public HolyEarth() {
		super(Material.GRASS, 3.0F, 0.6F, NetherTweaksMod.tabNetherTweaksMod);
		setCreativeTab(NetherTweaksMod.tabNetherTweaksMod);
		setLightLevel(15);
	}
	
	@Override
	public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
		if(!worldIn.isRemote && !worldIn.getBlockState(pos.add(0, 1, 0)).isOpaqueCube() 
				&& worldIn.getBlockState(pos.add(0, 1, 0)) != NTMItems.blockDemonWater){
			if(spawnTick <= 0){
				spawnSec = rand.nextInt(5);
				spawnTick = (spawnSec*20) + (minSpawnSec*20);
				
			}
			if(spawnTick == 1){
				EntityID = rand.nextInt(EntityIDList.length);
				ent = EntityList.createEntityByID(EntityIDList[EntityID], worldIn);
				ent.setLocationAndAngles(pos.getX()+0.5d, pos.getY()+1.0d, pos.getZ()+0.5d, MathHelper.wrapDegrees(180.0F), state.getBlockHardness(worldIn, pos));
				if(!ent.isInsideOfMaterial(Material.AIR)){
					worldIn.scheduleBlockUpdate(pos, this, 20, 0);
					return;
				}
				worldIn.spawnEntityInWorld(ent);
			}
			spawnTick--;
		}
		if(worldIn.getBlockState(pos.add(0, 1, 0)).isSideSolid(worldIn, pos, EnumFacing.UP)){
			worldIn.setBlockState(pos, Blocks.DIRT.getDefaultState());
		}
		
		//Spread the block
		if (!worldIn.isRemote)
        {
            if (worldIn.getLightFromNeighbors(pos.up()) < 4 && worldIn.getBlockState(pos.up()).getLightOpacity(worldIn, pos.up()) > 2)
            {
                worldIn.setBlockState(pos, Blocks.DIRT.getDefaultState());
            }
            else
            {
                if (worldIn.getLightFromNeighbors(pos.up()) >= 9)
                {
                    for (int i = 0; i < 4; ++i)
                    {
                        BlockPos blockpos = pos.add(rand.nextInt(3) - 1, rand.nextInt(5) - 3, rand.nextInt(3) - 1);

                        if (blockpos.getY() >= 0 && blockpos.getY() < 256 && !worldIn.isBlockLoaded(blockpos))
                        {
                            return;
                        }

                        IBlockState iblockstate = worldIn.getBlockState(blockpos.up());
                        IBlockState iblockstate1 = worldIn.getBlockState(blockpos);

                        if (iblockstate1.getBlock() == Blocks.DIRT && iblockstate1.getValue(BlockDirt.VARIANT) == BlockDirt.DirtType.DIRT && worldIn.getLightFromNeighbors(blockpos.up()) >= 4 && iblockstate.getLightOpacity(worldIn, pos.up()) <= 2)
                        {
                            worldIn.setBlockState(blockpos, this.getDefaultState());
                        }
                    }
                }
            }
        }
		worldIn.scheduleBlockUpdate(pos, this, 20, 0);
	}
		
	@SideOnly(Side.CLIENT)
	@Override
    public BlockRenderLayer getBlockLayer()
    {
        return BlockRenderLayer.CUTOUT_MIPPED;
    }
	
	@Override
	public boolean canCreatureSpawn(IBlockState state, IBlockAccess world, BlockPos pos, SpawnPlacementType type) {
		return true;
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