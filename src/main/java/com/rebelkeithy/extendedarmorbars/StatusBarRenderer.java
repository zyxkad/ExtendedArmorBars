package com.rebelkeithy.extendedarmorbars;

import com.mojang.blaze3d.systems.RenderSystem;
import com.rebelkeithy.extendedarmorbars.utils.DefaultList;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

import java.awt.*;

import static net.minecraft.client.gui.DrawableHelper.GUI_ICONS_TEXTURE;

public class StatusBarRenderer {
    public static int TEXTURE_WIDTH = 45;
    public static int TEXTURE_HEIGHT = 9;
    public static int ICON_WIDTH = 9;
    public static int ICON_HEIGHT = 9;
    public static int OVERLAP = 1;
    public static int BAR_WIDTH = 10 * (ICON_WIDTH - OVERLAP) + OVERLAP;
    public static final int U_EMPTY = 0;
    public static final int U_HALF = 9;
    public static final int U_FULL = 18;
    public static final int U_CAPPED = 27;
    public static final int U_CAPPED_HALF = 36;


    private final Identifier icons;
    private final DefaultList<Color> colors;
    private final boolean mirrored;
    public boolean hideWhenEmpty = true;
    public boolean hideEmptySlots = false;

    public StatusBarRenderer(Identifier icons, DefaultList<Color> colors, boolean mirrored) {
        this.icons = icons;
        this.colors = colors;
        this.mirrored = mirrored;
    }

    public StatusBarRenderer(Identifier icons, DefaultList<Color> colors) {
        this(icons, colors, false);
    }

    public void render(MatrixStack matrices, int x, int y, int statusBarValue) {
        if (hideWhenEmpty && statusBarValue <= 0) {
            return;
        }
        matrices.push();
        RenderSystem.setShaderTexture(0, icons);

        int topLayerIndex = (statusBarValue - 1) / 20;
        int valueOfTopLayer = (statusBarValue - 1) % 20 + 1;
        int numFullIcons = valueOfTopLayer / 2;
        int widthOfFullIcons = numFullIcons * (ICON_WIDTH - OVERLAP) + OVERLAP;
        boolean hasHalfIcon = valueOfTopLayer % 2 == 1;
        int indexOfHalfIcon = valueOfTopLayer/2;
        int dx = mirrored ? BAR_WIDTH - widthOfFullIcons : 0;
        int dxhalfSlot = mirrored ? dx - ICON_WIDTH + OVERLAP :  widthOfFullIcons - OVERLAP;

        drawLayer(matrices, x, y, topLayerIndex - 1, 10);
        drawLayer(matrices, x + dx, y, topLayerIndex, numFullIcons);
        if (hasHalfIcon) {
            if (topLayerIndex < colors.size()) {
                setColor(colors.get(topLayerIndex));
                DrawableHelper.drawTexture(matrices, x + dxhalfSlot, y, U_HALF, 0, ICON_WIDTH, ICON_HEIGHT, TEXTURE_WIDTH, TEXTURE_HEIGHT);
            } else {
                setColor(colors.get(topLayerIndex));
                DrawableHelper.drawTexture(matrices, x + dxhalfSlot, y, U_CAPPED_HALF, 0, ICON_WIDTH, ICON_HEIGHT, TEXTURE_WIDTH, TEXTURE_HEIGHT);
            }
        }

        setColor(Color.WHITE);
        RenderSystem.setShaderTexture(0, GUI_ICONS_TEXTURE);
        matrices.pop();
    }

    public void drawLayer(MatrixStack matrices, int x, int y, int layerIndex, int numIcons) {
        setColor(colors.get(layerIndex));
        int type = getUForLayerIndex(layerIndex);
        drawTiled(matrices, x, y, type, 0, ICON_WIDTH, ICON_HEIGHT, TEXTURE_WIDTH, TEXTURE_HEIGHT, numIcons, 1, OVERLAP, 0);
    }

    public void setColor(Color color) {
        RenderSystem.setShaderColor(color.getRed()/256f, color.getGreen()/256f, color.getBlue()/256f, color.getAlpha()/256f);
    }

    public int getUForLayerIndex(int index) {
        if (index < 0) {
            return U_EMPTY;
        } else if(index >= colors.size()) {
            return U_CAPPED;
        } else {
            return U_FULL;
        }
    }

    public void drawTiled(MatrixStack matrices, int x, int y, float u, float v, int regionWidth, int regionHeight, int textureWidth, int textureHeight, int xCount, int yCount, int xOverlap, int yOverlap) {
        for(int i = 0; i < xCount; i++) {
            DrawableHelper.drawTexture(matrices, x + i * (regionWidth - xOverlap), y, u, v, regionWidth, regionHeight, textureWidth, textureHeight);
        }
    }
}
