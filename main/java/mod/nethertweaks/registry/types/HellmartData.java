package mod.nethertweaks.registry.types;

import net.minecraft.item.ItemStack;

public class HellmartData {
    private final ItemStack item;
    private final ItemStack currency;
    private final int price;

    public HellmartData(ItemStack item, ItemStack currency, int price) {
        this.item = item;
        this.currency = currency;
        this.price = price;
    }

    public ItemStack getItem() {
        return item;
    }

    public ItemStack getCurrency() {
        return currency;
    }

    public int getPrice() {
        return price;
    }
}