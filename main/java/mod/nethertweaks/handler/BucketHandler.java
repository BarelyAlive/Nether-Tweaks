package mod.nethertweaks.handler;

import java.util.HashMap;
import java.util.Map;

import mod.nethertweaks.BucketLoader;
import net.minecraft.block.Block;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.FillBucketEvent;
import net.minecraftforge.fml.common.eventhandler.Event.Result;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class BucketHandler {

        public static BucketHandler INSTANCE = new BucketHandler();
        public Map<Block, Item> buckets = new HashMap<Block, Item>();

        private BucketHandler() {
        }
        
        @SubscribeEvent
        public void onBucketFill(FillBucketEvent event) {

        	Block block = event.getWorld().getBlockState(event.getTarget().getBlockPos()).getBlock();
        	Item item = event.getEmptyBucket().getItem();
        	
        	if(buckets.get(block) == BucketLoader.bucketDemonWater && item == Items.BUCKET){
                ItemStack result = fillCustomBucket(event.getWorld(), event.getTarget());

                if (result == null){
                        return;
                }
                
                event.setResult(Result.ALLOW);
                
        	}
        	
        }

        private ItemStack fillCustomBucket(World world, RayTraceResult rtr) {

                Block block = world.getBlockState(rtr.getBlockPos()).getBlock();

                Item bucket = buckets.get(block);
                if (bucket != null) {
                        world.setBlockToAir(rtr.getBlockPos());
                        return new ItemStack(bucket);
                } else
                       return null;

        }
}