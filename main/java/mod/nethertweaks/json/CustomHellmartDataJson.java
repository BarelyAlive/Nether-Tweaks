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
import mod.sfhcore.util.LogUtil;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class CustomHellmartDataJson implements JsonDeserializer<HellmartData>, JsonSerializer<HellmartData>
{
    @Override
    public HellmartData deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException
    {
        JsonHelper helper = new JsonHelper(json);
        
        String itemName = helper.getString("item_name");
        String currencyName = helper.getString("currency_name");
        int price = helper.getNullableInteger("price", 1);

        Item itemItem = Item.getByNameOrId(itemName);
        Item currencyItem = Item.getByNameOrId(currencyName);
                
        if(itemItem == null || currencyItem == null)
        {
            LogUtil.error("Error parsing JSON: Invalid Item: " + json.toString());
            LogUtil.error("This may result in crashing or other undefined behavior");
        }
        
        return new HellmartData(new ItemStack(itemItem), new ItemStack(currencyItem), price);
    }
    
    @Override
    public JsonElement serialize(HellmartData src, Type typeOfSrc, JsonSerializationContext context)
    {
        JsonObject jsonObject = new JsonObject();
        
        jsonObject.addProperty("item_name", src.getItem().getItem().getRegistryName().toString() + ":" + src.getItem().getItemDamage());
        jsonObject.addProperty("currency_name", src.getCurrency().getItem().getRegistryName().toString());
        jsonObject.addProperty("price", src.getPrice());

        return jsonObject;
    }
}