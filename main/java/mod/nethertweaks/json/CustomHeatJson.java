package mod.nethertweaks.json;

import com.google.gson.*;

import mod.nethertweaks.registry.types.Compostable;
import mod.nethertweaks.registry.types.Heat;
import mod.sfhcore.json.JsonHelper;
import mod.sfhcore.texturing.Color;
import mod.sfhcore.util.BlockInfo;

import java.lang.reflect.Type;

public class CustomHeatJson implements JsonDeserializer<Heat>, JsonSerializer<Heat>
{
    @Override
    public JsonElement serialize(Heat src, Type typeOfSrc, JsonSerializationContext context)
    {
        JsonObject obj = new JsonObject();
        obj.add("heatBlock", context.serialize(src.getItem(), BlockInfo.class));
        obj.addProperty("value", src.getValue());
        
        return obj;
    }

    @Override
    public Heat deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException
    {
        JsonHelper helper = new JsonHelper(json);
        JsonObject obj = json.getAsJsonObject();

        int value =  helper.getInteger("value");

        BlockInfo result = context.deserialize(obj.get("heatBlock"), BlockInfo.class);

        return new Heat(result, value);
    }
}
