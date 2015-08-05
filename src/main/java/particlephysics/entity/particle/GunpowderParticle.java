package particlephysics.entity.particle;

import net.minecraft.world.World;

public class GunpowderParticle extends TemplateParticle
{

    public GunpowderParticle(World worldObj)
    {
        super(worldObj);
    }

    public int ticksToExplode;

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
        return "Gunpowder";
    }

    @Override
    public void onEntityUpdate()
    {
        super.onEntityUpdate();
        this.ticksToExplode++;
        if (this.ticksToExplode > 100)
        {
            worldObj.createExplosion(this, this.posX, this.posY, this.posZ, 1F, true);
            this.setDead();
        }
    }

    @Override
    public void onCollideWithParticle(TemplateParticle particle)
    {
        if (particle instanceof GunpowderParticle)
        {
            return;
        }
        GunpowderParticle produce = new GunpowderParticle(worldObj);
        produce.potential = particle.potential;
        produce.setPosition(particle.posX, particle.posY, particle.posZ);
        produce.addVelocity(particle.motionX, particle.motionY, particle.motionZ);
        // this.addVelocity(this.motionX*-1, this.motionY*-1,this.motionZ*-1);
        worldObj.spawnEntityInWorld(produce);
        particle.setDead();
    }

}
