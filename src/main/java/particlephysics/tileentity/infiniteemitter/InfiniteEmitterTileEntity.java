package particlephysics.tileentity.infiniteemitter;

import net.minecraftforge.common.util.ForgeDirection;
import particlephysics.entity.particle.TemplateParticle;
import particlephysics.tileentity.emitter.EmitterTileEntity;

public class InfiniteEmitterTileEntity extends EmitterTileEntity
{
    @Override
    public void updateEntity()
    {
        this.intervalReset = (int) (worldObj.getTotalWorldTime() % ((20 * interval) + 20));
        if (!worldObj.isRemote && intervalReset == 0)
        {
            if (this.inventory != null)
            {
                if (this.inventory[0] != null && isValidFuel(this.inventory[0]))
                {
                    // TODO: Get fuel amount from ItemMolecule.getSize() * 100;
                    this.fuelStored = 100;

                    this.fuelType = 0;

                    this.fuelMeta = this.inventory[0].getItemDamage();
                }

            }
            if (this.fuelStored > 0)
            {
                ForgeDirection[] outputDirections =
                {
                    ForgeDirection.SOUTH, ForgeDirection.NORTH, ForgeDirection.WEST, ForgeDirection.EAST
                };
                for (ForgeDirection dir : outputDirections)
                {

                    TemplateParticle particle = getParticleFromFuel(fuelType, fuelMeta);
                    if (particle == null)
                    {
                        return;
                    }
                    particle.addVelocity(dir.offsetX, dir.offsetY, dir.offsetZ);
                    particle.setPosition(xCoord + dir.offsetX + 0.375, yCoord + dir.offsetY + 0.375, zCoord + dir.offsetZ + 0.375);
                    worldObj.spawnEntityInWorld(particle);

                }
            }
        }
    }
}
