package particlephysics.tileentity.receptor;

import net.minecraft.tileentity.TileEntity;
import particlephysics.api.IParticleReceptor;
import particlephysics.entity.particle.TemplateParticle;

public class SeriesReceptorTileEntity extends TileEntity implements IParticleReceptor
{

    public int excitedTicks = 0;

    public float powerToDischarge = 0;

    // 28000 RF per coal
    // 280 RF per emission
    // 70 RF per coal particle
    // Potential of 4000

    public static final float constant = .12F;

    @Override
    public void onContact(TemplateParticle particle)
    {
        this.excitedTicks = 20;
        particle.setDead();
    }

    @Override
    public void updateEntity()
    {
        super.updateEntity();
    }

}
