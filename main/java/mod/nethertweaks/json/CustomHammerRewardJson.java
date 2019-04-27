package mod.nethertweaks.json;

import com.google.gson.*;

import mod.nethertweaks.registry.types.HammerReward;
import mod.sfhcore.json.JsonHelper;
import mod.sfhcore.util.ItemInfo;
import mod.sfhcore.util.LogUtil;
import net.minecraft.item.ItemStack;

import java.lang.reflect.Type;

public class CustomHammerRewardJson implements JsonDeserializer<HammerReward>, JsonSerializer<HammerReward>
{
    @Override
    public JsonElement serialize(HammerReward src, Type typeOfSrc, JsonSerializationContext context)
    {
    	JsonObject obj = new JsonObject();

        obj.addProperty("item", src.getItemStack().getItem().getRegistryName().toString() + ":" + src.getItemStack().getItemDamage());
        obj.addProperty("amount", src.getItemStack().getCount());
        obj.addProperty("miningLevel", src.getMiningLevel());
        obj.addProperty("chance", src.getChance());
        obj.addProperty("fortuneChance", src.getFortuneChance());

        return obj;
    }

    @Override
    public HammerReward deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException{
        JsonHelper helper = new JsonHelper(json);
        JsonObject obj = json.getAsJsonObject();


        // gets the item in both the new and the old way
        ItemStack stack = ItemStack.EMPTY;
        if (obj.has("item")) {
            ItemInfo info = new ItemInfo(helper.getString("item"));
            if (!info.isValid()) {
                LogUtil.error("Error parsing JSON: Invalid Item: $json");
                return new HammerReward(ItemStack.EMPTY, 0, 0f, 0f);
            }

            stack = new ItemStack(info.getItem(), helper.getInteger("amount"), info.getMeta());
        } else {
            context.deserialize(json.getAsJsonObject().get("stack"), ItemStack.class);
        }

        int miningLevel = helper.getInteger("miningLevel");
        float chance = (float) helper.getDouble("chance");
        double fortuneChance = (float) helper.getDouble("fortuneChance");

        return new HammerReward(stack, miningLevel, chance, fortuneChance);
    }
}
