package com.rebelkeithy.extendedarmorbars;

import java.nio.file.Paths;
import com.rebelkeithy.extendedarmorbars.config.Config;
import com.rebelkeithy.extendedarmorbars.config.ConfigLoader;
import com.rebelkeithy.extendedarmorbars.mixin.InGameHudMixin;
import net.fabricmc.api.ModInitializer;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.tag.FluidTags;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;

public class ToughnessBar implements ModInitializer {

	public static final String MOD_ID = "extendedarmorbars";
	private static final String CONFIG_FILE = MOD_ID + ".json";
	private static final Identifier ARMOR = new Identifier(MOD_ID, "textures/gui/armor.png");
	private static final Identifier TOUGHNESS = new Identifier(MOD_ID, "textures/gui/toughness.png");

	public static Config config;
	public static StatusBarRenderer armorBar;
	public static StatusBarRenderer toughnessBar;
	public static int armorYValue = 0;

	@Override
	public void onInitialize() {
		config = ConfigLoader.loadConfigFile(CONFIG_FILE);
		armorBar = new StatusBarRenderer(ARMOR, config.getParsedColors());
		toughnessBar = new StatusBarRenderer(TOUGHNESS, config.getParsedColors(), true);
	}

	public static void saveAndReloadConfig() {
		ConfigLoader.saveConfigFile(CONFIG_FILE, config);
		armorBar = new StatusBarRenderer(ARMOR, config.getParsedColors());
		armorBar.hideEmptySlots = config.isArmorHideEmptySlots();
		armorBar.hideWhenEmpty = config.isArmorHideWhenEmpty();
		toughnessBar = new StatusBarRenderer(TOUGHNESS, config.getParsedColors(), true);
		toughnessBar.hideEmptySlots = config.isToughnessHideEmptySlots();
		toughnessBar.hideWhenEmpty = config.isToughnessHideWhenEmpty();
	}

	public static void render(MatrixStack matrices) {
		MinecraftClient client = MinecraftClient.getInstance();
		ClientPlayerEntity player = client.player;
		if(player != null) {
			int scaledWidth = client.getWindow().getScaledWidth();
			int scaledHeight = client.getWindow().getScaledHeight();

			if (config.isArmorEnable()) {
				int x = scaledWidth / 2 - 91;
				int y = armorYValue + scaledHeight + getArmorYOffset(player);
				int value = player.getArmor();
				armorBar.render(matrices, x, y, value);
			}

			if(config.isToughnessEnable()) {
				int x = scaledWidth / 2 + 91 - 8 * 10 - 1;
				int y = scaledHeight + getToughnessYOffset(player);
				int value = (int) player.getAttributeValue(EntityAttributes.GENERIC_ARMOR_TOUGHNESS);
				toughnessBar.render(matrices, x, y, value);
			}
		}
	}

	private static int getArmorYOffset(ClientPlayerEntity player) {
		int health = MathHelper.ceil(player.getHealth());
		int renderedHealth = health; //this.renderHealthValue;
		float actualHealth = Math.max((float)player.getAttributeValue(EntityAttributes.GENERIC_MAX_HEALTH), Math.max(renderedHealth, health));
		int absorption = MathHelper.ceil(player.getAbsorptionAmount());
		int healthBarRows = MathHelper.ceil((actualHealth + (float)absorption) / 20.0F);
		if(config.isOneHealthBar()) {
			healthBarRows = 1;
		}
		int r = Math.max(10 - (healthBarRows - 2), 3);
		return -39 - (healthBarRows - 1) * r - 10 - config.getArmorBarOffset();
	}

	private static int getToughnessYOffset(ClientPlayerEntity player) {
		int y = -49;
		int z = player.getMaxAir();
		int aa = Math.min(player.getAir(), z);
		if (player.isSubmergedIn(FluidTags.WATER) || aa < z) {
			y -= 10;
		}
		return y - config.getToughnessBarOffset();
	}
}
