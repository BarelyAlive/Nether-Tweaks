package mod.nethertweaks.blocks;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nonnull;

import mod.nethertweaks.INames;
import mod.nethertweaks.NetherTweaksMod;
import mod.nethertweaks.blocks.tile.TileSieve;
import mod.nethertweaks.config.Config;
import mod.nethertweaks.items.ItemMesh;
import mod.sfhcore.util.ItemStackItemHandler;
import mod.sfhcore.util.Util;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import p455w0rd.danknull.util.DankNullUtils;

public class Sieve extends BlockContainer {

    public static final PropertyEnum<MeshType> MESH = PropertyEnum.create("mesh", MeshType.class);

    public Sieve(String name, Material material) {
        super(material);
        if (material == Material.ROCK) {
			this.setResistance(30.0F);
			this.setHardness(2.0f);
		}
        else
        {
			this.setHardness(2.0f);
        }
		this.setRegistryName(NetherTweaksMod.MODID, name);
        this.setCreativeTab(NetherTweaksMod.TABNTM);
        this.setDefaultState(this.blockState.getBaseState().withProperty(MESH, MeshType.NONE));
    }

    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ)
    {
        // I think this should work. Let's just go with it.
        if(player instanceof FakePlayer && !Config.fakePlayersCanSieve) return false;
        if(!world.isBlockLoaded(pos)) return false;
    	if(world.isRemote) return true;
    	if(player.isSneaking()) return false;
    	  	
    	TileSieve te = (TileSieve) world.getTileEntity(pos);
		if(te == null) return true;
		
        ItemStack heldItem = player.getHeldItem(hand);

        // Removing a mesh
        if (heldItem.isEmpty() && !te.getMeshStack().isEmpty() && player.isSneaking() && te.setMesh(ItemStack.EMPTY, true)) {
            //Removing a mesh.
            Util.dropItemInWorld(te, player, te.getMeshStack(), 0.02f);
            te.setMesh(ItemStack.EMPTY, false);
            return true;
        }

        // Inserting blocks
        IItemHandler cap = null;
        if(Config.generalItemHandlerCompat)
            cap = heldItem.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);
        if(cap == null)
            cap = new ItemStackItemHandler(heldItem);

        int slot = 0;
        int maxSlot = cap.getSlots();
        if(Loader.isModLoaded("danknull") &&
                Config.dankNullIntegration &&
                DankNullUtils.isDankNull(heldItem)){
            slot = DankNullUtils.getSelectedStackIndex(DankNullUtils.getInventoryFromHeld(player));
            maxSlot = slot + 1;
        }

        for(;slot < maxSlot; slot++){
            ItemStack stack = cap.getStackInSlot(slot);
            if(!stack.isEmpty() && stack.getItem() instanceof ItemMesh){
                // Adding a mesh
                boolean added = te.setMesh(cap.extractItem(slot, 1, true));
                if(added){
                    cap.extractItem(slot, 1, player.isCreative());
                    return true;
                }
            }
            if(te.addBlock(cap.extractItem(slot,1,true))){
                // Adding a block
                cap.extractItem(slot, 1, player.isCreative());
                for(int dx = -Config.sieveSimilarRadius; dx <= Config.sieveSimilarRadius; dx++){
                    for(int dz = -Config.sieveSimilarRadius; dz <= Config.sieveSimilarRadius; dz++){
                        if(cap.getStackInSlot(slot).isEmpty())
                            continue; // No more items
                        TileEntity otherTE = world.getTileEntity(pos.add(dx, 0, dz));
                        if(!(otherTE instanceof TileSieve))
                            continue; // Not a sieve
                        TileSieve sieve = (TileSieve) otherTE;
                        if(!te.isSieveSimilarToInput(sieve))
                            continue; // Not a similar sieve
                        if(sieve.addBlock(cap.extractItem(slot,1,true))){
                            cap.extractItem(slot, 1, player.isCreative());
                        }
                    }
                }
                return true;
            }
        }

        List<BlockPos> toSift = new ArrayList<>();
        for (int xOffset = -1 * Config.sieveSimilarRadius; xOffset <= Config.sieveSimilarRadius; xOffset++) {
            for (int zOffset = -1 * Config.sieveSimilarRadius; zOffset <= Config.sieveSimilarRadius; zOffset++) {
                TileEntity entity = world.getTileEntity(pos.add(xOffset, 0, zOffset));
                if (entity instanceof TileSieve) {
                    TileSieve sieve = (TileSieve) entity;

                    if (te.isSieveSimilar(sieve))
                        toSift.add(pos.add(xOffset, 0, zOffset));
                }
            }
        }
        for (BlockPos posIter : toSift) {
            if (posIter != null) {
                TileSieve sieve = (TileSieve) world.getTileEntity(posIter);
                if (sieve != null)
                    sieve.doSieving(player, false);
            }
        }
        return true;
    }

    @Override
    @Nonnull
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, MESH);
    }

    @Override
    @Nonnull
    @Deprecated
    public IBlockState getStateFromMeta(int meta) {
        return getDefaultState();
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return 0;
    }

    @Override
    public void breakBlock(@Nonnull World world, @Nonnull BlockPos pos, @Nonnull IBlockState state) {
        TileEntity te = world.getTileEntity(pos);
        if (te != null) {
            TileSieve sieve = (TileSieve) te;
            if (!sieve.getMeshStack().isEmpty())
                Util.dropItemInWorld(sieve, null, sieve.getMeshStack(), 0.02f);
        }

        super.breakBlock(world, pos, state);
    }
    
    //region >>>> RENDERING OPTIONS
    @Override
    @Deprecated
    public boolean isFullBlock(IBlockState state) {
        return false;
    }

    @Override
    @Deprecated
    public boolean isOpaqueCube(IBlockState state) {
        return false;
    }

    @Override
    @Deprecated
    public boolean isFullCube(IBlockState state) {
        return false;
    }

    @Override
    public boolean shouldSideBeRendered(IBlockState blockState, @Nonnull IBlockAccess blockAccess, @Nonnull BlockPos pos, EnumFacing side) {
        return false;
    }
    //endregion

    public enum MeshType implements IStringSerializable {
        NONE(0, "none"), STRING(1, "string"), FLINT(2, "flint"), IRON(3, "iron"), DIAMOND(4, "diamond"), NO_RENDER(5, "no_render");

        private final int id;
        private final String name;

        MeshType(int id, String name) {
            this.id = id;
            this.name = name;
        }

        public boolean isValid(){
            return id > 0 && id < 5;
        }

        public static MeshType getMeshTypeByID(String type) {
            switch (type) {
                case INames.MESH_STRING:
                    return STRING;
                case INames.MESH_FLINT:
                    return FLINT;
                case INames.MESH_IRON:
                    return IRON;
                case INames.MESH_DIAMOND:
                    return DIAMOND;
                case "idk":
                    return NO_RENDER;
            }

            return NONE;
        }

        @Override
        @Nonnull
        public String getName() {
            return name;
        }

        public int getID() {
            return id;
        }

        @Override
        public String toString() {
            return getName();
        }
    }
    
	@Override
	public TileSieve createNewTileEntity(World worldIn, int meta) {
    	return new TileSieve();
	}
}