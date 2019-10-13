package mod.nethertweaks.json;

import java.lang.reflect.Type;
import java.util.HashMap;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import mod.nethertweaks.registry.types.DynOre;
import mod.sfhcore.json.JsonHelper;
import mod.sfhcore.texturing.Color;
import mod.sfhcore.util.ItemInfo;

public class CustomDynOreJson implements JsonDeserializer<DynOre>, JsonSerializer<DynOre>
{
	public static final CustomDynOreJson INSTANCE = new CustomDynOreJson();
	
    @Override
    public JsonElement serialize(DynOre src, Type typeOfSrc, JsonSerializationContext context)
    {
        JsonObject obj = new JsonObject();
        
        obj.addProperty("id", src.getID());
        obj.addProperty("name", src.getName());
        obj.add("ingot", context.serialize(src.getResult(), ItemInfo.class));
        obj.addProperty("color", src.getColor());
        obj.addProperty("rarity", src.getRarity());
        return obj;
    }

	@Override
	public DynOre deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
			throws JsonParseException {
		JsonHelper helper = new JsonHelper(json);
		
		String id = helper.getString("id");
		String name = helper.getString("name");
		
        ItemInfo ingot = json.getAsJsonObject().has("ingot") ? context.deserialize(json.getAsJsonObject().get("ingot"), ItemInfo.class) : null;
		
		int color = helper.getInteger("color");
		int rarity = helper.getInteger("rarity");
		

        return new DynOre(id, name, ingot, color, rarity);
	}
}
