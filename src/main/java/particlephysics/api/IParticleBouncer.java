package particlephysics.api;

import particlephysics.entity.particle.TemplateParticle;
import net.minecraft.world.World;

public interface IParticleBouncer
{
    public boolean canBounce(World world, int x, int y, int z, TemplateParticle particle);

}
