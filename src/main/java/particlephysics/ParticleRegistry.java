package particlephysics;

import cpw.mods.fml.common.registry.EntityRegistry;
import java.util.ArrayList;
import java.util.Hashtable;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;
import particlephysics.entity.particle.BlankParticle;
import particlephysics.entity.particle.BlazepowderParticle;
import particlephysics.entity.particle.CharcoalParticle;
import particlephysics.entity.particle.ClayParticle;
import particlephysics.entity.particle.CoalParticle;
import particlephysics.entity.particle.ConcentratedParticle;
import particlephysics.entity.particle.GlassParticle;
import particlephysics.entity.particle.GunpowderParticle;
import particlephysics.entity.particle.LeafParticle;
import particlephysics.entity.particle.PaperParticle;
import particlephysics.entity.particle.SandParticle;
import particlephysics.entity.particle.SeedParticle;
import particlephysics.entity.particle.SplitParticle;
import particlephysics.entity.particle.TemplateParticle;

public class ParticleRegistry
{

    public static ArrayList<Class> particles = new ArrayList();

    public static void populateParticleList()
    {
        particles.add(ClayParticle.class);
        particles.add(CoalParticle.class);
        particles.add(ConcentratedParticle.class);
        particles.add(SandParticle.class);
        particles.add(SeedParticle.class);
        particles.add(SplitParticle.class);
        particles.add(CharcoalParticle.class);
        particles.add(GlassParticle.class);
        particles.add(PaperParticle.class);
        particles.add(BlankParticle.class);
        particles.add(GunpowderParticle.class);
        particles.add(BlazepowderParticle.class);
        particles.add(LeafParticle.class);
    }

    public static Hashtable<Class, IIcon> icons = new Hashtable<Class, IIcon>();

    public static void populateIcons(IIconRegister register)
    {

        for (int i = 0; i < particles.size(); i++)
        {
            IIcon particleIcon = register.registerIcon(ParticlePhysics.ID + ":" + particles.get(i).getName().substring("particlephysics.entity.particle.".length()));
            icons.put(particles.get(i), particleIcon);
        }

    }

    public static IIcon getIconFromInstance(TemplateParticle particle)
    {
        for (int i = 0; i < particles.size(); i++)
        {
            if (particles.get(i).isInstance(particle))
            {
                return icons.get(particles.get(i));
            }
        }
        return null;
    }

    public static void registerEntities()
    {

        for (int i = 0; i < particles.size(); i++)
        {
            try
            {
                EntityRegistry.registerModEntity(particles.get(i), particles.get(i).getName(), i, ParticlePhysics.INSTANCE, 80, 1, true);

            } catch (SecurityException e)
            {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

}
