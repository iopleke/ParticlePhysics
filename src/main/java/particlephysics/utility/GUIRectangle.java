package particlephysics.utility;

import particlephysics.tileentity.emitter.EmitterGUI;

import java.util.Arrays;

public class GUIRectangle
{
    public int x;
    public int y;
    public int w;
    public int h;

    public GUIRectangle(int x, int y, int w, int h)
    {
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
    }

    public void setX(int x)
    {
        this.x = x;
    }

    public void setY(int t, int y)
    {
        this.y = y;
    }

    public boolean inRect(EmitterGUI gui, int mouseX, int mouseY)
    {
        mouseX -= gui.getLeft();
        mouseY -= gui.getTop();
        return mouseX >= x && mouseX <= x + w && mouseY >= y && mouseY <= y + w;

    }

    public void drawString(EmitterGUI gui, int mouseX, int mouseY, String str)
    {
        if (inRect(gui, mouseX, mouseY))
        {
            gui.drawHoverString(Arrays.asList(str.split("\n")), mouseX - gui.getLeft(), mouseY - gui.getTop());
        }
    }

    public void draw(EmitterGUI gui, int srcX, int srcY)
    {
        gui.drawTexturedModalRect(gui.getLeft() + x, gui.getTop() + y, srcX, srcY, w, h);
    }
}
