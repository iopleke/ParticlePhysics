package particlephysics;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import particlephysics.tileentity.emitter.EmitterBlock;

public class ParticlePhysicsTab extends CreativeTabs
{
    public static ParticlePhysicsTab instance;

    public ParticlePhysicsTab()
    {
        super("tabParticlePhysics");

        instance = this;
    }

    @Override
    public ItemStack getIconItemStack()
    {
        return new ItemStack(BetterLoader.getBlock(EmitterBlock.class));

    }
}
