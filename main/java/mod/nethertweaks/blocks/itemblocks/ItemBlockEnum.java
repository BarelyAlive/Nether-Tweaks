package mod.nethertweaks.blocks.itemblocks;

import mod.nethertweaks.blocks.base.BlockEnum;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.math.MathHelper;

public class ItemBlockEnum<E extends Enum<E> & IStringSerializable> extends ItemBlock
{

    public ItemBlockEnum(BlockEnum<E> block)
    {
        super(block);

            setHasSubtypes(true);
    }

    @SuppressWarnings("unchecked")
    @Override
    public BlockEnum<E> getBlock()
    {
        return (BlockEnum<E>) super.getBlock();
    }

    @Override
    public String getUnlocalizedName(ItemStack stack)
    {
        return getBlock().getUnlocalizedName() + getBlock().getTypes()[MathHelper.clamp_int(stack.getItemDamage(), 0, 15)].getName();
    }

    @Override
    public int getMetadata(int damage)
    {
        return damage;
    }
}