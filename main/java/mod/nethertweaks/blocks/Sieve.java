package mod.nethertweaks.blocks;

import java.util.ArrayList;
import java.util.Map;

import mod.nethertweaks.Config;
import mod.nethertweaks.NetherTweaksMod;
import mod.nethertweaks.blocks.tile.TileSieve;
import mod.nethertweaks.interfaces.INames;
import mod.nethertweaks.items.ItemMesh;
import mod.nethertweaks.network.NetworkHandlerNTM;
import mod.sfhcore.util.Util;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.resources.I18n;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class Sieve extends Block implements ITileEntityProvider{

	public enum MeshType implements IStringSerializable {
		NONE(0, "none"), STRING(1, "string"), FLINT(2, "flint"), IRON(3, "iron"), DIAMOND(4, "diamond");

		private int id;
		private String name;
		
		private MeshType(int id, String name) {
			this.id = id;
			this.name = name;
		}

		@Override
		public String getName() {
			return name;
		}

		public int getID() {
			return id;
		}

		@Override
		public String toString() {
			return getName();
		}

		public static MeshType getMeshTypeByID(int meta) {
			switch (meta) {
			case 1:
				return STRING;
			case 2:
				return FLINT;
			case 3:
				return IRON;
			case 4:
				return DIAMOND;
			}

			return NONE;
		}
	}

	public static final PropertyEnum<MeshType> MESH = PropertyEnum.create("mesh", MeshType.class);
	
	public Sieve()
	{
		super(Material.ROCK);
		this.setDefaultState(this.blockState.getBaseState().withProperty(MESH, MeshType.NONE));
		setUnlocalizedName(INames.SIEVE);
		setRegistryName("nethertweaksmod", INames.SIEVE);
		setResistance(15.0f);
		setHardness(2.0f);
		this.setCreativeTab(NetherTweaksMod.tabNTM);
	}

	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn,
			EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		if (worldIn.isRemote)
			return true;
		
		// I think this should work. Let's just go with it.
		if(playerIn instanceof FakePlayer && !Config.fakePlayersCanSieve)
		    return false;
		ItemStack heldItem = playerIn.getHeldItem(hand);
		
		TileSieve te = (TileSieve) worldIn.getTileEntity(pos);
		if (te != null) {
			if (heldItem != null && heldItem.getItem() instanceof ItemMesh) {
				//Adding a mesh.
				ItemStack meshStack = heldItem.copy(); meshStack.setCount(1);;
				MeshType type = MeshType.getMeshTypeByID(heldItem.getItemDamage());
				boolean done = te.setMesh(meshStack, false);
				
				if (done) {
				    if(!playerIn.isCreative())
				        heldItem.setCount(-1);
				    
					worldIn.setBlockState(pos, state.withProperty(MESH, type));
					NetworkHandlerNTM.sendNBTUpdate(te);			
					return true;
				}
			}
			if (playerIn.getHeldItemMainhand() == null && te.getMeshStack() != null && playerIn.isSneaking() && te.setMesh(null, true)) {
				//Removing a mesh.
					Util.dropItemInWorld(te, playerIn, te.getMeshStack(), 0.02f);
					te.setMesh(null);
					NetworkHandlerNTM.sendNBTUpdate(te);
					worldIn.setBlockState(pos, state.withProperty(MESH, MeshType.NONE));
					
					return true;
				}
				
				if (te.addBlock(heldItem)) {
				    if(!playerIn.isCreative())
	                    heldItem.setCount(-1);
	                
					for (int xOffset = -1*Config.sieveSimilarRadius ; xOffset <= 1*Config.sieveSimilarRadius ; xOffset++) {
						for (int zOffset = -1*Config.sieveSimilarRadius ; zOffset <= 1*Config.sieveSimilarRadius ; zOffset++) {
							TileEntity entity = worldIn.getTileEntity(pos.add(xOffset, 0, zOffset));
							if (entity != null && entity instanceof TileSieve) {
								TileSieve sieve = (TileSieve) entity;
								if (heldItem.getCount() > 0 && te.isSieveSimilarToInput(sieve)) 
	                                if (sieve.addBlock(heldItem) && !playerIn.isCreative())
	                                    heldItem.setCount(-1);
							}
						}
					}
					return true;
				}
				
				ArrayList<BlockPos> toSift = new ArrayList<BlockPos>();
				for (int xOffset = -1*Config.sieveSimilarRadius ; xOffset <= Config.sieveSimilarRadius ; xOffset++) {
					for (int zOffset = -1*Config.sieveSimilarRadius ; zOffset <= 1*Config.sieveSimilarRadius ; zOffset++) {
						TileEntity entity = worldIn.getTileEntity(pos.add(xOffset, 0, zOffset));
						if (entity != null && entity instanceof TileSieve) {
							TileSieve sieve = (TileSieve) entity;
							if (te.isSieveSimilar(sieve))
								toSift.add(pos.add(xOffset, 0, zOffset));
						}
					}
				}
				for (BlockPos posIter : toSift) {
					if (posIter != null) {
						TileSieve sieve = (TileSieve) worldIn.getTileEntity(posIter);
						sieve.doSieving(playerIn);
					}
				}
				return true;
			}

			return true;
		}

		@Override
		protected BlockStateContainer createBlockState() {
			return new BlockStateContainer(this, new IProperty[] {MESH});
		}

		@Override
		public IBlockState getStateFromMeta(int meta) {
			MeshType type;
			switch (meta) {
			case 0:
				type = MeshType.NONE;
				break;
			case 1:
				type = MeshType.STRING;
				break;
			case 2:
				type = MeshType.FLINT;
				break;
			case 3:
				type = MeshType.IRON;
				break;
			case 4:
				type = MeshType.DIAMOND;
				break;
			default:
				type = MeshType.STRING;
				break;
			}
			return getDefaultState().withProperty(MESH, type);
		}

		@Override
		public int getMetaFromState(IBlockState state) {
			MeshType type = (MeshType) state.getValue(MESH);
			return type.getID();
		}
		
		@Override
		public void breakBlock(World world, BlockPos pos, IBlockState state) {
			TileEntity te = world.getTileEntity(pos);
			if (te != null) {
				TileSieve sieve = (TileSieve) te;
				if (sieve.getMeshStack() != null)
					Util.dropItemInWorld(sieve, null, sieve.getMeshStack(), 0.02f);
			}
			
			super.breakBlock(world, pos, state);
		}

		@Override
		public TileEntity createNewTileEntity(World worldIn, int meta) {
			return new TileSieve();
		}

		@Override
		public boolean isFullBlock(IBlockState state)
		{
			return false;
		}

		@Override
		public boolean isOpaqueCube(IBlockState state)
		{
			return false;
		}

		@Override
		public boolean isFullCube(IBlockState state)
		{
			return false;
		}

	}
