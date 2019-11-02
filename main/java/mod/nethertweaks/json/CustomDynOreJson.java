package mod.nethertweaks.json;

import java.lang.reflect.Type;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import mod.nethertweaks.registry.types.DynOre;
import mod.sfhcore.json.JsonHelper;
import mod.sfhcore.util.ItemInfo;

public class CustomDynOreJson implements JsonDeserializer<DynOre>, JsonSerializer<DynOre>
{
	public static final CustomDynOreJson INSTANCE = new CustomDynOreJson();

    @Override
    public JsonElement serialize(final DynOre src, final Type typeOfSrc, final JsonSerializationContext context)
    {
        final JsonObject obj = new JsonObject();

        obj.addProperty("id", src.getID());
        obj.addProperty("name", src.getName());
        obj.add("ingot", context.serialize(src.getResult(), ItemInfo.class));
        obj.addProperty("color", src.getColor());
        obj.addProperty("rarity", src.getRarity());
        return obj;
    }

	@Override
	public DynOre deserialize(final JsonElement json, final Type typeOfT, final JsonDeserializationContext context)
			throws JsonParseException {
		final JsonHelper helper = new JsonHelper(json);

		final String id = helper.getString("id");
		final String name = helper.getString("name");

        final ItemInfo ingot = json.getAsJsonObject().has("ingot") ? context.deserialize(json.getAsJsonObject().get("ingot"), ItemInfo.class) : null;

		final int color = helper.getInteger("color");
		final int rarity = helper.getInteger("rarity");


        return new DynOre(id, name, ingot, color, rarity);
	}
}
