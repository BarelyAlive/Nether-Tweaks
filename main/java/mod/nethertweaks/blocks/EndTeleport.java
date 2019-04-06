package mod.nethertweaks.blocks;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

import mod.nethertweaks.Config;
import mod.nethertweaks.INames;
import mod.nethertweaks.NetherTweaksMod;
import mod.nethertweaks.handler.RecipeHandler;
import mod.sfhcore.Constants;
import mod.sfhcore.blocks.Cube;
import mod.sfhcore.proxy.IVariantProvider;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemMonsterPlacer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class EndTeleport extends Cube{
	
	int dimension;
	
	public EndTeleport() {
		super(Material.ROCK, 75.0F, 16.0F, 1, NetherTweaksMod.tabNTM, new ResourceLocation("nethertweaksmod", INames.ENDTELEPORT));
		setCreativeTab(NetherTweaksMod.tabNTM);
		setLightLevel(1.0F);
		setHarvestLevel("pickaxe", 3);
		setUnlocalizedName(INames.ENDTELEPORT);
		this.setTickRandomly(true);
	}
	
	@Override
	public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer,
			ItemStack stack) {
		EntityLightningBolt entitybolt = new EntityLightningBolt(worldIn, 0D, 0D, 0D, enableStats);
		double x = pos.getX();  	//what ever location you want
		double y = pos.getY();	//what ever location you want
		double z = pos.getZ();  	//what ever location you want
		entitybolt.setLocationAndAngles(x, y, z, 0, 0.0F);	
		worldIn.spawnEntity(entitybolt);
		super.onBlockPlacedBy(worldIn, pos, state, placer, stack);
	}
	
	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn,
			EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
	{
		if (worldIn.isBlockLoaded(pos)) {
			if (playerIn.getRidingEntity() == null && !worldIn.isRemote && worldIn.provider.getDimension() != 1) {
				playerIn.changeDimension(1);
			} else {
				if (playerIn.getRidingEntity() == null && !worldIn.isRemote && worldIn.provider.getDimension() == 1) {
					playerIn.changeDimension(Config.StwtHDimension);
				}
			} 
		}
		return false;
	}

	@Override
	public void randomDisplayTick(IBlockState stateIn, World worldIn, BlockPos pos, Random rand) {
		if (rand.nextInt(100) == 0)
        {
            worldIn.playSound((double)pos.getX() + 0.5D, (double)pos.getY() + 0.5D, (double)pos.getZ() + 0.5D, SoundEvents.BLOCK_PORTAL_AMBIENT, SoundCategory.BLOCKS, 0.5F, rand.nextFloat() * 0.4F + 0.8F, false);
        }

        for (int i = 0; i < 4; ++i)
        {
            double d0 = (double)((float)pos.getX() + rand.nextFloat());
            double d1 = (double)((float)pos.getY() + rand.nextFloat());
            double d2 = (double)((float)pos.getZ() + rand.nextFloat());
            double d3 = ((double)rand.nextFloat() - 0.5D) * 0.5D;
            double d4 = ((double)rand.nextFloat() - 0.5D) * 0.5D;
            double d5 = ((double)rand.nextFloat() - 0.5D) * 0.5D;
            int j = rand.nextInt(2) * 2 - 1;

            if (worldIn.getBlockState(pos.west()).getBlock() != this && worldIn.getBlockState(pos.east()).getBlock() != this)
            {
                d0 = (double)pos.getX() + 0.5D + 0.25D * (double)j;
                d3 = (double)(rand.nextFloat() * 2.0F * (float)j);
            }
            else
            {
                d2 = (double)pos.getZ() + 0.5D + 0.25D * (double)j;
                d5 = (double)(rand.nextFloat() * 2.0F * (float)j);
            }

            worldIn.spawnParticle(EnumParticleTypes.PORTAL, d0, d1, d2, d3, d4, d5, new int[0]);
        }
		super.randomDisplayTick(stateIn, worldIn, pos, rand);
	}
}
