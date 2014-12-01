package particlephysics.tileentity.receptor;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChatComponentText;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;

public class SeriesReceptorInteractionHandler
{
    @SubscribeEvent
    public void onInteract(PlayerInteractEvent event)
    {
        if (event.action == PlayerInteractEvent.Action.RIGHT_CLICK_BLOCK)
        {
            TileEntity te = event.world.getTileEntity(event.x, event.y, event.z);
            if (te != null && te instanceof SeriesReceptorTileEntity)
            {
                SeriesReceptorTileEntity receptor = (SeriesReceptorTileEntity) te;
                if (event.entityPlayer != null && event.entityPlayer instanceof EntityPlayerMP)
                event.entityPlayer.addChatComponentMessage(
                        new ChatComponentText("Stored power: " + receptor.getEnergyStored(null) + "/" + receptor.getMaxEnergyStored(null) + "RF")
                );
            }
        }
    }
}
