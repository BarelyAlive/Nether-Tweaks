package mod.nethertweaks.json;


import java.lang.reflect.Type;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import mod.nethertweaks.registry.registries.base.types.Dryable;
import mod.sfhcore.json.JsonHelper;
import mod.sfhcore.util.ItemInfo;

public class CustomDryableJson implements JsonDeserializer<Dryable>, JsonSerializer<Dryable>
{
	@Override
	public JsonElement serialize(final Dryable src, final Type typeOfSrc, final JsonSerializationContext context)
	{
		JsonObject obj = new JsonObject();
		obj.addProperty("value", src.getValue());
		obj.add("item", context.serialize(new ItemInfo(src.getItem()), ItemInfo.class));

		return obj;
	}

	@Override
	public Dryable deserialize(final JsonElement json, final Type typeOfT, final JsonDeserializationContext context) throws JsonParseException
	{
		JsonHelper helper = new JsonHelper(json);
		JsonObject obj = json.getAsJsonObject();

		int value =  helper.getInteger("value");
		ItemInfo result = context.deserialize(obj.get("item"), ItemInfo.class);

		return new Dryable(result.getItemStack(), value);
	}
}