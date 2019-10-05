package mod.nethertweaks.api;

public interface IBarrelLiquidBlacklistRegistry extends IRegistryMappedList<Integer, String>
{
	boolean isBlacklisted(int level, String fluid);
	void register(int level, String fluid);
}