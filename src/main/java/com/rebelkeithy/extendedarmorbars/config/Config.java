package com.rebelkeithy.extendedarmorbars.config;

import com.rebelkeithy.extendedarmorbars.utils.DefaultList;

import java.awt.Color;
import java.util.List;

public class Config {

    private boolean armorEnable = true;
    private boolean armorHideWhenEmpty = true;
    private boolean armorHideEmptySlots = false;
    private boolean toughnessEnable = true;
    private boolean toughnessHideWhenEmpty = true;
    private boolean toughnessHideEmptySlots = false;
    private int armorBarOffset = 0;
    private int toughnessBarOffset = 0;
    private boolean oneHealthBar = false;
    private List<String> colors = List.of("#ECEEFF", "#FF5500", "#FFC747", "#27FFE3", "#00FF00", "#7F00FF");
    private transient DefaultList<Color> parsedColors;

    public List<String> getColors() {
        return colors;
    }

    public void setColors(List<String> colors) {
        this.colors = colors;
        this.parsedColors = null;
    }

    public DefaultList<Color> getParsedColors() {
        if (this.parsedColors == null) {
            this.parsedColors = new DefaultList<>(List.of(), Color.WHITE);
            for(int i = 0; i < colors.size(); i++)
            {
                try {
                    parsedColors.add(Color.decode(colors.get(i)));
                } catch(NumberFormatException e) {
                    System.out.println(e);
                    this.colors.set(i, "invalid");
                    this.parsedColors.add(Color.MAGENTA);
                }
            }
        }
        return this.parsedColors;
    }

    public boolean isArmorEnable() {
        return armorEnable;
    }

    public void setArmorEnable(boolean armorEnable) {
        this.armorEnable = armorEnable;
    }

    public boolean isArmorHideWhenEmpty() {
        return armorHideWhenEmpty;
    }

    public void setArmorHideWhenEmpty(boolean armorHideWhenEmpty) {
        this.armorHideWhenEmpty = armorHideWhenEmpty;
    }

    public boolean isArmorHideEmptySlots() {
        return armorHideEmptySlots;
    }

    public void setArmorHideEmptySlots(boolean armorHideEmptySlots) {
        this.armorHideEmptySlots = armorHideEmptySlots;
    }

    public boolean isToughnessEnable() {
        return toughnessEnable;
    }

    public void setToughnessEnable(boolean toughnessEnable) {
        this.toughnessEnable = toughnessEnable;
    }

    public boolean isToughnessHideWhenEmpty() {
        return toughnessHideWhenEmpty;
    }

    public void setToughnessHideWhenEmpty(boolean toughnessHideWhenEmpty) {
        this.toughnessHideWhenEmpty = toughnessHideWhenEmpty;
    }

    public boolean isToughnessHideEmptySlots() {
        return toughnessHideEmptySlots;
    }

    public void setToughnessHideEmptySlots(boolean toughnessHideEmptySlots) {
        this.toughnessHideEmptySlots = toughnessHideEmptySlots;
    }

    public int getArmorBarOffset() {
        return armorBarOffset;
    }

    public void setArmorBarOffset(int armorBarOffset) {
        this.armorBarOffset = armorBarOffset;
    }

    public int getToughnessBarOffset() {
        return toughnessBarOffset;
    }

    public void setToughnessBarOffset(int toughnessBarOffset) {
        this.toughnessBarOffset = toughnessBarOffset;
    }

    public boolean isOneHealthBar() {
        return oneHealthBar;
    }

    public void setOneHealthBar(boolean oneHealthBar) {
        this.oneHealthBar = oneHealthBar;
    }
}
