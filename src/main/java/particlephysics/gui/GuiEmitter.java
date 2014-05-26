package particlephysics.gui;

import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import particlephysics.PacketHandler;
import particlephysics.api.BaseParticle;
import particlephysics.tile.EmitterTileEntity;

// Thanks to VSWE for assorted bits of the code
public class GuiEmitter extends GuiContainer
{
    private static final ResourceLocation texture = new ResourceLocation("particlephysics", "textures/gui/emitter.png");
    private final EmitterTileEntity tile;

    public static final int sliderLeftOffset = 38;
    private int intervalPercent = 0;
    private int progressBar = 0;
    private int burstOffset = 0;

    public static final GuiRectangle bar = new GuiRectangle(sliderLeftOffset, 15, 87, 6);
    public static final GuiRectangle slider = new GuiRectangle(sliderLeftOffset, 12, 8, 11);

    public GuiEmitter(InventoryPlayer invPlayer, EmitterTileEntity tile)
    {
        super(new ContainerEmitter(invPlayer, tile));
        this.tile = tile;

        xSize = 176;
        ySize = 218;

    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float f, int x, int y)
    {
        Minecraft.getMinecraft().getTextureManager().bindTexture(texture);

        drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);

        if (tile.intervalReset > 0)
        {
            progressBar = (int) (((float) tile.intervalReset / (float) ((tile.interval + 1) * 20)) * 20);

            if (progressBar <= 10)
            {
                burstOffset = 0;
                // do progress bar stuffs
            } else if (progressBar >= 10 && progressBar <= 20)
            {
                burstOffset = progressBar - 11;
            } else
            {
                // a safety net for
                // verifying sanity
                // never should be called
                burstOffset = 0;
            }

        }

        // draw the burst
        if (progressBar >= 0 && progressBar <= 20 && burstOffset >= 0 && burstOffset <= 10)
        {
            drawTexturedModalRect(guiLeft + 118 - burstOffset, guiTop + 31 - burstOffset, 200 - burstOffset, 22 - burstOffset, 2 + burstOffset * 2, 2 + burstOffset * 2);
        }

        // draw the fuel meter
        if (tile.fuelStored > 0)
        {
            int offset = (tile.fuelStored / 2);
            drawTexturedModalRect(guiLeft + 37, guiTop + 89 - offset, 176, 50 - offset, 15, 0 + offset);
        }

        if (!this.isDragging)
        {
            this.tempHeightSetting = tile.interval;
        }

        bar.draw(this, 0, 218);
        this.updateSliderPosition();
        slider.draw(this, 0, 224);

        fontRenderer.drawString((this.tempHeightSetting + 1) + " Seconds", guiLeft + 48, guiTop + 4, 0x404040);
        fontRenderer.drawString("Queue", guiLeft + 85, guiTop + 101, 0x404040);

        if (tile.getStackInSlot(0) != null)
        {
            BaseParticle particle = tile.getParticleFromFuel(tile.getStackInSlot(0).itemID, tile.getStackInSlot(0).getItemDamage());
            if (particle != null)
            {
                // Display particle type
                fontRenderer.drawString(particle.getName(), guiLeft + 115, guiTop + 46, 0x404040);
                fontRenderer.drawString("Type:", guiLeft + 89, guiTop + 46, 0x404040);
            }
        }

    }

    public int getLeft()
    {
        return this.guiLeft;
    }

    public int getTop()
    {
        return this.guiTop;
    }

    public void drawHoverString(List l, int w, int h)
    {
        this.drawHoveringText(l, w, h, fontRenderer);
    }

    @Override
    protected void actionPerformed(GuiButton button)
    {
        super.actionPerformed(button);
        PacketHandler.sendInterfacePacket((byte) 0, (byte) button.id);

    }

    private int tempHeightSetting = 25;
    private boolean isDragging;

    @Override
    public void initGui()
    {
        super.initGui();
        buttonList.clear();
        GuiButton clearButton = new GuiButton(0, guiLeft + 54, guiTop + 70, 31, 20, "Dump");
        buttonList.add(clearButton);

    }

    @Override
    public void mouseClicked(int x, int y, int button)
    {
        super.mouseClicked(x, y, button);
        if (slider.inRect(this, x, y))
        {
            isDragging = true;
            tempHeightSetting = tile.interval;
        }
    }

    @Override
    public void mouseClickMove(int x, int y, int button, long timeSinceClicked)
    {
        super.mouseClickMove(x, y, button, timeSinceClicked);
        if (isDragging)
        {
            tempHeightSetting = (x - getLeft() - sliderLeftOffset - 4);
            if (tempHeightSetting < 0)
            {
                tempHeightSetting = 00;
            }
            if (tempHeightSetting > 79)
            {
                tempHeightSetting = 79;
            }
        }
    }

    @Override
    public void mouseMovedOrUp(int x, int y, int button)
    {
        super.mouseMovedOrUp(x, y, button);
        if (isDragging)
        {
            PacketHandler.sendInterfacePacket((byte) 1, (byte) tempHeightSetting);

            tile.interval = tempHeightSetting;
            this.isDragging = false;
        }
    }

    private void updateSliderPosition()
    {

        slider.setX(sliderLeftOffset + (isDragging ? tempHeightSetting : tile.interval));
    }
}
