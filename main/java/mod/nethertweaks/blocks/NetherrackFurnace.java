package mod.nethertweaks.blocks;
 
import java.util.Random;

import javax.annotation.Nullable;

import mod.nethertweaks.NetherTweaksMod;
import mod.nethertweaks.blocks.tile.TileCondenser;
import mod.nethertweaks.blocks.tile.TileNetherrackFurnace;
import mod.nethertweaks.handler.BlockHandler;
import mod.nethertweaks.interfaces.INames;
import mod.sfhcore.blocks.CubeContainerHorizontal;
import mod.sfhcore.handler.GuiHandler;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.stats.StatList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.Mirror;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Rotation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
 
public class NetherrackFurnace extends CubeContainerHorizontal {
     
    public static final PropertyDirection FACING = BlockHorizontal.FACING;
    private static boolean keepInventory;
    public static final PropertyBool ISBURNING = PropertyBool.create("is_burning");

    public NetherrackFurnace()
    {
        super(Material.ROCK, new ResourceLocation("nethertweaksmod", INames.NETHERRACKFURNACE));
        this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH).withProperty(ISBURNING, false));
        setResistance(17.5F);
        setHardness(3.5F);
        setCreativeTab(NetherTweaksMod.tabNTM);
        setUnlocalizedName(INames.NETHERRACKFURNACE);
    }

    @SideOnly(Side.CLIENT)
    @SuppressWarnings("incomplete-switch")
    public void randomDisplayTick(IBlockState stateIn, World worldIn, BlockPos pos, Random rand)
    {
        if (stateIn.getValue(ISBURNING))
        {
            EnumFacing enumfacing = (EnumFacing)stateIn.getValue(FACING);
            double d0 = (double)pos.getX() + 0.5D;
            double d1 = (double)pos.getY() + rand.nextDouble() * 6.0D / 16.0D;
            double d2 = (double)pos.getZ() + 0.5D;
            double d3 = 0.52D;
            double d4 = rand.nextDouble() * 0.6D - 0.3D;

            if (rand.nextDouble() < 0.1D)
            {
                worldIn.playSound((double)pos.getX() + 0.5D, (double)pos.getY(), (double)pos.getZ() + 0.5D, SoundEvents.BLOCK_FURNACE_FIRE_CRACKLE, SoundCategory.BLOCKS, 1.0F, 1.0F, false);
            }

            switch (enumfacing)
            {
                case WEST:
                    worldIn.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, d0 - d3, d1, d2 + d4, 0.0D, 0.0D, 0.0D, new int[0]);
                    worldIn.spawnParticle(EnumParticleTypes.FLAME, d0 - d3, d1, d2 + d4, 0.0D, 0.0D, 0.0D, new int[0]);
                    break;
                case EAST:
                    worldIn.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, d0 + d3, d1, d2 + d4, 0.0D, 0.0D, 0.0D, new int[0]);
                    worldIn.spawnParticle(EnumParticleTypes.FLAME, d0 + d3, d1, d2 + d4, 0.0D, 0.0D, 0.0D, new int[0]);
                    break;
                case NORTH:
                    worldIn.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, d0 + d4, d1, d2 - d3, 0.0D, 0.0D, 0.0D, new int[0]);
                    worldIn.spawnParticle(EnumParticleTypes.FLAME, d0 + d4, d1, d2 - d3, 0.0D, 0.0D, 0.0D, new int[0]);
                    break;
                case SOUTH:
                    worldIn.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, d0 + d4, d1, d2 + d3, 0.0D, 0.0D, 0.0D, new int[0]);
                    worldIn.spawnParticle(EnumParticleTypes.FLAME, d0 + d4, d1, d2 + d3, 0.0D, 0.0D, 0.0D, new int[0]);
                    break;
            }
        }
    }
    
    @Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand,
			EnumFacing side, float hitX, float hitY, float hitZ) {
    	if (world.isBlockLoaded(pos)) {
			if (!world.isRemote) {
				// ...
				if (player.isSneaking()) {
					// ...
				} else {
					TileEntity te = world.getTileEntity(pos);

					if (te instanceof TileNetherrackFurnace)
						player.openGui(NetherTweaksMod.instance, 0, world, pos.getX(), pos.getY(), pos.getZ());
				}
			}
			return true;
		}
    	return false;
    }
    
    public static void setState(boolean active, World worldIn, BlockPos pos)
    {
    	IBlockState b = worldIn.getBlockState(pos);
        TileEntity tileentity = worldIn.getTileEntity(pos);
    	
    	if(b != Blocks.AIR.getDefaultState()) {
    		if(active && b.getValue(ISBURNING) == false) {
    	   		b.getBlock().setLightLevel(0.875F);
    	        b = b.withProperty(ISBURNING, true);
    	        worldIn.setBlockState(pos, b, 3);
    	        worldIn.setBlockState(pos, b, 3);
    		}
    		else if(!active && b.getValue(ISBURNING) == true) {
        		b.getBlock().setLightLevel(0.0F);
        		b = b.withProperty(ISBURNING, false);
    	        worldIn.setBlockState(pos, b, 3);
    	        worldIn.setBlockState(pos, b, 3);
        	}
    	}
    	
        if (tileentity != null)
        {
            tileentity.validate();
            worldIn.setTileEntity(pos, tileentity);
        }
    }

    @Override
    protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, new IProperty[] {FACING, ISBURNING});
    }
    
    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new TileNetherrackFurnace("netherrack_furnace");
    }
}