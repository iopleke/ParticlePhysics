package particlephysics.entity.particle;

import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;
import particlephysics.utility.CoordTuple;

public class SandParticle extends TemplateParticle
{

    public SandParticle(World par1World)
    {
        super(par1World);
        // TODO Auto-generated constructor stub
    }

    public int genLeft = 4;

    @Override
    public float getStartingPotential()
    {
        // TODO Auto-generated method stub
        return 100;
    }

    @Override
    public String getName()
    {
        return "Sand";
    }

    @Override
    public void onCollideWithParticle(TemplateParticle particle)
    {

    }

    @Override
    public void onBounceHook(int x, int y, int z)
    {
        CoordTuple newTuple = new CoordTuple(x, y, z);
        if (genLeft == 0)
        {
            return;
        }
        genLeft--;
        SandParticle reflectedParticle = new SandParticle(this.worldObj);
        reflectedParticle.genLeft = this.genLeft;
        ForgeDirection dir = this.movementDirection.getOpposite();
        reflectedParticle.setVelocity(dir.offsetX, dir.offsetY, dir.offsetZ);
        reflectedParticle.setPosition(this.posX, this.posY, this.posZ);
        worldObj.spawnEntityInWorld(reflectedParticle);
    }

}
