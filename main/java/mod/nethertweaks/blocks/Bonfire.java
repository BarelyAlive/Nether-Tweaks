package mod.nethertweaks.blocks;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

import mod.nethertweaks.INames;
import mod.nethertweaks.NetherTweaksMod;
import mod.sfhcore.proxy.IVariantProvider;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;

public class Bonfire extends Block{
	
	public Bonfire(Material bed) {
		super(bed);
		setLightLevel(15);
		setUnlocalizedName(INames.BONFIRE);
		setCreativeTab(NetherTweaksMod.tabNetherTweaksMod);
	}
	
	int l = 0;
	@Override
	public void randomDisplayTick(IBlockState stateIn, World worldIn, BlockPos pos, Random rand) {
		l++;
		switch(l) {
		case 1:
			worldIn.spawnParticle(EnumParticleTypes.FLAME, (double)pos.getX()+0.5D, (double)pos.getY(), (double)pos.getZ()+0.5D, (double)(rand.nextDouble()%0.04D), (double)(rand.nextDouble()%0.08D), (double)(rand.nextDouble()%0.04D));
			worldIn.spawnParticle(EnumParticleTypes.FLAME, (double)pos.getX()+0.5D, (double)pos.getY(), (double)pos.getZ()+0.5D, (double)-(rand.nextDouble()%0.04D), (double)(rand.nextDouble()%0.08D), (double)(rand.nextDouble()%0.04D));
			worldIn.spawnParticle(EnumParticleTypes.FLAME, (double)pos.getX()+0.5D, (double)pos.getY(), (double)pos.getZ()+0.5D, (double)(rand.nextDouble()%0.04D), (double)(rand.nextDouble()%0.08D), (double)(rand.nextDouble()%0.04D));
			worldIn.spawnParticle(EnumParticleTypes.FLAME, (double)pos.getX()+0.5D, (double)pos.getY(), (double)pos.getZ()+0.5D, (double)-(rand.nextDouble()%0.04D), (double)(rand.nextDouble()%0.08D), (double)(rand.nextDouble()%0.04D));
			worldIn.spawnParticle(EnumParticleTypes.FLAME, (double)pos.getX()+0.5D, (double)pos.getY(), (double)pos.getZ()+0.5D, (double)(rand.nextDouble()%0.04D), (double)(rand.nextDouble()%0.08D), (double)(rand.nextDouble()%0.04D));
			worldIn.spawnParticle(EnumParticleTypes.FLAME, (double)pos.getX()+0.5D, (double)pos.getY(), (double)pos.getZ()+0.5D, (double)-(rand.nextDouble()%0.04D), (double)(rand.nextDouble()%0.08D), (double)(rand.nextDouble()%0.04D));
			worldIn.spawnParticle(EnumParticleTypes.FLAME, (double)pos.getX()+0.5D, (double)pos.getY(), (double)pos.getZ()+0.5D, (double)(rand.nextDouble()%0.04D), (double)(rand.nextDouble()%0.08D), (double)(rand.nextDouble()%0.04D));
			worldIn.spawnParticle(EnumParticleTypes.FLAME, (double)pos.getX()+0.5D, (double)pos.getY(), (double)pos.getZ()+0.5D, (double)-(rand.nextDouble()%0.04D), (double)(rand.nextDouble()%0.08D), (double)(rand.nextDouble()%0.04D));
			l = 0;
        	break;
		}
		super.randomDisplayTick(stateIn, worldIn, pos, rand);
	}
	
	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn,
			EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		
		if(playerIn.onGround && !worldIn.isRemote && playerIn.dimension == -1){
		    ITextComponent name = playerIn.getDisplayName();
		    playerIn.setSpawnChunk(pos, true, -1);
		    int x = (int) playerIn.posX;
		    int y = (int) playerIn.posY;
		    int z = (int) playerIn.posZ;
		    playerIn.sendMessage(new TextComponentString(name + " rested at X: " + x + " Y: " + y +" Z: " + z + "!"));
		}
		
		return super.onBlockActivated(worldIn, pos, state, playerIn, hand, facing, hitX, hitY, hitZ);
	}
	
	@Override
	public void onBlockHarvested(World worldIn, BlockPos pos, IBlockState state, EntityPlayer player) {
		player.sendMessage(new TextComponentString("Your point of rest is lost!"));
		
	    player.setSpawnPoint(null, true);
		super.onBlockHarvested(worldIn, pos, state, player);
	}
}
