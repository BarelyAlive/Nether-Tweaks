package mod.nethertweaks.json;

import java.lang.reflect.Type;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import mod.nethertweaks.registry.registries.base.types.Compostable;
import mod.sfhcore.json.JsonHelper;
import mod.sfhcore.texturing.Color;
import mod.sfhcore.util.BlockInfo;

public class CustomCompostableJson implements JsonDeserializer<Compostable>, JsonSerializer<Compostable>
{
	@Override
	public JsonElement serialize(final Compostable src, final Type typeOfSrc, final JsonSerializationContext context)
	{
		final JsonObject obj = new JsonObject();
		obj.addProperty("value", src.getValue());
		obj.add("compostBlock", context.serialize(src.getCompostBlock(), BlockInfo.class));
		if (src.getColor() != Color.INVALID_COLOR)
			obj.add("color", context.serialize(src.getColor(), Color.class));

		return obj;
	}

	@Override
	public Compostable deserialize(final JsonElement json, final Type typeOfT, final JsonDeserializationContext context) throws JsonParseException
	{
		final JsonHelper helper = new JsonHelper(json);
		final JsonObject obj = json.getAsJsonObject();

		final float value = (float) helper.getDouble("value");
		Color color = Color.INVALID_COLOR;
		if (obj.has("color"))
			color = context.deserialize(obj.get("color"), Color.class);

		final BlockInfo result = context.deserialize(obj.get("compostBlock"), BlockInfo.class);

		return new Compostable(value, color, result);
	}
}