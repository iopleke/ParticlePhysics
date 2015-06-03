package particlephysics.tileentity.infiniteemitter;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import particlephysics.BetterLoader;
import particlephysics.ParticlePhysics;
import particlephysics.tileentity.emitter.EmitterBlock;
import particlephysics.utility.BasicComplexBlock;

import java.util.ArrayList;

public class InfiniteEmitterBlock extends BasicComplexBlock
{

    public InfiniteEmitterBlock()
    {
        super();
    }

    public InfiniteEmitterBlock(Material material)
    {
        super(material);
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
    }

    @Override
    public String getFront()
    {
        return "InfiniteEmitter";
    }

    @Override
    public boolean hasModel()
    {
        return true;
    }

    @Override
    public String getTop()
    {
        return "InfiniteEmitterTop";
    }

    @Override
    public Class getTileEntityClass()
    {
        return InfiniteEmitterTileEntity.class;
    }

    @Override
    public void addRecipe()
    {
        GameRegistry.addRecipe(new ItemStack(this), "XYX", "YZY", "XYX", 'X', new ItemStack(Blocks.lapis_block), 'Y', new ItemStack(Items.diamond), 'Z', new ItemStack(BetterLoader.getBlock(EmitterBlock.class)));

    }

    @Override
    public String getName()
    {
        return "InfiniteEmitter";
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
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer entityPlayer, int side, float hitX, float hitY, float hitZ)
    {

        TileEntity te = world.getTileEntity(x, y, z);
        if (te != null && te instanceof InfiniteEmitterTileEntity)
        {
            entityPlayer.openGui(ParticlePhysics.INSTANCE, 0, world, x, y, z);
            return true;
        }
        return false;
    }

}
