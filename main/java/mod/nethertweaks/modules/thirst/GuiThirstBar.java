package mod.nethertweaks.modules.thirst;

import mod.nethertweaks.config.Config;
import mod.nethertweaks.proxy.ClientProxy;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiIngame;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiThirstBar {

	public static final ResourceLocation THIRST_BAR_ICONS = new ResourceLocation("nethertweaksmod:textures/gui/thirst_bar.png");

	public static void onRenderGameOverlayEvent(final RenderGameOverlayEvent event)
	{
		if(!Config.enableThirst) return;

		if (event.getType() == RenderGameOverlayEvent.ElementType.HEALTH) {
			final GuiIngame gui = Minecraft.getMinecraft().ingameGUI;
			final ScaledResolution scaledRes = event.getResolution();

			Minecraft.getMinecraft().getTextureManager().bindTexture(THIRST_BAR_ICONS);

			final EntityPlayerSP player = Minecraft.getMinecraft().player;

			if (!player.isRidingHorse() && ClientProxy.CLIENT_STATS != null) {
				final int thirstLevel = ClientProxy.CLIENT_STATS.thirstLevel;
				final int xStart = scaledRes.getScaledWidth()/2 + 10;
				final int yStart = scaledRes.getScaledHeight() - 49;

				final int poisonModifier = ClientProxy.CLIENT_STATS.poisoned ? 16 : 0;

				for (int i = 0; i < 10; i++) {
					gui.drawTexturedModalRect(xStart + i*8, yStart, 1, 1, 7, 9); //empty thirst droplet
					if (thirstLevel % 2 != 0 && 10 - i - 1 == thirstLevel/2)
						gui.drawTexturedModalRect(xStart + i*8, yStart, 17 + poisonModifier, 1, 7, 9); //half full thirst droplet
					else if (thirstLevel/2 >= 10 - i)
						gui.drawTexturedModalRect(xStart + i*8, yStart, 9 + poisonModifier, 1, 7, 9); //full thirst droplet
				}
			}

			Minecraft.getMinecraft().getTextureManager().bindTexture(Gui.ICONS);
		} else if (event.getType() == RenderGameOverlayEvent.ElementType.AIR) {
			event.setCanceled(true);

			final GuiIngame gui = Minecraft.getMinecraft().ingameGUI;
			final ScaledResolution scaledRes = event.getResolution();

			final int xStart = scaledRes.getScaledWidth() / 2 + 91;
			final int yStart = scaledRes.getScaledHeight() - 49;
			int xModifier = 0;
			int yModifier = 0;

			final int armorLevel = ForgeHooks.getTotalArmorValue(Minecraft.getMinecraft().player);

			if (!Minecraft.getMinecraft().player.isRidingHorse())
				if (armorLevel > 0)
					yModifier = -10;
				else
					xModifier = -101;

			if (Minecraft.getMinecraft().player.isInsideOfMaterial(Material.WATER)) {
				final int air = Minecraft.getMinecraft().player.getAir();
				final int full = MathHelper.ceil((air - 2) * 10.0D / 300.0D);
				final int partial = MathHelper.ceil(air * 10.0D / 300.0D) - full;

				for (int i = 0; i < full + partial; ++i)
					gui.drawTexturedModalRect(xStart - i * 8 - 9 + xModifier, yStart + yModifier, i < full ? 16 : 25, 18, 9, 9);
			}
		}
	}
}
