package mod.nethertweaks.json;


import java.lang.reflect.Type;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import mod.nethertweaks.registry.types.Dryable;
import mod.nethertweaks.registry.types.HellmartData;
import mod.sfhcore.json.JsonHelper;
import mod.sfhcore.util.ItemInfo;
import mod.sfhcore.util.LogUtil;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class CustomHellmartDataJson implements JsonDeserializer<HellmartData>, JsonSerializer<HellmartData>
{
    @Override
    public HellmartData deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException
    {
        JsonHelper helper = new JsonHelper(json);
        JsonObject obj = json.getAsJsonObject();
        
        ItemInfo itemItem = context.deserialize(obj.get("item_name"), ItemInfo.class);
        ItemInfo currencyItem = context.deserialize(obj.get("currency_name"), ItemInfo.class);
        int price = helper.getNullableInteger("price", 1);

        if(itemItem == null || currencyItem == null)
        {
        	LogUtil.error(itemItem);
        	LogUtil.error(currencyItem);
            LogUtil.error("Error parsing JSON: Invalid Item: " + json.toString());
            LogUtil.error("This may result in crashing or other undefined behavior");
        }
        
        return new HellmartData(itemItem.getItemStack(), currencyItem.getItemStack(), price);
    }
    
    @Override
    public JsonElement serialize(HellmartData src, Type typeOfSrc, JsonSerializationContext context)
    {
        JsonObject jsonObject = new JsonObject();
        
        jsonObject.add("item_name", context.serialize(new ItemInfo(src.getItem()), ItemInfo.class));
        jsonObject.add("currency_name", context.serialize(new ItemInfo(src.getCurrency()), ItemInfo.class));
        jsonObject.addProperty("price", src.getPrice());

        return jsonObject;
    }
}