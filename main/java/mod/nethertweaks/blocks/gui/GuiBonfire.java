package mod.nethertweaks.blocks.gui;

import java.io.IOException;
import java.util.*;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import mod.nethertweaks.blocks.container.ContainerBonfire;
import mod.nethertweaks.network.MessageTeleportPlayer;
import mod.nethertweaks.network.bonfire.MessageBonfireUpdate;
import mod.nethertweaks.network.bonfire.UpdateStatus;
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

	private final Map<BlockPos, BonfireInfo> bonfires;

	private final World world;
	private final EntityPlayer player;
	private final BlockPos pos;
	private int page;
	private boolean toggle;
	private GuiTextField text;

	public GuiBonfire(final BlockPos pos, final World world, final EntityPlayer player) {
		super(new ContainerBonfire());
		this.pos = pos;
		this.player = player;
		this.world = world;
		page = 0;
		toggle = true;

		bonfires = new HashMap<>();
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

		if(WorldSpawnLocation.bonfire_info.containsKey(pos))
			b = new GuiButton(0, posX + 108, posY - 42, 20, 20, WorldSpawnLocation.bonfire_info.get(pos).isPublic() ? "G" : "P");
		else
			b = new GuiButton(0, posX + 108, posY - 42, 20, 20, "G");
		buttonList.add(b);

		Set<BlockPos> bonfires = this.bonfires.keySet();
		Collection<BonfireInfo> bonfire_infos = this.bonfires.values();

		BlockPos[] bonfire_array = bonfires.toArray(new BlockPos[0]);
		BonfireInfo[] bonfire_info_array = bonfire_infos.toArray(new BonfireInfo[0]);

		int bon_i = page * 5;

		for(int i = 0; i < 5; i++)
		{
			b = new GuiButton(i + 1, posX - 30, posY - 20 + i * 21, 158, 20, bon_i < bonfire_array.length ? bonfire_info_array[bon_i].getName().isEmpty() ? "[ X: " + bonfire_array[bon_i].getX() + ", Y: " + bonfire_array[bon_i].getY() + ", Z: " + bonfire_array[bon_i].getZ() + " ]" : bonfire_info_array[bon_i].getName() : "");
			buttonList.add(b);
			if (bon_i < bonfire_array.length)
				bon_i++;
		}
		if (page > 0)
		{
			b = new GuiButton(6, posX - 30, posY + 85, 74, 20, "<---");
			buttonList.add(b);
		}
		if (page < this.bonfires.size() / 5)
		{
			b = new GuiButton(7, posX + 54, posY + 85, 74, 20, "--->");
			buttonList.add(b);
		}
		text = new GuiTextField(8, fontRenderer, posX - 30, posY - 40, 128, 14);
		text.setMaxStringLength(26);
		String name = WorldSpawnLocation.bonfire_info.get(pos).getName();
		text.setText(name);
		if (Objects.equals(name, ""))
			text.setFocused(true);
		else
			text.setFocused(false);
	}

	@Override
	protected void actionPerformed(final GuiButton button) throws IOException {
		super.actionPerformed(button);
		//if(world.isRemote) return;
		if(button.id == 0)
		{
			WorldSpawnLocation.bonfire_info.get(pos).isPublic(!WorldSpawnLocation.bonfire_info.get(pos).isPublic());
			if(world.isRemote)
				NetworkHandler.sendToServer(new MessageBonfireUpdate(UpdateStatus.UPDATE, pos, WorldSpawnLocation.bonfire_info.get(pos)));
		}
		if (button.id > 0 && button.id < 6)
		{
			int id = button.id - 1;
			id = page * 5 + id;
			if (id >= bonfires.size())
			{
				buttonList.clear();
				initGui();
				return;
			}

			final BlockPos destination = bonfires.keySet().toArray(new BlockPos[0])[id];

			int result = 0;
			if(world.isRemote)
				NetworkHandler.sendToServer(new MessageTeleportPlayer(destination, player));
		}
		if (button.id == 6)
		{
			toggle = true;
			page--;
			if (page < 0)
				page = 0;
		}
		if(button.id == 7)
			if(toggle)
			{
				toggle = false;
				page++;
				if (page > bonfires.size() / 5)
					page = bonfires.size() / 5;
			} else
				toggle = true;
		buttonList.clear();
		initGui();
	}

	@Override
	protected void keyTyped(final char typedChar, final int keyCode) throws IOException {
		text.textboxKeyTyped(typedChar, keyCode);

		if (text.isFocused())
		{
			WorldSpawnLocation.bonfire_info.get(pos).setName(text.getText());
			if(world.isRemote)
				NetworkHandler.sendToServer(new MessageBonfireUpdate(UpdateStatus.UPDATE, pos, WorldSpawnLocation.bonfire_info.get(pos)));
		}

		if (!(keyCode == Keyboard.KEY_E && text.isFocused()))
			super.keyTyped(typedChar, keyCode);
	}

	@Override
	public void drawScreen(final int mouseX, final int mouseY, final float partialTicks) {
		drawDefaultBackground();
		super.drawScreen(mouseX, mouseY, partialTicks);
	}

	@Override
	protected void mouseClicked(final int mouseX, final int mouseY, final int mouseButton) throws IOException {
		super.mouseClicked(mouseX, mouseY, mouseButton);
		text.mouseClicked(mouseX, mouseY, mouseButton);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(final float f, final int i, final int j) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		mc.getTextureManager().bindTexture(gui);
		int l = (width - xSize) / 2;
		int i1 = (height - ySize) / 2;
		this.drawTexturedModalRect(l, i1 - 10, 0, 0, xSize, ySize + 21);
		text.drawTextBox();
	}
}