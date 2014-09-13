package particlephysics.block;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;
import particlephysics.api.IParticleBouncer;
import particlephysics.entity.particle.TemplateParticle;
import particlephysics.utility.BasicComplexBlock;

import java.util.ArrayList;
import java.util.List;

public class ControlGlassBlock extends BasicComplexBlock implements IParticleBouncer
{

    public ControlGlassBlock()
    {
        super();
    }

    public ControlGlassBlock(Material material)
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
        return "ControlGlass";
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
        return "ControlGlass";
    }

    @Override
    public boolean isBlockNormalCube()
    {
        return false;
    }

    @Override
    public void addCollisionBoxesToList(World par1World, int par2, int par3, int par4, AxisAlignedBB par5AxisAlignedBB, List par6List, Entity par7Entity)
    {
        if (par7Entity instanceof TemplateParticle && par1World.isBlockIndirectlyGettingPowered(par2, par3, par4))
        {
            return;
        }
        AxisAlignedBB axisalignedbb1 = this.getCollisionBoundingBoxFromPool(par1World, par2, par3, par4);

        if (axisalignedbb1 != null && par5AxisAlignedBB.intersectsWith(axisalignedbb1))
        {
            par6List.add(axisalignedbb1);
        }
    }

    @Override
    public Class getTileEntityClass()
    {
        return null;
    }

    @Override
    public void addRecipe()
    {
        GameRegistry.addRecipe(new ItemStack(this), "R R", " G ", "R R", 'R', new ItemStack(Items.redstone), 'G', new ItemStack(Blocks.glass));

    }

    @Override
    public String getName()
    {
        return "ControlGlass";
    }

    @Override
    public boolean hasItemBlock()
    {
        return false;
    }

    @Override
    public Class getItemBlock()
    {
        return null;

    }

    @Override
    public boolean topSidedTextures()
    {
        return false;
    }

    @Override
    public boolean canBounce(World world, int x, int y, int z, TemplateParticle particle)
    {
        return !(world.isBlockIndirectlyGettingPowered(x, y, z));
    }

}
