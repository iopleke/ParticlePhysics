package particlephysics.utility;

import cpw.mods.fml.common.network.IGuiHandler;
import cpw.mods.fml.common.network.NetworkRegistry;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import particlephysics.ModParticlePhysics;
import particlephysics.tileentity.emitter.EmitterContainer;
import particlephysics.tileentity.emitter.EmitterGUI;
import particlephysics.tileentity.emitter.EmitterTileEntity;

public class GUIHandler implements IGuiHandler
{

    public GUIHandler()
    {
        NetworkRegistry.INSTANCE.registerGuiHandler(ModParticlePhysics.INSTANCE, this);
    }

    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
    {
        switch (ID)
        {
        case 0:
            return new EmitterContainer(player.inventory, (EmitterTileEntity) world.getTileEntity(x, y, z));
        }
        return null;
    }

    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
    {
        switch (ID)
        {
        case 0:
            return new EmitterGUI(player.inventory, (EmitterTileEntity) world.getTileEntity(x, y, z));
        }
        return null;
    }

}
