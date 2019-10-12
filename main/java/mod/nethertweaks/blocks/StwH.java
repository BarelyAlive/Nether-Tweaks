package mod.nethertweaks.blocks;

import java.util.Random;

import mod.nethertweaks.world.Hellworld;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class StwH extends Block{

	public StwH() {
		super(Material.ROCK);
		setLightLevel(1.0F);
		setHarvestLevel("pickaxe", 3);
		setResistance(75.0F);
		setHardness(16.0F);
		setTickRandomly(true);
	}

	@Override
	public void onBlockPlacedBy(final World world, final BlockPos pos, final IBlockState state, final EntityLivingBase placer,
			final ItemStack stack) {
		EntityLightningBolt entitybolt = new EntityLightningBolt(world, 0D, 0D, 0D, enableStats);
		double x = pos.getX();  	//what ever location you want
		double y = pos.getY();	//what ever location you want
		double z = pos.getZ();  	//what ever location you want
		entitybolt.setLocationAndAngles(x, y, z, 0, 0.0F);
		world.spawnEntity(entitybolt);
		super.onBlockPlacedBy(world, pos, state, placer, stack);
	}

	@Override
	public boolean onBlockActivated(final World world, final BlockPos pos, final IBlockState state, final EntityPlayer playerIn,
			final EnumHand hand, final EnumFacing facing, final float hitX, final float hitY, final float hitZ)
	{
		if(!world.isBlockLoaded(pos)) 	   return false;
		if(world.isRemote) 				   return true;
		if(playerIn.getRidingEntity() != null) return false;

		if (world.provider.getDimension() != 1)
			playerIn.changeDimension(1);
		else if (world.provider.getDimension() == 1)
			if (Hellworld.isHellworld(world))
				playerIn.changeDimension(-1);
			else
				playerIn.changeDimension(0);
		return true;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void randomDisplayTick(final IBlockState state, final World world, final BlockPos pos, final Random rand) {
		if (rand.nextInt(100) == 0)
			world.playSound(pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D, SoundEvents.BLOCK_PORTAL_AMBIENT, SoundCategory.BLOCKS, 0.5F, rand.nextFloat() * 0.4F + 0.8F, false);

		for (int i = 0; i < 4; ++i)
		{
			double d0 = pos.getX() + rand.nextFloat();
			double d1 = pos.getY() + rand.nextFloat();
			double d2 = pos.getZ() + rand.nextFloat();
			double d3 = (rand.nextFloat() - 0.5D) * 0.5D;
			double d4 = (rand.nextFloat() - 0.5D) * 0.5D;
			double d5 = (rand.nextFloat() - 0.5D) * 0.5D;
			int j = rand.nextInt(2) * 2 - 1;

			if (world.getBlockState(pos.west()).getBlock() != this && world.getBlockState(pos.east()).getBlock() != this)
			{
				d0 = pos.getX() + 0.5D + 0.25D * j;
				d3 = rand.nextFloat() * 2.0F * j;
			}
			else
			{
				d2 = pos.getZ() + 0.5D + 0.25D * j;
				d5 = rand.nextFloat() * 2.0F * j;
			}

			world.spawnParticle(EnumParticleTypes.PORTAL, d0, d1, d2, d3, d4, d5);
		}
		super.randomDisplayTick(state, world, pos, rand);
	}

	@Override
	public boolean isOpaqueCube(final IBlockState state) {
		return false;
	}
}
