package particlephysics.entity.particle;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import particlephysics.ParticleRegistry;
import particlephysics.api.IParticleBouncer;
import particlephysics.api.IParticleReceptor;
import particlephysics.utility.BlockRenderInfo;

public abstract class TemplateParticle extends EntityLivingBase
{

    public int ticks = 0;

    public int iSize = 1;
    public int jSize = 1;
    public int kSize = 1;
    public int rotationX = 0;
    public int rotationY = 0;
    public int rotationZ = 0;
    public float potential;
    public ForgeDirection movementDirection;

    public int effect = 0;
    public static IIcon icon;

    public TemplateParticle(World par1World)
    {
        super(par1World);
        this.setSize(0.25F, 0.25F);
        this.potential = getStartingPotential();
    }

    public abstract float getStartingPotential();

    public abstract String getName();

    public BlockRenderInfo getRenderIcon()
    {
        Class key = this.getClass();
        return new BlockRenderInfo(ParticleRegistry.icons.get(key));
    }

    public void bounce(int targetX, int targetY, int targetZ, ForgeDirection forward)
    {

        int x = MathHelper.floor_double(posX);
        int y = MathHelper.floor_double(posY);
        int z = MathHelper.floor_double(posZ);
        ArrayList<ForgeDirection> freeDirections = new ArrayList<ForgeDirection>();
        for (ForgeDirection dir : ForgeDirection.VALID_DIRECTIONS)
        {
            if (dir.getOpposite() != forward && worldObj.isAirBlock(x + dir.offsetX, y + dir.offsetY, z + dir.offsetZ))
            {
                freeDirections.add(dir);
            }
        }
        if (freeDirections.size() != 0)
        {
            ForgeDirection targetDirection = freeDirections.get((new Random().nextInt(freeDirections.size())));
            this.setPosition(x + 0.375, y + 0.375, z + 0.375);
            this.potential *= 0.9;
            double f = (this.potential / getStartingPotential());

            this.motionX = targetDirection.offsetX * f;
            this.motionY = targetDirection.offsetY * f;
            this.motionZ = targetDirection.offsetZ * f;
            this.onBounceHook(x, y, z);

        } else
        {
            this.setDead();
        }
    }

    @Override
    public boolean canRenderOnFire()
    {
        return this.effect == 1;
    }

    public void onBounceHook(int x, int y, int z)
    {

    }

    @Override
    public ItemStack[] getLastActiveItems()
    {
        return new ItemStack[0];
    }

    @Override
    public void setCurrentItemOrArmor(int i, ItemStack itemStack)
    {

    }

    @Override
    public void onEntityUpdate()
    {
        ticks++;
        if (!worldObj.isRemote && worldObj.getTotalWorldTime() % 20 == 0)
        {
            // Debug code when dealing with potentials
            // System.out.println("Energy: "+this.potential+" Of: "+this.getName());
        }

        // Blaze Fire
        if (this.effect == 1)
        {
            this.potential *= 0.97;
        }
        // Leaf Regeneration
        if (this.effect == 2)
        {
            this.potential *= 1.01;

        }
        super.onEntityUpdate();
        if (ticks > 600 || this.posY > 257)
        {
            this.setDead();
        }
        if (worldObj.isRemote)
        {
            return;
        }
        ForgeDirection dir = this.getForward();
        if (dir != ForgeDirection.UNKNOWN)
        {
            this.movementDirection = dir;
        }
        if (!worldObj.isRemote && Math.abs(motionX) < 0.3 && Math.abs(motionY) < 0.3 && Math.abs(motionZ) < 0.3)
        {
            ForgeDirection forward = movementDirection;
            if (forward == null)
            {
                return;

            }
            int targetX = MathHelper.floor_double(posX + (0.5 * forward.offsetX));

            int targetY = MathHelper.floor_double(posY + (0.5 * forward.offsetY));

            int targetZ = MathHelper.floor_double(posZ + (0.5 * forward.offsetZ));
            Block block = worldObj.getBlock(targetX, targetY, targetZ);
            boolean isReflective = false;
            if (block instanceof IParticleBouncer)
            {

                isReflective = ((IParticleBouncer) block).canBounce(worldObj, targetX, targetY, targetZ, this);

            }

            if (block == Blocks.glass || isReflective)
            {
                this.bounce(targetX, targetY, targetZ, forward);
            } else
            {
                // Polarized glass
                // Magic numbers are fun!
                TileEntity entity = worldObj.getTileEntity(targetX, targetY, targetZ);
                if (entity instanceof IParticleReceptor)
                {
                    ((IParticleReceptor) entity).onContact(this);
                } else
                {
                    this.setDead();
                }
            }
        }
        this.checkForParticleCollision();
    }

    @Override
    public ItemStack getHeldItem()
    {
        return null;
    }

    @Override
    public ItemStack getEquipmentInSlot(int slot)
    {
        return null;
    }

    public void checkForParticleCollision()
    {
        if (!worldObj.isRemote)
        {
            List<TemplateParticle> nearbyParticles = this.worldObj.getEntitiesWithinAABB(TemplateParticle.class, AxisAlignedBB.getBoundingBox(posX - 1.5, posY - 1.5, posZ - 1.5, posX + 1.5, posY + 1.5, posZ + 1.5));

            for (int i = 0; i < nearbyParticles.size(); i++)
            {
                TemplateParticle particle = nearbyParticles.get(i);
                if (this != particle && !this.isDead && !particle.isDead)
                {
                    this.onCollideWithParticle(particle);
                }
            }
        }
    }

    public abstract void onCollideWithParticle(TemplateParticle particle);

    @Override
    public void writeToNBT(NBTTagCompound nbt)
    {
        super.writeToNBT(nbt);
        if (movementDirection != null)
        {
            nbt.setInteger("direction", movementDirection.ordinal());
        }
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt)
    {
        super.readFromNBT(nbt);
        this.movementDirection = ForgeDirection.VALID_DIRECTIONS[nbt.getInteger("direction")];
    }

    public ForgeDirection getForward()
    {

        if (motionX > 0.1)
        {
            return ForgeDirection.EAST;
        }
        if (motionX < -0.1)
        {
            return ForgeDirection.WEST;
        }

        if (motionY > 0.1)
        {
            return ForgeDirection.UP;
        }
        if (motionY < -0.1)
        {
            return ForgeDirection.DOWN;
        }

        if (motionZ > 0.1)
        {
            return ForgeDirection.SOUTH;
        }
        if (motionZ < -0.1)
        {
            return ForgeDirection.NORTH;
        }
        return ForgeDirection.UNKNOWN;

    }

    @Override
    public void moveEntityWithHeading(float par1, float par2)
    {
        double d0;

        {
            float f2 = 0.91F;

            float f3 = 0.16277136F / (f2 * f2 * f2);
            float f4;

            if (this.onGround)
            {
                f4 = this.getAIMoveSpeed() * f3;
            } else
            {
                f4 = this.jumpMovementFactor;
            }

            this.moveFlying(par1, par2, f4);
            f2 = 0.91F;

            if (this.onGround)
            {
                f2 = 0.54600006F;
                Block block = this.worldObj.getBlock(MathHelper.floor_double(this.posX), MathHelper.floor_double(this.boundingBox.minY) - 1, MathHelper.floor_double(this.posZ));

                if (block != Blocks.air)
                {
                    f2 = block.slipperiness * 0.91F;
                }
            }

            this.moveEntity(this.motionX, this.motionY, this.motionZ);

        }

        d0 = this.posX - this.prevPosX;
        double d1 = this.posZ - this.prevPosZ;
        float f6 = MathHelper.sqrt_double(d0 * d0 + d1 * d1) * 4.0F;

        if (f6 > 1.0F)
        {
            f6 = 1.0F;
        }

    }

    @Override
    public boolean isAIEnabled()
    {
        return false;
    }

    @Override
    protected void updateFallState(double par1, boolean par3)
    {
    }

    @Override
    public boolean canBePushed()
    {
        return false;
    }

    @Override
    protected void collideWithEntity(Entity par1Entity)
    {
        if (!(par1Entity instanceof TemplateParticle))
        {
            par1Entity.applyEntityCollision(this);
        }
    }

    @Override
    public boolean canBeCollidedWith()
    {
        return false;
    }

}
