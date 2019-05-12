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
import mod.sfhcore.json.JsonHelper;
import mod.sfhcore.util.LogUtil;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class CustomDryableJson implements JsonDeserializer<Dryable>, JsonSerializer<Dryable>
{
    @Override
    public Dryable deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException
    {
        JsonHelper helper = new JsonHelper(json);
        
        String name = helper.getString("name");
        int meta = helper.getNullableInteger("meta", 0);
        int value = helper.getNullableInteger("millibuckets", 1);

        Item item = Item.getByNameOrId(name);
        
        if(item == null)
        {
            LogUtil.error("Error parsing JSON: Invalid Item: " + json.toString());
            LogUtil.error("This may result in crashing or other undefined behavior");
        }
        
        return new Dryable(item, meta, value);
    }
    
    @Override
    public JsonElement serialize(Dryable src, Type typeOfSrc, JsonSerializationContext context)
    {
        JsonObject jsonObject = new JsonObject();
        
        jsonObject.addProperty("name", src.getItem().getRegistryName().toString());
        jsonObject.addProperty("meta", new ItemStack(src.getItem()).getItemDamage());
        jsonObject.addProperty("millibuckets", src.getValue());
        
        System.out.println(jsonObject);

        return jsonObject;
    }
}