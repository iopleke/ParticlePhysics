package particlephysics.network;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet250CustomPayload;
import particlephysics.entity.particle.TemplateParticle;
import particlephysics.tileentity.emitter.EmitterContainer;
import cpw.mods.fml.common.network.IPacketHandler;
import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.common.network.Player;
import particlephysics.ModParticlePhysics;

public class PacketHandler implements IPacketHandler
{

    public static PacketHandler instance;

    @Override
    public void onPacketData(INetworkManager manager, Packet250CustomPayload packet, Player playerEntity)
    {
        if (packet.channel.equals(ModParticlePhysics.CHANNEL_NAME))
        {

            DataInputStream inputStream = new DataInputStream(new ByteArrayInputStream(packet.data));
            int type = -1;
            try
            {
                type = inputStream.readByte();
            } catch (IOException e)
            {

                e.printStackTrace();
            }
            switch (type)
            {
                case 0:
                    this.handleParticleUpdatePacket(inputStream);
                    break;
                case 1:
                    if (playerEntity instanceof EntityPlayer)
                    {
                        Container container = ((EntityPlayer) playerEntity).openContainer;
                        if (container instanceof EmitterContainer)
                        {
                            try
                            {
                                ((EmitterContainer) container).getMachine().receiveButton(inputStream.readByte(), inputStream.readByte());
                            } catch (IOException e)
                            {

                                e.printStackTrace();
                            }
                        }
                    }
            }

        }
    }

    private void handleParticleUpdatePacket(DataInputStream inputStream)
    {

        try
        {

            int entityId = inputStream.readInt();
            Entity toMove = Minecraft.getMinecraft().theWorld.getEntityByID(entityId);
            if (toMove != null)
            {
                toMove.setPosition(inputStream.readDouble(), inputStream.readDouble(), inputStream.readDouble());
                toMove.setVelocity(inputStream.readDouble(), inputStream.readDouble(), inputStream.readDouble());

                if (toMove instanceof TemplateParticle)
                {
                    ((TemplateParticle) toMove).effect = inputStream.readInt();

                }
            }
        } catch (IOException e)
        {
            e.printStackTrace();
            return;
        }

    }

    public static void sendInterfacePacket(byte type, byte val)
    {
        ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
        DataOutputStream dataStream = new DataOutputStream(byteStream);

        try
        {
            dataStream.writeByte((byte) 1);

            dataStream.writeByte(type);
            dataStream.writeByte(val);

            PacketDispatcher.sendPacketToServer(PacketDispatcher.getPacket(ModParticlePhysics.CHANNEL_NAME, byteStream.toByteArray()));
        } catch (IOException ex)
        {
            System.err.append("Failed to send button click packet");
        }
    }

}
