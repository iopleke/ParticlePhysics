package particlephysics.entity.particle;

import net.minecraft.world.World;

public class ConcentratedParticle extends TemplateParticle
{

    public ConcentratedParticle(World par1World)
    {
        super(par1World);
    }

    @Override
    public float getStartingPotential()
    {
        // TODO Auto-generated method stub
        return 25000;
    }

    @Override
    public String getName()
    {
        return "Concentrated";
    }

    @Override
    public void onCollideWithParticle(TemplateParticle particle)
    {

    }

}
