package mod.nethertweaks.block.tile;

import java.util.Objects;

import mod.nethertweaks.capabilities.CapabilityHeatManager;
import mod.nethertweaks.registry.manager.NTMRegistryManager;
import mod.sfhcore.util.BlockInfo;
import mod.sfhcore.util.ItemInfo;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;

public class TileCrucibleStone extends TileCrucibleBase {

	public TileCrucibleStone() {
		super(NTMRegistryManager.CRUCIBLE_STONE_REGISTRY);
	}

	@Override
	public void update() {
		if (getWorld().isRemote)
			return;

		ticksSinceLast++;

		if (ticksSinceLast >= 10) {
			ticksSinceLast = 0;

			int heatRate = getHeatRate();

			if (heatRate <= 0)
				return;

			if (solidAmount <= 0)
				if (!itemHandler.getStackInSlot(0).isEmpty()) {
					setCurrentItem(new ItemInfo(itemHandler.getStackInSlot(0)));
					itemHandler.getStackInSlot(0).shrink(1);

					if (itemHandler.getStackInSlot(0).isEmpty())
						itemHandler.setStackInSlot(0, ItemStack.EMPTY);

					solidAmount = crucibleRegistry.getMeltable(getCurrentItem()).getAmount();
				} else
					//                  onBlockActivated in TileCrucibleBase already updates the client item/fluid is removed
					return;

			if (!itemHandler.getStackInSlot(0).isEmpty() && itemHandler.getStackInSlot(0).isItemEqual(getCurrentItem().getItemStack()))
				// For meltables with a really small "amount"
				while (heatRate > solidAmount && !itemHandler.getStackInSlot(0).isEmpty()) {
					solidAmount += crucibleRegistry.getMeltable(getCurrentItem()).getAmount();
					itemHandler.getStackInSlot(0).shrink(1);

					if (itemHandler.getStackInSlot(0).isEmpty())
						itemHandler.setStackInSlot(0, ItemStack.EMPTY);
				}

			// Never take more than we have left
			if (heatRate > solidAmount)
				heatRate = solidAmount;

			if (heatRate > 0 && getCurrentItem().isValid() && crucibleRegistry.canBeMelted(getCurrentItem())) {
				final FluidStack toFill = new FluidStack(FluidRegistry.getFluid(crucibleRegistry.getMeltable(getCurrentItem()).getFluid()), heatRate);
				final int filled = tank.fillInternal(toFill, true);
				solidAmount -= filled;

				// already done two lines above in fillinternal
			}
		}
	}

	@Override
	public int getHeatRate() {
		final BlockPos posBelow = pos.add(0, -1, 0);
		final IBlockState stateBelow = getWorld().getBlockState(posBelow);

		if (stateBelow == Blocks.AIR.getDefaultState())
			return 0;

		// Try to match metadata
		int heat = NTMRegistryManager.HEAT_REGISTRY.getHeatAmount(new BlockInfo(stateBelow));

		// Try to match without metadata
		if (heat == 0 && !Item.getItemFromBlock(stateBelow.getBlock()).getHasSubtypes())
			heat = NTMRegistryManager.HEAT_REGISTRY.getHeatAmount(new BlockInfo(stateBelow.getBlock()));

		if (heat != 0)
			return heat;

		final TileEntity tile = getWorld().getTileEntity(posBelow);

		if (tile != null && tile.hasCapability(CapabilityHeatManager.HEAT_CAPABILITY, EnumFacing.UP))
			return Objects.requireNonNull(tile.getCapability(CapabilityHeatManager.HEAT_CAPABILITY, EnumFacing.UP)).getHeatRate();

		return 0;
	}
}
