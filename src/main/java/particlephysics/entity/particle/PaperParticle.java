package particlephysics.entity.particle;

import net.minecraft.world.World;

public class PaperParticle extends TemplateParticle
{

    public PaperParticle(World par1World)
    {
        super(par1World);
    }

    @Override
    public float getStartingPotential()
    {
        return 500;
    }

    @Override
    public void onEntityUpdate()
    {
        super.onEntityUpdate();
    }

    @Override
    public String getName()
    {
        return "Paper";
    }

    @Override
    public void onCollideWithParticle(TemplateParticle particle)
    {
        if ((particle instanceof CharcoalParticle))
        {
            BlankParticle blankParticle = new BlankParticle(worldObj);

            blankParticle.setPosition(this.posX, this.posY, this.posZ);

            blankParticle.addVelocity(this.motionX, this.motionY, this.motionZ);

            blankParticle.movementDirection = this.movementDirection;
            worldObj.spawnEntityInWorld(blankParticle);
            particle.setDead();
            this.setDead();
        }
    }

}
