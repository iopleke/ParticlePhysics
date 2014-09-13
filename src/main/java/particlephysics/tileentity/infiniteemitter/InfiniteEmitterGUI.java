package particlephysics.tileentity.infiniteemitter;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.InventoryPlayer;
import particlephysics.entity.particle.TemplateParticle;
import particlephysics.tileentity.emitter.EmitterGUI;

public class InfiniteEmitterGUI extends EmitterGUI
{
    private final InfiniteEmitterTileEntity tile;

    public InfiniteEmitterGUI(InventoryPlayer invPlayer, InfiniteEmitterTileEntity tile)
    {
        super(invPlayer, tile);
        this.tile = tile;

        xSize = 176;
        ySize = 218;

        this.syncGUIElements();

    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float f, int x, int y)
    {
        Minecraft.getMinecraft().getTextureManager().bindTexture(texture);

        drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);

        if (tile.intervalReset > 0 && tile.fuelStored != 0)
        {
            intervalPercent = (int) (((float) tile.intervalReset / (float) ((tile.interval + 1) * 20)) * 200);
            progressBar = (int) ((float) intervalPercent * 0.7F);
            if (intervalPercent <= 100)
            {
                burstOffset = 0;

                if (progressBar <= 7)
                {
                    progressVertical = progressBar;
                    progressHorizontal = 0;
                } else
                {
                    progressVertical = 7;
                    progressHorizontal = progressBar - 6;
                }

            } else if (intervalPercent >= 100 && intervalPercent <= 200)
            {
                burstOffset = (int) (((float) tile.intervalReset / (float) ((tile.interval + 1) * 20)) * 20) - 11;
            } else
            {
                // a safety net for
                // verifying sanity
                // never should be called
                burstOffset = 0;
            }

        }

        // draw the burst
        if (intervalPercent >= 0 && intervalPercent <= 200 && tile.fuelStored != 0)
        {
            // particle progress bar
            drawTexturedModalRect(guiLeft + 42, guiTop + 38 - progressVertical, 211, 20 - progressVertical, 5, progressVertical);
            drawTexturedModalRect(guiLeft + 42, guiTop + 27, 191, 0, progressHorizontal + 1, 10);

            if (intervalPercent >= 100)
            {
                if (intervalPercent <= 113)
                {
                    guiParticle = (int) ((float) (intervalPercent - 100));
                    if (tile.interval <= 1)
                    {
                        // adjust for 0 and 1 interval
                        guiParticle = guiParticle + 3;
                    }
                }
                // draw "particle" in GUI
                drawTexturedModalRect(guiLeft + 105 + guiParticle, guiTop + 31, 254, 4, 2, 2);

                // draw "burst" pattern
                drawTexturedModalRect(guiLeft + 118 - burstOffset, guiTop + 31 - burstOffset, 200 - burstOffset, 22 - burstOffset, 2 + burstOffset * 2, 2 + burstOffset * 2);
            }
        }

        if (tile.fuelStored > 0)
        {
            int offset = (tile.fuelStored / 2);
            // draw the fuel meter
            drawTexturedModalRect(guiLeft + 37, guiTop + 89 - offset, 176, 50 - offset, 15, 0 + offset);
        }

        if (tile.fuelStored <= 1)
        {
            if (oldTick != tile.getWorldObj().getWorldTime())
            {
                oldTick = tile.getWorldObj().getWorldTime();
                tickCounter++;
                if (tickCounter >= 10 + (20 * tile.interval) * 2)
                {
                    if (fuelLoadVertical <= 13)
                    {
                        fuelLoadVertical++;
                    } else if (fuelLoadhorizontal <= 18)
                    {
                        fuelLoadhorizontal++;
                    }
                }
            }
            if (tile.inventory[0] != null)
            {
                drawTexturedModalRect(guiLeft + 10, guiTop + 70 - fuelLoadVertical, 191, 47 - fuelLoadVertical, 13, fuelLoadVertical);
                drawTexturedModalRect(guiLeft + 17, guiTop + 53, 225, 13, fuelLoadhorizontal, 8);
            }
        } else
        {
            fuelLoadVertical = 0;
            fuelLoadhorizontal = 0;
            tickCounter = 0;
        }

        if (!this.isDragging)
        {
            this.tempHeightSetting = tile.interval;
        }

        bar.draw(this, 0, 218);
        this.updateSliderPosition();
        slider.draw(this, 0, 224);

        fontRendererObj.drawString((this.tempHeightSetting + 1) + " Seconds", guiLeft + 48, guiTop + 4, 0x404040);
        fontRendererObj.drawString("Queue", guiLeft + 85, guiTop + 101, 0x404040);

        if (tile.getStackInSlot(0) != null)
        {
            TemplateParticle particle = tile.getParticleFromFuel(tile.getStackInSlot(0).getItemDamage(), tile.getStackInSlot(0).getItemDamage());
            if (particle != null)
            {
                // Display particle type
                fontRendererObj.drawString(particle.getName(), guiLeft + 115, guiTop + 46, 0x404040);
                fontRendererObj.drawString("Type:", guiLeft + 89, guiTop + 46, 0x404040);
            }
        }

    }
}
