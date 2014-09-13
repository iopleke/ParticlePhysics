package particlephysics.proxy;

import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.util.IIcon;
import particlephysics.entity.particle.RenderParticle;
import particlephysics.entity.particle.TemplateParticle;

@SideOnly(Side.CLIENT)
public class ClientProxy extends CommonProxy
{

    public static int RENDER_ID;

    public static IIcon clay;
    public static IIcon coal;
    public static IIcon concentrated;
    public static IIcon seed;
    public static IIcon split;

    public static IIcon sand;

    @Override
    public void registerRenderers()
    {
        RenderingRegistry.registerEntityRenderingHandler(TemplateParticle.class, new RenderParticle());
    }
}
