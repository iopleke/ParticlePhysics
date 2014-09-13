package particlephysics.network.message;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;
import particlephysics.entity.particle.TemplateParticle;

public class MessageParticleUpdate implements IMessage
{
    private Entity entity;
    private int effect;

    public MessageParticleUpdate() {
        this.entity = null;
        this.effect = 0;
    }

    public MessageParticleUpdate(Entity entity, int effect)
    {
        this.entity = entity;
        this.effect = effect;
    }

    @Override
    public void fromBytes(ByteBuf buf)
    {
        World world = Minecraft.getMinecraft().theWorld;
        this.entity = world.getEntityByID(buf.readInt());
        this.effect = buf.readInt();
    }

    @Override
    public void toBytes(ByteBuf buf)
    {
        buf.writeInt(this.entity.getEntityId());
        buf.writeInt(this.effect);
    }

    public class Handler implements IMessageHandler<MessageParticleUpdate, IMessage>
    {

        @Override
        public IMessage onMessage(MessageParticleUpdate message, MessageContext ctx)
        {
            if (entity != null)
            {
                entity.setPosition(message.entity.posX, message.entity.posY, message.entity.posZ);
                entity.setVelocity(message.entity.motionX, message.entity.motionY, message.entity.motionZ);
                if (entity instanceof TemplateParticle)
                {
                    ((TemplateParticle) entity).effect = message.effect;
                }
            }
            return null;
        }
    }
}
