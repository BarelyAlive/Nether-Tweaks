package mod.nethertweaks.json;

import java.lang.reflect.Type;
import java.util.Objects;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import mod.nethertweaks.registries.ingredient.IngredientUtil;
import mod.nethertweaks.registries.ingredient.OreIngredientStoring;
import mod.sfhcore.util.LogUtil;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;

public class CustomIngredientJson implements JsonDeserializer<Ingredient>, JsonSerializer<Ingredient> {

	public static final CustomIngredientJson INSTANCE = new CustomIngredientJson();

	@Override
	public Ingredient deserialize(final JsonElement json, final Type typeOfT, final JsonDeserializationContext context) throws JsonParseException
	{
		if (json.isJsonPrimitive() && ((JsonPrimitive)json).isString()) {
			String s = json.getAsString();
			return IngredientUtil.parseFromString(s);
		} else
			LogUtil.error("Error parsing JSON: No Primitive String: $json");

		return Ingredient.EMPTY;
	}

	@Override
	public JsonElement serialize(final Ingredient src, final Type typeOfSrc, final JsonSerializationContext context)
	{
		if (src instanceof OreIngredientStoring)
			return new JsonPrimitive("ore:" + ((OreIngredientStoring)src).getOreName());
		else {
			ItemStack[] stacks = src.getMatchingStacks();
			if (stacks.length != 0)
				return new JsonPrimitive(Objects.requireNonNull(stacks[0].getItem().getRegistryName()).toString() + ":" + stacks[0].getItemDamage());
		}

		return new JsonPrimitive("minecraft:air:0");
	}
}
