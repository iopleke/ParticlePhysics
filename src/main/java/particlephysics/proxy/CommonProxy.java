package particlephysics.proxy;

import cpw.mods.fml.common.FMLCommonHandler;
import net.minecraft.world.World;
import particlephysics.network.MessageHandler;

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
    }

    public World getWorld()
    {
        return FMLCommonHandler.instance().getMinecraftServerInstance().getEntityWorld();
    }
}
