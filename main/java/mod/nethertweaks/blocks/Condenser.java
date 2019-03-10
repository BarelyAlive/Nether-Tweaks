package mod.nethertweaks.blocks;
 
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

import mod.nethertweaks.INames;
import mod.nethertweaks.NetherTweaksMod;
import mod.nethertweaks.blocks.tileentities.TileEntityBarrel;
import mod.nethertweaks.blocks.tileentities.TileEntityCondenser;
import mod.sfhcore.handler.RegisterTileEntity;
import mod.sfhcore.proxy.IVariantProvider;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;
 
public class Condenser extends BlockContainer implements IVariantProvider{
     
    public static TileEntityCondenser tecondenser = new TileEntityCondenser("condenser");

    public Condenser() {
        super(Material.ROCK);
        setUnlocalizedName(INames.CONDENSER);
        setResistance(30.0f);
        setHardness(4.0f);
        setCreativeTab(NetherTweaksMod.tabNetherTweaksMod);
        RegisterTileEntity.add(this, new TileEntityCondenser(INames.TECONDENSER));
    }

    /*
    @SideOnly(Side.CLIENT)
    @Override
    public void registerBlockIcons(IIconRegister p_149651_1_)
    {
        this.blockIcon = p_149651_1_.registerIcon("NetherTweaksMod:BlockCondenser");
        this.field_149936_O = p_149651_1_.registerIcon("NetherTweaksMod:BlockCondenser_front");
        this.field_149935_N = p_149651_1_.registerIcon("NetherTweaksMod:BlockCondenser");
    }
    */
    
    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn,
    		EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
    	if(!worldIn.isRemote) {
            if(worldIn.getTileEntity(pos) != null && !worldIn.provider.isSurfaceWorld()) {
                playerIn.openGui(NetherTweaksMod.instance, 0, worldIn, pos.getX(), pos.getY(), pos.getZ());
            }
            return true;
        }
        return true;
    }
     
    @Override
    public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
    	dropItems(worldIn, pos.getX(), pos.getY(), pos.getZ());
    	super.breakBlock(worldIn, pos, state);
    }
     
    private void dropItems(World world, int x, int y, int z) {
        Random rand = new Random();
         
        TileEntity tileEntity = world.getTileEntity(new BlockPos(x, y, z));
        if(!(tileEntity instanceof IInventory)) {
            return;
        }
         
        IInventory inventory = (IInventory) tileEntity;
         
        for(int i = 0; i < inventory.getSizeInventory(); i++) {
            ItemStack item = inventory.getStackInSlot(i);
             
            if(item != null && item.getCount() > 0) {
                float rx = rand.nextFloat() *0.8F + 0.1F;
                float ry = rand.nextFloat() *0.8F + 0.1F;
                float rz = rand.nextFloat() *0.8F + 0.1F;
                 
                EntityItem entityItem = new EntityItem(world, x + rx, y + ry, z + rz, new ItemStack(item.getItem(), item.getCount(), item.getItemDamage()));
                 
                if(item.hasTagCompound()) {
                    entityItem.getItem().setTagCompound((NBTTagCompound) item.getTagCompound().copy());
                }
             
                float factor = 0.05F;
                entityItem.motionX = rand.nextGaussian() * factor;
                entityItem.motionY = rand.nextGaussian() * factor + 0.2F;
                entityItem.motionZ = rand.nextGaussian() * factor;
                world.spawnEntity(entityItem);
                item.setCount(0);
            }
        }
    }

    @Override
    public boolean hasTileEntity() {
    	// TODO Auto-generated method stub
    	return true;
    }
    
	@Override
	public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_) {
		// TODO Auto-generated method stub
		return new TileEntityCondenser("condenser");
	}
	
	@Override
    public List<Pair<Integer, String>> getVariants()
    {
        List<Pair<Integer, String>> ret = new ArrayList<Pair<Integer, String>>();
            ret.add(new ImmutablePair<Integer, String>(0, "type=normal"));
        return ret;
    }
    
}