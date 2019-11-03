package mod.nethertweaks.blocks;

import javax.swing.Icon;

import org.lwjgl.opengl.GL11;

import mod.nethertweaks.BucketLoader;
import mod.nethertweaks.blocks.tileentities.TileEntityBarrel;
import mod.nethertweaks.blocks.tileentities.TileEntitySieve;
import mod.nethertweaks.blocks.tileentities.TileEntityBarrel.BarrelMode;
import mod.nethertweaks.blocks.tileentities.TileEntitySieve.SieveMode;
import mod.nethertweaks.items.NTMItems;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidRegistry;

public class SieveRenderer{
	
//	public BlockSieveRenderer() {
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
//			drawSieve(world, x, y, z);
//		}
//		return true;
//	}
//	
//	
//	@SideOnly(Side.CLIENT)
//	public static void drawSieve(IBlockAccess world, int x, int y, int z) {
//		TileEntitySieve sieve = (TileEntitySieve)world.getTileEntity(x, y, z);
//
//		IIcon c = Blocks.planks.getIcon(0, 0);
//		IIcon c1 = NTMBlocks.blockWeb.getIcon(0, 0);	
//		IIcon c3 = BucketLoader.blockDemonWater.getIcon(0, 0);
//		
//		float u = c.getMinU();
//		float v = c.getMinV();
//		float U = c.getMaxU();
//		float V = c.getMaxV();
//		float V_2= ((c.getMaxV() - c.getMinV())/16) + c.getMinV();
//		
//		//Web
//		float u1 = c1.getMinU();
//		float v1 = c1.getMinV();
//		float U1 = c1.getMaxU();
//		float V1 = c1.getMaxV();
//
//		
//		double dval2 = 0;
//		Tessellator t = Tessellator.instance;
//		t.setColorRGBA(195, 195, 195, 255);
//		t.addTranslation(x, y, z);
//		
//		// Outside
//		
//		//Unterseite
//		t.addVertexWithUV(0.10D, 0.90D, 0.10D, u1, v1);
//		t.addVertexWithUV(0.90D, 0.90D, 0.10D, u1, V1);
//		t.addVertexWithUV(0.90D, 0.90D, 0.90D, U1, V1);
//		t.addVertexWithUV(0.10D, 0.90D, 0.90D, U1, v1);
//		
//		t.addVertexWithUV(0.00D, 0.90D, 0.00D, u, v);
//		t.addVertexWithUV(0.00D, 1.00D, 0.00D, u, V);
//		t.addVertexWithUV(1.00D, 1.00D, 0.00D, U, V);
//		t.addVertexWithUV(1.00D, 0.90D, 0.00D, U, v);
//		
//		t.addVertexWithUV(1.00D, 0.90D, 0.00D, u, v);
//		t.addVertexWithUV(1.00D, 1.00D, 0.00D, u, V);
//		t.addVertexWithUV(1.00D, 1.00D, 1.00D, U, V);
//		t.addVertexWithUV(1.00D, 0.90D, 1.00D, U, v);
//		
//		t.addVertexWithUV(1.00D, 0.90D, 1.00D, u, v);
//		t.addVertexWithUV(1.00D, 1.00D, 1.00D, u, V);
//		t.addVertexWithUV(0.00D, 1.00D, 1.00D, U, V);
//		t.addVertexWithUV(0.00D, 0.90D, 1.00D, U, v);
//		
//		t.addVertexWithUV(0.00D, 0.90D, 1.00D, u, v);
//		t.addVertexWithUV(0.00D, 1.00D, 1.00D, u, V);
//		t.addVertexWithUV(0.00D, 1.00D, 0.00D, U, V);
//		t.addVertexWithUV(0.00D, 0.90D, 0.00D, U, v);
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
//		//Ring Unterseite
//		
//		t.addVertexWithUV(0.00D, 0.90D, 0.00D, u, v);
//		t.addVertexWithUV(0.10D, 0.90D, 0.10D, u, V);
//		t.addVertexWithUV(0.10D, 0.90D, 0.90D, U, V);
//		t.addVertexWithUV(0.00D, 0.90D, 1.00D, U, v);
//		
//		t.addVertexWithUV(0.00D, 0.90D, 1.00D, u, v);
//		t.addVertexWithUV(0.10D, 0.90D, 0.90D, u, V);
//		t.addVertexWithUV(0.90D, 0.90D, 0.90D, U, V);
//		t.addVertexWithUV(1.00D, 0.90D, 1.00D, U, v);
//		
//		t.addVertexWithUV(1.00D, 0.90D, 1.00D, u, v);
//		t.addVertexWithUV(0.90D, 0.90D, 0.90D, u, V);
//		t.addVertexWithUV(0.90D, 0.90D, 0.10D, U, V);
//		t.addVertexWithUV(1.00D, 0.90D, 0.00D, U, v);
//		
//		t.addVertexWithUV(1.00D, 0.90D, 0.00D, u, v);
//		t.addVertexWithUV(0.90D, 0.90D, 0.10D, u, V);
//		t.addVertexWithUV(0.10D, 0.90D, 0.10D, U, V);
//		t.addVertexWithUV(0.00D, 0.90D, 0.00D, U, v);
//		
//		// Innerei
//		
//		//Netz
//		t.addVertexWithUV(0.10D, 0.90D, 0.10D, u1, v1);
//		t.addVertexWithUV(0.10D, 0.90D, 0.90D, u1, V1);
//		t.addVertexWithUV(0.90D, 0.90D, 0.90D, U1, V1);
//		t.addVertexWithUV(0.90D, 0.90D, 0.10D, U1, v1);
//		
//		t.addVertexWithUV(0.90D, 0.90D, 0.10D, u, v);
//		t.addVertexWithUV(0.90D, 1.00D, 0.10D, u, V);
//		t.addVertexWithUV(0.10D, 1.00D, 0.10D, U, V);
//		t.addVertexWithUV(0.10D, 0.90D, 0.10D, U, v);
//		
//		t.addVertexWithUV(0.10D, 0.90D, 0.10D, u, v);
//		t.addVertexWithUV(0.10D, 1.00D, 0.10D, u, V);
//		t.addVertexWithUV(0.10D, 1.00D, 0.90D, U, V);
//		t.addVertexWithUV(0.10D, 0.90D, 0.90D, U, v);
//		
//		t.addVertexWithUV(0.10D, 0.90D, 0.90D, u, v);
//		t.addVertexWithUV(0.10D, 1.00D, 0.90D, u, V);
//		t.addVertexWithUV(0.90D, 1.00D, 0.90D, U, V);
//		t.addVertexWithUV(0.90D, 0.90D, 0.90D, U, v);
//		
//		t.addVertexWithUV(0.90D, 0.90D, 0.90D, u, v);
//		t.addVertexWithUV(0.90D, 1.00D, 0.90D, u, V);
//		t.addVertexWithUV(0.90D, 1.00D, 0.10D, U, V);
//		t.addVertexWithUV(0.90D, 0.90D, 0.10D, U, v);
//		
//		// Pfosten 1
//		
//		t.addVertexWithUV(0.10D, 0.01D, 0.00D, u, v);
//		t.addVertexWithUV(0.10D, 0.01D, 0.10D, u, V);
//		t.addVertexWithUV(0.00D, 0.01D, 0.10D, U, V);
//		t.addVertexWithUV(0.00D, 0.01D, 0.00D, U, v);
//		
//		t.addVertexWithUV(0.10D, 0.90D, 0.00D, u, v);
//		t.addVertexWithUV(0.10D, 0.90D, 0.10D, u, V);
//		t.addVertexWithUV(0.00D, 0.90D, 0.10D, U, V);
//		t.addVertexWithUV(0.00D, 0.90D, 0.00D, U, v);
//		
//		t.addVertexWithUV(0.00D, 0.01D, 0.00D, u, v);
//		t.addVertexWithUV(0.00D, 0.90D, 0.00D, u, V);
//		t.addVertexWithUV(0.10D, 0.90D, 0.00D, U, V);
//		t.addVertexWithUV(0.10D, 0.01D, 0.00D, U, v);
//		
//		t.addVertexWithUV(0.10D, 0.01D, 0.00D, u, v);
//		t.addVertexWithUV(0.10D, 0.90D, 0.00D, u, V);
//		t.addVertexWithUV(0.10D, 0.90D, 0.10D, U, V);
//		t.addVertexWithUV(0.10D, 0.01D, 0.10D, U, v);
//		
//		t.addVertexWithUV(0.10D, 0.01D, 0.10D, u, v);
//		t.addVertexWithUV(0.10D, 0.90D, 0.10D, u, V);
//		t.addVertexWithUV(0.00D, 0.90D, 0.10D, U, V);
//		t.addVertexWithUV(0.00D, 0.01D, 0.10D, U, v);
//		
//		t.addVertexWithUV(0.00D, 0.01D, 0.10D, u, v);
//		t.addVertexWithUV(0.00D, 0.90D, 0.10D, u, V);
//		t.addVertexWithUV(0.00D, 0.90D, 0.00D, U, V);
//		t.addVertexWithUV(0.00D, 0.01D, 0.00D, U, v);
//		
//		// Pfosten 2
//		
//		t.addVertexWithUV(1.00D, 0.01D, 0.00D, u, v);
//		t.addVertexWithUV(1.00D, 0.01D, 0.10D, u, V);
//		t.addVertexWithUV(0.90D, 0.01D, 0.10D, U, V);
//		t.addVertexWithUV(0.90D, 0.01D, 0.00D, U, v);
//		
//		t.addVertexWithUV(1.00D, 0.90D, 0.00D, u, v);
//		t.addVertexWithUV(1.00D, 0.90D, 0.10D, u, V);
//		t.addVertexWithUV(0.90D, 0.90D, 0.10D, U, V);
//		t.addVertexWithUV(0.90D, 0.90D, 0.00D, U, v);
//		
//		t.addVertexWithUV(0.90D, 0.01D, 0.00D, u, v);
//		t.addVertexWithUV(0.90D, 0.90D, 0.00D, u, V);
//		t.addVertexWithUV(1.00D, 0.90D, 0.00D, U, V);
//		t.addVertexWithUV(1.00D, 0.01D, 0.00D, U, v);
//		
//		t.addVertexWithUV(1.00D, 0.01D, 0.00D, u, v);
//		t.addVertexWithUV(1.00D, 0.90D, 0.00D, u, V);
//		t.addVertexWithUV(1.00D, 0.90D, 0.10D, U, V);
//		t.addVertexWithUV(1.00D, 0.01D, 0.10D, U, v);
//		
//		t.addVertexWithUV(1.00D, 0.01D, 0.10D, u, v);
//		t.addVertexWithUV(1.00D, 0.90D, 0.10D, u, V);
//		t.addVertexWithUV(0.90D, 0.90D, 0.10D, U, V);
//		t.addVertexWithUV(0.90D, 0.01D, 0.10D, U, v);
//		
//		t.addVertexWithUV(0.90D, 0.01D, 0.10D, u, v);
//		t.addVertexWithUV(0.90D, 0.90D, 0.10D, u, V);
//		t.addVertexWithUV(0.90D, 0.90D, 0.00D, U, V);
//		t.addVertexWithUV(0.90D, 0.01D, 0.00D, U, v);
//		
//		//Pfosten 3
//		
//		t.addVertexWithUV(0.10D, 0.01D, 0.90D, u, v);
//		t.addVertexWithUV(0.10D, 0.01D, 1.00D, u, V);
//		t.addVertexWithUV(0.00D, 0.01D, 1.00D, U, V);
//		t.addVertexWithUV(0.00D, 0.01D, 0.90D, U, v);
//		
//		t.addVertexWithUV(0.10D, 0.90D, 0.90D, u, v);
//		t.addVertexWithUV(0.10D, 0.90D, 1.00D, u, V);
//		t.addVertexWithUV(0.00D, 0.90D, 1.00D, U, V);
//		t.addVertexWithUV(0.00D, 0.90D, 0.90D, U, v);
//		
//		t.addVertexWithUV(0.00D, 0.01D, 0.90D, u, v);
//		t.addVertexWithUV(0.00D, 0.90D, 0.90D, u, V);
//		t.addVertexWithUV(0.10D, 0.90D, 0.90D, U, V);
//		t.addVertexWithUV(0.10D, 0.01D, 0.90D, U, v);
//		
//		t.addVertexWithUV(0.10D, 0.01D, 0.90D, u, v);
//		t.addVertexWithUV(0.10D, 0.90D, 0.90D, u, V);
//		t.addVertexWithUV(0.10D, 0.90D, 1.00D, U, V);
//		t.addVertexWithUV(0.10D, 0.01D, 1.00D, U, v);
//		
//		t.addVertexWithUV(0.10D, 0.01D, 1.00D, u, v);
//		t.addVertexWithUV(0.10D, 0.90D, 1.00D, u, V);
//		t.addVertexWithUV(0.00D, 0.90D, 1.00D, U, V);
//		t.addVertexWithUV(0.00D, 0.01D, 1.00D, U, v);
//		
//		t.addVertexWithUV(0.00D, 0.01D, 1.00D, u, v);
//		t.addVertexWithUV(0.00D, 0.90D, 1.00D, u, V);
//		t.addVertexWithUV(0.00D, 0.90D, 0.90D, U, V);
//		t.addVertexWithUV(0.00D, 0.01D, 0.90D, U, v);
//		
//		// Pfosten 4
//		
//		t.addVertexWithUV(1.00D, 0.01D, 0.90D, u, v);
//		t.addVertexWithUV(1.00D, 0.01D, 1.00D, u, V);
//		t.addVertexWithUV(0.90D, 0.01D, 1.00D, U, V);
//		t.addVertexWithUV(0.90D, 0.01D, 0.90D, U, v);
//		
//		t.addVertexWithUV(1.00D, 0.90D, 0.90D, u, v);
//		t.addVertexWithUV(1.00D, 0.90D, 1.00D, u, V);
//		t.addVertexWithUV(0.90D, 0.90D, 1.00D, U, V);
//		t.addVertexWithUV(0.90D, 0.90D, 0.90D, U, v);
//		
//		t.addVertexWithUV(0.90D, 0.01D, 0.90D, u, v);
//		t.addVertexWithUV(0.90D, 0.90D, 0.90D, u, V);
//		t.addVertexWithUV(1.00D, 0.90D, 0.90D, U, V);
//		t.addVertexWithUV(1.00D, 0.01D, 0.90D, U, v);
//		
//		t.addVertexWithUV(1.00D, 0.01D, 0.90D, u, v);
//		t.addVertexWithUV(1.00D, 0.90D, 0.90D, u, V);
//		t.addVertexWithUV(1.00D, 0.90D, 1.00D, U, V);
//		t.addVertexWithUV(1.00D, 0.01D, 1.00D, U, v);
//		
//		t.addVertexWithUV(1.00D, 0.01D, 1.00D, u, v);
//		t.addVertexWithUV(1.00D, 0.90D, 1.00D, u, V);
//		t.addVertexWithUV(0.90D, 0.90D, 1.00D, U, V);
//		t.addVertexWithUV(0.90D, 0.01D, 1.00D, U, v);
//		
//		t.addVertexWithUV(0.90D, 0.01D, 1.00D, u, v);
//		t.addVertexWithUV(0.90D, 0.90D, 1.00D, u, V);
//		t.addVertexWithUV(0.90D, 0.90D, 0.90D, U, V);
//		t.addVertexWithUV(0.90D, 0.01D, 0.90D, U, v);
//		
//		if(sieve.mode == SieveMode.FILLED){
//			IIcon c2 = sieve.content.getIcon(0, 0);
//			//Content
//					float u2 = c2.getMinU();
//					float v2 = c2.getMinV();
//					float U2 = c2.getMaxU();
//					float V2 = c2.getMaxV();
//			
//			t.addVertexWithUV(0.10D, 0.90D, 0.10D, u2, v2);
//			t.addVertexWithUV(0.10D, 0.90D, 0.90D, u2, V2);
//			t.addVertexWithUV(0.90D, 0.90D, 0.90D, U2, V2);
//			t.addVertexWithUV(0.90D, 0.90D, 0.10D, U2, v2);
//		}
//		
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
