package mod.nethertweaks.json;

import java.lang.reflect.Type;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import mod.nethertweaks.registry.types.Heat;
import mod.sfhcore.json.JsonHelper;
import mod.sfhcore.util.BlockInfo;

public class CustomHeatJson implements JsonDeserializer<Heat>, JsonSerializer<Heat>
{
	@Override
	public JsonElement serialize(final Heat src, final Type typeOfSrc, final JsonSerializationContext context)
	{
		JsonObject obj = new JsonObject();
		obj.add("heatBlock", context.serialize(src.getItem(), BlockInfo.class));
		obj.addProperty("value", src.getValue());

		return obj;
	}

	@Override
	public Heat deserialize(final JsonElement json, final Type typeOfT, final JsonDeserializationContext context) throws JsonParseException
	{
		JsonHelper helper = new JsonHelper(json);
		JsonObject obj = json.getAsJsonObject();

		int value =  helper.getInteger("value");

		BlockInfo result = context.deserialize(obj.get("heatBlock"), BlockInfo.class);

		return new Heat(result, value);
	}
}
