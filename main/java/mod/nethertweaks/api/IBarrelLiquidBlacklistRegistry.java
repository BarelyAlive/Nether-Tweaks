package mod.nethertweaks.api;

public interface IBarrelLiquidBlacklistRegistry extends IRegistryMappedList<Integer, String>
{
    public boolean isBlacklisted(int level, String fluid);
    public void register(int level, String fluid);
}