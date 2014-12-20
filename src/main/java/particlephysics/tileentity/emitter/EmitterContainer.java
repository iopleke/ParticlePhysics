package particlephysics.tileentity.emitter;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class EmitterContainer extends Container
{

    private EmitterTileEntity machine;

    public EmitterContainer(InventoryPlayer invPlayer, EmitterTileEntity machine)
    {
        this.machine = machine;

        // Player inventory hotbar slots
        for (int x = 0; x < 9; x++)
        {
            addSlotToContainer(new Slot(invPlayer, x, 8 + 18 * x, 64 + 130));
        }
        // Player non-hotbar inventory
        for (int y = 0; y < 3; y++)
        {
            for (int x = 0; x < 9; x++)
            {
                addSlotToContainer(new Slot(invPlayer, x + y * 9 + 9, 8 + 18 * x, 64 + 72 + y * 18));
            }
        }

        // Add active fuel slot
        addSlotToContainer(new EmitterFuelSlot(machine, 0, 8, 73));

        // Extra fuel container slots
        // Change this to 9 eventually?
        // Throws arrayoutofbounds on existing TEs.
        for (int x = 0; x < 6; x++)
        {
            addSlotToContainer(new EmitterFuelSlot(machine, 1 + x, 8 + 18 * x, 111));
        }
    }

    public EmitterTileEntity getMachine()
    {
        return this.machine;
    }

    @Override
    public boolean canInteractWith(EntityPlayer entityplayer)
    {
        return machine.isUseableByPlayer(entityplayer);
    }

    @Override
    public ItemStack transferStackInSlot(EntityPlayer player, int i)
    {
        Slot slot = getSlot(i);

        if (slot != null && slot.getHasStack())
        {
            ItemStack stack = slot.getStack();

            if (i >= 36)
            {
                if (!mergeItemStack(stack, 0, 36, false))
                {
                    return null;
                }
            } else if (machine.isValidFuel(stack) || !mergeItemStack(stack, 36, 36 + machine.getSizeInventory(), false))
            {
                return null;
            }

            if (stack.stackSize == 0)
            {
                slot.putStack(null);
            } else
            {
                slot.onSlotChanged();
            }

            slot.onPickupFromSlot(player, stack);

        }
        return null;
    }

    @Override
    public void addCraftingToCrafters(ICrafting player)
    {
        super.addCraftingToCrafters(player);

        player.sendProgressBarUpdate(this, 0, machine.fuelStored);
        player.sendProgressBarUpdate(this, 1, machine.fuelType != null ? Item.getIdFromItem(machine.fuelType.getItem()) : 0);
        player.sendProgressBarUpdate(this, 2, machine.fuelType != null ? machine.fuelType.getItemDamage() : 0);
        player.sendProgressBarUpdate(this, 3, machine.interval);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void updateProgressBar(int id, int data)
    {
        if (id == 0)
        {
            machine.fuelStored = data;
        }
        if (id == 1)
        {
            machine.setFuelData(1, data);
        }
        if (id == 2)
        {
            machine.setFuelData(2, data);
        }
        if (id == 3)
        {
            machine.interval = data;
        }
    }

    private int oldFuelStored;
    private Item oldFuelType;
    private int oldFuelMeta;

    @Override
    public void detectAndSendChanges()
    {
        super.detectAndSendChanges();
        for (Object player : crafters)
        {

            if (oldFuelStored != machine.fuelStored)
            {
                ((ICrafting) player).sendProgressBarUpdate(this, 0, machine.fuelStored);
            }
            if (machine.fuelType != null)
            {
                if (oldFuelType != machine.fuelType.getItem())
                {
                    ((ICrafting) player).sendProgressBarUpdate(this, 1, Item.getIdFromItem(machine.fuelType.getItem()));
                }
                if (oldFuelMeta != machine.fuelType.getItemDamage())
                {
                    ((ICrafting) player).sendProgressBarUpdate(this, 2, machine.fuelType.getItemDamage());
                }
            }

        }
        this.oldFuelStored = machine.fuelStored;
        if (machine.fuelType != null)
        {
            this.oldFuelType = machine.fuelType.getItem();
            this.oldFuelMeta = machine.fuelType.getItemDamage();
        } else
        {
            this.oldFuelType = null;
            this.oldFuelMeta = -1;
        }
    }

}
