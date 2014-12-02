package particlephysics.proxy;

import cpw.mods.fml.common.FMLCommonHandler;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import particlephysics.network.MessageHandler;
import particlephysics.tileentity.receptor.SeriesReceptorInteractionHandler;

public class CommonProxy
{

    public void registerRenderers()
    {
    }

    public void init()
    {

    }

    public void registerHandlers()
    {
        MessageHandler.init();
        MinecraftForge.EVENT_BUS.register(new SeriesReceptorInteractionHandler());
    }

    public World getWorld()
    {
        return FMLCommonHandler.instance().getMinecraftServerInstance().getEntityWorld();
    }
}
