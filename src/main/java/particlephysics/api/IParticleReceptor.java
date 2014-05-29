package particlephysics.api;

import particlephysics.entity.particle.TemplateParticle;

public interface IParticleReceptor
{

    public void onContact(TemplateParticle particle);

}
