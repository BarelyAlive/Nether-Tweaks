package mod.nethertweaks.json;

import java.lang.reflect.Type;

import com.google.gson.*;

import mod.nethertweaks.registry.types.Meltable;
import mod.sfhcore.util.BlockInfo;

public class CustomMeltableJson implements JsonDeserializer<Meltable>, JsonSerializer<Meltable> {

	public static final CustomMeltableJson INSTANCE = new CustomMeltableJson();

	@Override
	public JsonElement serialize(Meltable src, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject obj = new JsonObject();

        obj.addProperty("fluid", src.getFluid());
        obj.addProperty("amount", src.getAmount());

        if (src.getTextureOverride() != BlockInfo.EMPTY) {
            obj.add("color", context.serialize(src.getTextureOverride(), BlockInfo.class));
        }

        return obj;
	}

	@Override
	public Meltable deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
			throws JsonParseException {
		JsonHelper helper = new JsonHelper(json);
	    JsonObject obj = json.getAsJsonObject();

	    String fluid = helper.getString("fluid");
	    int amount = helper.getInteger("amount");

	    BlockInfo textureOverride = BlockInfo.EMPTY;
	    if (obj.has("textureOverride")) {
	    	textureOverride = context.deserialize(json.getAsJsonObject().get("textureOverride"), BlockInfo.class);
	    }

	    return new Meltable(fluid, amount, textureOverride);
	}

}
