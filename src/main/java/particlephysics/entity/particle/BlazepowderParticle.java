package particlephysics.entity.particle;

import net.minecraft.world.World;

public class BlazepowderParticle extends TemplateParticle
{

    public BlazepowderParticle(World par1World)
    {
        super(par1World);
        // TODO Auto-generated constructor stub
    }

    @Override
    public float getStartingPotential()
    {
        // TODO Auto-generated method stub
        return 1000;
    }

    @Override
    public String getName()
    {
        // TODO Auto-generated method stub
        return "Blazepowder";
    }

    @Override
    public void onCollideWithParticle(TemplateParticle particle)
    {
        if (!(particle instanceof BlazepowderParticle))
        {
            particle.effect = 1;
        }
    }

}
