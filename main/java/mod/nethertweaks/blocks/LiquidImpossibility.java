package mod.nethertweaks.blocks;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

import mod.nethertweaks.Config;
import mod.nethertweaks.NetherTweaksMod;
import mod.nethertweaks.handler.BlockHandler;
import mod.nethertweaks.handler.BucketNFluidHandler;
import mod.nethertweaks.interfaces.INames;
import mod.sfhcore.proxy.IVariantProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EntityLiving.SpawnPlacementType;
import net.minecraft.entity.monster.EntityMagmaCube;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.monster.EntityPigZombie;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.entity.monster.EntityWitherSkeleton;
import net.minecraft.entity.passive.EntityPig;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fluids.BlockFluidClassic;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;


public class LiquidImpossibility extends BlockFluidClassic{

    public LiquidImpossibility()
    {
        super(BucketNFluidHandler.FLUIDLIQUIDIMPOSSIBILITY, Material.WATER);
        setResistance(3000.0F);
        setHardness(300.0F);
        setRegistryName(NetherTweaksMod.MODID, INames.BLOCKLIQUIDIMPOSSIBILITY);
        setLightLevel(15);
        setTemperature(0);
    }
    
    @Override
    public boolean canSpawnInBlock() {
    	return true;
    }
    
    @Override
    public boolean canCreatureSpawn(IBlockState state, IBlockAccess world, BlockPos pos, SpawnPlacementType type) {
    	if(type.equals(SpawnPlacementType.IN_WATER) && state.equals(this.getDefaultState()))
    	{
    		return true;
    	}
		return false;
    }
    
    @Override
	public void onEntityCollidedWithBlock(World world, BlockPos pos, IBlockState state, Entity entity) {
		
		if (world.isRemote)
			return;
		
		if (entity.isDead)
			return;
		
		if (Config.spawnPig) {
			if (entity instanceof EntityPigZombie && !((EntityPigZombie) entity).isAngry()) {

				EntityPig entitypig = new EntityPig(world);
				entitypig.setLocationAndAngles(pos.getX(), pos.getY(), pos.getZ(), entity.rotationYaw, entity.rotationPitch);
				entitypig.setNoAI(entitypig.isAIDisabled());

				if (entitypig.hasCustomName()) {
					entitypig.setCustomNameTag(entitypig.getCustomNameTag());
					entitypig.setAlwaysRenderNameTag(entitypig.getAlwaysRenderNameTag());
				}
				entitypig.setHealth(entitypig.getMaxHealth());

				world.spawnEntity(entitypig);
				entity.setDead();
			} 
		}
		
		if (Config.spawnSkeleton) {
			if (entity instanceof EntityWitherSkeleton && ((EntityWitherSkeleton) entity).getAttackTarget() == null) {

				EntitySkeleton skeleton = new EntitySkeleton(world);
				skeleton.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, new ItemStack(Items.BOW));
				skeleton.setLocationAndAngles(pos.getX(), pos.getY(), pos.getZ(), entity.rotationYaw, entity.rotationPitch);
				skeleton.setNoAI(skeleton.isAIDisabled());

				if (skeleton.hasCustomName()) {
					skeleton.setCustomNameTag(skeleton.getCustomNameTag());
					skeleton.setAlwaysRenderNameTag(skeleton.getAlwaysRenderNameTag());
				}
				skeleton.setHealth(skeleton.getMaxHealth());

				world.spawnEntity(skeleton);
				entity.setDead();
			}
		}
			
		if (Config.spawnSlime) {
			if (entity instanceof EntityMagmaCube && ((EntityMagmaCube) entity).getAttackTarget() == null) {

				EntitySlime slime = new EntitySlime(world);
				slime.setLocationAndAngles(pos.getX(), pos.getY(), pos.getZ(), entity.rotationYaw, entity.rotationPitch);
				slime.setNoAI(slime.isAIDisabled());

				if (slime.hasCustomName()) {
					slime.setCustomNameTag(slime.getCustomNameTag());
					slime.setAlwaysRenderNameTag(slime.getAlwaysRenderNameTag());
				}
				slime.setHealth(slime.getMaxHealth());

				world.spawnEntity(slime);
				entity.setDead();
			} 
		}
    }
    
    @Override
    public IBlockState getStateFromMeta(int meta)
    {
        return getBlockState().getBaseState().withProperty(LEVEL, meta);
    }
    
    @Override
    public Fluid getFluid() {
    return BucketNFluidHandler.FLUIDLIQUIDIMPOSSIBILITY;
    }
}
