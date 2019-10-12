package mod.nethertweaks.items;

import org.apache.commons.lang3.StringUtils;

import mod.nethertweaks.Constants;
import mod.nethertweaks.registry.types.Ore;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.translation.I18n;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemOre extends Item {

	private boolean registerIngot;
	private boolean registerDust;
	private Ore ore;

	public boolean isRegisterIngot() {
		return registerIngot;
	}

	public boolean isRegisterDust() {
		// TODO Auto-generated method stub
		return registerDust;
	}

	public void setRegisterIngot(final boolean registerIngot) {
		this.registerIngot = registerIngot;
	}

	public void setRegisterDust(final boolean registerDust) {
		this.registerDust = registerDust;
	}

	public Ore getOre() {
		return ore;
	}

	public void setOre(final Ore ore) {
		this.ore = ore;
	}

	public ItemOre(final Ore ore) {
		super();

		this.ore = ore;
		registerIngot = ore.getResult() == null;
		setUnlocalizedName(Constants.MOD_ID + ".ore."+ore.getName());
		//this.setRegistryName("itemOre"+StringUtils.capitalize(ore.getName()));
		setCreativeTab(Constants.TABNTM);
		setHasSubtypes(true);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void getSubItems(final CreativeTabs tab, final NonNullList<ItemStack> items) {
		items.add(new ItemStack(this, 1, 0)); //Piece
		items.add(new ItemStack(this, 1, 1)); //Chunk
		items.add(new ItemStack(this, 1, 2)); //Dust
		if (registerIngot)
			items.add(new ItemStack(this, 1, 3)); //Ingot
	}

	@SideOnly(Side.CLIENT)
	public void initModel()	{

		ModelLoader.setCustomModelResourceLocation(this, 0, new ModelResourceLocation("nethertweaksmod:itemOre", "type=piece"));
		ModelLoader.setCustomModelResourceLocation(this, 1, new ModelResourceLocation("nethertweaksmod:itemOre", "type=hunk"));
		ModelLoader.setCustomModelResourceLocation(this, 2, new ModelResourceLocation("nethertweaksmod:itemOre", "type=dust"));
		if (registerIngot)
			ModelLoader.setCustomModelResourceLocation(this, 3, new ModelResourceLocation("nethertweaksmod:itemOre", "type=ingot"));
	}

	@Override
	public String getItemStackDisplayName(final ItemStack stack) {
		String name = ore.getName();
		String pre = "";
		switch (stack.getItemDamage()) {
		case 0:
			pre = "orepiece";
			break;
		case 1:
			pre = "orehunk";
			break;
		case 2:
			pre = "oredust";
			break;
		case 3:
			pre = "oreingot";
			break;
		}
		return (StringUtils.capitalize(name) + " " +I18n.translateToLocal(pre+".name")).trim();
	}

}
