package particlephysics.proxy;

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
}
