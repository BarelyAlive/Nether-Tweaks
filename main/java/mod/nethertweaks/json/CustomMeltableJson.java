package mod.nethertweaks.json;

import java.lang.reflect.Type;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import mod.nethertweaks.registry.registries.base.types.Meltable;
import mod.sfhcore.json.JsonHelper;
import mod.sfhcore.util.BlockInfo;

public class CustomMeltableJson implements JsonDeserializer<Meltable>, JsonSerializer<Meltable> {

	public static final CustomMeltableJson INSTANCE = new CustomMeltableJson();

	@Override
	public JsonElement serialize(final Meltable src, final Type typeOfSrc, final JsonSerializationContext context) {
		final JsonObject obj = new JsonObject();

		obj.addProperty("fluid", src.getFluid());
		obj.addProperty("amount", src.getAmount());

		if (src.getTextureOverride() != BlockInfo.EMPTY)
			obj.add("color", context.serialize(src.getTextureOverride(), BlockInfo.class));

		return obj;
	}

	@Override
	public Meltable deserialize(final JsonElement json, final Type typeOfT, final JsonDeserializationContext context)
			throws JsonParseException {
		final JsonHelper helper = new JsonHelper(json);
		final JsonObject obj = json.getAsJsonObject();

		final String fluid = helper.getString("fluid");
		final int amount = helper.getInteger("amount");

		BlockInfo textureOverride = BlockInfo.EMPTY;
		if (obj.has("textureOverride"))
			textureOverride = context.deserialize(json.getAsJsonObject().get("textureOverride"), BlockInfo.class);

		return new Meltable(fluid, amount, textureOverride);
	}

}
