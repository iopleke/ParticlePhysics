package particlephysics.network.message;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;
import particlephysics.ModParticlePhysics;
import particlephysics.entity.particle.TemplateParticle;

public class MessageParticleUpdate implements IMessage, IMessageHandler<MessageParticleUpdate, IMessage>
{
    private int entityId;
    private int effect;

    private double posX, posY, posZ;
    private double motionX, motionY, motionZ;

    public MessageParticleUpdate() {
        this.entityId = -1;
        this.effect = 0;
    }

    public MessageParticleUpdate(Entity entity, int effect)
    {
        this.entityId = entity.getEntityId();
        this.effect = effect;
        this.posX = entity.posX;
        this.posY = entity.posY;
        this.posZ = entity.posZ;
        this.motionX = entity.motionX;
        this.motionY = entity.motionY;
        this.motionZ = entity.motionZ;
    }

    @Override
    public void fromBytes(ByteBuf buf)
    {
        this.entityId = buf.readInt();
        this.effect = buf.readInt();
        this.posX = buf.readDouble();
        this.posY = buf.readDouble();
        this.posZ = buf.readDouble();
        this.motionX = buf.readDouble();
        this.motionY = buf.readDouble();
        this.motionZ = buf.readDouble();
    }

    @Override
    public void toBytes(ByteBuf buf)
    {
        buf.writeInt(this.entityId);
        buf.writeInt(this.effect);
        buf.writeDouble(this.posX);
        buf.writeDouble(this.posX);
        buf.writeDouble(this.posY);
        buf.writeDouble(this.posZ);
        buf.writeDouble(this.motionX);
        buf.writeDouble(this.motionY);
        buf.writeDouble(this.motionZ);
    }

    @Override
    public IMessage onMessage(MessageParticleUpdate message, MessageContext ctx)
    {
        World world = ModParticlePhysics.PROXY.getWorld();
        Entity entity = world.getEntityByID(message.entityId);
        if (entity != null)
        {
            entity.setPosition(message.posX, message.posY, message.posZ);
            entity.setVelocity(message.motionX, message.motionY, message.motionZ);
            if (entity instanceof TemplateParticle)
            {
                ((TemplateParticle) entity).effect = message.effect;
            }
        }
        return null;
    }
}
