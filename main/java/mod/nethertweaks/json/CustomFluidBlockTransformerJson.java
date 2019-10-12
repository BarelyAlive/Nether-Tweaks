package mod.nethertweaks.json;

import java.lang.reflect.Type;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import mod.nethertweaks.registry.registries.base.types.FluidBlockTransformer;
import mod.sfhcore.util.BlockInfo;
import mod.sfhcore.util.EntityInfo;
import net.minecraft.item.crafting.Ingredient;

public class CustomFluidBlockTransformerJson implements JsonDeserializer<FluidBlockTransformer>, JsonSerializer<FluidBlockTransformer>
{
	@Override
	public JsonElement serialize(final FluidBlockTransformer src, final Type typeOfSrc, final JsonSerializationContext context)
	{
		JsonObject obj = new JsonObject();
		obj.addProperty("fluidName", src.getFluidName());
		obj.add("input", context.serialize(src.getInput()));
		obj.add("output", context.serialize(src.getOutput()));

		if (src.getToSpawn() != EntityInfo.EMPTY)
			obj.add("toSpawn", context.serialize(src.getToSpawn().getName()));

		if (src.getSpawnCount() != 0)
			obj.addProperty("spawnCount", src.getSpawnCount());

		if (src.getSpawnRange() != 0)
			obj.addProperty("spawnRange", src.getSpawnRange());

		return obj;
	}
	@Override
	public FluidBlockTransformer deserialize(final JsonElement json, final Type typeOfT, final JsonDeserializationContext context)
			throws JsonParseException
	{
		JsonObject obj = json.getAsJsonObject();

		return new FluidBlockTransformer(
				//obj.getAsJsonPrimitive("fluidName").getAsString(),
				obj.get("fluidName").getAsString(),
				context.deserialize(obj.get("input"), Ingredient.class),
				context.deserialize(obj.get("output"), BlockInfo.class),
				obj.has("toSpawn") ?
						context.deserialize(obj.get("toSpawn"), EntityInfo.class).toString() : EntityInfo.EMPTY.getName(),
						obj.has("spawnCount") ? obj.getAsJsonPrimitive("spawnCount").getAsInt() : 0,
								obj.has("spawnRange") ? obj.getAsJsonPrimitive("spawnRange").getAsInt() : 0
				);
	}
}
