package mod.nethertweaks.blocks;
 
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.annotation.Nullable;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

import mod.nethertweaks.INames;
import mod.nethertweaks.NetherTweaksMod;
import mod.sfhcore.proxy.IVariantProvider;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLeaves;
import net.minecraft.block.BlockPlanks;
import net.minecraft.block.BlockPlanks.EnumType;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.audio.Sound;
import net.minecraft.client.renderer.EnumFaceDirection;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.EnumTypeAdapterFactory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
 
public class NetherLeaves extends Block implements net.minecraftforge.common.IShearable, IVariantProvider {
     
	 public static final PropertyBool DECAYABLE = PropertyBool.create("decayable");
	    public static final PropertyBool CHECK_DECAY = PropertyBool.create("check_decay");
	    protected boolean leavesFancy;
	    int[] surroundings;

	    public NetherLeaves()
	    {
	        super(Material.LEAVES);
	        this.setTickRandomly(true);
	        setCreativeTab(NetherTweaksMod.tabNetherTweaksMod);
	        this.setHardness(0.2F);
	        this.setLightOpacity(1);
	        this.setSoundType(SoundType.PLANT);
	    }

	    public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand)
	    {
	        if (!worldIn.isRemote)
	        {
	                int i = 4;
	                int j = 5;
	                int k = pos.getX();
	                int l = pos.getY();
	                int i1 = pos.getZ();
	                int j1 = 32;
	                int k1 = 1024;
	                int l1 = 16;

	                if (this.surroundings == null)
	                {
	                    this.surroundings = new int[32768];
	                }

	                if (worldIn.isAreaLoaded(new BlockPos(k - 5, l - 5, i1 - 5), new BlockPos(k + 5, l + 5, i1 + 5)))
	                {
	                    BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();

	                    for (int i2 = -4; i2 <= 4; ++i2)
	                    {
	                        for (int j2 = -4; j2 <= 4; ++j2)
	                        {
	                            for (int k2 = -4; k2 <= 4; ++k2)
	                            {
	                                IBlockState iblockstate = worldIn.getBlockState(blockpos$mutableblockpos.setPos(k + i2, l + j2, i1 + k2));
	                                Block block = iblockstate.getBlock();

	                                if (!block.canSustainLeaves(iblockstate, worldIn, blockpos$mutableblockpos.setPos(k + i2, l + j2, i1 + k2)))
	                                {
	                                    if (block.isLeaves(iblockstate, worldIn, blockpos$mutableblockpos.setPos(k + i2, l + j2, i1 + k2)))
	                                    {
	                                        this.surroundings[(i2 + 16) * 1024 + (j2 + 16) * 32 + k2 + 16] = -2;
	                                    }
	                                    else
	                                    {
	                                        this.surroundings[(i2 + 16) * 1024 + (j2 + 16) * 32 + k2 + 16] = -1;
	                                    }
	                                }
	                                else
	                                {
	                                    this.surroundings[(i2 + 16) * 1024 + (j2 + 16) * 32 + k2 + 16] = 0;
	                                }
	                            }
	                        }
	                    }

	                    for (int i3 = 1; i3 <= 4; ++i3)
	                    {
	                        for (int j3 = -4; j3 <= 4; ++j3)
	                        {
	                            for (int k3 = -4; k3 <= 4; ++k3)
	                            {
	                                for (int l3 = -4; l3 <= 4; ++l3)
	                                {
	                                    if (this.surroundings[(j3 + 16) * 1024 + (k3 + 16) * 32 + l3 + 16] == i3 - 1)
	                                    {
	                                        if (this.surroundings[(j3 + 16 - 1) * 1024 + (k3 + 16) * 32 + l3 + 16] == -2)
	                                        {
	                                            this.surroundings[(j3 + 16 - 1) * 1024 + (k3 + 16) * 32 + l3 + 16] = i3;
	                                        }

	                                        if (this.surroundings[(j3 + 16 + 1) * 1024 + (k3 + 16) * 32 + l3 + 16] == -2)
	                                        {
	                                            this.surroundings[(j3 + 16 + 1) * 1024 + (k3 + 16) * 32 + l3 + 16] = i3;
	                                        }

	                                        if (this.surroundings[(j3 + 16) * 1024 + (k3 + 16 - 1) * 32 + l3 + 16] == -2)
	                                        {
	                                            this.surroundings[(j3 + 16) * 1024 + (k3 + 16 - 1) * 32 + l3 + 16] = i3;
	                                        }

	                                        if (this.surroundings[(j3 + 16) * 1024 + (k3 + 16 + 1) * 32 + l3 + 16] == -2)
	                                        {
	                                            this.surroundings[(j3 + 16) * 1024 + (k3 + 16 + 1) * 32 + l3 + 16] = i3;
	                                        }

	                                        if (this.surroundings[(j3 + 16) * 1024 + (k3 + 16) * 32 + (l3 + 16 - 1)] == -2)
	                                        {
	                                            this.surroundings[(j3 + 16) * 1024 + (k3 + 16) * 32 + (l3 + 16 - 1)] = i3;
	                                        }

	                                        if (this.surroundings[(j3 + 16) * 1024 + (k3 + 16) * 32 + l3 + 16 + 1] == -2)
	                                        {
	                                            this.surroundings[(j3 + 16) * 1024 + (k3 + 16) * 32 + l3 + 16 + 1] = i3;
	                                        }
	                                    }
	                                }
	                            }
	                        }
	                    }
	                }

	                int l2 = this.surroundings[16912];

	        }
	    }

	    private void destroy(World worldIn, BlockPos pos)
	    {
	        this.dropBlockAsItem(worldIn, pos, worldIn.getBlockState(pos), 0);
	        worldIn.setBlockToAir(pos);
	    }

	    @SideOnly(Side.CLIENT)
	    public void randomDisplayTick(IBlockState stateIn, World worldIn, BlockPos pos, Random rand)
	    {
	        if (worldIn.isRainingAt(pos.up()) && !worldIn.getBlockState(pos.down()).isFullyOpaque() && rand.nextInt(15) == 1)
	        {
	            double d0 = (double)((float)pos.getX() + rand.nextFloat());
	            double d1 = (double)pos.getY() - 0.05D;
	            double d2 = (double)((float)pos.getZ() + rand.nextFloat());
	            worldIn.spawnParticle(EnumParticleTypes.DRIP_WATER, d0, d1, d2, 0.0D, 0.0D, 0.0D, new int[0]);
	        }
	    }

	    /**
	     * Returns the quantity of items to drop on block destruction.
	     */
	    public int quantityDropped(Random random)
	    {
	        return random.nextInt(20) == 0 ? 1 : 0;
	    }

	    /**
	     * Get the Item that this Block should drop when harvested.
	     */
	    @Nullable
	    public Item getItemDropped(IBlockState state, Random rand, int fortune)
	    {
	        return Item.getItemFromBlock(NTMBlocks.blockNetherSapling);
	    }

	    /**
	     * Spawns this Block's drops into the World as EntityItems.
	     */
	    public void dropBlockAsItemWithChance(World worldIn, BlockPos pos, IBlockState state, float chance, int fortune)
	    {
	        super.dropBlockAsItemWithChance(worldIn, pos, state, chance, fortune);
	    }

	    protected void dropApple(World worldIn, BlockPos pos, IBlockState state, int chance)
	    {
	    }

	    protected int getSaplingDropChance(IBlockState state)
	    {
	        return 20;
	    }

	    /**
	     * Used to determine ambient occlusion and culling when rebuilding chunks for render
	     */
	    public boolean isOpaqueCube(IBlockState state)
	    {
	        return !this.leavesFancy;
	    }

	    /**
	     * Pass true to draw this block using fancy graphics, or false for fast graphics.
	     */
	    @SideOnly(Side.CLIENT)
	    public void setGraphicsLevel(boolean fancy)
	    {
	        this.leavesFancy = fancy;
	    }

	    @SideOnly(Side.CLIENT)
	    public BlockRenderLayer getBlockLayer()
	    {
	        return this.leavesFancy ? BlockRenderLayer.CUTOUT_MIPPED : BlockRenderLayer.SOLID;
	    }

	    public boolean isVisuallyOpaque()
	    {
	        return false;
	    }

	    public BlockPlanks.EnumType getWoodType(int meta) {
			return null;
		}

	    @Override public boolean isShearable(ItemStack item, IBlockAccess world, BlockPos pos){ return true; }
	    @Override public boolean isLeaves(IBlockState state, IBlockAccess world, BlockPos pos){ return true; }

	    @Override
	    public void beginLeavesDecay(IBlockState state, World world, BlockPos pos)
	    {
	        if (!(Boolean)state.getValue(CHECK_DECAY))
	        {
	            world.setBlockState(pos, state.withProperty(CHECK_DECAY, true), 4);
	        }
	    }

	    @Override
	    public java.util.List<ItemStack> getDrops(IBlockAccess world, BlockPos pos, IBlockState state, int fortune)
	    {
	        java.util.List<ItemStack> ret = new java.util.ArrayList<ItemStack>();
	        Random rand = world instanceof World ? ((World)world).rand : new Random();
	        int chance = this.getSaplingDropChance(state);

	        if (fortune > 0)
	        {
	            chance -= 2 << fortune;
	            if (chance < 10) chance = 10;
	        }

	        if (rand.nextInt(chance) == 0)
	            ret.add(new ItemStack(getItemDropped(state, rand, fortune), 1, damageDropped(state)));

	        chance = 200;
	        if (fortune > 0)
	        {
	            chance -= 10 << fortune;
	            if (chance < 40) chance = 40;
	        }

	        this.captureDrops(true);
	        if (world instanceof World)
	            this.dropApple((World)world, pos, state, chance); // Dammet mojang
	        ret.addAll(this.captureDrops(false));
	        return ret;
	    }


	    @SideOnly(Side.CLIENT)
	    public boolean shouldSideBeRendered(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side)
	    {
	        return !this.leavesFancy && blockAccess.getBlockState(pos.offset(side)).getBlock() == this ? false : super.shouldSideBeRendered(blockState, blockAccess, pos, side);
	    }

		@Override
		public List<ItemStack> onSheared(ItemStack item, IBlockAccess world, BlockPos pos, int fortune) {
			// TODO Auto-generated method stub
			return null;
		}
		
		public List<Pair<Integer, String>> getVariants()
	    {
	        List<Pair<Integer, String>> ret = new ArrayList<Pair<Integer, String>>();
	        ret.add(new ImmutablePair<Integer, String>(0, "normal"));
	        return ret;
	    }
}