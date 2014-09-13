package particlephysics.api;

import net.minecraft.world.World;
import particlephysics.entity.particle.TemplateParticle;

public interface IParticleBouncer
{
    public boolean canBounce(World world, int x, int y, int z, TemplateParticle particle);

}
