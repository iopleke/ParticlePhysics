package particlephysics.tileentity.receptor;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.material.Material;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import particlephysics.utility.BasicComplexBlock;

import java.util.ArrayList;

public class SeriesReceptorBlock extends BasicComplexBlock
{

    public SeriesReceptorBlock()
    {
        super();
    }

    public SeriesReceptorBlock(Material material)
    {
        super(material);
    }

    @Override
    public void addStacksDroppedOnBlockBreak(TileEntity tileEntity, ArrayList<ItemStack> itemStacks)
    {

    }

    @Override
    public String getFront()
    {
        // TODO Auto-generated method stub
        return "SeriesReceptor";
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
        return "SeriesReceptorTop";
    }

    @Override
    public Class getTileEntityClass()
    {
        return SeriesReceptorTileEntity.class;
    }

    @Override
    public void addRecipe()
    {
        GameRegistry.addRecipe(new ItemStack(this), "III", "D  ", "III", 'I', new ItemStack(Items.iron_ingot), 'D', new ItemStack(Items.diamond));

    }

    @Override
    public String getName()
    {
        return "SeriesReceptor";
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

}
