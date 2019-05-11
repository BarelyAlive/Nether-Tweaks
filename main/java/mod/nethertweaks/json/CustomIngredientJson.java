package mod.nethertweaks.json;

import com.google.gson.*;

import mod.nethertweaks.registries.ingredient.IngredientUtil;
import mod.nethertweaks.registries.ingredient.OreIngredientStoring;
import mod.sfhcore.util.LogUtil;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import java.lang.reflect.Type;

public class CustomIngredientJson implements JsonDeserializer<Ingredient>, JsonSerializer<Ingredient> {

    public static final CustomIngredientJson INSTANCE = new CustomIngredientJson();

	@Override
    public Ingredient deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException
    {
        if (json.isJsonPrimitive() && ((JsonPrimitive)json).isString()) {
            String s = json.getAsString();
            return IngredientUtil.parseFromString(s);
        } else {
            LogUtil.error("Error parsing JSON: No Primitive String: $json");
        }

        return Ingredient.EMPTY;
    }

    @Override
    public JsonElement serialize(Ingredient src, Type typeOfSrc, JsonSerializationContext context)
    {
        if (src instanceof OreIngredientStoring) {
            return new JsonPrimitive("ore:" + ((OreIngredientStoring)src).getOreName());
        } else {
            ItemStack[] stacks = src.getMatchingStacks();
            if (stacks.length != 0)
                return new JsonPrimitive(stacks[0].getItem().getRegistryName().toString() + ":" + stacks[0].getItemDamage());
        }

        return new JsonPrimitive("minecraft:air:0");
    }
}
