package particlephysics.network.message;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import net.minecraft.inventory.Container;
import particlephysics.tileentity.emitter.EmitterContainer;

public class MessageEmitterUpdate implements IMessage, IMessageHandler<MessageEmitterUpdate, IMessage>
{
    private int type;
    private int value;

    public MessageEmitterUpdate()
    {
        this.type = 0;
        this.value = 0;
    }

    public MessageEmitterUpdate(int type, int value)
    {
        this.type = type;
        this.value = value;
    }
    @Override
    public void fromBytes(ByteBuf buf)
    {
        this.type = buf.readInt();
        this.value = buf.readInt();
    }

    @Override
    public void toBytes(ByteBuf buf)
    {
        buf.writeInt(this.type);
        buf.writeInt(this.value);
    }

    @Override
    public IMessage onMessage(MessageEmitterUpdate message, MessageContext ctx)
    {
        //Container container = FMLClientHandler.instance().getClient().thePlayer.openContainer;
        Container container = ctx.getServerHandler().playerEntity.openContainer;
        if (container instanceof EmitterContainer)
        {
            ((EmitterContainer) container).getMachine().receiveButton(message.type, message.value);
        }
        return null;
    }
}
