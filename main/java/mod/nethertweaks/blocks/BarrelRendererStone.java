package mod.nethertweaks.blocks;

import javax.swing.Icon;

import org.lwjgl.opengl.GL11;

import mod.nethertweaks.BucketLoader;
import mod.nethertweaks.blocks.tileentities.TileEntityBarrel;
import mod.nethertweaks.blocks.tileentities.TileEntityBarrel.BarrelMode;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.init.Blocks;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidRegistry;

public class BarrelRendererStone{
	
//	public BlockBarrelRendererStone() {
//	}
//	
//	@Override
//	public void renderInventoryBlock(Block block, int metadata, int modelId, RenderBlocks renderer) {
//		
//	}
//
//	@Override
//	public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z,
//			Block block, int modelId, RenderBlocks renderer) {
//		if(NetherTweaksModClientProxy.renderPass == 0) {
//			drawBarrel(world, x, y, z);
//		}
//		return true;
//	}
//	
//	
//	@SideOnly(Side.CLIENT)
//	public static void drawBarrel(IBlockAccess world, int x, int y, int z) {
//		IIcon c = Blocks.stone.getIcon(0, 0);
//		IIcon c1 = Blocks.dirt.getIcon(0, 0);
//		IIcon c2 = Blocks.water.getIcon(0, 0);
//		IIcon c3 = BucketLoader.blockDemonWater.getIcon(0, 0);
//		IIcon c4 = Blocks.obsidian.getIcon(0, 0);
//		IIcon c5 = Blocks.hay_block.getIcon(0, 0);
//		IIcon c6 = Blocks.clay.getIcon(0, 0);
//		IIcon c7 = Blocks.cobblestone.getIcon(0, 0);
//		IIcon c8 = Blocks.sapling.getIcon(0, 0);
//		IIcon c9 = NTMBlocks.blockSlime.getIcon(0, 0);
//		IIcon c10 = Blocks.lava.getIcon(0, 0);
//		float u = c.getMinU();
//		float v = c.getMinV();
//		float U = c.getMaxU();
//		float V = c.getMaxV();
//		float V_2= ((c.getMaxV() - c.getMinV())/16) + c.getMinV();
//		
//		//Dirt
//		float u1 = c1.getMinU();
//		float v1 = c1.getMinV();
//		float U1 = c1.getMaxU();
//		float V1 = c1.getMaxV();
//		
//		//Water
//		float u2 = c2.getMinU();
//		float v2 = c2.getMinV();
//		float U2 = c2.getMaxU();
//		float V2 = c2.getMaxV();
//		
//		//Dwater
//		float u3 = c3.getMinU();
//		float v3 = c3.getMinV();
//		float U3 = c3.getMaxU();
//		float V3 = c3.getMaxV();
//		
//		//Compost
//		float u4 = c4.getMinU();
//		float v4 = c4.getMinV();
//		float U4 = c4.getMaxU();
//		float V4 = c4.getMaxV();
//		
//		//Compost
//		float u5 = c5.getMinU();
//		float v5 = c5.getMinV();
//		float U5 = c5.getMaxU();
//		float V5 = c5.getMaxV();
//		
//		//clay
//		float u6 = c6.getMinU();
//		float v6 = c6.getMinV();
//		float U6 = c6.getMaxU();
//		float V6 = c6.getMaxV();
//		
//		//Cobblestone
//		float u7 = c7.getMinU();
//		float v7 = c7.getMinV();
//		float U7 = c7.getMaxU();
//		float V7 = c7.getMaxV();
//		
//		//Milk
//		float u8 = c8.getMinU();
//		float v8 = c8.getMinV();
//		float U8 = c8.getMaxU();
//		float V8 = c8.getMaxV();
//		
//		//Slime
//		float u9 = c9.getMinU();
//		float v9 = c9.getMinV();
//		float U9 = c9.getMaxU();
//		float V9 = c9.getMaxV();
//		
//		//Lava
//		float u10 = c10.getMinU();
//		float v10 = c10.getMinV();
//		float U10 = c10.getMaxU();
//		float V10 = c10.getMaxV();
//		
//
//		
//		double dval2 = 0;
//		TileEntityBarrel barrel = (TileEntityBarrel)world.getTileEntity(x, y, z);
//		Tessellator t = Tessellator.instance;
//		t.setColorRGBA(195, 195, 195, 255);
//		t.addTranslation(x, y, z);
//		
//		// Outside
//		
//		t.addVertexWithUV(1.00D, 0.10D, 0.00D, u, v);
//		t.addVertexWithUV(1.00D, 0.10D, 1.00D, u, V);
//		t.addVertexWithUV(0.00D, 0.10D, 1.00D, U, V);
//		t.addVertexWithUV(0.00D, 0.10D, 0.00D, U, v);
//		
//		t.addVertexWithUV(0.00D, 0.10D, 0.00D, u, v);
//		t.addVertexWithUV(0.00D, 1.00D, 0.00D, u, V);
//		t.addVertexWithUV(1.00D, 1.00D, 0.00D, U, V);
//		t.addVertexWithUV(1.00D, 0.10D, 0.00D, U, v);
//		
//		t.addVertexWithUV(1.00D, 0.10D, 0.00D, u, v);
//		t.addVertexWithUV(1.00D, 1.00D, 0.00D, u, V);
//		t.addVertexWithUV(1.00D, 1.00D, 1.00D, U, V);
//		t.addVertexWithUV(1.00D, 0.10D, 1.00D, U, v);
//		
//		t.addVertexWithUV(1.00D, 0.10D, 1.00D, u, v);
//		t.addVertexWithUV(1.00D, 1.00D, 1.00D, u, V);
//		t.addVertexWithUV(0.00D, 1.00D, 1.00D, U, V);
//		t.addVertexWithUV(0.00D, 0.10D, 1.00D, U, v);
//		
//		t.addVertexWithUV(0.00D, 0.10D, 1.00D, u, v);
//		t.addVertexWithUV(0.00D, 1.00D, 1.00D, u, V);
//		t.addVertexWithUV(0.00D, 1.00D, 0.00D, U, V);
//		t.addVertexWithUV(0.00D, 0.10D, 0.00D, U, v);
//		
//		// Ring
//		
//		t.addVertexWithUV(0.00D, 1.00D, 0.00D, u, v);
//		t.addVertexWithUV(0.10D, 1.00D, 0.10D, u, V);
//		t.addVertexWithUV(0.90D, 1.00D, 0.10D, U, V);
//		t.addVertexWithUV(1.00D, 1.00D, 0.00D, U, v);
//		
//		t.addVertexWithUV(1.00D, 1.00D, 0.00D, u, v);
//		t.addVertexWithUV(0.90D, 1.00D, 0.10D, u, V);
//		t.addVertexWithUV(0.90D, 1.00D, 0.90D, U, V);
//		t.addVertexWithUV(1.00D, 1.00D, 1.00D, U, v);
//		
//		t.addVertexWithUV(1.00D, 1.00D, 1.00D, u, v);
//		t.addVertexWithUV(0.90D, 1.00D, 0.90D, u, V);
//		t.addVertexWithUV(0.10D, 1.00D, 0.90D, U, V);
//		t.addVertexWithUV(0.00D, 1.00D, 1.00D, U, v);
//		
//		t.addVertexWithUV(0.00D, 1.00D, 1.00D, u, v);
//		t.addVertexWithUV(0.10D, 1.00D, 0.90D, u, V);
//		t.addVertexWithUV(0.10D, 1.00D, 0.10D, U, V);
//		t.addVertexWithUV(0.00D, 1.00D, 0.00D, U, v);
//		
//		// Innerei
//		
//		t.addVertexWithUV(0.10D, 0.20D, 0.10D, u, v);
//		t.addVertexWithUV(0.10D, 0.20D, 0.90D, u, V);
//		t.addVertexWithUV(0.90D, 0.20D, 0.90D, U, V);
//		t.addVertexWithUV(0.90D, 0.20D, 0.10D, U, v);
//		
//		t.addVertexWithUV(0.90D, 0.20D, 0.10D, u, v);
//		t.addVertexWithUV(0.90D, 1.00D, 0.10D, u, V);
//		t.addVertexWithUV(0.10D, 1.00D, 0.10D, U, V);
//		t.addVertexWithUV(0.10D, 0.20D, 0.10D, U, v);
//		
//		t.addVertexWithUV(0.10D, 0.20D, 0.10D, u, v);
//		t.addVertexWithUV(0.10D, 1.00D, 0.10D, u, V);
//		t.addVertexWithUV(0.10D, 1.00D, 0.90D, U, V);
//		t.addVertexWithUV(0.10D, 0.20D, 0.90D, U, v);
//		
//		t.addVertexWithUV(0.10D, 0.20D, 0.90D, u, v);
//		t.addVertexWithUV(0.10D, 1.00D, 0.90D, u, V);
//		t.addVertexWithUV(0.90D, 1.00D, 0.90D, U, V);
//		t.addVertexWithUV(0.90D, 0.20D, 0.90D, U, v);
//		
//		t.addVertexWithUV(0.90D, 0.20D, 0.90D, u, v);
//		t.addVertexWithUV(0.90D, 1.00D, 0.90D, u, V);
//		t.addVertexWithUV(0.90D, 1.00D, 0.10D, U, V);
//		t.addVertexWithUV(0.90D, 0.20D, 0.10D, U, v);
//		
//		// Pfosten 1
//		
//		t.addVertexWithUV(0.10D, 0.01D, 0.00D, u, v);
//		t.addVertexWithUV(0.10D, 0.01D, 0.10D, u, V);
//		t.addVertexWithUV(0.00D, 0.01D, 0.10D, U, V);
//		t.addVertexWithUV(0.00D, 0.01D, 0.00D, U, v);
//		
//		t.addVertexWithUV(0.00D, 0.01D, 0.00D, u, v);
//		t.addVertexWithUV(0.00D, 0.10D, 0.00D, u, V);
//		t.addVertexWithUV(0.10D, 0.10D, 0.00D, U, V);
//		t.addVertexWithUV(0.10D, 0.01D, 0.00D, U, v);
//		
//		t.addVertexWithUV(0.10D, 0.01D, 0.00D, u, v);
//		t.addVertexWithUV(0.10D, 0.10D, 0.00D, u, V);
//		t.addVertexWithUV(0.10D, 0.10D, 0.10D, U, V);
//		t.addVertexWithUV(0.10D, 0.01D, 0.10D, U, v);
//		
//		t.addVertexWithUV(0.10D, 0.01D, 0.10D, u, v);
//		t.addVertexWithUV(0.10D, 0.10D, 0.10D, u, V);
//		t.addVertexWithUV(0.00D, 0.10D, 0.10D, U, V);
//		t.addVertexWithUV(0.00D, 0.01D, 0.10D, U, v);
//		
//		t.addVertexWithUV(0.00D, 0.01D, 0.10D, u, v);
//		t.addVertexWithUV(0.00D, 0.10D, 0.10D, u, V);
//		t.addVertexWithUV(0.00D, 0.10D, 0.00D, U, V);
//		t.addVertexWithUV(0.00D, 0.01D, 0.00D, U, v);
//		
//		// Pfosten 2
//		
//		t.addVertexWithUV(1.00D, 0.01D, 0.00D, u, v);
//		t.addVertexWithUV(1.00D, 0.01D, 0.10D, u, V);
//		t.addVertexWithUV(0.90D, 0.01D, 0.10D, U, V);
//		t.addVertexWithUV(0.90D, 0.01D, 0.00D, U, v);
//		
//		t.addVertexWithUV(0.90D, 0.01D, 0.00D, u, v);
//		t.addVertexWithUV(0.90D, 0.10D, 0.00D, u, V);
//		t.addVertexWithUV(1.00D, 0.10D, 0.00D, U, V);
//		t.addVertexWithUV(1.00D, 0.01D, 0.00D, U, v);
//		
//		t.addVertexWithUV(1.00D, 0.01D, 0.00D, u, v);
//		t.addVertexWithUV(1.00D, 0.10D, 0.00D, u, V);
//		t.addVertexWithUV(1.00D, 0.10D, 0.10D, U, V);
//		t.addVertexWithUV(1.00D, 0.01D, 0.10D, U, v);
//		
//		t.addVertexWithUV(1.00D, 0.01D, 0.10D, u, v);
//		t.addVertexWithUV(1.00D, 0.10D, 0.10D, u, V);
//		t.addVertexWithUV(0.90D, 0.10D, 0.10D, U, V);
//		t.addVertexWithUV(0.90D, 0.01D, 0.10D, U, v);
//		
//		t.addVertexWithUV(0.90D, 0.01D, 0.10D, u, v);
//		t.addVertexWithUV(0.90D, 0.10D, 0.10D, u, V);
//		t.addVertexWithUV(0.90D, 0.10D, 0.00D, U, V);
//		t.addVertexWithUV(0.90D, 0.01D, 0.00D, U, v);
//		
//		//Pfosten 3
//		
//		t.addVertexWithUV(0.10D, 0.01D, 0.90D, u, v);
//		t.addVertexWithUV(0.10D, 0.01D, 1.00D, u, V);
//		t.addVertexWithUV(0.00D, 0.01D, 1.00D, U, V);
//		t.addVertexWithUV(0.00D, 0.01D, 0.90D, U, v);
//		
//		t.addVertexWithUV(0.00D, 0.01D, 0.90D, u, v);
//		t.addVertexWithUV(0.00D, 0.10D, 0.90D, u, V);
//		t.addVertexWithUV(0.10D, 0.10D, 0.90D, U, V);
//		t.addVertexWithUV(0.10D, 0.01D, 0.90D, U, v);
//		
//		t.addVertexWithUV(0.10D, 0.01D, 0.90D, u, v);
//		t.addVertexWithUV(0.10D, 0.10D, 0.90D, u, V);
//		t.addVertexWithUV(0.10D, 0.10D, 1.00D, U, V);
//		t.addVertexWithUV(0.10D, 0.01D, 1.00D, U, v);
//		
//		t.addVertexWithUV(0.10D, 0.01D, 1.00D, u, v);
//		t.addVertexWithUV(0.10D, 0.10D, 1.00D, u, V);
//		t.addVertexWithUV(0.00D, 0.10D, 1.00D, U, V);
//		t.addVertexWithUV(0.00D, 0.01D, 1.00D, U, v);
//		
//		t.addVertexWithUV(0.00D, 0.01D, 1.00D, u, v);
//		t.addVertexWithUV(0.00D, 0.10D, 1.00D, u, V);
//		t.addVertexWithUV(0.00D, 0.10D, 0.90D, U, V);
//		t.addVertexWithUV(0.00D, 0.01D, 0.90D, U, v);
//		
//		// Pfosten 4
//		
//		t.addVertexWithUV(1.00D, 0.01D, 0.90D, u, v);
//		t.addVertexWithUV(1.00D, 0.01D, 1.00D, u, V);
//		t.addVertexWithUV(0.90D, 0.01D, 1.00D, U, V);
//		t.addVertexWithUV(0.90D, 0.01D, 0.90D, U, v);
//		
//		t.addVertexWithUV(0.90D, 0.01D, 0.90D, u, v);
//		t.addVertexWithUV(0.90D, 0.10D, 0.90D, u, V);
//		t.addVertexWithUV(1.00D, 0.10D, 0.90D, U, V);
//		t.addVertexWithUV(1.00D, 0.01D, 0.90D, U, v);
//		
//		t.addVertexWithUV(1.00D, 0.01D, 0.90D, u, v);
//		t.addVertexWithUV(1.00D, 0.10D, 0.90D, u, V);
//		t.addVertexWithUV(1.00D, 0.10D, 1.00D, U, V);
//		t.addVertexWithUV(1.00D, 0.01D, 1.00D, U, v);
//		
//		t.addVertexWithUV(1.00D, 0.01D, 1.00D, u, v);
//		t.addVertexWithUV(1.00D, 0.10D, 1.00D, u, V);
//		t.addVertexWithUV(0.90D, 0.10D, 1.00D, U, V);
//		t.addVertexWithUV(0.90D, 0.01D, 1.00D, U, v);
//		
//		t.addVertexWithUV(0.90D, 0.01D, 1.00D, u, v);
//		t.addVertexWithUV(0.90D, 0.10D, 1.00D, u, V);
//		t.addVertexWithUV(0.90D, 0.10D, 0.90D, U, V);
//		t.addVertexWithUV(0.90D, 0.01D, 0.90D, U, v);
//		
//		
//		if(barrel.getMode() == BarrelMode.DIRT){
//			
//			t.addVertexWithUV(0.10D, 0.90D, 0.10D, u1, v1);
//			t.addVertexWithUV(0.10D, 0.90D, 0.90D, u1, V1);
//			t.addVertexWithUV(0.90D, 0.90D, 0.90D, U1, V1);
//			t.addVertexWithUV(0.90D, 0.90D, 0.10D, U1, v1);
//			
//		}
//		
//		if(barrel.getMode() == BarrelMode.FLUID){
//			
//			t.addVertexWithUV(0.10D, 0.90D, 0.10D, u2, v2);
//			t.addVertexWithUV(0.10D, 0.90D, 0.90D, u2, V2);
//			t.addVertexWithUV(0.90D, 0.90D, 0.90D, U2, V2);
//			t.addVertexWithUV(0.90D, 0.90D, 0.10D, U2, v2);
//			
//		}
//		
//		if(barrel.getMode() == BarrelMode.FLUID && barrel.fluid.getFluidID() == FluidRegistry.LAVA.getID()){
//			
//			t.addVertexWithUV(0.10D, 0.90D, 0.10D, u10, v10);
//			t.addVertexWithUV(0.10D, 0.90D, 0.90D, u10, V10);
//			t.addVertexWithUV(0.90D, 0.90D, 0.90D, U10, V10);
//			t.addVertexWithUV(0.90D, 0.90D, 0.10D, U10, v10);
//			
//		}
//		
//		if(barrel.getMode() == BarrelMode.FLUID && barrel.fluid.getFluidID() == BucketLoader.fluidDemonWater.getID()){
//			
//			t.addVertexWithUV(0.10D, 0.90D, 0.10D, u3, v3);
//			t.addVertexWithUV(0.10D, 0.90D, 0.90D, u3, V3);
//			t.addVertexWithUV(0.90D, 0.90D, 0.90D, U3, V3);
//			t.addVertexWithUV(0.90D, 0.90D, 0.10D, U3, v3);
//			
//		}
//		
//		if(barrel.getMode() == BarrelMode.OBSIDIAN){
//			
//			t.addVertexWithUV(0.10D, 0.90D, 0.10D, u4, v4);
//			t.addVertexWithUV(0.10D, 0.90D, 0.90D, u4, V4);
//			t.addVertexWithUV(0.90D, 0.90D, 0.90D, U4, V4);
//			t.addVertexWithUV(0.90D, 0.90D, 0.10D, U4, v4);
//			
//		}
//		
//		if(barrel.getMode() == BarrelMode.COMPOST){
//			
//			t.addVertexWithUV(0.10D, 0.90D, 0.10D, u5, v5);
//			t.addVertexWithUV(0.10D, 0.90D, 0.90D, u5, V5);
//			t.addVertexWithUV(0.90D, 0.90D, 0.90D, U5, V5);
//			t.addVertexWithUV(0.90D, 0.90D, 0.10D, U5, v5);
//			
//		}
//		
//		if(barrel.getMode() == BarrelMode.CLAY){
//			
//			t.addVertexWithUV(0.10D, 0.90D, 0.10D, u6, v6);
//			t.addVertexWithUV(0.10D, 0.90D, 0.90D, u6, V6);
//			t.addVertexWithUV(0.90D, 0.90D, 0.90D, U6, V6);
//			t.addVertexWithUV(0.90D, 0.90D, 0.10D, U6, v6);
//			
//		}
//		
//		if(barrel.getMode() == BarrelMode.COBBLESTONE){
//			
//			t.addVertexWithUV(0.10D, 0.90D, 0.10D, u7, v7);
//			t.addVertexWithUV(0.10D, 0.90D, 0.90D, u7, V7);
//			t.addVertexWithUV(0.90D, 0.90D, 0.90D, U7, V7);
//			t.addVertexWithUV(0.90D, 0.90D, 0.10D, U7, v7);
//			
//		}
//		
//		if(barrel.getMode() == BarrelMode.OAK){
//			
//			t.addVertexWithUV(0.10D, 0.90D, 0.10D, u8, v8);
//			t.addVertexWithUV(0.10D, 0.90D, 0.90D, u8, V8);
//			t.addVertexWithUV(0.90D, 0.90D, 0.90D, U8, V8);
//			t.addVertexWithUV(0.90D, 0.90D, 0.10D, U8, v8);
//			
//		}
//		
//		if(barrel.getMode() == BarrelMode.SLIME){
//			
//			t.addVertexWithUV(0.10D, 0.90D, 0.10D, u9, v9);
//			t.addVertexWithUV(0.10D, 0.90D, 0.90D, u9, V9);
//			t.addVertexWithUV(0.90D, 0.90D, 0.90D, U9, V9);
//			t.addVertexWithUV(0.90D, 0.90D, 0.10D, U9, v9);
//			
//		}
//		
//		if(barrel.getMode() == BarrelMode.ENDSTONE){
//			
//			t.addVertexWithUV(0.10D, 0.90D, 0.10D, u10, v10);
//			t.addVertexWithUV(0.10D, 0.90D, 0.90D, u10, V10);
//			t.addVertexWithUV(0.90D, 0.90D, 0.90D, U10, V10);
//			t.addVertexWithUV(0.90D, 0.90D, 0.10D, U10, v10);
//			
//		}
//		
//		t.addTranslation(-x, -y, -z);
//		
//	}
//	
//	
//	
//	@Override
//	public boolean shouldRender3DInInventory(int modelId) {
//		return false;
//	}
//	
//	@Override
//	public int getRenderId() {
//		return NetherTweaksModClientProxy.blockBarrelRendererId;
//	}
	
}
