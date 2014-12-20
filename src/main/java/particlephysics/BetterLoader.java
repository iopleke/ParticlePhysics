package particlephysics;

import cpw.mods.fml.common.registry.GameRegistry;
import java.util.ArrayList;
import net.minecraft.block.Block;
import particlephysics.block.ControlGlassBlock;
import particlephysics.block.PolarizedGlassBlock;
import particlephysics.tileentity.emitter.EmitterBlock;
import particlephysics.tileentity.infiniteemitter.InfiniteEmitterBlock;
import particlephysics.tileentity.receptor.SeriesReceptorBlock;
import particlephysics.utility.IBlock;

public class BetterLoader
{

    public static ArrayList<Block> blocks = new ArrayList<Block>();
    public static ArrayList<Class> classes = new ArrayList<Class>();

    public void populateClasses()
    {
        classes.add(EmitterBlock.class);
        classes.add(InfiniteEmitterBlock.class);
        classes.add(PolarizedGlassBlock.class);
        classes.add(SeriesReceptorBlock.class);
        classes.add(ControlGlassBlock.class);
    }

    public void loadBlocks()
    {

        populateClasses();

        try
        {
            for (int i = 0; i < classes.size(); i++)
            {

                Class currentClass = classes.get(i);
                Class clazz = currentClass;
                Block newBlock = ((Block) clazz.newInstance()).setHardness(0.5F).setStepSound(Block.soundTypeAnvil);
                if (((IBlock) newBlock).inCreativeTab())
                {
                    newBlock.setCreativeTab(ModParticlePhysics.CREATIVE_TAB);
                }
                blocks.add(newBlock);

            }
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public static Block getBlock(Class clazz)
    {

        for (int i = 0; i < blocks.size(); i++)
        {

            Block currentBlock = blocks.get(i);

            if (clazz.isInstance(currentBlock))
            {
                return currentBlock;
            }
        }

        ModParticlePhysics.LOGGER.fatal("Failed to find block in BetterLoader. Crash incoming.");
        return null;
    }

    public void mainload()
    {
        System.out.println(blocks);
        for (int i = 0; i < blocks.size(); i++)
        {

            Block currentBlock = blocks.get(i);
            if (currentBlock instanceof IBlock)
            {
                IBlock currentIBlock = (IBlock) currentBlock;
                currentBlock.setHarvestLevel("pickaxe", 0);

                if (currentIBlock.getItemBlock() != null)
                {
                    GameRegistry.registerBlock(currentBlock, currentIBlock.getItemBlock(), currentIBlock.getName());
                } else
                {
                    GameRegistry.registerBlock(currentBlock, currentIBlock.getName());
                }
                currentIBlock.addRecipe();

                GameRegistry.registerTileEntity(currentIBlock.getTileEntityClass(), currentIBlock.getName() + "Particle Physics Tile Entity");
            }

        }
    }

}
