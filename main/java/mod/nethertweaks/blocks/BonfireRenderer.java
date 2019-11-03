package mod.nethertweaks.blocks;

import javax.swing.Icon;

import org.lwjgl.opengl.GL11;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.init.Blocks;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BonfireRenderer{
	
//	public BlockBonfireRenderer() {
//	}
//	
//	@Override
//	public void renderInventoryBlock(Block block, int metadata, int modelId, RenderBlocks renderer) {
//	}
//
//	@Override
//	public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z,
//			Block block, int modelId, RenderBlocks renderer) {
//		if(NetherTweaksModClientProxy.renderPass == 0) {
//			drawBonfire(x, y, z);
//		}
//		return true;
//	}
//	
//	public void drawBonfire(int x, int y, int z) {
//		IIcon c = Blocks.iron_block.getIcon(0, 0);
//		IIcon c1 = Blocks.netherrack.getIcon(0, 0);
//		IIcon c2 = Blocks.planks.getIcon(0, 1);
//		float u = c.getMinU();
//		float v = c.getMinV();
//		float U = c.getMaxU();
//		float V = c.getMaxV();
//		float u1 = c1.getMinU();
//		float v1 = c1.getMinV();
//		float U1 = c1.getMaxU();
//		float V1 = c1.getMaxV();
//		float V1_2= ((c1.getMaxV() - c1.getMinV())/16) + c1.getMinV();
//		float u2 = c2.getMinU();
//		float v2 = c2.getMinV();
//		float U2 = c2.getMaxU();
//		float V2 = c2.getMaxV();
//
//		
//		Tessellator t = Tessellator.instance;
//		Tessellator t1 = Tessellator.instance;
//		Tessellator t2 = Tessellator.instance;
//		t.setColorRGBA(195, 195, 195, 255);
//		t.addTranslation(x, y, z);
//		
//		t.addVertexWithUV(0.45D, 0.00D, 0.45D, u, v);
//		t.addVertexWithUV(0.45D, 1.00D, 0.45D, u, V);
//		t.addVertexWithUV(0.55D, 1.00D, 0.45D, U, V);
//		t.addVertexWithUV(0.55D, 0.00D, 0.45D, U, v);
//		
//		t.addVertexWithUV(0.55D, 0.00D, 0.55D, U, v);
//		t.addVertexWithUV(0.55D, 1.00D, 0.55D, U, V);
//		t.addVertexWithUV(0.45D, 1.00D, 0.55D, u, V);
//		t.addVertexWithUV(0.45D, 0.00D, 0.55D, u, v);
//		
//		t.addVertexWithUV(0.55D, 0.00D, 0.45D, u, v);
//		t.addVertexWithUV(0.55D, 1.00D, 0.45D, u, V);
//		t.addVertexWithUV(0.55D, 1.00D, 0.55D, U, V);
//		t.addVertexWithUV(0.55D, 0.00D, 0.55D, U, v);
//		
//		t.addVertexWithUV(0.45D, 0.00D, 0.55D, u, v);
//		t.addVertexWithUV(0.45D, 1.00D, 0.55D, u, V);
//		t.addVertexWithUV(0.45D, 1.00D, 0.45D, U, V);
//		t.addVertexWithUV(0.45D, 0.00D, 0.45D, U, v);
//		
//		t.addVertexWithUV(0.45D, 1.00D, 0.45D, u, v);
//		t.addVertexWithUV(0.45D, 1.00D, 0.55D, u, V);
//		t.addVertexWithUV(0.55D, 1.00D, 0.55D, U, V);
//		t.addVertexWithUV(0.55D, 1.00D, 0.45D, U, v);
//		
//		/*
//		t.addVertexWithUV(0.55D, 0.00D, 0.45D, u, v);
//		t.addVertexWithUV(0.55D, 0.00D, 0.55D, u, V);
//		t.addVertexWithUV(0.45D, 0.00D, 0.55D, U, V);
//		t.addVertexWithUV(0.45D, 0.00D, 0.45D, U, v);
//		
//		*/
//		t.addVertexWithUV(0.54D, 0.71D, 0.70D, u2, v2);
//		t.addVertexWithUV(0.54D, 0.71D, 0.30D, u2, V2);
//		t.addVertexWithUV(0.54D, 0.79D, 0.30D, U2, V2);
//		t.addVertexWithUV(0.54D, 0.79D, 0.70D, U2, v2);
//		
//		t.addVertexWithUV(0.46D, 0.79D, 0.70D, U2, v2);
//		t.addVertexWithUV(0.46D, 0.79D, 0.30D, U2, V2);
//		t.addVertexWithUV(0.46D, 0.71D, 0.30D, u2, V2);
//		t.addVertexWithUV(0.46D, 0.71D, 0.70D, u2, v2);
//		
//		t.addVertexWithUV(0.54D, 0.79D, 0.70D, u2, v2);
//		t.addVertexWithUV(0.54D, 0.79D, 0.30D, u2, V2);
//		t.addVertexWithUV(0.46D, 0.79D, 0.30D, U2, V2);
//		t.addVertexWithUV(0.46D, 0.79D, 0.70D, U2, v2);
//		
//		t.addVertexWithUV(0.46D, 0.71D, 0.70D, U2, v2);
//		t.addVertexWithUV(0.46D, 0.71D, 0.30D, U2, V2);
//		t.addVertexWithUV(0.54D, 0.71D, 0.30D, u2, V2);
//		t.addVertexWithUV(0.54D, 0.71D, 0.70D, u2, v2);
//		
//		t.addVertexWithUV(0.54D, 0.79D, 0.70D, u2, v2);
//		t.addVertexWithUV(0.54D, 0.79D, 0.30D, u2, V2);
//		t.addVertexWithUV(0.46D, 0.79D, 0.30D, U2, V2);
//		t.addVertexWithUV(0.46D, 0.79D, 0.70D, U2, v2);
//		
//		t.addVertexWithUV(0.54D, 0.79D, 0.30D, u2, v2);
//		t.addVertexWithUV(0.54D, 0.71D, 0.30D, u2, V2);
//		t.addVertexWithUV(0.46D, 0.71D, 0.30D, U2, V2);
//		t.addVertexWithUV(0.46D, 0.79D, 0.30D, U2, v2);
//		
//		t.addVertexWithUV(0.46D, 0.79D, 0.70D, U2, v2);
//		t.addVertexWithUV(0.46D, 0.71D, 0.70D, U2, V2);
//		t.addVertexWithUV(0.54D, 0.71D, 0.70D, u2, V2);
//		t.addVertexWithUV(0.54D, 0.79D, 0.70D, u2, v2);
//		
//		t.addTranslation(-x, -y, -z);
//		
//		t1.addTranslation(x, y, z);
//		
//		t1.addVertexWithUV(1.00D, 0.00D, 0.00D, u1, v1);
//		t1.addVertexWithUV(1.00D, 0.00D, 1.00D, u1, V1);
//		t1.addVertexWithUV(0.00D, 0.00D, 1.00D, U1, V1);
//		t1.addVertexWithUV(0.00D, 0.00D, 0.00D, U1, v1);
//		
//		t1.addVertexWithUV(0.00D, 0.06D, 0.00D, u1, v1);
//		t1.addVertexWithUV(0.00D, 0.06D, 1.00D, u1, V1);
//		t1.addVertexWithUV(1.00D, 0.06D, 1.00D, U1, V1);
//		t1.addVertexWithUV(1.00D, 0.06D, 0.00D, U1, v1);
//		
//		t1.addVertexWithUV(0.00D, 0.00D, 0.00D, u1, v1);
//		t1.addVertexWithUV(0.00D, 0.06D, 0.00D, u1, V1_2);
//		t1.addVertexWithUV(1.00D, 0.06D, 0.00D, U1, V1_2);
//		t1.addVertexWithUV(1.00D, 0.00D, 0.00D, U1, v1);
//		
//		t1.addVertexWithUV(1.00D, 0.00D, 0.00D, u1, v1);
//		t1.addVertexWithUV(1.00D, 0.06D, 0.00D, u1, V1_2);
//		t1.addVertexWithUV(1.00D, 0.06D, 1.00D, U1, V1_2);
//		t1.addVertexWithUV(1.00D, 0.00D, 1.00D, U1, v1);
//		
//		t1.addVertexWithUV(1.00D, 0.00D, 1.00D, u1, v1);
//		t1.addVertexWithUV(1.00D, 0.06D, 1.00D, u1, V1_2);
//		t1.addVertexWithUV(0.00D, 0.06D, 1.00D, U1, V1_2);
//		t1.addVertexWithUV(0.00D, 0.00D, 1.00D, U1, v1);
//		
//		t1.addVertexWithUV(0.00D, 0.00D, 1.00D, u1, v1);
//		t1.addVertexWithUV(0.00D, 0.06D, 1.00D, u1, V1_2);
//		t1.addVertexWithUV(0.00D, 0.06D, 0.00D, U1, V1_2);
//		t1.addVertexWithUV(0.00D, 0.00D, 0.00D, U1, v1);
//		
//		t1.addTranslation(-x, -y, -z);
//		
//		t2.addTranslation(x, y, z);
//		/*
//		t1.addVertexWithUV(0.00D, 0.00D, 1.00D, u2, v2);
//		t1.addVertexWithUV(0.00D, 0.06D, 1.00D, u2, V2);
//		t1.addVertexWithUV(0.00D, 0.06D, 0.00D, U2, V2);
//		t1.addVertexWithUV(0.00D, 0.00D, 0.00D, U2, v2);
//		*/
//		t2.addVertexWithUV(0.44D, 0.70D, 0.44D, U2, v2);
//		t2.addVertexWithUV(0.44D, 1.01D, 0.44D, U2, V2);
//		t2.addVertexWithUV(0.56D, 1.01D, 0.44D, u2, V2);
//		t2.addVertexWithUV(0.56D, 0.70D, 0.44D, u2, v2);
//		
//		t2.addVertexWithUV(0.56D, 0.70D, 0.44D, U2, v2);
//		t2.addVertexWithUV(0.56D, 1.01D, 0.44D, U2, V2);
//		t2.addVertexWithUV(0.56D, 1.01D, 0.56D, u2, V2);
//		t2.addVertexWithUV(0.56D, 0.70D, 0.56D, u2, v2);
//		
//		t2.addVertexWithUV(0.56D, 0.70D, 0.56D, U2, v2);
//		t2.addVertexWithUV(0.56D, 1.01D, 0.56D, U2, V2);
//		t2.addVertexWithUV(0.44D, 1.01D, 0.56D, u2, V2);
//		t2.addVertexWithUV(0.44D, 0.70D, 0.56D, u2, v2);
//		
//		t2.addVertexWithUV(0.44D, 0.70D, 0.56D, U2, v2);
//		t2.addVertexWithUV(0.44D, 1.01D, 0.56D, U2, V2);
//		t2.addVertexWithUV(0.44D, 1.01D, 0.44D, u2, V2);
//		t2.addVertexWithUV(0.44D, 0.70D, 0.44D, u2, v2);
//		
//		t2.addVertexWithUV(0.44D, 1.01D, 0.56D, U2, v2);
//		t2.addVertexWithUV(0.56D, 1.01D, 0.56D, U2, V2);
//		t2.addVertexWithUV(0.56D, 1.01D, 0.44D, u2, V2);
//		t2.addVertexWithUV(0.44D, 1.01D, 0.44D, u2, v2);
//		
//		t2.addVertexWithUV(0.44D, 0.70D, 0.44D, U2, v2);
//		t2.addVertexWithUV(0.56D, 0.70D, 0.44D, U2, V2);
//		t2.addVertexWithUV(0.56D, 0.70D, 0.56D, u2, V2);
//		t2.addVertexWithUV(0.44D, 0.70D, 0.56D, u2, v2);
//		
//		t2.addTranslation(-x, -y, -z);
//		
//	}
//	
//	@Override
//	public boolean shouldRender3DInInventory(int modelId) {
//		return false;
//	}
//	
//	@Override
//	public int getRenderId() {
//		return NetherTweaksModClientProxy.bonfireRendererId;
//	}
	
}
