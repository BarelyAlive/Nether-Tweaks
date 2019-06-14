package mod.nethertweaks.blocks;
 
import java.util.Random;

import javax.annotation.Nullable;

import mod.nethertweaks.INames;
import mod.nethertweaks.NetherTweaksMod;
import mod.nethertweaks.blocks.tile.TileBarrel;
import mod.nethertweaks.blocks.tile.TileCondenser;
import mod.nethertweaks.blocks.tile.TileHellmart;
import mod.nethertweaks.blocks.tile.TileNetherrackFurnace;
import mod.nethertweaks.handler.BlockHandler;
import mod.nethertweaks.handler.GuiHandlerNTM;
import mod.sfhcore.blocks.CubeContainerHorizontal;
import mod.sfhcore.handler.GuiHandler;
import mod.sfhcore.network.MessageCheckLight;
import mod.sfhcore.network.NetworkHandler;
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
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import p455w0rdslib.util.ChunkUtils;
 
public class NetherrackFurnace extends CubeContainerHorizontal {
     
    public static final PropertyDirection FACING = BlockHorizontal.FACING;
    public static final PropertyBool ISBURNING = PropertyBool.create("is_burning");

    public NetherrackFurnace()
    {
        super(Material.ROCK, new ResourceLocation(NetherTweaksMod.MODID, INames.NETHERRACK_FURNACE));
        this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH).withProperty(ISBURNING, false));
        setResistance(17.5F);
        setHardness(3.5F);
        setCreativeTab(NetherTweaksMod.TABNTM);
    }
    
    @Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand,
			EnumFacing side, float hitX, float hitY, float hitZ)
    {
    	if(!world.isBlockLoaded(pos)) return false;
    	if(world.isRemote) return true;
    	if(player.isSneaking()) return false;
    	  	
		TileEntity te = world.getTileEntity(pos);
		if(te ==  null) return false;
		if(!(te instanceof TileNetherrackFurnace)) {
			return false;
		}
		
		player.openGui(NetherTweaksMod.instance, GuiHandlerNTM.idFurnace, world, pos.getX(), pos.getY(), pos.getZ());
		return true;
    }
    
    @Override
    public int getLightValue(IBlockState state, IBlockAccess world, BlockPos pos)
    { 	
    	if(state.getValue(ISBURNING)) return 12;
    	return 0;
    }
    
    public static void setState(boolean active, World worldIn, BlockPos pos)
    {
    	IBlockState b = worldIn.getBlockState(pos);
    	if(b == Blocks.AIR.getDefaultState()) return;
        TileEntity tileentity = worldIn.getTileEntity(pos);
        if(tileentity == null) return;
        TileNetherrackFurnace furnace = (TileNetherrackFurnace) tileentity;
    	
        //Only do this, if something has changed
		if(active && !b.getValue(ISBURNING)) {
	        b = b.withProperty(ISBURNING, true);
	        worldIn.setBlockState(pos, b, 3);
	        validate(worldIn, pos, furnace);
		}
		else if(!active && b.getValue(ISBURNING)) {
    		b = b.withProperty(ISBURNING, false);
	        worldIn.setBlockState(pos, b, 3);
	        validate(worldIn, pos, furnace);
    	}
    }
    
    private static void validate(World world, BlockPos pos, TileNetherrackFurnace furnace)
    {
    	if (furnace != null)
        {
            furnace.validate();
            world.setTileEntity(pos, furnace);
            NetworkHandler.sendToAllAround(new MessageCheckLight(pos), furnace);
        }
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
    protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, new IProperty[] {FACING, ISBURNING});
    }
    
    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new TileNetherrackFurnace();
    }
}