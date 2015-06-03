package particlephysics.tileentity.emitter;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import particlephysics.ParticlePhysics;
import particlephysics.ParticleRegistry;
import particlephysics.utility.BasicComplexBlock;

import java.util.ArrayList;

public class EmitterBlock extends BasicComplexBlock
{

    public EmitterBlock()
    {
        super();
    }

    public EmitterBlock(Material material)
    {
        super(material);
    }

    @Override
    public String getFront()
    {
        // TODO Auto-generated method stub
        return "Emitter";
    }

    @Override
    public boolean hasModel()
    {
        return true;
    }

    @Override
    public String getTop()
    {
        // TODO Auto-generated method stub
        return "EmitterTop";
    }

    @Override
    public Class getTileEntityClass()
    {
        return EmitterTileEntity.class;
    }

    @Override
    public void addRecipe()
    {
        GameRegistry.addRecipe(new ItemStack(this), "I  ", "IID", "I  ", 'I', new ItemStack(Items.iron_ingot), 'D', new ItemStack(Items.diamond));

    }

    @Override
    public String getName()
    {
        return "Emitter";
    }

    @Override
    public boolean hasItemBlock()
    {
        return true;
    }

    @Override
    public Class getItemBlock()
    {
        return null;

    }

    @Override
    public boolean topSidedTextures()
    {
        return true;
    }

    @Override
    public void registerBlockIcons(IIconRegister icon)
    {
        super.registerBlockIcons(icon);
        // This is so hacky it makes me ashamed
        ParticleRegistry.populateIcons(icon);
    }

    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer entityPlayer, int side, float hitX, float hitY, float hitZ)
    {
        TileEntity te = world.getTileEntity(x, y, z);
        if (te != null && te instanceof EmitterTileEntity)
        {
            entityPlayer.openGui(ParticlePhysics.INSTANCE, 0, world, x, y, z);
            return true;
        }
        return false;
    }

    @Override
    public void addStacksDroppedOnBlockBreak(TileEntity tileEntity, ArrayList<ItemStack> itemStacks)
    {
        IInventory decomposer = (IInventory) tileEntity;
        for (int slot = 0; slot < decomposer.getSizeInventory(); slot++)
        {
            ItemStack itemstack = decomposer.getStackInSlot(slot);
            if (itemstack != null)
            {
                itemStacks.add(itemstack);
            }
        }
        return;
    }

}
