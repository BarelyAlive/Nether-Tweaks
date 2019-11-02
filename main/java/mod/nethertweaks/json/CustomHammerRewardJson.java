package mod.nethertweaks.json;

import java.lang.reflect.Type;
import java.util.Objects;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import mod.nethertweaks.registry.registries.base.types.HammerReward;
import mod.sfhcore.json.JsonHelper;
import mod.sfhcore.util.ItemInfo;
import mod.sfhcore.util.LogUtil;
import net.minecraft.item.ItemStack;

public class CustomHammerRewardJson implements JsonDeserializer<HammerReward>, JsonSerializer<HammerReward>
{
	@Override
	public JsonElement serialize(final HammerReward src, final Type typeOfSrc, final JsonSerializationContext context)
	{
		final JsonObject obj = new JsonObject();

		obj.addProperty("item", Objects.requireNonNull(src.getItemStack().getItem().getRegistryName()).toString() + ":" + src.getItemStack().getItemDamage());
		obj.addProperty("amount", src.getItemStack().getCount());
		obj.addProperty("miningLevel", src.getMiningLevel());
		obj.addProperty("chance", src.getChance());
		obj.addProperty("fortuneChance", src.getFortuneChance());

		return obj;
	}

	@Override
	public HammerReward deserialize(final JsonElement json, final Type typeOfT, final JsonDeserializationContext context) throws JsonParseException{
		final JsonHelper helper = new JsonHelper(json);
		final JsonObject obj = json.getAsJsonObject();


		// gets the item in both the new and the old way
		ItemStack stack = ItemStack.EMPTY;
		if (obj.has("item")) {
			final ItemInfo info = new ItemInfo(helper.getString("item"));
			if (!info.isValid()) {
				LogUtil.error("Error parsing JSON: Invalid Item: $json");
				return new HammerReward(ItemStack.EMPTY, 0, 0f, 0f);
			}

			stack = new ItemStack(info.getItem(), helper.getInteger("amount"), info.getMeta());
		} else
			context.deserialize(json.getAsJsonObject().get("stack"), ItemStack.class);

		final int miningLevel = helper.getInteger("miningLevel");
		final float chance = (float) helper.getDouble("chance");
		final double fortuneChance = (float) helper.getDouble("fortuneChance");

		return new HammerReward(stack, miningLevel, chance, fortuneChance);
	}
}
