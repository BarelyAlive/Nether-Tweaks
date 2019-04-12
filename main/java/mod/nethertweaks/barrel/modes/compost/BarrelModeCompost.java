package mod.nethertweaks.barrel.modes.compost;

import java.util.List;

import mod.nethertweaks.Config;
import mod.nethertweaks.barrel.IBarrelMode;
import mod.nethertweaks.blocks.tile.TileBarrel;
import mod.nethertweaks.network.MessageBarrelModeUpdate;
import mod.nethertweaks.network.MessageCompostUpdate;
import mod.nethertweaks.network.NetworkHandlerNTM;
import mod.nethertweaks.registry.CompostRegistry;
import mod.nethertweaks.registry.types.Compostable;
import mod.sfhcore.texturing.Color;
import mod.sfhcore.util.ItemInfo;
import mod.sfhcore.util.Util;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.ItemStackHandler;

public class BarrelModeCompost implements IBarrelMode {

	private float fillAmount = 0;
	private Color color = new Color("EEA96D");
	
	public void setColor(Color color) {
		this.color = color;
	}

	private Color whiteColor = new Color(1f, 1f, 1f, 1f);
	private Color originalColor;
	private float progress = 0;
	private IBlockState compostState;
	
	public float getFillAmount() {
		return fillAmount;
	}

	public void setFillAmount(float fillAmount) {
		this.fillAmount = fillAmount;
	}

	public float getProgress() {
		return progress;
	}

	public void setProgress(float progress) {
		this.progress = progress;
	}

	public Color getColor() {
		return color;
	}

	public Color getWhiteColor() {
		return whiteColor;
	}

	public Color getOriginalColor() {
		return originalColor;
	}

	public IBlockState getCompostState() {
		return compostState;
	}

	private BarrelItemHandlerCompost handler;
	
	public BarrelModeCompost()
	{
		handler = new BarrelItemHandlerCompost(null);
	}
	
	@SuppressWarnings("deprecation")
	@Override
	public boolean onBlockActivated(World world, TileBarrel barrel, BlockPos pos, IBlockState state, EntityPlayer player, EnumFacing side, float hitX, float hitY, float hitZ)
	{
		if (fillAmount == 0)
		{
			if (player.getHeldItem(EnumHand.MAIN_HAND) != null)
			{
				ItemInfo info = ItemInfo.getItemInfoFromStack(player.getHeldItem(EnumHand.MAIN_HAND));
				if (CompostRegistry.containsItem(info))
				{
					Compostable comp = CompostRegistry.getItem(info);
					compostState = Block.getBlockFromItem(comp.getCompostBlock().getItem())
							.getStateFromMeta(comp.getCompostBlock().getMeta());
					NetworkHandlerNTM.sendNBTUpdate(barrel);
				}
			}
		}
		if (fillAmount < 1 && compostState != null)
		{
			if (player.getHeldItem(EnumHand.MAIN_HAND) != null)
			{
				ItemInfo info = ItemInfo.getItemInfoFromStack(player.getHeldItem(EnumHand.MAIN_HAND));
				Compostable comp = CompostRegistry.getItem(info);
				IBlockState testState = Block.getBlockFromItem(comp.getCompostBlock().getItem())
						.getStateFromMeta(comp.getCompostBlock().getMeta());
				
				if (CompostRegistry.containsItem(info) && compostState.equals(testState))
				{
					Compostable compost = CompostRegistry.getItem(info);
					
					if (fillAmount == 0)
						color = compost.getColor();
					else
						color = Color.average(color, compost.getColor(), compost.getValue());
					
					fillAmount += compost.getValue();
					if (fillAmount > 1)
						fillAmount = 1;
					if (!player.capabilities.isCreativeMode)
						player.getHeldItem(EnumHand.MAIN_HAND).setCount(player.getHeldItem(EnumHand.MAIN_HAND).getCount() -1);
					NetworkHandlerNTM.sendToAllAround(new MessageCompostUpdate(this.fillAmount, this.color, this.progress, barrel.getPos()), barrel);
					barrel.markDirty();
					return true;
				}
			}
		}
		else if (progress >= 1)
		{
			Util.dropItemInWorld(barrel, player, new ItemStack(compostState.getBlock(), 1, compostState.getBlock().getMetaFromState(compostState)), 0.02f);
			removeItem(barrel);		
			return true;
		}
		
		return false;
	}
	
	public void removeItem(TileBarrel barrel)
	{
		progress = 0;
		fillAmount = 0;
		color = new Color("EEA96D");
		handler.setStackInSlot(0, null);
		compostState = null;
		NetworkHandlerNTM.sendToAllAround(new MessageCompostUpdate(this.fillAmount, this.color, this.progress, barrel.getPos()), barrel);
		barrel.setMode("null");
		IBlockState state = barrel.getWorld().getBlockState(barrel.getPos());
		NetworkHandlerNTM.sendToAllAround(new MessageBarrelModeUpdate("null", barrel.getPos()), barrel);
		barrel.getWorld().setBlockState(barrel.getPos(), state);
	}
	
	@SuppressWarnings("deprecation")
	public boolean addItem(ItemStack stack, TileBarrel barrel)
	{
		if (fillAmount < 1)
		{
			if (stack != null)
			{
				ItemInfo info = ItemInfo.getItemInfoFromStack(stack);
				Compostable comp = CompostRegistry.getItem(info);
				IBlockState testState = Block.getBlockFromItem(comp.getCompostBlock().getItem())
						.getStateFromMeta(comp.getCompostBlock().getMeta());
				
				if (CompostRegistry.containsItem(info) && compostState == null)
				{
					compostState = testState;
				}
				
				if (CompostRegistry.containsItem(info) && compostState.equals(testState))
				{
					Compostable compost = CompostRegistry.getItem(info);
					
					if (fillAmount == 0)
						color = compost.getColor();
					else
						color = Color.average(color, compost.getColor(), compost.getValue());
					
					fillAmount += compost.getValue();
					if (fillAmount > 1)
						fillAmount = 1;
					NetworkHandlerNTM.sendToAllAround(new MessageCompostUpdate(this.fillAmount, this.color, this.progress, barrel.getPos()), barrel);
					barrel.markDirty();
					return true;
				}
			}
		}
		return false;
	}
    
    @Override
    public void update(TileBarrel barrel)
    {
        if (fillAmount >= 1 && progress < 1)
        {
            if (progress == 0)
            {
                originalColor = color;
            }
            
            progress += 1.0 / Config.compostingTicks;
            
            color = Color.average(originalColor, whiteColor, progress);
            
            NetworkHandlerNTM.sendToAllAround(new MessageCompostUpdate(this.fillAmount, this.color, this.progress, barrel.getPos()), barrel);
            
            barrel.markDirty();
        }
        
        if (progress >= 1 && compostState != null)
        {
            barrel.setMode("block");
            NetworkHandlerNTM.sendToAllAround(new MessageBarrelModeUpdate("block", barrel.getPos()), barrel);
            
            barrel.getMode().addItem(new ItemStack(compostState.getBlock(), 1, compostState.getBlock().getMetaFromState(compostState)), barrel);
        }
    }
    
	@Override
	public String getName()
	{
		return "compost";
	}
	
	@Override
	public List<String> getWailaTooltip(TileBarrel barrel, List<String> currenttip) {
		if (compostState != null)
			currenttip.add("Composting "+compostState.getBlock().getLocalizedName());
		if (progress == 0) {
			currenttip.add(Math.round(fillAmount*100)+"% full");
		}
		else {
			currenttip.add(Math.round(progress*100)+"% complete");
		}
		return currenttip;
	}

	@Override
	public boolean isTriggerItemStack(ItemStack stack)
	{
		return CompostRegistry.containsItem(stack);
	}
	
	@Override
	public boolean isTriggerFluidStack(FluidStack stack)
	{
		return false;
	}

	@Override
	public void writeToNBT(NBTTagCompound tag) 
	{
		tag.setFloat("fillAmount", fillAmount);
		tag.setInteger("color", color.toInt());
		if (originalColor != null)
			tag.setInteger("originalColor", originalColor.toInt());
		tag.setFloat("progress", progress);
		if (compostState != null)
		{
			tag.setString("block", Block.REGISTRY.getNameForObject(compostState.getBlock()).toString());
			tag.setInteger("meta", compostState.getBlock().getMetaFromState(compostState));
		}

	}

	@SuppressWarnings("deprecation")
	@Override
	public void readFromNBT(NBTTagCompound tag) 
	{
		fillAmount = tag.getFloat("fillAmount");
		this.color = new Color(tag.getInteger("color"));
		if (tag.hasKey("originalColor"))
			this.originalColor = new Color(tag.getInteger("originalColor"));
		this.progress = tag.getFloat("progress");
		if (tag.hasKey("block"))
		{
			Block block = Block.REGISTRY.getObject(new ResourceLocation(tag.getString("block")));
			compostState = block.getStateFromMeta(tag.getInteger("meta"));
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public TextureAtlasSprite getTextureForRender(TileBarrel barrel)
	{
		if (compostState == null)
			return Minecraft.getMinecraft().getBlockRendererDispatcher().getBlockModelShapes()
					.getTexture(Blocks.DIRT.getDefaultState());
		return Minecraft.getMinecraft().getBlockRendererDispatcher().getBlockModelShapes()
		.getTexture(compostState);
	}
	
	@Override
	public float getFilledLevelForRender(TileBarrel barrel)
	{
		return fillAmount;
	}
	
	@Override
	public Color getColorForRender()
	{
		return color;
	}

	@Override
	public ItemStackHandler getHandler(TileBarrel barrel) 
	{
		handler.setBarrel(barrel);
		return handler;
	}

	@Override
	public FluidTank getFluidHandler(TileBarrel barrel) {
		return null;
	}
	
	@Override
	public boolean canFillWithFluid(TileBarrel barrel) {
		return false;
	}
	
}
