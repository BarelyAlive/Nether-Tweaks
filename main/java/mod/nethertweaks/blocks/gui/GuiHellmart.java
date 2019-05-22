package mod.nethertweaks.blocks.gui;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import mod.nethertweaks.blocks.container.ContainerHellmart;
import mod.nethertweaks.blocks.tile.TileHellmart;
import mod.nethertweaks.network.MessageHellmartBuy;
import mod.nethertweaks.network.MessageHellmartClosed;
import mod.nethertweaks.registries.registries.HellmartRegistry;
import mod.nethertweaks.registry.types.HellmartData;
import mod.sfhcore.network.NetworkHandler;
import mod.sfhcore.util.TankUtil;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.items.CapabilityItemHandler;

public class GuiHellmart extends GuiContainer {
	private static final ResourceLocation gui = new ResourceLocation("nethertweaksmod:textures/gui/guihellmart.png");

	private int itemNum;
	private final TileHellmart tileEntityMarket;

	public GuiHellmart(InventoryPlayer inventoryplayer, TileHellmart tileEntityMarket) {
		super(new ContainerHellmart(inventoryplayer, tileEntityMarket));
		this.tileEntityMarket = tileEntityMarket;
		tileEntityMarket.setBrowsingInfo(HellmartRegistry.getSize() -1);
	}

	@Override
	public void initGui() {
		super.initGui();
		Keyboard.enableRepeatEvents(false);

		buttonList.clear();

		final int posX = width / 2 - 48;
		final int posY = height / 2 - 48;

		final GuiButton left = new GuiButton(0, posX, posY - 21, 15, 20, "<");
		final GuiButton right = new GuiButton(1, posX + 16, posY - 21, 15, 20, ">");
		final GuiButton button_buy = new GuiButton(2, posX, posY + 1, 55, 20, "Buy");

		buttonList.add(left);
		buttonList.add(right);
		buttonList.add(button_buy);

		this.itemNum = tileEntityMarket.getBrowsingInfo();
	}

	@Override
	protected void actionPerformed(GuiButton guibutton) {
		if(!guibutton.enabled) {
			return;
		}
		if(guibutton.id == 0) {
			itemNum--;
			if(itemNum < 0) {
				itemNum = HellmartRegistry.getSize() - 1;
			}
			this.tileEntityMarket.setBrowsingInfo(itemNum);
		}
		if(guibutton.id == 1) {
			itemNum++;
			if(itemNum > HellmartRegistry.getSize() - 1) {
				itemNum = 0;
			}
			this.tileEntityMarket.setBrowsingInfo(itemNum);
		}
		if(guibutton.id == 2) {
			ItemStack buySlot = tileEntityMarket.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null)
					.getStackInSlot(0);
			if(buySlot != null) { 
				final HellmartData data = HellmartRegistry.getData(itemNum);
				if(buySlot.getItem() == data.getCurrency().getItem()) {
					if(buySlot.getItemDamage() == data.getCurrency().getItemDamage()) {
						int price = data.getPrice();
						if(buySlot.getCount() == price) {

							NetworkHandler.INSTANCE.sendToServer(new MessageHellmartBuy(this.itemNum,
									this.tileEntityMarket.getPos().getX(), this.tileEntityMarket.getPos().getY(),
									this.tileEntityMarket.getPos().getZ(), true));
						}
						else if(buySlot.getCount() > price && buySlot.getCount() > 1) {
							NetworkHandler.INSTANCE.sendToServer(new MessageHellmartBuy(this.itemNum,
									this.tileEntityMarket.getPos().getX(), this.tileEntityMarket.getPos().getY(),
									this.tileEntityMarket.getPos().getZ(), false));
						}
						if(buySlot.getCount() == 0 && price == 1) {
							NetworkHandler.INSTANCE.sendToServer(new MessageHellmartBuy(this.itemNum,
									this.tileEntityMarket.getPos().getX(), this.tileEntityMarket.getPos().getY(),
									this.tileEntityMarket.getPos().getZ(), true));
						}
					}
				}
			}
		}
	}

	@Override
	public void onGuiClosed() {
		NetworkHandler.INSTANCE.sendToServer(new MessageHellmartClosed(this.tileEntityMarket.getPos().getX(),
				this.tileEntityMarket.getPos().getY(), this.tileEntityMarket.getPos().getZ()));
		super.onGuiClosed();
	}

	protected void drawGuiContainerForegroundLayer(int par1, int par2) {
		this.fontRenderer.drawString("Inventory", 8, (ySize - 96) + 13, 4210752);

		GL11.glPushMatrix();
		RenderHelper.enableGUIStandardItemLighting();
		GL11.glDisable(GL11.GL_LIGHTING);
		GL11.glEnable(GL12.GL_RESCALE_NORMAL);
		GL11.glEnable(GL11.GL_COLOR_MATERIAL);
		GL11.glEnable(GL11.GL_LIGHTING);
		itemRender.zLevel = 100.0F;

		HellmartData data = HellmartRegistry.getData(itemNum);

		ItemStack item = data.getItem();
		itemRender.renderItemAndEffectIntoGUI(item, 73, 16);
		itemRender.renderItemOverlayIntoGUI(fontRenderer, item, 73, 16, "");

		ItemStack currency = data.getCurrency();
		itemRender.renderItemAndEffectIntoGUI(currency, 100, 16);
		itemRender.renderItemOverlayIntoGUI(fontRenderer, currency, 100, 16, "");
		itemRender.zLevel = 0.0F;
		GL11.glDisable(GL11.GL_LIGHTING);

		int price = data.getPrice();
		this.fontRenderer.drawString("x" + Integer.toString(price), 116, 20, 0);

		GL11.glPopMatrix();
		GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		RenderHelper.enableStandardItemLighting();
	}

	public void drawScreen(int par1, int par2, float par3) {
		drawDefaultBackground();
		super.drawScreen(par1, par2, par3);
		ItemStack item = HellmartRegistry.getData(itemNum).getItem();

		if(this.isPointInRegion(73, 16, 16, 16, par1, par2)) {
			this.renderToolTip(item, par1, par2);
		}
	}

	protected void drawGuiContainerBackgroundLayer(float f, int i, int j) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		this.mc.getTextureManager().bindTexture(gui);
		int l = (width - xSize) / 2;
		int i1 = (height - ySize) / 2;
		this.drawTexturedModalRect(l, i1 - 10, 0, 0, xSize, ySize + 21);
	}
}