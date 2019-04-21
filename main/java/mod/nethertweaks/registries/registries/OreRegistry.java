package mod.nethertweaks.registries.registries;

import com.google.common.collect.Lists;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import mod.nethertweaks.Config;
import mod.nethertweaks.NetherTweaksMod;
import mod.nethertweaks.api.IOreRegistry;
import mod.nethertweaks.items.ItemOre;
import mod.nethertweaks.json.CustomBlockInfoJson;
import mod.nethertweaks.json.CustomColorJson;
import mod.nethertweaks.json.CustomItemInfoJson;
import mod.nethertweaks.registries.manager.NTMRegistryManager;
import mod.nethertweaks.registries.registries.base.BaseRegistryList;
import mod.nethertweaks.registry.types.Ore;
import mod.sfhcore.texturing.Color;
import mod.sfhcore.util.BlockInfo;
import mod.sfhcore.util.ItemInfo;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemMeshDefinition;
import net.minecraft.client.renderer.block.model.ModelBakery;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.registries.IForgeRegistry;
import org.apache.commons.lang3.StringUtils;

import java.io.FileReader;
import java.util.*;

import javax.annotation.Nonnull;

public class OreRegistry extends BaseRegistryList<Ore> implements IOreRegistry {

    private final List<ItemOre> itemOreRegistry = new ArrayList<>();
    private final Set<ItemOre> sieveBlackList = new HashSet<>(); // A black list of ores to not register

    public OreRegistry() {
        super(
                new GsonBuilder()
                        .setPrettyPrinting()
                        .registerTypeAdapter(ItemInfo.class,  CustomItemInfoJson.INSTANCE)
                        .registerTypeAdapter(BlockInfo.class,  CustomBlockInfoJson.INSTANCE)
                        .registerTypeAdapter(Ore.class,  CustomOreJson.INSTANCE)
                        .registerTypeAdapter(Color.class,  CustomColorJson.INSTANCE)
                        .create(),
                NTMRegistryManager.ORE_DEFAULT_REGISTRY_PROVIDERS
        );
    }

    /**
     * Registers a new custom piece, hunk, dust and potentially ingot to be
     * generated by NTM.
     *
     * @param name  Unique name for ore
     * @param color Color for the pieces
     * @param info  Final result for the process. If null, an ingot is generated.
     *              Otherwise, the hunk will be smelted into this.
     * @return Ore, containing the base Ore object.
     */
    public Ore register(String name, Color color, ItemInfo info) {
        return register(name, color, info, null, null);
    }

    /**
     * Registers a new custom piece, hunk, dust and potentially ingot to be
     * generated by NTM.
     *
     * @param name  Unique name for ore
     * @param color Color for the pieces
     * @param ingot  Final result for the process. If null, an ingot is generated.
     *              Otherwise, the hunk will be smelted into this.
     * @param dust  If null, a dust is generated.
     * @return Ore, containing the base Ore object.
     */
    public Ore register(String name, Color color, ItemInfo ingot, ItemInfo dust) {
        return register(name, color, ingot, dust, null, null);
    }

    /**
     * Registers a new custom piece, hunk, dust and potentially ingot to be
     * generated by NTM.
     *
     * @param name  Unique name for ore
     * @param color Color for the pieces
     * @param info  Final result for the process. If null, an ingot is generated.
     *              Otherwise, the hunk will be smelted into this.
     * @return Ore, containing the base Ore object.
     */
    @Nonnull
    public Ore register(@Nonnull String name, @Nonnull Color color, ItemInfo info, Map<String, String> translations, String oredictName) {
        Ore ore = new Ore(name, color, info, null, translations, oredictName);
        register(ore);

        return ore;
    }

    /**
     * Registers a new custom piece, hunk, dust and potentially ingot to be
     * generated by NTM.
     *
     * @param name  Unique name for ore
     * @param color Color for the pieces
     * @param ingot  Final result for the process. If null, an ingot is generated.
     *              Otherwise, the hunk will be smelted into this.
     * @param dust  If null, a dust is generated.
     * @return Ore, containing the base Ore object.
     */
    public Ore register(String name, Color color, ItemInfo ingot, ItemInfo dust, Map<String, String> translations, String oredictName) {
        Ore ore = new Ore(name, color, ingot, dust, translations, oredictName);
        register(ore);

        return ore;
    }

    @Override
    public void register(Ore value) {
        registry.add(value);
        itemOreRegistry.add(new ItemOre(value));
    }

    @Override
    protected void registerEntriesFromJSON(FileReader fr) {
        List<Ore> gsonInput = gson.fromJson(fr, new TypeToken<List<Ore>>() {
        }.getType());
        for (Ore ore : gsonInput) {
            register(ore);
        }
    }

    public void doRecipes() {
        for (ItemOre ore : itemOreRegistry) {
            ResourceLocation group = new ResourceLocation(NetherTweaksMod.MODID, "exores");
            ResourceLocation baseName = new ResourceLocation(NetherTweaksMod.MODID, "ore_compression_");
            ResourceLocation recipeLocation = new ResourceLocation(baseName.getResourceDomain(), baseName.getResourcePath() + ore.getOre().getName());

            GameRegistry.addShapedRecipe(recipeLocation, group,
                    new ItemStack(ore, 1, EnumOreSubtype.CHUNK.getMeta()),
                    "xx", "xx", 'x', new ItemStack(ore, 1, EnumOreSubtype.PIECE.getMeta()));

            if (Config.preventUnidict && Loader.isModLoaded("unidict")) {
                UniDict.getConfig().recipesToIgnore.add(recipeLocation);
            }

            ItemStack smeltingResult;

            if (ore.isRegisterIngot()) {
                smeltingResult = new ItemStack(ore, 1, 3);
                OreDictionary.registerOre("ingot" + StringUtils.capitalize(ore.getOre().getName()), smeltingResult);
                if (ore.getOre().getName().contains("aluminium"))
                    OreDictionary.registerOre("ingotAluminum", smeltingResult);
            } else {
                smeltingResult = ore.getOre().getResult().getItemStack();
            }

            FurnaceRecipes.instance().addSmeltingRecipe(new ItemStack(ore, 1, EnumOreSubtype.CHUNK.getMeta()), smeltingResult, 0.7f);
            FurnaceRecipes.instance().addSmeltingRecipe(new ItemStack(ore, 1, EnumOreSubtype.DUST.getMeta()), smeltingResult, 0.7f);
        }
    }

    /**
     * Must be called in init or later,
     * in the model event there is no getRenderItem() yet
     */
    @SideOnly(Side.CLIENT)
    public void initModels() {
        final ItemMeshDefinition ORES = stack -> {
            switch (stack.getItemDamage()) {
                case 0:
                    return new ModelResourceLocation("nethertweaksmod:item_ore", "type=piece");
                case 1:
                    return new ModelResourceLocation("nethertweaksmod:item_ore", "type=chunk");
                case 2:
                    return new ModelResourceLocation("nethertweaksmod:item_ore", "type=dust");
                case 3:
                    return new ModelResourceLocation("nethertweaksmod:item_ore", "type=ingot");
                default:
                    return new ModelResourceLocation(stack.getItem().getRegistryName() == null ? new ResourceLocation("nethertweaksmod:item_ore") : stack.getItem().getRegistryName(), "inventory");
            }
        };
        for (ItemOre ore : itemOreRegistry) {
            ModelLoader.setCustomMeshDefinition(ore, ORES);
            ModelBakery.registerItemVariants(ore,
                    new ModelResourceLocation("nethertweaksmod:item_ore", "type=piece"),
                    new ModelResourceLocation("nethertweaksmod:item_ore", "type=chunk"),
                    new ModelResourceLocation("nethertweaksmod:item_ore", "type=dust"),
                    new ModelResourceLocation("nethertweaksmod:item_ore", "type=ingot"));
            Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(ore, ORES);
        }
    }

    public void registerToGameRegistry(IForgeRegistry<Item> itemRegistry) {

        String bChunk = Config.chunkBaseOreDictName, bDust = Config.dustBaseOreDictName, bPiece = Config.pieceBaseOreDictName, bIngot = Config.ingotBaseOreDictName;

        for (ItemOre itemOre : itemOreRegistry) {
            itemRegistry.register(itemOre);

            String oreName = itemOre.getOre().getName() != null ? itemOre.getOre().getName() : StringUtils.capitalize(itemOre.getOre().getName());

            OreDictionary.registerOre(bChunk + oreName, new ItemStack(itemOre, 1, EnumOreSubtype.CHUNK.getMeta()));
            OreDictionary.registerOre(bPiece + oreName, new ItemStack(itemOre, 1, EnumOreSubtype.PIECE.getMeta()));

            if (itemOre.isRegisterDust())
                OreDictionary.registerOre(bDust + oreName, new ItemStack(itemOre, 1, EnumOreSubtype.DUST.getMeta()));
            if (itemOre.isRegisterIngot())
                OreDictionary.registerOre(bIngot + oreName, new ItemStack(itemOre, 1, EnumOreSubtype.INGOT.getMeta()));
        }
    }

    public ItemOre getOreItem(@Nonnull String name) {
        for (ItemOre itemOre : itemOreRegistry) {
            if (itemOre.getOre().getName().equals(name)) {
                return itemOre;
            }
        }

        return null;
    }

    public boolean isRegistered(@Nonnull String name) {
        for (Ore ore : registry) {
            if (ore.getName().equals(name)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public List<ItemOre> getRecipeList() {
        return this.itemOreRegistry;
    }

    @Nonnull
    @Override
    public List<ItemOre> getItemOreRegistry() {
        return itemOreRegistry;
    }

    @Nonnull
    @Override
    public Set<ItemOre> getSieveBlackList() {
        return sieveBlackList;
    }
}
