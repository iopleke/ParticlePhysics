package particlephysics.tileentity.infiniteemitter;

import java.util.ArrayList;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import particlephysics.BetterLoader;
import particlephysics.ModParticlePhysics;
import particlephysics.Settings;
import particlephysics.tileentity.emitter.EmitterTileEntity;
import particlephysics.utility.BasicComplexBlock;
import cpw.mods.fml.common.registry.GameRegistry;
import particlephysics.tileentity.emitter.EmitterBlock;

public class InfiniteEmitterBlock extends BasicComplexBlock
{

    public InfiniteEmitterBlock()
    {
        super(Settings.InfiniteEmitter);
    }

    public InfiniteEmitterBlock(int i)
    {
        super(i);
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
        return EmitterTileEntity.class;
    }

    @Override
    public void addRecipe()
    {
        GameRegistry.addRecipe(new ItemStack(this), "XYX", "YZY", "XYX", 'X', new ItemStack(Block.blockLapis), 'Y', new ItemStack(Item.diamond), 'Z', new ItemStack(BetterLoader.getBlock(EmitterBlock.class)));

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

        TileEntity te = world.getBlockTileEntity(x, y, z);
        if (te != null && te instanceof EmitterTileEntity)
        {
            entityPlayer.openGui(ModParticlePhysics.INSTANCE, 0, world, x, y, z);
            return true;
        }
        return false;
    }

}
