package mod.nethertweaks.api;

import java.util.List;

public interface IRegistryList<V> extends IRegistry<List<V>> {
    void register(V value);
}
