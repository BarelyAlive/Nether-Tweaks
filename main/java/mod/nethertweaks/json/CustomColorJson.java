package mod.nethertweaks.json;

import java.lang.reflect.Type;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import mod.sfhcore.json.JsonHelper;
import mod.sfhcore.texturing.Color;
import mod.sfhcore.util.LogUtil;

public class CustomColorJson implements JsonDeserializer<Color>, JsonSerializer<Color>
{
	public static final CustomColorJson INSTANCE = new CustomColorJson();

	@Override
	public Color deserialize(final JsonElement json, final Type typeOfT, final JsonDeserializationContext context) throws JsonParseException{
		if (json.isJsonPrimitive()) {
			JsonPrimitive prim = json.getAsJsonPrimitive();
			if(prim.isString())
				return new Color(prim.getAsString());
			if(prim.isNumber())
				return new Color(prim.getAsInt());
			else LogUtil.warn("Invalid Color primitive for $json");
		} else {
			JsonHelper helper = new JsonHelper(json);
			return new Color((float) helper.getDouble("r"), (float) helper.getDouble("g"), (float) helper.getDouble("b"), (float) helper.getDouble("a"));
		}

		return Color.INVALID_COLOR;
	}

	@Override
	public JsonPrimitive serialize(final Color src, final Type typeOfSrc, final JsonSerializationContext context)
	{
		return new JsonPrimitive(src.getAsHexNoAlpha());
	}
}
