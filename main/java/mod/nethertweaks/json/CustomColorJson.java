package mod.nethertweaks.json;

import com.google.gson.*;
import mod.sfhcore.texturing.Color;
import mod.sfhcore.util.LogUtil;

import java.lang.reflect.Type;

public class CustomColorJson implements JsonDeserializer<Color>, JsonSerializer<Color>
{
    @Override
    public Color deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException{
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
    public JsonPrimitive serialize(Color src, Type typeOfSrc, JsonSerializationContext context)
    {
        return new JsonPrimitive(src.getAsHexNoAlpha());
    }
}
