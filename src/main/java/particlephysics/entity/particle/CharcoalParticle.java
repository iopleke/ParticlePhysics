package particlephysics.entity.particle;

import net.minecraft.world.World;

public class CharcoalParticle extends TemplateParticle
{

    public CharcoalParticle(World par1World)
    {
        super(par1World);
    }

    @Override
    public float getStartingPotential()
    {
        // TODO Auto-generated method stub
        return 3000;
    }

    @Override
    public String getName()
    {
        return "Charcoal";
    }

    @Override
    public void onCollideWithParticle(TemplateParticle particle)
    {
        if (!(particle instanceof PaperParticle || particle instanceof CharcoalParticle))
        {
            particle.setDead();
            this.potential += (0.5 * particle.potential);
        }
    }

}
