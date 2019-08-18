package mod.nethertweaks.blocks.gui;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import mod.nethertweaks.blocks.container.ContainerBonfire;
import mod.nethertweaks.network.MessageTeleportPlayer;
import mod.nethertweaks.network.bonfire.UpdateStatus;
import mod.nethertweaks.network.bonfire.MessageBonfireUpdate;
import mod.nethertweaks.world.BonfireInfo;
import mod.nethertweaks.world.WorldSpawnLocation;
import mod.sfhcore.network.NetworkHandler;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiBonfire extends GuiContainer {
	private static final ResourceLocation gui = new ResourceLocation("nethertweaksmod:textures/gui/guibonfire.png");

	private Map<BlockPos, BonfireInfo> bonfires;
	
	private World world;
	private EntityPlayer player;
	private BlockPos pos;
	private int page;
	private boolean toggle;
	private GuiTextField text;
	
	public GuiBonfire(BlockPos pos, World world, EntityPlayer player) {
		super(new ContainerBonfire());
		this.pos = pos;
		this.player = player;
		this.world = world;
		this.page = 0;
		this.toggle = true;
		
		this.bonfires = new HashMap<BlockPos, BonfireInfo>();
		for(Map.Entry<BlockPos, BonfireInfo> entry : WorldSpawnLocation.bonfire_info.entrySet())
		{
			if((entry.getValue().isPublic() || (!entry.getValue().isPublic() && (entry.getValue().getOwner().getLeastSignificantBits() == player.getUUID(player.getGameProfile()).getLeastSignificantBits() && entry.getValue().getOwner().getMostSignificantBits() == player.getUUID(player.getGameProfile()).getMostSignificantBits()))) && !entry.getKey().equals(this.pos))
			{
				this.bonfires.put(entry.getKey(), entry.getValue());
			}
		}
	}

	@Override
	public void initGui() {
		super.initGui();
		Keyboard.enableRepeatEvents(false);

		buttonList.clear();
		
		final int posX = width / 2 - 48;
		final int posY = height / 2 - 48;
		
		GuiButton b;
		
		if(WorldSpawnLocation.bonfire_info.containsKey(this.pos))
		{
			b = new GuiButton(0, posX + 108, posY - 42, 20, 20, WorldSpawnLocation.bonfire_info.get(this.pos).isPublic() ? "G" : "P");
		}
		else
		{
			b = new GuiButton(0, posX + 108, posY - 42, 20, 20, "G");
		}
		buttonList.add(b);
		
		Set<BlockPos> bonfires = this.bonfires.keySet();
		Collection<BonfireInfo> bonfire_infos = this.bonfires.values();
		
		BlockPos[] bonfire_array = bonfires.toArray(new BlockPos[0]);
		BonfireInfo[] bonfire_info_array = bonfire_infos.toArray(new BonfireInfo[0]);
		
		int bon_i = page * 5;
		
		for(int i = 0; i < 5; i++)
		{
			b = new GuiButton((i + 1), posX - 30, posY - 20 + (i * 21), 158, 20, (bon_i < bonfire_array.length ? (bonfire_info_array[bon_i].getName().isEmpty() ? "[ X: " + bonfire_array[bon_i].getX() + ", Y: " + bonfire_array[bon_i].getY() + ", Z: " + bonfire_array[bon_i].getZ() + " ]" : bonfire_info_array[bon_i].getName() ) : ""));
			buttonList.add(b);
			if (bon_i < bonfire_array.length)
			{
				bon_i++;
			}
		}
		if (page > 0)
		{
			b = new GuiButton(6, posX - 30, posY + 85, 74, 20, "<---");
			buttonList.add(b);
		}
		if (page < (this.bonfires.size() / 5))
		{
			b = new GuiButton(7, posX + 54, posY + 85, 74, 20, "--->");
			buttonList.add(b);
		}
		this.text = new GuiTextField(8, this.fontRenderer, posX - 30, posY - 40, 128, 14);
		this.text.setMaxStringLength(26);
		String name = WorldSpawnLocation.bonfire_info.get(this.pos).getName();
		this.text.setText(name == "" ? "" : name);
		if (name == "")
		{
			this.text.setFocused(true);
		}
		else
		{
			this.text.setFocused(false);
		}
	}
	
	@Override
	protected void actionPerformed(GuiButton button) throws IOException {
		super.actionPerformed(button);
		if(!this.world.isRemote) return;
		if(button.id == 0)
		{
			WorldSpawnLocation.bonfire_info.get(this.pos).isPublic(!WorldSpawnLocation.bonfire_info.get(this.pos).isPublic());
			NetworkHandler.sendToServer(new MessageBonfireUpdate(UpdateStatus.UPDATE, this.pos, WorldSpawnLocation.bonfire_info.get(this.pos)));
		}
		if (button.id > 0 && button.id < 6)
		{
			int id = button.id - 1;
			id = (this.page * 5) + id;
			if (id >= this.bonfires.size())
			{
				this.buttonList.clear();
				this.initGui();
				return;
			}
			
			final BlockPos destination = this.bonfires.keySet().toArray(new BlockPos[0])[id];
			
			int result = 0;
			NetworkHandler.sendToServer(new MessageTeleportPlayer(destination, player));
		}
		if (button.id == 6)
		{
			this.toggle = true;
			this.page--;
			if (this.page < 0)
			{
				this.page = 0;
			}
		}
		if(button.id == 7)
		{
			if(toggle == true)
			{
				this.toggle = false;
				this.page++;
				if (this.page > (this.bonfires.size() / 5))
				{
					this.page = (this.bonfires.size() / 5);
				}
			}
			else
			{
				this.toggle = true;
			}
		}
		this.buttonList.clear();
		this.initGui();
	}

	@Override
	protected void keyTyped(char typedChar, int keyCode) throws IOException {
		this.text.textboxKeyTyped(typedChar, keyCode);
		
		if (this.text.isFocused())
		{
			WorldSpawnLocation.bonfire_info.get(this.pos).setName(this.text.getText());
			NetworkHandler.sendToServer(new MessageBonfireUpdate(UpdateStatus.UPDATE, this.pos, WorldSpawnLocation.bonfire_info.get(this.pos)));
		}
		
		if (!(keyCode == Keyboard.KEY_E && this.text.isFocused()))
		{
			super.keyTyped(typedChar, keyCode);
		}
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		this.drawDefaultBackground();
		super.drawScreen(mouseX, mouseY, partialTicks);
	}
	
	@Override
	protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
		super.mouseClicked(mouseX, mouseY, mouseButton);
		this.text.mouseClicked(mouseX, mouseY, mouseButton);
	}
	
	@Override
	protected void drawGuiContainerBackgroundLayer(float f, int i, int j) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		this.mc.getTextureManager().bindTexture(gui);
		int l = (width - xSize) / 2;
		int i1 = (height - ySize) / 2;
		this.drawTexturedModalRect(l, i1 - 10, 0, 0, xSize, ySize + 21);
		this.text.drawTextBox();
	}
}