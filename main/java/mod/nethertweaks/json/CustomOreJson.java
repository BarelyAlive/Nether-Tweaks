package mod.nethertweaks.json;

import com.google.gson.*;

import mod.nethertweaks.registry.types.Ore;
import mod.sfhcore.json.JsonHelper;
import mod.sfhcore.texturing.Color;
import mod.sfhcore.util.ItemInfo;

import java.lang.reflect.Type;
import java.util.*;

public class CustomOreJson implements JsonDeserializer<Ore>, JsonSerializer<Ore>
{
	public static final CustomOreJson INSTANCE = new CustomOreJson();
	
    @Override
    public JsonElement serialize(Ore src, Type typeOfSrc, JsonSerializationContext context)
    {
        JsonObject obj = new JsonObject();
        obj.addProperty("name", src.getName());
        obj.add("color", context.serialize(src.getColor(), Color.class));
        if(src.getResult() != null)
            obj.add("ingot", context.serialize(src.getResult(), ItemInfo.class));
        if(src.getDustResult() != null)
            obj.add("dust", context.serialize(src.getDustResult(), ItemInfo.class));

        if (src.getOredictName() != null) {
            obj.addProperty("oredictName", src.getOredictName());
        }

        if (src.getTranslations() != null) {
            obj.add("translations", context.serialize(src.getTranslations(), src.getTranslations().getClass()));
        }

        return obj;
    }

	@Override
	public Ore deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
			throws JsonParseException {
		JsonHelper helper = new JsonHelper(json);

        String name = helper.getString("name");
        Color color = context.deserialize(json.getAsJsonObject().get("color"), Color.class);
        ItemInfo ingot = json.getAsJsonObject().has("result") ? context.deserialize(json.getAsJsonObject().get("result"), ItemInfo.class)
        		: json.getAsJsonObject().has("ingot") ? context.deserialize(json.getAsJsonObject().get("ingot"), ItemInfo.class) : null;
        ItemInfo dust = json.getAsJsonObject().has("dust") ? context.deserialize(json.getAsJsonObject().get("dust"), ItemInfo.class) : null;

        HashMap<String, String> translations = null;
        if (json.isJsonObject() && json.getAsJsonObject().has("translations")) {
            translations = context.deserialize(json.getAsJsonObject().get("translations"), json.getAsJsonObject().get("translations").getClass());
        }

        String oredictName = null;
        if (json.isJsonObject() && json.getAsJsonObject().has("oredictName")) {
            oredictName = helper.getString("oredictName");
        }

        return new Ore(name, color, ingot, dust, translations, oredictName);
	}
}
