package particlephysics.utility;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import particlephysics.ModParticlePhysics;
import particlephysics.tileentity.emitter.EmitterTileEntity;
import cpw.mods.fml.common.network.IGuiHandler;
import cpw.mods.fml.common.network.NetworkRegistry;
import particlephysics.tileentity.emitter.EmitterContainer;
import particlephysics.tileentity.emitter.EmitterGUI;

public class GUIHandler implements IGuiHandler
{

    public GUIHandler()
    {
        NetworkRegistry.instance().registerGuiHandler(ModParticlePhysics.INSTANCE, this);
    }

    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
    {
        switch (ID)
        {
        case 0:
            return new EmitterContainer(player.inventory, (EmitterTileEntity) world.getBlockTileEntity(x, y, z));
        }
        return null;
    }

    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
    {
        switch (ID)
        {
        case 0:
            return new EmitterGUI(player.inventory, (EmitterTileEntity) world.getBlockTileEntity(x, y, z));
        }
        return null;
    }

}
