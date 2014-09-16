package particlephysics.tileentity.emitter;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;
import particlephysics.entity.particle.CoalParticle;
import particlephysics.entity.particle.TemplateParticle;
import particlephysics.tileentity.infiniteemitter.InfiniteEmitterBlock;

public class EmitterTileEntity extends TileEntity implements IInventory
{
    public int interval = 40;
    public ItemStack[] inventory = new ItemStack[7];

    public int fuelType;

    public int fuelMeta;
    public int fuelStored = 0;
    public int intervalReset = 0;

    @Override
    public void updateEntity()
    {
        pushQueue();
        intervalReset = (int)(worldObj.getTotalWorldTime() % ((20 * interval) + 20));
        if (!worldObj.isRemote && intervalReset == 0)
        {
            if (fuelStored < 1)
            {
                if (this.inventory != null)
                {
                    if (this.inventory[0] != null && isValidFuel(this.inventory[0]))
                    {
                        // TODO: Get fuel amount from ItemMolecule.getSize() * 100;
                        this.fuelStored = 100;

                        this.fuelType = 0;

                        // to my future self
                        // when you refactor this code
                        // forgive my errors
                        this.fuelMeta = this.inventory[0].getItemDamage();

                        this.decrStackSize(0, 1);
                        if (this.inventory[0] == null)
                        {
                            for (int i = 1; i < getSizeInventory(); i++)
                            {
                                ItemStack item = getStackInSlot(i);
                                if (item != null && this.isValidFuel(item))
                                {
                                    this.setInventorySlotContents(0, item);
                                    this.setInventorySlotContents(i, null);
                                    break;
                                }
                            }
                        }
                    }
                }
            }
            if (fuelStored > 0)
            {
                if (!(worldObj.getBlock(xCoord, yCoord, zCoord) instanceof InfiniteEmitterBlock && getStackInSlot(0) != null && getStackInSlot(0).stackSize >= 63))
                {
                    this.fuelStored--;
                }
                ForgeDirection[] outputDirections =
                {
                    ForgeDirection.SOUTH, ForgeDirection.NORTH, ForgeDirection.WEST, ForgeDirection.EAST
                };
                for (ForgeDirection dir : outputDirections)
                {

                    TemplateParticle particle = getParticleFromFuel(fuelType, fuelMeta);
                    if (particle == null)
                    {
                        return;
                    }
                    particle.addVelocity(dir.offsetX, dir.offsetY, dir.offsetZ);
                    particle.setPosition(xCoord + dir.offsetX + 0.375, yCoord + dir.offsetY + 0.375, zCoord + dir.offsetZ + 0.375);
                    worldObj.spawnEntityInWorld(particle);

                }
            }
        }
    }

    protected void pushQueue()
    {
        for(int i = this.inventory.length -1; i >= 0; i--)
        {
            if (i == 0 || this.inventory[i] == null)
            {
                continue;
            }
            else if (this.inventory[i-1] == null)
            {
                setInventorySlotContents(i-1, this.inventory[i]);
                setInventorySlotContents(i, null);
            }
            else if (this.inventory[i-1].isItemEqual(this.inventory[i]))
            {
                int spaceLeft = this.inventory[i-1].getMaxStackSize() - this.inventory[i-1].stackSize;
                if (spaceLeft >= this.inventory[i].stackSize)
                {
                    this.inventory[i-1].stackSize += this.inventory[i].stackSize;
                    this.inventory[i] = null;
                }
                else
                {
                    this.inventory[i-1].stackSize += spaceLeft;
                    this.inventory[i].stackSize -= spaceLeft;
                }
            }
        }
    }

    public TemplateParticle getParticleFromFuel(int fuel, int meta)
    {
        return new CoalParticle(this.worldObj);
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt)
    {
        super.readFromNBT(nbt);
        this.fuelStored = nbt.getInteger("Fuel");

        this.fuelType = nbt.getInteger("FuelType");
        this.interval = nbt.getInteger("Interval");
        this.fuelMeta = nbt.getInteger("FuelMeta");
        NBTTagList tagList = nbt.getTagList("Inventory", 10);
        for (int i = 0; i < tagList.tagCount(); i++)
        {
            NBTTagCompound compound = tagList.getCompoundTagAt(i);
            int slot = compound.getInteger("Slot");
            inventory[slot] = ItemStack.loadItemStackFromNBT(compound);
        }
    }

    @Override
    public void writeToNBT(NBTTagCompound nbt)
    {
        super.writeToNBT(nbt);
        nbt.setInteger("Interval", this.interval);
        nbt.setInteger("Fuel", this.fuelStored);
        nbt.setInteger("FuelType", this.fuelType);
        nbt.setInteger("FuelMeta", this.fuelMeta);
        NBTTagList tagList = new NBTTagList();
        for (int i = 0; i < inventory.length; i++)
        {
            ItemStack item = getStackInSlot(i);
            if (item != null)
            {
                NBTTagCompound compound = new NBTTagCompound();
                compound.setInteger("Slot", i);
                item.writeToNBT(compound);
                tagList.appendTag(compound);
            }
        }
        nbt.setTag("Inventory", tagList);
    }

    @Override
    public int getSizeInventory()
    {
        return 7;
    }

    @Override
    public ItemStack getStackInSlot(int i)
    {
        return this.inventory[i];
    }

    @Override
    public ItemStack decrStackSize(int i, int j)
    {
        ItemStack itemstack = getStackInSlot(i);

        if (itemstack != null)
        {
            if (itemstack.stackSize <= j)
            {
                setInventorySlotContents(i, null);
            } else
            {
                itemstack = itemstack.splitStack(j);
            }
        }
        return itemstack;
    }

    @Override
    public ItemStack getStackInSlotOnClosing(int i)
    {
        ItemStack item = getStackInSlot(i);
        setInventorySlotContents(i, null);
        return item;
    }

    @Override
    public void setInventorySlotContents(int i, ItemStack itemStack)
    {
        this.inventory[i] = itemStack;
    }

    @Override
    public String getInventoryName()
    {
        return "emitter";
    }

    @Override
    public boolean hasCustomInventoryName()
    {
        return false;
    }

    @Override
    public int getInventoryStackLimit()
    {
        return 64;
    }

    @Override
    public boolean isUseableByPlayer(EntityPlayer entityplayer)
    {
        return entityplayer.getDistanceSq(xCoord, yCoord, zCoord) < 64;
    }

    @Override
    public void openInventory()
    {

    }

    @Override
    public void closeInventory()
    {

    }

    @Override
    public boolean isItemValidForSlot(int i, ItemStack itemstack)
    {
        return isValidFuel(itemstack);
    }

    public boolean isValidFuel(ItemStack itemstack)
    {
        return true;
    }

    public void receiveButton(int type, int value)
    {
        switch (type)
        {
            case 0:
                switch (value)
                {
                    case 0:
                        this.fuelStored = 0;
                }
            case 1:
                this.interval = value;
        }
    }

}
