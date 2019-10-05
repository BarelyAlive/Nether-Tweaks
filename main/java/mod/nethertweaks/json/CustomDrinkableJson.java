package mod.nethertweaks.json;


import java.lang.reflect.Type;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import mod.nethertweaks.registry.types.Drinkable;
import mod.sfhcore.json.JsonHelper;
import mod.sfhcore.util.ItemInfo;

public class CustomDrinkableJson implements JsonDeserializer<Drinkable>, JsonSerializer<Drinkable>
{
	@Override
	public JsonElement serialize(final Drinkable src, final Type typeOfSrc, final JsonSerializationContext context)
	{
		JsonObject obj = new JsonObject();
		obj.addProperty("thirstReplenish", src.getThirstReplenish());
		obj.addProperty("saturationReplenish", src.getSaturationReplenish());
		obj.addProperty("poisonChance", src.getPoisonChance());
		obj.add("item", context.serialize(new ItemInfo(src.getItem()), ItemInfo.class));

		return obj;
	}

	@Override
	public Drinkable deserialize(final JsonElement json, final Type typeOfT, final JsonDeserializationContext context) throws JsonParseException
	{
		JsonHelper helper = new JsonHelper(json);
		JsonObject obj = json.getAsJsonObject();

		int thirstReplenish = helper.getInteger("thirstReplenish");
		float saturationReplenish = helper.getNullableFloat("saturationReplenish", 0);
		float poisonChance = helper.getNullableFloat("poisonChance", 0);
		ItemInfo result = context.deserialize(obj.get("item"), ItemInfo.class);

		return new Drinkable(result.getItemStack(), thirstReplenish, saturationReplenish, poisonChance);
	}
}