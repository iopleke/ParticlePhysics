package particlephysics.gui;

import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.Icon;
import net.minecraft.util.ResourceLocation;
import particlephysics.PacketHandler;
import particlephysics.ParticleRegistry;
import particlephysics.api.BaseParticle;
import particlephysics.tile.EmitterTileEntity;

// Thanks to VSWE for assorted bits of the code
public class GuiEmitter extends GuiContainer
{
    private static final ResourceLocation texture = new ResourceLocation("particlephysics", "textures/gui/emitter.png");
    private final EmitterTileEntity tile;

    public static final int sliderLeftOffset = 38;

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

        if (tile.fuelStored > 0)
        {
            drawTexturedModalRect(guiLeft + 37, guiTop + 89 - (tile.fuelStored / 2), 176, 50 - (tile.fuelStored / 2), 15, 0 + (tile.fuelStored / 2));
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

        // Render selected particle face
        Minecraft.getMinecraft().getTextureManager().bindTexture(TextureMap.locationBlocksTexture);
        if (tile.getStackInSlot(0) != null)
        {
            BaseParticle particle = tile.getParticleFromFuel(tile.getStackInSlot(0).itemID, tile.getStackInSlot(0).getItemDamage());

            if (particle != null)
            {
                Icon icon = ParticleRegistry.getIconFromInstance(particle);
                if (icon != null)
                {
                    //this.drawTexturedModelRectFromIcon(guiLeft + 30, guiTop + 16, icon, 16, 16);
                }
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
        GuiButton clearButton = new GuiButton(0, guiLeft + 58, guiTop + 69, 31, 20, "Dump");
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
            tempHeightSetting = (x - getLeft() - 55);
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
