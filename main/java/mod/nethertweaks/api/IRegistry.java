package mod.nethertweaks.api;

public interface IRegistry<V> {
    void clearRegistry();

    V getRegistry();
}
