package particlephysics.network;

import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import cpw.mods.fml.relauncher.Side;
import particlephysics.ParticlePhysics;
import particlephysics.network.message.MessageEmitterUpdate;

public class MessageHandler implements IMessageHandler
{
    public static final SimpleNetworkWrapper INSATNCE = NetworkRegistry.INSTANCE.newSimpleChannel(ParticlePhysics.ID);

    public static void init()
    {
        INSATNCE.registerMessage(MessageEmitterUpdate.class, MessageEmitterUpdate.class, 0, Side.SERVER);
    }

    @Override
    public IMessage onMessage(IMessage message, MessageContext ctx)
    {
        return null;
    }
}
