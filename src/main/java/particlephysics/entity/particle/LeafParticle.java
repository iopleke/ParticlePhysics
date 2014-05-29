package particlephysics.entity.particle;

import net.minecraft.world.World;

public class LeafParticle extends TemplateParticle
{

    public LeafParticle(World par1World)
    {
        super(par1World);
    }

    @Override
    public float getStartingPotential()
    {
        return 500;
    }

    @Override
    public String getName()
    {
        return "Leaf";
    }

    @Override
    public void onCollideWithParticle(TemplateParticle particle)
    {
        if ((!(particle instanceof LeafParticle)) && particle.effect != 2)
        {
            particle.potential *= 0.5;
            particle.effect = 2;
        }

    }

}
