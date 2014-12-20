package particlephysics.tileentity.receptor;

import cofh.api.energy.IEnergyHandler;
import cofh.api.energy.TileEnergyHandler;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;
import particlephysics.api.IParticleReceptor;
import particlephysics.entity.particle.TemplateParticle;

public class SeriesReceptorTileEntity extends TileEnergyHandler implements IParticleReceptor
{
    // 28000 RF per coal
    // 280 RF per emission
    // 70 RF per coal particle
    // Potential of 4000

    public static final float constant = .12F;

    @Override
    public void onContact(TemplateParticle particle)
    {
        particle.setDead();
        this.storage.modifyEnergyStored(((int) (constant * particle.potential)));
    }

    @Override
    public void updateEntity()
    {
        super.updateEntity();
        if (this.storage.getEnergyStored() > 0)
        {
            for (ForgeDirection dir : ForgeDirection.VALID_DIRECTIONS)
            {
                TileEntity te = worldObj.getTileEntity(xCoord + dir.offsetX, yCoord + dir.offsetY, zCoord + dir.offsetZ);
                if (te != null && te instanceof IEnergyHandler)
                {
                    IEnergyHandler other = (IEnergyHandler) te;
                    if (other.canConnectEnergy(dir.getOpposite()))
                    {
                        this.storage.modifyEnergyStored(-1 * other.receiveEnergy(dir.getOpposite(), this.getEnergyStored(null), false));
                    }
                }
            }
        }
    }

    @Override
    public int receiveEnergy(ForgeDirection from, int maxReceive, boolean simulate)
    {
        return 0;
    }
}
