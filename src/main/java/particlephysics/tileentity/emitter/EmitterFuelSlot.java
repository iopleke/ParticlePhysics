package particlephysics.tileentity.emitter;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class EmitterFuelSlot extends Slot
{
    EmitterTileEntity emitter;

    public EmitterFuelSlot(IInventory par1iInventory, int par2, int par3, int par4)
    {
        super(par1iInventory, par2, par3, par4);

        emitter = (EmitterTileEntity) par1iInventory;
    }

    @Override
    public boolean isItemValid(ItemStack stack)
    {
        return emitter.isValidFuel(stack);
    }

}
